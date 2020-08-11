package com.manage.project.system.supply.vo;

/**
 * @ClassName SupplyOrderListVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 17:00$ 2018-10-11$
 **/
public class SupplyOrderListVo {
	private static final long serialVersionUID = 1L;
	private String vorderId;
	private String sOrderId;
    private String lineId;
    private String lineName;
    private String wmId;
    private String wmName;
    private String supplierName;
    private String curState;
    private String stockState;
    private Integer supplyNum;
    private Integer num;
    private String createTime;
    /**补货状态名称*/
    private String curStateName;
    /**库存状态名称*/
    private String stockStateName;
    /**补货编号(显示用)*/
    public String getsOrderIdShow() {
		return sOrderId.split("_")[0];
	}

	public String getVorderId() {
		return vorderId;
	}

	public void setVorderId(String vorderId) {
		this.vorderId = vorderId;
	}

	public String getCurStateName() {
		return curStateName;
	}

	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}

	public String getStockStateName() {
		return stockStateName;
	}

	public void setStockStateName(String stockStateName) {
		this.stockStateName = stockStateName;
	}

	public String getsOrderId() {
		return sOrderId;
	}

	public void setsOrderId(String sOrderId) {
		this.sOrderId = sOrderId;
	}

	public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getWmId() {
        return wmId;
    }

    public void setWmId(String wmId) {
        this.wmId = wmId;
    }

    public String getWmName() {
        return wmName;
    }

    public void setWmName(String wmName) {
        this.wmName = wmName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCurState() {
        return curState;
    }

    public void setCurState(String curState) {
        this.curState = curState;
    }

    public String getStockState() {
        return stockState;
    }

    public void setStockState(String stockState) {
        this.stockState = stockState;
    }

    public Integer getSupplyNum() {
        return supplyNum;
    }

//    public void setSupplyNum(int supplyNum) {
//        this.supplyNum = supplyNum;
//    }

    public void setSupplyNum(Integer supplyNum) {
        this.supplyNum = supplyNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
