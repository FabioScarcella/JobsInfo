/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GetInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static java.lang.System.out;

/**
 *
 * @author Fabio
 */
public class ExamineHTML {
    private SaveHTMLInfo saveHtmlInfo;
    
    private String htmlName;
    private Document document; 
    
    
    public ExamineHTML(SaveHTMLInfo saveHtmlInfo){
        this.saveHtmlInfo = saveHtmlInfo;
        
        htmlName = saveHtmlInfo.getFileName();
        getFileInfo();
    }
    
    
    private void getFileInfo(){
        try {
            File input = new File(htmlName);
            
            Document doc = Jsoup.parse(input, "UTF-8");
            
            this.document = doc;
        } catch (IOException ex) {
            Logger.getLogger(ExamineHTML.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            extractOffers();
        }
    }
    
    private void extractOffers(){
        ArrayList<String> offers = new ArrayList<>();
        
        //Selects the complete UL list of offers
        Elements offersLink = document.select("ul.jobs-search__results-list");
        //Selects the individual offers of that list
        Elements allOffers = offersLink.select("a.result-card__full-card-link");
        
        
        for(Element oneOffer:allOffers){
            offers.add(oneOffer.attr("href"));
        }
        
        out.println(offers);
    }
}
