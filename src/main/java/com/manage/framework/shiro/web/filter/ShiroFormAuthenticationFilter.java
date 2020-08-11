package com.manage.framework.shiro.web.filter;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.alibaba.fastjson.JSON;
import com.manage.framework.web.domain.AjaxResult;

public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {
	
	public ShiroFormAuthenticationFilter(String loginUrl) {
		super();
		this.setLoginUrl(loginUrl);		
	}
	
	@Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse response) throws Exception {
		HttpServletRequest request = (HttpServletRequest)req;
		String uri = request.getRequestURI();
		if (uri.equals("/")) {
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect("/index.html");
			return true;
		}
		
        if(this.isLoginRequest(request, response)) {
            if(this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                return true;
            }
        } else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");  
                PrintWriter out = response.getWriter();
                
                AjaxResult result = new AjaxResult();
        		Map<String, String> m = new HashMap<String, String>();
                m.put("retMsg", "您已超过预定时间,为了您的账户安全,请重新登陆");
                m.put("reTCode", "408");
                result.put("zhead", m);
                out.print(JSON.toJSONString(result));
                
                out.flush();
                out.close();
            return false;
        }
    }
}
