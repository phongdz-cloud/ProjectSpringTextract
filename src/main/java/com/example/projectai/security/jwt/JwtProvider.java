package com.example.projectai.security.jwt;

import com.example.projectai.security.principle.UserPrinciple;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  private final String HEADER = "Authorization";

  private final String jwtSecret = "sonphongtranphat@student";

  private String tokenWrapper;

  private int jwtExpiration = 86400 * 60 * 60;

  public boolean preHandle(HttpServletRequest request) {
    final String authorizationHeader = request.getHeader(HEADER);
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
      String token = authorizationHeader.substring(7, authorizationHeader.length());
      tokenWrapper = token;
      return true;
    }
    return false;
  }

  public String createToken(Authentication authentication) {
    UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject(userPrinciple.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + jwtExpiration))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }


  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature -> Message: {}", e);
    } catch (MalformedJwtException e) {
      logger.error("The token invalid format -> Message: {}", e);
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported JWT token -> Message: {}", e);
    } catch (ExpiredJwtException e) {
      logger.error("Expired JWT token -> Message: {}", e);
    } catch (IllegalArgumentException e) {
      logger.error("Jwt claims string is empty -> Message: {}", e);
    }
    return false;
  }

  public String getUsernameFormToken(String token) {
    String username = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    return username;
  }
}
