package com.manage.project.system.vending.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.common.zip.ZipUtils;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.Constant;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.controller.SoftwareVersionController;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingCmd;
import com.manage.project.system.vending.domain.VendingLogfile;
import com.manage.project.system.vending.domain.VendingUpgrade;
import com.manage.project.system.vending.domain.VendingUpgradeTask;
import com.manage.project.system.vending.mapper.VendingLogfileMapper;
import com.manage.project.system.vending.mapper.VendingMapper;

/**
 * 售货机日志文件服务层实现
 * 
 * @author zhangjiawei
 *
 */
@Service
public class VendingLogfileServiceImpl implements IVendingLogfileService{
	
	private Logger log = LoggerFactory.getLogger(VendingLogfileServiceImpl.class);
	
	@Autowired
	private VendingLogfileMapper vendingLogfileMapper;
	
	@Autowired
	private VendingMapper vendingMapper;
	
	@Autowired
	private IVendingCmdService vendingCmdService;
	
	@Autowired
	private ManageConfig manageConfig;
	
	/**
	 * 插入售货机日志文件信息
	 * 
	 * @param vendingLogFile
	 * @return
	 */
	@Override
	public int insertVendingLogfile(VendingLogfile vendingLogfile) {
		//数据校验
		int result = checkVendingLogfile(vendingLogfile);
		if(result!=1) {
			return result;
		}
		String corpId=ShiroUtils.getCorpId();
		vendingLogfile.setLogid(UUID.randomUUID().toString());
		vendingLogfile.setFileId(SystemUtil.getSeqId(corpId, "as_vending_logfile"));
		vendingLogfile.setFileSize(0F);
		vendingLogfile.setFileNum(0);
		vendingLogfile.setFileUrl("");
		vendingLogfile.setFilePath("");
		vendingLogfile.setCurState(Constant.VENDING_LOGFILE_CURSTATE_WAIT);
		vendingLogfile.setStateTime("");
		vendingLogfile.setCorpId(corpId);
		vendingLogfile.setCreateTime(DateUtils.getTime());
		vendingLogfileMapper.insertVendingLogfile(vendingLogfile);
		//通知终端
		int cmdResult = insertVendingCmd(vendingLogfile);
		if(cmdResult!=1) {
			return 6;
		}else {
			return 1;
		}
	}

	private int checkVendingLogfile(VendingLogfile vendingLogfile) {
		//售货机不存在
		String siteId = vendingLogfile.getSiteId();
		if(StringUtils.isEmpty(siteId)) {
			return 2;
		}
		Vending vending = vendingMapper.selectVendingBySiteId(siteId);
		if(vending==null) {
			return 2;
		}
		vendingLogfile.setSiteCode(vending.getSiteCode());
		//开始时间和结束时间不能为空
		String sTime = vendingLogfile.getsTime();
		String eTime = vendingLogfile.geteTime();
		if(StringUtils.isEmpty(sTime)||StringUtils.isEmpty(eTime)) {
			return 3;
		}
		//开始时间不能晚于结束时间
		if(sTime.compareTo(eTime)>=0) {
			return 4;
		}
		//开始时间和结束时间相差不能超过一个半小时
		Date startTime = DateUtils.parseStrToDate(sTime);
		Date endTime = DateUtils.parseStrToDate(eTime);
		long interval = endTime.getTime()-startTime.getTime();
		if(interval>1000*60*90) {
			return 5;
		}
		return 1;
	}
	
	/**
	 * 通知终端升级指令
	 * 
	 * @param vendingUpgrade
	 * @return
	 */
	public int insertVendingCmd(VendingLogfile vendingLogfile) {
		VendingCmd vendingCmd = new VendingCmd();
		vendingCmd.setCmdCode(vendingLogfile.getSiteId());
		vendingCmd.setCmdType(Constant.VENDING_CMD_TYPE_VENDING);
		vendingCmd.setCmd(Constant.CMD_LOG_REPORT);
		Map<String, String> map = new HashMap<String,String>();
		map.put("STime", vendingLogfile.getsTime());
		map.put("ETime", vendingLogfile.geteTime());
		map.put("FileId", vendingLogfile.getFileId());
		vendingCmd.setCont(JSONObject.toJSONString(map));
		return vendingCmdService.insertVendingCmdQuick(vendingLogfile.getSiteId(),vendingCmd,vendingLogfile.getCorpId()); 
	}

