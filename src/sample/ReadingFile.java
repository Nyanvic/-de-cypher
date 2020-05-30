package sample;

import javax.crypto.*;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

class ReadingFile {
    private byte[] fileContent;
    private byte[] content;
    private String extension;
    private String control;

    public void readingNOTCriptedFile(FileInputStream in, String extension) throws IOException, NoSuchAlgorithmException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int numberOfBytedRead;
        while ((numberOfBytedRead = in.read(b)) >= 0) {
            baos.write(b, 0, numberOfBytedRead);
        }
        this.fileContent=baos.toByteArray();


        byte[] control= ControlSumm.checksum(in);
        System.out.println("control read do encrypt "+new String(control));
        byte[] extens=extension.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        outStream.write(this.fileContent);
        outStream.write(46);
        outStream.write(extens);
        outStream.write(124);
        outStream.write(control);
        this.fileContent=outStream.toByteArray();
    }
    public void readingENCriptedFile(FileInputStream in, SecretKey secretKey) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        try{
            CipherInputStream cin = new CipherInputStream(in, cipher);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int numberOfBytedRead;
            while ((numberOfBytedRead = cin.read(b)) >= 0) {
                baos.write(b, 0, numberOfBytedRead);
                System.out.println("b = " + Arrays.toString(b));
            }
            this.fileContent = baos.toByteArray();
            int iter = 0;
            int position = 0;
            for (byte Byte : this.fileContent) {
                if (Byte == 46) {
                    position = iter;
                }
                iter += 1;
            }
            byte[] content = Arrays.copyOf(this.fileContent, position - 1);
            this.content = content;
            byte[] extensionAndControl = Arrays.copyOfRange(this.fileContent, position, this.fileContent.length);
            iter = 0;
            position = 0;
            for (byte Byte : extensionAndControl) {
                if (Byte == 124) {
                    position = iter;
                    break;
                }
                iter += 1;
            }
            byte[] extension = Arrays.copyOf(extensionAndControl, position);
            this.extension = new String(extension);
            System.out.println("extension " + new String(extension));
            byte[] control = Arrays.copyOfRange(extensionAndControl, position + 1, extensionAndControl.length);
            this.control = new String(control);
            System.out.println("control posle decipher" + new String(control));
        }
        catch (Exception e){
            fileContent=null;
            content=null;
            extension=null;
            control="ОШИБКА!";
        }
    }
    byte[] fileContent(){
        return this.fileContent;
    }
    byte[] getContent(){
        return this.content;
    }
    String getExtension(){
        return this.extension;
    }
    String getControl(){
        return this.control;
    }
}




