package com.example.claims_processing_agent.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClaimRouter {

    // Mandatory fields based on the assessment brief
    private static final String[] MANDATORY_FIELDS = {
            "policyNumber",
            "policyholderName",
            "effectiveDates",
            "incidentDate",
            "incidentTime",
            "incidentLocation",
            "description",
            "claimant",
            "contactDetails",
            "assetType",
            "assetId",
            "estimatedDamage",
            "claimType",
            "attachments",
            "initialEstimate"
    };

    /**
     * Finds missing mandatory fields
     */
    public static List<String> findMissingFields(Map<String, Object> fields) {

        List<String> missing = new ArrayList<>();

        for (String field : MANDATORY_FIELDS) {

            Object value = fields.get(field);

            if (value == null || value.toString().trim().isEmpty()) {
                missing.add(field);
            }
        }

        return missing;
    }

    /**
     * Applies routing rules
     */
    public static RouteDecision route(Map<String, Object> fields,
                                      List<String> missingFields) {

        // 1. Missing mandatory fields -> Manual review
        if (!missingFields.isEmpty()) {
            return new RouteDecision(
                    "Manual review",
                    "One or more mandatory fields are missing."
            );
        }

        String description =
                String.valueOf(fields.getOrDefault("description", "")).toLowerCase();

        String claimType =
                String.valueOf(fields.getOrDefault("claimType", "")).toLowerCase();

        // 2. Investigation keywords
        if (description.contains("fraud")
                || description.contains("inconsistent")
                || description.contains("staged")) {

            return new RouteDecision(
                    "Investigation Flag",
                    "Suspicious keywords found in claim description."
            );
        }

        // 3. Injury claims
        if ("injury".equals(claimType)) {
            return new RouteDecision(
                    "Specialist Queue",
                    "Claim type is injury."
            );
        }

        // 4. Fast-track for low damage
        Object estimatedDamageObj = fields.get("estimatedDamage");

        try {
            double estimatedDamage =
                    Double.parseDouble(estimatedDamageObj.toString());

            if (estimatedDamage < 25000) {
                return new RouteDecision(
                        "Fast-track",
                        "Estimated damage is below 25,000."
                );
            }

        } catch (Exception e) {
            // parsing failed – fall through to manual review
        }

        // Default
        return new RouteDecision(
                "Manual review",
                "Claim does not meet fast-track or specialist criteria."
        );
    }

    // Simple holder for route + reasoning
    public static class RouteDecision {

        public final String route;
        public final String reason;

        public RouteDecision(String route, String reason) {
            this.route = route;
            this.reason = reason;
        }
    }
}