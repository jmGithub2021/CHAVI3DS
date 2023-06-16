/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomdis;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Surajit Kundu
 */
public class DICOMDIS extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        /*if(validLicense()){*/
        loadLoginPage("/login/Login.fxml","CHAVIRO -DICOM De-identification login");
       /* }*/
        /*else{
            loadLoginPage("FXMLDocument.fxml","CHAVIRO -DICOM De-identification login");
        }*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
