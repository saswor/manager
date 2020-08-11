package com.manage.project.system.advert.vo;

/**
 * 优惠对象编辑时查询使用vo
 * @author xufeng
 *
 */
public class FobjEditVo {
	/** 优惠站点编号或商品的编号 */
	private String favObjId = "";
	
	private String favObjName = "";	// 售卖机或商品名称
	
	private String pic = "";	// 图片地址，如果是商品则显示图片

	public String getFavObjId() {
		return favObjId;
	}

	public void setFavObjId(String favObjId) {
		this.favObjId = favObjId;
	}

	public String getFavObjName() {
		return favObjName;
	}

	public void setFavObjName(String favObjName) {
		this.favObjName = favObjName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
}
