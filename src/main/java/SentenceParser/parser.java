package SentenceParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class parser{

private ArrayList<String> abbr = new ArrayList<String>();

//loads the abbEngl into an array
public parser(){

    try {
    	BufferedReader br = new BufferedReader(new FileReader("./Data/abbEngl"));
        String line = br.readLine();
        while (line != null) {
           abbr.add(line);
            line = br.readLine();
        }
        br.close();
    }  
    catch (Exception e) {
       System.out.println("Error reading the abbEngl (list of English Abbreviations) file"); 
    }

}


public StringBuffer parseDocToStringBuffer(String input){

	try {
		StringBuffer fw = new StringBuffer();




			String[] splitline = input.split(" ",-1);
			for (int t=0;t<splitline.length;t++){
				String word = splitline[t];

				if (word.startsWith("\'") || word.startsWith("\"")  ){
					word = word.substring(1,word.length());
				}
				if (word.endsWith("\'") || word.endsWith("\"")  ){
					word = word.substring(0, word.length()-1);
				}
				if (word.endsWith("\'.") || word.endsWith("\".")  ){
					word = word.substring(0, word.length()-2);
					word=word+".";
				}
				if (word.endsWith("\'!") || word.endsWith("\"!")  ){
					word = word.substring(0, word.length()-2);
					word=word+"!";
				}
				if (word.endsWith("\'?") || word.endsWith("\"?")  ){
					word = word.substring(0, word.length()-2);
					word=word+"?";
				}

				if (word.length()>1 && !word.endsWith(".") && !word.endsWith("!") && !word.endsWith("?") && !abbr.contains(word)){
					fw.append(word+" ");
				}
				else if (word.length()>1 && !abbr.contains(word)){fw.append(word+System.getProperty("line.separator"));}
				else if (word.length()>0){fw.append(word+" ");}




		}
		return fw;
	}
	catch (Exception e) {
		System.out.println("Error parsing the "+input+" file.");
	}
	return null;
}


public void parseDocToFile(String input, String output){

    try {
    	BufferedReader br = new BufferedReader(new FileReader(input));
    	FileWriter fw = new FileWriter(new File(output));
    	
        String line = br.readLine();
        while (line != null) {
           
        		
        	String[] splitline = line.split(" ",-1); 
        	for (int t=0;t<splitline.length;t++){
        		String word = splitline[t];
        		
        		if (word.startsWith("\'") || word.startsWith("\"")  ){
					word = word.substring(1,word.length());
					}
        		if (word.endsWith("\'") || word.endsWith("\"")  ){
					word = word.substring(0, word.length()-1);
        		}
        		if (word.endsWith("\'.") || word.endsWith("\".")  ){
					word = word.substring(0, word.length()-2);
					word=word+".";
        		}
        		if (word.endsWith("\'!") || word.endsWith("\"!")  ){
					word = word.substring(0, word.length()-2);
					word=word+"!";
        		}
        		if (word.endsWith("\'?") || word.endsWith("\"?")  ){
					word = word.substring(0, word.length()-2);
					word=word+"?";
        		}
        		
        		if (word.length()>1 && !word.endsWith(".") && !word.endsWith("!") && !word.endsWith("?") && !abbr.contains(word)){
        			fw.write(word+" ");
        		}
        		else if (word.length()>1 && !abbr.contains(word)){fw.write(word+System.getProperty("line.separator"));}
        		else if (word.length()>0){fw.write(word+" ");}
        			
        	}
        	
        	
        	
            line = br.readLine();
        }
        br.close();
        fw.close();
    }  
    catch (Exception e) {
       System.out.println("Error parsing the "+input+" file into "+output+" file."); 
    }

}

}