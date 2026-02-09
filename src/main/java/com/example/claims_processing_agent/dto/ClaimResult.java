package com.example.claims_processing_agent.dto;


import java.util.List;
import java.util.Map;

public class ClaimResult {

    private Map<String, Object> extractedFields;
    private List<String> missingFields;
    private String recommendedRoute;
    private String reasoning;

    public Map<String, Object> getExtractedFields() {
        return extractedFields;
    }

    public void setExtractedFields(Map<String, Object> extractedFields) {
        this.extractedFields = extractedFields;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }

    public void setMissingFields(List<String> missingFields) {
        this.missingFields = missingFields;
    }

    public String getRecommendedRoute() {
        return recommendedRoute;
    }

    public void setRecommendedRoute(String recommendedRoute) {
        this.recommendedRoute = recommendedRoute;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }
}