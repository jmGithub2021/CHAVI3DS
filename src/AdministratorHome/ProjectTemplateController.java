/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdministratorHome;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import login.LoginController;
import root.DatabaseQuery;
import root.ManageUser;

/**
 * FXML Controller class
 *
 * @author SURAJIT
 */
public class ProjectTemplateController implements Initializable {
    @FXML
    private ComboBox<String> projectList;
    @FXML
    private Button saveTemplate;
    @FXML
    private CheckBox diagnosis_anatomicsite;
    @FXML
    private CheckBox diagnosis_laterality;
    @FXML
    private CheckBox diagnosis_pathology;
    @FXML
    private CheckBox diagnosis_diagnosis_date;
    @FXML
    private CheckBox diagnosis_diagnosis_complete;
    @FXML
    private CheckBox diagnosis_recurence;
    @FXML
    private CheckBox ihcprofile_protein_tested;
    @FXML
    private CheckBox ihcprofile_ihc_result;
    @FXML
    private CheckBox fishprofile_gene_tested;
    @FXML
    private CheckBox fishprofile_type_genetic_abnormality;
    @FXML
    private CheckBox fishprofile_fish_result;
    @FXML
    private CheckBox mutationprofile_gene_tested;
    @FXML
    private CheckBox mutationprofile_type_mutation;
    @FXML
    private CheckBox mutationprofile_is_pathogenic;
    @FXML
    private CheckBox expressionprofile_gene_tested;
    @FXML
    private CheckBox expressionprofile_expression_result;
    @FXML
    private CheckBox epigeneticprofile_gene_tested;
    @FXML
    private CheckBox epigeneticprofile_type_epigenetic_abnotmality;
    @FXML
    private CheckBox epigeneticprofile_epigenetic_result;
    @FXML
    private CheckBox tumordescription_tumor_maxsize;
    @FXML
    private CheckBox tumordescription_tumor_maxsize_unit;
    @FXML
    private CheckBox tumordescription_tumor_site;
    @FXML
    private CheckBox tumordescription_tumor_focality;
    @FXML
    private CheckBox tumordescription_extent;
    @FXML
    private CheckBox tumordescription_perforation;
    @FXML
    private CheckBox tumorpathology_histological_type;
    @FXML
    private CheckBox tumorpathology_grade;
    @FXML
    private CheckBox tumorpathology_necrosis;
    @FXML
    private CheckBox tumorpathology_angioinvasion;
    @FXML
    private CheckBox tumorpathology_tumor_invasion;
    @FXML
    private CheckBox tumorpathology_perineural_invasion;
    @FXML
    private CheckBox tumorpathology_tumor_depostis;
    @FXML
    private CheckBox tumorpathology_treatment_effect;
    @FXML
    private CheckBox tumormargin_margin_name;
    @FXML
    private CheckBox tumormargin_margin_involved;
    @FXML
    private CheckBox tumormargin_margin_distance;
    @FXML
    private CheckBox tumormargin_margin_distance_unit;
    @FXML
    private CheckBox stageinformation_stagingtype;
    @FXML
    private CheckBox stageinformation_recurrentdisease;
    @FXML
    private CheckBox stageinformation_neoadjuvanttherpygiven;
    @FXML
    private CheckBox stageinformation_stagingsystem;
    @FXML
    private CheckBox stageinformation_tstage;
    @FXML
    private CheckBox stageinformation_nstage;
    @FXML
    private CheckBox stageinformation_mstage;
    @FXML
    private CheckBox stageinformation_overallstage;
    @FXML
    private CheckBox treatmentintent_treatmentintentvalue;
    @FXML
    private CheckBox radiotherapy_given;
    @FXML
    private CheckBox radiotherapy_fraction;
    @FXML
    private CheckBox radiotherapy_startdate;
    @FXML
    private CheckBox radiotherapy_enddate;
    @FXML
    private CheckBox radiotherapy_rt_site;
    @FXML
    private CheckBox radiotherapy_rt_side;
    @FXML
    private CheckBox radiotherapy_rt_dose;
    @FXML
    private CheckBox radiotherapy_rt_treatmentintent_setting;
    @FXML
    private CheckBox radiotherapy_equipment;
    @FXML
    private CheckBox radiotherapy_technique;
    @FXML
    private CheckBox radiotherapy_is_reradiation;
    @FXML
    private CheckBox radiotherapy_concurrentchemotherapy;
    @FXML
    private CheckBox radiotherapy_concurrentmedication;
    @FXML
    private CheckBox surgery_surgerydetails;
    @FXML
    private CheckBox surgery_surgeryvalue;
    @FXML
    private CheckBox surgery_sidesurgery;
    @FXML
    private CheckBox surgery_sentinel_node;
    @FXML
    private CheckBox surgery_reconstruction;
    @FXML
    private CheckBox surgery_node_dissection;
    @FXML
    private CheckBox surgery_min_invasive;
    @FXML
    private CheckBox surgery_intraop_guidance;
    @FXML
    private CheckBox surgery_extent_resection;
    @FXML
    private CheckBox surgery_surgerydate;
    @FXML
    private CheckBox chemotherapy_given;
    @FXML
    private CheckBox chemotherapy_setting;
    @FXML
    private CheckBox chemotherapy_cycle;    
    @FXML
    private CheckBox chemotherapy_regimen;
    @FXML
    private CheckBox chemotherapy_start_date;
    @FXML
    private CheckBox chemotherapy_end_date;
    @FXML
    private CheckBox immunotherapy_given;
    @FXML
    private CheckBox immunotherapy_setting;
    @FXML
    private CheckBox immunotherapy_immunoth_type;
    @FXML
    private CheckBox immunotherapy_regimen;
    @FXML
    private CheckBox immunotherapy_startdate;
    @FXML
    private CheckBox immunotherapy_enddate;
    @FXML
    private CheckBox hormonetherapy_given;
    @FXML
    private CheckBox hormonetherapy_setting;
    @FXML
    private CheckBox hormonetherapy_hormonetherapytype;
    @FXML
    private CheckBox hormonetherapy_regimen;
    @FXML
    private CheckBox hormonetherapy_startdate;
    @FXML
    private CheckBox hormonetherapy_enddate;
    @FXML
    private CheckBox hormonetherapy_drugname;
    @FXML
    private CheckBox targetedtherapy_given;
    @FXML
    private CheckBox targetedtherapy_setting;
    @FXML
    private CheckBox targetedtherapy_targeted_therapy;
    @FXML
    private CheckBox targetedtherapy_regimen_targeted;
    @FXML
    private CheckBox targetedtherapy_startdate;
    @FXML
    private CheckBox targetedtherapy_enddate;
    @FXML
    private CheckBox targetedtherapy_cycles;
    @FXML
    private CheckBox imagingdata_imagetype;
    @FXML
    private CheckBox imagingdata_imagedate;
    @FXML
    private CheckBox imagingfeature_imaging_timing;
    @FXML
    private CheckBox imagingfeature_site_abnormality;
    @FXML
    private CheckBox imagingfeature_enhancement;
    @FXML
    private CheckBox imagingfeature_edema;
    @FXML
    private CheckBox imagingfeature_svz_involvement;
    @FXML
    private CheckBox imagingfeature_neuraxis_spread;
    @FXML
    private CheckBox imagingfeature_disease_status_img;
    @FXML
    private CheckBox imagingfeature_disease_progression_type;
    @FXML
    private CheckBox recurrence_recurrencetype;
    @FXML
    private CheckBox recurrence_recurremcedate;
    @FXML
    private CheckBox nodalstatus_nodes_examined;
    @FXML
    private CheckBox nodalstatus_nodes_positive;
    @FXML
    private CheckBox nodalstatus_node_level;
    @FXML
    private CheckBox nodalstatus_extra_nodal_extension;
    @FXML
    private Button saveAttributes;
    @FXML
    private TextField inputCSVAttr;
    @FXML
    private TextField chaviAttr;
    @FXML
    private TextField associatedTable;
    @FXML
    private AnchorPane displayAttr;
    @FXML
    private TilePane otherAttributeList;
    
