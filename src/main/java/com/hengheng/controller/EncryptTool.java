package com.hengheng.controller;

import org.jasypt.util.text.AES256TextEncryptor;

public class EncryptTool {
    public static void main(String[] args) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword("mySecretKey"); // 你的密钥
        String encrypted = textEncryptor.encrypt("333333");
        System.out.println("ENC(" + encrypted + ")");
    }
}
