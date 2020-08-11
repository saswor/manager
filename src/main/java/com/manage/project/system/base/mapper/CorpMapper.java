package com.manage.project.system.base.mapper;

import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.base.domain.SequenceId;

import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface CorpMapper 
{
	/**
     * 查询商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * 
     * @param corpId 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。ID
     * @return 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     */
	public Corp selectCorpById(String corpId);
	/**
     * 查询商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * 
     * @param corpName 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。corpName
     * @return 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     */
	public Corp selectCorpByCorpName(String corpName);
	
	/**
     * 查询商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。列表
     * 
     * @param corp 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * @return 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。集合
     */
	public List<Corp> selectCorpList(Corp corp);
	
	/**
     * 新增商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。
     * 
     * @param corp 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * @return 结果
     */
	public int insertCorp(Corp corp);
	
	/**
     * 修改商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。
     * 
     * @param corp 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * @return 结果
     */
	public int updateCorp(Corp corp);
	
	/**
     * 删除商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。
     * 
     * @param corpId 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。ID
     * @return 结果
     */
	public int deleteCorpById(String corpId);
	
	/**
     * 批量删除商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。
     * 
     * @param corpIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteCorpByIds(String[] corpIds);
	
	/**
	 * 根据商户名称模糊查询
	 * @param corpName
	 * @param corpId 
	 * @return
	 */
	public List<Corp> selectCorpByName(@Param("corpName") String corpName, @Param("corpId") String corpId);
	
	/**
	 * 根据数据库名获取所有数据库表名
	 * @param schema
	 * @return
	 */
	public List<String> selectAllTableName(@Param("schema") String schema);
	
	/**
	 * 批量生成sequence
	 * @param list
	 * @return
	 */
	public int insertSequenceIdBatch(List<SequenceId> list);
	
}