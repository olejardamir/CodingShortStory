package URL;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by damir on 16/10/16.
 */
public class UrlWorks {
    private ArrayList<String> urls = new ArrayList<String>();
    private Random rnd = new Random();


    public UrlWorks() throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("./Data/history.txt"));
        String line = "";
        while((line=read.readLine())!=null){
            urls.add(line);
        }
        read.close();
    }

    public void appendURL(String url) throws IOException {
        urls.add(url);
        BufferedWriter write = new BufferedWriter(new FileWriter("./Data/history.txt",true));
        write.write(url+"\n");
        write.close();
    }

    public String[] getRandomUrls(int n){
        if(n>=urls.size()){return (String[])urls.toArray();} //in case there is less urls than requested

        ArrayList<String> tmp = new ArrayList<String>();
        while(tmp.size()<n){
            String rndurl = urls.get(rnd.nextInt(urls.size()));
            if(!tmp.contains(rndurl)){tmp.add(rndurl);}
        }

        return (String[])tmp.toArray();
    }




}
