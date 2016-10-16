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
            if(rnd.nextBoolean()){ret = ret+" "+frequency.getRandomWord();}
            else{ret = ret+" "+s;}
        }
        return ret.trim();
    }

}
