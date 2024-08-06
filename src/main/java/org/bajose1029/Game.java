package org.bajose1029;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    //Constants
    public static final int WORD_LENGTH = 5;
    public static final int MAX_GUESSES = 6;
    public static final int TOTAL_GUESSES_FOR_LOSS = 7;

    private static final int NO_MATCH = 0;
    private static final int WRONG_LOCATION = 1;
    private static final int EXACT_MATCH = 2;


    private String word;
    private List<String> guesses;

    //Game Words
    private static final String[] words = new String[]{"chair", "crate", "train", "allow", "about", "study"};
    public Game(){
        Random random = new Random(System.currentTimeMillis());
        int randomIndex = random.nextInt(words.length);
        word = words[randomIndex].toUpperCase();
        guesses = new ArrayList<>();
    }

    public String getWord() {
        return word;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public void addGuess(String guess){
        guesses.add(guess);
    }

    public int[] checkGuess(String guess)
    {
        int [] resultCodes = new int[WORD_LENGTH];
        List<Character> missedWordChars = new ArrayList<>();

        for (int i = 0; i < WORD_LENGTH; i++) {
            char wordChar = word.charAt(i);
            char guessChar = guess.charAt(i);

            if (wordChar == guessChar) {
                resultCodes[i] = EXACT_MATCH;
            }
            else {
                missedWordChars.add(wordChar);
            }
        }

        for (int j = 0; j < WORD_LENGTH; j++)
        {
            if(resultCodes[j] == EXACT_MATCH)
            {
                continue;
            }
            char guessChar = guess.charAt(j);

            int index = missedWordChars.indexOf(guessChar);
            if(index > -1)
            {
                resultCodes[j] = WRONG_LOCATION;
                missedWordChars.remove(index);
            }
            else
            {
                resultCodes[j] = NO_MATCH;
            }
        }
        return  resultCodes;
    }

    public boolean isWin(){
        if(!guesses.isEmpty())
        {
            return word.equals(guesses.get(guesses.size() - 1));
        }
        return false;
    }

    public boolean isLoss(){
       return guesses.size() == MAX_GUESSES && !isWin();
    }
}
