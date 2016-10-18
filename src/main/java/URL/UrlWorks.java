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
        try {
            if(url!=null && !url.contains("null")) {
                urls.add(url);
                BufferedWriter write = new BufferedWriter(new FileWriter("./Data/history.txt", true));
                write.write(url + "\n");
                write.close();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public String[] getRandomUrls(int n){
        if(n>=urls.size()){
            String[] ret = new String[urls.size()];
            while(urls.contains(null)){urls.remove(urls.indexOf(null));}
            for(int t=0;t<urls.size();t++){ret[t]=urls.get(t);}
            return ret;
        } //in case there is less urls than requested

        ArrayList<String> tmp = new ArrayList<String>();
        while(tmp.size()<n){
            String rndurl = urls.get(rnd.nextInt(urls.size()));
            if(!tmp.contains(rndurl)){tmp.add(rndurl);}
        }


        //fix
        while(tmp.contains(null)){tmp.remove(tmp.indexOf(null));}
        String[] ret = new String[tmp.size()];
        for(int t=0;t<tmp.size();t++){ret[t]=tmp.get(t);}

        return ret;
    }


    public void appendURLs(String[] urls) throws IOException {
        if(urls!=null) {
            for (String s : urls) {
                if (s != null) {
                    appendURL(s);
                }
            }
        }
    }
}
