package com.xrm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSumFactory {

    private final MessageDigest md;

    public HashSumFactory() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD5");
    }

    public String getMD5Checksum(byte[] bytes) {
        String result = "";

        byte[] digest = md.digest(bytes);
        for (int i = 0; i < digest.length; i++) {
            result += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
        }

        return result;
    }
}
