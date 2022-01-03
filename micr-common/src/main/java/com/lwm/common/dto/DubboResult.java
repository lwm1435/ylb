package com.lwm.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 13:27
 * @description 封装dubbo服务的返回结果
 */
@Data
public class DubboResult implements Serializable {
    /**
     * 调用结果
     */
    private boolean invoke;
    /**
     * 校验码
     */
    private int code = -1;
    /**
     * 结果信息
     */
    private String msg = "接口调用失败";
    /**
     * 封装的vo对象
     */
    private Object data ;

    public static DubboResult fail(){
        return new DubboResult();
    }
}
