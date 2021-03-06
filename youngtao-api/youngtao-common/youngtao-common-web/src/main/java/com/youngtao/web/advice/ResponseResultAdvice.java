package com.youngtao.web.advice;

import com.youngtao.core.result.RestResult;
import com.youngtao.core.result.Result;
import com.youngtao.core.util.JsonUtils;
import com.youngtao.web.support.NoWrapper;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author ankoye@qq.com
 * @date 2020/12/28
 */
@SuppressWarnings("all")
@ControllerAdvice(annotations = RestController.class)
public class ResponseResultAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.getMethodAnnotation(NoWrapper.class) != null) {
            return false;
        }
        Class<?> clazz = methodParameter.getDeclaringClass();
        return clazz.getAnnotation(ResponseWrapper.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest request, ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (body instanceof Result) {
            return body;
        }
        Result<?> result = RestResult.success(body);
        if (body instanceof String) {
            return JsonUtils.toJson(result);
        }
        return result;
    }
}
