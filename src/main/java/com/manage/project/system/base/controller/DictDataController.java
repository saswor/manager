package com.manage.project.system.base.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.DictData;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.base.service.IDictDataService;
import com.manage.project.system.util.SystemUtil;

/**
 * 数据字典信息
 * 
 */
@Controller
@RequestMapping("/system/dict/data")
public class DictDataController extends BaseController
{
    @Autowired
    private IDictDataService dictDataService;
    
    private Logger log = LoggerFactory.getLogger(DictDataController.class);

    @GetMapping("/list")
    @ResponseBody
    public AjaxResult list(DictData dictData)
    {
        startPage();
        List<DictData> list = dictDataService.selectDictDataList(dictData);
        for (DictData dict : list) {
        	User createUser = SystemUtil.getUserByLoginName(dict.getCreateBy());
        	if(createUser!=null) {
        		dict.setCreateByName(createUser.getUserName());
        	}
        	User updateUser = SystemUtil.getUserByLoginName(dict.getUpdateBy());
        	if(updateUser!=null) {
        		dict.setUpdateByName(updateUser.getUserName());
        	}
        	
		}
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 获取厂商列表
     * @param dictData
     * @return
     */
    @GetMapping("/findFactoryList")
    @ResponseBody
    public AjaxResult findFactoryList()
    {
    	DictData dictData = new DictData();
    	dictData.setDictType("sys_factory");
        List<DictData> list = dictDataService.selectDictDataList(dictData);
        return AjaxResult.success(list);
    }
    
    
    @Log(title = "字典数据", action = BusinessType.EXPORT)
    @GetMapping("/export")
    @ResponseBody
    public AjaxResult export(DictData dictData) throws Exception
    {
        try
        {
            List<DictData> list = dictDataService.selectDictDataList(dictData);
            ExcelUtil<DictData> util = new ExcelUtil<DictData>(DictData.class);
            return util.exportExcel(list, "dictData");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    /**
     * 新增保存字典类型
     */
    @Log(title = "字典数据", action = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody DictData dict)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("您没有权限进行此操作");
    	}
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @Log(title = "字典数据", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody DictData dict)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("您没有权限进行此操作");
    	}
        return toAjax(dictDataService.updateDictData(dict));
    }

    @Log(title = "字典数据", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody CommonVo ids)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("您没有权限进行此操作");
    	}
        return toAjax(dictDataService.deleteDictDataByIds(ids.getIds()));
    }
    
    /**
     * 修改说明书
     */
    @Log(title = "字典数据", action = BusinessType.UPDATE)
    @RequestMapping("/upload")
    @ResponseBody
    public AjaxResult upload(@RequestParam(value = "file", required = true) MultipartFile file,DictData dict)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("您没有权限进行此操作");
    	}
    	//替换原来的文件
    	String originalFilename = file.getOriginalFilename();
    	int index = originalFilename.lastIndexOf(".");
    	String name = originalFilename.substring(0, index);
    	String ext = originalFilename.substring(index);
    	String path="file/instruction/"+name+"_"+DateUtils.dateTimeNow("yyyyMMdd_hhmmss")+ext;
    	File dest = new File(ManageConfig.getUploadPrefix()+path);
    	try {
    		File dir = dest.getParentFile();
    		if(!dir.exists()) {
    			dir.mkdirs();
    		}
    		if(dest.exists()) {
    			dest.delete();
    		}
			file.transferTo(dest);
			dict.setDictValue(path);
			return toAjax(dictDataService.updateDictData(dict));
		} catch (Exception e) {
			log.error("更新说明书失败,时间:"+DateUtils.getTime(),e);
			return AjaxResult.error("更新说明书失败");
		}
    }
}
