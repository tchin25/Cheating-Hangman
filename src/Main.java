import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	static Set<String> dict = new HashSet<String>(); 
	
	public static void main(String[] args) {
		System.out.println("Hello Thomas");

	}
	
	//Import Dictionary into set
	public static void importWords() throws FileNotFoundException {
		Scanner in = new Scanner(new File("Dictionary.txt"));
		
		while (in.hasNextLine()) {
			dict.add(in.nextLine());
		}
	}
}
