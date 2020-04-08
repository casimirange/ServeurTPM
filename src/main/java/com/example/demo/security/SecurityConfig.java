/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.security;

import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    protected void configure(HttpSecurity http) throws Exception {
    	http
    	    .httpBasic()
    	    .and()
    	    .authorizeRequests()
    	    .antMatchers("/api/**").hasRole("USER").antMatchers("/**").hasRole("ADMIN").and()
    	    .csrf().disable().headers().frameOptions().disable();
    	
    }
    
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication().passwordEncoder(
    		NoOpPasswordEncoder.getInstance())
    	.withUser("user").password("password")
    	.roles("USER").and().withUser("admin").password("password2")
    	.roles("USER", "ADMIN");
    }
}
