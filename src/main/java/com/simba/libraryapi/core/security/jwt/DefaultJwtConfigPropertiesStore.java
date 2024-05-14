package com.simba.libraryapi.core.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "application.security.jwt")
public class DefaultJwtConfigPropertiesStore {

    private String secretKey;

    private long accessTokenExpiration;

    private long refreshTokenExpiration;
}
