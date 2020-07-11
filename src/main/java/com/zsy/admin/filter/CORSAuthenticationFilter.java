package com.zsy.admin.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zsy.admin.domian.dto.Result;
import com.zsy.admin.exception.LoginException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: admin
 * @description:
 * @author: 张思远
 * @create: 2020-05-07 11:15
 */
public class CORSAuthenticationFilter extends FormAuthenticationFilter {
    private static final String REQUET_TYPE = "OPTIONS";


    public CORSAuthenticationFilter() {
        super();
    }


    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals(REQUET_TYPE)) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Subject subject = getSubject(request, response);

        Result result=new Result();
        result.setData(null);
        result.setCode(2000);
        result.setStatus(false);

        if (subject.getPrincipal() == null) {
            result.setMsg("请先登录");

            response.getWriter().print( result );
        } else {
            resp.setHeader("Access-Control-Allow-Origin",  req.getHeader("Origin"));
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setContentType("application/json; charset=utf-8");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            result.setMsg("你没有权限");
//            out.println(result);
            JSONObject jsonObject =(JSONObject) JSONObject.toJSON(result);
            out.write(String.valueOf(jsonObject));
            out.flush();
            out.close();
            return false;

        }
        return false;

    }
}
