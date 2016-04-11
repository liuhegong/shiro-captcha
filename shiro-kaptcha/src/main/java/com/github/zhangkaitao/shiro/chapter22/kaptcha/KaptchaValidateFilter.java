/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.github.zhangkaitao.shiro.chapter22.kaptcha;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码过滤器
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-22 下午4:01
 * <p>Version: 1.0
 */
public class KaptchaValidateFilter extends AccessControlFilter {

    @Autowired
    private KaptchaSupport kaptchaSupport;

    private boolean kaptchaEbabled = true;//是否开启验证码支持

    private String kaptchaParam = "kaptchaCode";//前台提交的验证码参数名

    private String failureKeyAttribute = "shiroLoginFailure"; //验证码验证失败后存储到的属性名

    public void setKaptchaEbabled(boolean kaptchaEbabled) {
        this.kaptchaEbabled = kaptchaEbabled;
    }
    public void setKaptchaParam(String kaptchaParam) {
        this.kaptchaParam = kaptchaParam;
    }
    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
        request.setAttribute("kaptchaEbabled", kaptchaEbabled);

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //2、判断验证码是否禁用 或不是表单提交（允许访问）
        if (kaptchaEbabled == false || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }
        //3、此时是表单提交，验证验证码是否正确
        return kaptchaSupport.validateCaptcha(httpServletRequest.getParameter(kaptchaParam), httpServletRequest.getSession());
    }
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //如果验证码失败了，存储失败key属性
        request.setAttribute(failureKeyAttribute, "kaptcha.error");
        return true;
    }
}
