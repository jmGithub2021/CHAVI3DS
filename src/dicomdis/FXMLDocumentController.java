/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomdis;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Surajit Kundu
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label activationStatus;
    
    @FXML
    private void licenseActivateAction(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        //activationStatus.setText("Faild!");
      //  ((Node)event.getSource()).getScene().getWindow().hide();
       // loadLoginPage("/login/Login.fxml","CHAVIRO -DICOM De-identification login");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //((Node)new Stage().getSource()).getScene().getWindow().hide();
        try {
            loadLoginPage("/login/Login.fxml","CHAVIRO -DICOM De-identification login");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void loadLoginPage(String fxmlPath,String title) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();        
    }
    
    
}
