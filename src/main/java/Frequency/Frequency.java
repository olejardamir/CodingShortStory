package Frequency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by damir on 16/10/16.
 */
public class Frequency {
    private HashMap<String, Integer> freqMap = new HashMap<String, Integer>();
    private ArrayList<String> stopwords = new ArrayList<String>();
    private Random rnd = new Random();

    public Frequency() throws IOException {
    loadStops();
    }


    private void loadStops() throws IOException {
        BufferedReader read = new BufferedReader(new FileReader("./Data/stopwords.txt"));
        String line="";
        while((line=read.readLine())!=null){
            stopwords.add(line.toLowerCase().trim());
        }
        read.close();
    }

    public void addLine(String line) throws IOException {
        if(stopwords==null){loadStops();}
        line = formatLine(line);
        String[] sp = line.split(" ");
        for(String s:sp){
            if(!stopwords.contains(s)){
                if(!freqMap.containsKey(s)){
                    freqMap.put(s,0);
                }
                else{
                    Integer i = freqMap.get(s)+1;
                    freqMap.put(s,i);
                }
            }
        }
    }

    //gets the rnd word according to Frequency.Frequency
    public String getRandomWord(){
        String ret="";
        Integer sum = 0;
        for(String key:freqMap.keySet()){
            Integer r = freqMap.get(key);
            sum = sum + r;
        }
        if(sum>0) {
            int rankey = rnd.nextInt(sum);

            for (String key : freqMap.keySet()) {
                ret = key;
                Integer r = freqMap.get(key);
                rankey = rankey - r;
                if (rankey <= 0) {
                    return ret;
                }
            }
        }
        return ret;
    }



    private String formatLine(String line){
        if(line==null){return "";}
        line = line.toLowerCase().trim();
        line = line.replaceAll("[^a-zA-Z ']", " ");
        while(line.contains("  ")){line = line.replace("  ", " ");}
        return line;
    }


    public String getKeywords(int n){
        String ret="";

        List<Integer> mapValues = new ArrayList<Integer>(freqMap.values());
        Collections.sort(mapValues, Collections.reverseOrder());
        ArrayList<Integer> vals = new ArrayList<Integer>();
        for(int t=0;t<n && t< freqMap.size();t++){
            vals.add(mapValues.get(t));
        }
        int count = 0;
        for(String key:freqMap.keySet()){
            Integer val = freqMap.get(key);
            if(vals.contains(val)){ret = ret+" "+key;}
            if (count==n){break;}
            count++;
        }

        return ret.trim();
    }



}
