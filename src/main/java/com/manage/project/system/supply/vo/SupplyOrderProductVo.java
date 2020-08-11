package com.manage.project.system.supply.vo;

import com.manage.framework.aspectj.lang.annotation.Excel;

/**
 * @ClassName SupplyOrderProductVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00$ 2018-10-12$
 **/
public class SupplyOrderProductVo {
	@Excel(name="商品编号")
    private String productId;
	@Excel(name="商品名称")
    private String productName;
	@Excel(name="缺货数量")
    private Integer supplyNum;
	@Excel(name="已补数量")
    private Integer rSupplyNum;

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

    public Integer getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(Integer supplyNum) {
        this.supplyNum = supplyNum;
    }

    public Integer getrSupplyNum() {
        return rSupplyNum;
    }

    public void setrSupplyNum(Integer rSupplyNum) {
        this.rSupplyNum = rSupplyNum;
    }
}
