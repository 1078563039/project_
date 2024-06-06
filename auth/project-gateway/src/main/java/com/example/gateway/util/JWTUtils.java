package com.example.gateway.util;

import com.example.common.base.result.exception.BaseException;
import com.example.gateway.result.GatewayResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.PublicKey;

@Slf4j
public class JWTUtils {

    /**
     * 请求头中的 token的开始
     */
    private static final String AUTH_HEADER = "Bearer ";

    /**
     * 认证服务器许可我们的网关的clientId(需要在oauth_client_details表中配置)
     */
    private static final String clientId = "dzk-project";

    /**
     * 认证服务器许可我们的网关的client_secret(需要在oauth_client_details表中配置)
     */
    private static final String clientSecret = "123456";

    /**
     * 认证服务器暴露的获取token_key的地址
     */
    private static final String AUTH_TOKEN_KEY_URL = "http://project-auth/auth/oauth/token_key";

    public static Claims validateJwtToken(String authHeader, PublicKey publicKey) {
        String token =null ;
        try{
            //解析token获取claims
            token = StringUtils.substringAfter(authHeader, AUTH_HEADER);
            Jwt<JwsHeader, Claims> parseClaimsJwt = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            Claims claims = parseClaimsJwt.getBody();

            return claims;
        }catch(Exception e){
            log.error("校验token异常:{},异常信息:{}",token,e.getMessage());
            throw new BaseException(GatewayResult.TOKEN_INVALID);
        }
    }

    /**
     * 获取publicKey
     * @param restTemplate
     * @return
     */
//    @SneakyThrows
//    public static PublicKey getPublicKey(RestTemplate restTemplate) {
//        /*获取tokenKey*/
//        String tokenKey = getTokenKeyRemoteCall(restTemplate);
//        log.info("远程服务器{}获取到的tokenKey为{}", AUTH_TOKEN_KEY_URL, tokenKey);
//
//        try{
//            //把获取的公钥开头和结尾替换掉
//            String dealTokenKey =tokenKey.replaceAll("\\-*BEGIN PUBLIC KEY\\-*", "").replaceAll("\\-*END PUBLIC KEY\\-*", "").trim();
//            java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(dealTokenKey));
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
//            log.info("生成公钥:{}",publicKey);
//            return publicKey;
//        }catch (Exception e) {
//            log.info("生成公钥异常:{}",e.getMessage());
//            throw new GateWayException(ExceptionEnum.TokenKeyParseFail.getCode(), ExceptionEnum.TokenKeyParseFail.getMsg());
//        }
//    }
//
//    /**
//     * 远程调用获取tokenKey
//     * @param restTemplate
//     * @return 返回tokenKey
//     */
//    @SneakyThrows
//    private static String getTokenKeyRemoteCall(RestTemplate restTemplate) {
//        /*设置请求头*/
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        httpHeaders.setBasicAuth(clientId, clientSecret);
//
//        HttpEntity<MultiValueMap<String, String>> objectHttpEntity = new HttpEntity<>(null, httpHeaders);
//
//        /*远程调用*/
//        try {
//            ResponseEntity<Map> response = restTemplate.exchange(AUTH_TOKEN_KEY_URL, HttpMethod.GET, objectHttpEntity, Map.class);
//            return response.getBody().get("value").toString();
//        }catch (Exception exception){
//            log.info("获取远程服务器{}tokenKey失败,失败原因{}", AUTH_TOKEN_KEY_URL, exception.getMessage());
//            log.info("获取失败，请求重新获取");
//            Thread.sleep(1000);
//            return getTokenKeyRemoteCall(restTemplate);
//            //throw new GateWayException(ExceptionEnum.TokenKeyGetFail.getCode(), ExceptionEnum.TokenKeyGetFail.getMsg());
//        }
//    }
}
