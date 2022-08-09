package org.lr.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
public class RSAUtil {

    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static final String ALGORITHM_NAME = "RSA";
    private static final String MD5_RSA = "MD5withRSA";

    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJgcH5v0UTPu0w6ZxzEJA01w3kuHGAbeM9sP811nqmFJRP3lrGGPBdAbeiZN1umMKOvL3q0tb0r7Ep0VyiGSqOsGxa3hL2ZPwBe/hkYm2c0tdoW9sZB/mbsYtXKGwTfkGeyaQvZINDRLrMZf2VrBqL4g8gxCX+XvY9rk7M715NDlAgMBAAECgYAwk/q8UcCFN2aMKSxmSpyVZNB5ioFv4BLriSKN2Aef3kh8VZdB6O1QUxYlmYJjP/HprEhHQlViuuBs7oKJSk9LkY15xcSf2cgVRij8bE7Xn3MHLV1CqS9cpK8sV7AnMXxRKKuXcpYN7y9UDesY6jDzuPCbO5yYVd08DnefgtWpGQJBANTHl/Aa/ZLzLP+/Vzhgl14r7Us4xCO9myYO80n25ho0qtV8rWQ1dqlL7icv2dkFfSTm5b+Pm8JSBmjRm0Dw0YsCQQC3Ab0m8Pe3P49OK4pZs5pfGTKz7Zs7kbABxTvbcq8eE7QLm9dCmP6wekt/cUdO6sI+5UGD0ZY/sNyV5MXwI1VPAkEAzzimtvaCN6fODi/5MHWZVOQw0A4zrp8MRw9OTp0+UQ0zxoAKmUocCxpgkBdg8KMQ0mxwGZQxXBjoFxLzQVaGqwJAO66wfkulrbhnxumCNCDTnmH8XBuIPZQUn28wRzAOljN8yzBmskxs+ybXClfqFn2PHa3sgM3QPzMxrRyvxNHxFwJBAInROtk7uC091LsJMx+YK3hEVNSxlTTp8k1784YZtAznREqnJQTBNZxLqToEQ3UHAXyn78akSwfPChuWNcOJw60=";
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYHB+b9FEz7tMOmccxCQNNcN5LhxgG3jPbD/NdZ6phSUT95axhjwXQG3omTdbpjCjry96tLW9K+xKdFcohkqjrBsWt4S9mT8AXv4ZGJtnNLXaFvbGQf5m7GLVyhsE35BnsmkL2SDQ0S6zGX9lawai+IPIMQl/l72Pa5OzO9eTQ5QIDAQAB";

    private static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_NAME);
        generator.initialize(1024);
        return generator.generateKeyPair();
    }
    /**
     * 获取base64加密后密钥对
     */
    private static HashMap<String, String> getKeyPairMap() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_NAME);
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
        String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("privateKey", privateKey);
        keyMap.put("publicKey", publicKey);
        return keyMap;
    }
    /**
     * 获取公钥
     *
     * @param publicKey base64加密的公钥字符串
     */
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
        return keyFactory.generatePublic(keySpec);
    }
    /**
     * RSA加密
     *
     * @param data      待加密数据
     */
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(PUBLIC_KEY));
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64(encryptedData));
    }
    /**
     * 获取私钥
     *
     * @param privateKey base64加密的私钥字符串
     */
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
        return keyFactory.generatePrivate(keySpec);
    }
    /**
     * RSA解密
     *
     * @param data       待解密数据
     */
    public static String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(PRIVATE_KEY));
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
    /**
     * 签名
     *
     * @param data       待签名数据
     */
    public static String sign(String data) throws Exception {
        byte[] keyBytes = getPrivateKey(PRIVATE_KEY).getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(MD5_RSA);
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param sign      签名
     */
    public static boolean verify(String srcData, String sign) throws Exception {
        byte[] keyBytes = getPublicKey(PUBLIC_KEY).getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(MD5_RSA);
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }
    public static void main(String[] args) throws Exception {
//        try {
//            // RSA加密
//            //String data = "123456";
//            String data = "uhweofpewhafefewohfwehfiwoehf0ewhf20fhneof2eojpi2ef2peifnpe2fneiofufnewfnceownfowenfwefnnvlwevnlwevnaowevbweovbnoaevboawev";
//            System.out.println(data.length());
//            String encryptData = encrypt(data);
//            System.out.println("加密后内容 => " + encryptData + "\n");
//            // RSA解密
//            String decryptData = decrypt(encryptData);
//            System.out.println("解密后内容 => " + decryptData + "\n");
//
//
//            // RSA签名
//            String sign = sign(data);
//            // RSA验签
//            boolean result = verify(data, sign);
//            System.out.println("验签结果 => " + result + "\n");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("RSA加解密异常");
//        }
        String str = "XKDE9SPSn0wHsJ4a0hJBVmoDFuqiy5EK09lBK4LyQQkNZahOq1Gc5RGBFmPVI9+r2aDxH6fQjySIjlF5YxCVioZrUhnsYG6qBg8SBcXXzwcOudqBlHNS1JcP7TZ+46PqF4QsAeumbFOiJw6MNs7E9t2B55nfzg9Rllj/BIkq2x0=";
        String decryptData = decrypt(str);
        System.out.println(decryptData);
    }
}