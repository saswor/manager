package com.manage.project.system.base.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.base.domain.Role;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.base.service.IRoleService;
import com.manage.project.system.base.service.IUserService;
import com.manage.project.system.util.SystemUtil;

/**
 * 用户信息
 * 
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController
{
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IRoleService roleService;
    
    private Logger log = LoggerFactory.getLogger(UserController.class);
    
    @RequiresUser
    @GetMapping("/list")
    @ResponseBody
    public AjaxResult list(User user)
    {
        startPage();
        if (SystemUtil.isZhilai()) {
        	user.setCorpId("");
		} else {
			user.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<User> list = userService.selectUserList(user);
        return AjaxResult.success(getDataTable(list));
    }
    
    /**
     * 获取一个用户信息
     * @param userId
     * @return
     */
    @GetMapping("/selectByUserId")
    @ResponseBody
    public AjaxResult selectByUserId(Long userId)
    {
        User user = userService.selectByUserId(userId);
        List<Long> roleIds = userService.selectRolesByUserId(userId);
        if (roleIds != null && !roleIds.isEmpty()) {
        	Long[] s = new Long[roleIds.size()];
            roleIds.toArray(s);
            
            if (s != null) {        	
            	user.setRoleIds(s[0]);
            }
        }
        
        return AjaxResult.success(user);
    }
    
    @GetMapping("/getUserByName")
    @ResponseBody
    public AjaxResult getUserByName(String userName,String roleId)
    {
    	String corpId = SystemUtil.getCorpId();
        List<User> list = userService.selectUserByUserName(userName, corpId, roleId);
        return AjaxResult.success(list);
    }

    @Log(title = "用户管理", action = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(@RequestBody User user) throws Exception
    {
        try
        {
            List<User> list = userService.selectUserList(user);
            ExcelUtil<User> util = new ExcelUtil<User>(User.class);
            return util.exportExcel(list, "user");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    /**
     * 新增保存用户
     */
    @Log(title = "用户管理", action = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(@RequestBody User user)
    {   
    	if (StringUtils.isEmpty(user.getLoginName())) {
    		return error("登录账号不允许为空");
    	}
    	if(!Pattern.matches("[A-Za-z0-9]+", user.getLoginName())) {
    		return error("登录账号只能由字母和数字组成"); 
    	}
    	/*if(user.getPassword().length()<6) {
    		return error("密码不能小于6位");
    	}*/
    	if(StringUtils.isEmpty(user.getUserName())){
			return error("用户名称不能为空");
		}
		if(user.getUserName().length() > 30){
			return AjaxResult.error("用户名称输入字段过长");
		}
		//校验用户名称是否中文、英文、数字组成
		if(!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", user.getUserName())) {
		return AjaxResult.error("用户名称只能是中文、英文、数字组成");
		}
    	if (StringUtils.isEmpty(user.getPassword())) {
    		return error("密码不允许为空");
    	}
    	//校验密码必须密码必须由6-12位的字母和数字组成
    	if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,12}$", user.getPassword())) {
    	return AjaxResult.error("密码必须由6-12位的字母和数字组成");
    	}
    	if (StringUtils.isEmpty(user.getPhonenumber())) {
    		return error("手机号不允许为空");
    	}
    	if(user.getPhonenumber().length()!=11) {
    		return error("手机号位数错误");
    	}
    	//校验手机号是数字类型
    	if(!Pattern.matches("^[0-9]*$", user.getPhonenumber())) {
    	     return error("手机号电话只能是数字类型");
    	}
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId()))
        {
            return error("不允许修改超级管理员用户");
        }
        
        List<User> userList = userService.selectUserByPhoneNumber(user.getPhonenumber());
        if (userList != null&&userList.size()!=0) {
        	return error("新增失败，手机号已被使用");
        }
        User u1 = userService.selectUserByLoginName(user.getLoginName());
        if (u1 != null) {
        	return error("新增失败，登录名已被使用");
        }
        user.setAvatar("front/img/user/user.png");
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改保存用户
     */
    @Log(title = "用户管理", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(@RequestBody User user)
    {
    	if(user.getUserName().length() > 30){
			return AjaxResult.error("用户名称输入字段过长");
		}
		//校验用户名称是否中文、英文、数字组成
		if(!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", user.getUserName())) {
		return AjaxResult.error("用户名称只能是中文、英文、数字组成");
		}
    	if (StringUtils.isEmpty(user.getPhonenumber())) {
    		return error("手机号不允许为空");
    	}
    	if(user.getPhonenumber().length()!=11) {
    		return error("手机号位数错误");
    	}
    	//校验手机号是数字类型
    	if(!Pattern.matches("^[0-9]*$", user.getPhonenumber())) {
    	     return error("手机号电话只能是数字类型");
    	}
    	User loginNameExistUser = userService.selectUserByLoginName(user.getLoginName());
        if (loginNameExistUser != null&&loginNameExistUser.getUserId()!=user.getUserId()) {
        	return error("新增失败，登录名已被使用");
        }
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId()))
        {
            return error("不允许修改超级管理员用户");
        }
        User u1 = new User();
        u1.setPhonenumber(user.getPhonenumber());
        List<User> u1List = userService.selectUserList(u1);
        for (User a : u1List) {
        	if (a.getUserId().longValue() != user.getUserId().longValue()) {
        		return error("手机号已被使用");
        	}
        }
        
        //不允许修改密码
        user.setPassword(null);
        return toAjax(userService.updateUser(user));
    }

    @Log(title = "重置密码", action = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(@RequestBody User user)
    {
    	if(user.getPassword().length()<5) {
    		return error("密码不能小于5位");
    	}
    	User updateUser = userService.selectByUserId(user.getUserId());
        /*User updateUser = ShiroUtils.getUser();*/
    	//校验密码必须密码必须由6-12位的字母和数字组成
    	if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,12}$", user.getPassword())) {
    	return AjaxResult.error("密码必须由6-12位的字母和数字组成");
    	}
        updateUser.setPassword(user.getPassword());
        return toAjax(userService.resetUserPwd(updateUser));
    }

    @Log(title = "用户管理", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody CommonVo ids)
    {
        try
        {
            return toAjax(userService.deleteUserByIds(ids.getIds()));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public AjaxResult checkLoginNameUnique(@RequestBody User user)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(user))
        {
            uniqueFlag = userService.checkLoginNameUnique(user.getLoginName());
        }
        return AjaxResult.success(uniqueFlag);
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(User user)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(user))
        {
            uniqueFlag = userService.checkPhoneUnique(user);
        }
        return uniqueFlag;
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(User user)
    {
        String uniqueFlag = "0";
        if (StringUtils.isNotNull(user))
        {
            uniqueFlag = userService.checkEmailUnique(user);
        }
        return uniqueFlag;
    }
    
    /**
     * 根据用户id获取角色
     * @param userId
     * @return
     */
    @GetMapping("/selectRolesByUserId")
    @ResponseBody
    public AjaxResult selectRolesByUserId(Long userId) {
    	if (userId == null) {
    		return AjaxResult.error("userid is null.");
    	}
    	List<Long> roleIds = userService.selectRolesByUserId(userId);
    	return AjaxResult.success(roleIds);
    }
    
    
    @GetMapping("/getCurrUser")
    @ResponseBody
    public AjaxResult getCurrUser()
    {
    	User user = ShiroUtils.getUser();
    	List<Long> roleIds = userService.selectRolesByUserId(user.getUserId());
    	if (roleIds != null && !roleIds.isEmpty()) {
    		Long[] array = new Long[roleIds.size()];
        	user.setRoleIds(roleIds.toArray(array)[0]);
    	}
    	
//    	List<String> perms = userService.selectPermsByUserId(user.getUserId());
    	List<String> perms = userService.selectPermsByRoleId(user.getRoleIds());
//    	for (Iterator<String> it = perms.iterator(); it.hasNext();) {
//    		String str = it.next();
//    		if (str.startsWith("sys:base:role")){
//    			it.remove();
//    		}
//    	}
    	
    	// 如果不是超级管理员，移除角色管理菜单,测试要求
    	for (Long roleId : roleIds) {
    		Role role = roleService.selectRoleById(roleId);
    		if (role.getRoleKey().equals("super")) {
    			user.setIsSuper(1);
    		}
    	}
    	
    	
//    	List<String> list = new ArrayList<String>();
//    	list.add("sys:index");	// 首页
//    	list.add("sys:point");// 点位管理
//    	list.add("sys:point:district");// 点位管理-区域管理
//    	list.add("sys:point:district:add");// 点位管理-区域管理-增加按钮
//    	list.add("sys:point:district:del");// 点位管理-区域管理-删除按钮
//    	list.add("sys:point:district:edit");// 点位管理-区域管理-编辑按钮
    	user.setResources(perms);
        return AjaxResult.success(user);
    }
    
    /**
	 * 导出用户
	 */
	@Log(title = "导出用户", action = BusinessType.EXPORT)
	@PostMapping( "/exportExcel")
	@ResponseBody
	public AjaxResult exportExcel(@RequestBody User user)
	{	
		if (SystemUtil.isZhilai()) {
			user.setCorpId("");
		} else {
			user.setCorpId(ShiroUtils.getCorpId());
		}
		try {
			List<User> list = userService.selectUserList(user);
			for (User user2 : list) {
				user2.setStatusName(user2.getStatusName());
			}
	        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
	        return util.exportExcel(list, "用户信息");
		}catch (Exception e) {
			log.error("导出用户信息失败:",e);
			return AjaxResult.error("导出用户信息失败,请联系管理员");
		}
	}
}