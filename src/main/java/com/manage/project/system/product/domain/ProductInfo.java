package com.manage.project.system.product.domain;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manage.common.utils.StringUtils;
import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;
import com.manage.project.system.util.SystemUtil;

/**
 * 记录商品表 as_product_info
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Component
public class ProductInfo extends BaseEntity
{
	
	@Autowired
	private ManageConfig manageConfig;
	
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 商品编号 */
	private String productId;
	/** 商品编码 */
	@Excel(name = "商品编码", column = "A")
	private String productCode;
	/** 商品名称 */
	@Excel(name = "商品名称", column = "B")
	private String name;
	/** 商品全名 */
	@Excel(name = "商品全名", column = "C")
	private String fullName;
	/** 分类编号 */
	@Excel(name = "分类编号", column = "D",isExport=false)
	private String typeId;
	/**分类名称*/
	@Excel(name = "分类名称",isImport=false)
	private String typeName;
	/** 零售价 */
	@Excel(name = "零售价", column = "E")
	private Float salePrice;
	/** 过期时间(天) */
	@Excel(name = "过期时间(天)", column = "F")
	private Integer validTime;
	/** 包装类型1:瓶装 2:灌装 3:袋装 4:盒装 5:杯装 */
	@Excel(name = "包装类型", column = "G",isExport=false)
	private String bagType;
	/**包装形式名称*/
	@Excel(name = "包装类型",isImport=false)
	private String bagTypeName;	// 
	/**  产品规格 格式:190g*24瓶/箱 */
	@Excel(name = "净含量", column = "H")
	private String spec;
	/** 生产厂家 */
	@Excel(name = "生产厂家", column = "I")
	private String factoryName;
	/** 图片json格式 */
	@Excel(name = "图片", column = "J",isExport=false)
	private String picJson;
	/** 营养成分json 格式[{“nape”:”能量”,”every”:”每100ML”,”value”:”1mg”},…] */
	private String nutrition;
	/** 描述1 */
	@Excel(name = "描述1", column = "K")
	private String desOne;
	/** 描述2 */
	@Excel(name = "描述2", column = "L")
	private String desTwo;
	/** 描述3(图片地址，上传图片) */
	private String desThree;
	/** 托管公司编号 */
	private String corpId;
	/**公司名称*/
	private String corpName;
	
	private String wpic;	// 微信图片地址
	private String pcpic;	// 终端图片地址
	private String apppic;	// app图片地址
	
	public String getCorpName() {
		return SystemUtil.getCorpNameById(corpId);
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	//管理台图片
	private Map<String,String> managePic;

	public Map<String, String> getManagePic() {
		return managePic;
	}

	public void setManagePic(Map<String, String> managePic) {
		this.managePic = managePic;
	}

	/**
	 * picJson中第一个图片，页面显示使用
	 */
	private String pic;

	public String getTypeName() {
		ProductClassify productClassify = SystemUtil.getProductClassify(typeId);
		if (productClassify == null) {
			return "-";
		}
		return productClassify.getClassifyName();
	}

	public String getBagTypeName() {
		return SystemUtil.parseBagType(bagType);
	}

	public void setBagTypeName(String bagTypeName) {
		this.bagTypeName = bagTypeName;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setProductCode(String productCode) 
	{
		this.productCode = productCode;
	}

	public String getProductCode() 
	{
		return productCode;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setFullName(String fullName) 
	{
		this.fullName = fullName;
	}

	public String getFullName() 
	{
		return fullName;
	}
	public void setTypeId(String typeId) 
	{
		this.typeId = typeId;
	}

	public String getTypeId() 
	{
		return typeId;
	}
	public void setSalePrice(Float salePrice) 
	{
		this.salePrice = salePrice;
	}

	public Float getSalePrice() 
	{
		return salePrice;
	}
	public void setValidTime(Integer validTime) 
	{
		this.validTime = validTime;
	}

	public Integer getValidTime() 
	{
		return validTime;
	}
	public void setBagType(String bagType) 
	{
		this.bagType = bagType;
	}

	public String getBagType() 
	{
		return bagType;
	}
	public void setPicJson(String picJson) 
	{
		this.picJson = picJson;
	}

	public String getPicJson() 
	{
		return picJson;
	}
	public void setFactoryName(String factoryName) 
	{
		this.factoryName = factoryName;
	}

	public String getFactoryName() 
	{
		return factoryName;
	}
	public void setSpec(String spec) 
	{
		this.spec = spec;
	}

	public String getSpec() 
	{
		return spec;
	}
	public void setNutrition(String nutrition) 
	{
		this.nutrition = nutrition;
	}

	public String getNutrition() 
	{
		return nutrition;
	}
	public void setDesOne(String desOne) 
	{
		this.desOne = desOne;
	}

	public String getDesOne() 
	{
		return desOne;
	}
	public void setDesTwo(String desTwo) 
	{
		this.desTwo = desTwo;
	}

	public String getDesTwo() 
	{
		return desTwo;
	}
	public void setDesThree(String desThree) 
	{
		this.desThree = desThree;
	}

	public String getDesThree() 
	{
		return desThree;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

	public String getPic() {
		if(StringUtils.isNotEmpty(pic)) {
			return pic;
		}else {
			return Constant.PRPDUCTIMGPROFILE+SystemUtil.jsonpicToPic(picJson);
		}
	}

	public String getWpic() {
		return wpic;
	}

	public void setWpic(String wpic) {
		this.wpic = wpic;
	}

	public String getPcpic() {
		return pcpic;
	}

	public void setPcpic(String pcpic) {
		this.pcpic = pcpic;
	}

	public String getApppic() {
		return apppic;
	}

	public void setApppic(String apppic) {
		this.apppic = apppic;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("name", getName())
            .append("fullName", getFullName())
            .append("typeId", getTypeId())
            .append("salePrice", getSalePrice())
            .append("validTime", getValidTime())
            .append("bagType", getBagType())
            .append("picJson", getPicJson())
            .append("factoryName", getFactoryName())
            .append("spec", getSpec())
            .append("nutrition", getNutrition())
            .append("desOne", getDesOne())
            .append("desTwo", getDesTwo())
            .append("desThree", getDesThree())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
