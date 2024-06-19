package com.later.horizon.common.helper;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class EncryptAESHelper {

    private static final String Algorithm = "AES";

    private static final int KeySize = 16; // 128位密钥

    public static SecretKeySpec initSecretKey(String passwordSeed) {
        return new SecretKeySpec(Arrays.copyOf(passwordSeed.getBytes(StandardCharsets.UTF_8), EncryptAESHelper.KeySize), EncryptAESHelper.Algorithm);
    }

    public static String encrypt(String data, SecretKeySpec spec) {
        try {
            Cipher cipher = Cipher.getInstance(EncryptAESHelper.Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, spec);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception ignored) {
            throw new BusinessException(Constants.ProveStatus.AES_SecretKey_Encrypt_Failed);
        }
    }

    public static String decrypt(String encryptedData, SecretKeySpec spec) {
        try {
            Cipher cipher = Cipher.getInstance(EncryptAESHelper.Algorithm);
            cipher.init(Cipher.DECRYPT_MODE, spec);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception ignored) {
            throw new BusinessException(Constants.ProveStatus.AES_SecretKey_Decrypt_Failed);
        }
    }

    public static void main(String[] args) {
        String secret = "root"; // 替换为实际的密钥
        String plaintext = "user/exp/1";
        String encryptedText = EncryptAESHelper.encrypt(plaintext, EncryptAESHelper.initSecretKey(secret));
        System.out.println("加密后的数据：" + encryptedText);

        String decryptedText = EncryptAESHelper.decrypt(encryptedText, EncryptAESHelper.initSecretKey(secret));
        System.out.println("解密后的数据：" + decryptedText);
    }

}
