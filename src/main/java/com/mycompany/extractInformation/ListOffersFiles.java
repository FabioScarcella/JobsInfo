/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.extractInformation;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Fabio
 */
public class ListOffersFiles {
    ArrayList<String> nameOfFiles;
    
    public ListOffersFiles(){
        nameOfFiles = getNames();
        
    }
    
    private ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<>();
        
        File f = new File("OffersHTML");
        
        String[] pathNames = f.list();
        
        for(String name:pathNames){
            names.add(name);
            System.out.println(names);
        }
        
        return names;
    }
}
