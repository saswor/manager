/**
 * 
 */
package com.manage.project.system.base.mapper;

import java.util.List;

import com.manage.project.system.base.domain.Parameter;

/**
 * 系统参数数据层 sys_parameter
 * 
 * @author zhangjiawei
 * @date 2018年10月29日
 * 
 */
public interface ParameterMapper {

	/**
     * 根据名称和描述查询系统参数
     * 
     * @param parameter 查询条件
     * @return 系统参数值
     */
	Parameter selectParameterByName(String name);

	/**
     * 修改系统参数
     * 
     * @param parameter 系统参数
     * @return 结果
     */
	int update(Parameter parameter);

	/**
     * 删除系统参数
     * 
     * @param parameter 系统参数
     * @return 结果
     */
	int delete(String logid);
	
	/**
     * 添加系统参数
     * 
     * @param parameter 系统参数
     * @return 结果
     */
	int insert(Parameter parameter);

	/**
     * 查询系统参数列表
     * 
     * @param parameter 查询条件
     * @return 系统参数值
     */
	List<Parameter> selectParameterList(Parameter parameter);

}
