package com.marksheet.model;

public sealed interface EvaluationResult permits Pass, Fail {
    String getRemarks();
}
