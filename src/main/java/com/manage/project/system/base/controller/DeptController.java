package com.manage.project.system.base.controller;

import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.StringUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.base.domain.Dept;
import com.manage.project.system.base.service.IDeptService;

/**
 * 部门信息
 * 
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController
{
    @Autowired
    private IDeptService deptService;

    @GetMapping("/list")
    @ResponseBody
    public AjaxResult list(Dept dept)
    {
        List<Dept> deptList = deptService.selectDeptList(dept);
        return AjaxResult.success(deptList);
    }

    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody Dept dept)
    {
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 保存
     */
    @Log(title = "部门管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Dept dept)
    {
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除
     */
    @Log(title = "部门管理", action = BusinessType.DELETE)
    @PostMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") Long deptId)
    {
        if (deptService.selectDeptCount(deptId) > 0)
        {
            return error(1, "存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return error(1, "部门存在用户,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public AjaxResult checkDeptNameUnique(@RequestBody Dept dept)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(dept))
        {
            uniqueFlag = deptService.checkDeptNameUnique(dept);
        }
        return AjaxResult.success(uniqueFlag);
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public AjaxResult treeData()
    {
        List<Map<String, Object>> tree = deptService.selectDeptTree();
        return AjaxResult.success(tree);
    }
}
