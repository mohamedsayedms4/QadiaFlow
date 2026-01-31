package org.example.qadiaflow.presentation.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class ControllerExceptionLoggingAspect {

    @AfterThrowing(
            pointcut = "within(@org.springframework.web.bind.annotation.RestController *)",
            throwing = "ex"
    )
    public void logControllerException(JoinPoint jp, Throwable ex) {
        HttpServletRequest req = currentRequestOrNull();

        String correlationId = MDC.get("correlationId");
        String signature = jp.getSignature().toShortString();

        if (req == null) {
            log.error("Unhandled exception | correlationId={} | at={} | exType={} | msg={}",
                    correlationId, signature, ex.getClass().getSimpleName(), ex.getMessage(), ex);
            return;
        }

        String method = req.getMethod();
        String uri = req.getRequestURI();
        String query = req.getQueryString();
        String fullPath = (query == null || query.isBlank()) ? uri : (uri + "?" + query);

        String remoteIp = firstForwardedFor(req);
        String ua = req.getHeader("User-Agent");

        // choose warn/error based on your policy
        log.error(
                "Unhandled exception | correlationId={} | at={} | method={} | path={} | ip={} | ua={} | exType={} | msg={}",
                correlationId, signature, method, fullPath, remoteIp, ua,
                ex.getClass().getSimpleName(), ex.getMessage(), ex
        );
    }

    private HttpServletRequest currentRequestOrNull() {
        var attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes sra) return sra.getRequest();
        return null;
    }

    private String firstForwardedFor(HttpServletRequest req) {
        String xff = req.getHeader("X-Forwarded-For");
        if (xff == null || xff.isBlank()) return req.getRemoteAddr();
        return xff.split(",")[0].trim();
    }
}
