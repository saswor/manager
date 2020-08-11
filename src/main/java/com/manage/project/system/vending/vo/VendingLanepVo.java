package com.manage.project.system.vending.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manage.common.utils.StringUtils;
import com.manage.framework.config.ManageConfig;
import com.manage.project.common.Constant;
import com.manage.project.system.util.SystemUtil;

@Component
public class VendingLanepVo {
	
	@Autowired
	private ManageConfig manageConfig;
	/** 记录编号 */
	private String logid = "";
	/** 货道主键 */
	private String slaneId = "";
	/** 售货机编号 */
	private String siteId = "";
	/** 售货机名称 */
	private String siteName = "";
	/** 货道开始编号(一个商品占多个货道) */
	private Integer laneSId;
	/** 货道结束编号(如果只有一个商品则只有开始货道编号) */
	private Integer laneEId;
	/** 商品编号 */
	private String productId = "";
	/** 商品名称 */
	private String productName = ""; 
	/** 商品图片(json存储) */
	private String productPic = "";
	/** 售卖价格 */
	private Float salePrice = 0f;
	/** 存放容量 */
	private Integer capacity = 0;
	/** 缺货阈值 */
	private Integer warnCap = 0;
	/** 当前容量 */
	private Integer curCap = 0;
	/** 商品状态 1:可售 2:过期 */
	private String productSate = "1";
	/** 商品过期时间 */
	private String pStateTime = "";
	/** 货道状态 1:正常 2:停用 */
	private String laneSate = "1";
	/** 货道停用时间 */
	private String lSateTime = "";
	/** 托管公司编号 */
	private String corpId = "";
	
	private String pic;
	
	/**货道商品是否允许删除:1允许;2不允许*/
	private String allowDel;
	
	public String getLaneSateName() {
		if(StringUtils.isEmpty(laneSate)) {
			return "";
		}else if(Constant.VENDING_LANESTATE_NORMAL.equals(laneSate)) {
			return "正常";
		}else if(Constant.VENDING_LANESTATE_WAIT_LOCK.equals(laneSate)) {
			return "等待锁定";
		}else if(Constant.VENDING_LANESTATE_LOCKED.equals(laneSate)) {
			return "锁定";
		}else if(Constant.VENDING_LANESTATE_WAIT_UNLOCK.equals(laneSate)) {
			return "等待解锁";
		}else if(Constant.VENDING_LANESTATE_DAMAGE.equals(laneSate)) {
			return "损坏";
		}else {
			return "";
		}

	}
	
	public String getAllowDel() {
		return allowDel;
	}

	public void setAllowDel(String allowDel) {
		this.allowDel = allowDel;
	}
	
	public String getPic() {
		return Constant.PRPDUCTIMGPROFILE+SystemUtil.jsonpicToPic(productPic);
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setSlaneId(String slaneId) 
	{
		this.slaneId = slaneId;
	}

	public String getSlaneId() 
	{
		return slaneId;
	}
	public void setSiteId(String siteId) 
	{
		this.siteId = siteId;
	}

	public String getSiteId() 
	{
		return siteId;
	}
	public void setSiteName(String siteName) 
	{
		this.siteName = siteName;
	}

	public String getSiteName() 
	{
		return siteName;
	}
	public void setLaneSId(Integer laneSId) 
	{
		this.laneSId = laneSId;
	}

	public Integer getLaneSId() 
	{
		return laneSId;
	}
	public void setLaneEId(Integer laneEId) 
	{
		this.laneEId = laneEId;
	}

	public Integer getLaneEId() 
	{
		return laneEId;
	}
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setProductPic(String productPic) 
	{
		this.productPic = productPic;
	}

	public String getProductPic() 
	{
		return productPic;
	}
	public void setSalePrice(Float salePrice) 
	{
		this.salePrice = salePrice;
	}

	public Float getSalePrice() 
	{
		return salePrice;
	}
	public void setCapacity(Integer capacity) 
	{
		this.capacity = capacity;
	}

	public Integer getCapacity() 
	{
		return capacity;
	}
	public void setWarnCap(Integer warnCap) 
	{
		this.warnCap = warnCap;
	}

	public Integer getWarnCap() 
	{
		return warnCap;
	}
	public void setCurCap(Integer curCap) 
	{
		this.curCap = curCap;
	}

	public Integer getCurCap() 
	{
		return curCap;
	}
	public void setProductSate(String productSate) 
	{
		this.productSate = productSate;
	}

	public String getProductSate() 
	{
		return productSate;
	}
	public void setPStateTime(String pStateTime) 
	{
		this.pStateTime = pStateTime;
	}

	public String getPStateTime() 
	{
		return pStateTime;
	}
	public void setLaneSate(String laneSate) 
	{
		this.laneSate = laneSate;
	}

	public String getLaneSate() 
	{
		return laneSate;
	}
	public void setLSateTime(String lSateTime) 
	{
		this.lSateTime = lSateTime;
	}

	public String getLSateTime() 
	{
		return lSateTime;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

	public String getProductName() {
		return SystemUtil.getProductNameById(productId);
	}
	
}
