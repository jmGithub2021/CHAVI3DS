/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import login.LoginController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Surajit Kundu
 */
public class DatabaseQuery {
    Connection con = null;
    PreparedStatement pStatement = null;
    ResultSet rs = null;
    ResultSetMetaData rsMeta = null;
    MySqlConnect connection = null;
    Crypto crypto;
    SystemLog sysLog;
    public DatabaseQuery(){
        connection = new MySqlConnect();
        crypto = new Crypto();
        sysLog = new SystemLog();
    }
    
    public String prepareSelect(String qsql, String parms[], int nop) throws SQLException{
        String result="";
        JSONObject jsObj = new JSONObject();
        JSONArray jarray = new JSONArray();
        try{
            con = new MySqlCon().connectMysql();
            pStatement = con.prepareStatement(qsql);
            for(int i=0; i<nop; i++)
                pStatement.setString(i+1, parms[i]);
            rs = pStatement.executeQuery();
            rsMeta = rs.getMetaData();
            int totalColumn = rsMeta.getColumnCount();
            int k=totalColumn,col=0;
            while(rs.next()){
                JSONObject jsobj = new JSONObject();
                while(k>0){
                    String columnName = rsMeta.getColumnName(k);
                    if(!columnName.equalsIgnoreCase("pass")){
                        jsobj.put(columnName,rs.getString(k));
                    }
                    k--;
                    }
                jarray.add(jsobj);
                jsObj.put(col,jarray);
                col++;
            }
            
            if(totalColumn==0)
                result=jsObj.toString();
            
            result = jsObj.toString();
        }catch(SQLException ex){
            sysLog.errorLog(LoginController.u_id, ex.toString());
            jsObj.put("err", ex.toString());
            result = jsObj.toString();
        }
        finally{
            sysLog.sqlLog(parms[0],qsql);
            rs.close();
            pStatement.close();
            con.close();
        }
        return result;
    }
    
    public boolean isDataExists(String qsql, String parms[], int nop) throws SQLException{
        try{
            con = new MySqlCon().connectMysql();
            pStatement = con.prepareStatement(qsql);
            for(int i=0; i<nop; i++)
                pStatement.setString(i+1, parms[i]);
            rs = pStatement.executeQuery();
            if(rs.next())
                return true;
            else
                return false;
        }catch(Exception ex){
            sysLog.errorLog(LoginController.u_id, ex.toString());
            ex.printStackTrace();
            return true;
        }
        finally{
            sysLog.sqlLog(LoginController.u_id,qsql);
            rs.close();
            pStatement.close();
            con.close();
        }
    }
    public int getColumncount(String qsql, String parms[], int nop) throws SQLException{
        try{
            con = new MySqlCon().connectMysql();
            pStatement = con.prepareStatement(qsql);
            for(int i=0; i<nop; i++)
                pStatement.setString(i+1, parms[i]);
            rs = pStatement.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 0;
        }catch(Exception ex){
            sysLog.errorLog(LoginController.u_id, ex.toString());
            ex.printStackTrace();
            return 0;
        }
        finally{
            sysLog.sqlLog(LoginController.u_id,qsql);
            rs.close();
            pStatement.close();
            con.close();
        }        
    }    
    public String getSingleRecord(String qsql, String parms[], int nop) throws SQLException{
        String result="";
        try{
            con = new MySqlCon().connectMysql();
            pStatement = con.prepareStatement(qsql);
            for(int i=0; i<nop; i++)
                pStatement.setString(i+1, parms[i]);
            rs = pStatement.executeQuery();
            if(rs.next())
                return rs.getString(1);
            else
                return "";
        }catch(Exception ex){
            sysLog.errorLog(LoginController.u_id, ex.toString());
            ex.printStackTrace();
            return "";
        }
        finally{
            sysLog.sqlLog(LoginController.u_id,qsql+" : "+parms[0]);
            rs.close();
            pStatement.close();
            con.close();
        }        
    }
    public String getConcatUID(String qsql, String parms[], int nop) throws SQLException{
        String result="",part1="",part2="";
        try{
            con = new MySqlCon().connectMysql();
            pStatement = con.prepareStatement(qsql);
            for(int i=0; i<nop; i++)
                pStatement.setString(i+1, parms[i]);
            rs = pStatement.executeQuery();
            if(rs.next()){
                part1 = crypto.decryptString(rs.getString(1));
                part2 = String.valueOf(rs.getInt(2));
                result = part1+"."+part2;
                return result;
            }
            else
                return "";
        }catch(Exception ex){
            sysLog.errorLog(LoginController.u_id, ex.toString());
            ex.printStackTrace();
            return "";
        }
        finally{
            sysLog.sqlLog(LoginController.u_id,qsql);
            rs.close();
            pStatement.close();
            con.close();
        }        
    }


    public boolean insertData(String qsql, String parms[], int nop) throws SQLException{
        try{
            con = new MySqlCon().connectMysql();
            pStatement = con.prepareStatement(qsql);
            for(int i=0; i<nop; i++)
                pStatement.setString(i+1, parms[i]);
            int i = pStatement.executeUpdate();
            if(i>0)
                return true;
            else
                return false;
        }catch(Exception ex){
            sysLog.errorLog(LoginController.u_id, ex.toString());
            ex.printStackTrace();
            //sysLog.errorLog(parms[0]+"."+parms[1]+"<>"+parms[2]+" w:w "+qsql, ex.toString());
            return false;
        }
        finally{
            sysLog.sqlLog(LoginController.u_id,qsql);
            pStatement.close();
            con.close();
        }        
    }
    
}
