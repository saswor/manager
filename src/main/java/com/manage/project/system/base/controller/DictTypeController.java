package com.manage.project.system.base.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.DictType;
import com.manage.project.system.base.service.IDictTypeService;
import com.manage.project.system.util.SystemUtil;

/**
 * 数据字典信息
 * 
 */
@Controller
@RequestMapping("/system/dict")
public class DictTypeController extends BaseController
{

    @Autowired
    private IDictTypeService dictTypeService;

    @GetMapping("/list")
    @ResponseBody
    public AjaxResult list(DictType dictType)
    {
        startPage();
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        return AjaxResult.success(getDataTable(list));
    }

    @Log(title = "字典类型", action = BusinessType.EXPORT)
    @GetMapping("/export")
    @ResponseBody
    public AjaxResult export(DictType dictType) throws Exception
    {
        try
        {
            List<DictType> list = dictTypeService.selectDictTypeList(dictType);
            ExcelUtil<DictType> util = new ExcelUtil<DictType>(DictType.class);
            return util.exportExcel(list, "dictType");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    /**
     * 新增保存字典类型
     */
    @Log(title = "字典类型", action = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody DictType dict)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("您没有权限进行此操作");
    	}
    	//校验字典名称
    	if (StringUtils.isEmpty(dict.getDictName())) {
			return error("字典名称不允许为空");
		}
    	if (dict.getDictName().length()>100) {
    		return error("字典名称输入字段过长");
		}
    	//校验字典名称为中文、英文、数字
    	if (!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", dict.getDictName())) {
    		return error("字典名称含有非法字符");
		}
    	//校验字典类型
    	if (StringUtils.isEmpty(dict.getDictType())) {
			return error("字典类型不允许为空");
		}
    	if (dict.getDictType().length()>100) {
    		return error("字典类型输入字段过长");
		}
    	//校验字典类型为中文、英文、数字包括下划线
    	if (!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9_]+$", dict.getDictType())) {
    		return error("字典类型含有非法字符");
		}
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改保存字典类型
     */
    @Log(title = "字典类型", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody DictType dict)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("您没有权限进行此操作");
    	}
    	//校验字典名称
    	if (StringUtils.isEmpty(dict.getDictName())) {
			return error("字典名称不允许为空");
		}
    	//校验字典名称为中文、英文、数字
    	if (!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", dict.getDictName())) {
    		return error("字典名称含有非法字符");
		}
    	//校验字典类型
    	if (StringUtils.isEmpty(dict.getDictType())) {
			return error("字典类型不允许为空");
		}
    	//校验字典类型为中文、英文、数字包括下划线
    	if (!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9_]+$", dict.getDictType())) {
    		return error("字典类型含有非法字符");
		}
        return toAjax(dictTypeService.updateDictType(dict));
    }

    @Log(title = "字典类型", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody CommonVo ids)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("您没有权限进行此操作");
    	}
        try
        {
            return toAjax(dictTypeService.deleteDictTypeByIds(ids.getIds()));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 查询字典详细
     */
    @GetMapping("/detail")
    @ResponseBody
    public AjaxResult detail(Long dictId, ModelMap mmap)
    {
    	DictType type = dictTypeService.selectDictTypeById(dictId);
        return AjaxResult.success(type);
    }

    /**
     * 校验字典类型
     */
    @GetMapping("/checkDictTypeUnique")
    @ResponseBody
    public AjaxResult checkDictTypeUnique(DictType dictType)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(dictType))
        {
            uniqueFlag = dictTypeService.checkDictTypeUnique(dictType);
        }
        return AjaxResult.success(uniqueFlag);
    }
}
