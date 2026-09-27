# CodeAlpha_StudentGradeTracker

A console-based Java application to add, manage, and analyze student grades — built for the **CodeAlpha Java Programming Internship** (Task 1: Student Grade Tracker).

## Overview

This program lets you register students, record their subject-wise marks, and instantly see calculated performance data: total, average, highest, lowest, and letter grade — for both individual students and the class as a whole.

## Features

- **Add Student** — register a student with an ID, name, and any number of subjects/marks.
- **View All Students** — see every student's subjects, marks, and calculated stats.
- **Search Student** — look up a student by exact ID or partial name.
- **Update Marks** — re-enter a student's subjects/marks.
- **Delete Student** — remove a student record (with confirmation).
- **Class Performance** — top/bottom average, class-wide highest/lowest mark, overall class average.
- **Summary Report** — a table of every student's total, average, highest, lowest, and grade.
- **Input validation everywhere** — the program never crashes on bad input.

## Grading Scale

| Average | Grade |
|---|---|
| 90–100 | A+ |
| 80–89  | A  |
| 70–79  | B+ |
| 60–69  | B  |
| 50–59  | C  |
| 40–49  | D  |
| < 40   | F  |

## Technologies

- Java (standard library only — no external dependencies)
- `ArrayList` for dynamic storage of `Student` and `Subject` records

## OOP Concepts Used

- **Classes & Objects** — `Student`, `Subject`, `GradeTracker`
- **Encapsulation** — private fields with validated getters/setters
- **Constructors** — validate data as soon as an object is created
- **Custom Exceptions** — `DuplicateStudentException`, `StudentNotFoundException`
- **Exception Handling** — `try/catch` around every user-facing operation

## Project Structure

```
CodeAlpha_StudentGradeTracker/
├── src/
│   ├── Subject.java                  # A single subject + marks
│   ├── Student.java                  # A student's record + performance math
│   ├── GradeTracker.java             # Manages the ArrayList<Student>
│   ├── DuplicateStudentException.java
│   ├── StudentNotFoundException.java
│   └── Main.java                     # Console UI / menu
└── README.md
```

### Why this structure?

`Subject` and `Student` model the data. `GradeTracker` owns the `ArrayList<Student>` and all
list-level operations (add/search/update/delete/reports), so `Main` never touches the list
directly — it only calls `GradeTracker` methods. This keeps the UI layer thin and the data
encapsulated, and makes it easy to later swap the console UI for a GUI without touching the
logic.

## How to Run

### VS Code
1. Install the "Extension Pack for Java" from the VS Code marketplace.
2. Open the `CodeAlpha_StudentGradeTracker` folder.
3. Open `src/Main.java` and click **Run** above the `main` method (or press `F5`).

### IntelliJ IDEA
1. `File → Open` and select the `CodeAlpha_StudentGradeTracker` folder.
2. Let IntelliJ index the project (no build file is needed — plain `.java` files work).
3. Right-click `Main.java` → **Run 'Main.main()'**.

### Command Line
```bash
cd CodeAlpha_StudentGradeTracker/src
javac *.java
java Main
```

## Sample Input/Output

```
========================================
     WELCOME TO STUDENT GRADE TRACKER
========================================
========================================
           STUDENT GRADE TRACKER
========================================
1. Add Student
2. View All Students
3. Search Student
4. Update Marks
5. Delete Student
6. Class Performance
7. Summary Report
8. Exit
========================================
Enter your choice (1-8): 1

--- Add Student ---
Enter Student ID: S101
Enter Student Name: Asha Rao
How many subjects? 3
  Subject 1 name: Maths
  Marks for Maths (0-100): 92
  Subject 2 name: Science
  Marks for Science (0-100): 81
  Subject 3 name: English
  Marks for English (0-100): 88
Student 'Asha Rao' added successfully.
```

```
Enter your choice (1-8): 2
------------------------------------------------------
Student ID   : S101
Student Name : Asha Rao
Subjects & Marks:
   Maths                :  92.00
   Science              :  81.00
   English              :  88.00
------------------------------------------------------
Total Marks  : 261.00
Average      : 87.00
Highest      : 92.00
Lowest       : 81.00
Grade        : A
------------------------------------------------------
```

## Author's Notes

Built as a submission for the CodeAlpha Java Programming Internship, Task 1.
