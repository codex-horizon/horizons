package com.later.horizon.common.helper;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 对称加密算法（AES、DES、3DES）是指加密和解密采用相同的密钥，是可逆的（即可解密）。
 * **优点：**加密速度快。
 * **缺点：**密钥的传递和保存是一个问题，参与加密和解密的双方使用的密钥是一样的，这样密钥就很容易泄露。
 * ---------------------------------------------------------------------------------------------------------------------
 * 非对称加密算法（RSA、DSA、ECC）是指加密和解密采用不同的密钥（公钥和私钥），因此非对称加密也叫公钥加密，是可逆的（即可解密）。
 * 公钥密码体制根据其所依据的难题一般分为三类：大素数分解问题类、离散对数问题类、椭圆曲线类。
 * RSA加密算法是基于一个十分简单的数论事实：将两个大素数相乘十分容易，但是想要对其乘积进行因式分解极其困难，因此可以将乘积公开作为加密密钥。
 * 虽然RSA的安全性一直未能得到理论上的证明，但它经历了各种攻击至今未被完全攻破。
 * **优点：**加密和解密的密钥不一致，公钥是可以公开的，只需保证私钥不被泄露即可，这样就密钥的传递变的简单很多，从而降低了被破解的几率。
 * **缺点：**加密速度慢
 * RSA加密算法既可以用来做数据加密，也可以用来数字签名。
 * –数据加密过程：发送者用公钥加密，接收者用私钥解密（只有拥有私钥的接收者才能解读加密的内容）
 * –数字签名过程：甲方用私钥加密，乙方用公钥解密（乙方解密成功说明就是甲方加的密，甲方就不可以抵赖）
 */
@Slf4j
public class EncryptRSAHelper {

    private final static Map<String, String> SecretKeyCaches = new HashMap<>();

    public static String getPublicKey(String passwordSeed) {
        return EncryptRSAHelper.initSecretKey(passwordSeed);
    }

    public static String getPrivateKey(String publicKey) {
        if (SecretKeyCaches.containsKey(publicKey)) {
            return SecretKeyCaches.get(publicKey);
        }
        throw new BusinessException(Constants.ProveProveState.RSA_PublicKey_Expire);
    }

    public static void removeKey(String publicKey) {
        SecretKeyCaches.remove(publicKey);
    }

    private static String initSecretKey(String passwordSeed) {
        try {
            KeyPair keyPair = EncryptRSAHelper.generatorKeyPair(passwordSeed);
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            String publicKeyStr = new String(Base64.getEncoder().encode(publicKey.getEncoded()), StandardCharsets.UTF_8);
            SecretKeyCaches.put(publicKeyStr, new String(Base64.getEncoder().encode(privateKey.getEncoded()), StandardCharsets.UTF_8));
            return publicKeyStr;
        } catch (Exception ignore) {
            throw new BusinessException(Constants.ProveProveState.RSA_SecretKey_Initialize_Failed);
        }
    }

    public static KeyPair generatorKeyPair(String passwordSeed) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        random.setSeed(passwordSeed.getBytes(StandardCharsets.UTF_8));
        keyPairGenerator.initialize(1024, random);
        return keyPairGenerator.generateKeyPair();
    }

    public static String encrypt(String str, String publicKey) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes(StandardCharsets.UTF_8)));
            RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            return new String(Base64.getEncoder().encode(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        } catch (Exception ignore) {
            throw new BusinessException(Constants.ProveProveState.RSA_PublicKey_Encrypt_Failed);
        }
    }

    public static String decrypt(String str, String publicKey, boolean removeKeyPair) {
        try {
            String privateKey = SecretKeyCaches.get(publicKey);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey.getBytes(StandardCharsets.UTF_8)));
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            if (removeKeyPair) {
                EncryptRSAHelper.removeKey(publicKey);
            }
            return new String(cipher.doFinal(Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        } catch (Exception ignore) {
            throw new BusinessException(Constants.ProveProveState.RSA_SecretKey_Decrypt_Failed);
        }
    }

    public static void main(String[] args) {
        String toEncryptedStr = "123456";
        String publicKey = EncryptRSAHelper.getPublicKey("root");
        String encrypt = EncryptRSAHelper.encrypt(toEncryptedStr, publicKey);
        String decrypt = EncryptRSAHelper.decrypt(encrypt, publicKey, Boolean.FALSE);
        // String decrypt = EncryptRSAHelper.decrypt(encrypt, publicKey, Boolean.TRUE);
        log.info("publicKey[{}]", publicKey);
        log.info("encrypt[{}]", encrypt);
        log.info("decrypt[{}]", decrypt);
    }

}
