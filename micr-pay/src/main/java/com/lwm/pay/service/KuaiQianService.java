package com.lwm.pay.service;

import com.lwm.api.service.RechargeService;
import com.lwm.pay.util.Pkipair;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lwm1435@163.com
 * @date 2022-01-04 17:40
 * @description
 */
@Service
public class KuaiQianService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @DubboReference(interfaceClass = RechargeService.class, version = "1.0")
    private RechargeService rechargeService;

    /**
     * 生成快钱需要的表单数据
     * Map<String,String>存放的是表单里面的所有的参数名和值
     */
    public Map<String, String> generateKqPayApiData(BigDecimal money, Integer userId, String phone, String token) {

        Map<String, String> data = new HashMap<>();
        //人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填。
        String merchantAcctId = "1001214035601";//
        //编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填。
        String inputCharset = "1";
        //接收支付结果的页面地址，该参数一般置为空即可。
        String pageUrl = "";
        //服务器接收支付结果的后台地址，该参数务必填写，不能为空。
        String bgUrl = "http://localhost:9000/receive/kq/notify";
        //网关版本，固定值：v2.0,该参数必填。
        String version = "v2.0";
        //语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
        String language = "1";
        //签名类型,该值为4，代表PKI加密方式,该参数必填。
        String signType = "4";
        //支付人姓名,可以为空。
        String payerName = "";
        //支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空。
        String payerContactType = "2";
        //支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空。
        String payerContact = phone;
        //指定付款人，可以为空
        String payerIdType = "3";
        //付款人标识，可以为空
        String payerId = String.valueOf(userId);
        //付款人IP，可以为空
        String payerIP = "";
        //商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空。
        String orderId = generateOrderId();
        //订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试。该参数必填。
        //参数money是元，转为 分
        String orderAmount = money.multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString();
        //订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空。
        String orderTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        //快钱时间戳，格式：yyyyMMddHHmmss，如：20071117020101， 可以为空
        String orderTimestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        ;
        //商品名称，可以为空。
        String productName = "ylb";
        //商品数量，可以为空。
        String productNum = "1";
        //商品代码，可以为空。
        String productId = "ylb001";
        //商品描述，可以为空。
        String productDesc = "ylb";
        //扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext1 = String.valueOf(userId);
        //扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext2 = token;
        //支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10-1或10-2，必填。
        String payType = "00";
        //银行代码，如果payType为00，该值可以为空；如果payType为10-1或10-2，该值必须填写，具体请参考银行列表。
        String bankId = "";
        //同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
        String redoFlag = "0";
        //快钱合作伙伴的帐户号，即商户编号，可为空。
        String pid = "";

        // signMsg 签名字符串 不可空，生成加密签名串
        String signMsgVal = "";
        signMsgVal = appendParam(signMsgVal, "inputCharset", inputCharset, data);
        signMsgVal = appendParam(signMsgVal, "pageUrl", pageUrl, data);
        signMsgVal = appendParam(signMsgVal, "bgUrl", bgUrl, data);
        signMsgVal = appendParam(signMsgVal, "version", version, data);
        signMsgVal = appendParam(signMsgVal, "language", language, data);
        signMsgVal = appendParam(signMsgVal, "signType", signType, data);
        signMsgVal = appendParam(signMsgVal, "merchantAcctId", merchantAcctId, data);
        signMsgVal = appendParam(signMsgVal, "payerName", payerName, data);
        signMsgVal = appendParam(signMsgVal, "payerContactType", payerContactType, data);
        signMsgVal = appendParam(signMsgVal, "payerContact", payerContact, data);
        signMsgVal = appendParam(signMsgVal, "payerIdType", payerIdType, data);
        signMsgVal = appendParam(signMsgVal, "payerId", payerId, data);
        signMsgVal = appendParam(signMsgVal, "payerIP", payerIP, data);
        signMsgVal = appendParam(signMsgVal, "orderId", orderId, data);
        signMsgVal = appendParam(signMsgVal, "orderAmount", orderAmount, data);
        signMsgVal = appendParam(signMsgVal, "orderTime", orderTime, data);
        signMsgVal = appendParam(signMsgVal, "orderTimestamp", orderTimestamp, data);
        signMsgVal = appendParam(signMsgVal, "productName", productName, data);
        signMsgVal = appendParam(signMsgVal, "productNum", productNum, data);
        signMsgVal = appendParam(signMsgVal, "productId", productId, data);
        signMsgVal = appendParam(signMsgVal, "productDesc", productDesc, data);
        signMsgVal = appendParam(signMsgVal, "ext1", ext1, data);
        signMsgVal = appendParam(signMsgVal, "ext2", ext2, data);
        signMsgVal = appendParam(signMsgVal, "payType", payType, data);
        signMsgVal = appendParam(signMsgVal, "bankId", bankId, data);
        signMsgVal = appendParam(signMsgVal, "redoFlag", redoFlag, data);
        signMsgVal = appendParam(signMsgVal, "pid", pid, data);

        System.out.println(signMsgVal);
        Pkipair pki = new Pkipair();
        //生成签名串
        String signMsg = pki.signMsg(signMsgVal);
        data.put("signMsg", signMsg);

        return data;
    }

    /**
     * 处理快钱的异步通知
     *
     * @param request 请求参数
     */
    public boolean doKqNotify(HttpServletRequest request) {
        String merchantAcctId = request.getParameter("merchantAcctId");
        String version = request.getParameter("version");
        String language = request.getParameter("language");
        String signType = request.getParameter("signType");
        String payType = request.getParameter("payType");
        String bankId = request.getParameter("bankId");
        String orderId = request.getParameter("orderId");
        String orderTime = request.getParameter("orderTime");
        String orderAmount = request.getParameter("orderAmount");
        String bindCard = request.getParameter("bindCard");
        if (request.getParameter("bindCard") != null) {
            bindCard = request.getParameter("bindCard");
        }
        String bindMobile = "";
        if (request.getParameter("bindMobile") != null) {
            bindMobile = request.getParameter("bindMobile");
        }
        String bankDealId = request.getParameter("bankDealId");
        String dealTime = request.getParameter("dealTime");
        String payAmount = request.getParameter("payAmount");
        String fee = request.getParameter("fee");
        String ext1 = request.getParameter("ext1");
        String ext2 = request.getParameter("ext2");
        String payResult = request.getParameter("payResult");
        String aggregatePay = request.getParameter("aggregatePay");
        String errCode = request.getParameter("errCode");
        String signMsg = request.getParameter("signMsg");
        String dealId = request.getParameter("dealId");
        String merchantSignMsgVal = "";
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", merchantAcctId, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", version, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", language, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", signType, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", payType, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", bankId, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", orderId, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", orderTime, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", orderAmount, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindCard", bindCard, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindMobile", bindMobile, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", dealId, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", bankDealId, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", dealTime, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", payAmount, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", payResult, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "aggregatePay", aggregatePay, null);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", errCode, null);


        Pkipair pki = new Pkipair();
        //验签方法 TRUE:通过，false不能处理业务
        boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg);
        System.out.println("flag=====" + flag);
        flag = true;
        if (flag) {
            flag = false;
            //验签通过
            //判断是否时商户的账户id，比较merchantAcctId
            if ("1001214035601".equals(merchantAcctId)) {
                //处理订单，调用data-service的方法
                int res = rechargeService.handlerRecharge("kq", orderId, payResult, payAmount);
                if (res == 1) {
                    flag = true;
                }
            }
        } else {
            //没有通过
            //输出日志：
            System.out.println("没有通过验签的订单：" + orderId + "|" + payAmount);
        }
        return flag;
    }


    /**
     * 拼接字符串，存储数据到map中
     */
    private String appendParam(String returns, String paramId,
                               String paramValue, Map<String, String> data) {
        if (!"".equals(returns)) {
            if (paramValue != null && !paramValue.equals("")) {
                returns += "&" + paramId + "=" + paramValue;
                if (data != null) {
                    data.put(paramId, paramValue);
                }
            }
        } else {
            if (paramValue != null && !paramValue.equals("")) {
                returns = paramId + "=" + paramValue;
                if (data != null) {
                    data.put(paramId, paramValue);
                }
            }
        }
        return returns;
    }


    /*生成商家订单号*/
    private String generateOrderId() {
        //毫秒级的时间加上uuid前6位
        String dateStr = "KQ" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
                + UUID.randomUUID().toString().substring(0, 6);

        return dateStr;
    }


}
