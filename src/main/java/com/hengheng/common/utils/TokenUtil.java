package com.hengheng.common.utils;


import com.hengheng.common.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @Author lkj
 * @Date 2025/5/23 9:09
 * @Version 1.0
 */
@Component
public class TokenUtil {
    private static JwtProperties jwtProperties;

    public TokenUtil(JwtProperties jwtProperties) {
        TokenUtil.jwtProperties = jwtProperties;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //public static String createJWT(UserInfoEntity userInfo) {
    //    HashMap<String, Object> map = new HashMap<>();
    //    map.put("userId", userInfo.getUserId());
    //    return createJWT(map, jwtProperties.getTtl());
    //}

    /**
     * 生成 Token
     */
    public static String createJWT(String subject) {
        return getJwtBuilder(subject, null, getUUID()).compact();
    }

    /**
     * 创建一个带有过期时间的JWT（JSON Web Token）字符串。
     */
    public static String createJWT(String subject, Long ttlMillis) {
        return getJwtBuilder(subject, ttlMillis, getUUID()).compact();
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
        String base64Key = jwtProperties.getKey();
        byte[] encodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        //定义签名算法
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        //生成密钥
        SecretKey secretKey = generalKey();
        //计算过期时间
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
