package org.example;

import org.apache.commons.lang3.tuple.MutablePair;

import java.util.List;
import java.util.stream.Collectors;

public class SieveOfEratosthenes extends AbstractSieve {
    volatile private List<MutablePair<Long, Boolean>> listOfNumbers;
    private int quantityOfThreads;

    public SieveOfEratosthenes(List<MutablePair<Long, Boolean>> list, int threads) {
        super(list, threads);
        this.listOfNumbers = list;
        this.quantityOfThreads = threads;
    }

    public void algorithm() {

        int range = rangeSelect();
        int numberThread = getNumberThread();
        int startIndexOfRange = numberThread * range;
        int endIndexOfRange = startIndexOfRange + range;
        if (endIndexOfRange != listOfNumbers.size() - 1 && numberThread == quantityOfThreads - 1)
            endIndexOfRange = listOfNumbers.size();

        for (int i = startIndexOfRange; i < endIndexOfRange; i++) {
            if (listOfNumbers.get(i).getRight()
                    && listOfNumbers.get(i).getLeft() > 1) {
                for (int j = 2; j * j <= listOfNumbers.get(i).getLeft(); ++j) {
                    if ((listOfNumbers.get(i).getLeft() % j == 0)) {
                        listOfNumbers.get(i).setRight(false);
                        System.out.println(Thread.currentThread().getName() + " Not a prime number: " + listOfNumbers.get(i).getLeft());
                        break;
                    }
                }
            }
        }
    }

    public List<Long> getSieve() {
        return listOfNumbers.stream()
                .filter(x -> x.getRight())
                .map(x -> x.getLeft())
                .collect(Collectors.toList());
    }
}
