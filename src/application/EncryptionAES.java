package application;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 * Class used to encrypt/decrypt data from the database.properties file.
 * 
 * @author Lokesh Gupta & Thomas Rader (vil203)
 */
public class EncryptionAES {
	private final static String keyString = "aaah!";
	private static SecretKeySpec secretKey;
    private static byte[] key;
    
    /**
     * Sets the private key to the keyString passcode.
     * 
     * @param myKey				Name of the key passcode
     */
    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Encrypts data using AES encryption.
     * 
     * @param strToEncrypt		String you want to encrypt
     * @return					Returns an AES encrypted string
     */
    public static String encrypt(String strToEncrypt) {
        try {
            setKey(keyString);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
    
    /**
     * Decrypts data using AES encryption.
     * 
     * @param strToDecrypt		String you want to decrypt
     * @return					Returns a String in UTF-8 format
     */
    public static String decrypt(String strToDecrypt) {
        try {
            setKey(keyString);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
