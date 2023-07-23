package org.example;

import java.util.List;

public abstract class AbstractSieve {
    private List<?> listOfNumbers;
    private int quantityOfThreads;

    public AbstractSieve(List<?> listOfNumbers, int quantityOfThreads) {
        this.listOfNumbers = listOfNumbers;
        this.quantityOfThreads = quantityOfThreads;
    }

    int rangeSelect() {
        return listOfNumbers.size() / quantityOfThreads;
    }

    int getNumberThread() {
        String str = Thread.currentThread().getName().replaceAll("pool-1-thread-", "");
        return Integer.parseInt(str) - 1;
    }

    abstract void algorithm();

    abstract List<Long> getSieve();
}
