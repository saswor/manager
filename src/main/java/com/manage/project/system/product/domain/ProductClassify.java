package com.manage.project.system.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;
import com.manage.project.system.util.SystemUtil;

/**
 * 商品分类表 as_product_classify
 * 
 * @author shicong
 * @date 2018-09-25
 */
@Component
public class ProductClassify extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ManageConfig manageConfig;
	
	/** 记录编号 */
	private String logid;
	/** 公司编号(业主) */
	private String corpId;
	/** 分类编号 */
	private String classifyId;
	/** 分类关键字 */
	private String classifyName;
	/** 分类描述 */
	private String classifyDesc;
	/** 父级分类标识 */
	private String parentId;
	/** 同级排序 */
	private String sortBy;
	/** 图片json格式 */
	private String picJson;
	/** 级别 数字 1递增 */
	private String level;
	/**
	 * picJson中第一个图片，页面显示使用
	 */
	private String pic;

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setClassifyId(String classifyId) 
	{
		this.classifyId = classifyId;
	}

	public String getClassifyId() 
	{
		return classifyId;
	}
	public void setClassifyName(String classifyName) 
	{
		this.classifyName = classifyName;
	}

	public String getClassifyName() 
	{
		return classifyName;
	}
	public void setClassifyDesc(String classifyDesc) 
	{
		this.classifyDesc = classifyDesc;
	}

	public String getClassifyDesc() 
	{
		return classifyDesc;
	}
	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}

	public String getParentId() 
	{
		return parentId;
	}
	public void setSortBy(String sortBy) 
	{
		this.sortBy = sortBy;
	}

	public String getSortBy() 
	{
		return sortBy;
	}
	
	public void setPicJson(String picJson) 
	{
		this.picJson = picJson;
	}

	public String getPicJson() 
	{
		return picJson;
	}
	public void setLevel(String level) 
	{
		this.level = level;
	}

	public String getLevel() 
	{
		return level;
	}
	
	public String getPic() {
		return Constant.PRPDUCTCLASSIFYIMGPROFILE+SystemUtil.jsonpicToPic(picJson);
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("corpId", getCorpId())
            .append("classifyId", getClassifyId())
            .append("classifyName", getClassifyName())
            .append("classifyDesc", getClassifyDesc())
            .append("parentId", getParentId())
            .append("sortBy", getSortBy())
            .append("createTime", getCreateTime())
            .append("picJson", getPicJson())
            .append("level", getLevel())
            .toString();
    }
}
