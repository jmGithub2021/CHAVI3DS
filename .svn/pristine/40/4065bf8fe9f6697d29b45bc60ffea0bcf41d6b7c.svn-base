/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdministratorHome;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import login.LoginController;
import org.json.simple.JSONObject;
import root.GetConfig;
import root.ManageUser;
import root.SystemLog;

/**
 * FXML Controller class
 *
 * @author Surajit Kundu
 */
public class AdministratorhomeController implements Initializable {

    @FXML
    private Button activate;
    @FXML
    private Button deactivate;
    @FXML
    private Button createProject;
    @FXML
    private TableView userList;
    @FXML
    private ComboBox<String> userFilterList;

    private ObservableList<ObservableList> data;
    private String uidSelectedForUpdate = "";
    List selectedUser;
    String uid;
    SystemLog sysLog;
    ManageUser mu;
    @FXML
    private SplitPane splitPane;
    @FXML
    private TextField projectName;
    @FXML
    private TextField projectID;
    @FXML
    private TextField approvalNo;
    @FXML
    private DatePicker approvalDate;
    @FXML
    private TextField centerList;
    @FXML
    private ComboBox<String> animalImages;
    @FXML
    private TextField piName;
    @FXML
    private TextField scientificTitle;
    @FXML
    private TextField countriesList;
    @FXML
    private TextArea projectAbstract;
    @FXML
    private Button back;
    @FXML
    private TextField userName;
    @FXML
    private TextField userEmail;
    @FXML
    private TextField userContact;
    @FXML
    private PasswordField userPass;
    @FXML
    private PasswordField userCPass;
    @FXML
    private ComboBox<String> userType;
    @FXML
    private Button userRegister;
    @FXML
    private Button projectRegister;
    @FXML
    private Label welcome;
    @FXML
    private Button exportProject;
    @FXML
    private Button createProjectTemplatebtn;
    
