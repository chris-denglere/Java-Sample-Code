import java.util.*;		// This line is needed to use the Scanner object
import java.io.*;		// This line is needed to use the File object

/**
 * Map changer program that changes treasure maps by obscuring and/or uncovering them.
 * The user provdes an input file containing a map and this program provides the
 * obscured/uncovered map in an output file.
 *
 * @author Chris D'Englere
 */ 
public class MapChanger {
	public static final String VALID_CHARACTERS = "+/\\~X";
	public static final String OBSCURE_CHARACTERS = "!@#$%^&*()=";
	public static final Random RAND = new Random();

	/**
	* Calls the userInterface method.
	*
	* @param args command line arguments (not used)
	*/
	public static void main(String[] args) {
		userInterface();
	}
	
	/**
	* Prints a header to the console containing the name of the application and insructions
	* on how to use this software. Continually prompts the user for what action they want
	* to perform, their input file, and their output file until they tell the program to quit.
	* If the user enters an invalid action, a message is printed to the console telling them
	* that they have attempted an invalid action. Based on what valid action the user selects,
	* the processFile method is called and passed the appropriate parameters.
	*/
	public static void userInterface() {
		System.out.print("\nWELCOME TO CHRIS'S TREASURE MAP MANIPULATION SOFTWARE:");
		System.out.print("\n                MAPCHANGER VERSION 1.0");
		System.out.print("\n\nThis software manipulates treasure maps by obscuring");
		System.out.print("\nand/or uncovering them. Instructions on how to use this");
		System.out.print("\nsoftware are given below.");
		System.out.print("\n\n1. You will be prompted to choose one of these three actions:");
		System.out.print("\n\n   O-bscure (obscures an uncovered map)");
		System.out.print("\n   U-ncover (uncovers an obscured map)");
		System.out.print("\n   Q-uit (quits the software)");
		System.out.print("\n\n2. In order to execute a specific action, please enter");
		System.out.print("\n   either the upper case or lower case version of the");
		System.out.print("\n   first letter of the action that you would like to");
		System.out.print("\n   execute (i.e. O or o for O-bscure).");
		System.out.print("\n\n3. When you choose to obscure or uncover a map, you will");
		System.out.print("\n   be prompted to enter the name of an input file containing");
		System.out.print("\n   a map.");
		System.out.print("\n\n4. You will then be prompted to enter the output file name.");
		System.out.print("\n\n5. The software will perform the selected action and write");
		System.out.print("\n   to the file you specified for output.\n");
		
		Scanner console = new Scanner(System.in);
		String userinput = "";
		boolean exit = false;
		do {
			System.out.print("\nEnter O-bscure, U-ncover, or Q-uit: ");
			userinput = console.next();
			if (userinput.equals("O") || userinput.equals("o")) {
				processFile(true, getInputScanner(console), getOutputPrintStream(console));
			} else if (userinput.equals("U") || userinput.equals("u")) {
				processFile(false, getInputScanner(console), getOutputPrintStream(console));
			} else if (userinput.equals("Q") || userinput.equals("q")) {
				exit = true;	//Sets the loop control variable exit to true so the loop will not run on the next attempt
			} else {
				System.out.print("Invalid Action\n");
			}
		} while (!exit);
	}

