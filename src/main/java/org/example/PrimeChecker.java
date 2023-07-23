package org.example;

import org.apache.commons.lang3.tuple.MutablePair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PrimeChecker {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.out.println("Usage: java PrimeChecker <num_threads>");
            return;
        }
        if (Integer.parseInt(args[0]) < 1) {
            System.out.println("<num_threads> Must be less than 1");
            return;
        }
        int numThreads = Integer.parseInt(args[0]);
        String pathToFile = args[1];

//        AbstractSieve sieveOfEratosthenes = new SieveOfEratosthenes(readFileForPairs(pathToFile), numThreads);
        AbstractSieve sieveOfEratosthenes = new SieveOfEratosthenesAnotherSolution(readFile(pathToFile), numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Date start = new Date();
        for (int i = 0; i < numThreads; i++) {
            executor.execute(() -> {
                sieveOfEratosthenes.algorithm();
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        Date stop = new Date();
        System.out.println("Size sieve: " + sieveOfEratosthenes.getSieve().size());
        System.out.println("Application running time: " + (stop.getTime() - start.getTime()) + "ms");
    }

    public static List<MutablePair<Long, Boolean>> readFileForPairs(String parthToFile) {
        List<MutablePair<Long, Boolean>> listOfNumbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(parthToFile))) {
            String str = reader.readLine();
            listOfNumbers = Arrays.stream(str.split(" "))
                    .map(x -> new MutablePair<Long, Boolean>(Long.parseLong(x), true))
                    .collect(Collectors.toList());
            System.out.println("File has been read");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return listOfNumbers;
    }

    public static List<Long> readFile(String pathToFile) {
        List<Long> listOfNumbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            String str = reader.readLine();
            listOfNumbers = Arrays.stream(str.split(" "))
                    .map(x -> Long.parseLong(x))
                    .collect(Collectors.toList());
            System.out.println("File has been read");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return listOfNumbers;
    }
}