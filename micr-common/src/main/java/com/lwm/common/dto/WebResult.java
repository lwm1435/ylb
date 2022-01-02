package com.lwm.common.dto;

import com.lwm.common.enums.Code;
import lombok.Data;

/**
 * @author lwm1435@163.com
 * @date 2022-01-01 22:10
 * @description 表现层返回前端的数据封装结果
 */
@Data
public class WebResult {
    /**
     * 应答码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    /**
     * 默认失败的结果封装
     * @return
     */
    public static WebResult fail(){
        WebResult webResult = new WebResult();
        webResult.setEnumCode(Code.FAIlURE);
        return webResult;
    }

    public void setEnumCode(Code code){
        setCode(code.getCode());
        setMsg(code.getMsg());
    }

}
