package Crawler;

import net.sf.classifier4J.ClassifierException;

import java.io.IOException;
import java.util.ArrayList;

import Frequency.Frequency;

/**
 * Created by damir on 16/10/16.
 */
public class ProcessURLs {
    private ProcessText pt;
    private String bestSentence;
    private String bestURL;
    private Frequency fr = new Frequency();
    private ArrayList<String> visited = new ArrayList<String>();



    public ProcessURLs( ) throws IOException, ClassifierException {

    }

    public void process(String[] urls, String sentence) throws IOException, ClassifierException {
        pt = new ProcessText();
        if(urls!=null) {
            for (String url : urls) {
                if (url != null) {
                    if (!url.contains("query=Java") && !url.contains("null") && !visited.contains(url)) {
                        System.out.println(url);
                        pt.changeURL(url);
                        pt.findClosestSentence(sentence);
                        visited.add(url);
                        if (visited.size() >= 10000) {
                            visited.remove(0);
                        }
                    }
                }
            }
        }
        fr.addLine(pt.getBestSentence());
    }

    public void reset(){this.pt.reset();}
    public String getBestSentence(){return this.pt.getBestSentence();}
    public String getBestURL(){return this.pt.getBestURL();}
    public String[] getBestURLs(){return this.pt.getBestURLs();}
    public StringBuffer getBestText(){return this.pt.getBestText();}
    public Frequency getFrequency() { return this.fr; }


}
