/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author SURAJIT KUNDU
 */

public class Crypto {
    private String enKey="";
    public static String encrypt(String strToEncrypt, String key) throws Exception {
        try {

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            final String encryptedString = new String(Base64.encodeBase64(cipher.doFinal(strToEncrypt.getBytes())));
            return encryptedString;
        } catch (Exception e) {
            throw e;
        }
    }

    public static String decrypt(String strToDecrypt, String key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt.getBytes())));
            return decryptedString;
        } catch (Exception e) {
            throw e;
        }
    }  
    public String encryptString(String data) throws Exception {
;
        String encData = "";
        encData = Crypto.encrypt(data, enKey);
        return encData;
    } 
    public String decryptString(String encdata) throws Exception {
        String decStr="";
        decStr = Crypto.decrypt(encdata, enKey);
        return decStr;
        
        
    }

    
    
    public Crypto() {
        GetConfig gc = new GetConfig();
        SystemLog slog = new SystemLog();
        try{
            enKey = gc.encryptionKey;  
            //getIDMAP("select * from patientid_map",);
            //System.out.println(encryptString("CHAVI24012019.0"));
            //System.out.println(encryptString("CHAVI24012019")+encryptString(".")+encryptString("0"));
            //System.out.println(encrypt("1234","CHAVI-RO@S.KUNDU"));
            //System.out.println(decryptString("17dwsUiNttBxLmb78fSDYg=="));
             //System.out.println("18/002038: "+ decryptString("5GdhCILRM7zd0XPWxTrCaK3z5D2aq7v7VwqiwtiNaoM="));
             //System.out.println("16/002288: "+ decryptString("5GdhCILRM7zd0XPWxTrCaPUXIuc+AciGFnZ4X2GKGHg="));
               System.out.println("18/002277 "+decryptString("T7gN3J/juJaRkEDmG+l6CHr5AO4xVkOvOlnfIsxy4ko="));
              // System.out.println("18/002404 "+encryptString("2013032708202000004"));
                
                //System.out.println("8HmWSf+XY/0ea5B4WWwUYa3z5D2aq7v7VwqiwtiNaoM= : " +decryptString(" 8HmWSf+XY/0ea5B4WWwUYa3z5D2aq7v7VwqiwtiNaoM="));
                //System.out.println("2013030303202000000 :" +encryptString("2013030303202000000"));
//              System.out.println("2013030507202100004 : "+encryptString("2013030507202100004"));
//              System.out.println("2013030707202100001 : "+encryptString("2013030707202100001"));
//              System.out.println("2013030707202100002 : "+encryptString("2013030707202100002"));
//              System.out.println("2013030707202100003 : "+encryptString("2013030707202100003"));
              // System.out.println("Pass: "+decryptString("xixjm4WhSMFQVBkaVl59oA=="));
             
        }catch(Exception ex){
            ex.printStackTrace(System.out);
        }
    }
    /* 
    public static void main(String args[]) {
         new ImedixCrypto();
     }
    */
}
