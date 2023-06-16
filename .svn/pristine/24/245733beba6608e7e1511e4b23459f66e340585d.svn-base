/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHome;

import java.sql.ResultSet;
import java.sql.SQLException;
import root.Crypto;
import root.DatabaseQuery;

/**
 *
 * @author Administrator
 */
public class UserInfo {
    DatabaseQuery database;
    Crypto cr;
    public UserInfo(){
        database = new DatabaseQuery();
    }
    public String login(String uid, String pass) throws Exception{
        String parms[] = new String[2];
        parms[0] = uid;
        parms[1] = pass;
        String name = "",utype="",loginResult="";
        try{
            loginResult = database.prepareSelect("select * from login where uid=? and pass=?", parms, 2);
        }catch(Exception ex){System.out.println(parms[0]+parms[1]+"Err : "+ex.toString());}
        return loginResult;
    }
    public boolean changePassword(String oldPass, String newPass, String uid) throws Exception{
        cr = new Crypto();
        String sql = "update login set pass=? where uid=? and pass=?";
        boolean status = false;
        String parms[] = new String[3];
        parms[0] = cr.encryptString(newPass);
        parms[1] = uid;
        parms[2] = cr.encryptString(oldPass);
        try{
            status = database.insertData(sql,parms,3);
                return status;
        }catch(Exception ex){
            return status;
        }   
    }
    
    
}
