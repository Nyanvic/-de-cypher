package sample;

import javax.crypto.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;

class WritingFile {
    public void writingENcryptedFile(byte[] fileContent, String fileName, SecretKey SecretKey, String path) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        System.out.println(SecretKey);
        cipher.init(Cipher.ENCRYPT_MODE, SecretKey);
        String fileName1 = path + fileName + ".cg";
        System.out.println(fileName1);
        FileOutputStream fs = new FileOutputStream(fileName1);
        CipherOutputStream out = new CipherOutputStream(fs, cipher);
        out.write(fileContent);
        out.flush();
        out.close();
    }

    void writingDEcryptedFile(byte[] fileContent, String fileName, String path, String extenion) throws IOException {
        String fileName1 = path + fileName + extenion;
        System.out.println(fileName1);
        FileOutputStream out = new FileOutputStream(fileName1);
        for (byte b : fileContent) {
            out.write(b);
        }
        out.flush();
        out.close();
    }
}
