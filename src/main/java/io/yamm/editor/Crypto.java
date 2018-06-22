package io.yamm.editor;

import org.encryptor4j.Encryptor;
import org.encryptor4j.factory.KeyFactory;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Arrays;

public class Crypto {
    // TODO: key management

    public static byte[] encrypt(byte[] messsage) throws GeneralSecurityException {
        Key secretKey = KeyFactory.AES.randomKey(256);
        Encryptor encryptor = new Encryptor(secretKey, "AES/GCM/NoPadding", 16, 128);
        return encryptor.encrypt(messsage);
    }

    public static void printEncrypted(String message) {
        Key secretKey = KeyFactory.AES.randomKey(256);
        Encryptor encryptor = new Encryptor(secretKey, "AES/GCM/NoPadding", 16, 128);
        try {
            byte[] encrypted = encryptor.encrypt(message.getBytes());
            System.out.println(Arrays.toString(encrypted));
            byte[] decrypted = encryptor.decrypt(encrypted);
            if (Arrays.equals(decrypted, message.getBytes())) {
                System.out.println("Decryption successful!");
            } else {
                System.out.println("Decryption failed! Original and decrypted messages follows...");
                System.out.println(Arrays.toString(message.getBytes()));
                System.out.println(Arrays.toString(decrypted));
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
