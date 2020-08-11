package com.manage.project.system.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.ServletUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.base.service.IUserService;
import com.manage.project.system.base.vo.LoginParamVo;
import com.manage.project.system.base.vo.LoginReturnVo;

/**
 * 登录/注销
 * 
 */
@Controller
@RequestMapping("/system")
public class LoginController extends BaseController
{
	@Autowired
	private IUserService userService;
	
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(@RequestBody LoginParamVo vo)
    {
    	String username = vo.getUsername();
    	String password = vo.getPassword();
    	
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, false);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            LoginReturnVo loginReturnVo = new LoginReturnVo();
            loginReturnVo.setToken(com.manage.common.utils.security.ShiroUtils.getSessionId());
            loginReturnVo.setName(ShiroUtils.getUser().getUserName());
            loginReturnVo.setAvatar(ShiroUtils.getUser().getAvatar());
            loginReturnVo.setIntroduction(ShiroUtils.getUser().getRemark());
            loginReturnVo.setReTCode("0000");
            List<String> roles = new ArrayList<String>();
            roles.add("admin");
            loginReturnVo.setRoles(roles);
            return AjaxResult.success(loginReturnVo);
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return AjaxResult.error(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "/error/unauth";
    }
    
    /**
     * 注销
     * @return
     */
    @PostMapping("/ajaxLogout")
    @ResponseBody
    public AjaxResult ajaxLogout()
    {
    	ShiroUtils.logout();
        return super.success();
    }
}
