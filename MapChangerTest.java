/**
 * White box test program that tests the isValidCharacter() and uncoverLine()
 * methods in the MapChanger class.
 *
 * @author Chris D'Englere
 */
public class MapChangerTest {
	
	/**
	* Executes the white box test cases for the isValidCharacter() and uncoverLine()
	* methods in the MapChanger class.
	*
	* @param args command line arguments (not used)
	*/
	public static void main(String[] args) {  

		boolean isValid = MapChanger.isValidCharacter('X');
		System.out.println("Expected: true\t Actual: " + isValid);
	
		isValid = MapChanger.isValidCharacter('x');
		System.out.println("Expected: false\t Actual: " + isValid);
	
		isValid = MapChanger.isValidCharacter('+');
		System.out.println("Expected: true\t Actual: " + isValid);
	
		isValid = MapChanger.isValidCharacter('/');
		System.out.println("Expected: true\t Actual: " + isValid);
	
		isValid = MapChanger.isValidCharacter('\\');
		System.out.println("Expected: true\t Actual: " + isValid);
	
		isValid = MapChanger.isValidCharacter('~');
		System.out.println("Expected: true\t Actual: " + isValid);
	
		isValid = MapChanger.isValidCharacter('!');
		System.out.println("Expected: false\t Actual: " + isValid);
		
		isValid = MapChanger.isValidCharacter('@');
		System.out.println("Expected: false\t Actual: " + isValid);
		
		isValid = MapChanger.isValidCharacter('$');
		System.out.println("Expected: false\t Actual: " + isValid);
    
		String line = MapChanger.uncoverLine("++@#$%~~~/&*");
		System.out.println("Expected: \"++    ~~~/  \"");
		System.out.println("Actual:   \"" + line + "\"");
		
		line = MapChanger.uncoverLine("@^\\+*+#%XX");
		System.out.println("Expected: \"  \\+ +  XX\"");
		System.out.println("Actual:   \"" + line + "\"");
		
		line = MapChanger.uncoverLine("(&/\\/\\!@~~~~");
		System.out.println("Expected: \"  /\\/\\  ~~~~\"");
		System.out.println("Actual:   \"" + line + "\"");
		
		line = MapChanger.uncoverLine("=!$)++X~(~\\");
		System.out.println("Expected: \"    ++X~ ~\\\"");
		System.out.println("Actual:   \"" + line + "\"");
		
		line = MapChanger.uncoverLine("/\\/\\&@=++!X%)");
		System.out.println("Expected: \"/\\/\\   ++ X  \"");
		System.out.println("Actual:   \"" + line + "\"");
		
		line = MapChanger.uncoverLine("++++#(=!*~~~~@)^X");
		System.out.println("Expected: \"++++     ~~~~   X\"");
		System.out.println("Actual:   \"" + line + "\"");
		
		line = MapChanger.uncoverLine("~~^=@X!%$*)++/\\");
		System.out.println("Expected: \"~~   X     ++/\\\"");
		System.out.println("Actual:   \"" + line + "\"");
	}
}