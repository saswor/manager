package com.manage.project.system.vending.service;

import java.io.IOException;
import java.util.List;

import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.vending.domain.VendingLogfile;

/**
 * 售货机日志文件服务层接口
 * 
 * @author zhangjiawei
 *
 */
public interface IVendingLogfileService {

	/**
	 * 插入售货机日志文件信息
	 * 
	 * @param vendingLogFile
	 * @return
	 */
	int insertVendingLogfile(VendingLogfile vendingLogFile);

	/**
	 * 查询日志文件列表
	 * 
	 * @param vendingLogFile
	 * @return
	 */
	List<VendingLogfile> selectVendingLogfileList(VendingLogfile vendingLogfile);

	/**
	 * 下载日志文件
	 * 
	 * @param vendingLogFile
	 * @return
	 */
	AjaxResult download(VendingLogfile vendingLogFile) throws IOException;

	/**
	 * 根据编号查询日志记录
	 * 
	 * @param fileId
	 * @return
	 */
	VendingLogfile selectVendingLogfileByFileId(String fileId);

	/**
	 * 修改日志记录
	 * 
	 * @param vendingLogfile
	 * @return
	 */
	int updateVendingLogfile(VendingLogfile vendingLogfile);
}