    Map<String, String> projectlist;
    ManageUser mu;
    DatabaseQuery db;
    
    //**** READ BELOW FIRST
    /** When you include a new checkbox in the template, make sure you are maintaining the checkbox id pattern.
        * The table name and attribute name is concatenated by a under score symbol (_). 
        * Examples: If the checkbox id is xyz_qwer then the table name is xyz and attribute name is qwer. 
        * Similarly if the id is zxcv_rty_poio then the table name is zxcv and attribute name is rty_poio.
        * 
        * Add the newly created CheckBox in checkboxList[].
    **/    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String username = LoginController.uname;
        String uid = LoginController.u_id;
        String utype = LoginController.utype;         
        mu = new ManageUser(uid,utype);
        db = new DatabaseQuery();
        ObservableList<String> projectsList = FXCollections.observableArrayList();
        projectlist = mu.getProjectList();
        System.out.println(projectlist);
        ArrayList<String> projectCode = new ArrayList<String>(projectlist.keySet());
        ArrayList<String> projectName = new ArrayList<String>(projectlist.values());
        projectsList.addAll(projectCode);
        projectList.setItems(projectsList);
        createCheckBox();
    }   
    @FXML
    private void saveTemplateAction(ActionEvent event){
        //System.out.println(diagnosis_anatomicsite.getId()+" : "+diagnosis_anatomicsite.isSelected());
                
        CheckBox checkboxList [] = {diagnosis_anatomicsite, diagnosis_laterality, diagnosis_pathology, diagnosis_diagnosis_date, diagnosis_diagnosis_complete,diagnosis_recurence,
                                    ihcprofile_protein_tested,ihcprofile_ihc_result,
                                    fishprofile_gene_tested,fishprofile_type_genetic_abnormality,fishprofile_fish_result,
                                    mutationprofile_gene_tested,mutationprofile_type_mutation,mutationprofile_is_pathogenic,
                                    expressionprofile_gene_tested,expressionprofile_expression_result,
                                    epigeneticprofile_gene_tested,epigeneticprofile_type_epigenetic_abnotmality,epigeneticprofile_epigenetic_result,
                                    tumordescription_tumor_maxsize,tumordescription_tumor_maxsize_unit,tumordescription_tumor_site,tumordescription_tumor_focality,tumordescription_extent,tumordescription_perforation,
                                    tumorpathology_histological_type,tumorpathology_grade,tumorpathology_necrosis,tumorpathology_angioinvasion,tumorpathology_tumor_invasion,tumorpathology_perineural_invasion,tumorpathology_tumor_depostis,tumorpathology_treatment_effect,
                                    tumormargin_margin_name,tumormargin_margin_involved,tumormargin_margin_distance,tumormargin_margin_distance_unit,
                                    stageinformation_stagingtype,stageinformation_recurrentdisease,stageinformation_neoadjuvanttherpygiven,stageinformation_stagingsystem,stageinformation_tstage,stageinformation_nstage,stageinformation_mstage,stageinformation_overallstage,
                                    treatmentintent_treatmentintentvalue,
                                    radiotherapy_given,radiotherapy_fraction,radiotherapy_startdate,radiotherapy_enddate,radiotherapy_rt_site,radiotherapy_rt_side,radiotherapy_rt_dose,radiotherapy_rt_treatmentintent_setting,radiotherapy_equipment,radiotherapy_technique,radiotherapy_is_reradiation,radiotherapy_concurrentchemotherapy,radiotherapy_concurrentmedication,
                                    surgery_surgerydetails,surgery_surgeryvalue,surgery_sidesurgery,surgery_sentinel_node,surgery_reconstruction,surgery_node_dissection,surgery_min_invasive,surgery_intraop_guidance,surgery_extent_resection,surgery_surgerydate,
                                    chemotherapy_given,chemotherapy_setting,chemotherapy_cycle,chemotherapy_regimen,chemotherapy_start_date,chemotherapy_end_date,
                                    immunotherapy_given,immunotherapy_setting,immunotherapy_immunoth_type,immunotherapy_regimen,immunotherapy_startdate,immunotherapy_enddate,
                                    hormonetherapy_given,hormonetherapy_setting,hormonetherapy_hormonetherapytype,hormonetherapy_regimen,hormonetherapy_startdate,hormonetherapy_enddate,hormonetherapy_drugname,
                                    targetedtherapy_given,targetedtherapy_setting,targetedtherapy_targeted_therapy,targetedtherapy_regimen_targeted,targetedtherapy_startdate,targetedtherapy_enddate,targetedtherapy_cycles,
                                    imagingdata_imagetype,imagingdata_imagedate,
                                    imagingfeature_imaging_timing,imagingfeature_site_abnormality,imagingfeature_enhancement,imagingfeature_edema,imagingfeature_svz_involvement,imagingfeature_neuraxis_spread,imagingfeature_disease_status_img,imagingfeature_disease_progression_type,
                                    recurrence_recurrencetype,recurrence_recurremcedate,
                                    nodalstatus_nodes_examined,nodalstatus_nodes_positive,nodalstatus_node_level,nodalstatus_extra_nodal_extension
                                    };
        List<CheckBox> selectedBoxes = Stream.of(checkboxList).filter(CheckBox::isSelected).collect(Collectors.toList());
        String selectedProject = projectList.getValue();
        Vector tableObject = new Vector();
        Vector attributeObject = new Vector();
        String table,attribute;
        for(int i=0;i<selectedBoxes.size();i++){
            attribute = selectedBoxes.get(i).getId().substring(selectedBoxes.get(i).getId().indexOf("_")+1);
            table = selectedBoxes.get(i).getId().substring(0,selectedBoxes.get(i).getId().indexOf("_"));
            tableObject.add(i,table);
            attributeObject.add(i,attribute);
        }
        if(selectedProject ==null || selectedProject.equals("")){
            alert("No project is selected");
        }
        else if(selectedBoxes.size()<1){
            alert("Select atleast one attribute.");
        }
        else{
            CreateTemplate ct = new CreateTemplate(tableObject,attributeObject,selectedProject);
            if(mu.createProjectTemplate(ct))
                alert("Template is created");
            else
                alert("Template can not create, Check Log");
        }
    }
    @FXML
    private void saveAttributesAction(ActionEvent event){
        String inputAttribute,chaviAttribute,associateTable,projectid;
        inputAttribute = inputCSVAttr.getText();
        chaviAttribute = chaviAttr.getText();
        associateTable = associatedTable.getText();
        projectid = (projectList.getValue()==null)?"":projectList.getValue();
        if(inputAttribute.equals("") || chaviAttribute.equals("") || associateTable.equals("") || projectid.equals("")){
            alert("Make sure Projectid is selected, and all the required data is entered in the textfield.");
        }
        else{
            String sql = "insert into projectTemplateOtherAttribute (attributename, chaviattribute, associatedtable,projectid) values(?,?,?,?)";
            String parms[] = new String[4];
            parms[0] = inputAttribute;
            parms[1] = chaviAttribute;
            parms[2] = associateTable;
            parms[3] = projectid;
            try {
                if(db.insertData(sql,parms,parms.length))
                    alert("New field is added "+inputAttribute);
                else
                    alert("Field creation is faild, you may change with different fieldname");
                createCheckBox();
            } catch (SQLException ex) {
                Logger.getLogger(ProjectTemplateController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //System.out.println(inputAttribute+" : "+chaviAttribute+" : "+associateTable);
    }
    public void alert(String msgBody){
        Alert msg = new Alert(Alert.AlertType.INFORMATION);
        msg.setTitle("CHAVI Info");
        msg.setContentText(msgBody);
        msg.show();     
    }
    public void createCheckBox(){
            ArrayList<String> otherAttributeListArray = mu.getOtherAttributeName();
            int numberOfAttr = otherAttributeListArray.size();
            otherAttributeList.getChildren().clear();
            for(int i=0;i<numberOfAttr;i++){
                CheckBox cb = new CheckBox(String.valueOf(otherAttributeListArray.get(i)));
                otherAttributeList.getChildren().add(cb);
                
            }
            
    }
    public class CreateTemplate{
        public Vector table, attribute;
        public String project;
        CreateTemplate(Vector table,Vector attribute,String project){
            this.table = table;
            this.attribute = attribute;
            this.project = project;              
        }
    }
}
