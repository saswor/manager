package com.manage.project.system.supply.vo;

/**
 * @ClassName WarnLineDetailVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00:00$ 2018-10-09 $
 **/
public class SupplyProductAddVo {
  private String productId;
  private String productName;
  private String supplyNum;
  private int laneSId;
  private int laneEId;

    public int getLaneSId() {
        return laneSId;
    }

    public void setLaneSId(int laneSId) {
        this.laneSId = laneSId;
    }

    public int getLaneEId() {
        return laneEId;
    }

    public void setLaneEId(int laneEId) {
        this.laneEId = laneEId;
    }

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
}
