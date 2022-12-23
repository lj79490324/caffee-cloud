package com.coffee.common.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *  token工具包
 * @author rabit
 * @version v1.0
 * @date 2022/9/4 1:05
 */
public class JwtUtil {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    // public static final long TTL = 2 * 60 * 60 * 1000;
    public static final long TTL = 60 * 60 * 1000;
    public static final int TTL_COOKIE = 5 * 60 * 60;
    private static final String SECRET_KEY = "coffee-boot";
    private static final String AUTHORITIES = "authorities";
    public static final String  PREFIX_KEY = "jwt_user_info_";
            ;

    /**
     * 生成token
     * @param userName 用户名
     * @return 新token
     */
    public static String generateToken(String userName){
        return generateToken(userName, new ArrayList<>());
    }


    /**
     * 生成token
     * @param userName 用户名
     * @param authorities 权限列表
     * @return token
     */
    public static String generateToken(String userName, List<GrantedAuthority> authorities){
        return Jwts.builder().setSubject(userName)
                .claim(AUTHORITIES,authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+TTL))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }
    /**
     * 生成 token
     *
     * @param claims token信息
     * @return 生成新的token
     */
    private static String generateToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + TTL)) // 到期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 签名
                .compact();
    }

    /**
     * 解析 token
     *
     * @param token 需要解析的token
     * @return 解析后的数据
     */
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * 获取 username
     *
     * @param token 需要解析的token
     * @return 解析后的用户名
     */
    public static String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 获取 username
     *
     * @param claims token信息
     * @return 用户名
     */
    public static String getUsername(Claims claims) {
        return claims.getSubject();
    }

    /**
     * token是否过期
     *
     * @param token token
     * @return 过期true 未过期false
     */
    public static boolean isExpiration(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
    /**
     * 是否过期
     *
     * @param claims 信息
     * @return 是否过期
     */
    public static boolean isExpiration(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    /**
     * 获取角色
     *
     * @param token 前端的token
     * @return 权限列表
     */
    public static List<GrantedAuthority> getAuthorities(String token) {
        return parseToken(token).get(AUTHORITIES, List.class);
    }

    /**
     * 获取角色
     *
     * @param claims token信息
     * @return 全量列表
     */
    public static List<GrantedAuthority> getAuthorities(Claims claims) {
        return claims.get(AUTHORITIES, List.class);
    }

    public static List<String> getAuthoritiesStr(Claims claims) {
        return claims.get(AUTHORITIES, List.class);
    }


    /**
     * 刷新 token
     *
     * @param token 通过旧token刷新token
     * @return 刷新后的token
     */
    public static String refreshToken(String token) {
        return generateToken(parseToken(token));
    }
}