	/**
	* Creates a null Scanner for an input file. Prompts the user to enter the name of the
	* input file. Once the user enters the name of their file, the Scanner is instructed
	* to open that file. A try/catch block is used to handle any FileNotFoundException's
	* that occur. The Scanner is then returned to the method call in userInterface.
	*
	* @param console Scanner passed from userInterface that is used to accept input from the user
	* @return Scanner for the input file
	*/
	public static Scanner getInputScanner(Scanner console){
		Scanner input = null;
		while (input == null) {
			System.out.print("Enter input file: ");
			String filename = console.next();
			try {
				input = new Scanner(new File(filename));
			} 
			catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		return input;  
	}

	/**
	* Creates a null PrintStream for an output file. Prompts the user to enter the name of the output
	* file. Once the user enters the name of the output file, the program checks to see if the file
	* already exists. If it already exists, the program asks the user if it is OK to overwrite the file.
	* If they answer y or Y (yes), the PrintStream is set to overwrite the file. If they answer with anything
	* other than y or Y, the user is reprompted. A try/catch block is used to handle any FileNotFoundException's
	* that occur. The PrintStream is then returned to the method call in userInterface.
	*
	* @param console Scanner passed from userInterface that is used to accept input from the user
	* @return PrintStream for the output file
	*/
	public static PrintStream getOutputPrintStream(Scanner console){
		PrintStream output = null;
		while (output == null) {
			System.out.print("Enter output file: ");
			String filename = console.next();
			try {
				File f = new File(filename);
				if (f.exists()) {
					System.out.print("File exists. OK to overwrite it? (y or n): ");
					String userinput = console.next();
					if (userinput.equals("y") || userinput.equals("Y")) {
						output = new PrintStream(f);
					}
				} else {
					output = new PrintStream(f);
				}
			}
			catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
		}
		return output;
	}

	/**
	* If the obscure boolean is true, calls the obscureLine method with the next line
	* of input from the input file. Continues to do this as long as the input file has
	* another line. The return from obscureLine is then printed to the output PrintStream.
	* If the obscure boolean is false, calls the uncoverLine method with the next line of
	* input from the input file. Continues to do this as long as the input file has another
	* line. The return from uncoverLine is then printed to the output PrintStream.
	*
	* @param obscure boolean that, if true, tells the method to obscure the input, if false, to uncover the input
	* @param input Scanner for the input file
	* @param output PrintStream for the output file
	*/
	public static void processFile (boolean obscure, Scanner input, PrintStream output){
		if (obscure) {
			while (input.hasNextLine()) {
				output.println(obscureLine(input.nextLine()));
			}
		} else {
			while (input.hasNextLine()) {
				output.println(uncoverLine(input.nextLine()));
			}
		}
	}

	/**
	* Reads the input String one character at a time and replaces spaces with obscure characters.
	* Keeps the valid characters. Returns the obscured line string.
	*
	* @param line String of input to be obscured
	* @return String containing the obscured input line
	*/
	public static String obscureLine(String line){
		String obscured_line = "";
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ') {				//If the character at location i in the unobscured line is a space,
				obscured_line += getRandomCharacter();	//place a random character in its place for the obscured line
			} else {
				obscured_line += line.charAt(i);		//For all other characters (valid characters in this case of an unobscured line),
			}											//put the character in the obscured line
		}
		return obscured_line;
	}

	/**
	* Reads the input String one character at a time and replaces obscure characters with spaces.
	* Keeps the valid characters. Returns the uncovered line string.
	*
	* @param line String of input to be uncovered
	* @return String containing the uncovered input line
	*/
	public static String uncoverLine(String line){
		String uncovered_line = "";
		for (int i = 0; i < line.length(); i++) {
			if (isValidCharacter(line.charAt(i))) {		//If the character at location i in the obscured line is a valid character,
				uncovered_line += line.charAt(i);		//put the character in the uncovered line
			} else {
				uncovered_line += ' ';					//For all other characters, place a space in its place for the uncovered line
			}
		}
		return uncovered_line;
	}

	/**
	* Returns true if ch is a member of the VALID_CHARACTERS constant: +/\~X
	* Returns false otherwise.
	*
	* @param ch character that is checked for validity
	* @return boolean set as true if the character is a member of the VALID_CHARACTERS constant and false otherwise
	*/
	public static boolean isValidCharacter(char ch){
		boolean char_boolean = false;
		for (int i = 0; i < VALID_CHARACTERS.length(); i++) {
			if (ch == VALID_CHARACTERS.charAt(i)) {
				char_boolean = true;
			}
		}
		return char_boolean;
	}

	/**
	* Randomly selects a character from the OBSCURE_CHARACTERS constant: !@#$%^&*()=
	* and returns it.
	*
	* @return random character from the OBSCURE_CHARACTERS constant
	*/
	public static char getRandomCharacter(){
		char random_char = OBSCURE_CHARACTERS.charAt(RAND.nextInt(OBSCURE_CHARACTERS.length()));
		return random_char;
	}
}