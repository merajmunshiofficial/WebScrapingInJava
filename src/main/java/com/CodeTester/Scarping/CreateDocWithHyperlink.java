package com.CodeTester.Scarping;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateDocWithHyperlink {
    public static void main(String[] args) {
        XWPFDocument docx = new XWPFDocument();
        XWPFParagraph paragraph = docx.createParagraph();
        XWPFHyperlinkRun run = (XWPFHyperlinkRun) paragraph.createHyperlinkRun("https://stackoverflow.com/");
        run.setText("Stack Overflow");
        run.setUnderline(UnderlinePatterns.SINGLE);
        run.setColor("0000FF");

        // Save the document
        try {
            FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Meraj.munshi\\Downloads\\Leet_COdeSolutions\\DocWithHyperlink.docx"));
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
