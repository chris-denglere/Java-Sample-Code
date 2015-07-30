import java.util.*;		// This line is needed to use the Scanner object
import java.io.*;		// This line is needed to use the File object

/**
 * Reads in movie data from a user provided .txt file and performs various
 * searching and organization functions on the data within the file. Helps
 * the user select a movie based on certain characteristics (title, year,
 * length, rating, and genre).
 *
 * @author Chris D'Englere
 */ 
public class MovieSelector {
	public static final int HEADER_ROWS = 1;	//Number of rows that the header in the file occupies
	public static final int MIN_YEAR = 1880;	//Minimum year
	public static final int MAX_YEAR = 2050;	//Maximum year
	public static final int MIN_LENGTH = 1;		//Minimum movie length (minutes)
	public static final int ACT_INDEX = 0;		//Index location that contains a 1 to indicate that the movie is of genre action
	public static final int ANI_INDEX = 1;		//Index location that contains a 1 to indicate that the movie is of genre animation
	public static final int COM_INDEX = 2;		//Index location that contains a 1 to indicate that the movie is of genre comedy
	public static final int DRA_INDEX = 3;		//Index location that contains a 1 to indicate that the movie is of genre drama
	public static final int DOC_INDEX = 4;		//Index location that contains a 1 to indicate that the movie is of genre documentary
	public static final int ROM_INDEX = 5;		//Index location that contains a 1 to indicate that the movie is of genre romance

	/**
	* Calls the checkCommandLineArguments, getInputScanner, and getInputFileLength
	* methods. This begins the flow of the program.
	*
	* @param args command line arguments
	*/
	public static void main(String[] args) {
		checkCommandLineArguments(args);
		getInputScanner(args[0]);
		getInputFileLength(args[0]);
	}
	
