package com.example.chatting.service;

//import io.jsonwebtoken.*;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private long tokenValidMilisecond = 100L * 60 * 60 ;
    //1시간만 토근이 가능하도록 설정

//    name으로 jwt토큰을 만든다
    public String generateToken(String name){
        Date now =new Date();
        return Jwts.builder()
                .setId(name)
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime()+tokenValidMilisecond))
                    //유효시간 설정
                .signWith(SignatureAlgorithm.ES256,secretKey)
                // 암호화 알고리즘 ,secret 값
                .compact();
    }
    // 복호화하여 이름을 얻는다
    public String getUserNameFromJwt(String jwt ){
        return getClaims(jwt).getBody().getId();
    }
    //    유효성을 검사한다
    public boolean validateToken(String jwt ){
        return this.getClaims(jwt)!=null;
    }

    public Jws<Claims> getClaims(String jwt){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        }
    }


}

