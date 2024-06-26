package org.bajose1029;

import java.util.*;

public class Terdle {
    private static final int WORD_LENGTH = 5;
    private  static final int MAX_GUESSES = 6;

    private static final String keyboard =
                    "QWERTYUIOP\n" +
                    "ASDFGHJKL\n" +
                    "ZXCVBNM\n";

    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_BLACK = "\u001B[30m";
    private static final String GREEN_BACKGROUND = "\u001B[42m";
    private static final String YELLOW_BACKGROUND = "\u001B[43m";
    private static final String GRAY_BACKGROUND = "\u001B[47m";

    private static final int NO_MATCH = 0;
    private static final int WRONG_LOCATION = 1;
    private static final int EXACT_MATCH = 2;

    //Backgrounds
    private String[] backgrounds = new String[]{GRAY_BACKGROUND, YELLOW_BACKGROUND, GREEN_BACKGROUND};

    //Game Words
    private static final String[] words = new String[]{"chair", "crate", "train", "allow", "about", "study"};

    //Instance Variables
    private String word;
    private List<String> guesses = new ArrayList<>();

    public Terdle()
    {
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        word = words[randomIndex].toUpperCase();
    }

    public static void main(String[] args) {
        Terdle  terdleGame = new Terdle();
        terdleGame.run();
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
            guess = guess.toUpperCase();

            guesses.add(guess);

            printGuesses();

            guessCount++;

            win = guess.equalsIgnoreCase(word);

            if(!win)
            {
                printKeyboard();
            }
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

    private void printGuesses()
    {
        for(String guessToDisplay : guesses)
        {
            int[] results = checkGuess(guessToDisplay);
            printGuessResults(guessToDisplay, results);
        }
        System.out.println();
    }

    private void printKeyboard()
    {
        Map<Character, Integer> resultMap = new HashMap<>();
        for(String currentGuess : guesses)
        {
            int[] results = checkGuess(currentGuess);

            for(int i = 0; i < WORD_LENGTH; i++)
            {
                Character guessChar = currentGuess.charAt(i);
                Integer resultCode = resultMap.get(guessChar);
                if(resultCode == null || results[i] > resultCode)
                {
                    resultMap.put(guessChar, results[i]);
                }
            }
        }
        for(int i = 0; i < keyboard.length(); i++)
        {
            char keyboardChar = keyboard.charAt(i);
            Integer resultCode = resultMap.get(keyboardChar);
            printResultChar(keyboardChar, resultCode);
        }
        System.out.println();
    }


    private int[] checkGuess(String guess)
    {
        int [] resultCodes = new int[WORD_LENGTH];
        List<Character> missedWordChars = new ArrayList<>();

        for (int i = 0; i < WORD_LENGTH; i++)
        {
            char wordChar = word.charAt(i);
            char guessChar = guess.charAt(i);

            if(wordChar != guessChar)
            {
                missedWordChars.add(wordChar);
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
            else
            {
                int index = missedWordChars.indexOf(guessChar);

                if(index > -1)
                {
                    resultCodes[i] = WRONG_LOCATION;
                    missedWordChars.remove(index);
                }
                else
                {
                    resultCodes[i] = NO_MATCH;
                }

            }
        }

        return  resultCodes;
    }

    private void printGuessResults(String guess, int [] result)
    {
        for (int i = 0; i < WORD_LENGTH; i++)
        {
            char ch = guess.charAt(i);
            int resultCode = result[i];
            printResultChar(ch, resultCode);
        }
        System.out.println();
    }

    private void printResultChar(char guessChar, Integer resultCode)
    {
        if(resultCode == null)
        {
            System.out.format(" %c ", guessChar);
        }
        else
        {
            String background = backgrounds[resultCode];
            System.out.format("%s%s %c %s", background, COLOR_BLACK, guessChar, COLOR_RESET);
        }
    }
}