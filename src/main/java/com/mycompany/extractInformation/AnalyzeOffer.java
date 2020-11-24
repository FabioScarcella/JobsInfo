/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.extractInformation;

import com.mypackage.textInterpreter.TextInterpreter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
public class AnalyzeOffer {
    private String fileName;
    private String requirmentName;
    private TextInterpreter interpreter;
    
    public AnalyzeOffer(String fileName){
        this.fileName = fileName;
        interpreter = new TextInterpreter();
    }
    
    public void ReadFile(){
        File input = new File("OffersHTML" + File.separator + fileName);
        ArrayList<Elements> requirments = new ArrayList<>();
            
        try {
            Document doc = Jsoup.parse(input, "UTF-8");
            Element divElements = doc.select("div.show-more-less-html__markup").first();
            if(divElements != null){
               Elements divChildren = divElements.children();
                for(int i = 0; i < divChildren.size(); ++i){
                    Element el = divChildren.get(i);
                    boolean isRequirment = interpreter.startClassification(el.text());
                    /*
                    FABIO VAGO, ARREGLA A PARTIR DE AQUI EL SIGUIENTE DIA
                    ALGUNAS LISTAS NO SON HIJOS DEL TITULO, ESTAN DENTRO DE UNA
                    <ul>. SIMPLEMENTE SI ES UN REQUIRMENT, COJER EL SIGUENTE I BUSCAR
                    EN SUS HIJOS.
                    */
                    if(isRequirment){
                        Elements childEl = el.children();
                        if(childEl.size() <= 1){
                            int nextChild = i + 1;
                            if(nextChild >= divChildren.size())
                                continue;
                            
                            Element nEl = divChildren.get(nextChild);
                            childEl = nEl.children();
                            
                            if(childEl.size() <= 1){
                                nextChild++;
                                if(nextChild >= divChildren.size()){
                                    continue;
                                }
                                nEl = divChildren.get(nextChild);
                                childEl = nEl.children();
                            }
                        }
                        
                        requirments.add(childEl);
                    }
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AnalyzeOffer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            saveRequirments(requirments);
        }
    }
    
    private void saveRequirments(ArrayList<Elements> childEl){
        ArrayList<String> elTexts = new ArrayList<>();
        
        for(Elements els:childEl){
            for(Element el:els){
                elTexts.add(el.text());
            }
        }
        
        if(elTexts.isEmpty()){
            return;
        }
        
        String fullName = "Requirments"+ File.separator + fileName + ".txt";
        File fOut = new File(fullName);
        
        System.out.println("File created with name: " + fullName + " with " + elTexts.size() + " requirments");
        
        try {
            FileOutputStream fos = new FileOutputStream(fOut);
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            for(int i = 0; i < elTexts.size(); ++i){
                bw.write(elTexts.get(i));
                bw.newLine();
            }
            
            bw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnalyzeOffer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnalyzeOffer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
