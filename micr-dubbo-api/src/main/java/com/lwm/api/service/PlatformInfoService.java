package com.lwm.api.service;

import com.lwm.common.vo.PlatformInfo;

/**
 * @author lwm1435@163.com
 * @date 2022-01-01 21:51
 * @description
 */
public interface PlatformInfoService {
    /**
     * @return 平台首页统计信息
     */
    PlatformInfo queryIndexInfo();
}
