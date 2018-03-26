import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	static Set<String> dict = new HashSet<String>(); 
	
	public static void main(String[] args) {
		importWords();

	}
	
	//Import Dictionary into set
	public static void importWords() {
		Scanner in;
		try {
			in = new Scanner(new File("Dictionary.txt"));
			while (in.hasNextLine()) {
				dict.add(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			//e.printStackTrace();
		}
	}
}
