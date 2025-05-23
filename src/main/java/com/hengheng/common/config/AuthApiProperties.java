package com.hengheng.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lkj
 */
@Component
@ConfigurationProperties(prefix = "auth.api")
@Data
public class AuthApiProperties {
    private String free;
    private String user;
    private String admin;

    public String[] getFreeArray() {
        return free != null ? free.trim().split("\\s*,\\s*") : new String[0];
    }

    public String[] getUserArray() {
        return user != null ? user.trim().split("\\s*,\\s*") : new String[0];
    }

    public String[] getAdminArray() {
        return admin != null ? admin.trim().split("\\s*,\\s*") : new String[0];
    }


}
