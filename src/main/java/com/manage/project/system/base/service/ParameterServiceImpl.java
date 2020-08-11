/**
 * 
 */
package com.manage.project.system.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.project.system.base.domain.Parameter;
import com.manage.project.system.base.mapper.ParameterMapper;

/**
 * 系统参数服务层 实现类
 * 
 * @author zhangjiawei
 * @date 2018年10月29日
 * 
 */
@Service
public class ParameterServiceImpl implements IParameterService{

	@Autowired
	private ParameterMapper parameterMapper;
	
	/**
     * 查询系统参数列表
     * 
     * @param parameter 查询条件
     * @return 系统参数值
     */
	public List<Parameter> selectParameterList(Parameter parameter){
		return parameterMapper.selectParameterList(parameter);
	}
	
	/**
     * 根据名称和描述查询系统参数
     * 
     * @param parameter 查询条件
     * @return 系统参数值
     */
	@Override
	public Parameter selectParameterByName(String name) {
		return parameterMapper.selectParameterByName(name);
	}

	/**
     * 修改系统参数
     * 
     * @param parameter 系统参数
     * @return 结果
     */
	@Override
	public int update(Parameter parameter) {
		return parameterMapper.update(parameter);
	}

	/**
     * 删除系统参数
     * 
     * @param parameter 系统参数
     * @return 结果
     */
	@Override
	public int delete(String logid) {
		return parameterMapper.delete(logid);
	}

	/**
     * 添加系统参数
     * 
     * @param parameter 系统参数
     * @return 结果
     */
	@Override
	public int insert(Parameter parameter) {
		return parameterMapper.insert(parameter);
	}

}
