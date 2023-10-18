package fa.training.mockproject.mockprojectfjb05group01.configuration.security.jwt;

import fa.training.mockproject.mockprojectfjb05group01.configuration.security.CustomeUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${ra.jwt.cecret}")
    private  String JWT_SECRET;
    @Value("${ra.jwt.expriration}")
    private int JWT_EXPIRATION;


    //tao jwt tu thong tin user
    public  String generateToken(CustomeUserDetails customeUserDetails) {


        Date now = new Date();
        Date expiry = new Date(now.getTime() + JWT_EXPIRATION);
        //tao chuoi jwt tu email cua user
        return Jwts.builder()
                .setSubject(customeUserDetails.getUsername())
                .claim("isEnable", customeUserDetails.isEnabled())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET)
                .compact();
    }

    //lay thong tin user tu jwt
    public  String getUserNameFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        //tra lai ra thong tin username==email;
        return claims.getSubject();
    }
    //validate khi lay thong tin tu jwt
    public  boolean validateToken(String authToken) {
        try{
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException ex) {
            log.error("Invalid Jwt token");
        }catch (ExpiredJwtException ex) {
            log.error("Expired Jwt token");
        }catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWt Token");
        }catch (IllegalArgumentException ex) {
            log.error("JWt claims String is empty");
        }
        return  false;
    }
}
