package com.example.claims_processing_agent.util;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.nio.file.Files;

public class DocumentReader {

    public static String read(String path) throws Exception {

        File file = new File(path);

        if (path.toLowerCase().endsWith(".pdf")) {

            try (PDDocument doc = PDDocument.load(file)) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(doc);
            }

        } else {
            return Files.readString(file.toPath());
        }
    }
}