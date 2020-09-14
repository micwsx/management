package com.micwsx.project.advertise.utility;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class CryptoUtil {

    public enum Algrithom {SHA, MD5}

    public static String digest(String message, Algrithom algrithom) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algrithom.name());
            byte[] dataByte = message.getBytes("UTF-8");
            byte[] shaBytes = digest.digest(dataByte);
            return Hex.encodeHexString(shaBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String hmacSHA256(String key, String message) {
        Mac hmacSHA256 = null;
        try {
            hmacSHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            hmacSHA256.init(keySpec);
            byte[] dataBytes = message.getBytes();
            byte[] cipher = hmacSHA256.doFinal(dataBytes);
            String result = Hex.encodeHexString(cipher);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean hmacSHA256Verify(String key, String message, String signature) {
        String calculate = hmacSHA256(key, message);
        return calculate.equals(signature);
    }


    public static void main(String[] args) {
        String message = "8ee4e922c39446ac9ee66095a4a4b475**Test*100*USD";
        String key = "sS(28nR=?9tKCy!47Ge*)j3P6_Mo]Zf5";
        String signStr = hmacSHA256(key, message);
        System.out.println(signStr);
        System.out.println(hmacSHA256Verify(key, message, signStr));
    }
}
