package org.example.springnewbie;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.springnewbie.DTO.UserDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;

@Component
public class JwtToken {

    private static final String ISS = "P3K0p3K0";
    private static final String SECRET = "i7_15_@_b35t_5PR1N9_53RV3r";
    private static final int EXPIRE_TIME = 5;

    public static String generateToken(Authentication auth) {
        UserDTO user = (UserDTO) auth.getPrincipal();
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.SECOND, EXPIRE_TIME);

        Claims claims = Jwts.claims();
        claims.setSubject(user.getName());
        claims.setExpiration(exp.getTime());
        claims.setIssuer(ISS);
        Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder().setClaims(claims).signWith(secretKey).compact();
    }

    public static String parseToken(String token) {
        Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
        JwtParser parser = Jwts
                .parserBuilder()
                .setSigningKey(secretKey).build();

        Claims claims = parser.parseClaimsJws(token).getBody();

        return claims.getSubject();
    }


    public static boolean validateToken(UserDTO user, String token) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
            JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            Claims claims = parser.parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();
            return (username.equals(userDetails.getUsername()) && !expiration.before(new Date()));
        } catch (Exception e) {
            return false;
        }

    }
}
