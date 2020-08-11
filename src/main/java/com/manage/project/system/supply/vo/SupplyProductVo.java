package com.manage.project.system.supply.vo;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * @ClassName WarnLineDetailVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00:00$ 2018-10-09 $
 **/
public class SupplyProductVo {
	
	@Excel(name="商品编号",column="A")
	private String productId;
	@Excel(name="商品名称",column="B")
	private String productName;
	@Excel(name="缺货数量",column="C")
	private String supplyNum;
	private Integer outNum;
	private Float buyPrice;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(String supplyNum) {
        this.supplyNum = supplyNum;
    }

	public Integer getOutNum() {
		return outNum;
	}

	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}

	public Float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Float buyPrice) {
		this.buyPrice = buyPrice;
	}
    
}
