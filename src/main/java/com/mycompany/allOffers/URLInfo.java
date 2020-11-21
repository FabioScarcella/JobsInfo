/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.allOffers;

import com.mycompany.jobsinfo.JobsInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Checks for a specified URL it gets the raw HTML
 * @author Fabio
 */
public class URLInfo {
    public URLInfo(String url){
        getURLInfo(url);
    }
    
    /**
     * Checks the URL and gets the HTML info
     * @param wUrl String from a Web
     */
    private void getURLInfo(String wUrl){
        ArrayList<String> rawHTML = new ArrayList<>();
        
        SaveHTMLInfo saveInfo = new SaveHTMLInfo(this);
        saveInfo.createFile();
        try{
            URLConnection connection = new URL(wUrl).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null){
                rawHTML.add(inputLine);
            }
                
            in.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(JobsInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JobsInfo.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            saveInfo.saveText(rawHTML);
             
         }
    }
}
