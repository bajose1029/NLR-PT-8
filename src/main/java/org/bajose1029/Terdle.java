package org.bajose1029;

import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Terdle {
    private static final int WORD_LENGTH = 5;
    private  static final int MAX_GUESSES = 6;

    private static final int NO_MATCH = 0;
    private static final int EXACT_MATCH = 1;
    private static final int WRONG_LOCATION = 2;
    private static final String[] words = {"chair", "crate", "train", "allow", "allow", "about", "study"};

    private String word;
    public static void main(String[] args) {
        Terdle  terdleGame = new Terdle();
        terdleGame.run();
    }


    public Terdle()
    {
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        word = words[randomIndex];
    }


    public void run()
    {
        System.out.println("Welcome to TErdle");
        Scanner scanner = new Scanner(System.in);

        int guessCount = 1;
        boolean win = false;

        while(guessCount <= MAX_GUESSES && !win)
        {
            System.out.print("Please enter your guess # " + guessCount + ": ");
            String guess = scanner.nextLine();
            guess = guess.toLowerCase();

            int [] results = checkGuess(guess);
            printGuessResults(guess, results);

            guessCount++;

            win = guess.equalsIgnoreCase(word);
        }
        if (win)
        {
            System.out.println("Congrats you won!!");
        }
        else
        {
            System.out.println("Sorry, you didn't win. The word is: " + word + ".");
        }
    }


    private int[] checkGuess(String guess)
    {
        int [] resultCodes = new int[WORD_LENGTH];
        String missedWordChars = "";

        for (int i = 0; i < WORD_LENGTH; i++)
        {
            char wordChar = word.charAt(i);
            char guessChar = guess.charAt(i);

            if(wordChar != guessChar)
            {
                missedWordChars += wordChar;
            }
        }
        for (int i = 0; i < WORD_LENGTH; i++)
        {
            char wordChar = word.charAt(i);
            char guessChar = guess.charAt(i);

            if (wordChar == guessChar)
            {
                resultCodes[i] = EXACT_MATCH;
            }
            else if (missedWordChars.contains(Character.toString(guessChar)))
            {
                resultCodes[i] = WRONG_LOCATION;
                missedWordChars = missedWordChars.replace(Character.toString(guessChar), "");
            }
            else
            {
                resultCodes[i] = NO_MATCH;
            }
        }
        return  resultCodes;
    }

    private void printGuessResults(String guess, int [] result)
    {

    }

}