package com.lwm.common.consts;

/**
 * @author lwm1435@163.com
 * @date 2021-12-14 20:47
 */
public class YLBConst {
    /***************产品类型************************/
    /*新手宝*/
    public static final int PRODUCT_TYPE_XINSHOUBAO = 0;
    /*优选*/
    public static final int PRODUCT_TYPE_YOUXUAN = 1;
    /*散标*/
    public static final int PRODUCT_TYPE_SANBIAO = 2;

    /***************产品状态************************/
    /*未满标*/
    public static final int PRODUCT_STATUS_SELL= 0;
    /*满标*/
    public static final int PRODUCT_STATUS_SELLED= 1;
    /*满标生成收益计划*/
    public static final int PRODUCT_STATUS_PLAN= 2;

    /***************投资记录状态************************/
    /*投资成功*/
    public static final int BID_STATUS_SUCC = 1;
    /*投资失败*/
    public static final int BID_STATUS_FAIL = 0;

    /***************收益记录状态************************/
    /*计划*/
    public static final int INCOME_STATUS_PLAN = 0;
    /*已返还*/
    public static final int INCOME_STATUS_BACK = 1;
}
