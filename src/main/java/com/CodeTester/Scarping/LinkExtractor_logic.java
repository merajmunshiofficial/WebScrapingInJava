package com.CodeTester.Scarping;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

public class LinkExtractor_logic {
    public static void main(String[] args) {
        String url = "https://leetcode.ca/";
        Set<String> extractedLinks = new HashSet<>();

        try {
            // Create a trust manager that does not perform checks
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Use the custom SSL context to connect to the URL
            try {
                Document doc = Jsoup.connect(url).get();

                // Extract all the links
                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    // Print the link along with the text it contains
                    String extractedLink = link.attr("href");
                    extractedLinks.add(extractedLink);
                    System.out.println("Text: " + link.text() + " Link: " + extractedLink);
                }

                // Visit each extracted link and extract h1 heading
                FileWriter fileWriter = new FileWriter("C:\\Users\\Meraj.munshi\\Downloads\\Leet_COdeSolutions\\output.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for (String extractedLink : extractedLinks) {
                    try {
                        if (!extractedLink.startsWith("http")) {
                            extractedLink = url + extractedLink;
                        }
                        Document innerDoc = Jsoup.connect(extractedLink).get();
                        Element h1 = innerDoc.selectFirst("h1");
                        if (h1 != null) {
                            String output = "H1 heading from " + extractedLink + ": " + h1.text() + "\n";
                            System.out.println(output);
                            bufferedWriter.write(output);
                        } else {
                            String output = "No h1 heading found in " + extractedLink + "\n";
                            System.out.println(output);
                            bufferedWriter.write(output);
                        }
                    } catch (IOException e) {
                        System.err.println("Error connecting to " + extractedLink);
                        e.printStackTrace();
                    }
                }
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
