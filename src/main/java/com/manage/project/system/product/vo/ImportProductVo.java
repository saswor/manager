package com.manage.project.system.product.vo;

import java.io.Serializable;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * 导入商品vo
 * 
 * @author xufeng
 * @date 2018年10月17日
 * 
 */
public class ImportProductVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**商品编码*/
	@Excel(name="商品编码",column="B")
	private String productCode;
	/**商品全名*/
	@Excel(name="商品全名",column="C")
	private String fullName;
	/**商品名称*/
	@Excel(name="商品名称",column="D")
	private String name;
	/**零售价*/
	@Excel(name="零售价",column="E")
	private Float salePrice;
	/** 包装类型1:瓶装 2:灌装 3:袋装 4:盒装 5:杯装 */
	@Excel(name = "包装类型", column = "F")
	private String bagType;
	/**  净含量,产品规格 格式:190g*24瓶/箱 */
	@Excel(name = "净含量", column = "G")
	private String spec;
	/** 厂家 */
	@Excel(name = "厂家", column = "H")
	private String factoryName;
	/** 营养成分json 格式[{“nape”:”能量”,”every”:”每100ML”,”value”:”1mg”},…] */
	@Excel(name = "营养成分", column = "I")
	private String nutrition;
	/** 过期时间 */
	@Excel(name = "有效天数", column = "J")
	private Integer validTime;
	/** 描述1 */
	@Excel(name = "描述1", column = "K")
	private String desOne;
	/** 描述2 */
	@Excel(name = "描述2", column = "L")
	private String desTwo;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}
	public String getBagType() {
		return bagType;
	}
	public void setBagType(String bagType) {
		this.bagType = bagType;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getNutrition() {
		return nutrition;
	}
	public void setNutrition(String nutrition) {
		this.nutrition = nutrition;
	}
	public Integer getValidTime() {
		return validTime;
	}
	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}
	public String getDesOne() {
		return desOne;
	}
	public void setDesOne(String desOne) {
		this.desOne = desOne;
	}
	public String getDesTwo() {
		return desTwo;
	}
	public void setDesTwo(String desTwo) {
		this.desTwo = desTwo;
	}
}
