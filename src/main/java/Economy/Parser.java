package Economy;

import com.opencsv.CSVParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by damir on 15/10/16.
 */
public class Parser {
    private double bid;
    private double ask;
    private double average;

    private double bid_old;
    private double ask_old;
    private double average_old;

    private String url;




    /**
     * Parses the URL into bid, ask, and calculates the average
     * @param url the URL to a desired target
     */
    public Parser(String url){
    this.url = url;
    next();
    bid_old=bid;
    ask_old=ask;
    ask_old=average;
    }


    public void next(){
        try {

            bid_old=bid;
            ask_old=ask;
            ask_old=average;

            String line = download(url);
            CSVParser csvParser = new CSVParser(',','"');
            String[] parsed = csvParser.parseLine(line);
            ask = Double.parseDouble(parsed[1]);
            bid = Double.parseDouble(parsed[2]);
            average = (ask+bid)/2;
        }catch (Exception e){e.printStackTrace();}
    }


    public int bidChange(){
        if(bid_old>bid){return -1;} //is falling
        else if(bid_old<bid){return 1;} //is rising
        return 0; //no change
    }

    public int askChange(){
        if(ask_old>ask){return -1;} //is falling
        else if(ask_old<ask){return 1;} //is rising
        return 0; //no change
    }

    public int averageChange(){
        if(average_old>average){return -1;} //is falling
        else if(average_old<average){return 1;} //is rising
        return 0; //no change
    }


    /**
     *
     * @return the bid price
     */
    public double getBid(){return this.bid;}

    /**
     *
     * @return the ask price
     */
    public double getAsk(){return this.ask;}

    /**
     *
     * @return the average price
     */
    public double getAverage(){return this.average;}


    /**
     * Downloads the CSV from Internet, this should be an anonymous action from anonymous source with a Tor-like network, otherwise easily tracked.
     * @param url url to finance CSV
     * @param url
     * @return The relevant data to be parsed
     */
    private String download(String url){
        String ret = null;
        try{
            URL oracle = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            ret = in.readLine();
            in.close();
        }catch (Exception e){e.printStackTrace();}
        return ret;
    }

}
