/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.offerInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio
 */
public class SaveSingleOfferHTML {
    
    private ArrayList<String> htmlOffer;
    private String textName;
    
    public SaveSingleOfferHTML(ArrayList<String> htmlOffer, int index){
        this.htmlOffer = htmlOffer;
        
        createFile(String.valueOf(index));
    }
    
   /**
     * Creates a new File given a specified name from getDate() function
     */
    public void createFile(String indexValue){
        String fileName = getDate();
        String fullName = "OffersHTML/"+fileName + "-" + indexValue + ".txt";
        System.out.println(fullName);
        try{
            File myObj = new File(fullName);
            if(myObj.createNewFile()){
                System.out.println("File created " + fileName);
            } else{
                System.out.println("File already exists");
            }
        } catch (IOException ex) {
            Logger.getLogger(SaveSingleOfferHTML.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            this.textName = fullName;
            
            saveText(htmlOffer);
        }
    }
    
    /**
     * Gets current day and date given a specified format
     * @return String with day and date in yyyy-MM-dd HH-mm-ss format
     */
    private String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        
        return dtf.format(now);
    }
    
     /**
     * Saves the raw HTML data into a .txt
     * @param rawHtml ArrayList<String> that contains all the HTML info
     */
    public void saveText(ArrayList<String> rawHtml){
        File fOut = new File(textName);
        try {
            FileOutputStream fos = new FileOutputStream(fOut);
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            for(int i = 0; i < rawHtml.size(); i++){
                bw.write(rawHtml.get(i));
                bw.newLine();
            }
            
            bw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveSingleOfferHTML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveSingleOfferHTML.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            System.out.println("Offer with name: " + textName + " has been saved!");
        }
    }
}
