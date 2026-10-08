package com.marksheet;

import com.marksheet.model.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("JECRC University - Marksheet Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 750);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // --- Input Panel ---
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        inputPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(15);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Reg No:"));
        JTextField regField = new JTextField(12);
        inputPanel.add(regField);

        JButton generateBtn = new JButton("Generate Marksheets");
        inputPanel.add(generateBtn);

        frame.add(inputPanel, BorderLayout.NORTH);

        // --- Output Area ---
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13)); 
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Grade Cards"));
        frame.add(scrollPane, BorderLayout.CENTER);

        // --- Button Action ---
        generateBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String regText = regField.getText().trim().toUpperCase();

            if (name.isEmpty() || regText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both Name and Reg No.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            generateBtn.setEnabled(false);
            outputArea.setText("Generating marksheets for " + name + " (Reg No: " + regText + ")...\n");

            SwingWorker<String, Void> worker = new SwingWorker<>() {
                @Override
                protected String doInBackground() throws Exception {
                    return generateMarksheetText(name, regText);
                }

                @Override
                protected void done() {
                    try {
                        outputArea.setText(get());
                        outputArea.setCaretPosition(0); // Scroll to top
                    } catch (Exception ex) {
                        outputArea.setText("Error generating marksheets: " + ex.getMessage());
                    } finally {
                        generateBtn.setEnabled(true);
                    }
                }
            };
            worker.execute();
        });

        frame.setVisible(true);
    }

    private static String getSemesterString(int sem) {
        return switch (sem) {
            case 1 -> "FIRST";
            case 2 -> "SECOND";
            case 3 -> "THIRD";
            case 4 -> "FOURTH";
            case 5 -> "FIFTH";
            case 6 -> "SIXTH";
            case 7 -> "SEVENTH";
            case 8 -> "EIGHTH";
            default -> "UNKNOWN";
        };
    }

    private static String generateMarksheetText(String name, String regNo) {
        StringBuilder sb = new StringBuilder();
        String dateStr = new SimpleDateFormat("d-MMM-yyyy HH:mm:ss").format(new Date());

        // Dummy subjects to mimic the format
        String[][] subjects = {
            {"BCO028B", "Compiler Construction", "4"},
            {"BCO031B", "Compiler Design Lab", "1"},
            {"BCO037B", "Advance Programming in Java", "3"},
            {"BCO069B", "Advance Programming in Java Lab", "1"},
            {"BCO074C", "Minor Project", "4"},
            {"BCO486A", "Aws Academy Cloud Developing", "3"},
            {"NPTEL824A", "Forests and their Management", "3"}
        };
        
        String[] possibleGrades = {"O", "A+", "A", "B+", "B", "C"};

        for (int sem = 1; sem <= 8; sem++) {
            sb.append("\n");
            sb.append("                               GRADE CARD\n\n");
            sb.append("                  School of Engineering and Technology\n");
            sb.append("                         Bachelor of Technology\n");
            sb.append(String.format("                   %s SEMESTER ,%s\n\n", getSemesterString(sem), regNo));

            sb.append(String.format("%-45s REG.NO.: %s\n", "NAME : " + name.toUpperCase(), regNo));
            sb.append("FATHER'S NAME : KANHAIYA PRASAD\n\n");

            sb.append("----------------------------------------------------------------------------------------\n");
            sb.append(String.format("%-15s | %-45s | %-8s | %-5s\n", "SUBJECT CODE", "SUBJECT TITLE", "CREDIT", "GRADE"));
            sb.append("----------------------------------------------------------------------------------------\n");

            int totalCredits = 0;
            double totalGradePoints = 0;
            
            for (String[] sub : subjects) {
                int credit = Integer.parseInt(sub[2]);
                totalCredits += credit;
                
                // Randomly assign grades for simulation based on semester to keep it somewhat varied
                int gradeIndex = (regNo.hashCode() + sem + sub[0].hashCode()) % possibleGrades.length;
                if (gradeIndex < 0) gradeIndex *= -1;
                String grade = possibleGrades[gradeIndex];
                
                double gp = switch(grade) {
                    case "O" -> 10.0;
                    case "A+" -> 9.0;
                    case "A" -> 8.0;
                    case "B+" -> 7.0;
                    case "B" -> 6.0;
                    case "C" -> 5.0;
                    default -> 0.0;
                };
                totalGradePoints += (gp * credit);

                sb.append(String.format("%-15s | %-45s |    %d     |   %s\n", sub[0], sub[1], credit, grade));
            }
            
            sb.append("----------------------------------------------------------------------------------------\n");
            sb.append(String.format("%-15s   %-45s |    %d    |   ---\n", "TOTAL", "", totalCredits));
            sb.append("----------------------------------------------------------------------------------------\n\n");

            double sgpa = totalGradePoints / totalCredits;
            sb.append(String.format("  SGPA : %.2f                                      CREDITS EARNED : %d\n\n", sgpa, totalCredits));
            
            sb.append("----------------------------------------------------------------------------------------\n");
            sb.append("Date " + dateStr + "\n");
            sb.append("========================================================================================\n\n\n\n");
        }

        return sb.toString();
    }
}
