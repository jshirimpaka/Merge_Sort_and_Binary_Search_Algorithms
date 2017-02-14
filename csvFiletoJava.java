// Jean Shirimpaka
// 
// From csv file of string data to an array of double 
package Personal;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class csvFiletoJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
     
        // Initialize a String data to the file location
		String myFile = "studios/Personal/DataExperiment1.csv";
		// Use the File class of Java to import the .csv file
		File file = new File(myFile);
		
		// Initialization of List to be used in String to Double conversion
		List<Double> list=new ArrayList<Double>();
		
		// A try an catch to handle any exception
		try{
			// Import data using scanner
			@SuppressWarnings("resource")
			Scanner input = new Scanner(file);
			
			input.nextLine();
			
			// Constructing a new String data as long as the imported file still has more data 
			while(input.hasNextLine()){
				String data = input.nextLine();
				System.out.println(data);
				
				// Pulate the list with data Double type
				String[] val = data.split(",");
				double nber=Double.parseDouble(val[3]);
				//System.out.println(real);
				list.add(nber);
			}
			
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		// Converting list to an array of double primitive data type by taking advantage of Stream API
	    double[] myNberArray=list.stream().mapToDouble(Double::doubleValue).toArray();
	    
	    System.out.println("An arry of double data type: --------------------------");
	    
	    
	    // The enhanced for loop to print elements of array
	   for(double x: myNberArray)// In java-5.0,very simple
	     System.out.println(x);
	}
}
