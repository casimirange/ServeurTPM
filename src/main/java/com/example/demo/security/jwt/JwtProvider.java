///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.demo.security.jwt;
//
//import com.example.demo.security.service.UserPrinciple;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//
//import io.jsonwebtoken.Jwts;
//import java.util.Date;
//import io.jsonwebtoken.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
///**
// *
// * @author Casimir
// */
//public class JwtProvider {
//    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
// 
//    @Value("${grokonez.app.jwtSecret}")
//    private String jwtSecret;
// 
//    @Value("${grokonez.app.jwtExpiration}")
//    private int jwtExpiration;
// 
//    public String generateJwtToken(Authentication authentication) {
// 
//        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
// 
//        return Jwts.builder()
//                    .setSubject((userPrincipal.getUsername()))
//                    .setIssuedAt(new Date())
//                    .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
//                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                    .compact();
//    }
//    
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            return true;
//        } catch (SignatureException e) {
//            logger.error("Invalid JWT signature -> Message: {} ", e);
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token -> Message: {}", e);
//        } catch (ExpiredJwtException e) {
//            logger.error("Expired JWT token -> Message: {}", e);
//        } catch (UnsupportedJwtException e) {
//            logger.error("Unsupported JWT token -> Message: {}", e);
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty -> Message: {}", e);
//        }
//        
//        return false;
//    }
//    
//    public String getUserNameFromJwtToken(String token) {
//        return Jwts.parser()
//                      .setSigningKey(jwtSecret)
//                      .parseClaimsJws(token)
//                      .getBody().getSubject();
//    }
//}
