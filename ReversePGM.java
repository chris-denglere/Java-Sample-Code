import java.util.*;		// This line is needed to use the Scanner object
import java.io.*;		// This line is needed to use the File object

/**
 * Converts a PGM file that uses the P2 format into its reverse image.
 *
 * @author Chris D'Englere
 */ 
public class ReversePGM {

	/**
	* Calls the checkCommandLineArguments, getInputScanner, and createOriginalArray
	* methods. This begins the flow of the program.
	*
	* @param args command line arguments
	*/
	public static void main(String[] args) {
		checkCommandLineArguments(args);
		getInputScanner(args[0]);
		createOriginalArray(args[0]);
	}
	
	/**
	* Checks the length of the String array containing the command line arguments provided
	* by the user. If the length is anything other than 1, an error message is output
	* to the console and the program is terminated. Checks that the filename extension is
	* .pgm. If it is not, an error message is output to the console and the program is
	* terminated.
	*
	* @param args String array containing command line arguments, if any, entered by the user
	*/
	public static void checkCommandLineArguments(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java ReversePGM filename");
			System.exit(1);
		}
		if (!args[0].endsWith(".pgm")) {
			System.out.println("Filename must have .pgm extension");
			System.exit(1);
		}
	}
	
	/**
	* Tries to open the file whose name is stored in the String filename and create a Scanner
	* using that file. A try/catch block is used to handle the FileNotFoundException. If the
	* file cannot be found, an error is output to the console and the program is terminated.
	* Otherwise, the Scanner is then returned to the method that called getInputScanner.
	*
	* @param filename String containing the name of the input file
	* @return Scanner for the input file
	* @throws FileNotFoundException thrown if the specified file cannot be found
	*/
	public static Scanner getInputScanner(String filename) {
		Scanner input = null;
		try {
			input = new Scanner(new File(filename));
		}
		catch (FileNotFoundException e) {
			System.out.printf("%s (No such file or directory)\n", filename);
			System.exit(1);
		}
		return input;
	}
	
	/**
	* Creates a new PrintStream called output that will overwrite the original input file that
	* the user specified. The PrintStream is returned to the method that called getOutputPrintStream.
	*
	* @param filename String containing the name of the input file
	* @return PrintStream for overwriting the original input file
	* @throws FileNotFoundException thrown if the specified file cannot be found
	*/
	public static PrintStream getOutputPrintStream(String filename){
		PrintStream output = null;
		try {
			output = new PrintStream(new File(filename));
			}
			catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
		return output;
	}
	
	/**
	* Creates and fills a header array containing the metadata (columns, rows, max grayscale value)
	* associated with the PGM file. Creates and fills an array containing the data from the original
	* PGM file. Calls the method outputOperations, passing it the format type, header array, pgm array
	* and filename.
	*
	* @param filename String containing the name of the input file
	*/
	public static void createOriginalArray(String filename) {
		Scanner input = getInputScanner(filename);
		String format_type = input.next();
		int columns = input.nextInt();
		int rows = input.nextInt();
		int max_grayscale_value = input.nextInt();
		
		//Creating jagged array for header info
		int[][] header_array = new int[2][];
		header_array[0] = new int[2];
		header_array[1] = new int[1];
		header_array[0][0] = columns;
		header_array[0][1] = rows;
		header_array[1][0] = max_grayscale_value;
		
		//Creating array for main PGM content
		int pgm_array[][] = new int [rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				pgm_array[i][j] = input.nextInt();
			}
		}
		outputOperations(format_type, header_array, pgm_array, filename);
	}
	
	/**
	* Creates the reversed array by calling the reverse method, passing it the original pgm array,
	* and receiving the return from the method call. Overwrites the input file with the format type,
	* metadata, and all values in the reversed array.
	*
	* @param format_type String containing the format type of the PGM file
	* @param header_array integer array containing the metadata (columns, rows, max grayscale value) stored in the header area
	* @param pgm_array integer array containing the PGM file main content
	* @param filename String containing the name of the input file
	*/
	public static void outputOperations (String format_type, int[][] header_array, int[][] pgm_array, String filename) {
		int[][] reversed_array = reverse(pgm_array);
		PrintStream output = getOutputPrintStream(filename);
		output.println(format_type);
		output.printf("%d %d\n", header_array[0][0], header_array[0][1]);
		output.printf("%d\n", header_array[1][0]);
		for (int i = 0; i < reversed_array.length; i++) {
			for (int j = 0; j < reversed_array[i].length; j++) {
				output.printf("%3d", reversed_array[i][j]);
			}
			output.println();
		}
	}
	
	/**
	* Returns a new 2D array with the same number of rows and columns as the pgm array.
	* Each row in the returned array is in reverse order from the original array.
	* 
	* @param pgm integer array containing the original pgm array main content
	* @return integer array containing the reversed pgm array main content
	*/
	public static int[][] reverse(int[][] pgm) {
		int[][] reversed_array = new int[pgm.length][pgm[0].length];
		for (int i = 0; i < pgm.length; i++) {
			int k = 0;
			for (int j = pgm[i].length - 1; j >= 0; j--) {
				reversed_array[i][k] = pgm[i][j];
				k++;
			}
		}
		return reversed_array;
	}
}