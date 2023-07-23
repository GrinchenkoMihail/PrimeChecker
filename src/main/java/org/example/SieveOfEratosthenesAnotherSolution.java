package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SieveOfEratosthenesAnotherSolution extends AbstractSieve {
    private List<Long> listOfNumbers;
    private int quantityOfThreads;
    private volatile List<Long> sieve = new ArrayList<>();

    public SieveOfEratosthenesAnotherSolution(List<Long> listOfNumbers, int quantityOfThreads) {
        super(listOfNumbers, quantityOfThreads);
        this.listOfNumbers = listOfNumbers;
        this.quantityOfThreads = quantityOfThreads;
    }

    public void algorithm() {

        int range = rangeSelect();
        int numberThread = getNumberThread();
        int startIndexOfRange = numberThread * range;
        int endIndexOfRange = startIndexOfRange + range;
        if (endIndexOfRange != listOfNumbers.size() - 1 && numberThread == quantityOfThreads - 1)
            endIndexOfRange = listOfNumbers.size();

        Date start = new Date();
        for (int i = startIndexOfRange; i < endIndexOfRange; i++) {
            if (listOfNumbers.get(i) > 1) {
                boolean cheack=false;
                synchronized (sieve) {
                    for (int k = 0; k < sieve.size(); k++) {
                        if (listOfNumbers.get(i) % sieve.get(k) == 0 && listOfNumbers.get(i)!=sieve.get(k)) {
                            System.out.println(Thread.currentThread().getName() + " CurrentNum: " + listOfNumbers.get(i)+" and NumPrime in sieve: "+ sieve.get(k) );
                            cheack = true;
                            break;
                        }
                    }
                }
                if(!cheack) {
                    for (int j = 2; j * j <= listOfNumbers.get(i); j++) {
                        if (listOfNumbers.get(i) % j == 0 ) {
                            break;
                        } else if ((j + 1) * (j + 1) >= listOfNumbers.get(i)) {
                            synchronized (sieve) {
                                sieve.add(listOfNumbers.get(i));
                                System.out.println((new Date().getTime() - start.getTime()) + "ms " + Thread.currentThread().getName() + " " + "Sieve size:  " + sieve.size() + " Added prime: " + listOfNumbers.get(i));
                            }
                        }
                    }
                }
            }
        }
    }


    public List<Long> getSieve() {
        return sieve;
    }
}
