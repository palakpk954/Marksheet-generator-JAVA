# Marksheet Generation System

A Java-based application for generating multi-semester marksheets/grade cards with a graphical user interface. This project demonstrates several core and advanced concepts in Java programming.

## Features
- Generates 8-semester marksheets matching university grade card formats.
- Interactive GUI (Java Swing) to accept Name and Registration Number.
- High-performance marksheet generation utilizing Java multithreading.
- Modular architecture leveraging the Java Platform Module System.

## Java Concepts Utilized

Here is a breakdown of the specific Java concepts and features implemented in this project:

### 1. Object-Oriented Programming (OOP)
- **Inheritance & Polymorphism:** The `Student` class extends a base `Person` class, inheriting attributes and demonstrating base OOP structures.
- **Encapsulation:** The data in models like `Marksheet` is encapsulated using `private` fields with `public` getters.

### 2. Modern Java Features
- **Sealed Interfaces (Java 15+):** The `EvaluationResult` interface is declared as `sealed`, specifically permitting only `Pass` and `Fail` to implement it. This allows for exhaustive pattern matching and type safety.
- **Java Records (Java 14+):** The `Pass` and `Fail` classes are implemented as `record` classes to concisely define immutable data carriers for evaluation outcomes without boilerplate code.
- **Switch Expressions (Java 12+):** Arrow (`->`) based switch expressions are used for calculating SGPA grade points and determining string representations for semesters.

### 3. Java Module System (Project Jigsaw)
- **Modularity (Java 9+):** The application is packaged as a module (`marksheet.app`) defined in `module-info.java`. It demonstrates how to explicitly require external modules (like `java.desktop` for Swing) and export packages (`com.marksheet`).

### 4. Concurrency & Multithreading
- **ExecutorService & Thread Pools:** To prevent blocking, marksheets for different semesters are generated concurrently using `Executors.newFixedThreadPool()`. 
- **Callable & Future:** Tasks are submitted as `Callable` instances, and their results are collected via `Future` objects.
- **SwingWorker:** Used to bridge the background generation threads with the Event Dispatch Thread (EDT) for the Swing UI, ensuring the GUI does not freeze during generation.

### 5. Graphical User Interface (GUI)
- **Java Swing:** Core components like `JFrame`, `JPanel`, `JTextField`, and `JTextArea` are used.
- **Event-Driven Programming:** Action listeners (using Lambda expressions) are used to handle user interactions such as button clicks.

### 6. Collections & Utilities
- **Java Collections Framework:** Use of `List`, `ArrayList`, and `Comparator` to manage and sort the generated marksheets.

## How to Run

1. **Compile the code:**
   ```bash
   javac -d out --module-source-path src -m marksheet.app
   ```
2. **Run the module:**
   ```bash
   java --module-path out --module marksheet.app/com.marksheet.Main
   ```
