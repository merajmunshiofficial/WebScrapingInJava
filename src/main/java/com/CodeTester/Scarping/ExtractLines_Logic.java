package com.CodeTester.Scarping;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractLines_Logic {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Meraj.munshi\\Downloads\\Leet_COdeSolutions\\output.txt"; // Provide the path to your text file here
        String linkFilePath = "C:\\Users\\Meraj.munshi\\Downloads\\Leet_COdeSolutions\\input.txt"; // Provide the path to the second text file with links here

        XWPFDocument docx = new XWPFDocument();
        FileOutputStream out = null;

        try {
            List<String> patterns = readPatterns(linkFilePath);
            Map<Integer, String> matchingLines = extractMatchingLines(filePath, patterns);

            for (String line : matchingLines.values()) {
                Pattern pattern = Pattern.compile("((http|https)://[\\w./'-]+): (.+)$");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String link = matcher.group(1);
                    String title = matcher.group(3);
                    XWPFParagraph paragraph = docx.createParagraph();
                    XWPFHyperlinkRun run = paragraph.createHyperlinkRun(link);
                    run.setText(title.trim());
                    run.setUnderline(UnderlinePatterns.SINGLE);
                    run.setColor("0000FF");
                }
            }

            // Save the document
            out = new FileOutputStream(new File("C:\\Users\\Meraj.munshi\\Downloads\\Leet_COdeSolutions\\DocWithHyper.docx")); // Provide the output path for the Word document
            docx.write(out);
            System.out.println("Word document created successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                docx.close();
            } catch (IOException e) {
                System.out.println("An error occurred while closing the resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static List<String> readPatterns(String inputTxtFilePath) throws IOException {
        List<String> patterns = new ArrayList<>();

        try (BufferedReader txtReader = new BufferedReader(new FileReader(inputTxtFilePath))) {
            String line;
            while ((line = txtReader.readLine()) != null) {
                String[] parts = line.split("\\s+", 2);
                if (parts.length > 0) {
                    patterns.add(parts[0].replaceAll("^0+", ""));
                }
            }
        }

        return patterns;
    }

    private static Map<Integer, String> extractMatchingLines(String inputDocxFilePath, List<String> patterns) throws IOException {
        Map<Integer, String> matchingLines = new TreeMap<>();

        try (BufferedReader txtReader = new BufferedReader(new FileReader(inputDocxFilePath))) {
            String line;
            while ((line = txtReader.readLine()) != null) {
                Pattern pattern = Pattern.compile(": (\\d+)\\s*-");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String number = matcher.group(1).replaceAll("^0+", ""); // Remove preceding zeros
                    if (patterns.contains(number)) {
                        matchingLines.put(Integer.parseInt(number), line);
                    }
                }
            }
        }

        return matchingLines;
    }
}
