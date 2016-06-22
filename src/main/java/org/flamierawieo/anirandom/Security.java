package org.flamierawieo.anirandom;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Base64;
import java.util.Random;

public class Security {

    public static String randomAccessToken() {
        byte[] randomBytes = new byte[64];
        new Random().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    public static String bhash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean bcheck(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}
