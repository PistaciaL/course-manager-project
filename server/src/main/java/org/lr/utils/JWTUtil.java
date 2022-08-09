package org.lr.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.api.ResultCode;
import org.lr.entity.User;

import javax.crypto.SecretKey;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {

    private final static String KEY_STR = "ajewglujxyyxodixcrpmspdoavdykplenhhmbsjtfzzcjduq";

    public static String getToken(Map<String, String> map){
        Algorithm algorithm = Algorithm.HMAC256(KEY_STR);
        JWTCreator.Builder builder = JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime()+1000l*60*60*4))
                .withIssuer("course_manager_project");
        for(String key : map.keySet()){
            builder.withClaim(key, map.get(key));
        }
        String token = builder.sign(algorithm);
        return token;
    }

    public static String getToken(User user){
        Map<String, String> map = new HashMap<>();
        map.put("workNumb", user.getId());
        map.put("permission", user.getPermission().getValue());
        map.put("identity", user.getIdentity().getValue());
        map.put("name", user.getName());
        map.put("phone", user.getPhone());
        map.put("avatarUrl", user.getAvatar());
        return getToken(map);
    }

    public static String getTokenByClaimMap(Map<String, Claim> map){
        Algorithm algorithm = Algorithm.HMAC256(KEY_STR);
        JWTCreator.Builder builder = JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime()+1000l*60*60*4))
                .withIssuer("course_manager_project");
        for(String key : map.keySet()){
            builder.withClaim(key, map.get(key).asString());
        }
        String token = builder.sign(algorithm);
        return token;
    }

    /**
     *
     * @param token
     * @return
     * @throws IllegalArgumentException: "JWT超时"
     * @throws Exception: "JWT解码失败"
     */
    public static Map verifyToken(String token) throws IllegalArgumentException, Exception{
        DecodedJWT verify = null;
        Algorithm algorithm = Algorithm.HMAC256(KEY_STR);
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer("course_manager_project")
                .build();
        verify = jwtVerifier.verify(token);
        return verify.getClaims();
    }

}
