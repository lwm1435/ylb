package com.lwm.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.lwm.common.dto.WebResult;
import com.lwm.common.enums.Code;
import com.lwm.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 17:49
 * @description token校验拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate  redis;

    @Value("${jwt-secretkey}")
    private String userKey;
    /**
     * 携带token请求的预处理
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {
        System.out.println("拦截器执行===Method:" + request.getMethod()
                + "，URL:" + request.getRequestURL());

        //预处理请求返回true
        if ("OPTIONS".equals(request.getMethod())){
            return true;
        }
        boolean checkToken = false;
        //从请求头中获取token和uid,验证token中的uid是否和请求头的uid相同
        String authorization = request.getHeader("Authorization");
        String uid = request.getHeader("uid");
        if (StringUtils.isNoneBlank(authorization,uid)){
            if ("Bearer".equals(authorization.substring(0,6))){
                String token = authorization.substring(7);
                //从redis中查询是否有该token，没有表示该token用户没有退出
                if (redis.opsForValue().get(token) == null){
                    //读取token
                    try {
                        Claims claims = JwtUtil.readJwt(userKey, token);
                        //从claims中的获取uid
                        Integer tokenUid = claims.get("uid", Integer.class);
                        //和请求头中的uid比较
                        if (uid.equals(String.valueOf(tokenUid))){
                            checkToken = true;
                        }
                    } catch (Exception e) {
                        checkToken = false;
                        e.printStackTrace();
                    }
                }
            }
        }
        if (!checkToken){
            //输出权限校验失败的json消息
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            WebResult result = new WebResult();
            result.setEnumCode(Code.TOKEN_INVALID);
            writer.print(JSONObject.toJSONString(result));
            writer.close();
        }
        return checkToken;
    }
}