	/**
	* Checks the length of the String array containing the command line arguments provided
	* by the user. If the length is anything other than 1, an error message is output
	* to the console and the program is terminated.
	*
	* @param args String array containing command line arguments, if any, entered by the user
	*/
	public static void checkCommandLineArguments(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java MovieSelector filename");
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
			System.out.printf("Unable to access input file: %s.\n", filename);
			System.exit(1);
		}
		return input;
	}
	
	/**
	* Counts the number of lines (rows) in the input file. This number is used to
	* create the arrays that will store all of the data. Calls the createArrays
	* method, passing it the name of the file and the number of lines.
	*
	* @param filename String containing the name of the input file
	*/
	public static void getInputFileLength(String filename) {
		Scanner input = getInputScanner(filename);
		int number_of_lines = 0;
		while (input.hasNextLine()) {
			input.nextLine();	//Consumes the next line
			number_of_lines++;
		}
		number_of_lines -= HEADER_ROWS;		//Subtract the number of rows that the header occupies (1) from the total number of lines
		createArrays(filename, number_of_lines);
	}
	
	/**
	* Creates an array for each of the five different movie data fields based
	* on the length stored in number_of_lines. Reads in the data from the input
	* file and fills this data into the appropriate element of the appropriate
	* array. Calls the userInterface method, passing it all of the arrays that were
	* created.
	*
	* @param filename String containing the name of the input file
	* @param number_of_lines integer representing the number of rows to be used in sizing the arrays
	*/
	public static void createArrays(String filename, int number_of_lines) {
		String[] title = new String[number_of_lines];
		int[] year = new int[number_of_lines];
		int[] length = new int[number_of_lines];
		String[] rating = new String[number_of_lines];
		String[] genre = new String[number_of_lines];
		Scanner input = getInputScanner(filename);
		input.nextLine();			//Skips the header line in the input file
		for (int i = 0; i < number_of_lines; i++) {
			String temp = input.nextLine();
			Scanner line_scanner = new Scanner(temp);
			line_scanner.useDelimiter("\t");	//Changes token separator from a space to a tab
			title[i] = line_scanner.next();
			year[i] = line_scanner.nextInt();
			length[i] = line_scanner.nextInt();
			rating[i] = line_scanner.next();
			genre[i] = line_scanner.next();
		}
		userInterface(title, year, length, rating, genre);
	}
	
	/**
	* Prints a menu of options to the console. Prompts the user to select an option.
	* Calls the appropriate method based on the option selected by the user and passes the
	* arrays needed to execute the selected option. The user is prompted for an option
	* continually until they decide to quit the program.
	*
	* @param title String array containing the title data
	* @param year integer array containing the year data
	* @param length integer array containing the length data
	* @param rating String array containing the ratings data
	* @param genre String arrray containing the genre data
	*/
	public static void userInterface(String[] title, int[] year, int[] length, String[] rating, String[] genre) {
		Scanner console = new Scanner(System.in);
		String userinput = "";
		boolean exit = false;
		do {
			System.out.print("\nMovie Selector - Please enter an option below.");
			System.out.print("\n\nL - List all movies");
			System.out.print("\nY - List movies by year");
			System.out.print("\nT - Search by title");
			System.out.print("\nS - Search by genre, rating, and maximum length");
			System.out.print("\nQ - Quit the program");
			System.out.print("\n\nOption: ");
			userinput = console.next();
			userinput = userinput.toUpperCase();
			if (userinput.equals("L")) {
				listMovies(title);
			} else if (userinput.equals("Y")) {
				listByYear(console, title, year);
			} else if (userinput.equals("T")) {
				searchByTitle(console, title);
			} else if (userinput.equals("S")) {
				search(console, title, genre, rating, length);
			} else if (userinput.equals("Q")) {
				System.out.print("\nGoodbye!\n");
				exit = true;	//Sets the loop control variable exit to true so the loop will not run on the next attempt
			} else {
				System.out.print("Invalid option, please try again.\n");
			}
		} while (!exit);
	}
	
	/**
	* Lists all movie titles followed by the total number of movies.
	*
	* @param title String array containing the title data
	*/
	public static void listMovies(String[] title) {
		System.out.println();	//Blank line for console output aesthetics
		for (int i = 0; i < title.length; i++) {
			System.out.println(title[i]);
		}
		System.out.printf("\nNumber of movies: %d\n", title.length);
	}
	
	/**
	* Prompts the user for a year and lists all movie titles for that year.
	*
	* @param console Scanner passed from userInterface that is used to accept input from the user
	* @param title String array containing the title data
	* @param year integer array containing the year data
	*/
	public static void listByYear(Scanner console, String[] title, int[] year) {
		System.out.printf("\nYear (%d-%d): ", MIN_YEAR, MAX_YEAR);
		if (console.hasNextInt()) {
			int userinput = console.nextInt();
			if (userinput >= MIN_YEAR && userinput <= MAX_YEAR) {
				System.out.println();	//Blank line for console output aesthetics
				for (int i = 0; i < year.length; i++) {
					if (year[i] == userinput) {
						System.out.println(title[i]);
					}
				}
			} else {
				System.out.println("Invalid year");
			}
		}
	}
	
	/**
	* Prompts the user for part or all of a movie title and
	* lists all movie titles that contain that substring ignoring case.
	*
	* @param console Scanner passed from userInterface that is used to accept input from the user
	* @param title String array containing the title data
	*/
	public static void searchByTitle(Scanner console, String[] title) {
		System.out.print("\nTitle (is/contains): ");
		console.nextLine();		//Moves input cursor past the previous line end
		String userinput = console.nextLine();
		userinput = userinput.toLowerCase();
		System.out.println();	//Blank line for console output aesthetics
		for (int i = 0; i < title.length; i++) {
			if (title[i].toLowerCase().contains(userinput)) {
				System.out.println(title[i]);
			}
		}
	}
	
	/**
	* Prompts the user for a rating, genre, and maximum length and
	* lists all movie titles that meet the criteria.
	*
	* @param console Scanner passed from userInterface that is used to accept input from the user
	* @param title String array containing the title data
	* @param genre String arrray containing the genre data
	* @param rating String array containing the ratings data
	* @param length integer array containing the length data
	*/
	public static void search(Scanner console, String[] title, String[] genre, String[] rating, int[] length) {
		System.out.print("\nGenre (Action(A),Animation(N),Comedy(C),Drama(D),Documentary(O),Romance(R)): ");
		String usergenre = console.next();
		usergenre = usergenre.toUpperCase();
		int genre_index = 0;
		if (usergenre.equals("A") || usergenre.equals("N") || usergenre.equals("C") || usergenre.equals("D")
			|| usergenre.equals("O") || usergenre.equals("R")) {
			System.out.print("Rating (G,PG,PG-13,R,NC-17,NR): ");
			String userrating = console.next();
			userrating = userrating.toUpperCase();
			if (userrating.equals("G") || userrating.equals("PG") || userrating.equals("PG-13") || userrating.equals("R")
				|| userrating.equals("NC-17") || userrating.equals("NR")) {
				System.out.print("Maximum length (min): ");
				int userlength = console.nextInt();
				if (userlength >= MIN_LENGTH) {
					if (usergenre.equals("A")) {
						genre_index = ACT_INDEX;
					} else if (usergenre.equals("N")) {
						genre_index = ANI_INDEX;
					} else if (usergenre.equals("C")) {
						genre_index = COM_INDEX;
					} else if (usergenre.equals("D")) {
						genre_index = DRA_INDEX;
					} else if (usergenre.equals("O")) {
						genre_index = DOC_INDEX;
					} else if (usergenre.equals("R")) {
						genre_index = ROM_INDEX;
					}
					System.out.println();	//Blank line for console output aesthetics
					for (int i = 0; i < title.length; i++) {
						if (genre[i].charAt(genre_index) == '1' && rating[i].equals(userrating) && length[i] <= userlength) {
							System.out.println(title[i]);
						}
					}
				} else {
					System.out.println("Invalid length");
				}
			} else {
				System.out.println("Invalid rating");
			}
		} else {
			System.out.println("Invalid genre");
		}
	}
}