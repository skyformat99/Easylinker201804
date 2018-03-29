package com.easylinker.proxy.server.app.handler;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统异常处理，比如：404,500
     *
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JSONObject defaultErrorHandler(Exception e) {
        JSONObject resultJson = new JSONObject();
        e.printStackTrace();
        if (e instanceof NoHandlerFoundException) {
            resultJson.put("state", 0);
            resultJson.put("message", "Error code 404! 不存在该路由!");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            resultJson.put("state", 0);
            resultJson.put("message", "Error code 500! 请求的HTTP方法不支持!");
        } else if (e instanceof NullPointerException) {
            resultJson.put("state", 0);
            resultJson.put("message", "Error code 5001! 丢失必要参数!");

        } else if (e instanceof HttpMessageNotReadableException) {
            resultJson.put("state", 0);
            resultJson.put("message", "Error code 5002! 请求体不全!!");


        } else if (e instanceof MethodArgumentTypeMismatchException) {
            resultJson.put("state", 0);
            resultJson.put("message", "Error code 5003! 参数转换格式错误!");


        } else if (e instanceof AccessDeniedException) {

            resultJson.put("state", 0);
            resultJson.put("message", "Error code 5004!你没有权限访问该路径!");
        }
        else {
            System.out.println("异常信息:"+e.getLocalizedMessage());
            resultJson.put("state", 0);
            resultJson.put("message", "Error code 5005!未知错误,请联系管理员!");
        }
        return resultJson;
    }


}