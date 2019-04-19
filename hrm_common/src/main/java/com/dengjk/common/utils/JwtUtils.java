package com.dengjk.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * @author Dengjk
 * @create 2019-04-19 23:34
 * @desc jwt配置
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt.config")
public class JwtUtils {

    /**
     * 签名
     */
    public String signKey;

    /**
     * 实效时间
     */
    public Long ttl;


    /**
     * 生成jwt
     *
     * @param id
     * @param name
     * @param dataMap
     * @return
     */
    public String createJwt(String id, String name, Map<String, Object> dataMap) {
        /**计算失效时间*/
        long now = System.currentTimeMillis();
        long expTime = now + ttl;
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, signKey);
        jwtBuilder.setClaims(dataMap);
        jwtBuilder.setExpiration(new Date(expTime));
        return jwtBuilder.compact();
    }


    /**
     * 解析jwt
     *
     * @param token
     * @return
     */
    public Claims parseJwt(String token) {
        Claims hrm = Jwts.parser().setSigningKey(signKey).parseClaimsJws(token).getBody();
        return hrm;
    }
}
