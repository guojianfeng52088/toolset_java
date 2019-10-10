package security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description jwt工具类
 * @Author pc
 * @Date 2019/10/05 16:08
 */
public class JwtUtil {
    /**
     * APP登录Token的生成和解析
     */
    public static final String KEY = "TxOQdaQt7oZ3PgdyT8UvRNQ4UEJJPJwW";
    /**
     * token秘钥，请勿泄露，请勿随便修改
     */
    public static final String SECRET = "4GY110tuZX6$xAMWxgF#fR@1g%3FFo#4";
    /**
     * token 过期时间: 30分钟
     */
    public static final int calendarField = Calendar.MINUTE;
    public static final int calendarInterval = 30 * 60 * 1000;


    /**
      * @Description jwt生成token（JWT构成: header, payload, signature）
      * @Author guojianfeng
      * @Date 2019/10/05 16:12
      * @param userId 登录成功后的user_id,不能为空
      * @Return
      */
    public static String createToken(Integer userId) throws Exception {
        Date iatDate = new Date();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", KEY) // payload
                .withClaim("aud", "kwd")
                .withClaim("userId", userId)
                .withIssuedAt(iatDate) // sign time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    public static String createToken(Integer userId, String key, String secret) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", key) // payload
                .withClaim("aud", "kwd")
                .withClaim("userId", userId)
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(secret)); // signature

        return token;
    }


    /**
      * @Description 校验token
      * @Author guojianfeng
      * @Date 2019/10/05 16:19
      * @param token
      * @Return
      */
    public static String verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            e.getMessage();
            System.out.println("Message:" + e.getMessage());
            //e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
            return e.getMessage();
        }
        return null;
    }


    /**
      * @Description 根据Token获取user_id
      * @Author guojianfeng
      * @Date 2019/10/05 16:19
      * @param token
      * @Return
      */
    public static String getUID(String token) {

        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
            return e.getMessage();
        }

        Map<String, Claim> claims = jwt.getClaims();
        if (claims == null) {
            return null;
        }
        Claim user_id_claim = claims.get("userId");
        if (null == user_id_claim) {
            // token 校验失败, 抛出Token验证非法异常
            return null;
        }
        return user_id_claim.asString();
    }

}
