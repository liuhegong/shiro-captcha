/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.github.zhangkaitao.shiro.chapter22.kaptcha;

import com.github.zhangkaitao.shiro.spring.SpringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 生成验证码
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-22 下午3:36
 * <p>Version: 1.0
 */
public class KaptchaFilter extends OncePerRequestFilter {

    private KaptchaSupport kaptchaSupport = SpringUtils.getBean("kaptchaSupport");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        kaptchaSupport.captcha(request, response);
    }


}
