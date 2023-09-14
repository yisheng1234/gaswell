package com.gaswell.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @return null
 * @author yisheng
 * @description 这个只是用来加密用户id，判断用户是否登陆过期的
 * @date 2021-11-26 10:44
 */
public class JWTUtils {
    //jwtToken代表密钥
    private static final String jwtToken = "gaswell!@#";

    public static String createToken(Integer userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken)
                .setClaims(claims)
                .setIssuedAt(new Date()) // 设置签发时间    30 * 24 * 60 * 60 * 1000 一个月过期时间
                .setExpiration(new Date(System.currentTimeMillis() +30 * 24 * 60 * 60 * 1000L));//
        String token = jwtBuilder.compact();
        return token;
    }
//检查token是否合法，能检查出是否过期
    public static Map<String, Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
//    public static void main(String[] args){
//        String token=JWTUtils.createToken(1l);
//        System.out.println(token);
//        Map<String,Object> map=JWTUtils.checkToken(token);
//        System.out.println(map.get("userId"));
//    }

}