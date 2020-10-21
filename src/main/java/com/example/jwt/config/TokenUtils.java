package com.example.jwt.config;

import com.example.jwt.domain.member.Member;
import com.example.jwt.domain.member.UserRole;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander
 * @date 2020-10-20
 **/
@Slf4j
@RequiredArgsConstructor
public class TokenUtils {

    private static final String SECRET_KEY = "alxndr";

    public static String generateJwtToken(User member) {

        JwtBuilder builder = Jwts.builder()
                .setSubject(member.getUsername())
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setExpiration(createExpireDate())
                .signWith(SignatureAlgorithm.HS256, createSigningKey());

        return builder.compact();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);

            log.info("expireTime" + claims.getExpiration());
            log.info("email" + claims.get("email"));
            log.info("role" + claims.get("role"));

            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token Expired");
            return false;
        } catch (JwtException je) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException ne) {
            log.error("Token is null");
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    // 토근 만료 30일
    private static Date createExpireDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        return c.getTime();
    }

    // Header 생성
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private static Map<String, Object> createClaims(User member) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("email", member.getUsername());
        claims.put("role", member.getAuthorities());

        return claims;
    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
    }

    private static String getEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return (String) claims.get("email");
    }

    private static UserRole getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return (UserRole) claims.get("role");
    }


}
