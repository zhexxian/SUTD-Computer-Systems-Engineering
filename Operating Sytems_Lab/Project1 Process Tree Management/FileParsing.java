// verify line reading of the testproc.txt file

import java.io.*;

public class FileParsing {

	public static void main(String args[]){ 

		// FileReader reads text files in the default encoding.
		try{
        	FileReader fileReader = new FileReader(args[0]);
        	// Always wrap FileReader in BufferedReader.
	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        try {

	        	String line = bufferedReader.readLine();

	        	while(line != null){
	                System.out.println(line);
	                line = bufferedReader.readLine();  
	            } 

	        } catch (IOException e) {

	            System.out.println(e);
	        } 

    	} catch (FileNotFoundException e) {
    		System.out.println(e);
    	}

        
	}

}