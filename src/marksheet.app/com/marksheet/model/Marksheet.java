package com.marksheet.model;

public class Marksheet<T extends EvaluationResult> {
    private final Student student;
    private final T result;
    private final int semester;
    
    public Marksheet(Student student, T result, int semester) {
        this.student = student;
        this.result = result;
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public T getResult() {
        return result;
    }
    
    public int getSemester() {
        return semester;
    }

    public void display() {
        // Switch Case with Arrow Syntax
        String status = switch (result) {
            case Pass p -> "Passed with Grade: " + p.grade();
            case Fail f -> "Failed. Reason: " + f.reason();
        };

        // Text Blocks
        String marksheetView = """
                =========================================
                               MARKSHEET
                =========================================
                Semester    : %d
                Name        : %s
                Roll Number : %d
                Status      : %s
                Remarks     : %s
                =========================================
                """.formatted(
                        semester,
                        student.getName(),
                        student.getRollNumber(),
                        status,
                        result.getRemarks()
                );
        System.out.println(marksheetView);
    }
}
