package com.example.admin.Interceptor;

import com.example.admin.converter.SysUserConverter;
import com.example.admin.domain.SysUserDomain;
import com.example.admin.pojo.vo.SysUserVo;
import com.example.common.utils.content.UserContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * todo 将获取用户信息改为在过滤器中处理
 */
@Slf4j
@Component
public class UserContentInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserDomain sysUserDomain;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("UserContentInterceptor");
        String userId = request.getHeader("userId");
        if(!StringUtils.isBlank(userId)){
            //在线程上下文设置用户信息
            SysUserVo sysUserVo = sysUserDomain.select(Long.valueOf(userId));
            UserContent.set(SysUserConverter.INSTANCE.voToDto(sysUserVo));
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContent.clear();   //清除线程上下文中的用户信息
    }
}