	/**
	 * 查询日志文件列表
	 * 
	 * @param vendingLogFile
	 * @return
	 */
	@Override
	public List<VendingLogfile> selectVendingLogfileList(VendingLogfile vendingLogfile) {
		return vendingLogfileMapper.selectVendingLogfileList(vendingLogfile);
	}

	/**
	 * 下载日志文件
	 * 
	 * @param vendingLogfile
	 * @return
	 * @throws IOException 
	 */
	@Override
	public AjaxResult download(VendingLogfile vendingLogfile) throws IOException {
		String fileId = vendingLogfile.getFileId();
		//查询是否有已合并的日志文件存在
		File dir = new File(ManageConfig.getUploadPrefix()+"file/logfile/"+fileId+"/");
		File file = new File(dir,fileId+".log");
		if(file.exists()) {
			return AjaxResult.success("file/logfile/"+fileId+"/"+fileId+".log");
		}
		VendingLogfile logfile = vendingLogfileMapper.selectVendingLogfileByFileId(fileId);
		if(logfile==null) {
			return AjaxResult.error("对应日志信息不存在");
		}else if(!Constant.VENDING_LOGFILE_CURSTATE_FINISH.equals(logfile.getCurState())) {
			return AjaxResult.error("日志还未上传成功,请等待上传完成后再下载");
		}else {
			FileOutputStream fos = null;
			SequenceInputStream sis = null;
			try {
				if(StringUtils.isEmpty(logfile.getFilePath())) {
					return AjaxResult.error("日志文件不存在");
				}
				String[] filePaths = logfile.getFilePath().split("\\|");
				List<FileInputStream> fisList = new ArrayList<FileInputStream>();
				if(!dir.exists()) {
					dir.mkdirs();
				}
				//合并日志
				for (String filePath : filePaths) {
					//解压文件到指定目录
					File zipFile = new File(ManageConfig.getUploadPrefix()+filePath);
					if(zipFile.exists()&&zipFile.isFile()) {
						ZipUtils.unZip(zipFile, dir);
					}else {
						return AjaxResult.error("日志文件不存在");
					}
				}
				File[] listFiles = dir.listFiles();
				if(listFiles==null||listFiles.length==0) {
					return AjaxResult.error("日志文件不存在");
				}
				//获取所有解压后的文件
				for (File unzipFile : listFiles) {
					fisList.add(new FileInputStream(unzipFile));
				}
				Enumeration<FileInputStream> en = Collections.enumeration(fisList);
				sis = new SequenceInputStream(en);
				// 将合成的文件封装成一个文件对象
				fos = new FileOutputStream(file);
				int len = 0;
				byte buf[] = new byte[1024];
				while ((len = sis.read(buf)) != -1) {
					fos.write(buf, 0, len);
				}
				fos.close();
				sis.close();
				return AjaxResult.success("file/logfile/"+fileId+"/"+fileId+".log");
			}catch (Exception e) {
				log.error("下载日志文件失败,时间:"+DateUtils.getTime()+",日志编号:"+vendingLogfile.getFileId(),e);
				if(fos!=null) {
					fos.close();
					fos=null;
				}
				if(sis!=null) {
					sis.close();
					sis=null;
				}
				return AjaxResult.error("下载日志文件失败");
			}
		}
	}

	/**
	 * 根据编号查询日志记录
	 * 
	 * @param fileId
	 * @return
	 */
	@Override
	public VendingLogfile selectVendingLogfileByFileId(String fileId) {
		return vendingLogfileMapper.selectVendingLogfileByFileId(fileId);
	}

	/**
	 * 修改日志记录
	 * 
	 * @param vendingLogfile
	 * @return
	 */
	@Override
	public int updateVendingLogfile(VendingLogfile vendingLogfile) {
		return vendingLogfileMapper.updateVendingLogfile(vendingLogfile);
	}

}
