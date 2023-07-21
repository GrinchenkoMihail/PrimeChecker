package org.example;

import org.apache.commons.lang3.tuple.MutablePair;

import java.io.BufferedReader;
import java.io.File;
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
        int numThreads = Integer.parseInt(args[0]);
        String parthToFile = args[1];

        SieveOfEratosthenes sieveOfEratosthenes = new SieveOfEratosthenes(readFile(parthToFile),numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for(int i=0;i<numThreads;i++) {
            executor.execute(() -> {
                sieveOfEratosthenes.algorithm();
            });
        }
        executor.shutdown();
        executor.awaitTermination(1,TimeUnit.MINUTES);
        sieveOfEratosthenes.getResultTable().stream().forEach(System.out::println);
    }

    public static List<MutablePair<Long, Boolean>> readFile( String parthToFile) {
        List<MutablePair<Long, Boolean>> result = new ArrayList<>();
        File file = new File("test-input.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(parthToFile))) {
            String str = reader.readLine();
            result = Arrays.stream(str.split(" "))
                    .map(x -> new MutablePair<Long, Boolean>(Long.parseLong(x), true))
                    .collect(Collectors.toList());
            System.out.println("File has been read");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
//java -jar <jar-file-name>.jar