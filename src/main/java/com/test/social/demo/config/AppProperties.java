package com.test.social.demo.config;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Auth {
        private String tokenSecret;
        private String refreshTokenSecret;
        private long tokenExpirationMsec;
        private long refreshTokenExpirationMsec;

        @Builder
        public Auth(String tokenSecret, String refreshTokenSecret, long tokenExpirationMsec, long refreshTokenExpirationMsec) {
            this.tokenSecret = tokenSecret;
            this.refreshTokenSecret = refreshTokenSecret;
            this.tokenExpirationMsec = tokenExpirationMsec;
            this.refreshTokenExpirationMsec = refreshTokenExpirationMsec;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

}