import Economy.Parser;

/**
 * Created by damir on 15/10/16.
 */
public class Start {

    public static void main (String[] args){
        String URL = "https://en.wikipedia.org/wiki/Cyberpunk";
        String finance = "http://download.finance.yahoo.com/d/quotes.csv?s=cadusd=x&f=nab";
        String inputSentence = "Advanced technological and scientific achievements";

        Parser parse = new Parser(finance); //initiate

        //Infinite loop begins here, you will need to stop the program to exit the loop
        while(true) {
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
            if(change==-1){
                //TODO read each page from URL stack as text
                //TODO Get the best sentence from each
                //TODO Find the best URL from a sentences stack in comparison to input sentence.
                //TODO Collect new links to a stack from a selected URL
                //TODO Map the frequency of the words (by adding the best URL, removing the stopwords)
                //TODO Append the current URL to a history
            }
            else if(change==1){
                //TODO Mutate the sentence, and display the message that there was a necessary change
                //TODO Randomly select whether to change URLs, and if there is a change, select a stack of URLs from a history otherwise continue
            }


        }


    }

}
