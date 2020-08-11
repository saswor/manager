package com.manage.project.system.base.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.manage.project.system.base.domain.Post;
import com.manage.project.system.base.service.IPostService;

/**
 * 岗位信息操作处理
 * 
 */
@Controller
@RequestMapping("/system/post")
public class PostController extends BaseController
{
    @Autowired
    private IPostService postService;

    @PostMapping("/list")
    @ResponseBody
    public AjaxResult list(Post post)
    {
        startPage();
        List<Post> list = postService.selectPostList(post);
        return AjaxResult.success(getDataTable(list));
    }

    @Log(title = "岗位管理", action = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(@RequestBody Post post) throws Exception
    {
        try
        {
            List<Post> list = postService.selectPostList(post);
            ExcelUtil<Post> util = new ExcelUtil<Post>(Post.class);
            return util.exportExcel(list, "post");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    @Log(title = "岗位管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody CommonVo ids)
    {
        try
        {
            return toAjax(postService.deletePostByIds(ids.getIds()));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 新增保存岗位
     */
    @Log(title = "岗位管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody Post post)
    {
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改保存岗位
     */
    @Log(title = "岗位管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody Post post)
    {
        return toAjax(postService.updatePost(post));
    }

    /**
     * 校验岗位名称
     */
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public AjaxResult checkPostNameUnique(@RequestBody Post post)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(post))
        {
            uniqueFlag = postService.checkPostNameUnique(post);
        }
        return AjaxResult.success(uniqueFlag);
    }

    /**
     * 校验岗位编码
     */
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public String checkPostCodeUnique(Post post)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(post))
        {
            uniqueFlag = postService.checkPostCodeUnique(post);
        }
        return uniqueFlag;
    }

}
