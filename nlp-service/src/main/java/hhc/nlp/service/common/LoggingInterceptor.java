package hhc.nlp.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author huangzhiqiang
 */
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String trace_uuid = UUID.randomUUID().toString().replace("-", "");
        try {
            MDC.put("trace_uuid", trace_uuid);
        } catch (Exception e) {}
        request.setAttribute("trace_uuid", trace_uuid);
        request.setAttribute("request_time", System.currentTimeMillis());
        logRequest(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        response.addHeader("trace_id", request.getAttribute("trace_uuid").toString());
        long requestTime = (Long) request.getAttribute("request_time");
        LOGGER.info("\nResponse: {} \n\tstatus: {}\n\ttime: {}", request.getAttribute("trace_uuid"), response.getStatus(), System.currentTimeMillis() - requestTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            MDC.remove("trace_uuid");
        } catch (Exception e) {}
    }

    private void logRequest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder log = new StringBuilder();
        log.append("\nRequest: " + request.getAttribute("trace_uuid") + "\n\t");
        log.append("url: " + request.getRequestURL() + "\n\t");
        log.append("queryString: " + request.getQueryString() + "\n\t");
        log.append("method: " + request.getMethod() + "\n\t");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.append(headerName + ": " + request.getHeader(headerName) + "\n\t");
        }
        LOGGER.info(log.toString());
    }
}
