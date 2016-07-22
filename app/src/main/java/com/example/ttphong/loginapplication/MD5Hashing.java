package com.example.ttphong.loginapplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ttphong on 7/22/2016.
 */
public class MD5Hashing {
    public static String computeHash(String password)
    {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer MD5Hash = new StringBuffer();

            for (int i = 0; i < messageDigest.length; i++)
            {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }
            return new String(MD5Hash);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
