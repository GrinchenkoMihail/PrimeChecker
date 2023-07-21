package org.example;

import org.apache.commons.lang3.tuple.MutablePair;
import java.util.List;
import java.util.stream.Collectors;

public class SieveOfEratosthenes {
    volatile private List<MutablePair<Long, Boolean>> listOfNumbers;
    private int quantityOfThreads;

    public SieveOfEratosthenes(List<MutablePair<Long, Boolean>> list, int threads) {
        this.listOfNumbers = list;
        this.quantityOfThreads = threads;
    }

    public void algorithm(){
        int range = rangeSelect();
        int numberThread = getNumberThread();
        int startIndexOfRange = numberThread*range;
        int endIndexOfRange=startIndexOfRange+range;
        if(endIndexOfRange!= listOfNumbers.size()-1 && numberThread== quantityOfThreads -1) endIndexOfRange= listOfNumbers.size();
        for (int i=startIndexOfRange; i < endIndexOfRange; i++) {
            if (listOfNumbers.get(i).getRight()
                    && listOfNumbers.get(i).getLeft()!=0
                    && listOfNumbers.get(i).getLeft()!=1) {
                for (int j = 0; j < listOfNumbers.size(); j++) {
                    if(listOfNumbers.get(j).getRight()
                            && listOfNumbers.get(i).getLeft()!= listOfNumbers.get(j).getLeft()
                            && listOfNumbers.get(j).getLeft()% listOfNumbers.get(i).getLeft()==0){
                        listOfNumbers.get(j).setRight(false);
                    }
                }
            }
        }
    }

    private int rangeSelect(){
        return listOfNumbers.size()/ quantityOfThreads;
    }

    public List<Long> getResultTable(){
        return listOfNumbers.stream()
                .filter(x->x.getRight())
                .map(x->x.getLeft())
                .collect(Collectors.toList());
    }

    private int getNumberThread(){
        String str = Thread.currentThread().getName().replaceAll("pool-1-thread-", "");
        return Integer.parseInt(str)-1;
    }

}
