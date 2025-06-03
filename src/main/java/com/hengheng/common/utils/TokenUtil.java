package com.hengheng.common.utils;


import com.hengheng.common.config.JwtProperties;
import com.hengheng.pojo.entity.UserInfoEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @Author lkj
 * @Date 2025/5/23 9:09
 * @Version 1.0
 */
@Component
public class TokenUtil {
    private static JwtProperties jwtProperties ;

    public TokenUtil(JwtProperties jwtProperties) {
        TokenUtil.jwtProperties = jwtProperties;
    }

    public static String createJWT(UserInfoEntity userInfo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userInfo.getUserId());
        map.put("password", userInfo.getPassword());
        return createJWT(map, jwtProperties.getTtl());
    }

    /**
     * 生成 Token
     */
    public static String createJWT(Map<String, Object> map, Long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setClaims(map)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setExpiration(expDate)
                .setIssuer("budai")
                .signWith(SignatureAlgorithm.HS256, generalKey())
                .compact();
    }

    /**
     * 解析 Token
     */
    public static Claims parseJWT(String token) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 生成加密后的秘钥
     */
    public static SecretKey generalKey() {
        //byte[] encodedKey = Base64.getDecoder().decode(jwtProperties.getKey());
        String base64Key = jwtProperties.getKey(); // 必须是 Base64 编码
        byte[] encodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
    }


    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = jwtProperties.getTtl();
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)
                // 计算内容
                .setSubject(subject)
                // 签发者
                .setIssuer("lkj")
                // 签发时间
                .setIssuedAt(now)
                // 加密算法签名
                .signWith(algorithm, secretKey)
                .setExpiration(expDate);
    }
}
