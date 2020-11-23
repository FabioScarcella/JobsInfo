/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mypackage.textInterpreter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.ml.AbstractTrainer;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 *
 * @author Fabio
 */
public class Trainee {
    public Trainee(){
        train();
    }
    
    private void train(){
        try {
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(
                    new File("OpenNLP"+File.separator + 
                            "en-categorizer.train"));
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);
            
            
            
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
            params.put(TrainingParameters.CUTOFF_PARAM, 0+"");
            params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
            
            DoccatModel 
                    model = DocumentCategorizerME.train("en", 
                                sampleStream, params, new DoccatFactory());
            
            
            //SAVE TRAINED
            BufferedOutputStream modelOut = new BufferedOutputStream(
                new FileOutputStream("OpenNLP"+File.separator+"en-categorizer-trained.bin"));
            model.serialize(modelOut);
            
            
            
            
            //TEST
            
            DocumentCategorizer doccat = new DocumentCategorizerME(model);
            String[] docWords = "Salary and benefits".replaceAll("[^A-Za-z]", " ").split(" ");
            double[] aProbs = doccat.categorize(docWords);
            
            // print the probabilities of the categories
            /*System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
            for(int i=0;i<doccat.getNumberOfCategories();i++){
                System.out.println(doccat.getCategory(i)+" : "+aProbs[i]);
            }
            System.out.println("---------------------------------");*/
 
            System.out.println("\n"+doccat.getBestCategory(aProbs)+" : is the predicted category for the given sentence.");
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trainee.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Trainee.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            
        }
    }
}
