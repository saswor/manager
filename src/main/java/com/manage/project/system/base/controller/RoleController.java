package com.manage.project.system.base.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.Role;
import com.manage.project.system.base.service.IRoleService;
import com.manage.project.system.base.service.IUserService;
import com.manage.project.system.util.SystemUtil;

/**
 * 角色信息
 * 
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController
{
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    @ResponseBody
    public AjaxResult list(Role role)
    {
        startPage();
        List<Role> list = roleService.selectRoleList(role);
        
        // 如果不是超级管理员,则不返回超级管理员角色
        boolean isSuper = false;	// 是否超级管理员,默认不是
        List<Long> roleIds = userService.selectRolesByUserId(ShiroUtils.getUserId());
        for (Long roleId : roleIds) {
    		Role r = roleService.selectRoleById(roleId);
    		if (r.getRoleKey().equals("super")) {
    			isSuper = true;
    		}
    	}
        if(!isSuper) {
        	//不显示超级管理员
            for (Role r1 : list) {
            	// 如果不是超级管理员,则从结果中删除超级管理员
            	if (r1.getRoleKey().equals("super")) {
            		list.remove(r1);
            		break;
            	}
            }
        }
        
        
        return AjaxResult.success(getDataTable(list));
    }
    
    /**
     * 根据角色返回该角色原有打勾的菜单id.
     * @param roleId
     * @return
     */
    @GetMapping("/getYuanMenuId")
    @ResponseBody
    public AjaxResult getYuanMenuId(Long roleId) {
    	Role role = roleService.selectRoleById(roleId);
    	String vmenuids = role.getVmenuids();	// remark存的menuid,以逗号分割
    	Long[] ml = null;
    	if (!StringUtils.isEmpty(vmenuids)) {
    		String[] menuIds = vmenuids.split(",");   
        	if (menuIds != null && menuIds.length > 0) {
        		ml = new Long[menuIds.length];
        		for (int i = 0; i < menuIds.length; i++) {
        			try {
        				ml[i] = Long.valueOf(menuIds[i]);
        			} catch(Exception e) {
        				ml = new Long[menuIds.length];
        				break;
        			}
        		}
        	}
    	}
    	
    	return AjaxResult.success(ml);
    }

    @Log(title = "角色管理", action = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(@RequestBody Role role) throws Exception
    {
        try
        {
            List<Role> list = roleService.selectRoleList(role);
            ExcelUtil<Role> util = new ExcelUtil<Role>(Role.class);
            return util.exportExcel(list, "role");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    /**
     * 新增保存角色
     */
    @Log(title = "角色管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(@RequestBody Role role)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("只有超级管理员有此权限");
    	}
    	//校验角色名称
    	if (StringUtils.isEmpty(role.getRoleName())) {
    		return error("角色名称不允许为空");
		}
    	if (role.getRoleName().length()>30) {
    		return error("角色名称输入字段过长");
		}
    	//校验角色名称是否中文、英文、数字组成
    	if(!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", role.getRoleName())) {
    	     return error("角色名称只能是中文、英文、数字组成");
    	}
    	Role roleName = roleService.selectRoleByRoleName(role.getRoleName());
    	if (StringUtils.isNotNull(roleName)) {
    		 return error("角色名称已存在");
		}
    	
    	//校验权限字符
    	if (StringUtils.isEmpty(role.getRoleKey())) {
    		return error("权限字符不允许为空");
		}
    	if (role.getRoleKey().length()>100) {
    		return error("权限字符输入字段过长");
		}
    	//校验权限字符是否中文、英文、数字组成
    	if(!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", role.getRoleKey())) {
    	     return error("权限字符只能是中文、英文、数字组成");
    	}
    	Role roleKey = roleService.selectRoleByRoleKey(role.getRoleKey());
    	if (StringUtils.isNotNull(roleKey)) {
   		 return error("权限字符已存在");
		}
    	
    	//校验显示顺序
    	if (StringUtils.isEmpty(role.getRoleSort())) {
    		return error("显示顺序不允许为空");
		}
    	if (role.getRoleSort().length()>4) {
    		return error("显示顺序输入字段过长");
		}
    	//校验显示顺序是否是数字
    	if(!Pattern.matches("^[0-9]*$", role.getRoleSort())) {
    	     return error("显示顺序只能是数字");
    	}
        return toAjax(roleService.insertRole(role));
  }

    /**
     * 修改保存角色
     */
    @Log(title = "角色管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(@RequestBody Role role)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("只有超级管理员有此权限");
    	}
    	//校验角色名称
    	if (StringUtils.isEmpty(role.getRoleName())) {
    		return error("角色名称不允许为空");
		}
    	if (role.getRoleName().length()>30) {
    		return error("角色名称输入字段过长");
		}
    	//校验角色名称是否中文、英文、数字组成
    	if(!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", role.getRoleName())) {
    	     return error("角色名称只能是中文、英文、数字组成");
    	}
    	
    	//校验权限字符
    	if (StringUtils.isEmpty(role.getRoleKey())) {
    		return error("权限字符不允许为空");
		}
    	if (role.getRoleKey().length()>100) {
    		return error("权限字符输入字段过长");
		}
    	//校验权限字符是否中文、英文、数字组成
    	if(!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", role.getRoleKey())) {
    	     return error("权限字符只能是中文、英文、数字组成");
    	}
    	
    	//校验显示顺序
    	if (StringUtils.isEmpty(role.getRoleSort())) {
    		return error("显示顺序不允许为空");
		}
    	if (role.getRoleSort().length()>4) {
    		return error("显示顺序输入字段过长");
		}
    	//校验显示顺序是否是数字
    	if(!Pattern.matches("^[0-9]*$", role.getRoleSort())) {
    	     return error("显示顺序只能是数字");
    	}
        return toAjax(roleService.updateRole(role));
    }

    @Log(title = "角色管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody CommonVo ids)
    {
    	if(!SystemUtil.isZhilai()) {
    		return AjaxResult.error("只有超级管理员有此权限");
    	}
        try
        {
            return toAjax(roleService.deleteRoleByIds(ids.getIds()));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public AjaxResult checkRoleNameUnique(@RequestBody Role role)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(role))
        {
            uniqueFlag = roleService.checkRoleNameUnique(role);
        }
        return AjaxResult.success(uniqueFlag);
    }
    
    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public AjaxResult checkRoleKeyUnique(@RequestBody Role role)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(role))
        {
            uniqueFlag = roleService.checkRoleKeyUnique(role);
        }
        return AjaxResult.success(uniqueFlag);
    }
}