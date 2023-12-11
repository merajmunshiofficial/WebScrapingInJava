package com.CodeTester.Scarping;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateDocWithHyperlink_logic {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Meraj.munshi\\Downloads\\Leet_COdeSolutions\\output.txt"; // Provide the path to your text file here
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            XWPFDocument docx = new XWPFDocument();
            String line;
            while ((line = reader.readLine()) != null) {
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
            reader.close();

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
}
