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

    public ProcessText(String url) throws IOException {
        doc = Jsoup.connect(url)
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post();

        getText();
    }

    public String findClosestSentence(String sentence) throws ClassifierException {
        String[] lines = sb.toString().split("\\n");
        double distance = 0;


        TermVectorStorage storage = new HashMapTermVectorStorage();
        VectorClassifier vc = new VectorClassifier(storage);

        vc.teachMatch(sentence);

        String ret = null;
        for(String s: lines){
            if(s.length()>10 && s.length()<60){
               double result = vc.classify("category", "hello blah");
                if(result>distance){ret = s; distance=result;}
            }

        }
        return ret;
    }

    public String[] getLinks() {
        Elements links = doc.select("a[href]");
        String[] ret = new String[links.size()];
        for (int i = 0; i < links.size(); i++) {
            Element link = links.get(i);
            ret[i] = link.attr("abs:href");
        }
        return ret;
    }


    private void getText() throws IOException {
        String text = doc.body().text();
        parser p = new parser();
        sb = p.parseDocToStringBuffer(text);
    }






}
