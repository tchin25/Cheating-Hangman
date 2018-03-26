import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	static Map<String, Integer> dict = new HashMap<String, Integer>(); 
	
	public static void main(String[] args) {
		importWords();

	}
	
	//Import Dictionary into set
	public static void importWords() {
		Scanner in;
		try {
			in = new Scanner(new File("Dictionary.txt"));
			while (in.hasNextLine()) {
				String s = in.nextLine();
				dict.put(s, s.length());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			//e.printStackTrace();
		}
	}
}
