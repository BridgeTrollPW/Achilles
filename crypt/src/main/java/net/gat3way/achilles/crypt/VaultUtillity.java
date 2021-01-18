package net.gat3way.achilles.crypt;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.gat3way.achilles.crypt.entity.Vault;

public class VaultUtillity {
    private VaultUtillity() {
    }

    public static void create(String password, Vault openVault) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            InvalidKeySpecException, IOException {
        AESCryptUtillity.encryptWriteToFile(openVault, password);
    }

    public static Vault open(String password) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            ClassNotFoundException, InvalidKeySpecException, IOException {
        return AESCryptUtillity.decryptReadFromFile(password);
    }
}
