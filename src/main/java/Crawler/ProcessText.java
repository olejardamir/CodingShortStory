package Crawler;

import SentenceParser.parser;
import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.vector.HashMapTermVectorStorage;
import net.sf.classifier4J.vector.TermVectorStorage;
import net.sf.classifier4J.vector.VectorClassifier;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

/**
 * Created by damir on 16/10/16.
 */
public class ProcessText {
private Document doc;
private StringBuffer sb;
private StringBuffer bestsb;
private double score = 0;
private String bestSentence = null;
private String bestURL = null;
private String currentURL;
private String[] bestURLs;

    public ProcessText(){}

    public ProcessText(String url) throws IOException {
        doc = new Document(url);
        sb = new StringBuffer();
        bestsb = new StringBuffer();
        currentURL = url;
        doc = Jsoup.connect(url)
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(300)
                .get();

        getText();
    }

    public void reset(){
        bestsb = null;
        score = 0;
        bestSentence = null;
        bestURL = null;
        String[] bestURLs = null;
    }

    public String getBestSentence(){return this.bestSentence;}
    public String getBestURL(){return this.bestURL;}
    public String[] getBestURLs(){return this.bestURLs;}
    public StringBuffer getBestText(){return this.bestsb;}

    public void changeURL(String url)throws IOException {

        if (doc==null){doc = new Document(url);}
        try {
            if (url != null && !url.equals("null")) {
                currentURL = url;
                doc = Jsoup.connect(url)
                        .data("query", "Java")
                        .userAgent("Mozilla")
                        .cookie("auth", "token")
                        .timeout(30000)
                        .get();

                getText();
            }
        }catch(Exception ex){}
    }

    public void findClosestSentence(String sentence) throws ClassifierException {
        if(sb==null || sentence==null || sentence.length()<1){return;}
        String[] lines = sb.toString().split("\n");

        TermVectorStorage storage = new HashMapTermVectorStorage();
        VectorClassifier vc = new VectorClassifier(storage);

        vc.teachMatch("category", sentence);

        for(String s: lines){
            if(s.length()>10 && s.length()<500){
               double result = vc.classify("category", s);
                if(result>score){
                    bestSentence = s;
                    score=result;
                    if(bestURL!=currentURL) {
                        bestURL = currentURL;
                        getLinks();
                        bestsb = sb;
                    }
                }
            }

        }
    }

    private void getLinks() {
        Elements links = doc.select("a[href]");
        String[] ret = new String[links.size()];
        for (int i = 0; i < links.size(); i++) {
            Element link = links.get(i);
            ret[i] = link.attr("abs:href");
        }
        bestURLs = ret;
    }


    private void getText() throws IOException {
        String text = doc.body().text();
        parser p = new parser();
        sb = p.parseDocToStringBuffer(text);
    }






}
