/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.extractInformation;

import java.io.File;
import java.io.IOException;
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
public class AnalyzeOffer {
    private String fileName;
    
    public AnalyzeOffer(String fileName){
        this.fileName = fileName;
    }
    
    public void ReadFile(){
        File input = new File("OffersHTML/"+fileName);
            
        try {
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements divElements = doc.select("show-more-less-html__markup");
            System.out.println(divElements);
        } catch (IOException ex) {
            Logger.getLogger(AnalyzeOffer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
