/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserHome;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;
import login.LoginController;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import root.GenerateSystemUID;
import root.GetConfig;
import root.ManageUser;
import root.SystemLog;

/**
 *
 * @author SURAJIT Kundu
 */
public class ProjectWiseDeidentification {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    SystemLog sysLog;
    ManageUser mu;
    String projectID="",filePath="";
    String uid="";
    String log="";
    public ProjectWiseDeidentification(String projectID, String filePath){
        this.projectID = projectID;
        this.filePath = filePath;
        sysLog = new SystemLog();        
        String userid = LoginController.u_id;
        String utype = LoginController.utype;
        String username = LoginController.uname;
        uid = userid;
        mu = new ManageUser(userid,utype);
        
        try{
                //C:/Users/SURAJIT/Downloads/RadGlioHighA_DATA_2019-12-10_0653.csv
                Reader reader = Files.newBufferedReader(Paths.get(filePath),StandardCharsets.UTF_8);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
                List<String> headerName = csvParser.getHeaderNames();
                Map<String, String> patientIDMap = mu.getDeidenfitiedPatientIDMap();
                List<Map<String,List<String>>> attributeMap = mu.getChaviAttribute(projectID);
                //System.out.println(attributeMap.get(0).get("mr_number"));
                //System.out.println(mu.getMoleculeList().contains("MGMT"));
                List<String> moleculeList = mu.getMoleculeList(); 
                List<String> unMappedAttribute = new ArrayList<String>();
                String mr_number_field = mu.getCSVMRNumField("mr_number",projectID);
                int n = headerName.size();
                int k = 0;   
                JSONArray leafjsArray = new JSONArray();
                
                String prevPat = "";
                try{
                    GenerateSystemUID sysUID = new GenerateSystemUID();
                    JSONObject allPatientInfojsObj = new JSONObject();
                    allPatientInfojsObj.put("project", projectID);
                    int count=0;
                    for(CSVRecord csvRecords : csvParser){
                        count++;
                        //System.out.println(count+"-------------------------------Start---------------------------------");
                        JSONObject leafJSObj = new JSONObject();
                        JSONArray geneArr = new JSONArray();
                        while(k<n){
                            String org_patientid=csvRecords.get(mr_number_field).replace("MR/", "");
                            //System.out.println(org_patientid+" > "+headerName.get(k) + patientIDMap.get(org_patientid));
                            if(!csvRecords.get(headerName.get(k)).isEmpty() && patientIDMap.get(org_patientid) != null){
                                //String csvAttributeGeneric = headerName.get(k).split("__")[0];
                                String csvAttributeGeneric = headerName.get(k);
                                List<String> mappedAttribute = attributeMap.get(0).get(csvAttributeGeneric);
                                //System.out.println(csvAttributeGeneric+" EE : "+attributeMap.get(0).get(csvAttributeGeneric));
                                String validData = "";
                                validData = (mappedAttribute==null)? moleculeList.contains(csvAttributeGeneric.toUpperCase())?csvAttributeGeneric.toUpperCase()+" : "+csvRecords.get(headerName.get(k)):null:mappedAttribute+" : "+csvRecords.get(headerName.get(k));
                                if(validData != null && mappedAttribute != null){
                                        int dayDifference = 0;                                       
                                        String chaviAttributeName = mappedAttribute.get(1);
                                        String tableName = mappedAttribute.get(0);
                                        String csvRecord = csvRecords.get(headerName.get(k));
                                        if(isDMYFormat(csvRecord) || isYMDFormat(csvRecord)){
                                            dayDifference = sysUID.getDayDiffrence(patientIDMap.get(org_patientid));
                                            //System.out.println("Day Difference: "+dayDifference);
                                        }
                                        String value = isDMYFormat(csvRecord)?sysUID.yyyyMMddTODate(sysUID.deidentifiedDate(dmyTOymd(csvRecord).replaceAll("\\-+","").replaceAll("\\/+", ""),dayDifference)):isYMDFormat(csvRecord)?sysUID.yyyyMMddTODate(sysUID.deidentifiedDate(csvRecord.replaceAll("\\-+","").replaceAll("\\/+", ""),dayDifference)):csvRecord;
                                        String chaviValue = getConfigureValue(csvAttributeGeneric,chaviAttributeName,value).isEmpty()?value:getConfigureValue(csvAttributeGeneric,chaviAttributeName,value);                                       

                                        if(mappedAttribute.get(0)!=null){
                                            JSONArray js = new JSONArray();
                                            js.add(chaviValue);
                                            js.add(tableName);
                                            leafJSObj.put(chaviAttributeName, js);
                                            addIfDefault(leafJSObj,tableName);
                                            
                                            //System.out.println(csvRecords.get("mr_number")+" > "+chaviAttributeName+" : "+ js);
                                        }
                                }
                                if(validData != null && mappedAttribute == null){
                                    //System.out.println(mappedAttribute+" Here: "+validData
                                    JSONObject geneObj = new JSONObject();
                                    String geneCode = validData.split(":")[0].trim();
                                    String mutation_profile = validData.split(":")[1].trim();
                                    String mutation_result = getConfigureValue("expression_result","expression_result",mutation_profile).isEmpty()?mutation_profile:getConfigureValue("expression_result","expression_result",mutation_profile);
                                    geneObj.put("gene_tested", geneCode); //Key name should be same as the attributename of expressionprofile table.
                                    geneObj.put("expression_result",mutation_result);//Key name should be same as the attributename of expressionprofile table.
                                    geneArr.add(geneObj);
                                    leafJSObj.put("expressionprofile",geneArr);
                                    //System.out.println("Gene: "+leafJSObj);
                                }
                                if(validData == null && mappedAttribute == null && !unMappedAttribute.contains(csvAttributeGeneric))
                                {
                                    unMappedAttribute.add(csvAttributeGeneric);
                                }
                                
                            }
                            k++;
                        }
                        try{
                        if(leafJSObj.size()>0){
                            //System.out.println(leafJSObj);
                            String patientid = csvRecords.get(mr_number_field).replace("MR/", "");;
                            //leafJSObj.put("mr_number", patientid);
                            /* Get mandatory values from Config file */
                            try{
                            if(!prevPat.equalsIgnoreCase(patientid) && !getConfigureValue("mandatory").equals("")){
                                //System.out.println(patientIDMap.get(patientid)+"-------"+patientid);
                                JSONParser defaultParser = new JSONParser();
                                JSONObject defaultObj = (JSONObject) defaultParser.parse(getConfigureValue("mandatory"));                                
                                defaultObj.put("dcmpatientid", patientIDMap.get(patientid));
                                defaultObj.put("objectid","1");
                                leafjsArray.add(defaultObj);
                                prevPat = patientid;
                            }
                            }catch(Exception ex){System.out.println("Err10434: "+ex.toString());}
                            /* Get mandatory values from Config file */
                            String repeat_instance = "1";
                            if (headerName.contains("redcap_repeat_instance"))
                                repeat_instance = csvRecords.get("redcap_repeat_instance");
                            
                            leafJSObj.put("dcmpatientid", patientIDMap.get(patientid));
                            leafJSObj.put("objectid",repeat_instance);
                            leafjsArray.add(leafJSObj);
                           // System.out.println(leafjsArray.toString());
                            allPatientInfojsObj.put("leafTable", groupTableWise(leafjsArray.toString()));
                        }
                        //System.out.println("EE: "+leafjsArray);
                        k=0;
                        //System.out.println("----------------------------------End------------------------------------");
                        }catch(Exception ex){System.out.println("Err92840: "+ex.toString());}
                    }
                    try{
                    if(log.length()>0)
                        sysLog.tempLog(uid, log);
                    else{
                        writeJSONFile(System.getProperty("user.dir")+GetConfig.deidentifiedDCMSource,projectID+"_PatientEHR_"+projectID+"_"+System.currentTimeMillis()+".json",allPatientInfojsObj.toJSONString());

                        String attributeUnMappedList="";
                        for(int i=0;i<unMappedAttribute.size();i++)
                            attributeUnMappedList+="["+unMappedAttribute.get(i)+"] datafield is not recognized by the De-identification System\n------------------------------------\n";
                        sysLog.tempLog(uid, attributeUnMappedList);
                    }   
                    }catch(Exception ex){System.out.println("Err09259: "+ex.toString());}
        //System.out.println(getConfigureValue("mandatory")+ " : " +leafjsArray.toString());
        //JSONArray jsss = (JSONArray) allPatientInfojsObj.get("leafTable");
        //System.out.println(groupTableWise(jsss.toString()));                       
                }catch(Exception ex){System.out.println("Err2340: "+ex.toString());}
        }catch(Exception ex){System.out.println("Err5425: "+ex.toString());}
        //System.out.println(mu.getChaviAttribute("TMC20190001"));
    }
    
