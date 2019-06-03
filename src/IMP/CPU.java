package IMP;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static IMP.DOW.split;

class CPU extends Player {
    private ArrayList<Integer> numbers = generatePossibleNums();
    private int currentGuess;


    CPU(int size) {
        super(size);
    }

    private ArrayList<Integer> Random() {
        Integer[] tempArr = new Integer[getSize()];
        for (int a = 0; a < getSize(); a++) {
            tempArr[a] = (int) (Math.random() * 9 + 1);
            for (int b = 0; b < a; b++) {
                if (tempArr[b].equals(tempArr[a])) {
                    a--;
                }
            }
        }
        return new ArrayList<>(Arrays.asList(tempArr));
    }

    void inputNumbers() {
        inputNumbers(Random());
    }

    ArrayList<Integer> guess() {
        currentGuess = numbers.iterator().next();
        return split(currentGuess);
    }

    void updateResult(DwCount result) {
        numbers.removeIf(str -> !DeadandWoundedCount(currentGuess, str).equals(result));
    }

    private ArrayList<Integer> generatePossibleNums() {
        ArrayList<Integer> list = new ArrayList<>();
        int lowerLimit = (int)Math.pow(10, getSize()-1);
        int higherlimit = (int)Math.pow(10, (getSize()));
        for (int i = lowerLimit; i < higherlimit; i++) {
            list.add(i);
        }
        Iterator<Integer> iter = list.iterator();
        while (iter.hasNext()) {
            int num = iter.next();
            ArrayList<Integer> digits = new ArrayList<>();
            for (int c : split(num)) {
                if (digits.contains(c)) {
                    iter.remove();
                    break;
                }
                digits.add(c);
            }
        }
        return list;
    }

    private DwCount DeadandWoundedCount(int num, int guess) {
        DwCount dwCount = new DwCount(0,0);
        ArrayList arrnum = split(num);
        ArrayList<Integer> arrguess = split(guess);
        for (int g : arrguess) {
            if (arrnum.contains(g)) {
                if (arrguess.indexOf(g) == arrnum.indexOf(g)) {
                    dwCount.dead++;
                } else {
                    dwCount.wounded++;
                }
            }
        }
        return dwCount;
    }


}
