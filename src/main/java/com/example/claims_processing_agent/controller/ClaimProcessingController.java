package com.example.claims_processing_agent.controller;

import com.example.claims_processing_agent.dto.ClaimResult;
import com.example.claims_processing_agent.processor.ClaimExtractor;
import com.example.claims_processing_agent.processor.ClaimRouter;
import com.example.claims_processing_agent.util.DocumentReader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/claims")
public class ClaimProcessingController {

    @PostMapping(value = "/process")
    public ClaimResult process(@RequestParam("file") MultipartFile file) throws Exception {

        File tempFile = File.createTempFile("fnol-", file.getOriginalFilename());
        file.transferTo(tempFile);

        String text = DocumentReader.read(tempFile.getAbsolutePath());

        Map<String, Object> extracted = ClaimExtractor.extract(text);

        List<String> missing = ClaimRouter.findMissingFields(extracted);

        ClaimRouter.RouteDecision d = ClaimRouter.route(extracted, missing);

        ClaimResult r = new ClaimResult();

        r.setExtractedFields(extracted);
        r.setMissingFields(missing);
        r.setRecommendedRoute(d.route);
        r.setReasoning(d.reason);

        return r;

    }
}
