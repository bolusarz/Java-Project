package IMP;

import java.util.ArrayList;
import java.util.Arrays;

class Player {
    int numberOfGuesses = 0;
    private int size;
    private ArrayList<Integer> secretNumbers;

    Player(int size) {
        setSize(size);
    }

    int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    void inputNumbers(Integer[] numbers) {
        if (numbers.length <= getSize()) this.secretNumbers = new ArrayList<>(Arrays.asList(numbers));
    }

    void inputNumbers(ArrayList<Integer> numbers) {
        if (numbers.size() <= getSize()) this.secretNumbers = numbers;
    }

    DwCount validate(ArrayList<Integer> guesses) {
        DwCount result = new DwCount(0, 0);
        for (int guess : guesses) {
            if(this.secretNumbers.contains(guess)) {
                if (this.secretNumbers.indexOf(guess) == guesses.indexOf(guess)) {
                    result.dead++;
                } else {
                    result.wounded++;
                }
            }
        }
        return result;
    }
}
