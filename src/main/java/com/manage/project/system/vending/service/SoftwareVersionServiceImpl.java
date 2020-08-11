package com.manage.project.system.vending.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.file.FileUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.config.ManageConfig;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.SoftwareFile;
import com.manage.project.system.vending.domain.SoftwareVersion;
import com.manage.project.system.vending.domain.VendingUpgrade;
import com.manage.project.system.vending.mapper.SoftwareFileMapper;
import com.manage.project.system.vending.mapper.SoftwareVersionMapper;
import com.manage.project.system.vending.mapper.VendingUpgradeMapper;

/**
 * 软件版本信息列表 服务层实现
 * 
 * @author zhangjiawei
 *
 */
@Service
public class SoftwareVersionServiceImpl implements ISoftwareVersionService{
	
	@Autowired
	private SoftwareVersionMapper softwareVersionMapper;
	
	@Autowired
	private SoftwareFileMapper softwareFileMapper;
	
	@Autowired
	private VendingUpgradeMapper vendingUpgradeMapper;
	
	@Autowired
	private ManageConfig manageConfig;
	
	private Logger log = LoggerFactory.getLogger(SoftwareVersionServiceImpl.class);
	
	/**
	 * 查询软件版本信息列表
	 * 
	 * @param softwareVersion
	 * @return
	 */
	@Override
	public List<SoftwareVersion> selectSoftwareVersionList(SoftwareVersion softwareVersion){
		return softwareVersionMapper.selectSoftwareVersionList(softwareVersion);
	}
	
	/**
	 * 根据版本号查询软件版本信息
	 * 
	 * @param versionId
	 * @return
	 */
	@Override
	public SoftwareVersion selectSoftwareVersionById(String versionId) {
		SoftwareVersion softwareVersion = softwareVersionMapper.selectSoftwareVersionById(versionId);
		List<SoftwareFile> softwareFileList = softwareFileMapper.selectSoftwareFileByVersionId(versionId);
		softwareVersion.setSoftwareFileList(softwareFileList);
		return softwareVersion;
	}
	
	/**
	 * 插入软件版本信息
	 * 
	 * @param softwareVersion
	 * @return
	 */
	@Override
	@Transactional
	public int insertSoftwareVersion(SoftwareVersion softwareVersion) {
		//校验版本信息
		int flag = checkSoftwareVersion(softwareVersion);
		if(flag!=1) {
			return flag;
		}
		List<SoftwareFile> softwareFileList = softwareVersion.getSoftwareFileList();
		//版本信息
		String corpId=ShiroUtils.getCorpId();
		int sumFileNum=0;
		float sumFileSize=0F;
		softwareVersion.setLogid(UUID.randomUUID().toString());
		String versionId = SystemUtil.getSeqId(corpId, "as_software_version");
		softwareVersion.setVersionId(versionId);
		softwareVersion.setCorpId(corpId);
		softwareVersion.setCreateTime(DateUtils.getTime());
		int seqId=0;
		for (SoftwareFile softwareFile : softwareFileList) {
			softwareFile.setLogid(UUID.randomUUID().toString());
			softwareFile.setvFileId(SystemUtil.getSeqIdLong(corpId, "as_software_file"));
			softwareFile.setVersionId(versionId);
			softwareFile.setCreateTime(DateUtils.getTime());
			softwareFile.setCorpId(corpId);
			seqId++;
			softwareFile.setSeqId(seqId);
			sumFileNum++;
			sumFileSize+=softwareFile.getFileSize();
			//保存版本文件
			try {
				softwareFile = saveSoftwareVersion(softwareFile);
				if(softwareFile==null) {
					return 6;
				}
			}catch (Exception e) {
				log.error("保存版本信息失败:保存版本文件失败",e);
				return 6;
			}
		}
		softwareVersion.setFileNum(sumFileNum);
		softwareVersion.setFileSize(sumFileSize);
		softwareFileMapper.insertSoftwareFileBatch(softwareFileList);
		softwareVersionMapper.insertSoftwareVersion(softwareVersion);
		return 1;
	}
	
	/**
	 * 保存版本文件
	 * 
	 * @param softwareVersion
	 * @return 
	 */
	private SoftwareFile saveSoftwareVersion(SoftwareFile softwareFile) {
		//文件云地址不为空,说明是上次上传的且本次未修改
		if(StringUtils.isNotEmpty(softwareFile.getFilePath())) {
			return softwareFile;
		}
		File file = new File(ManageConfig.getUploadPrefix()+softwareFile.getLocalPath());
		boolean flag;
		//文件不存在
		if(!file.exists()) {
			log.error("保存版本信息失败:保存版本文件失败,源文件不存在,时间"+DateUtils.getTime());
			return null;
		}else {
			File destDir = new File(ManageConfig.getUploadPrefix()+"file/version/"+softwareFile.getVersionId());
			File dest = new File(destDir,softwareFile.getFileName());
			if(!destDir.exists()) {
				destDir.mkdirs();
			}
			if(dest.exists()) {
				dest.delete();
			}
			flag = file.renameTo(dest);
			if(flag) {
				softwareFile.setLocalPath("/file/version/"+softwareFile.getVersionId()+"/"+softwareFile.getFileName());
				softwareFile.setFilePath(manageConfig.getIp()+softwareFile.getLocalPath());
				return softwareFile;
			}else {
				log.error("保存版本信息失败:保存版本文件失败,复制文件到指定目录失败,时间"+DateUtils.getTime());
				return null;
			}	
		}
	}
	
