package org.example.springnewbie;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtToken {

    // 簽發人
    private static final String ISS = "P3K0p3K0";
    @Value("${token.SECRET}")
    private String SECRET;

    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
