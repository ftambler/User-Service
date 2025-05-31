package um.g7.User_Service.Domain.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private Integer expirationTime;

    private final String DOORTYPE = "raspi";

    private JwtParser jwtParser;


    @PostConstruct
    public void configParser() {
        this.jwtParser = Jwts.parser().verifyWith(getKey()).build();
    } 

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateDoorToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim("type", DOORTYPE)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getKey())
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        try {
            return jwtParser.parseSignedClaims(token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
