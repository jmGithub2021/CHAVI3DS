/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;

/**
 *
 * @author SURAJIT KUNDU
 */
public class SystemLog {
    String sqlLogPath="";
    String errorLogPath="";
    String otherLogPath="";
    String tempLogPath="";
    String fileName="";
    String logTimestamp="";
    public SystemLog(){
        sqlLogPath=System.getProperty("user.dir")+"/log/sqlLog/";
        errorLogPath=System.getProperty("user.dir")+"/log/errorLog/";
        otherLogPath=System.getProperty("user.dir")+"/log/otherLog/"; 
        tempLogPath=System.getProperty("user.dir")+"/log/tempLog/";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat datetime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date dt = new Date();
        String today = dateFormat.format(dt);
        fileName = "CHAVIRO-"+today+".log";
        logTimestamp = datetime.format(dt);
    }
    private void writeLog(String path,String uid, String event){      
        try{
            File destFolder = new File(path);
            if(! destFolder.exists())
                {
                    destFolder.mkdirs();
                } 
            FileWriter fr = new FileWriter(destFolder+"/"+fileName, true);
            BufferedWriter br = new BufferedWriter(fr);
            JSONObject obj = new JSONObject();
                obj.put("user",""+uid+"");
                obj.put("event",""+event+"");
                obj.put("timestamp",""+logTimestamp+"");	           
            br.write(obj.toString()+",\n");
            br.close();
            fr.close();
        }catch(Exception ex){JOptionPane.showMessageDialog(null, "Log is not generating :"+ex.toString());}    
    }
    private void writeTmpLog(String path,String uid, String event){      
        try{
            File destFolder = new File(path);
            FileUtils.cleanDirectory(destFolder); 
            if(! destFolder.exists())
                {
                    destFolder.mkdirs();
                } 
            FileWriter fr = new FileWriter(destFolder+"/"+fileName, true);
            BufferedWriter br = new BufferedWriter(fr);
            String log = "";
            log += "User: "+uid;
            log+= "\nTimestamp: "+logTimestamp;
            log+= "\nLog: "+event;	           
            br.write(log+"\n");
            br.close();
            fr.close();
            Alert msg = new Alert(Alert.AlertType.WARNING);
            msg.setTitle("Temp Log");
            msg.setContentText("A log file is created for this event:\n Do you want to open it.");
            //msg.showAndWait();
            Optional<ButtonType> option = msg.showAndWait();
            try{
                if (option.get() == null) {
                   System.out.println("No action");
                } else if (option.get() == ButtonType.OK) {
                    viewInDesktop(path+"/"+fileName);
                }
                else if(option.get() == ButtonType.CLOSE){
                    System.out.println("Closed");
                }
                else {
                   System.out.println("-");
                }                  
            }catch(Exception ex){System.out.println("Err34954: "+ex.toString());}
        }catch(Exception ex){JOptionPane.showMessageDialog(null, "Log is not generating :"+ex.toString());}            
    }
    public void viewInDesktop(String filename) throws IOException{
        File file = new File(filename);
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);    
    }
    
    public void sqlLog(String uid,String event){
        writeLog(sqlLogPath,uid,event);
    }
    public void errorLog(String uid,String event){
        writeLog(errorLogPath,uid,event);    
    }
    public void otherLog(String uid,String event){
        writeLog(otherLogPath,uid,event);    
    }
    public void tempLog(String uid,String event){
        writeTmpLog(tempLogPath,uid,event);
    }
}