	/**
	 * 校验版本信息
	 * 
	 * @param softwareVersion
	 * @return
	 */
	private int checkSoftwareVersion(SoftwareVersion softwareVersion) {
		//版本号为空
		if(StringUtils.isEmpty(softwareVersion.getvName())) {
			return 2;
		}
		//版本号已存在
		SoftwareVersion softwareVersionSelect = new SoftwareVersion();
		softwareVersionSelect.setvName(softwareVersion.getvName());
		softwareVersionSelect.setCorpId(ShiroUtils.getCorpId());
		List<SoftwareVersion> list = softwareVersionMapper.selectSoftwareVersionList(softwareVersionSelect);
		if(list!=null&&!list.isEmpty()) {
			for (SoftwareVersion softwareVersionExit : list) {
				if(!softwareVersionExit.getVersionId().equals(softwareVersion.getVersionId())) {
					return 3;
				}
			}	
		}
		//版本类型为空
		if(StringUtils.isEmpty(softwareVersion.getuType())) {
			return 4;
		}
		//版本中一个文件也没有
		List<SoftwareFile> softwareFileList = softwareVersion.getSoftwareFileList();
		if(softwareFileList==null||softwareFileList.isEmpty()) {
			return 5;
		}
		return 1;
	}

	/**
	 * 修改软件版本信息
	 * 
	 * @param softwareVersion
	 * @return
	 */
	@Override
	@Transactional
	public int updateSoftwareVersion(SoftwareVersion softwareVersion) {
		//校验版本信息
		int flag = checkSoftwareVersion(softwareVersion);
		if(flag!=1) {
			return flag;
		}
		List<SoftwareFile> softwareFileList = softwareVersion.getSoftwareFileList();
		//版本信息
		String corpId=softwareVersion.getCorpId();
		int sumFileNum=0;
		float sumFileSize=0F;
		String versionId = softwareVersion.getVersionId();
		int seqId=0;
		for (SoftwareFile softwareFile : softwareFileList) {
			softwareFile.setLogid(UUID.randomUUID().toString());
			softwareFile.setvFileId(SystemUtil.getSeqIdLong(corpId, "as_software_file"));
			softwareFile.setVersionId(versionId);
			softwareFile.setCreateTime(DateUtils.getTime());
			softwareFile.setCorpId(corpId);
			seqId++;
			softwareFile.setSeqId(seqId);
			sumFileNum++;
			sumFileSize+=softwareFile.getFileSize();
			//保存版本文件
			try {
				softwareFile = saveSoftwareVersion(softwareFile);
				if(softwareFile==null) {
					return 6;
				}
			}catch (Exception e) {
				log.error("保存版本信息失败:保存版本文件失败",e);
				return 6;
			}
		}
		softwareVersion.setFileNum(sumFileNum);
		softwareVersion.setFileSize(sumFileSize);
		softwareFileMapper.deleteSoftwareFileByVersionId(versionId);
		softwareFileMapper.insertSoftwareFileBatch(softwareFileList);
		softwareVersionMapper.updateSoftwareVersion(softwareVersion);
		return 1;
	}
	
	/**
	 * 删除软件版本信息
	 * 
	 * @param softwareVersion
	 * @return
	 */
	@Override
	@Transactional
	public int deleteSoftwareVersion(String ids) {
		String[] versionIds = ids.split(",");
		//版本编号不能为空
		if(versionIds==null||versionIds.length==0) {
			return 2;
		}
		for (String versionId : versionIds) {
			//查询该版本是否已加入升级任务
			VendingUpgrade vendingUpgrade = new VendingUpgrade();
			vendingUpgrade.setVersionId(versionId);
			List<VendingUpgrade> vendingUpgradeList = vendingUpgradeMapper.selectVendingUpgradeList(vendingUpgrade);
			if(vendingUpgradeList!=null&&!vendingUpgradeList.isEmpty()) {
				return 3;
			}
		}		
		for (String versionId : versionIds) {
			SoftwareVersion softwareVersion = softwareVersionMapper.selectSoftwareVersionById(versionId);
			log.info("删除版本信息:"+JSONObject.toJSONString(softwareVersion)+",时间"+DateUtils.getTime());
			softwareFileMapper.deleteSoftwareFileByVersionId(versionId);
			//删除版本所有的文件
			FileUtils.deleteDir(new File(ManageConfig.getUploadPrefix()+"file/version/"+versionId));
		}
		softwareVersionMapper.deleteSoftwareVersionByIds(versionIds);
		return 1;
	}
}
