package com.manage.project.system.vending.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.vending.mapper.VendingLanepMapper;
import com.manage.project.system.vending.mapper.VendingStockMapper;
import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.Dispatch;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.domain.VendingStock;
import com.manage.project.system.vending.service.IVendingLanepService;
import com.manage.project.system.vending.vo.UnderProductVo;
import com.manage.project.system.vendingPoint.domain.VendingPoint;
import com.manage.common.support.Convert;
import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.security.ShiroUtils;

/**
 * 售货机货道商品 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingLanepServiceImpl implements IVendingLanepService 
{
	@Autowired
	private VendingLanepMapper vendingLanepMapper;
	
	@Autowired
	private VendingStockMapper vendingStockMapper;

	/**
     * 查询售货机货道商品信息
     * 
     * @param logid 售货机货道商品ID
     * @return 售货机货道商品信息
     */
    @Override
	public VendingLanep selectVendingLanepById(String logid)
	{
	    return vendingLanepMapper.selectVendingLanepById(logid);
	}
	
	/**
     * 查询售货机货道商品列表
     * 
     * @param vendingLanep 售货机货道商品信息
     * @return 售货机货道商品集合
     */
	@Override
	public List<VendingLanep> selectVendingLanepList(VendingLanep vendingLanep)
	{
	    return vendingLanepMapper.selectVendingLanepList(vendingLanep);
	}
	
    /**
     * 新增售货机货道商品
     * 
     * @param vendingLanep 售货机货道商品信息
     * @return 结果
     */
	@Override
	public int insertVendingLanep(VendingLanep vendingLanep)
	{
		vendingLanep.setLogid(UUID.randomUUID().toString());
		vendingLanep.setCorpId(ShiroUtils.getUser().getCorpId());
	    return vendingLanepMapper.insertVendingLanep(vendingLanep);
	}
	
	/**
     * 修改售货机货道商品
     * 
     * @param vendingLanep 售货机货道商品信息
     * @return 结果
     */
	@Override
	public int updateVendingLanep(VendingLanep vendingLanep)
	{
	    return vendingLanepMapper.updateVendingLanep(vendingLanep);
	}

	/**
     * 删除售货机货道商品对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingLanepByIds(String ids)
	{
		return vendingLanepMapper.deleteVendingLanepByIds(Convert.toStrArray(ids));
	}
	
	/**
	 * 根据货柜id查询该货柜货道及商品
	 * @param cabinetId	货柜id
	 * @return	货道商品列表
	 */
	@Override
	public List<VendingLanep> selectVendingLanepByCabinetId(String cabinetId) {
		return vendingLanepMapper.selectVendingLanepByCabinetId(cabinetId);
	}

	/**  根据阈值查询站点的补货商品信息**/
	public List<VendingLanep> selectSupplyPThreshold(String siteId){
		return vendingLanepMapper.selectSupplyPThreshold(siteId);
	}
	/**  根据站点查询所有商品补货信息**/
	public List<VendingLanep> selectSupplyProduct(String siteId){
		return vendingLanepMapper.selectSupplyProduct(siteId);
	}

	/**
	 * 查询下架商品的站点
	 * 
	 * @param vendingLaneProductVo 下架商品信息
	 * @return
	 */
	@Override
	public List<UnderProductVo> selectUnderProductSite(UnderProductVo vendingLaneProductVo) {
		VendingStock vendingStock = new VendingStock();
		vendingStock.setCorpId(vendingLaneProductVo.getCorpId());
		vendingStock.setProductId(vendingLaneProductVo.getProductId());
		List<UnderProductVo> resultList = new ArrayList<UnderProductVo>();
		List<VendingStock> list = vendingStockMapper.selectVendingStockListByProductId(vendingStock);
		for (VendingStock vs : list) {
			UnderProductVo underProductVo = new UnderProductVo();
			underProductVo.setLogid(vs.getLogid());
			String siteId = vs.getSiteId();
			underProductVo.setSiteId(siteId);
			underProductVo.setSiteName(vs.getSiteName());
			Vending vending = SystemUtil.getVendingBase(siteId);
			if(vending!=null) {
				underProductVo.setDistrictName(SystemUtil.getVendingDistrictNameFromCache(vending.getDistrictId()));
				underProductVo.setLineName(SystemUtil.getVendingLineNameFromCache(vending.getLineId()));
				VendingPoint point = SystemUtil.getVendingPointFromCache(vending.getPointId());
				if(point!=null) {
					Map<String, Dispatch> map = (Map<String, Dispatch>)CacheUtils.get(Constant.DISPATCH_CACHE);
        			Dispatch dispatch = map.get(point.getDistrict());
        			underProductVo.setDispatch(dispatch.getNamepath());
				}
			}
			underProductVo.setCreateTime(vs.getCreateTime());
			resultList.add(underProductVo);
		}
		return resultList;
	}

	/**
	 * 通过id数组查询货道商品信息
	 * 
	 * @param id id数组
	 * @return
	 */
	@Override
	public List<UnderProductVo> selectVendingLanepByIds(String[] id) {
		return vendingLanepMapper.selectVendingLanepByIds(id);
	}

	@Override
	public List<VendingLanep> selectVendingProduct(String siteId) {
		return vendingLanepMapper.selectVendingProduct(siteId);
	}
}
