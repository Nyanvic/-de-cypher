package sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class ControlSumm {

    public static byte[] checksum(FileInputStream in) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[]buf = new byte[1024];
        byte[] dataBytes = new byte[1024];
        int bytesRead;
        while((bytesRead = in.read(dataBytes)) > 0) {
            md.update(dataBytes, 0, bytesRead);
        }
        byte[] mdBytes = md.digest();
        //Пepeвoдиm koнтpoльнyю cymmy в видe maccивa бaйт в
        //шecтнaдцaтepичнoe пpeдcтaвлeниe
        StringBuilder sb = new StringBuilder();
        for (byte mdByte : mdBytes) {
            sb.append(Integer.toString((mdByte & 0xff) + 0x100, 16)
                    .substring(1));
        }
        System.out.println("Koнтpoльнaя cymma: " + sb.toString());
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
