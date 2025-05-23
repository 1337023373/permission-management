package com.hengheng.common.utils;


import com.hengheng.pojo.entity.UserInfoEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @Author lkj
 * @Date 2025/5/23 9:09
 * @Version 1.0
 */
public class TokenUtil {

    @Value("${jwt.key}")
    private static String key;

    @Value("${jwt.ttl}")
    private static String ttl;
    /**
     * 密钥
     */
    public static final String JWT_KEY = key;
    /**
     * 过期时间
     */
    public static final Long JWT_TTL = Long.valueOf(ttl);

    public static String createJWT(UserInfoEntity userInfo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userInfo.getUserId());
        map.put("password", userInfo.getPassword());
        return createJWT(map, JWT_TTL);
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
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)
                // 计算内容
                .setSubject(subject)
                // 签发者
                .setIssuer("budai")
                // 签发时间
                .setIssuedAt(now)
                // 加密算法签名
                .signWith(algorithm, secretKey)
                .setExpiration(expDate);
    }
}
