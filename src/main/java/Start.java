import Crawler.ProcessURLs;
import Economy.Parser;
import Frequency.Frequency;
import Mutator.Mutator;
import URL.UrlWorks;

import java.util.Random;

/**
 * Created by damir on 15/10/16.
 */
public class Start {

    public static void main (String[] args){
        try {
            UrlWorks urlWorks = new UrlWorks();
            Frequency frequency = null;
            Random rnd = new Random();

            Mutator mutator = new Mutator();

            String[] URLs = urlWorks.getRandomUrls(3);
            String finance = "http://download.finance.yahoo.com/d/quotes.csv?s=cadusd=x&f=nab";
            String inputSentence = "Advanced technological and scientific achievements";

            Parser parse = new Parser(finance); //initiate

            //Infinite loop begins here, you will need to stop the program to exit the loop
            while (true) {
                //you are allowed 2000 free hits per minute, and this program does one per second.
                Long now = System.nanoTime();
                Long wait = System.nanoTime();
                while ((wait - now) < 1000000000) {
                    wait = System.nanoTime();
                } //this halts the thread for 1sec.


                parse.next(); //gets the next set of prices

                //The program is now tracking the average price (bid+ask)/2
                int change = parse.bidChange();
                //If there is no change, there is no need to do anything, which is also harmful but not as negative change
                //If there is negative change, the program does the same thing as with no change
                //If there is a positive change, the program changes the sentence and/or chooses another URL (from a history.txt)
                if (change == -1) {
                    //read each page from URL stack as text
                    ProcessURLs purls= new ProcessURLs(URLs, inputSentence);

                    //Append the current URL to a history
                    urlWorks.appendURL(purls.getBestURL());

                    //keep the frequency
                    frequency = purls.getFrequency();

                    //display the message
                    System.out.println("Success, you are one step closer to a freedom!");
                    System.out.println("If you want to learn what might be highly relevant to your freedom, input these words into a search engine: "+frequency.getKeywords(5));
                    System.out.println("===============BATTLE LOG=====================");
                } else if (change == 1) {
                    if(frequency==null){
                        System.out.println("System will win, we are preventing you from losing. Please restart the program or change the input parameters.");
                        break;
                    }

                    System.out.println("The system is fighting back, we need to adjust the parameters.");
                    boolean mutate = rnd.nextBoolean();
                    //Mutate the sentence, and display the message that there was a necessary change
                    if(mutate) {
                        inputSentence = mutator.mutateSentence(inputSentence, frequency);
                        System.out.println("Your input sentence is now: " + inputSentence);
                        if(rnd.nextBoolean()){
                            System.out.println("We are also changing your URLs");
                            URLs = urlWorks.getRandomUrls(3);
                        }
                    }
                    else if(!mutate) {
                        System.out.println("We are trying different urls now...");
                        URLs = urlWorks.getRandomUrls(3);
                    }
                    System.out.println("===============BATTLE LOG=====================");

                    }


            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
