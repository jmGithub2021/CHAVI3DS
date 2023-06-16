/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import UserHome.UserIndexController;
import UserHome.UserhomeController;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;

/**
 *
 * @author Surajit Kundu
 */
public class PreProcessDICOM {
    static List<String> extList;
    static List<String> logList;
    static int numberOfFiles = 0;
    int totalAlreadyDeidentified = 0;
    int totalDeidentified = 0;        
    
    public void filterFileExtension(FileChooser fileChooser){
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("ZIP/DICOm files (*.zip,*.dcm)", "*.zip","*.dcm");
        fileChooser.getExtensionFilters().add(extentionFilter);
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);
        extList = extentionFilter.getExtensions();
    }
    public void JSONfilterFileExtension(FileChooser fileChooser){
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("JSON files (*.json,*.JSON)", "*.json","*.JSON");
        fileChooser.getExtensionFilters().add(extentionFilter);
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);
        extList = extentionFilter.getExtensions();
    }  
    public void CSVfilterFileExtension(FileChooser fileChooser){
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("CSV files (*.csv,*.CSV)", "*.csv","*.CSV");
        fileChooser.getExtensionFilters().add(extentionFilter);
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);
        extList = extentionFilter.getExtensions();
    }
    public static List<String> getAllowedExtension(){
        return extList;
    }
    public boolean isValidFile(File file){
        String selectedFileName = "",selectedFileExt="";
        if(file !=null){
            selectedFileName = file.getName();
            selectedFileExt = "*."+selectedFileName.substring(selectedFileName.lastIndexOf(".")+1, selectedFileName.length());
        }
        List<String> extList = getAllowedExtension();
        System.out.println("Check this list : "+extList.toString());
        if(!extList.contains(selectedFileExt) && file !=null){
            file = null;
            return false;
        }
        else{
            return true;
        }       
    }
    public void zipProcess(String fileName){
        UnZip unzip = new UnZip();
        String zipPath = unzip.unzipFile(fileName);
        File folder = new File(zipPath);
        File[] listOfFiles = folder.listFiles();
        int noOfFiles = listOfFiles.length;
        /*Thread th = new Thread(new progressStatus());
        th.start();*/
        //logList.add("Surajit");
        //printLog(logList);
 
       
        /**
         * Below for loop is used to rearrange the DICOM such that RT Dose can be de-identified at the last and RT plan
         *  is placed at the second last and RT Structure set is placed before RT plan.
         */
        for (int i = 0; i < noOfFiles; i++){
            File swap = null;
            File folder1 = new File(zipPath);
            File[] listOfFiles1 = folder1.listFiles();
            String modality="";
            if (listOfFiles[i].isFile() && unzip.isDCMFile(listOfFiles[i].getName())) {
                try{
                    DicomInputStream dis = new DicomInputStream(listOfFiles[i]);
                    DicomObject DCMObj = dis.readDicomObject();
                    modality = DCMObj.getString(Tag.Modality);
                }catch(Exception ex){} 
                if(modality.equalsIgnoreCase("RTDOSE")){
                    swap = listOfFiles[i];
                    listOfFiles[i] = listOfFiles[noOfFiles-1];
                    listOfFiles[noOfFiles-1] = swap;
                }
                if(modality.equalsIgnoreCase("RTPLAN")){
                    swap = listOfFiles[i];
                    listOfFiles[i] = listOfFiles[noOfFiles-2];
                    listOfFiles[noOfFiles-2] = swap;
                }
                if(modality.equalsIgnoreCase("RTSTRUCT")){
                    swap = listOfFiles[i];
                    listOfFiles[i] = listOfFiles[noOfFiles-3];
                    listOfFiles[noOfFiles-3] = swap;
                }
            } else if (listOfFiles[i].isDirectory()) {
              System.out.println("Directory are " + listOfFiles[i].getName());
            } 

        }
        
        for (int i = 0; i < noOfFiles; i++){
            if (listOfFiles[i].isFile() && unzip.isDCMFile(listOfFiles[i].getName())) {
                System.out.println("--------------------------------\nFile number : "+i);
                UserhomeController.progressStatus = (double) ((i+1)/(double)noOfFiles);
                uploadForDeidentification(listOfFiles[i]);
                System.out.println("File " + listOfFiles[i].getName());
              
               // UserhomeController.logStatus = (i+1)+" files of "+(noOfFiles+1)+" : " + listOfFiles[i].getName();
            } else if (listOfFiles[i].isDirectory()) {
              System.out.println("Directory " + listOfFiles[i].getName());
            }              
        }            
        try{
            FileUtils.cleanDirectory(folder);
        }catch(Exception ex){
            try{
                FileUtils.cleanDirectory(folder);
            }catch(Exception e){System.out.println("Nes Temp file is not deleted : "+e.toString());}
            System.out.println("Temp file is not deleted : "+ex.toString());
        }  
    }    

    public void uploadForDeidentification(File upDCMFile){
        String deidentifiedDCMDir = "", SysPatientId="",SysStudyUID="",SysSeriesUID="",SysSOPInsUID="";
        String dcmPatientId="",dcmStudyUID="",dcmSeriesUID="",dcmSOPInsUID="",dcmStudyDate="";
        String msg1 = "New Patient ID is created : ";
        String msg2 = "New Study Instance UID is created : ";
        String msg3 = "New Series Instance UID is created : ";
        String msg4 = "New SOP Instance UID is created : ";
        String msg5 = "New Frame Of Referenced UID is created : ";
        String imsg1 = "New Patient ID is inserted : ";
        String imsg2 = "New Study Instance UID is inserted : ";
        String imsg3 = "New Series Instance UID is inserted : ";
        String imsg4 = "New SOP Instance UID inserted : "; 
        String imsg5 = "New Frame Of Referenced UID is inserted : ";
             try{ 
                String uid="Surajit",utype="U";
                SystemLog sysLog = new SystemLog();
                DicomInputStream dis = new DicomInputStream(upDCMFile);
                DicomObject originalDCMObj = dis.readDicomObject();                 
                GenerateSystemUID gsuid  = new GenerateSystemUID(originalDCMObj,uid,utype);
                dis.close();
                dcmPatientId = gsuid.DicomPatientID.replace("MR/", "");
                dcmStudyUID = gsuid.DicomStudyUID;
                dcmSeriesUID = gsuid.DicomSeriesUID;
                dcmSOPInsUID = gsuid.DicomSOPInsUID;
                /*JOptionPane.showMessageDialog(null, gsuid.DicomPatientID);
                JOptionPane.showMessageDialog(null, gsuid.DicomStudyUID);
                JOptionPane.showMessageDialog(null, gsuid.DicomSeriesUID);
                JOptionPane.showMessageDialog(null, gsuid.DicomSOPInsUID);*/
                DCMDeidentification dd = new DCMDeidentification(upDCMFile);
                if(gsuid.isSOPInsUIDExist(dcmSOPInsUID)){
                    String msg = "This DICOM file is already deidentified : "+dcmSOPInsUID;
                    System.out.println(msg);
                    totalAlreadyDeidentified++;
                    UserhomeController.totalAlreadyDeidentified = totalAlreadyDeidentified;
                    //UserhomeController.logStatus = "This DICOM file is already deidentified";
                    //new UserhomeController().printLog(msg);
                    //Thread.sleep(500);
                   // JOptionPane.showMessageDialog(null, msg);
                }
                else{
                    if(gsuid.isPatientIdExist(dcmPatientId)){  //If DICOM Patient Id Already Exist in the database.
                        SysPatientId = gsuid.getSysPatientId(dcmPatientId);
                        String msg = "Patient ID is found";
                        sysLog.otherLog(uid,msg);
                        if(gsuid.isStudyUIDExist(dcmStudyUID)){ //If DICOM Study Instance UID already exist in the database.                              
                            SysStudyUID = gsuid.getSysStudyUID(dcmStudyUID);
                            String msgStudy = "Study Instance UID is found";
                            sysLog.otherLog(uid,msgStudy);                        
                            if(gsuid.isSeriesUIDExist(dcmSeriesUID)){   //If DICOM Series Instance UID already exist in the database.
                                SysSeriesUID = gsuid.getSysSeriesUID(dcmSeriesUID);
                                String msgSeries = "Series Instance UID is found";
                                sysLog.otherLog(uid,msgSeries);
                                ///fr///////////////////
                                SysSOPInsUID = gsuid.createSysSOPInsUID(SysSeriesUID);
                                sysLog.otherLog(uid,msg4+SysSOPInsUID);
                                if(gsuid.insertSOPInsUID(dcmSOPInsUID, SysSOPInsUID)){
                                    sysLog.otherLog(uid, imsg4+SysSOPInsUID);
                                }
                            }
                            else{   //If DICOM Series Instance UID does not exist in the database.
                                SysSeriesUID = gsuid.createSysSeriesUID(SysStudyUID);
                                sysLog.otherLog(uid,msg3+SysSeriesUID);
                                if(gsuid.insertSeriesUID(dcmSeriesUID, SysSeriesUID)){
                                    sysLog.otherLog(uid,imsg3+SysSeriesUID);
                                }
                                SysSOPInsUID = gsuid.createSysSOPInsUID(SysSeriesUID);
                                sysLog.otherLog(uid,msg4+SysSOPInsUID);
                                if(gsuid.insertSOPInsUID(dcmSOPInsUID, SysSOPInsUID)){
                                    sysLog.otherLog(uid, imsg4+SysSOPInsUID);
                                }
                            }
                        }
                        else{   //If DICOM Study Instance UID does not exist in the database                           
                            SysStudyUID = gsuid.createSysStudyUID(SysPatientId);
                            sysLog.otherLog(uid, msg2+SysStudyUID);
                            if(gsuid.insertStudyUID(dcmStudyUID, SysStudyUID)){
                                sysLog.otherLog(uid, imsg2+SysStudyUID);
                            }
                            SysSeriesUID = gsuid.createSysSeriesUID(SysStudyUID);
                            sysLog.otherLog(uid, msg3+SysSeriesUID);
                            if(gsuid.insertSeriesUID(dcmSeriesUID, SysSeriesUID)){
                                sysLog.otherLog(uid, imsg3+SysSeriesUID);
                            }
                            SysSOPInsUID = gsuid.createSysSOPInsUID(SysSeriesUID);
                            sysLog.otherLog(uid,msg4+SysSOPInsUID);
                            if(gsuid.insertSOPInsUID(dcmSOPInsUID, SysSOPInsUID)){
                                sysLog.otherLog(uid,imsg4+SysSOPInsUID);
                            }                              
                        }

                    }
                    else{     //If DICOM Pateint ID does not exist in the database
                        SysPatientId = gsuid.createSysPatientId();
                        sysLog.otherLog(uid,msg1+SysPatientId);  
                        if(gsuid.insertPatientId(dcmPatientId,SysPatientId)){
                            sysLog.otherLog(uid,imsg1+SysPatientId);
                            dcmStudyDate = gsuid.DicomStudyDate;
                            String randStudyDate = dcmStudyDate.substring(0,4)+gsuid.randomMonth()+gsuid.randomDay();
                            int difference = gsuid.getDayDifference(dcmStudyDate, randStudyDate);
                            gsuid.insertStudyDate(SysPatientId,dcmStudyDate,difference);
                            System.out.println("Random StudyDate created : "+randStudyDate +" Day Difference : "+difference);
                            sysLog.otherLog(uid, "Random StudyDate created : "+randStudyDate +" Day Difference : "+difference);
                        }
                            
                        SysStudyUID = gsuid.createSysStudyUID(SysPatientId);
                        sysLog.otherLog(uid,msg2+SysStudyUID);
                        if(gsuid.insertStudyUID(dcmStudyUID, SysStudyUID)){
                            //showLog(msg2+SysStudyUID);
                            sysLog.otherLog(uid,imsg2+SysStudyUID);                            
                        }
                        SysSeriesUID = gsuid.createSysSeriesUID(SysStudyUID);
                        sysLog.otherLog(uid, msg3+SysSeriesUID);
                        if(gsuid.insertSeriesUID(dcmSeriesUID, SysSeriesUID)){
                            sysLog.otherLog(uid,imsg3+SysSeriesUID);
                        }
                        SysSOPInsUID = gsuid.createSysSOPInsUID(SysSeriesUID);
                        sysLog.otherLog(uid,msg4+SysSOPInsUID);
                        if(gsuid.insertSOPInsUID(dcmSOPInsUID, SysSOPInsUID)){
                            sysLog.otherLog(uid,imsg4+SysSOPInsUID);
                        }                        
                    }
                   // System.out.println("Paramaters : "+SysPatientId+" : "+SysStudyUID+" : "+SysSeriesUID+" : "+SysSOPInsUID);
                    deidentifiedDCMDir = dd.deIdentifyDCM(SysPatientId, SysStudyUID, SysSeriesUID, SysSOPInsUID);
                   // dcmList.setModel(new FileDirectoryToJTree(new File(System.getProperty("user.dir")+"/"+deidentifiedDCMSource)));
                    System.out.println("Url : "+deidentifiedDCMDir);
                            //UserhomeController.logStatus= "Url : "+deidentifiedDCMDir;
                            
                    totalDeidentified++;
                    UserhomeController.totalDeidentified = totalDeidentified;
                }
                
                //upDCMFile.delete();
            }
            catch(Exception ex){System.out.println(upDCMFile.getName()+"Error0456578 : "+ex.toString());}   
    }
    
}
