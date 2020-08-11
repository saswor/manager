package com.manage.project.system.vending.service;

import java.util.List;

import com.manage.project.system.vending.domain.SoftwareVersion;

/**
 * 软件版本信息列表 服务层
 * 
 * @author zhangjiawei
 *
 */
public interface ISoftwareVersionService {

	/**
	 * 查询软件版本信息列表
	 * 
	 * @param softwareVersion
	 * @return
	 */
	List<SoftwareVersion> selectSoftwareVersionList(SoftwareVersion softwareVersion);
	
	/**
	 * 根据版本号查询软件版本信息
	 * 
	 * @param versionId
	 * @return
	 */
	SoftwareVersion selectSoftwareVersionById(String versionId);
	
	/**
	 * 插入软件版本信息
	 * 
	 * @param softwareVersion
	 * @return
	 */
	int insertSoftwareVersion(SoftwareVersion softwareVersion);
	
	/**
	 * 修改软件版本信息
	 * 
	 * @param softwareVersion
	 * @return
	 */
	int updateSoftwareVersion(SoftwareVersion softwareVersion);
	
	/**
	 * 删除软件版本信息
	 * 
	 * @param softwareVersion
	 * @return
	 */
	int deleteSoftwareVersion(String ids);

}
