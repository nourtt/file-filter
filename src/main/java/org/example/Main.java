package org.example;

import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main (String args[]) throws ParseException{
        Options options = new Options();
        options.addOption("o",true, "set path of the files");
        options.addOption("p", true, "adds prefix");
        options.addOption("a", false, "rewrites");
        options.addOption("s", false, "short statistics");
        options.addOption("f", false, "full statistics");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        File integers = new File("integers.txt");
        File strings = new File("strings.txt");

        List<String> inputFiles = cmd.getArgList();

        for (String i : inputFiles){
            System.out.println(i);
        }
    }
}
