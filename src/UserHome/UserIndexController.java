/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.LoginController;
import org.apache.commons.io.FileUtils;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import root.GetConfig;
import root.ManageUser;
import root.PreProcessDICOM;

/**
 * FXML Controller class
 *
 * @author Surajit Kundu
 */
public class UserIndexController implements Initializable {

    /**
     * Initializes the controller class.
     */
    final ObservableList<String> image_type = FXCollections.observableArrayList("Diagnostic Image","Therapy Planning Image","Therapy Verification Image","Therapy Response Image","Screening Image","Pathology Image","Endoscopy Image");
    final ObservableList<String> sideList = FXCollections.observableArrayList("Right","Left","Bilateral","Midline","Not applicable");
    static PreProcessDICOM fileProcess;    
    File selectedFile;
    String selectedImageType = "",selectedJSONFileName="";
    String selectedAnatomicSite="", selectedSide="";
    public static JSONObject patientJSONData;

    @FXML
    private ComboBox<String> imageType;
    @FXML
    private Button chooseFile;
    @FXML
    private Label fileName;
    @FXML
    private Button setDeidentification;
    @FXML
    private Label confirmMsg;
    @FXML
    private Button startDICOMDe;
    @FXML
    private Button chooseFolder;
    @FXML
    private ComboBox<String> anatomicSite;
    @FXML
    private ComboBox<String> side;
    
    ManageUser mu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageType.setItems(image_type);
        startDICOMDe.setDisable(true);
        String userid = LoginController.u_id;
        String utype = LoginController.utype;
        String username = LoginController.uname;
        mu = new ManageUser(userid,utype);
        ObservableList<String> anatomicSiteList = FXCollections.observableArrayList();
        Map<String, String> diseaseSiteList = mu.getDiseaseSiteList();
        System.out.println("AnatomicSiteList: "+diseaseSiteList);
        ArrayList<String> diseaseSiteLabel = new ArrayList<String>(diseaseSiteList.keySet());
        ArrayList<String> diseaseSiteName = new ArrayList<String>(diseaseSiteList.values());
        anatomicSiteList.addAll(diseaseSiteLabel);
        anatomicSite.setItems(anatomicSiteList);   
        side.setItems(sideList);
    }   
    
    @FXML
    private void chooseFileAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileProcess = new PreProcessDICOM();
        fileProcess.filterFileExtension(fileChooser);
        selectedFile = fileChooser.showOpenDialog(null); 
        if(fileProcess.isValidFile(selectedFile)){
            fileName.setText(selectedFile.getName());
        }
        else
            fileName.setText("Choose valid File(zip/dcm)"); 
       
    }
    
    
    
    public boolean isDCMFile(String fileName){
        String ext = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        if(ext.equalsIgnoreCase("dcm") && ext != null)
            return true;
        else
            return false;
    }    
