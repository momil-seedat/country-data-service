package com.demo.country.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

/*
In this class userDetailService bean added to retreive the user detail
Two users are created in userDetail Manager using InMemoryUserDetailsManager
 */

@Configuration
public class UserConfig {

    public static final String ADMIN_ROLE="ADMIN";
    public static final String CUSTOMER_ROLE="CUSTOMER";

    @Value("${admin.user.name}")
    private String adminName;

    @Value("${admin.user.password}")
    private String adminPassword;

    @Value("${customer.user.name}")
    private String customerName;

    @Value("${customer.user.password}")
    private String customerPassword;

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        UserDetails adminUser = User.withUsername(adminName)
                .password(adminPassword) .authorities("read","write") .roles(ADMIN_ROLE) .build();
        userDetailsManager.createUser(adminUser);

        UserDetails customerUser = User.withUsername(customerName)
                .password(customerPassword) .authorities("read") .roles(CUSTOMER_ROLE) .build();
        userDetailsManager.createUser(customerUser);

        return userDetailsManager;
    }
}