    public void setUniformCSVValue(String String){
        
    }

    public void writeJSONFile(String folder,String fileName, String jsObj){
        try{
            File destFolder = new File(folder);
            if(! destFolder.exists())
                {
                    destFolder.mkdirs();
                } 
            FileWriter fr = new FileWriter(destFolder+"/"+fileName);
            BufferedWriter br = new BufferedWriter(fr);  
            br.write(jsObj);
            br.close();
            fr.close();  
        Alert msg = new Alert(Alert.AlertType.INFORMATION);
        msg.setTitle("CSV De-identification status");
        msg.setContentText("RadglioHigh (Glioblastoma) de-identified JSON file is created >>"+destFolder+"/Globlastoma_PatientEHR_"+projectID+"_(Timestamp)"+".json");
        msg.show();                         
        }catch(Exception ex){System.out.println("Er3454:"+ex.toString());}    
    }
    private String getConfigureValue(String csvAttribute, String chaviAttribute, String csvValue){
        String value="";
        JSONParser jsParser = new JSONParser();
        try{
            FileReader reader = new FileReader(projectID+".json");
            Object obj = jsParser.parse(reader);
            JSONObject mapObj = (JSONObject) obj;; 
            JSONObject jsObj = (JSONObject) mapObj.get("valuesmap");
            //System.out.println(jsObj.size());
            JSONObject innerObj = (JSONObject) jsObj.get(csvAttribute);
            JSONArray jsArray = (JSONArray) innerObj.get(chaviAttribute);
            JSONArray csvValues = (JSONArray) jsArray.get(0);
            JSONArray chaviValues = (JSONArray) jsArray.get(1);
            boolean flag = true;
            for(int i=0;i<csvValues.size();i++){
                if(csvValues.get(i).equals(csvValue)){
                    flag = false;
                    return chaviValues.get(i).toString();
                }
                if(!csvValues.get(i).equals(csvValue) && flag && (i==csvValues.size()-1)){
                    log += "\n------------------------------\n";
                    log+="AttributeName: "+ chaviAttribute+" \nExcepted input values from CSV: "+csvValues+"\nOutput values for CAHVI: "+chaviValues+"\nInput value: "+csvValue+"\n";
                    log+="------------------------------\n";
                    flag = false;
                }
            }
        }catch(Exception ex){return "";}
        return value;
    }
    private String getConfigureValue(String attribute){
        String value="";
        JSONParser jsParser = new JSONParser();
        try{
            FileReader reader = new FileReader(projectID+".json");
            Object obj = jsParser.parse(reader);
            JSONObject jsObj = (JSONObject) obj;
            value = jsObj.get(attribute).toString();
        }catch(Exception ex){System.out.println("Ignore Err03249: "+ex.toString());return "";}
        return value;
    }    
    public String dmyTOymd(String dmfFormateDate) throws java.text.ParseException{
        Date initDate = new SimpleDateFormat("dd-MM-yyyy").parse(dmfFormateDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(initDate);    
    }
    public boolean isDMYFormat(String date){
        SimpleDateFormat dmy = new SimpleDateFormat("dd-MM-yyyy");
        dmy.setLenient(false);
        if(date==null)
            return false;
        try{
            Date dtdmy = dmy.parse(date);
            return true;
        }catch(Exception ex){return false;}
    }   
    public boolean isYMDFormat(String date){
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        ymd.setLenient(false);
        if(date==null)
            return false;
        try{
            Date dtymd = ymd.parse(date);
            return true;
        }catch(Exception ex){return false;}    
    }
    public void addIfDefault(JSONObject updateObj, String tableName){    
    JSONParser jsParser = new JSONParser();
        try{
            FileReader configReader = new FileReader(projectID+".json");
            Object obj = jsParser.parse(configReader);
            JSONObject jsObj = (JSONObject) obj;
            JSONArray jsArr = (JSONArray) jsObj.get("default");
            //System.out.println(jsArr);
            for(int e=0;e<jsArr.size();e++){
                //System.out.println(jsArr.get(e));
                JSONObject defUpdate = (JSONObject) jsArr.get(e);
                JSONArray arr = (JSONArray) defUpdate.get(defUpdate.keySet().iterator().next().toString()); 
                if(arr.get(1).equals(tableName))
                updateObj.put(defUpdate.keySet().iterator().next().toString(),arr);
            }
        }catch(Exception ex){System.out.println("Err34801:  "+ex.toString());}         
    }
    public JSONArray groupTableWise(String json){
		JSONParser parser = new JSONParser();
		JSONObject output = new JSONObject();
		JSONArray inputArr = new JSONArray();
		try{
			inputArr = (JSONArray) parser.parse(json);         
		}catch(Exception ex){System.out.println("EX09453: "+ex.toString());}
		JSONArray jsArr = new JSONArray();
		for(int i=0;i<inputArr.size();i++){
			output = (JSONObject) inputArr.get(i); 
			JSONObject groups = new JSONObject();
			JSONObject rows = new JSONObject();
			
			//System.out.println(output);
			for(Iterator iterator = output.keySet().iterator();iterator.hasNext();){
				String key = (String) iterator.next();
				if(output.get(key) instanceof JSONArray){
					JSONArray js = (JSONArray) output.get(key);
					//System.out.println((js.get(0) instanceof String)+" WW : "+js.get(0));
					//System.out.println(js.get(0)+" : "+js);
					if(js.get(0) instanceof String){
						String tableName = (String) js.get(1);
							JSONObject jsObj = new JSONObject();
							jsObj.put(key, (String) js.get(0));
							//System.out.println("GG: "+rows.get(tableName));
						if(rows.get(tableName) != null){
							JSONArray ss1 = (JSONArray) rows.get(tableName);            
							ss1.add(jsObj);
							//System.out.println("SS1: "+ss1);
							rows.put(tableName,ss1);
						}     
						if(rows.get(tableName) == null || rows.get(tableName).equals("")){
							JSONArray ss = new JSONArray();
							ss.add(jsObj);
							rows.put(tableName,ss);
						}
					}
					else
						rows.put(key,output.get(key));
						
				}
				else
					rows.put(key,output.get(key));
				//System.out.println(jsArr);
			}
			jsArr.add(rows);
		}  
		//System.out.println(jsArr);	
		return jsArr;
    }

}
