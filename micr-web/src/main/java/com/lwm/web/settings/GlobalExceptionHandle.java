package com.lwm.web.settings;

import com.lwm.common.dto.WebResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lwm1435@163.com
 * @date 2021-12-31 17:39
 * @description 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandle {

    /**
     * 处理所有异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler()
    public WebResult handle(Exception e) {
        WebResult fail = WebResult.fail();
        fail.setMsg("异常信息：" + e.getMessage());
        return fail;
    }
}
