import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	static Set<Word> dict = new HashSet<Word>(); 
	
	public static void main(String[] args) {
		importWords();
		Set<Word> x = userInput();
		
		for(Word w : x) {
			System.out.println( w.s + " : " + w.length);
		}
		System.out.println("---------------------");
		Set<Word> y = sortFamily(x, 'a');
		
		for(Word w : y) {
			System.out.println( w.s + " : " + w.length);
		}
		
	}
	
	//Import Dictionary into set
	public static void importWords() {
		Scanner in;
		try {
			in = new Scanner(new File("test.txt"));
			while (in.hasNextLine()) {
				String s = in.nextLine();
				dict.add(new Word(s));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			//e.printStackTrace();
		}
	}
	
	public static Set<Word> sortFamily(Set<Word> w, char guess) {
		Map<Integer, Set<Word>> families = new HashMap<Integer, Set<Word>>();
		for(Word word : w) {
			
			int counter = 0; // counter for number of char in word
			for (int i = 0; i < word.length; i++) {
				if (word.s.charAt(i) == guess) {
					counter++;
				}
			}
			
			//inserts words into family groups
			if (!families.containsKey(counter)) {
				families.put(counter, new HashSet<Word>());
				families.get(counter).add(word);
			}else {
				families.get(counter).add(word);
			}
			
		}
		
		
		//outputs largest set in families
		int size = 0;
		int index = 0;
		for(Map.Entry<Integer, Set<Word>> k : families.entrySet()) {
			if (k.getValue().size() > size) {
				index = k.getKey();
				size = k.getValue().size();
			}
		}
		
		return families.get(index);
	}
	
	public static Set<Word> userInput() {
		Set<Word> setOfWords = new HashSet<Word>();

		int wordLength;

		System.out.println("Please enter the word length:");
		Scanner kb = new Scanner(System.in);

		wordLength = kb.nextInt();

		for (Word word : dict) {
			if (word.length == wordLength) {
				setOfWords.add(word);

				//System.out.println(word.s + " : " + word.length);
			}
		}
		return setOfWords;


	}

}
