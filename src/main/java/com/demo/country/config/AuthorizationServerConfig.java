package com.demo.country.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/*
In this class we are configuring authorization server.
This class extends AuthorizationServerConfigurerAdapter that generates token to clients
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String CLIENT_ID = "demo-oath-client";
    private static final String CLIENT_SECRET = "$2a$12$x6N9H5Wck1Jv8eyzFapGX.kBWhpcPiHtGfBJB9KuCb9ksb7tkGwN.";
    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String AUTHORIZATION_CODE = "authorization_code";
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 4*60*60;

    @Autowired
    AuthenticationManager authenticationManager;
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("as466gf");
        return converter;
    }

    //in memory token store to store tokens
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    //configurer clients in authorization server
    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

        configurer
                .inMemory()
                .withClient(CLIENT_ID)
                .secret(CLIENT_SECRET)
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD,AUTHORIZATION_CODE).scopes("read")
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);



    }
    // This method defines  authorization,token endpoints and services
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter());
    }


}
