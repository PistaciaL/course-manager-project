package org.lr.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.lr.api.ErrorCode;
import org.lr.exception.TokenException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JWTUtil {

    private final static String KEY_STR = "ajewglujxyyxodixcrpmspdoavdykplenhhmbsjtfzzcjduq";
    private final static SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY_STR));

    public static String getToken(long id){
        Algorithm algorithm = Algorithm.HMAC256(KEY_STR);
        String token = JWT.create().withIssuedAt(new Date())
                .withIssuer("course_manager_project")
                .withClaim("userId", id)
                .sign(algorithm);
        return token;
    }

    public static void verifyToken(String token) throws TokenException {
        DecodedJWT verify = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY_STR);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer("course_manager_project")
                    .build();
            verify = jwtVerifier.verify(token);
            Map map = verify.getClaims();
            log.info(map.toString());
        } catch (UnsupportedJwtException e){
            throw new TokenException(ErrorCode.JWT_NOT_SUPPORTED);
        } catch (MalformedJwtException e){
            throw new TokenException(ErrorCode.JWT_MALFORMED);
        } catch (SignatureException e){
            throw new TokenException(ErrorCode.JWT_INVALID_SIGNATURE);
        } catch (ExpiredJwtException e){
            throw new TokenException(ErrorCode.JWT_IS_EXPIRED);
        } catch (IllegalArgumentException e){
            throw new TokenException(ErrorCode.JWT_IS_NULL);
        } catch (Exception e){
            throw new TokenException(ErrorCode.JWT_FAIL_ANALYSE);
        }
    }


}
