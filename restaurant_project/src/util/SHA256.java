package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    private String string;

    public SHA256(String string) {
        this.string = string;
    }

    public String getSHA256(){
        String stringSHA256 = null;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] encodehash = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
//        System.out.println(encodehash);
        stringSHA256 = bytesToHex(encodehash);
        return stringSHA256;
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public String toString() {
        return string;
    }


}
