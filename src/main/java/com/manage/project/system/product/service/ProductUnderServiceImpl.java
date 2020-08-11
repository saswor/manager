package com.manage.project.system.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.product.mapper.ProductInfoMapper;
import com.manage.project.system.product.mapper.ProductLunderMapper;
import com.manage.project.system.product.mapper.ProductUnderMapper;
import com.manage.project.system.product.mapper.ProductVunderMapper;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.domain.ProductLunder;
import com.manage.project.system.product.domain.ProductUnder;
import com.manage.project.system.product.domain.ProductVunder;
import com.manage.project.system.product.service.IProductUnderService;
import com.manage.project.system.product.vo.ProductLunderVo;
import com.manage.project.system.product.vo.UnderLaneProductVo;
import com.manage.project.system.supply.domain.SupplyConfig;
import com.manage.project.system.supply.mapper.SupplyConfigMapper;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.mapper.VendingLanepMapper;
import com.manage.project.system.vending.mapper.VendingMapper;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;

/**
 * 商品下架 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class ProductUnderServiceImpl implements IProductUnderService 
{
	@Autowired
	private ProductUnderMapper productUnderMapper;
	
	@Autowired
	private ProductLunderMapper productLunderMapper;
	
	@Autowired
	private VendingMapper vendingMapper;
	
	@Autowired
	private VendingLanepMapper vendingLanepMapper;
	
	@Autowired
	private ProductInfoMapper productInfoMapper;
	
	@Autowired
	private ProductVunderMapper productVunderMapper;
	
	@Autowired
	private SupplyConfigMapper supplyConfigMapper;

	/**
     * 查询商品下架信息
     * 
     * @param logid 商品下架ID
     * @return 商品下架信息
     */
    @Override
	public ProductUnder selectProductUnderById(String logid)
	{
	    return productUnderMapper.selectProductUnderById(logid);
	}
	
	/**
     * 查询商品下架列表
     * 
     * @param productUnder 商品下架信息
     * @return 商品下架集合
     */
	@Override
	public List<ProductUnder> selectProductUnderList(ProductUnder productUnder)
	{
		if (SystemUtil.isZhilai()) {
			productUnder.setCorpId("");
		} else {
			productUnder.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		return productUnderMapper.selectProductUnderList(productUnder);
	}
	
    /**
     * 新增商品下架
     * 
     * @param productUnder 商品下架信息
     * @return 结果
     */
	@Override
	public int insertProductUnder(ProductUnder productUnder)
	{
		return productUnderMapper.insertProductUnder(productUnder);
	}
	
	/**
     * 修改商品下架
     * 
     * @param productUnder 商品下架信息
     * @return 结果
     */
	@Override
	public int updateProductUnder(ProductUnder productUnder)
	{
	    return productUnderMapper.updateProductUnder(productUnder);
	}

	/**
     * 删除商品下架对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProductUnderByIds(String ids)
	{
		return productUnderMapper.deleteProductUnderByIds(Convert.toStrArray(ids));
	}

//	/**
//     * 批量插入商品下架信息
//     * 
//     * @param productUnder 商品下架信息
//     * @return 结果
//     */
//	@Override
//	@Transactional
//	public int insertProductUnderBatch(List<ProductUnder> productUnderList) {
//		int result = productUnderMapper.insertProductUnderBatch(productUnderList);
//		Set<String> siteIdSet = new HashSet<String>();
//		for (ProductUnder productUnder : productUnderList) {
//			siteIdSet.add(productUnder.getSiteId());
//		}
//		String[] sites=new String[siteIdSet.size()];
//		sites = siteIdSet.toArray(sites);
//		//修改对应售货机的状态
//		vendingMapper.siteProductUnder(sites);
//		return result;
//	}
	
	/**
     * 批量插入商品下架信息
     * 
     * @param productUnder 商品下架信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertProductUnderBatch(List<VendingLanep> list) {
		Map<String,String> productMap = new HashMap<String,String>();
		Map<String,String> siteMap = new HashMap<String,String>();
		Map<String,ProductVunder> siteUpdateMap = new HashMap<String,ProductVunder>();
		List<ProductLunder> productLunderList = new ArrayList<ProductLunder>();
		List<ProductUnder> productUnderList = new ArrayList<ProductUnder>();
		List<ProductVunder> productVunderList = new ArrayList<ProductVunder>();
		List<ProductVunder> productVunderUpdateList = new ArrayList<ProductVunder>();
		if(list!=null&&list.size()!=0) {		
			for (VendingLanep vendingLanep : list) {
				if(vendingLanep!=null) {
					if(vendingLanep.getSiteId()!=null&&vendingLanep.getProductId()!=null&&vendingLanep.getCurCap()!=null&&vendingLanep.getCurCap()!=0) {
						String corpId=vendingLanep.getCorpId();
						if(StringUtils.isEmpty(corpId)) {
							corpId = ShiroUtils.getCorpId();
						}	
						//查询是否有等待下架的记录存在
						ProductLunder productLunderSelect = new ProductLunder();
						productLunderSelect.setSiteId(vendingLanep.getSiteId());
						productLunderSelect.setProductId(vendingLanep.getProductId());
						productLunderSelect.setLaneSId(vendingLanep.getLaneSId());
						productLunderSelect.setLaneEId(vendingLanep.getLaneEId());
						productLunderSelect.setCurState("1");
						List<ProductLunderVo> selectProductLunderList = productLunderMapper.selectProductLunderList(productLunderSelect);
						if(selectProductLunderList==null||selectProductLunderList.isEmpty()) {
							
							ProductLunder productLunder = new ProductLunder();
							productLunder.setLogid(UUID.randomUUID().toString());						
							productLunder.setLunderId(SystemUtil.getSeqIdLong(corpId, "product_lunder_id"));
							//将对应站点的vunderId存入map
							if(siteMap.get(vendingLanep.getSiteId())==null) {
								if(siteUpdateMap.get(vendingLanep.getSiteId())==null) {
									//查询当前售货机是否有正在下架的商品
									ProductVunder productVunderSelect = new ProductVunder();
									productVunderSelect.setSiteId(vendingLanep.getSiteId());
									productVunderSelect.setCurState("1");
									List<ProductVunder> selectProductVunderList = productVunderMapper.selectProductVunderList(productVunderSelect);
									if(selectProductVunderList==null||selectProductVunderList.isEmpty()) {	
										siteMap.put(vendingLanep.getSiteId(),SystemUtil.getSeqIdLong(corpId,"as_product_vunder"));		
									}else {
										siteUpdateMap.put(vendingLanep.getSiteId(),selectProductVunderList.get(0));
									}
								}				
							}
							//从map中获取对应的vunderId
							if(siteMap.get(vendingLanep.getSiteId())!=null) {
								productLunder.setvUnderId(siteMap.get(vendingLanep.getSiteId()));
							}else {
								productLunder.setvUnderId(siteUpdateMap.get(vendingLanep.getSiteId()).getvUnderId());
							}
							if(productMap.get(vendingLanep.getSiteId()+","+vendingLanep.getProductId())==null) {
								productMap.put(vendingLanep.getSiteId()+","+vendingLanep.getProductId(),SystemUtil.getSeqIdLong(corpId,"as_product_under"));
							}
							productLunder.setUnderId(productMap.get(vendingLanep.getSiteId()+","+vendingLanep.getProductId()));
							Vending vending = vendingMapper.selectNeverDelVendingBySiteId(vendingLanep.getSiteId());
							if(vending==null) {
								return 2;
							}else {
								productLunder.setDistrictId(vending.getDistrictId());
								productLunder.setLineId(vending.getLineId());
								productLunder.setPointId(vending.getPointId());
								productLunder.setSiteId(vending.getSiteId());
								productLunder.setSiteName(vending.getSiteName());
							}
							productLunder.setProductId(vendingLanep.getProductId());
							productLunder.setUnderNum(0);
							productLunder.setLaneSId(vendingLanep.getLaneSId());
							productLunder.setLaneEId(vendingLanep.getLaneEId());
							productLunder.setCurState("1");
							productLunder.setCorpId(corpId);
							productLunder.setCreateTime(DateUtils.getTime());
							productLunderList.add(productLunder);
						}
					}
				}		
			}
		}
		//获取所有的商品站点下架信息
		Set<String> productKeySet = productMap.keySet();
		for (String key : productKeySet) {
			String[] keys = key.split(",");
			String siteId=keys[0];
			String productId=keys[1];
			ProductUnder productUnderSelect = new ProductUnder();
			productUnderSelect.setProductId(productId);
			productUnderSelect.setSiteId(siteId);
			productUnderSelect.setCurState("1");
			List<ProductUnder> underList = productUnderMapper.selectProductUnderList(productUnderSelect);
			//当前售货机没有该种类的商品等待下架时,插入信息
			if(underList==null||underList.isEmpty()) {
				ProductUnder productUnder = new ProductUnder();
				productUnder.setLogid(UUID.randomUUID().toString());
				productUnder.setUnderId(productMap.get(key));
				if(siteMap.get(keys[0])!=null) {
					productUnder.setvUnderId(siteMap.get(keys[0]));
				}else {
					productUnder.setvUnderId(siteUpdateMap.get(keys[0]).getvUnderId());
				}
				productUnder.setUnderNum(0);
				Vending vending = vendingMapper.selectNeverDelVendingBySiteId(siteId);
				if(vending==null) {
					return 2;
				}else {
					productUnder.setDistrictId(vending.getDistrictId());
					productUnder.setLineId(vending.getLineId());
					productUnder.setPointId(vending.getPointId());
					productUnder.setSiteId(vending.getSiteId());
					productUnder.setSiteName(vending.getSiteName());
				}
				ProductInfo productInfo = productInfoMapper.selectProductInfoByProductId(productId);
				if(productInfo==null) {
					return 3;
				}else {
					productUnder.setProductId(productId);
					productUnder.setName(productInfo.getName());
					productUnder.setFullName(productInfo.getFullName());
				}
				productUnder.setCurState("1");
				productUnder.setStateTime(DateUtils.getTime());
				productUnder.setUnderType("1");
				productUnder.setCorpId(vending.getCorpId());
				productUnder.setCreateTime(DateUtils.getTime());
				productUnderList.add(productUnder);
			}	
		}
		//封装所有站点下架信息
		Set<String> siteKeySet = siteMap.keySet();
		for (String siteId : siteKeySet) {
			ProductVunder productVunder = new ProductVunder();
			productVunder.setLogid(UUID.randomUUID().toString());
			productVunder.setvUnderId(siteMap.get(siteId));
			Vending vending = vendingMapper.selectVendingBySiteId(siteId);
			productVunder.setCorpId(vending.getCorpId());
			productVunder.setSiteId(siteId);
			productVunder.setLineId(vending.getLineId());
			//查询线路对应的补货员
			SupplyConfig supplyConfig = supplyConfigMapper.selectSupplyConfigByLineId(vending.getLineId());
			if(supplyConfig==null||StringUtils.isEmpty(supplyConfig.getSupplierId())) {
				return 4;
			}else {
				productVunder.setLoginId(supplyConfig.getSupplierId());
			}
			//productVunder.setLoginId(ShiroUtils.getLoginName());
			productVunder.setCurState("1");
			productVunder.setStateTime(DateUtils.getTime());
			productVunder.setCreateTime(DateUtils.getTime());
			productVunder.setUnderNum(0);
			productVunderList.add(productVunder);
		}
//		Set<String> siteUpdateKeySet = siteUpdateMap.keySet();
//		for (String siteId : siteUpdateKeySet) {
//			ProductVunder productVunder = siteUpdateMap.get(siteId);
//			productVunder.setCurState("1");
//			productVunder.setStateTime(DateUtils.getTime());
//			productVunder.setUnderNum(0);
//			productVunderMapper.updateProductVunder(productVunder);
//		}
		if (!productUnderList.isEmpty()) {
			productUnderMapper.insertProductUnderBatch(productUnderList);
		}else {
			return 5;
		}
		if (!productLunderList.isEmpty()) {
			productLunderMapper.insertProductLunderBatch(productLunderList);
		}
		if (!productVunderList.isEmpty()) {
			productVunderMapper.insertProductVunderBatch(productVunderList);
		}
		return 1;
	}	
}
