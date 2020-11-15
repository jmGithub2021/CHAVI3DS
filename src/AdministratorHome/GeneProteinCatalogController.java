/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdministratorHome;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import login.LoginController;
import root.DatabaseQuery;
import root.ManageUser;

/**
 * FXML Controller class
 *
 * @author SURAJIT
 */
public class GeneProteinCatalogController implements Initializable {
    @FXML
    private ComboBox<String> selectType;
    @FXML
    private TextField name;
    @FXML
    private Button addCatalogbtn;
    @FXML
    private TextField symbol;
    @FXML
    private TableView geneProteinList;
    @FXML
    private TextField search;
    
    /**
     * Initializes the controller class.
     */
    private ObservableList<ObservableList> data;
    ManageUser mu;
    DatabaseQuery db;
    FilteredList flist;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String userid = LoginController.u_id;
        String utype = LoginController.utype;        
        mu = new ManageUser(userid,utype);
        db = new DatabaseQuery();
        final String catalogType [] = {"GENE", "PROTEIN", "OTHER"};
        ObservableList<String> cataloglist = FXCollections.observableArrayList(catalogType);
        selectType.setItems(cataloglist);   
        try {
            printMoleculeList();
        } catch (SQLException ex) {
           System.out.println("Err2550: "+ex.toString());
        }        
    }  
    public void alert(String msgBody){
        Alert msg = new Alert(Alert.AlertType.INFORMATION);
        msg.setTitle("CHAVI Message");
        msg.setContentText(msgBody);
        msg.show();     
    }    
    
    @FXML
    private void addCatalogbtnAction(ActionEvent event){
        String selectedMoleculeType="",sequenceSymbol="",sequenceName="";
        sequenceSymbol = symbol.getText().trim();
        sequenceName = name.getText().trim();
        selectedMoleculeType = (selectType.getValue()==null)?"":selectType.getValue();
        if(selectedMoleculeType.equals("") || sequenceSymbol.equals("") || sequenceName.equals("")){
            alert("Make sure Molecule type is selected, and all the required data is entered in the textfield.");
        }
        else{
            String sql = "insert into moleculescatalog(symbol, moleculetype, moleculename) values(?,?,?)";
            String parms[] = new String[3];
            parms[0] = sequenceSymbol;
            parms[1] = selectedMoleculeType;
            parms[2] = sequenceName;
            try {
                if(db.insertData(sql,parms,parms.length)){
                    alert("New "+selectedMoleculeType+" "+sequenceSymbol+" is added");
                    printMoleculeList();
                }
                else
                    alert("Field creation is faild, you may change with different fieldname");
            } catch (SQLException ex) {
                Logger.getLogger(GeneProteinCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        
            System.out.println("Molecule Type: "+selectedMoleculeType+" Molecule Symbol: "+sequenceSymbol+" Molecule Name: "+sequenceName);
        }
    }
    @FXML
    private void searchAction(KeyEvent event){
        String searchValue = search.getText();
        flist.setPredicate((Predicate <? super ObservableList<String>>) (ObservableList<String> mlist)->{
                if(searchValue.isEmpty() || searchValue==null)
                    return true;
                else if(mlist.toString().toLowerCase().contains(searchValue.toLowerCase()))
                    return true;
                return false;
        });
        SortedList sortedlist = new SortedList(flist);
        sortedlist.comparatorProperty().bind(geneProteinList.comparatorProperty());
        geneProteinList.setItems(sortedlist);
    }
  
    public void printMoleculeList() throws SQLException{
        //userList.setItems((ObservableList<ObservableList>) DbUtils.resultSetToTableModel(mu.getUserList()));
        ResultSet rs = mu.getMoleculeSet();
        data = FXCollections.observableArrayList();
        
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                geneProteinList.getColumns().addAll(col);
            }
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
                flist = new FilteredList(data, e->true);
            }
            geneProteinList.setItems(data); 
       }
    
}
