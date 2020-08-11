package com.manage.project.system.vending.mapper;

import java.util.List;

import com.manage.project.system.vending.domain.VendingLogfile;

/**
 * 售货机日志文件数据层
 * 
 * @author zhangjiawei
 *
 */
public interface VendingLogfileMapper {

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
	List<VendingLogfile> selectVendingLogfileList(VendingLogfile vendingLogFile);

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
