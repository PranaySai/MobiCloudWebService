package com.asu.cloud.utils;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class FileEncryptDecrypt {
 
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DES_ENCRYPTION_SCHEME = "DES";
    private KeySpec myKeySpec;
    private SecretKeyFactory mySecretKeyFactory;
    private Cipher cipher;
    byte[] keyAsBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;
 
      
    public FileEncryptDecrypt(String privateKey) throws Exception
    {
        myEncryptionKey = privateKey;
        myEncryptionScheme = DES_ENCRYPTION_SCHEME;
        keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        myKeySpec = new DESKeySpec(keyAsBytes);
        mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = mySecretKeyFactory.generateSecret(myKeySpec);
    }
 
    /**
     * Method To Encrypt The String
     */
    public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = Base64.encode(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
    /**
     * Method To Decrypt An Ecrypted String
     */
    public byte[] decrypt(String encryptedString) {
        byte[] decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decode(encryptedString);
            decryptedText = cipher.doFinal(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
}
