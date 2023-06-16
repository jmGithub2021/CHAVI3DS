/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import UserHome.UserIndexController;
import UserHome.UserhomeController;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author SURAJIT
 */


public class UnZip {
    String destDir = "";
    UnZip(){
        GetConfig cf = new GetConfig();
        destDir = cf.tempDirectory;
        try{
            FileUtils.deleteDirectory(new File(destDir));
        }catch(Exception ex){
            System.out.println("Temp file is not deleted : "+ex.toString());
        } 
    }

    public String unzipFile(String zipFilePath) {
        File dir = new File(destDir);
        String zipPath="";
        if(!dir.exists()) 
            dir.mkdirs();
        FileInputStream fis;
        byte[] buffer = new byte[2024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry ze = zis.getNextEntry();
            ZipFile zf = new ZipFile(zipFilePath);
            int noOfFiles = zf.size();
            UserhomeController.totalFile = noOfFiles;
            //System.out.println("FileSize : "+zf.size());
            int i=0;
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                UserhomeController.unzipStatus = (double) ((i+1)/(double)noOfFiles);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                zipPath = newFile.getParent();
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                zis.closeEntry();
                ze = zis.getNextEntry();
                i++;
            }
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
       return zipPath; 
    }
    public boolean isDCMFile(String fileName){
        String ext = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        if(ext.equalsIgnoreCase("dcm") && ext != null)
            return true;
        else
            return false;
    }

}
