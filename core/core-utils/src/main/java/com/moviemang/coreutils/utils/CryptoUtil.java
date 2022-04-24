//package com.moviemang.coreutils.utils;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.lang3.StringUtils;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.Key;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.Security;
//
//public class CryptoUtil {
//    private static final String AES256_SECRET_KEY = "moviemang_aes256";
//
//    public static String sha256(String message) {
//        try{
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(message.getBytes("UTF-8"));
//            StringBuffer hexString = new StringBuffer();
//
//            for (int i = 0; i < hash.length; i++) {
//                String hex = Integer.toHexString(0xff & hash[i]);
//                if(hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//
//            return hexString.toString();
//        } catch(Exception ex){
//            throw new RuntimeException(ex);
//        }
//    }
//
//    public static String base64Encode(String base) {
//        return new String(Base64.encodeBase64(base.getBytes()));
//    }
//    public static String base64Decode(String base) {
//        return new String(Base64.decodeBase64(base.getBytes()));
//    }
//
//    /**
//     * 복호화(복합알고리즘)
//     * @param strCipherText
//     * @return
//     * @throws Exception
//     */
//    public static String decryptComplex(String strCipherText) throws Exception {
//
//        Key key = new SecretKeySpec(KEY_BYTES, "AES");
//
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//
//        Cipher out = Cipher.getInstance("AES/CBC/ZeroBytePadding", "BC");
//        out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV_BYTES));
//        byte[] dec = out.doFinal(decodeBase64(strCipherText.getBytes()));
//
//        String strBase64decoded = new String(dec, "UTF-8");
//        return strBase64decoded;
//    }
//
//    public static String decryptAES256(String str)  throws Exception{
//        Key keySpec = new SecretKeySpec()
//        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
//
//        byte[] byteStr = Base64.decodeBase64(str.getBytes());
//
//        return new String(c.doFinal(byteStr),"UTF-8");
//    }
//
//    public static byte[] encodeBase64(byte[] binaryData) {
//
//        //encoding  byte array into base 64
//        byte[] encoded = Base64.encodeBase64(binaryData);
//        return encoded;
//    }
//    public static byte[] decodeBase64(byte[] base64Data) {
//        byte[] decoded = Base64.decodeBase64(base64Data);
//        return decoded;
//    }
//
//}
