import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {


    static Set<Word> dict = new HashSet<Word>();


    public static void main(String[] args) {
        menu();
    }


    //Import Dictionary into set
    public static boolean importWords() {
        try {
            Scanner in = new Scanner(new File("test.txt"));
            while (in.hasNextLine()) {
                String s = in.nextLine();
                dict.add(new Word(s));
            }
            in.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            //e.printStackTrace();
            return false;
        }
    }


    public static Set<Word> sortFamily(Set<Word> w, char guess) {
        Map<Integer, Set<Word>> families = new HashMap<Integer, Set<Word>>();
        for (Word word : w) {

            int counter = 0; // counter for number of char in word
            for (int i = 0; i < word.length; i++) {
                if (word.s.charAt(i) == guess) {
                    counter++;
                }
            }

            //inserts words into family groups
            if (!families.containsKey(counter)) {
                families.put(counter, new HashSet<Word>());
            }
            families.get(counter).add(word);
        }

        //outputs largest set in families
        int size = 0;
        int index = 0;
        for (Map.Entry<Integer, Set<Word>> k : families.entrySet()) {
            if (k.getValue().size() > size) {
                index = k.getKey();
                size = k.getValue().size();
            }
        }

        //System.out.println(families.entrySet());

        //returns largest set
        return families.get(index);
    }


    public static Set<Word> sortNumChar(Set<Word> w, char guess) {
        //test how many times char shows up
        Map<ArrayList<Integer>, Set<Word>> letterPosition = new HashMap<ArrayList<Integer>, Set<Word>>();

        for (Word word : w) {
            ArrayList<Integer> positions = new ArrayList<Integer>();

            char letters[] = word.s.toCharArray();
            for (int i = 0; i < word.length; i++) {
                //System.out.println(letters[i]);
                if (letters[i] == guess) {
                    //System.out.println(letters[i]);
                    positions.add(i);
                }

            }

            if (positions.isEmpty()) {
                return w;

            } else {
                if (!letterPosition.containsKey(positions)) {
                    letterPosition.put(positions, new HashSet<Word>());
                }
                letterPosition.get(positions).add(word);

            }


            //System.out.println(positions);

        }
        System.out.println(letterPosition.values());
        ArrayList<Integer> key = new ArrayList<Integer>();
        int size = 0;
        for (Map.Entry<ArrayList<Integer>, Set<Word>> k : letterPosition.entrySet()) {
            if (k.getValue().size() > size) {
                size = k.getValue().size();
                key = k.getKey();
            }

        }
        System.out.println(letterPosition.get(key));
        return letterPosition.get(key);
    }

    //Prompts user for word length and creates and returns a set with all words of that length
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

    public static void menu() {


        if (!importWords())
            return;

        Set<Word> x = userInput();

        for (Word w : x) {
            System.out.println(w);
        }
        System.out.println("---------------------");


        Set<Word> y = sortFamily(x, 'a');

        for (Word w : y) {
            System.out.println(w);
        }
        sortNumChar(y, 'a');

    }

}
