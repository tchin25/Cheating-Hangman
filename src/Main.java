import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	static Set<Word> dict = new HashSet<Word>(); 
	
	public static void main(String[] args) {
		importWords();
		
		for(Word word : dict) {
			System.out.println(word.s + " : " + word.length);
		}

	}
	
	//Import Dictionary into map
	public static void importWords() {
		Scanner in;
		try {
			in = new Scanner(new File("Dictionary.txt"));
			while (in.hasNextLine()) {
				String s = in.nextLine();
				dict.add(new Word(s));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			//e.printStackTrace();
		}
	}
}
