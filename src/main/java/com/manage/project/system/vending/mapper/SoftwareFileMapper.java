package com.manage.project.system.vending.mapper;

import java.util.List;

import com.manage.project.system.vending.domain.SoftwareFile;

/**
 * 软件文件下载信息 数据层
 * 
 * @author zhangjiawei
 *
 */
public interface SoftwareFileMapper {

	/**
	 * 查询软件文件下载信息列表
	 * 
	 * @param softwareFile
	 * @return
	 */
	List<SoftwareFile> selectSoftwareFileByVersionId(String versionId);
	
	/**
	 * 批量插入软件文件下载信息
	 * 
	 * @param list
	 * @return
	 */
	int insertSoftwareFileBatch(List<SoftwareFile> list);
	
	/**
	 * 根据版本号删除软件文件下载信息
	 * 
	 * @param ids
	 * @return
	 */
	int deleteSoftwareFileByVersionId(String versionIds);
}
