package org.flamierawieo.anirandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class Security {

    private static final byte[] salt = {(byte) 0xc2, (byte) 0x4c, (byte) 0x98, (byte) 0x56,
                                        (byte) 0x1d, (byte) 0x24, (byte) 0xea, (byte) 0x3b,
                                        (byte) 0xf3, (byte) 0x73, (byte) 0x1e, (byte) 0x6e,
                                        (byte) 0x9d, (byte) 0x22, (byte) 0x15, (byte) 0xf2};

    public String pbkdf2WithHmacSHA1(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Base64.getEncoder().encodeToString(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(password.toCharArray(), salt, 1024, 64)).getEncoded());
    }

}
