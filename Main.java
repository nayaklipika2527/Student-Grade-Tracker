import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main.java
 *
 * The program entry point and console user interface. This class only
 * handles input/output and menu flow - all real logic (calculations,
 * storage, validation of business rules) is delegated to GradeTracker,
 * Student, and Subject. That separation keeps the UI easy to change
 * later (e.g. to a GUI) without touching the core logic.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final GradeTracker tracker = new GradeTracker();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("     WELCOME TO STUDENT GRADE TRACKER    ");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readMenuChoice(1, 8);

            switch (choice) {
                case 1:
                    addStudentFlow();
                    break;
                case 2:
                    tracker.viewAllStudents();
                    break;
                case 3:
                    searchStudentFlow();
                    break;
                case 4:
                    updateMarksFlow();
                    break;
                case 5:
                    deleteStudentFlow();
                    break;
                case 6:
                    tracker.showClassPerformance();
                    break;
                case 7:
                    tracker.printSummaryReport();
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using Student Grade Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("========================================");
        System.out.println("           STUDENT GRADE TRACKER         ");
        System.out.println("========================================");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Marks");
        System.out.println("5. Delete Student");
        System.out.println("6. Class Performance");
        System.out.println("7. Summary Report");
        System.out.println("8. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice (1-8): ");
    }

    // ================= FEATURE FLOWS =================

    private static void addStudentFlow() {
        System.out.println("\n--- Add Student ---");

        String id;
        while (true) {
            id = readNonEmptyLine("Enter Student ID: ");
            if (tracker.isDuplicateId(id)) {
                System.out.println("A student with that ID already exists. Please use a unique ID.");
            } else {
                break;
            }
        }

        String name = readNonEmptyLine("Enter Student Name: ");
        Student student = new Student(id, name);

        int subjectCount = readIntInRange("How many subjects? ", 1, 20);
        for (int i = 1; i <= subjectCount; i++) {
            String subjectName = readNonEmptyLine("  Subject " + i + " name: ");
            double marks = readValidMarks("  Marks for " + subjectName + " (0-100): ");
            student.addSubject(new Subject(subjectName, marks));
        }

        try {
            tracker.addStudent(student);
            System.out.println("Student '" + name + "' added successfully.");
        } catch (DuplicateStudentException e) {
            // Should not normally happen since we already checked above,
            // but handled here in case of a race between the two checks.
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchStudentFlow() {
        System.out.println("\n--- Search Student ---");
        String query = readNonEmptyLine("Enter Student ID or Name to search: ");
        List<Student> results = tracker.search(query);

        if (results.isEmpty()) {
            System.out.println("No matching student found.");
            return;
        }

        System.out.println("Found " + results.size() + " matching student(s):");
        for (Student s : results) {
            s.displayDetails();
        }
    }

    private static void updateMarksFlow() {
        System.out.println("\n--- Update Marks ---");
        String id = readNonEmptyLine("Enter Student ID to update: ");

        Student existing;
        try {
            existing = tracker.findById(id);
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("Current record:");
        existing.displayDetails();
        System.out.println("You will now re-enter all subjects and marks for this student.");

        int subjectCount = readIntInRange("How many subjects? ", 1, 20);
        List<Subject> newSubjects = new ArrayList<>();
        for (int i = 1; i <= subjectCount; i++) {
            String subjectName = readNonEmptyLine("  Subject " + i + " name: ");
            double marks = readValidMarks("  Marks for " + subjectName + " (0-100): ");
            newSubjects.add(new Subject(subjectName, marks));
        }

        try {
            tracker.updateStudentMarks(id, newSubjects);
            System.out.println("Marks updated successfully for '" + existing.getName() + "'.");
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteStudentFlow() {
        System.out.println("\n--- Delete Student ---");
        String id = readNonEmptyLine("Enter Student ID to delete: ");
        try {
            Student toDelete = tracker.findById(id);
            String confirm = readNonEmptyLine(
                    "Are you sure you want to delete '" + toDelete.getName() + "'? (yes/no): ");
            if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
                tracker.deleteStudent(id);
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= INPUT VALIDATION HELPERS =================

    /** Keeps asking until the user provides a non-empty, non-blank line. */
    private static String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    /** Keeps asking until the user enters a valid double between 0 and 100. */
    private static double readValidMarks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double marks = Double.parseDouble(input);
                if (marks < 0 || marks > 100) {
                    System.out.println("Marks must be between 0 and 100. Please try again.");
                    continue;
                }
                return marks;
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid number. Please try again.");
            }
        }
    }

    /** Keeps asking until the user enters a whole number within [min, max]. */
    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid whole number. Please try again.");
            }
        }
    }

    /** Reads and validates the main menu choice so bad input never crashes the app. */
    private static int readMenuChoice(int min, int max) {
        return readIntInRange("", min, max);
    }
}
