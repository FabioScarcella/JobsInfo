/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mypackage.textInterpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

/**
 *
 * @author Fabio
 */
public class TextInterpreter {
    private String text;
    
    public TextInterpreter(){
        
    }
    
    private void startClassification(){
        try {
            //ACCESS TO THE TRAINED FILE AND CREATE THE MODEL
            InputStream is = 
                    new FileInputStream("OpenNLP"+File.separator+"en-categorizer-trained.bin");
            DoccatModel m = new DoccatModel(is);
            
            String[] inputText = text.replaceAll("[^A-Za-z]", " ").split(" ");
            
            DocumentCategorizerME myCategorizer =
                    new DocumentCategorizerME(m);
            
            double[] outcomes = myCategorizer.categorize(inputText);
            String category = myCategorizer.getBestCategory(outcomes);
            
            System.out.println(text + " and the category is: " + category);
        } catch (IOException ex) {
            Logger.getLogger(TextInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}
