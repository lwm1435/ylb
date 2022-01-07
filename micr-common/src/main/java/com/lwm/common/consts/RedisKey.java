package com.lwm.common.consts;

/**
 * @author lwm1435@163.com
 * @date 2021-12-21 22:05
 * @description
 */
public class RedisKey {
    /**
     * 短信验证码
     */
    public static final String SMS_CODE_REGISTER="SMS:CODE:REG:";

    /**
     * 投资排行榜
     */
    public static final String INVEST_RANKING = "INVEST:RANKING";

    /**
     * 平台首页的三个数据
     */
    public static final String PLATFORM_INDEX_DATA = "PLATFORM:INDEX:DATA";

    /**
     * 充值订单key
     */
    public static final String RECHARGE_ORDER_ID_ZSET = "RECHARGE:ORDER:ID:ZSET";
}
