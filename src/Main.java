import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {


    static Set<Word> dict = new HashSet<Word>();
    static char underScores[];


    public static void main(String[] args) {
        menu();
    }


    //Import Dictionary into set
    public static boolean importWords() {
        try {
            Scanner in = new Scanner(new File("dictionary.txt"));
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

        }

        ArrayList<Integer> key = new ArrayList<Integer>();
        int size = 0;
        for (Map.Entry<ArrayList<Integer>, Set<Word>> k : letterPosition.entrySet()) {
            if (k.getValue().size() > size) {
                size = k.getValue().size();
                key = k.getKey();
            }

        }
        //System.out.println(letterPosition.get(key));
        return letterPosition.get(key);
    }

    //Prompts user for word length and creates and returns a set with all words of that length
    public static Set<Word> userInput() {
        Set<Word> setOfWords = new HashSet<Word>();

        int wordLength = 0;

        System.out.println("Please enter the word length:");
        Scanner kb = new Scanner(System.in);
        wordLength = kb.nextInt();

        StringBuilder build = new StringBuilder();

        for (int i = 0; i < wordLength; i++) {
            build.append("_");
        }

        //underScores = new char[build.toString().length()-1];
        underScores = build.toString().toCharArray().clone();

        for (Word word : dict) {
            if (word.length == wordLength) {
                setOfWords.add(word);

                //System.out.println(word.s + " : " + word.length);
            }
        }
        return setOfWords;

    }

    /*
    If there's only 2 words left in the set, pick the one that always makes the guesser guess wrong
     */
    public static Set<Word> lastChoices(Set<Word> words, char guess) {
        if (words.size() != 2) {
            return words;
        } else if (words.size() == 1) {
            return words;
        }

        for (Word w : words) {
            for (int i = 0; i < w.length; i++) {
                if (w.s.charAt(i) == guess) {
                    words.remove(w);
                    return words;
                }
            }
        }

        return words;
    }

    public static void guessingGUI(Set<Word> words, char guess) {
        int size = 0;
        String check = "";
        for (Word w : words) {
            size = w.length;
            check = w.s;
        }

        char letters[] = check.toCharArray();

        for(int i = 0; i < size; i++){
            if(guess == letters[i]) {
                underScores[i] = guess;
            }

        }
        System.out.println(underScores);

    }

    public static void menu() {
        Scanner in = new Scanner(System.in);
        if (!importWords())
            return;

        Set<Word> x = userInput();
        System.out.println("How many guesses?");
        int guesses = in.nextInt(); //TODO: Error check
        char guess = ' ';
        for (int i = 0; guesses > i; guesses--) {
            System.out.println(x);
            if (x.size() == 1) { //TODO: if there's still letters left, switch to normal hangman
                    for (int j = 0; guesses > j; guesses--){ //TODO: catch if already guesses a letter
                        System.out.println("Enter a guess:");
                        guess = in.next().charAt(0);

                        guessingGUI(x, guess);

                        boolean isFilled = true;
                        for (int k = 0; k < underScores.length; k++){
                            if (underScores[i] == '_'){
                                isFilled = false;
                            }
                        }
                        if (isFilled){
                            System.out.println("You won. Yay.");
                            break;
                        }
                    }
                break;
            } else {
                System.out.println("Enter a guess:");
                guess = in.next().charAt(0);
                if (x.size() == 2) {
                    x = lastChoices(x, guess);
                } else {
                    x = sortFamily(x, guess);
                    x = sortNumChar(x, guess);
                }
                System.out.println(x.size());
                guessingGUI(x, guess);

            }
        }
    }

}





