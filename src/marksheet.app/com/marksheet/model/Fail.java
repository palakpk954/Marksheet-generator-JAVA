package com.marksheet.model;

public final record Fail(int marks, String reason) implements EvaluationResult {
    @Override
    public String getRemarks() {
        return "Needs Improvement.";
    }
}
