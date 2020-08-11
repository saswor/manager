package com.manage.project.system.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.manage.project.system.base.domain.Menu;
import com.manage.project.system.base.service.IMenuService;
import com.manage.project.system.base.vo.MenuVo;
import com.manage.project.system.base.domain.Role;

/**
 * 菜单信息
 * 
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController
{

    @Autowired
    private IMenuService menuService;

    @GetMapping("/list")
    @ResponseBody
    public AjaxResult list(Menu menu)
    {
        List<Menu> menuList = menuService.selectMenuList(menu);
        return AjaxResult.success(menuList);
    }

    /**
     * 删除菜单
     */
    @Log(title = "菜单管理", action = BusinessType.DELETE)
    @GetMapping("/remove/{menuId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("menuId") Long menuId)
    {
        if (menuService.selectCountMenuByParentId(menuId) > 0)
        {
            return error(1, "存在子菜单,不允许删除");
        }
        if (menuService.selectCountRoleMenuByMenuId(menuId) > 0)
        {
            return error(1, "菜单已分配,不允许删除");
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }

    /**
     * 新增保存菜单
     */
    @Log(title = "菜单管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody Menu menu)
    {
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改保存菜单
     */
    @Log(title = "菜单管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody Menu menu)
    {
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 校验菜单名称
     */
    @GetMapping("/checkMenuNameUnique")
    @ResponseBody
    public AjaxResult checkMenuNameUnique(Menu menu)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(menu))
        {
            uniqueFlag = menuService.checkMenuNameUnique(menu);
        }
        return AjaxResult.success(uniqueFlag);
    }

    /**
     * 加载角色菜单列表树
     */
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public AjaxResult roleMenuTreeData(Role role)
    {
        List<Map<String, Object>> tree = menuService.roleMenuTreeData(role);
        return AjaxResult.success(tree);
    }

    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTreeData")
    @ResponseBody
    public AjaxResult menuTreeData(Role role)
    {
        List<Map<String, Object>> tree = menuService.menuTreeData();
        return AjaxResult.success(tree);
    }
    
    /**
     * 查询所有菜单，并组装成前端要的结构
     * @return
     */
    @GetMapping("/getAllMenu")
    @ResponseBody
    public AjaxResult getAllMenu() {
    	List<Menu> menus = menuService.selectMenuAll();
    	if (null != menus && !menus.isEmpty()) {
    		List<MenuVo> ms = new ArrayList<MenuVo>();
    		for (Menu menu : menus) {
    			MenuVo vo = new MenuVo();
    			vo.setId(menu.getMenuId());
    			vo.setLabel(menu.getMenuName());
    			vo.setParentId(menu.getParentId());
                ms.add(vo);
            }
    		return AjaxResult.success(createTreeMenus(ms));
    	} else {
    		return AjaxResult.success();
    	}
    }
    
    public static List<MenuVo> createTreeMenus(List<MenuVo> menus) {
        List<MenuVo> treeMenus = null;
        if (null != menus && !menus.isEmpty()) {
            // 创建根节点
            MenuVo root = new MenuVo();
            root.setLabel("菜单根目录");

            // 组装Map数据
            Map<Long, MenuVo> dataMap = new HashMap<Long, MenuVo>();
            for (MenuVo menu : menus) {
                dataMap.put(menu.getId(), menu);
            }

            // 组装树形结构
            Set<Entry<Long, MenuVo>> entrySet = dataMap.entrySet();
            for (Entry<Long, MenuVo> entry : entrySet) {
                MenuVo menu = entry.getValue();
                if (null == menu.getParentId() || 0 == menu.getParentId()) {
                    root.getChildren().add(menu);
                } else {
                    dataMap.get(menu.getParentId()).getChildren().add(menu);
                }
            }

            // 对树形结构进行二叉树排序
            treeMenus = root.getChildren();
        }
        return treeMenus;
    }
    
    @GetMapping("/selectMenuIdByRoleId")
    @ResponseBody
    public AjaxResult selectMenuIdByRoleId(Long[] roleIds) {
    	List<Long> menus = menuService.selectMenuIdByRoleId(roleIds);
    	return AjaxResult.success(menus);
    }
    
}