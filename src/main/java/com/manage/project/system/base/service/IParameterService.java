/**
 * 
 */
package com.manage.project.system.base.service;

import java.util.List;

import com.manage.project.system.base.domain.Parameter;

/**
 * 系统参数服务层 接口
 * 
 * @author zhangjiawei
 * @date 2018年10月29日
 * 
 */
public interface IParameterService {
	
	/**
     * 查询系统参数列表
     * 
     * @param parameter 查询条件
     * @return 系统参数值
     */
	public List<Parameter> selectParameterList(Parameter parameter);

	/**
     * 根据名称和描述查询系统参数
     * 
     * @param name 查询条件
     * @return 系统参数值
     */
	public Parameter selectParameterByName(String name);
	
	/**
     * 修改系统参数
     * 
     * @param parameter 系统参数
     * @return 结果
     */
	public int update(Parameter parameter);
	
	/**
     * 删除系统参数
     * 
     * @param parameter 系统参数
     * @return 结果
     */
	public int delete(String logid);
	
	/**
     * 添加系统参数
     * 
     * @param parameter 系统参数
     * @return 结果
     */
	public int insert(Parameter parameter);
}
