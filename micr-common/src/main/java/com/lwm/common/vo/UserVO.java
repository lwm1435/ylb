package com.lwm.common.vo;

import lombok.Data;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 15:32
 * @description 包含user信息的token
 */
@Data
public class UserVO {
    private Integer uid;
    private String name;
    private String phone;
}
