package com.marksheet.model;

public final record Pass(int marks, String grade) implements EvaluationResult {
    @Override
    public String getRemarks() {
        return "Excellent work!";
    }
}
