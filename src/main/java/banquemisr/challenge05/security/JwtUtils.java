package banquemisr.challenge05.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    // إنشاء التوكن باستخدام مفتاح قوي (512 بت)
    public String generateJwtToken(String username) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes()); // استخدام Keys لإنشاء مفتاح قوي

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512) // استخدام الخوارزمية والمفتاح
                .compact();
    }

    // استخراج اسم المستخدم من التوكن
    public String getUsernameFromJwtToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes()); // استخدام نفس المفتاح للتأكيد
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // التحقق من صحة التوكن
    public boolean validateJwtToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parser().setSigningKey(key).parseClaimsJws(token); // التحقق من التوكن
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid or expired JWT: " + e.getMessage());
        }
        return false;
    }
}
