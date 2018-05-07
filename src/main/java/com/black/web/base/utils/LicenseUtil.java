package com.black.web.base.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class LicenseUtil {
	
	/*校验证书入口*/
    public static String license(String license_______,byte[] license________,String license_________) throws Exception {
    	//raysdata.license.key
    	if(license_______.indexOf(new String(new byte[] {114, 97, 121, 115, 100, 97, 116, 97, 46, 108, 105, 99, 101, 110, 115, 101, 46, 107, 101, 121}))>0){
    		license_________ = license_________==null?"./xxxxxxx.jar":license_________;
    		license________ = license_______(license________.toString(), license_______(license_________)).getBytes();
    	}
    	Cipher license______ = Cipher.getInstance(new String(new byte[] {82, 83, 65}));
    	license______.init(Cipher.DECRYPT_MODE, 
    			KeyFactory.getInstance(new String(new byte[] {82, 83, 65})).generatePublic(
    			new X509EncodedKeySpec(
    					Base64.getDecoder().decode(FileUtil.readerPath(license_______).getBytes()))));
    	license________ = license______.doFinal(license________);
    	return new String(license________);
    }
    /*校验证书*/
    private static String license_______(String license_______, String license________) throws Exception {
        Cipher license______ = Cipher.getInstance(new String(new byte[] {68, 69, 83, 47, 69, 67, 66, 47, 78, 111, 80, 97, 100, 100, 105, 110, 103}));
        license______.init(Cipher.DECRYPT_MODE, license_________(license________));
        return new String(license______.doFinal(Base64.getDecoder().decode(license_______)));
    }
    /*校验证书*/
    private static SecretKey license_________(String license_______) throws Exception {
        return SecretKeyFactory.getInstance(new String(new byte[] {68, 69, 83})).generateSecret(new DESKeySpec(license________(license_______)));
    }
    /*校验证书*/
    private static byte[] license________(String license_______) {
        byte[] license______ = new byte[license_______.length() / 2];
        int license________ = 0;
        for (int license_________ = 0; license_________ < license______.length; license_________++) {
            license______[license_________] = (byte) (
            		(license_______(license_______.charAt(license________++)) << 4) | 
            		license_______(license_______.charAt(license________++)));
        }
        return license______;
    }
    /*校验证书*/
    private static int license_______(char license_______) {
        if (license_______ >= 'a') return (license_______ - 'a' + 10) & 0x0f;
        if (license_______ >= 'A') return (license_______ - 'A' + 10) & 0x0f;
        return (license_______ - '0') & 0x0f;
    }
    /*校验证书*/
    private static String license_______(String license_______) throws IOException {
		FileInputStream license________ = null;
		DigestInputStream license_________ = null;
		try {
			MessageDigest license______ = MessageDigest.getInstance(new String(new byte[] {77, 68, 53}));
			license________ = new FileInputStream(license_______);
			license_________ = new DigestInputStream(license________, license______);
			while (license_________.read(new byte[256*1024]) > 0);
			license______ = license_________.getMessageDigest();
			return license_______(license______.digest());
		} catch (NoSuchAlgorithmException license______) {
			return null;
		} finally {
			try {
				license_________.close();
				license________.close();
			} catch (Exception license______) {}
		}
	}
    /*校验证书*/
    private static String license_______(byte[] license_______) {
        char[] license______ = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };  
        char[] license________ =new char[license_______.length * 2];  
        int license_____ = 0;
        for (byte license_________ : license_______) {
           license________[license_____++] = license______[license_________>>> 4 & 0xf];  
           license________[license_____++] = license______[license_________& 0xf];  
		}
        return new String(license________);
    }
    /*校验证书*/
}
