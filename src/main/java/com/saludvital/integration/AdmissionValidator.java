package com.saludvital.integration;

import java.util.List;
import java.util.Map;

public class AdmissionValidator {
    public boolean validate(List<Map<String, String>> rows) {
        if (rows == null || rows.isEmpty()) return false;
        for (Map<String, String> row : rows) {
            if (isMissingData(row)) return false;
            if (!row.get("appointment_date").matches("\\d{4}-\\d{2}-\\d{2}")) return false;
            String insurance = row.get("insurance_code");
            if (!List.of("IESS", "PRIVADO", "NINGUNO").contains(insurance)) return false;
        }
        return true;
    }

    private boolean isMissingData(Map<String, String> row) {
        String[] required = {"patient_id", "full_name", "appointment_date", "insurance_code"};
        for (String field : required) {
            if (!row.containsKey(field) || row.get(field) == null || row.get(field).isBlank()) {
                return true;
            }
        }
        return false;
    }
}