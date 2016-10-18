package Mutator;

import Frequency.Frequency;

import java.util.Random;

/**
 * Created by damir on 16/10/16.
 */
public class Mutator {
private Random rnd = new Random();

    public String mutateSentence(String sentence, Frequency frequency){
        String[] sp = sentence.split(" ");
        String ret = "";
        for(String s:sp){
            String rw = frequency.getRandomWord();

            if(rnd.nextBoolean()){ //skip or not?

            }
            else{
                if(rnd.nextBoolean()){//mutate or not?
                    ret = ret+" "+rw;
                }
                else{
                    ret = ret+" "+s;
                }
            }

            if(rnd.nextBoolean() && ret.length()<500){ //add new or not
                ret = ret +" "+rw;
            }

        }
        return ret.trim();
    }

}
