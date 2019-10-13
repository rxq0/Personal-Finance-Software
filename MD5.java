package Login.Util;

import java.security.MessageDigest;

public class MD5 {
    static byte[] digest;

    public MD5() {
    }

    public static String getHash(String stringToHash) {
        // HexString is used to store the hash
        StringBuffer hexString = new StringBuffer();
        // Get a byte array of the String to be hashed
        byte[] buffer = stringToHash.getBytes();
        try {
            //get and instance of the MD5 MessageDigest
            MessageDigest alg = MessageDigest.getInstance("MD5");
            alg.reset();
            alg.update(buffer);
            //Create the MD5 Hash
            digest = alg.digest();
            //Now we need to pull out each char of the byte array into the StringBuffer
        } catch (Exception e) {
        }
        // Return the hex hash value as a String
        return asHex(digest);
    }

    private static String asHex(byte hash[]) {
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;
        for (i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10)
                buf.append("0");
            buf.append(Long.toString((int) hash[i] & 0xff, 16));
        }
        return buf.toString();
    }
}
