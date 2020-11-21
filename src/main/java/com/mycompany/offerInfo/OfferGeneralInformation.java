/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.offerInfo;

import com.mycompany.allOffers.ExamineHTML;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Fabio
 */
public class OfferGeneralInformation {
    private ExamineHTML examineHTML;
    
    public OfferGeneralInformation(ExamineHTML examineHTML){
        this.examineHTML = examineHTML;
    }
    
    public void iterateThroughtEachElement(){
        ArrayList<String> links = examineHTML.getHrefOffers();
        
        int index = 0;
        
        for(String link:links){
            getHTMLOffer(link, index);
            ++index;
        }
    }
    
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
            htmlOffer = checkLength(htmlOffer);
            
            SaveSingleOfferHTML saveOffer = new SaveSingleOfferHTML(htmlOffer, index);
        }
        
        
        
    }
    
    
    private ArrayList<String> checkLength(ArrayList<String> htmlOffer){
        ArrayList<String> fixedList = new ArrayList<>();
        
        if(htmlOffer.size() > 1){
            return htmlOffer;
        }
        
        Document doc = Jsoup.parseBodyFragment(htmlOffer.get(0));
        Elements els = doc.getAllElements();
        
        for(Element el:els){
            fixedList.add(el.toString());
        }
        
        return fixedList;
    }
    
}
