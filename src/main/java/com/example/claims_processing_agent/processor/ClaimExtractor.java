package com.example.claims_processing_agent.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClaimExtractor {

    public static Map<String, Object> extract(String text) {

        Map<String, Object> fields = new HashMap<>();

        fields.put("policyNumber", find(text, "Policy Number[:\\s]+(.+)"));
        fields.put("policyholderName", find(text, "Policyholder Name[:\\s]+(.+)"));
        fields.put("effectiveDates", find(text, "Effective Dates?[:\\s]+(.+)"));

        fields.put("incidentDate", find(text, "Incident Date[:\\s]+(.+)"));
        fields.put("incidentTime", find(text, "Incident Time[:\\s]+(.+)"));
        fields.put("incidentLocation", find(text, "Incident Location[:\\s]+(.+)"));
        fields.put("description", find(text, "Description[:\\s]+(.+)"));

        fields.put("claimant", find(text, "Claimant[:\\s]+(.+)"));
        fields.put("thirdParties", find(text, "Third Parties[:\\s]+(.+)"));
        fields.put("contactDetails", find(text, "Contact Details[:\\s]+(.+)"));

        fields.put("assetType", find(text, "Asset Type[:\\s]+(.+)"));
        fields.put("assetId", find(text, "Asset ID[:\\s]+(.+)"));
        fields.put("estimatedDamage", find(text, "Estimated Damage[:\\s]+(.+)"));

        fields.put("claimType", find(text, "Claim Type[:\\s]+(.+)"));
        fields.put("attachments", find(text, "Attachments?[:\\s]+(.+)"));
        fields.put("initialEstimate", find(text, "Initial Estimate[:\\s]+(.+)"));

        return fields;
    }

    private static String find(String text, String regex) {

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return null;
    }
}
