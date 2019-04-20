package com.dengjk.common.utils;

import com.dengjk.common.exception.LoginErrorException;
import io.jsonwebtoken.*;
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
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name).setIssuedAt(new Date()).setExpiration(new Date(expTime))
                .signWith(SignatureAlgorithm.HS256, signKey);
        if (dataMap != null) {
            dataMap.forEach((k, v) -> {
                jwtBuilder.claim(k, v);
            });
        }
        return jwtBuilder.compact();
    }


    /**
     * 解析jwt
     *
     * @param token
     * @return
     */
    public Claims parseJwt(String token) throws LoginErrorException {
        try {
            Claims hrm = Jwts.parser().setSigningKey(signKey).parseClaimsJws(token).getBody();
            return hrm;
        } catch (ExpiredJwtException e) {
            throw new LoginErrorException("token已过期");
        } catch (UnsupportedJwtException e) {
            throw new LoginErrorException("token不支持");
        } catch (MalformedJwtException e) {
            throw new LoginErrorException("token格式不对");
        } catch (SignatureException e) {
            throw new LoginErrorException("token签名不对");
        } catch (IllegalArgumentException e) {
            throw new LoginErrorException("token格式转换错误");
        }
    }
}
