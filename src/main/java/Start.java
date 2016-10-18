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

            int score = 0;
            int finScore = 0;
            boolean logic = true;

            UrlWorks urlWorks = new UrlWorks();
            Frequency frequency = null;
            Random rnd = new Random();

            Mutator mutator = new Mutator();

            String[] URLs = urlWorks.getRandomUrls(3);
//            String finance = "http://download.finance.yahoo.com/d/quotes.csv?s=BNS.TO&f=nab";
            String finance = "http://download.finance.yahoo.com/d/quotes.csv?s=cadusd=x&f=nab";
//            String finance = "http://download.finance.yahoo.com/d/quotes.csv?s=clx16.nym&f=nab";
            String inputSentence = "Top stories Top stories Britain could pay billions to EU in exchange for single market access after Brexit The Cabinet is reportedly discussing the measure in pursuit of a softer Brexit that would relieve pressure on the City of London US says Britain needs to sort out its relationship with EU Pound's fall is a good thing, according to Bank of England policymaker Desperate Iraqis paying up to $1,000 to flee Mosul as assault begins Tory councillor starts petition to make support for Remain a crime Cabinet rift deepens as Brexit row intensifies Police reveal most likely cause of Ben Needham's death More headlines Russia Today bank accounts frozen in UK What you need to know about Russia Today - and their links to Moscow Live Brexit legal challenge underway as campaigners seek to stop Article 50 Hillary Clinton takes huge lead over Donald Trump in latest poll Euro is a house of cards ready to collapse, key founder warns Several missing and injured after explosion in Germany Subscribe to the Daily Edition, our newspaper for your tablet Donald Trump blames animals backing Hillary Clinton for office bomb Dubai's Emir flies £250,000 of aid to Haiti in his private jet Irish leaders warn of devastating economic consequences of Brexit UK to take 10 refugee children from Calais Jungle with more to follow talking points Joshua Zitser After Brexit, I've changed my mind on Scottish independence The world's happiest man says one thing is making him unhappy Jessica Brown Boris Johnson knew this week's financial disasters would happen YouTubers are eating 10,000 calories in a day to impress their fans Tess Finch Lees I watched as my two-year-old brother was mauled by a dog Most popular I've sold The Big Issue for five years - without it, I'd be dead Seb Munoz London's Oil and Money conference is morally bankrupt The new scramble for Africa' has seen 101 companies listed on the London Stock Exchange, most of them British, which now collectively control £800bn worth of Africa’s most valuable resources Ben Chu How Marmite conspiracy theories exposed the ignorance of Brexiteers Despite the deluge of headlines it generated, there was actually nothing unusual about the process of a supermarket haggling with its supplier over prices Trump is failing at basically everything now.";

            Parser parse = new Parser(finance); //initiate
            ProcessURLs purls= new ProcessURLs();

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
                score = score + change;
                if(score>3){
                    logic = !logic;
                    score = 0;
                    finScore--;
                } //it means that system has adjusted and that you are losing so we need to reverse the logic.
                if(score<-3){
                    score = 0;
                    finScore++;
                } //set a new bar

                if(!logic){change=change*-1;} //verify which logic to use.

                //If there is no change, there is no need to do anything, which is also harmful but not as negative change
                //If there is negative change, the program does the same thing as with no change
                //If there is a positive change, the program changes the sentence and/or chooses another URL (from a history.txt)
                if (change == 1) {

                    //read each page from URL stack as text
                      purls.process(URLs, inputSentence);

                    //move forward
                    URLs = purls.getBestURLs();

                    //Append the current URL to a history
                    String bestURL = purls.getBestURL();
                    urlWorks.appendURL(bestURL);
                    urlWorks.appendURLs(URLs);

                    //keep the frequency
                    frequency = purls.getFrequency();

                    //reset
                    purls.reset();

                    if(URLs!=null && URLs.length>0) {
                        String[] N = new String[15];
                        for (int t = 0; t < 15; t++) {
                            N[t] = URLs[rnd.nextInt(URLs.length)];
                        }

                        URLs = N;


                        //display the message
                        System.out.println("Success, you are one step closer to a freedom!");
                        System.out.println("If you want to learn what might be highly relevant to your freedom, input these words into a search engine: "+frequency.getKeywords(5));

                    }
                    else{ //if there were no connections made, too many timeouts or similar
                        System.out.println("The system has tricked you into a dead-end, trying different URLs");
                        URLs = urlWorks.getRandomUrls(5);
                    }

                System.out.println("===============BATTLE LOG, SCORE: "+finScore+" =====================");
                } else if (change == -1) {
                    if(frequency!=null){
                    System.out.println("The system is fighting back, we need to adjust the parameters.");
                    boolean mutate = rnd.nextBoolean();
                    //Mutate the sentence, and display the message that there was a necessary change
                    if(mutate) {
                        inputSentence = mutator.mutateSentence(inputSentence, frequency);
                        System.out.println("Your input sentence is now: " + inputSentence);
                        if(rnd.nextBoolean()){
                            System.out.println("We are also changing your URLs");
                            URLs = urlWorks.getRandomUrls(5);
                        }
                    }
                    else if(!mutate) {
                        System.out.println("We are trying different urls now...");
                        URLs = urlWorks.getRandomUrls(5);
                    }
                    System.out.println("===============BATTLE LOG, SCORE: "+finScore+" =====================");
                    }
                    else{
                        System.out.println("Waiting for the system's response...");
                    }
                    }


            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
