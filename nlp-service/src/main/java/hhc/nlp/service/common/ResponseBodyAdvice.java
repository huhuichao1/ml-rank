package hhc.nlp.service.common;

import hhc.common.entity.RestResponse;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.LinkedHashMap;


@ControllerAdvice
public class ResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (!super.supports(returnType, converterType)) {
            return false;
        }
        Class<?> clazz = returnType.getMethod().getDeclaringClass();
        if (clazz.getPackage().getName().startsWith("hhc.nlp.service.controller")) {
            return true;
        } else if (clazz.equals(BasicErrorController.class)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        Object rawData = bodyContainer.getValue();
        Class<?> clazz = returnType.getMethod().getDeclaringClass();
        if (clazz.equals(BasicErrorController.class)) {
            LinkedHashMap errorAttributes = (LinkedHashMap) rawData;
            int errCode = (int) errorAttributes.get("status");
            String errMessage = (String) errorAttributes.get("message");
            bodyContainer.setValue(new RestResponse(errCode, errMessage, errorAttributes));
        } else {
            long requestTime=(Long) ((ServletServerHttpRequest) request).getServletRequest().getAttribute("request_time");
            bodyContainer.setValue(new RestResponse(rawData,System.currentTimeMillis()-requestTime));
        }
    }
}
