import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {


    static Set<Word> dict = new HashSet<Word>();
    static Set<Character> guessed = new HashSet<Character>();
    static char underScores[];
    static int guesses;


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
            return false;
        }
    }


    public static Set<Word> sortFamily(Set<Word> words, char guess) {
        Map<Integer, Set<Word>> families = new HashMap<Integer, Set<Word>>();
        for (Word word : words) {

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


    public static Set<Word> sortNumChar(Set<Word> words, char guess) {
        //test how many times char shows up
        Map<ArrayList<Integer>, Set<Word>> letterPosition = new HashMap<ArrayList<Integer>, Set<Word>>();

        for (Word word : words) {
            ArrayList<Integer> positions = new ArrayList<Integer>();

            char letters[] = word.s.toCharArray();
            for (int i = 0; i < word.length; i++) {
                if (letters[i] == guess) {
                    positions.add(i);
                }

            }

            if (positions.isEmpty()) {
                return words;

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
        return letterPosition.get(key);

    }

    //Prompts user for word length and creates and returns a set with all words of that length
    public static Set<Word> userInput() {
        Set<Word> setOfWords = new HashSet<Word>();

        int wordLength = 0;

        System.out.println("Please enter the word length:");
        Scanner kb = new Scanner(System.in);
        while (!kb.hasNextInt()) {
            System.out.println("Error: not a number. Please try again.");
            kb.next();
        }
        wordLength = kb.nextInt();

        StringBuilder build = new StringBuilder();

        for (int i = 0; i < wordLength; i++) {
            build.append("_");
        }

        underScores = build.toString().toCharArray().clone();

        for (Word word : dict) {
            if (word.length == wordLength) {
                setOfWords.add(word);
            }
        }
        return setOfWords;

    }

    //If there's only 2 words left in the set, pick the one that always makes the guesser guess wrong
    public static Set<Word> lastChoices(Set<Word> words, char guess) {
        for (Word word : words) {
            for (int i = 0; i < word.length; i++) {
                if (word.s.charAt(i) == guess) {
                    words.remove(word);
                    return words;
                }
            }
        }

        return words;
    }

    public static void guessingGUI(Set<Word> words, char guess) {
        int size = 0;
        String check = "";
        for (Word word : words) {
            size = word.length;
            check = word.s;
        }

        char letters[] = check.toCharArray();

        boolean guessToggle = false;

        for(int i = 0; i < size; i++){
            if(guess == letters[i]) {
                underScores[i] = guess;
                if (!guessToggle){
                    guesses++;
                    guessToggle = true;
                }
            }

        }for(int i = 0; i < underScores.length; i++){
            System.out.print(underScores[i]);
            System.out.print(" ");
        }
        System.out.println("");

    }

    public static void menu() {
        Scanner in = new Scanner(System.in);
        if (!importWords())
            return;

        Set<Word> x = userInput();
        System.out.println("How many guesses?");

        while (!in.hasNextInt()) {
            System.out.println("Error: not a number. Please try again.");
            in.next();
        }
        guesses = in.nextInt();

        char guess;
        for (int i = 0; guesses > i; guesses--) {
            System.out.println("Guessed Letters: " + guessed);
            if (x.size() == 1) { //If there's still letters left, switch to normal hangman
                    for (int j = 0; guesses > j; guesses--){
                        //Catch if already guesses a letter
                        System.out.println("Enter a guess:");
                        guess = in.next().trim().charAt(0);
                        guess = Character.toLowerCase(guess);
                        while (!Character.isLetter(guess) || guessed.contains(guess)){
                            if (!Character.isLetter(guess)){
                                System.out.println("Not a letter. Please guess again");
                            }else{
                                System.out.println("Already guessed that letter. Please guess again");
                            }
                            guess = in.next().trim().charAt(0);
                        }
                        guessed.add(guess);

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
                guess = in.next().trim().charAt(0);
                guess = Character.toLowerCase(guess);
                while (!Character.isLetter(guess) || guessed.contains(guess)){
                    if (!Character.isLetter(guess)){
                        System.out.println("Not a letter. Please guess again");
                    }else{
                        System.out.println("Already guessed that letter. Please guess again");
                    }
                    guess = in.next().trim().charAt(0);
                }
                guessed.add(guess);

                if (x.size() == 2) {
                    x = lastChoices(x, guess);
                } else {
                    x = sortFamily(x, guess);
                    x = sortNumChar(x, guess);
                }
                //System.out.println(x.size());
                guessingGUI(x, guess);

            }
        }

        if (guesses == 0){
            System.out.println("You ran out of guesses!");
            Word y[] = x.toArray(new Word[x.size()]);
            System.out.println("The word was: " + y[0].s);
        }
    }
}





