package com.manage.project.system.stock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.web.domain.BaseEntity;

/**
 * 商品库存量表 as_stock_product
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class StockProduct extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 商品库存编号 */
	private String pstockId;
	/** 商品编号 */
	@Excel(name="商品编号",column="A")
	private String productId;
	/** 商品名称 */
	@Excel(name="商品名称",column="B")
	private String productName;
	/** 总库存数量 */
	@Excel(name="历史库存总数量",column="C")
	private Integer totalNum;
	/** 当前库存总数量 */
	@Excel(name="当前库存总数量",column="D")
	private Integer curNum;
	/** 过期总数 */
	@Excel(name="过期总数量",column="E")
	private Integer overNum;
	/** 托管公司编号 */
	private String corpId;
	/** 创建时间 */
	@Excel(name="创建时间",column="E")
	private String createTime;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setPstockId(String pstockId) 
	{
		this.pstockId = pstockId;
	}

	public String getPstockId() 
	{
		return pstockId;
	}
	public void setProductId(String productId) 
	{
		this.productId = productId;
	}

	public String getProductId() 
	{
		return productId;
	}
	public void setProductName(String productName) 
	{
		this.productName = productName;
	}

	public String getProductName() 
	{
		return productName;
	}
	public void setTotalNum(Integer totalNum) 
	{
		this.totalNum = totalNum;
	}

	public Integer getTotalNum() 
	{
		return totalNum;
	}
	public void setCurNum(Integer curNum) 
	{
		this.curNum = curNum;
	}

	public Integer getCurNum() 
	{
		return curNum;
	}
	public void setOverNum(Integer overNum) 
	{
		this.overNum = overNum;
	}

	public Integer getOverNum() 
	{
		return overNum;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("pstockId", getPstockId())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("totalNum", getTotalNum())
            .append("curNum", getCurNum())
            .append("overNum", getOverNum())
            .append("corpId", getCorpId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
