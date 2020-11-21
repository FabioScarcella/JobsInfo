/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.offerInfo;

import com.mycompany.allOffers.ExamineHTML;
import com.mycompany.extractInformation.ListOffersFiles;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gets all the links from ExamineHTML and it opens a connection
 * Connects to the page and it gets the HTML information from that single Offer
 * @author Fabio
 */
public class OfferGeneralInformation {
    private ExamineHTML examineHTML;
    
    public OfferGeneralInformation(ExamineHTML examineHTML){
        this.examineHTML = examineHTML;
    }
    
    /**
     * Gets all the previous href from ExamineHTML class
     */
    public void iterateThroughtEachElement(){
        ArrayList<String> links = examineHTML.getHrefOffers();
        
        int index = 0;
        
        /*for(String link:links){
            getHTMLOffer(link, index);
            ++index;
        }*/
        for(int i = 0; i < 10; i++){
            getHTMLOffer(links.get(i), index);
            ++index;
        }
        
        analyzeEachOffer();
    }
    /**
     * Gets the HTML information from a given URL
     * Once it gets the information, it creates a new saveOffer object to save it
     * @param href link of the web page
     * @param index Nothing important, just for the name after
     */
    private void getHTMLOffer(String href, int index){
        ArrayList<String> htmlOffer = new ArrayList<>();
        
        try{
            URLConnection connection = new URL(href).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null){
                htmlOffer.add(inputLine);
            }
                
            in.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(OfferGeneralInformation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OfferGeneralInformation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SaveSingleOfferHTML saveOffer = new SaveSingleOfferHTML(htmlOffer, index);
        }
    }
    
    
    private void analyzeEachOffer(){
        ListOffersFiles listOffer = new ListOffersFiles();
    }
}
