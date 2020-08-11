package com.manage.project.system.base.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.base.mapper.CorpMapper;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.base.domain.SequenceId;
import com.manage.project.system.base.service.ICorpService;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.jayway.jsonpath.JsonPath;
import com.manage.common.support.Convert;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.datasource.DynamicDataSourceContextHolder;

/**
 * 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class CorpServiceImpl implements ICorpService 
{
	@Autowired
	private CorpMapper corpMapper;
	
	@Autowired
	private BussinessCacheService bussinessCacheService;
	@Autowired
    private ManageConfig manageConfig;
	
	@Value("classpath:sequence.json")
	private Resource sequenceIds;

	/**
     * 查询商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * 
     * @param corpId 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。ID
     * @return 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     */
    @Override
	public Corp selectCorpById(String corpId)
	{
	    return corpMapper.selectCorpById(corpId);
	}
	
	/**
     * 查询商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。列表
     * 
     * @param corp 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * @return 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。集合
     */
	@Override
	public List<Corp> selectCorpList(Corp corp)
	{
	    return corpMapper.selectCorpList(corp);
	}
	
    /**
     * 新增商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。
     * 
     * @param corp 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertCorp(Corp corp)
	{
	    int r = corpMapper.insertCorp(corp);
	    
//	    String schema = manageConfig.getSchema();
//	    List<String> tableNames = corpMapper.selectAllTableName(schema);
	    List<String> tableNames = this.selectAllTableName();
	    List<SequenceId> seqList = new ArrayList<SequenceId>();
	    for (int i = 0; i < tableNames.size(); i++) {
	    	String table = tableNames.get(i);
	    	SequenceId s = new SequenceId();
	    	s.setCorpId(corp.getCorpId());
	    	s.setLogid(UUID.randomUUID().toString());
	    	s.setName(table);
	    	s.setId("1");
	    	s.setDescription("");
	    	seqList.add(s);
	    }
	    corpMapper.insertSequenceIdBatch(seqList);
	    
	    bussinessCacheService.initCorp();
		return r;
	}
	
	private List<String> selectAllTableName() {
			String areaData = null;
			try {
				areaData = IOUtils.toString(sequenceIds.getInputStream(), Charset.forName("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<String> districtNames = JsonPath.read(areaData, "$.[*].name");
			return districtNames;

	}
	
	public static void main(String[] args) throws IOException {
		List<String> districtNames = JsonPath.read(new File("D:\\workspace\\manage\\manage\\src\\main\\resources\\sequence.json"), "$.[*].name");
		System.out.println(districtNames);
	}
	
	/**
     * 修改商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。
     * 
     * @param corp 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * @return 结果
     */
	@Override
	public int updateCorp(Corp corp)
	{
	    int r = corpMapper.updateCorp(corp);
	    bussinessCacheService.initCorp();
		return r;
	}

	/**
     * 删除商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。对象
     * 
     * @param corpIds 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCorpByIds(String corpIds)
	{
		int r = corpMapper.deleteCorpByIds(Convert.toStrArray(corpIds));
		bussinessCacheService.initCorp();
		return r;
	}
	
	/**
	 * 根据商户名称模糊查询
	 * @param corpName
	 * @return
	 */
	public List<Corp> selectCorpByName(String corpName) {
		String corpId = SystemUtil.getCorpId();
		return corpMapper.selectCorpByName(corpName,corpId);
	}
	/**
     * 查询商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     * 
     * @param corpName 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。corpName
     * @return 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。信息
     */
	@Override
	public Corp selectCorpByCorpName(String corpName) {
		
		return corpMapper.selectCorpByCorpName(corpName);
	}
}
