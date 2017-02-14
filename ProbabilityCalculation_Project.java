/* Jean Shirimpaka
 * January 15, 2017
 * 
 * Probability calculations based on a large file of statistical Data
 
 -------------------------------------------------------------------------------------------------------
 * In this program, a .csv file is imported, a column of data is singled out to be further processed:
 * A column of String data is converted into an array
 * An array of strings is converted into an array of Double data type
 * Data are organized in increasing order
 * Location/index of the targeted value is determined and thus an approximation of the probability 
 * of finding a number less than the targeted value in the given data set
 ------------------------------------------------------------------------------------------------------
 */

package Personal;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProbabilityCalculation_Project {

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
				//System.out.println(data);
				
				// Pulate the list with data Double type
				String[] val = data.split(",");
				double real=Double.parseDouble(val[3]);
				//System.out.println(real);
				list.add(real);
			}
			
 
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		// A list to an array by using Method Reference of Java-8
	    double[] myNberArray=list.stream().mapToDouble(Double::doubleValue).toArray();
	    // The enhanced for loop to print elements of array
	    for(double x: myNberArray)// In java-5.0,very simple
	    	System.out.println(x);
		
	    // Ordering data in assending order
	    System.out.print("A new List of data in assending order: ");
	    System.out.println(" ");
	    double[] newData =orderingDataProcess(myNberArray); 
	    for(double y: newData)
	    	System.out.println(y);
	    
	    
	    // Finding the index of my number of interest
	    
	    // Pick a number in the list of your choice to compute the probability for
	    double theError = 1.25;
	    
	    
	    System.out.println("---------------------------------------------------------------");
	    int indexofMaxError = findIndexofNber(newData,theError, 0, newData.length-1);
	    System.out.println("Index of 1.25 is: "+ indexofMaxError);
	    
	    // Compute the probability based on the data size and the index of the targeted erro value
	    double prob = 100*(double)indexofMaxError/newData.length;
	    System.out.println("Expected probability for error less than 1% is: "+ prob + " %");
	}
	
	
	
	//Arranging a given array of double in incremental-order of values 
    public static double[] orderingDataProcess(double [] oldArr){
    	
    	// Base case: If the array has only 1, the new array is the same as the old array
    	if(oldArr.length <=1){
    		return oldArr;
    	}
    	
    	// Find the index for the middle element in the aarray
    	int midVal=oldArr.length/2;
    	
    	// left and arrays declarations and left array initialized to having the size of index
    	// of mid value in the array
    	double [] leftPart = new double[midVal];
    	double [] rightPart;
    	
    	// Right array initialization to mid or mid +1 depending on the original array size
    	if(oldArr.length%2 == 0){
    		rightPart = new double [midVal];
    	}else{
    		rightPart = new double [midVal+1];
    	}
    	
    	// new array construction
    	double [] newArray = new double[oldArr.length];
    	
    	// left side array population from 0 to mid value indices of old array 
    	for(int i = 0; i < midVal;i++){
    		leftPart[i] = oldArr[i];
    	}
    	
    	int m = 0;
    	// right side array population from mid value to ending indices of old array 
    	for(int j = midVal; j < oldArr.length;j++){
    		rightPart[m] = oldArr[j];
    		m++;
    	}
    	
    	// Call the method over and over until we reach the above base case
    	leftPart = orderingDataProcess(leftPart);
    	rightPart = orderingDataProcess(rightPart);
    	
    	// Create the new array by combining both sides: left and right
    	newArray = combining2Arr(leftPart, rightPart);
    	
    	return newArray;
    }
    
    // combining two arrays in order and return an ordered array
    public static double [] combining2Arr(double [] leftArr, double [] rightArr){
    	// Final array and its length declaration and initialization 
    	int lengthFinal = leftArr.length + rightArr.length;
    	double [] finalArr = new double[lengthFinal];
    	
    	// Indices for left, right and final array declaration
    	int indLeft = 0;
    	int indRight = 0;
    	int indFinal = 0;
    	
    	// Keep combining left and right arrays as long as any of the arrays still has elements in it.
    	while(indLeft < leftArr.length || indRight < rightArr.length){
    		//Check whether left and right arrays have elements
    		if(indLeft<leftArr.length && indRight < rightArr.length){
    			// when a current element at left is less than a current element at right,
    			// element at left is populated into the final array. Otherwise, the element
    			// at right is populated into the final array
    			if(leftArr[indLeft] <= rightArr[indRight]){
    				finalArr[indFinal] = leftArr[indLeft];
    				indLeft++;
    				indFinal++;
    			}
    			else{
    				finalArr[indFinal] = rightArr[indRight];
    				indRight++;
    				indFinal++;
    			}
    		}
    		//Check whether only left has elements
    		else if(indLeft < leftArr.length){
    			finalArr[indFinal] = leftArr[indLeft];
				indLeft++;
				indFinal++;
    		}
    		//Check whether only right has elements
    		else if(indRight < rightArr.length){
    			finalArr[indFinal] = rightArr[indRight];
				indRight++;
				indFinal++;
    		}
    	}
    	return finalArr;
    	
    }
    
    
    // Searching a particular number in an ordered list of data
    public static int findIndexofNber(double [] Arr, double myVal, int arrayBgn, int arrayEnd){
    	// Base case: If the array has only 1 or no element
    	if((arrayBgn- arrayEnd)<= 1){
    		if(Arr[arrayBgn]==myVal){
        		return arrayBgn;
        	}
    		if(Arr[arrayEnd]==myVal){
    			return arrayEnd;
    		}	
    	}
    	
    	// index of the middle value
    	int midVal = (arrayBgn + arrayEnd)/2;
    	
    	// When the targeted value is less than the middle value, the key value is from first to middle of array
    	// Otherwise, it is located in from middle to the end of the array
    	if(Arr[midVal]>myVal){
    		return findIndexofNber(Arr,myVal,0,midVal);
    	}else{
    		return findIndexofNber(Arr,myVal,midVal, arrayEnd);
    	}
      
    }  

}
