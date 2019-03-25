package com.harrymark.wechatapp.frientcommon.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Wechat aes加密工具类
 * Created by haoweima on 2019/3/22.
 */
public class WechatAESUtils {

    /**
     * 微信小程序 开放数据解密
     * AES解密（Base64）
     * @param encryptedData 已加密的数据
     * @param sessionKey    解密密钥
     * @param iv            IV偏移量
     * @return
     * @throws Exception
     */
    public static String decryptForWeChatApplet(String encryptedData, String sessionKey, String iv) throws Exception {
        byte[] decryptBytes = Base64.decodeBase64(encryptedData);
        byte[] keyBytes = Base64.decodeBase64(sessionKey);
        byte[] ivBytes = Base64.decodeBase64(iv);

        return new String(decryptByAesBytes(decryptBytes, keyBytes, ivBytes));
    }

    /**
     * AES解密
     * @param decryptedBytes    待解密的字节数组
     * @param keyBytes          解密密钥字节数组
     * @param ivBytes           IV初始化向量字节数组
     * @return
     * @throws Exception
     */
    private static byte[] decryptByAesBytes(byte[] decryptedBytes, byte[] keyBytes, byte[] ivBytes) throws Exception {
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] outputBytes = cipher.doFinal(decryptedBytes);
        return outputBytes;
    }
}
