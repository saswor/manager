package com.manage.project.system.vending.vo;

import java.text.DecimalFormat;
import java.util.Map;

import com.manage.common.utils.CacheUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.base.domain.Dispatch;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;

public class VendingListVo extends Vending {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pointName;	// 点位名称
	private String lineName;	// 线路名称
	private String districtName;	// 区域名称
	private String cabinetTypeName;	// 货柜类型 01:商店机 02:弹簧机 03:格子机
	private String addresses;	// 所属行政区
	private String sellStateName;	// 售卖状态 1:可售卖 2:不可售卖
	private String corpName;	// 商户名称
	private String netSateName;	// 网络状态(0:在线 1:离线)
	private String kuc;	// 库存百分比
	
	private String searchName;	// 搜索字段，售卖机id或名称
	
	private String country;	// 国家
	private String province;	// 省
	private String city;	// 市
	private String district;	// 区
	
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getCabinetTypeName() {
		return SystemUtil.parseCabinetType(super.getCabinetType());
	}
	public void setCabinetTypeName(String cabinetTypeName) {
		this.cabinetTypeName = cabinetTypeName;
	}
	public String getAddresses() {
		Object o = CacheUtils.get(Constant.DISPATCH_CACHE);
		if (o == null) {
			return "";
		}
		Map<String, Dispatch> map = (Map<String, Dispatch>)o;
		
		StringBuffer sb = new StringBuffer();
		Dispatch cou = map.get(country);	// 国家
		if (cou != null) {
			sb.append(cou.getName());
		}
		Dispatch pro = map.get(province);	// 省
		if (pro != null) {
			sb.append(pro.getName());
		}
		Dispatch ci = map.get(city);	// 市
		if (ci != null) {
			sb.append(ci.getName());
		}
		Dispatch dis = map.get(district);	// 区
		if (dis != null) {
			sb.append(dis.getName());
		}
		//sb.append(super.getAddress());
		return sb.toString();
	}
	
	public static void main(String[] args) {
		int a=4;
	    int b=8;
	    DecimalFormat df=new DecimalFormat("0.00");

	    System.out.println(df.format((float)a/b));
	    System.out.println(df.format(a/(float)b));
	    System.out.println(df.format((float)a/(float)b));
	    System.out.println(df.format((float)(a/b)));
	}
	
	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}
	public String getSellStateName() {
		return SystemUtil.parseSellState(super.getSellState());
	}
	public void setSellStateName(String sellStateName) {
		this.sellStateName = sellStateName;
	}
	public String getCorpName() {
		Object o = CacheUtils.get(Constant.CORP_CACHE);
		
		if (o == null) {
			return "";
		}
		Map<String, Corp> map = (Map<String, Corp>)o;		
		Corp cou = map.get(super.getCorpId());
		if (cou != null) {
			return cou.getCorpName();
		}
		return "";
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getNetSateName() {	// 从缓存中查找名称
		return SystemUtil.parseNetSate(super.getNetSate());
	}
	public void setNetSateName(String netSateName) {
		this.netSateName = netSateName;
	}
	public String getKuc() {
		if (super.getPmaxNum() == null || super.getPmaxNum() == 0) {
			return "0";
		}
		if (super.getPcurNum() == null) {
			return "0";
		}
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format((float)super.getPcurNum()/super.getPmaxNum());
	}
	public void setKuc(String kuc) {
		this.kuc = kuc;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
