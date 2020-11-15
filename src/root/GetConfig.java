/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.io.FileReader;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author SURAJIT KUNDU
 */
public class GetConfig {
   String dbName="";
   String dbURL="";
   String dbUsername="";
   String dbPassword="";
   String dbDriver="";
   public static String deidentifiedDCMSource="";
   public static String deidentifiedDCMDest="";
   public static String globalPath="";
   String globalID="";   
   public static String tempDirectory="";
   String encryptionKey="";
   
    public GetConfig(){
        try{
           JSONParser parser = new JSONParser();
           Object obj = parser.parse(new FileReader("config.json"));
           JSONObject jsobj = (JSONObject) obj;
           dbDriver = (String) jsobj.get("dbDriver");
           dbName = (String) jsobj.get("dbName");
           dbURL = (String) jsobj.get("dbURL");
           dbUsername = (String) jsobj.get("dbUsername");
           dbPassword = (String) jsobj.get("dbPassword");
           deidentifiedDCMSource = (String) jsobj.get("deidentifiedDCMSource");
           deidentifiedDCMDest = (String) jsobj.get("deidentifiedDCMdest");
           globalPath = (String) jsobj.get("globalPath");
           globalID = (String) jsobj.get("globalID");
           tempDirectory = (String) jsobj.get("tempDirectory");
           encryptionKey = (String) jsobj.get("encryptionKey");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Config parse error(GetConfig.java) : "+ex.toString());
        }
    }
    
}