//    @FXML
//    private void chooseFolderAction(ActionEvent event){        
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        File selectedDirectory = directoryChooser.showDialog(null);
//
//    /*    if(selectedDirectory == null){
//            //No Directory selected
//       }else{
//            System.out.println(selectedDirectory.getAbsolutePath());
//            String originalDCMPath="";
//            originalDCMPath = selectedDirectory.getAbsolutePath();
//            File folder = new File(selectedDirectory.getAbsolutePath());
//            File[] listOfFiles = folder.listFiles();
//            int noOfFiles = listOfFiles.length;
//            System.out.println(noOfFiles);
//            for (int i = 0; i < noOfFiles; i++){
//                File swap = null;
//                File folder1 = new File(originalDCMPath);
//                File[] listOfFiles1 = folder1.listFiles();
//                String modality="",studyUID="",frameofreferencedUID="";
//                if (listOfFiles[i].isFile() && isDCMFile(listOfFiles[i].getName())) {
//                    try{
//                        DicomInputStream dis = new DicomInputStream(listOfFiles[i]);
//                        DicomObject DCMObj = dis.readDicomObject();
//                        modality = DCMObj.getString(Tag.Modality);
//                        studyUID = DCMObj.getString(Tag.StudyInstanceUID);
//                        frameofreferencedUID = DCMObj.getString(Tag.FrameOfReferenceUID);
//                    }catch(Exception ex){} 
//                    if(modality.equalsIgnoreCase("RTDOSE")){
//                        swap = listOfFiles[i];
//                        listOfFiles[i] = listOfFiles[noOfFiles-1];
//                        listOfFiles[noOfFiles-1] = swap;
//                    }
//                    if(modality.equalsIgnoreCase("RTPLAN")){
//                        swap = listOfFiles[i];
//                        listOfFiles[i] = listOfFiles[noOfFiles-2];
//                        listOfFiles[noOfFiles-2] = swap;
//                    }
//                    if(modality.equalsIgnoreCase("RTSTRUCT")){
//                        swap = listOfFiles[i];
//                        listOfFiles[i] = listOfFiles[noOfFiles-3];
//                        listOfFiles[noOfFiles-3] = swap;
//                    }
//                } else if (listOfFiles[i].isDirectory()) {
//                  System.out.println("Directory are " + listOfFiles[i].getName());
//                } 
//            }
//        
//        
//       } */      
//    }
    @FXML
    private void setDeidentificationAction(ActionEvent event){
        selectedImageType = imageType.getValue();
        selectedAnatomicSite = anatomicSite.getValue();
        selectedSide = side.getValue();
        JSONParser parser = new JSONParser();
        patientJSONData = new JSONObject();
        try{
            //String json = UserhomeController.jsObj.toJSONString();
            //System.out.println(json);
           // patientJSONData = (JSONObject)UserhomeController.jsObj;
            patientJSONData.put("imageType", selectedImageType);
            patientJSONData.put("anatomicsite",selectedAnatomicSite);
            patientJSONData.put("laterality",selectedSide);
        }catch(Exception ex){}
        //System.out.println(obj.toString());
        if(patientJSONData != null && selectedFile != null && selectedImageType!= null && selectedAnatomicSite !=null && selectedSide!=null){
            startDICOMDe.setDisable(false);
            //confirmMsg.setText(patientJSONData.toString());
            confirmMsg.setText("Selected image type is "+selectedImageType+"\nAnd system found the patient data ");
        }
        else{
            confirmMsg.setText("System is not ready for deidentification.\n i. Please make sure valid file is selected\n ii. Image type is selected.\n iii. You have provided patient data.\n iv. Anatomic site is selected \n v. Laterality/Side is selected.");
        }
    }
    @FXML
    private void startDICOMDeAction(ActionEvent event){
        Stage stage = (Stage) startDICOMDe.getScene().getWindow();
        stage.close();        
        Thread th = new Thread(dicomProcessThread);
        th.setDaemon(true);
        th.start();        
    }    
    Task dicomProcessThread = new Task<Void>(){
        @Override protected Void call(){
            String selectedFileName="",selectedFileExt="";
            try{
                if(selectedFile != null){
                    selectedFileName = selectedFile.getName();
                    selectedFileExt = selectedFileName.substring(selectedFileName.lastIndexOf(".")+1, selectedFileName.length());        
                }
                if(selectedFileExt.equalsIgnoreCase("zip")){ 
                    fileProcess.zipProcess(selectedFile.getAbsolutePath());
                }       
                else if(selectedFile != null && selectedFileExt.equalsIgnoreCase("dcm")){
                    fileProcess.uploadForDeidentification(selectedFile);
                } 
                else{ 
                    fileName.setText("Please choose a DICOM file or series");
                    //JOptionPane.showMessageDialog(null, "Please choose a DICOM file or series");
                }    
                selectedFile = null;
                fileName.setText("No file is selected");
            }catch(Exception ex){System.out.println("Err0256356: "+ex.toString());}
            return null;
        }
    }; 
    
    
    
    
}
