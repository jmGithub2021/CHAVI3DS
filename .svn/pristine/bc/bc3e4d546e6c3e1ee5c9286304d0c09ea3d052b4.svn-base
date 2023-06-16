/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import root.GetConfig;
import root.Crypto;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import javax.swing.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author SURAJIT KUNDU
 */
public class MySqlConnect {
   //static Connection con = null;
   /* MySqlConnect(){
        this.con = null;
    }*/
    public static Connection connectMysql() {
        String dbDriver="", dbURL="", dbName="",dbUsername="", dbPassword="";
        Crypto crpt = new Crypto();
        GetConfig gc = new GetConfig();
        dbDriver = gc.dbDriver;
        dbURL = gc.dbURL;
        dbName = gc.dbName;
        dbUsername = gc.dbUsername;
        try{
            dbPassword = crpt.decryptString(gc.dbPassword); 
        }catch(Exception ex){System.err.println("Err943405 : "+ex.toString());}
        //JOptionPane.showMessageDialog(null,"Database config: "+dbDriver+dbURL+dbName+dbUsername+dbPassword);      
        try{
            Class.forName(dbDriver);

                Connection con = DriverManager.getConnection(dbURL+dbName, dbUsername, dbPassword);
            return con;
        }catch(Exception ex){
           // sysLog.errorLog(login.uid,"Database password decryption error: "+ex.toString());
            JOptionPane.showMessageDialog(null,"Database Con error : "+ex.toString());
            return null;
        }
    }
}
