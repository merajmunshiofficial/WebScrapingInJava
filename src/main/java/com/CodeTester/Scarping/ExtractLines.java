package com.CodeTester.Scarping;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractLines {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Meraj.munshi\\Downloads\\Leet_COdeSolutions\\output.txt"; // Provide the path to your text file here
        String linkFilePath = "C:\\Users\\Meraj.munshi\\Downloads\\Leet_COdeSolutions\\input.txt"; // Provide the path to the second text file with links here

        try {
            List<String> patterns = readPatterns(linkFilePath);
            List<String> matchingLines = extractMatchingLines(filePath, patterns);

            XWPFDocument docx = new XWPFDocument();
            for (String line : matchingLines) {
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
            FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Meraj.munshi\\Downloads\\Leet_COdeSolutions\\DocWithHyper.docx")); // Provide the output path for the Word document
            docx.write(out);
            out.close();
            docx.close();
            System.out.println("Word document created successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
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

    private static List<String> extractMatchingLines(String inputDocxFilePath, List<String> patterns) throws IOException {
        List<String> matchingLines = new ArrayList<>();

        try (BufferedReader txtReader = new BufferedReader(new FileReader(inputDocxFilePath))) {
            String line;
            while ((line = txtReader.readLine()) != null) {
                Pattern pattern = Pattern.compile(": (\\d+)\\s*-");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String number = matcher.group(1).replaceAll("^0+", ""); // Remove preceding zeros
                    if (patterns.contains(number)) {
                        matchingLines.add(line);
                    }
                }
            }
        }

        return matchingLines;
    }
}
