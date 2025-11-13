package com.fawnyr.healthsmartmedicine.aop;

import com.fawnyr.healthsmartmedicine.annotation.AuthCheck;
import com.fawnyr.healthsmartmedicine.exception.BusinessException;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.model.entity.User;
import com.fawnyr.healthsmartmedicine.model.enums.UserRoleEnum;
import com.fawnyr.healthsmartmedicine.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthInterceptor {
    @Resource
    UserService userService;
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 1.获取当前登录用户
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User loginUser = userService.getLoginUser(request);
        // 2.不需要权限，放行
        String mustRole = authCheck.mustRole();
        UserRoleEnum userRoleEnum = UserRoleEnum.getUserRoleEnum(mustRole);
        if(userRoleEnum==null)
            joinPoint.proceed();
        // 3.获取当前用户的权限
        UserRoleEnum roleEnum = UserRoleEnum.getUserRoleEnum(loginUser.getUserRole());
        // 4.没有权限，拒绝
        if(roleEnum==null)
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        // 5.要求必须有管理员权限，但用户没有管理员权限，拒绝
        if(UserRoleEnum.ADMIN.equals(userRoleEnum)&!UserRoleEnum.ADMIN.equals(roleEnum))
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        // 6.通过权限校验，放行
        return joinPoint.proceed();
    }
}
