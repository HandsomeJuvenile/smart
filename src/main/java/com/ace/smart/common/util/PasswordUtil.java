package com.ace.smart.common.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordUtil {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static final String algorithmName = "md5";
    private static final int hashIterations = 2;

    /**
     * 使用盐的不同  即时密码相同 但是加密后不相同
     * @param id
     * @param password
     */
    public static String encryptPassword(String id,String password){
        ByteSource credentialsSalt = ByteSource.Util.bytes(id);
        String newPassword = new SimpleHash(algorithmName,password,ByteSource.Util.bytes(id),hashIterations).toHex();
        return newPassword;
    }

    public static void main(String[] args) {
        System.out.println(PasswordUtil.encryptPassword("152479894562501","111111"));
    }

}
