/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHome;

import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import login.LoginController;
import org.controlsfx.control.textfield.TextFields;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import root.Crypto;
import root.GetConfig;
import root.ManageUser;
/**
 * FXML Controller class
 *
 * @author SURAJIT
 */
public class MapPatientProjectController implements Initializable {
    @FXML
    private TextField patientId;
    @FXML
    private ComboBox<String> projectId;
    @FXML
    private Button mapButton;

    Map<String, String> projectlist;
    JSONArray projectPatientMappingList;
    static String timestamp;
    Crypto cr;
    ManageUser mu;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextArea mappingLog;
    @FXML
    private Button export;
    @FXML
    private Button exportDatabase;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String userid = LoginController.u_id;
        String utype = LoginController.utype;
        String username = LoginController.uname;
        mu = new ManageUser(userid,utype);
        cr = new Crypto();
        projectPatientMappingList = new JSONArray();
        //System.out.println(mu.getPatientList());
        ArrayList<String> patientList = mu.getPatientList();
        TextFields.bindAutoCompletion(patientId, patientList);
        ObservableList<String> projectsList = FXCollections.observableArrayList();
        projectlist = mu.getProjectList();
        ArrayList<String> projectName = new ArrayList<String>(projectlist.values());
        projectsList.addAll(projectName);
        projectId.setItems(projectsList);
        timestamp = String.valueOf(System.currentTimeMillis());
    }  

    @FXML
    private void mapButtonAction(ActionEvent event) throws Exception {
        String selectedProject = String.valueOf(projectlist.keySet().toArray()[projectId.getSelectionModel().getSelectedIndex()]);
        String patientID = patientId.getText();
        String deIdentifiedID = mu.getDeidentifiedPatientID(patientID);
        //System.out.println(selectedProject+" : "+patientID+" : "+deIdentifiedID);
        JSONObject jsObj = new JSONObject();
        jsObj.put("patientid",cr.decryptString(deIdentifiedID));
        jsObj.put("projectid",selectedProject);
        projectPatientMappingList.add(jsObj);
        if(mu.mapProjectPatient(selectedProject, deIdentifiedID))
            mappingLog.setText(mappingLog.getText()+"\n"+selectedProject+" and "+patientID+" is mapped to the database.");  
    }


    @FXML
    private void exportAction(ActionEvent event) {
        try{
                FileWriter  file = new FileWriter(System.getProperty("user.dir")+GetConfig.deidentifiedDCMSource+"/PatientTOProjectMap_"+timestamp+".json",false);
                mu.getProjectListJSON().writeJSONString(projectPatientMappingList, file);
                file.close();
                mappingLog.setText(mappingLog.getText()+"\n"+"The file is exported to "+System.getProperty("user.dir")+GetConfig.deidentifiedDCMSource+"/PatientTOProjectMap_"+timestamp+".json");                
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }    
    }
    @FXML
    private void exportDatabaseAction(ActionEvent event){
    
    }
    
    
}
