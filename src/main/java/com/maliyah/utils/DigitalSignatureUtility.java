package com.maliyah.utils;
import java.security.*;
import java.util.Scanner;

public class DigitalSignatureUtility {

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static final String RSA = "SHA256withRSA";

    public static byte[] generateDigitalSignature(byte[] plainText, PrivateKey privateKey) throws Exception  {
        Signature signature = Signature.getInstance(RSA);
        signature.initSign(privateKey);
        signature.update(plainText);
        return signature.sign();

    }
    
    public static boolean verifyDigitalSignature(byte[] plainText, byte[] digitalSignature, PublicKey publicKey)
            throws Exception {
        Signature signature = Signature.getInstance(RSA);
        signature.initVerify(publicKey);
        signature.update(plainText);
        return signature.verify(digitalSignature);
    }

    public static void main(String[] args) throws Exception {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keypair = keyPairGenerator.generateKeyPair();

        Scanner message = new Scanner(System.in);
        System.out.print("Enter the message you want to encrypt using RSA: ");
        String plainText = message.nextLine();
        byte[] bytes = plainText.getBytes();
        message.close();

        byte[] digitalSignature = generateDigitalSignature(bytes, keypair.getPrivate());

        System.out.println("Signature Value:\n " + bytesToHex(digitalSignature));
        System.out.println("Verification: " + verifyDigitalSignature(bytes, digitalSignature, keypair.getPublic()));

    }
}