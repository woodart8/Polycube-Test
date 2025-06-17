package kr.co.polycube.backendtest.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


// 로깅용 AOP 클래스
@Slf4j
@Aspect
@Component
public class ClientAgentLoggingAspect {

    // user 등록, 조회, 수정 메서드에 적용
    @Before("execution(* kr.co.polycube.backendtest.user.controller.UserController.createUser(..)) || " +
            "execution(* kr.co.polycube.backendtest.user.controller.UserController.getUser(..)) || " +
            "execution(* kr.co.polycube.backendtest.user.controller.UserController.updateUser(..))")
    public void logClientAgent(JoinPoint joinPoint) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            log.warn("HttpServletRequest is null");
            return;
        }
        HttpServletRequest request = attrs.getRequest();
        String userAgent = request.getHeader("User-Agent");
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[{}] Client User-Agent: {}", methodName, userAgent);
    }

}

