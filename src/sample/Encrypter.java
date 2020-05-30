package sample;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

class Encrypter {

    private SecretKey secretKey=null;

    void KeyGen(String myKey) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException {
        MessageDigest sha = null;
        byte[] key;
        byte[] origKey = myKey.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(origKey));
        sha = MessageDigest.getInstance("SHA-256");
        origKey = sha.digest(origKey);
        origKey = Arrays.copyOf(origKey, 32);
        this.secretKey= new SecretKeySpec(origKey, "AES");
    }
    public SecretKey getSecretKey(){
        return this.secretKey;
    }
}
