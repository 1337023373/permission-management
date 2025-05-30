package com.hengheng.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lkj
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String key;
    private Long ttl;
    private String token;
    /**
     * 令牌前缀，最后留个空格 Bearer
     */
    private String tokenStartWith;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}
