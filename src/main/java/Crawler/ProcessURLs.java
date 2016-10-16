package Crawler;

import net.sf.classifier4J.ClassifierException;

import java.io.IOException;
import Frequency.Frequency;

/**
 * Created by damir on 16/10/16.
 */
public class ProcessURLs {
    private ProcessText pt;
    private String bestSentence;
    private String bestURL;
    private Frequency fr;

    public ProcessURLs(String[] urls, String sentence) throws IOException, ClassifierException {
        for(String url:urls){
            pt.changeURL(url);
            pt.findClosestSentence(sentence);
        }
        fr.addLine(pt.getBestSentence());
    }


    public String getBestSentence(){return this.pt.getBestSentence();}
    public String getBestURL(){return this.pt.getBestURL();}
    public String[] getBestURLs(){return this.pt.getBestURLs();}
    public StringBuffer getBestText(){return this.pt.getBestText();}
    public Frequency getFrequency() { return this.fr; }


}