    public void loadFXML(String fxmlPath) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("CHAVIRO -DICOM De-identification System");
        stage.setScene(scene);
        stage.show();        
    } 
    @FXML
    private void activateAction(ActionEvent event) {
        selectedUser =  (List) userList.getSelectionModel().getSelectedItem();
        //System.out.println(selectedUser.get(0));
        uidSelectedForUpdate = (String) selectedUser.get(0);
        if(mu.setUserActive(uidSelectedForUpdate)){
            System.out.println(uidSelectedForUpdate+"'s "+"account is activated by "+uid);
            sysLog.otherLog(uid,uidSelectedForUpdate+"'s "+"account is activated");
        }        
    }

    @FXML
    private void deactivateAction(ActionEvent event) {
        selectedUser =  (List) userList.getSelectionModel().getSelectedItem();
        //System.out.println(selectedUser.get(0));
        uidSelectedForUpdate = (String) selectedUser.get(0);
        if(mu.setUserDeactive(uidSelectedForUpdate)){
            System.out.println(uidSelectedForUpdate+"'s "+"account is deactivated by "+uid);
            sysLog.otherLog(uid,uidSelectedForUpdate+"'s "+"account is deactivated");
        } 
    }

    @FXML
    private void userFilterListChange(ActionEvent event) {
        int selectedItem = userFilterList.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem+" : "+userFilterList.getValue());
        try{
            printUserList(selectedItem);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }        
    }

    @FXML
    private void createProjectAction(ActionEvent event) {
        splitPane.setDividerPosition(0, 1.0);
    }
    @FXML
    private void createProjectTemplatebtnAction(ActionEvent event){
        try {
            loadFXML("/AdministratorHome/ProjectTemplate.fxml");
        } catch (IOException ex) {
            Logger.getLogger(AdministratorhomeController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    @FXML
    private void createCatalogAction(ActionEvent event){
        try {
            loadFXML("/AdministratorHome/GeneProteinCatalog.fxml");
        } catch (IOException ex) {
            Logger.getLogger(GeneProteinCatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    @FXML
    private void exportProjectAction(ActionEvent event){
       System.out.println(mu.getProjectListJSON());
        try{
                FileWriter  file = new FileWriter(System.getProperty("user.dir")+GetConfig.deidentifiedDCMSource+"/projectList.json",false);
                mu.getProjectListJSON().writeJSONString(mu.getProjectListJSON(), file);
                file.close();
                alertInformation("The projectList.json file is exported to "+System.getProperty("user.dir")+GetConfig.deidentifiedDCMSource+"/projectList.json");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }       
    }

    @FXML
    private void backAction(ActionEvent event) {
        splitPane.setDividerPosition(0, 0);
    }

    @FXML
    private void userRegisterAction(ActionEvent event) throws SQLException {
        final String nameRegex = "^[a-zA-Z,\\s]+$";
        final String emailRegex = "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b";
        final String contactRegex = "\\d{6,10}";
        String name="",emailid="",contactNo="",pass="",cPass="",loginid="";
        String validName="",validEmail="",validContactNo="",validPass="",validUtype="";
        final String utypeList[] = {"U","A"};
        name = userName.getText().trim();
        if(name.matches(nameRegex))
            validName = name;
        else
            alertInformation("Given name is not valid(System does not support number in name field)");
        emailid = userEmail.getText().trim();
        if(emailid.matches(emailRegex) && emailid != null){
            validEmail = emailid;
            loginid = validEmail;
        }
        else{
            System.out.println("DD : "+emailid.matches(emailRegex) + emailid);
            alertInformation("Please enter a valid email id.");
        }
        contactNo = userContact.getText().trim();
        if(contactNo.matches(contactRegex) && contactNo !=null)
            validContactNo = contactNo;
        else
            alertInformation("Enter valid contact number.");
        pass = new String(userPass.getText());
        cPass = new String(userCPass.getText());       
        if(pass.equals(cPass) && pass !=null)
            validPass = cPass;
        else
            alertInformation("Password and confirm password should be same.");
        validUtype = utypeList[userType.getSelectionModel().getSelectedIndex()];
        

        //validName="",validEmail="",validContactNo="",validPass="",validUtype="";
        if(validName.length()>0 && validEmail.length()>0 && validContactNo.length()>0 && validPass.length()>0){
            if(!mu.isUserExist(emailid,contactNo)){
                UserRegistration reg = new UserRegistration(loginid,validName,validEmail,validContactNo,validPass,validUtype);
                if(mu.createNewUser(reg)){
                    printUserList();
                    alertInformation("New "+userType.getSelectionModel().getSelectedItem()+" ID is created : "+loginid);
                    sysLog.otherLog(uid,"New "+userType.getSelectionModel().getSelectedItem()+" ID is created : "+loginid);
                }
            }
            else{
                alertInformation("This user already exists, please check email id and contact number");
            }
        }
        
        System.out.println(name.matches(nameRegex)+" : "+name.length());
    
    }

    @FXML
    private void projectRegisterAction(ActionEvent event) {
        String name="", projectid="", approvalno="", approvaldate="", centerlist="",piname="",scientifictitle="",countrieslist="",projectabstract="";
        boolean isAnimalImage;  
        final boolean animalFlagList[] = {true,false};
        name = projectName.getText().trim();
        projectid = projectID.getText().trim();
        approvalno = approvalNo.getText().trim();
        approvaldate = String.valueOf(approvalDate.getValue());
        centerlist = centerList.getText().trim();
        piname = piName.getText().trim();
        scientifictitle = scientificTitle.getText().trim();
        countrieslist = countriesList.getText().trim();
        projectabstract = projectAbstract.getText().trim();
        isAnimalImage = animalFlagList[animalImages.getSelectionModel().getSelectedIndex()];
        ProjectRegistration preg = new ProjectRegistration(name, projectid, approvalno, approvaldate, centerlist, piname, scientifictitle, countrieslist, projectabstract, isAnimalImage);
        if(!mu.isProjectExist(projectid,approvalno)){
            if(mu.createNewProject(preg)){
                    String fileName = projectid+".json";
                    JSONObject jsObj = new JSONObject();
                    jsObj.put("projectid",projectid);
                    try{
                        FileWriter fr = new FileWriter(fileName);
                        BufferedWriter br = new BufferedWriter(fr);  
                        br.write(jsObj.toString());
                        br.close();
                        fr.close();
                    }catch(Exception ex){System.out.println("Project Configuration file is not created.");}
                alertInformation("New project "+name+" is created.");
                sysLog.otherLog(uid,"New project "+name+", ID "+projectid+" is created.");
            }
            else{
                alertInformation("Can not create the project "+name+".");
            }            
        }
        else{
            alertInformation("The project "+name+" is already exist. Check the approval no or change project id");
        }
    }


    public class UserRegistration{
       public String name="", emailid="", contact="", pass="",utype="",loginid="";
        UserRegistration(String loginid,String name, String emailid, String contact, String pass, String utype){
            this.loginid = loginid;
            this.name = name;
            this.emailid = emailid;
            this.contact = contact;
            this.pass = pass;
            this.utype = utype;
        }
    }    
    public class ProjectRegistration{
        public String name="", projectid="", approvalno="", approvaldate="", centerlist="",piname="",scientifictitle="",countrieslist="",projectabstract="";
        public boolean isAnimalImage; 
        ProjectRegistration(String name, String projectid, String approvalno, String approvaldate, String centerlist, String piname, String scientifictitle, String countrieslist, String projectabstract, boolean isAnimalImage){
            this.name = name;
            this.projectid = projectid;
            this.approvalno = approvalno;
            this.approvaldate = approvaldate;
            this.centerlist = centerlist;
            this.piname = piname;
            this.scientifictitle = scientifictitle;
            this.countrieslist = countrieslist;
            this.projectabstract = projectabstract;
            this.isAnimalImage = isAnimalImage;
        }
    }    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String userid = LoginController.u_id;
        uid = userid;
        String utype = LoginController.utype;
        String username = LoginController.uname;
        mu = new ManageUser(userid,utype);
        sysLog = new SystemLog();
        try {
            printUserList();
        } catch (SQLException ex) {
           System.out.println("Err3450: "+ex.toString());
        }
        final String userFilter[] = {"All","User","Administrator","Activated","Deactivated"};
        ObservableList<String> flist = FXCollections.observableArrayList(userFilter);
        userFilterList.setItems(flist);
        projectAbstract.setWrapText(true);  
        final String utypeList[] = {"User","Administrator"};
        ObservableList<String> ulist = FXCollections.observableArrayList(utypeList);
        userType.setItems(ulist);  
        final String animalImagesFlag[] = {"YES","NO"};
        ObservableList<String> animalImagesFlagList = FXCollections.observableArrayList(animalImagesFlag);
        animalImages.setItems(animalImagesFlagList); 
        welcome.setText("Welcome, \n"+username);
    }

    public void printUserList() throws SQLException{
        //userList.setItems((ObservableList<ObservableList>) DbUtils.resultSetToTableModel(mu.getUserList()));
        ResultSet rs = mu.getUserList();
        data = FXCollections.observableArrayList();
        
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                userList.getColumns().addAll(col);
            }
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
 
            }
            userList.setItems(data);
            //rs.close();
       }
    public void printUserList(int selectedFilterItem) throws SQLException{
        //userList.setItems((ObservableList<ObservableList>) DbUtils.resultSetToTableModel(mu.getUserList()));
        ResultSet rs = mu.getFilteredUserList(selectedFilterItem);
        data = FXCollections.observableArrayList();
        
            /*for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                userList.getColumns().addAll(col);
            }*/
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
 
            }
            userList.setItems(data);
    }
    public void alertInformation(String msgBody){
        Alert msg = new Alert(Alert.AlertType.INFORMATION);
        msg.setTitle("DDIS alert");
        msg.setContentText(msgBody);
        msg.show();     
    }
    
}
