package com.marksheet.model;

public final class Student extends Person {
    private final String rollNumber;

    public Student(String name, String rollNumber) {
        super(name);
        this.rollNumber = rollNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }
}
