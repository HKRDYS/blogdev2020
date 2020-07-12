package com.example.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 加密工具
 */
public class PasswordUtil {
    public static String  enCodeByBCrypt(String oldPass){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(oldPass);
    }
    public static void main(String args[]){
        System.out.println(enCodeByBCrypt("123456"));
    }
}
