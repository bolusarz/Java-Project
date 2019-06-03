package IMP;

import java.io.IOException;
import java.util.*;

class DOW {
    private Scanner scanner = new Scanner(System.in);

    DOW() {
        initializeGame();
    }

    private void initializeGame() {
        welcome();
    }

    private void welcome() {
        System.out.println("-----Dead or Wounded, The Game of probability----");
        chooseGameStyle();
    }

    private void chooseGameStyle() {
        System.out.println("How do you want to proceed?");
        System.out.println("Press 1 for CPU vs Player, Press 2 for Player vs Player");
        playGame(scanner.next());
    }

    private void playGame(String style) {
        switch (style) {
            case "1":
                cpuVsPlayer();
                break;
            case "2":
                playerVsPlayer();
                break;
        }
    }

    private void cpuVsPlayer() {
        System.out.println("Choose Difficulty (3 and above)");
        int size = scanner.nextInt();
        Player player1 = new Player(size);
        CPU cpu = new CPU(size);
        InputNumbers(player1, "Enter your secret numbers: ", size);
        System.out.println("Can you chill? Computer is generating its own secret numbers");
        cpu.inputNumbers();
        System.out.println("Computer is done. Now the game starts ");
        while(true) {
            System.out.println("Guess the computer's secret number");
            int guesses;
            while(true) {
                guesses = scanner.nextInt();
                if (guesses > (int)(Math.pow(10, size+1)-1)) {
                    System.out.println("Your guess cannot be more than " + size +" digits");
                    System.out.println();
                }
                else {
                    break;
                }
            }
            boolean result = compare(cpu, split(guesses));
            player1.numberOfGuesses++;
            if (result) {
                System.out.println("You win after " + player1.numberOfGuesses + " guesses");
                System.out.println();
                break;
            } else {
                System.out.println("CPU is trying to guess your number");
                cpu.numberOfGuesses++;
                ArrayList<Integer> cpuGuess = cpu.guess();
                System.out.println(cpuGuess);
                System.out.println();
                DwCount answer = player1.validate(cpuGuess);
                if (answer.dead == player1.getSize()) {
                    System.out.println("CPU wins after " + cpu.numberOfGuesses +" guesses");
                    break;
                } else {
                    cpu.updateResult(answer);
                    System.out.println(answer.wounded + " wounded " + answer.dead + " dead.");
                }
            }
        }
    }

    private void playerVsPlayer() {
        System.out.println("Choose Difficulty (3 and above)");
        int size = scanner.nextInt();
        Player player1 = new Player(size);
        Player player2 = new Player(size);
        InputNumbers(player1, "Player 1: Enter your secret number: ", size);
        InputNumbers(player2, "Player 2: Enter you secret number: ", size);
        while(true) {
            System.out.println("Guess Player 1's secret number");
            int guesses = scanner.nextInt();
            boolean result = compare(player1, split(guesses));
            player2.numberOfGuesses++;
            if (result) {
                System.out.println("Player 2 wins after " + player2.numberOfGuesses + " guesses");
                break;
            } else {
                System.out.println("Guess Player 2's secret number");
                guesses = scanner.nextInt();
                player1.numberOfGuesses++;
                result = compare(player2, split(guesses));
                if (result) {
                    System.out.println("Player 1 wins after " + player1.numberOfGuesses + " guesses");
                    break;
                }
            }
        }
    }

    private boolean compare(Player player, ArrayList<Integer> guesses) {
        DwCount answer = player.validate(guesses);
        if (answer.dead == player.getSize()) {
            return true;
        } else {
            System.out.println(answer.wounded + " wounded " + answer.dead + " dead.");
            System.out.println();
            return false;
        }
    }

    static ArrayList<Integer> split(int number) {
        ArrayList<Integer> arr = new ArrayList<>();
        String temp = Integer.toString(number);
        for (int i = 0; i < temp.length(); i++) {
            arr.add((temp.charAt(i) - '0'));
        }
        return arr;
    }

    private static ArrayList<Integer> convertStringToIntArr(String arr) {
        return split(Integer.parseInt(arr));
    }

    private void InputNumbers(Player player, String prompt, int size) {
        char[] secretNumber;
        while(true) {
            try {
                secretNumber = SecretInput.readInput(System.in, prompt);
                if (secretNumber.length < size) {
                    throw new ArrayIndexOutOfBoundsException("You cannot enter less than " + size + " numbers");
                } else if (secretNumber.length > size) {
                    throw new ArrayIndexOutOfBoundsException("You cannot enter more than " + size + " numbers");
                }
                if (secretNumber == null) {
                    throw new NullPointerException("Please enter a value");
                }
                for (int i = 0; i < secretNumber.length; i++) {
                    for (int k = i+1; k < secretNumber.length; k++) {
                        if(secretNumber[i] == secretNumber[k]) {
                            throw new DuplicatesException();
                        }
                    }
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException | NullPointerException | DuplicatesException e) {
                System.out.println(e.getMessage());
            }
        }
        player.inputNumbers(convertStringToIntArr(String.valueOf(secretNumber)));
    }
}
