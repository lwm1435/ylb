package com.lwm.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * @author lwm1435@163.com
 * @date 2021-12-24 17:30
 * @description
 */
public class JwtUtil {

    /**
     * 创建token
     * @param userKey 用户密钥
     * @param payload 个人数据（负荷）
     * @param minute 失效时间
     * @return token字符串
     */
    public static String creatJwt(String userKey, Map<String,Object> payload, int minute){
        String token = null;
        try {
            //根据用户的密钥创建密钥对象
            SecretKey secretKey = Keys.hmacShaKeyFor(userKey.getBytes(StandardCharsets.UTF_8));
            //创建jwt添加签名设置算法
            token = Jwts.builder().signWith(secretKey, SignatureAlgorithm.HS256)
                    //设置失效时间
                    .setExpiration(DateUtils.addMinutes(new Date(), minute))
                    //添加个人数据
                    .addClaims(payload)
                    //生成token字符串
                    .compact();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return token;
    }


    public static Claims readJwt(String userKey,String jwt){
        //根据用户的密钥创建密钥对象
        SecretKey secretKey = Keys.hmacShaKeyFor(userKey.getBytes(StandardCharsets.UTF_8));
        //创建jwt解析对象,传入密钥对象
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        //解析jwt
        Jws<Claims> claims = jwtParser.parseClaimsJws(jwt);

        return claims.getBody();

    }
}
