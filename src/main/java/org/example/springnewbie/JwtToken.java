package org.example.springnewbie;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtToken {
    private static final String ISS = "P3K0p3K0";
    private static final long EXPIRE_TIME = 60 * 60 * 1000;
    SecretKey key = Jwts.SIG.HS512.key().build();

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuer(ISS)
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        boolean valid = true;

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

        } catch (JwtException ex) {
            valid = false;
        }

        return valid;
    }
}
