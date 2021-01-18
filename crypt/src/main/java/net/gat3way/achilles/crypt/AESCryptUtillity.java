package net.gat3way.achilles.crypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import net.gat3way.achilles.crypt.entity.Vault;

public class AESCryptUtillity {
    private static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    public static final int GCM_TAG_LENGTH = 16;
    public static final int GCM_IV_LENGTH = 12;
    public static final int SALT_LENGTH = 16;

    private AESCryptUtillity() {
    }

    public static GCMParameterSpec generateIv() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return new GCMParameterSpec(GCM_TAG_LENGTH * java.lang.Byte.SIZE, iv);
    }

    public static SecretKey getKeyFromPassword(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 2048);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    public static void encryptWriteToFile(Serializable vault, String password) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

        GCMParameterSpec ivParameterSpec = AESCryptUtillity.generateIv();
        SecretKey secretKey = AESCryptUtillity.getKeyFromPassword(password, ivParameterSpec.getIV());
        // serialize vault
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(vault);
            objectOutputStream.flush();
        }

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        //Write vault encrypted to file
        try (InputStream vaultInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                FileOutputStream outputStream = new FileOutputStream("achilles.vault")) {
            //Write init vector
            outputStream.write(ivParameterSpec.getIV());

            //After first 32 bytes, start writing data
            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = vaultInputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        }
        byteArrayOutputStream.close();
    }

    public static Vault decryptReadFromFile(String password) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException,
            BadPaddingException, ClassNotFoundException, InvalidKeySpecException {

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Write vault encrypted to file
        try (FileInputStream fileInputStream = new FileInputStream("achilles.vault")) {
            //Read IV from first GCM_IV_LENGTH bytes ()12 bytes recommended
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * java.lang.Byte.SIZE,
                    fileInputStream.readNBytes(GCM_IV_LENGTH));
            SecretKey secretKey = AESCryptUtillity.getKeyFromPassword(password, gcmParameterSpec.getIV());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    byteArrayOutputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                byteArrayOutputStream.write(outputBytes);
            }
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Vault) objectInputStream.readObject();
    }

    public static byte[] getNextSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }
}