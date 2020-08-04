//package com.example.demo.security;
//
////import com.example.demo.config.JwtAuthenticationEntryPoint;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    
//	@Autowired
//	private UserDetailsService userDetailsService;
//        
////        @Autowired
////        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//        
////        @Autowired
////        private JwtRequestFilter jwtRequestFilter;
//        
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//    	http
//    	    .httpBasic()
//    	    .and()
//    	    .authorizeRequests()
//    	    .antMatchers("/api/**").hasRole("USER")
//    	    .antMatchers("/**").hasRole("ADMIN")
//    	    .antMatchers("**/**").hasRole("SUPER_ADMMIN")   
//            .and().csrf().disable().headers().frameOptions().disable();
//
////        http.csrf().disable()
////                .authorizeRequests().antMatchers("/authenticate")
////                .permitAll().antMatchers(HttpMethod.OPTIONS, "/**")
////                .permitAll()
////                .antMatchers("/api/**").hasRole("USER")
////                .antMatchers("/**").hasRole("ADMIN")
////                .antMatchers("**/**").hasRole("SUPER_ADMMIN")
////                .and()
////                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
////                .and().sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        
//        // Add a filter to validate the tokens with every request
////        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    	
//    }
//    
//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    	auth.userDetailsService(userDetailsService).passwordEncoder(
//    		NoOpPasswordEncoder.getInstance());
//    }
//    
//    @Bean
//    public PasswordEncoder passworEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    
////    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception{
////        return super.authenticationManagerBean();
////    }
//}
