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
              // System.out.println("18/002277 "+decryptString("WuZMwE6hy9tauQxAp0VPhmgn+vYAlVCM/KrcPdY97GM="));
              // System.out.println("18/002404 "+encryptString("2013032708202000004"));
               
               System.out.println("18/004273 =>> "+decryptString("Z3Zzfo9cobyk3RPyawTfplfNP5bf0asgChPwmd0/A+w="));
               System.out.println("19_004135 =>> "+decryptString("PVLCk4SJ/Ubpwhop3fpFPGgn+vYAlVCM/KrcPdY97GM="));
               System.out.println("19_007325 =>> "+decryptString("FlRadV3vek89vJ5g2gpi1VWt1S9hDtxKeFgQOUFpJdE="));
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
