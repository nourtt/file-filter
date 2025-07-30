package org.example;

import org.apache.commons.cli.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static boolean isInt(String line){
        try {
            Long.parseLong(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isFloat(String line){
        try {
            Double.parseDouble(line);
            return !isInt(line);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void main (String[] args) throws ParseException, IOException{
        Options options = new Options();
        options.addOption("o",true, "set path of the files");
        options.addOption("p", true, "adds prefix");
        options.addOption("a", false, "adds to existing");
        options.addOption("s", false, "short statistics");
        options.addOption("f", false, "full statistics");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        String pathPrefix = "";
        String filePrefix = "";
        if(cmd.hasOption("o")){
            pathPrefix = cmd.getOptionValue("o");
        }
        if(cmd.hasOption("p")){
            filePrefix = cmd.getOptionValue("p");
        }
        if (!pathPrefix.isEmpty()) {
            Path outputDir = Paths.get(pathPrefix);
            if (!Files.exists(outputDir)) {
                try {
                    Files.createDirectories(outputDir);
                } catch (IOException e) {
                    System.err.println("Could not create output directory: " + pathPrefix);
                    return;
                }
            }
        }
        Path pathInt = Paths.get(pathPrefix + filePrefix + "integers.txt");
        Path pathFloat = Paths.get(pathPrefix + filePrefix + "floats.txt");
        Path pathString = Paths.get(pathPrefix + filePrefix + "strings.txt");

        if (!cmd.hasOption("a")) {
            Files.write(pathInt, new ArrayList<>(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(pathFloat, new ArrayList<>(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(pathString, new ArrayList<>(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }
        List<String> inputFiles = cmd.getArgList();
        for (String i : inputFiles){
            Path path = Paths.get(i);
            if (!Files.exists(path)) {
                System.err.println("File not found: " + path);
                continue;
            }
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(originalLine ->{
                    String line = originalLine.trim();
                    if (line.isEmpty()) return;
                    if(isInt(line)){
                        try {
                            Files.write(pathInt, Collections.singletonList(line), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                        } catch (IOException e){
                            System.err.println("Error in counting lines");
                        }
                    } else if (isFloat(line)){
                        try {
                            Files.write(pathFloat, Collections.singletonList(line), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                        } catch (IOException e){
                            System.err.println("Error in counting lines");
                        }
                    } else {
                        try {
                            Files.write(pathString, Collections.singletonList(line), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                        } catch (IOException e){
                            System.err.println("Error in counting lines");
                        }
                    }
                });
            }
        }
        if (cmd.hasOption("s")){
            long integerCount = Files.lines(Paths.get(pathPrefix + filePrefix + "integers.txt")).count();
            System.out.println("Number of lines in " + pathPrefix + "/" + filePrefix + "integers.txt: " + integerCount);
            long floatCount = Files.lines(Paths.get(pathPrefix + filePrefix + "floats.txt")).count();
            System.out.println("Number of lines in " + pathPrefix + "/" + filePrefix + "floats.txt: " + floatCount);
            long stringCount = Files.lines(Paths.get(pathPrefix + filePrefix + "strings.txt")).count();
            System.out.println("Number of lines in " + pathPrefix + "/" + filePrefix + "strings.txt: " + stringCount);
        }
        if(cmd.hasOption("f")){
            try{
                long min = Integer.MAX_VALUE;
                long max = Integer.MIN_VALUE;
                long sum = 0;
                long count = 0;
                try (Stream<String> lines = Files.lines(Paths.get(pathPrefix + filePrefix + "integers.txt"))) {
                    for (String line : (Iterable<String>) lines::iterator) {
                        long num = Long.parseLong(line.trim());
                        if (num < min){
                            min = num;
                        }
                        if (num > max) {
                            max = num;
                        }
                        sum += num;
                        count++;
                    }
                } catch (IOException e) {
                    System.err.println("Error in reading the file");
                }
                double mean = (double) sum / count;
                System.out.println("Number of lines in " + pathPrefix  + "/" + filePrefix + "integers.txt: " + count);
                System.out.println("Largest value in " + pathPrefix + "/" + filePrefix + "integers.txt: " + max);
                System.out.println("Smallest value in " + pathPrefix + "/" + filePrefix + "integers.txt: " + min);
                System.out.println("Sum of values in " + pathPrefix + "/" + filePrefix + "integers.txt: " + sum);
                System.out.println("Average value in " + pathPrefix + "/" + filePrefix + "integers.txt: " + mean);
                System.out.println("");
            } catch (NumberFormatException e) {
                System.err.println("Error in providing full statistics for integers.txt");
            }
            try {
                double minFloat = Double.MAX_VALUE;
                double maxFloat = -Double.MAX_VALUE;
                double sumFloat = 0;
                long countFloat = 0;
                try (Stream<String> lines = Files.lines(Paths.get(pathPrefix + filePrefix + "floats.txt"))) {
                    for (String line : (Iterable<String>) lines::iterator) {
                        double num = Double.parseDouble(line.trim());
                        if (num < minFloat){
                            minFloat = num;
                        }
                        if (num > maxFloat) {
                            maxFloat = num;
                        }
                        sumFloat += num;
                        countFloat++;
                    }
                } catch (IOException e) {
                    System.err.println("Error in reading the file");
                }
                double meanFloat = sumFloat / countFloat;
                System.out.println("Number of lines in " + pathPrefix + "/" + filePrefix + "floats.txt: " + countFloat);
                System.out.println("Largest value in " + pathPrefix + "/" + filePrefix + "floats.txt: " + maxFloat);
                System.out.println("Smallest value in " + pathPrefix + "/" + filePrefix + "floats.txt: " + minFloat);
                System.out.println("Sum of values in " + pathPrefix + "/" + filePrefix + "floats.txt: " + sumFloat);
                System.out.println("Average value in " + pathPrefix + "/" + filePrefix + "floats.txt: " + meanFloat);
                System.out.println("");
            } catch (NumberFormatException e){
                System.err.println("Error in providing full statistics for floats.txt");
            }
            long stringCount = 0;
            long minString = Integer.MAX_VALUE;
            long maxString = 0;
            try (Stream<String> lines = Files.lines(Paths.get(pathPrefix + filePrefix + "strings.txt"))) {
                for (String line : (Iterable<String>) lines::iterator) {
                    if (line.length() < minString){
                        minString = line.length();
                    }
                    if (line.length() > maxString) {
                        maxString = line.length();
                    }
                    stringCount++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Number of lines in " + pathPrefix + "/" + filePrefix + "strings.txt: " + stringCount);
            System.out.println("The largest line in " + pathPrefix + "/" + filePrefix + "strings.txt has the length " + maxString);
            System.out.println("The smallest line in " + pathPrefix + "/" + filePrefix + "strings.txt has the length " + minString);
        }
    }
}
