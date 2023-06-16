/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Random;
import javax.swing.JOptionPane;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;

/**
 *
 * @author SURAJIT
 * @des 
 */
public class GenSystemUID {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;    
    File originalDCM;
    String DicomPatientID="",DicomStudyUID="",DicomSeriesUID="",DicomSOPInsUID="",DicomStudyDate="",DicomFrameOfReferencedUID="";
    String uid="",utype="";
    SystemLog sysLog;
    Crypto crypto;
    GetConfig gc;
    //MySqlConnect MySqlConnect;
    public GenSystemUID(){
        crypto = new Crypto();
        sysLog = new SystemLog();
        gc = new GetConfig();    
        //MySqlConnect = new MySqlConnect();
    }
    public GenSystemUID(DicomObject dcmObj,String uid, String utype) throws IOException{
        this.originalDCM = originalDCM;
        this.uid = uid;
        this.utype = utype;
        crypto = new Crypto();
        sysLog = new SystemLog();
        gc = new GetConfig();
        //MySqlConnect = new MySqlConnect();
       // DicomInputStream dis = new DicomInputStream(originalDCM);
        //DicomObject dcmObj = dis.readDicomObject();   
        DicomPatientID = dcmObj.getString(Tag.PatientID);
        DicomStudyUID = dcmObj.getString(Tag.StudyInstanceUID);
        DicomSeriesUID = dcmObj.getString(Tag.SeriesInstanceUID);
        DicomFrameOfReferencedUID = dcmObj.getString(Tag.FrameOfReferenceUID);
        DicomSOPInsUID = dcmObj.getString(Tag.SOPInstanceUID);
        DicomStudyDate = dcmObj.getString(Tag.StudyDate);
    }

