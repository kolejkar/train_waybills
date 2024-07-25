package karol.train_waybill.component;

import java.security.SignatureException;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import karol.train_waybill.CompanyViewImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
	
	private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final String jwtIssuer = "com.project";


	public boolean validate(String token) {
		 try {
	            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	            return true;
	        //} catch (SignatureException ex) {
	        //    log.error("Invalid JWT signature - {}", ex.getMessage());
	        } catch (MalformedJwtException ex) {
	            log.error("Invalid JWT token - {}", ex.getMessage());
	        //} catch (ExpiredJwtException ex) {
	        //    logger.error("Expired JWT token - {}", ex.getMessage());
	        } catch (UnsupportedJwtException ex) {
	            log.error("Unsupported JWT token - {}", ex.getMessage());
	        } catch (IllegalArgumentException ex) {
	            log.error("JWT claims string is empty - {}", ex.getMessage());
	        }
	        return false;
	}

	public String getUsername(String token) {
		return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
	}

	public String generateAccessToken(CompanyViewImpl user) {		
			return Jwts.builder()
			        .setSubject(user.getUsername())
			        .setIssuer(jwtIssuer)
			        .setIssuedAt(new Date())
			        .setExpiration(new Date((new Date()).getTime() + 86400000))
			        .signWith(key)
			        .compact();
	}

}