    public String todayString(){
        int day,month,year;
        String dayString="",monthString="",yearString="",dateString="";
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH)+1;
        year = cal.get(Calendar.YEAR);
        dayString = (day>9)?String.valueOf(day):"0"+String.valueOf(day);
        monthString = (month>9)?String.valueOf(month):"0"+String.valueOf(month);
        yearString = String.valueOf(year);
        dateString = dayString+monthString+yearString;    
        return dateString;
    }
    public String today(){
        int day,month,year;
        String dayString="",monthString="",yearString="",dateString="";
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH)+1;
        year = cal.get(Calendar.YEAR);
        dayString = (day>9)?String.valueOf(day):"0"+String.valueOf(day);
        monthString = (month>9)?String.valueOf(month):"0"+String.valueOf(month);
        yearString = String.valueOf(year);
        dateString = yearString+"-"+monthString+"-"+dayString;    
        return dateString;
    }    
    public boolean isSOPInsUIDExist(String dcmSOPInsUID) throws SQLException{
        String parms[] = new String[1];
        parms[0] = dcmSOPInsUID;
        return new DatabaseQuery().isDataExists("select count(*) from sopuid_map where DCMSOPInsID = ?", parms, 1);
       /* con = new MySqlCon().connectMysql();
       // String sql = "select * from sopuid_map where concat(SysSeriesUID,'.',serialNo) = ?";
        String sql = "select * from sopuid_map where DCMSOPInsID = ?";        
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,dcmSOPInsUID);
            rs = pst.executeQuery();
            sysLog.sqlLog(uid,sql);
            if(rs.next())
                return true;
            else
                return false;    
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.toString());
            sysLog.errorLog(uid,ex.toString()); 
            return true;
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }*/
    }
    public boolean isPatientIdExist(String dcmPatientID) throws SQLException{
        String parms[] = new String[1];
        parms[0] = dcmPatientID;
        return new DatabaseQuery().isDataExists("select * from PatientID_map where DCMPatientID = ?", parms, 1);        
       /* con = new MySqlCon().connectMysql();
        String sql = "select * from PatientID_map where DCMPatientID = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,dcmPatientID);
            rs = pst.executeQuery();
            sysLog.sqlLog(uid,sql);
            if(rs.next())
                return true;
            else
                return false; 
        }catch(Exception ex){
            sysLog.errorLog(uid,ex.toString()); 
            return true;
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }    */    
    }
    public boolean isStudyUIDExist(String dcmStudyUID) throws SQLException{
        String parms[] = new String[1];
        parms[0] = dcmStudyUID;
        return new DatabaseQuery().isDataExists("select * from StudyUID_map where DCMStudyUID = ?", parms, 1);         
       /* con = new MySqlCon().connectMysql();
        //String sql = "select * from StudyUID_map where concat(DCMPatientID,'.',serialNo) = ?";
        String sql = "select * from StudyUID_map where DCMStudyUID = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,dcmStudyUID);
            rs = pst.executeQuery();
            sysLog.sqlLog(uid,sql);
            if(rs.next())
                return true;
            else
                return false; 
        }catch(Exception ex){
            sysLog.errorLog(uid,ex.toString()); 
            return true;
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }    */    
    }
    public boolean isSeriesUIDExist(String dcmSeriesUID)throws SQLException{
        String parms[] = new String[1];
        parms[0] = dcmSeriesUID;
        return new DatabaseQuery().isDataExists("select * from SeriesUID_map where DCMSeriesUID = ?", parms, 1);        
        /*con = new MySqlCon().connectMysql();
        //String sql = "select * from SeriesUID_map where concat(SysStudyUID,'.',serialNo) = ?";
        String sql = "select * from SeriesUID_map where DCMSeriesUID = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,dcmSeriesUID);
            rs = pst.executeQuery();
            sysLog.sqlLog(uid,sql);
            if(rs.next())
                return true;
            else
                return false; 
        }catch(Exception ex){
            sysLog.errorLog(uid,ex.toString()); 
            return true;
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }*/        
    }
    public String getSysPatientId(String dcmPatientID) throws SQLException, Exception{
        String parms[] = new String[1];
        parms[0] = dcmPatientID;
        String sysPatientID = new DatabaseQuery().getSingleRecord("select SysPatientID from PatientID_map where DCMPatientID=?", parms, 1);
        return crypto.decryptString(sysPatientID);        
    /*    String sysPatientID="";
        con = new MySqlCon().connectMysql();
        String sql = "select SysPatientID from PatientID_map where DCMPatientID=?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, dcmPatientID);
            rs = pst.executeQuery();
            if(rs.next()){
                sysPatientID = crypto.decryptString(rs.getString("SysPatientID"));
            }
            sysLog.sqlLog(uid,sql);
        }catch(Exception ex){
            sysLog.errorLog(uid,"Err0990 : "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }        
        return sysPatientID;*/
    }
    public String getSysStudyUID(String dcmStudyUID) throws SQLException, Exception{
        String parms[] = new String[1];
        parms[0] = dcmStudyUID;
        String sysStudyUID = new DatabaseQuery().getConcatUID("select SysPatientID,serialNo from StudyUID_map where DCMStudyUID=?", parms, 1);
        return crypto.decryptString(sysStudyUID);   
        
    /*    String sysStudyUID="",sysPatientId="",serialNo="";
        con = new MySqlCon().connectMysql();
        String sql = "select SysPatientID,serialNo from StudyUID_map where DCMStudyUID=?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, dcmStudyUID);
            rs = pst.executeQuery();
            if(rs.next()){
                sysPatientId = crypto.decryptString(rs.getString("SysPatientID"));
                serialNo = String.valueOf(rs.getInt("serialNo"));
                sysStudyUID = sysPatientId+"."+serialNo;
            }
            sysLog.sqlLog(uid,sql);
        }catch(Exception ex){
            sysLog.errorLog(uid,"Err0991 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }        
        return sysStudyUID;*/
    }
    public String getFrameOfReferenceUID(String dcmFrameOfReferenceUID) throws SQLException, Exception{
        String parms[] = new String[1];
        parms[0] = dcmFrameOfReferenceUID;
        String SysFrameOfReferenceUID = new DatabaseQuery().getSingleRecord("select SysFrameOfReferenceUID from FrameOfReferenceUID_map where DCMFrameOfReferenceUID=?", parms, 1);
        return crypto.decryptString(SysFrameOfReferenceUID);          
    /*    String SysFrameOfReferenceUID="";
        con = new MySqlCon().connectMysql();
        String sql = "select SysFrameOfReferenceUID from FrameOfReferenceUID_map where DCMFrameOfReferenceUID=?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, dcmFrameOfReferenceUID);
            rs = pst.executeQuery();
            if(rs.next()){
                SysFrameOfReferenceUID = crypto.decryptString(rs.getString("SysFrameOfReferenceUID"));
            }
            sysLog.sqlLog(uid,sql+" : "+dcmFrameOfReferenceUID);
        }catch(Exception ex){
            sysLog.errorLog(uid,"Err09091 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }   
        return SysFrameOfReferenceUID;*/
    }    
    public String getSysSeriesUID(String dcmSeriesUID) throws SQLException, Exception{
        String parms[] = new String[1];
        parms[0] = dcmSeriesUID;
        String sysSeriesUID = new DatabaseQuery().getConcatUID("select SysStudyUID,serialNo from SeriesUID_map where DCMSeriesUID=?", parms, 1);
        return crypto.decryptString(sysSeriesUID);          
    /*    String sysSeriesUID="",sysStudyUID="",serialNo="";
        con = new MySqlCon().connectMysql();
        String sql = "select SysStudyUID,serialNo from SeriesUID_map where DCMSeriesUID=?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, dcmSeriesUID);
            rs = pst.executeQuery();
            if(rs.next()){
                sysStudyUID = crypto.decryptString(rs.getString("SysStudyUID"));
                serialNo = String.valueOf(rs.getInt("serialNo"));
                sysSeriesUID = sysStudyUID+"."+serialNo;
            }
            sysLog.sqlLog(uid,sql);
        }catch(Exception ex){
            sysLog.errorLog(uid,"Err0992 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }        
        return sysSeriesUID;*/
    }    
    public String getSysSOPUID(String dcmSOPUID) throws SQLException, Exception{
        String parms[] = new String[1];
        parms[0] = dcmSOPUID;
        String sysSOPUID = new DatabaseQuery().getConcatUID("select SysSeriesUID,serialNo from SOPUID_map where DCMSOPInsID=?", parms, 1);
        return crypto.decryptString(sysSOPUID);          
    /*    String sysSOPUID="",sysSeriesUID="",serialNo="";
        con = new MySqlCon().connectMysql();
        String sql = "select SysSeriesUID,serialNo from SOPUID_map where DCMSOPInsID=?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, dcmSOPUID);
            rs = pst.executeQuery();
            if(rs.next()){
                sysSeriesUID = crypto.decryptString(rs.getString("SysSeriesUID"));
                serialNo = String.valueOf(rs.getInt("serialNo"));
                sysSOPUID = sysSeriesUID+"."+serialNo;
            }
            sysLog.sqlLog(uid,sql);
        }catch(Exception ex){
            sysLog.errorLog(uid,"Err0993 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }        
        return sysSOPUID;*/
    }    
    public String getOtherUID(String dcmUID, String sysUID) throws SQLException, Exception{
        String parms[] = new String[1];
        parms[0] = dcmUID;
        String deUID = crypto.decryptString(new DatabaseQuery().getSingleRecord("select SysUID from otherUID_map where dcmUID=?", parms, 1));
        if(deUID.equalsIgnoreCase("")){
            if(insertOtherUID(dcmUID,sysUID))
                deUID = sysUID;
        }
        return deUID;
    /*    String deUID="";
        con = new MySqlCon().connectMysql();
        String sql = "select SysUID from otherUID_map where dcmUID=?"; 
        
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, dcmUID);
            rs = pst.executeQuery();
            if(rs.next()){
                deUID = crypto.decryptString(rs.getString("SysUID"));
            }
            else{
                if(insertOtherUID(dcmUID,sysUID))
                    deUID = sysUID;
            }            
            sysLog.sqlLog(uid,sql);            
        }catch(Exception ex){sysLog.errorLog(uid, "ERR0775576"+ex.toString());}
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){sysLog.errorLog(uid, "ERR077557"+ex.toString());}
        }         
        return deUID;*/
    }
    
    
    public String createSysPatientId() throws SQLException{
        String sysPatientId = "", registeredPatStr="", todayString="",today="";
        int registeredPat=0;
        String parms[] = new String[1];
        parms[0] = today();
        registeredPat = new DatabaseQuery().getColumncount("select count(*) as nop from PatientID_map where DATE(instanceCreated) = ?", parms, 1);          
       /* con = new MySqlCon().connectMysql();
        String sql = "select count(*) as nop from PatientID_map where DATE(instanceCreated) = ?";
        todayString = todayString();
        today = today();
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,today);
            rs = pst.executeQuery();
            //sysLog.sqlLog(login.uid,sql);
            if(rs.next()){
                registeredPat = rs.getInt("nop");
            }   
        }catch(Exception ex){
            //sysLog.errorLog(login.uid,ex.toString()); 
        }
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }   */     
        if(registeredPat>=0 && registeredPat<10)
            registeredPatStr = "0000"+String.valueOf(registeredPat);
        if(registeredPat>9 && registeredPat<100)
            registeredPatStr = "000"+String.valueOf(registeredPat);
        if(registeredPat>99 && registeredPat<1000)
            registeredPatStr = "00"+String.valueOf(registeredPat);
        if(registeredPat>999 && registeredPat<10000)
            registeredPatStr = "0"+String.valueOf(registeredPat); 
        if(registeredPat>9999 && registeredPat<100000)
            registeredPatStr = String.valueOf(registeredPat);
        sysPatientId = gc.globalID.trim()+todayString.trim()+registeredPatStr.trim();
        return sysPatientId;
    }
    public String createSysStudyUID(String sysPatientId) throws SQLException{
        String sysStudyUID="";
        int serialNo=0;
        String parms[] = new String[1];
        parms[0] = today();
        serialNo = new DatabaseQuery().getColumncount("select count(*) nostd from StudyUID_map where SysPatientID = ?", parms, 1);          
        sysStudyUID = sysPatientId+"."+String.valueOf(serialNo).trim();
        return sysStudyUID;
        /*con = new MySqlCon().connectMysql();
        int serialNo=0;
        String sysStudyUID="";
        String sql = "select count(*) nostd from StudyUID_map where SysPatientID = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,crypto.encryptString(sysPatientId));
            rs = pst.executeQuery();
            if(rs.next()){
                serialNo = rs.getInt("nostd");
            }
            sysStudyUID = sysPatientId+"."+String.valueOf(serialNo).trim();
            System.out.println(crypto.encryptString(sysPatientId)+" : "+sysPatientId +" :E "+serialNo);
            sysLog.sqlLog(uid,sql);
        }catch(Exception ex){sysLog.errorLog(uid,ex.toString());}
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }        
        return sysStudyUID;*/
    }
    public String createSysFrameOfReferencedUID(String SysStudyUID){
        String SysFrameOfReferencedUID = "";
        long timestamp = System.currentTimeMillis();
        return SysStudyUID+"."+String.valueOf(timestamp);
    }
    public String createSysSeriesUID(String sysStudyUID) throws SQLException, Exception{
        String sysSeriesUID="";
        int serialNo=0;
        String parms[] = new String[1];
        parms[0] = crypto.encryptString(sysStudyUID);
        serialNo = new DatabaseQuery().getColumncount("select count(*) nosrs from SeriesUID_map where SysStudyUID = ?", parms, 1);          
        sysSeriesUID = sysStudyUID+"."+String.valueOf(serialNo).trim();
        return sysSeriesUID;        
        /*con = new MySqlCon().connectMysql();
        int serialNo=0;
        String sysSeriesUID="";
        String sql = "select count(*) nosrs from SeriesUID_map where SysStudyUID = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,crypto.encryptString(sysStudyUID));
            rs = pst.executeQuery();
            if(rs.next()){
                serialNo = rs.getInt("nosrs");
            }
            sysSeriesUID = sysStudyUID+"."+String.valueOf(serialNo).trim();
           // sysLog.sqlLog(login.uid,sql);
        }catch(Exception ex){/*sysLog.errorLog(login.uid,sql);*//*}
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }        
        return sysSeriesUID;*/
    }  
    public String createSysSOPInsUID(String sysSeriesUID) throws SQLException, Exception{
        String sysSOPInsUID="";
        int serialNo=0;
        String parms[] = new String[1];
        parms[0] = crypto.encryptString(sysSeriesUID);
        serialNo = new DatabaseQuery().getColumncount("select count(*) nosop from SOPUID_map where SysSeriesUID = ?", parms, 1);          
        sysSOPInsUID = sysSeriesUID+"."+String.valueOf(serialNo).trim();
        return sysSOPInsUID;        
        /*con = new MySqlCon().connectMysql();
        int serialNo=0;
        String sysSOPInsUID="";
        String sql = "select count(*) nosop from SOPUID_map where SysSeriesUID = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,crypto.encryptString(sysSeriesUID));
            rs = pst.executeQuery();
            if(rs.next()){
                serialNo = rs.getInt("nosop");
            }
            sysSOPInsUID = sysSeriesUID+"."+String.valueOf(serialNo).trim();
           // sysLog.sqlLog(login.uid,sql);
        }catch(Exception ex){/*sysLog.errorLog(login.uid,sql);*//*}
        finally{
            try{
                con.close();
                pst.close();
                rs.close();
            }catch(SQLException ex){}
        }        
        return sysSOPInsUID;*/
    }

    public boolean insertPatientId(String dcmPatientId, String SysPatientId) throws Exception{
        String parms[] = new String[2];
        parms[0] = dcmPatientId;
        parms[1] =  crypto.encryptString(SysPatientId);
        return new DatabaseQuery().insertData("insert into PatientID_map (DCMPatientID,SysPatientID) values(?,?)", parms, 2);
        /*con = new MySqlCon().connectMysql();
        String sqlPat = "insert into PatientID_map (DCMPatientID,SysPatientID) values(?,?)";
        boolean status = false;
        try{
            pst = con.prepareStatement(sqlPat);
            pst.setString(1, dcmPatientId);
            pst.setString(2, crypto.encryptString(SysPatientId));
            int i = pst.executeUpdate();
            if(i>0)
                status = true;
        }catch(Exception ex){
            status = false;
            sysLog.errorLog(uid, "Err insert00120 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
            }catch(SQLException ex){}
        }        
        return status;*/
    }
    public boolean insertStudyUID(String dcmStudyUID, String SysStudyUID) throws SQLException, Exception{
        String SysPatientId="";
        int studySerial=0;
        SysPatientId = SysStudyUID.substring(0, SysStudyUID.lastIndexOf("."));
        studySerial = Integer.parseInt(SysStudyUID.substring(SysStudyUID.lastIndexOf(".")+1,SysStudyUID.length()));
        String parms[] = new String[3];
        parms[0] = dcmStudyUID;
        parms[1] =  crypto.encryptString(SysPatientId);
        parms[2] = String.valueOf(studySerial);
        return new DatabaseQuery().insertData("insert into StudyUID_map (DCMStudyUID,SysPatientID,serialNo) values(?,?,?)", parms, 3);
        /*con = new MySqlCon().connectMysql();
        String sqlStudy = "insert into StudyUID_map (DCMStudyUID,SysPatientID,serialNo) values(?,?,?)";
        boolean status = false;
        try{
            pst = con.prepareStatement(sqlStudy);
            pst.setString(1, dcmStudyUID);
            pst.setString(2, crypto.encryptString(SysPatientId));
            pst.setInt(3, studySerial);
            int i = pst.executeUpdate();
            if(i>0)
                status = true;
        }catch(Exception ex){
            status = false;
            sysLog.errorLog(uid, "Err insert00121 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
            }catch(SQLException ex){}
        }        
        return status;*/
    }
    public boolean insertFrameOfReferenceUID(String dcmFrameOfReferenceUID, String SysFrameOfReferenceUID, String SysStudyUID) throws SQLException, Exception{
        String parms[] = new String[3];
        parms[0] = dcmFrameOfReferenceUID;
        parms[1] =  crypto.encryptString(SysFrameOfReferenceUID);
        parms[2] = crypto.encryptString(SysStudyUID);
        return new DatabaseQuery().insertData("insert into FrameOfReferenceUID_map (DCMFrameOfReferenceUID,SysFrameOfReferenceUID,SysStudyUID) values(?,?,?)", parms, 3);        
        /*con = new MySqlCon().connectMysql();
        String sqlSeries = "insert into FrameOfReferenceUID_map (DCMFrameOfReferenceUID,SysFrameOfReferenceUID,SysStudyUID) values(?,?,?)";
        boolean status = false;
        try{
            pst = con.prepareStatement(sqlSeries);
            pst.setString(1, dcmFrameOfReferenceUID);
            pst.setString(2, crypto.encryptString(SysFrameOfReferenceUID));
            pst.setString(3, crypto.encryptString(SysStudyUID));
            int i = pst.executeUpdate();
            if(i>0)
                status = true;
        }catch(Exception ex){
            status = false;
            sysLog.errorLog(uid, "Err insert09092 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
            }catch(SQLException ex){}
        }   
        return status;*/
    }
    public boolean insertSeriesUID(String dcmSeriesUID, String SysSeriesUID) throws SQLException, Exception{
        String SysStudyUID=""; 
        int seriesSerial=0;
        SysStudyUID = SysSeriesUID.substring(0, SysSeriesUID.lastIndexOf("."));
        seriesSerial = Integer.parseInt(SysSeriesUID.substring(SysSeriesUID.lastIndexOf(".")+1,SysSeriesUID.length()));        
        String parms[] = new String[3];
        parms[0] = dcmSeriesUID;
        parms[1] =  crypto.encryptString(SysStudyUID);
        parms[2] = String.valueOf(seriesSerial);
        return new DatabaseQuery().insertData("insert into SeriesUID_map (DCMSeriesUID,SysStudyUID,serialNo) values(?,?,?)", parms, 3);          
        
        /*con = new MySqlCon().connectMysql();
        String sqlSeries = "insert into SeriesUID_map (DCMSeriesUID,SysStudyUID,serialNo) values(?,?,?)";
        boolean status = false;
        try{
            pst = con.prepareStatement(sqlSeries);
            pst.setString(1, dcmSeriesUID);
            pst.setString(2, crypto.encryptString(SysStudyUID));
            pst.setInt(3, seriesSerial);
            int i = pst.executeUpdate();
            if(i>0)
                status = true;
        }catch(Exception ex){
            status = false;
            sysLog.errorLog(uid, "Err insert00122 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
            }catch(SQLException ex){}
        }        
        return status;*/
    }
    public boolean insertSOPInsUID(String dcmSOPInsUID, String SysSOPInsUID) throws SQLException, Exception{
        String SysSeriesUID=""; 
        int sopSerial=0;
        SysSeriesUID = SysSOPInsUID.substring(0, SysSOPInsUID.lastIndexOf("."));
        sopSerial = Integer.parseInt(SysSOPInsUID.substring(SysSOPInsUID.lastIndexOf(".")+1,SysSOPInsUID.length()));         
        String parms[] = new String[3];
        parms[0] = dcmSOPInsUID;
        parms[1] =  crypto.encryptString(SysSeriesUID);
        parms[2] = String.valueOf(sopSerial);
        return new DatabaseQuery().insertData("insert into SOPUID_map (DCMSOPInsID,SysSeriesUID,serialNo) values(?,?,?)", parms, 3);          
               
        
        /*con = new MySqlCon().connectMysql();
        String sqlSOP = "insert into SOPUID_map (DCMSOPInsID,SysSeriesUID,serialNo) values(?,?,?)";
        boolean status = false;
        try{
            pst = con.prepareStatement(sqlSOP);
            pst.setString(1, dcmSOPInsUID);
            pst.setString(2, crypto.encryptString(SysSeriesUID));
            pst.setInt(3, sopSerial);
            int i = pst.executeUpdate();
            if(i>0)
                status = true;
        }catch(Exception ex){
            status = false;
            sysLog.errorLog(uid, SysSOPInsUID+" Err insert00123 - "+ex.toString()+dcmSOPInsUID);
        }
        finally{
            try{
                con.close();
                pst.close();
            }catch(SQLException ex){}
        }        
        return status;*/
    }
    public boolean insertOtherUID(String dcmUID, String sysUID) throws SQLException, Exception{  
        String parms[] = new String[2];
        parms[0] = dcmUID;
        parms[1] =  crypto.encryptString(sysUID);
        return new DatabaseQuery().insertData("insert into otherUID_map (dcmUID, SysUID) values(?, ?)", parms, 2);          
                       
        /*con = new MySqlCon().connectMysql();
        String insertSql = "insert into otherUID_map (dcmUID, SysUID) values(?, ?)";
        boolean status = false;
        try{
            pst = con.prepareStatement(insertSql);
            pst.setString(1, dcmUID);
            pst.setString(2,crypto.encryptString(sysUID));
            int i = pst.executeUpdate();
            if(i>0)
                status = true;
            sysLog.sqlLog(uid, insertSql);
        }catch(Exception ex){
            status = false;
            sysLog.errorLog(uid, "Err insert00524 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
            }catch(SQLException ex){}
        }        
        return status;  */      
    } 
    public boolean insertStudyDate(String dcmPatientId,String originalStudyDate,int difference) throws SQLException, Exception{
        String parms[] = new String[3];
        parms[0] = dcmPatientId;
        parms[1] =  crypto.encryptString(originalStudyDate);
        parms[2] = String.valueOf(difference);
        return new DatabaseQuery().insertData("insert into studyDate_ref (SysPatientID,studyDate,difference) values(?,?,?)", parms, 3);          
               
        /*con = new MySqlCon().connectMysql();
        String sqlStudyDate = "insert into studyDate_ref (SysPatientID,studyDate,difference) values(?,?,?)";
        boolean status = false;
        try{
            pst = con.prepareStatement(sqlStudyDate);
            pst.setString(1, dcmPatientId);
            pst.setString(2, crypto.encryptString(originalStudyDate));
            pst.setInt(3, difference);
            int i = pst.executeUpdate();
            if(i>0)
                status = true;
        }catch(Exception ex){
            status = false;
            sysLog.errorLog(uid, "Err insert00124 - "+ex.toString());
        }
        finally{
            try{
                con.close();
                pst.close();
            }catch(SQLException ex){}
        }        
        return status;   */     
    }
    public String getDeidentifiedDate(String SysPatientId, String originalDate){
        int difference=0;
        String deidentifiedDate="";
        con = new MySqlCon().connectMysql();
        String sql = "select difference from studyDate_ref where SysPatientID = ?";
            try{
                pst = con.prepareStatement(sql);
                pst.setString(1,SysPatientId);
                rs = pst.executeQuery();
                if(rs.next()){
                    difference = rs.getInt("difference");
                }
            deidentifiedDate = deidentifiedDate(originalDate,difference); 
            //System.out.println(SysPatientId+" LL "+originalDate+ " RRRD "+difference);
            }catch(Exception ex){System.out.println("Error450092: "+ex.toString());}
            finally{
                try{
                    con.close();
                    pst.close();
                    rs.close();
                }catch(SQLException ex){}
            }            
            return deidentifiedDate;
    }
    public String deidentifiedDate(String originalDate,int difference){ //date format yyyymmdd ex. 20180123
        String deidentifiedDate="";
        int orgDay=0,orgMonth=0,monthCounter=0,orgTotal=0,year=0,dayCounter=0;
        int day=0,month=0;
        int dayList[] = new int[12];
        int normalYear[] = {31,28,31,30,31,30,31,31,30,31,30,31};
        int leapYear[] = {31,29,31,30,31,30,31,31,30,31,30,31};
        //System.out.println("Parameter : "+originalDate+" : "+difference);
        try{
            orgDay = Integer.parseInt(originalDate.substring(6,8));
            orgMonth = Integer.parseInt(originalDate.substring(4,6));
            year = Integer.parseInt(originalDate.substring(0,4));
            dayList = ((year%4==0 && year%100 !=0) || year%400==0)?leapYear:normalYear;
            for(int i=0;i<orgMonth-1;i++)
                orgTotal +=dayList[i];
            orgTotal += orgDay;
            int totalDay = orgTotal+difference;
            //System.out.println("Total Day : "+totalDay);
            int tempTotalDay = 0;
            int dayRemains = ((year%4==0 && year%100 !=0) || year%400==0)?(366-orgTotal):(365-orgTotal);
            int diff = dayRemains-totalDay;
            //System.out.println("Diff : "+diff+" : "+dayRemains+" : ");
            if(totalDay>0 && dayRemains<difference){
                int newYear = year+1;
                int newDay = 31;
                String newMonth = "01";
                String newDate = String.valueOf(newYear)+newMonth+String.valueOf(newDay);
                //System.out.println("First 1 : "+newYear+" : DD : "+newDate+" : "+newMonth+" : "+newDay+" : TotalDay : "+totalDay);
                deidentifiedDate = deidentifiedDate(newDate,diff);                
            }
            else if(totalDay>0 && dayRemains>=difference){
                while(totalDay>tempTotalDay){
                    tempTotalDay += dayList[monthCounter];
                    monthCounter++;
                }
                //System.out.println("Second2:  Month : "+(monthCounter)+" : Day : "+(tempTotalDay-dayList[monthCounter-1]));
                month = monthCounter;
                dayCounter = tempTotalDay-dayList[monthCounter-1];
                day = totalDay-dayCounter;
                String randDay = (day>9)?String.valueOf(day):"0"+String.valueOf(day);
                String randMonth = (month>9)?String.valueOf(month):"0"+String.valueOf(month);
                deidentifiedDate = String.valueOf(year)+randMonth+randDay;
                //System.out.println("FF : "+deidentifiedDate);            
            }
            else{
                int newYear = year-1;
                int newDay = 31;
                int newMonth = 12;
                String newDate = String.valueOf(newYear)+String.valueOf(newMonth)+String.valueOf(newDay);
                //System.out.println("Third 3 : "+newYear+" : DD : "+newDate+" : "+newMonth+" : "+newDay+" : TotalDay : "+totalDay);
                deidentifiedDate = deidentifiedDate(newDate,totalDay);
            }
        }catch(Exception ex){System.out.println("Err0913465 : "+ex.toString());}
        //System.out.println(originalDate +" : input/Output : "+deidentifiedDate);
        return deidentifiedDate;
    }
    public int getDayDifference(String originalDate, String randomDate){    //We assume that originalDate and randomDate belongs to the same year.
        int difference=0;
        int orgDay=0,orgMonth=0,randDay=0,randMonth=0,orgTotal=0,randTotal=0,year=0;
        int dayList[] = new int[12];
        int normalYear[] = {31,28,31,30,31,30,31,31,30,31,30,31};
        int leapYear[] = {31,29,31,30,31,30,31,31,30,31,30,31};
        try{
        orgDay = Integer.parseInt(originalDate.substring(6,8));
        orgMonth = Integer.parseInt(originalDate.substring(4,6));
        //System.out.println("Original Date : "+orgDay+" : "+orgMonth);
        year = Integer.parseInt(originalDate.substring(0,4));
        dayList = ((year%4==0 && year%100 !=0) || year%400==0)?leapYear:normalYear;
        randDay = Integer.parseInt(randomDate.substring(6,8));
        randMonth = Integer.parseInt(randomDate.substring(4,6));
       // System.out.println("Random Date : "+randDay+" : "+randMonth);
        for(int i=0;i<randMonth-1;i++)
            randTotal += dayList[i];
        randTotal += randDay;
        for(int i=0;i<orgMonth-1;i++)
            orgTotal +=dayList[i];
        orgTotal += orgDay;
        difference = randTotal-orgTotal;
        //System.out.println("uu "+randTotal+" : "+orgTotal);
        sysLog.otherLog(uid, originalDate+" Day difference "+difference);
        }catch(Exception ex){
            sysLog.errorLog(uid, "Err0880 getDayDifference(*,*) : "+ex.toString());
            System.out.println("Err getDayDifference(*,*) : "+ex.toString());
        }
        return difference;
    }
    public String randomDay(){ //Ignore leap year
        String randDay="01";
        try{
        Random r = new Random();
        int minDay = 1;
        int maxDay = 28;
        int day = r.nextInt(maxDay-minDay) + minDay;
        randDay = (day>9)?String.valueOf(day):"0"+String.valueOf(day);
        }catch(Exception ex){System.out.println("Err randomDay() : "+ex.toString());}
        return randDay;
    }
    public String randomMonth(){
        String randMonth="01";
        try{
            Random r = new Random();
            int minMonth = 1;
            int maxMonth = 12;
            int month = r.nextInt(maxMonth-minMonth) + minMonth;
            randMonth = (month>9)?String.valueOf(month):"0"+String.valueOf(month);
        }catch(Exception ex){System.out.println("Err randomMonth() : "+ex.toString());}
        return randMonth;        
    }
   
    
}
