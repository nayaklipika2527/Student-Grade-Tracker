import java.util.ArrayList;
import java.util.List;

/**
 * GradeTracker.java
 *
 * The core "manager" class. Holds the ArrayList of Student objects and
 * implements every CodeAlpha requirement that isn't specific to a single
 * student: adding, searching, updating, deleting, class-wide performance,
 * and the summary report. Main.java only talks to this class - it never
 * touches the ArrayList directly, which keeps the data encapsulated.
 */
public class GradeTracker {

    private List<Student> students;

    public GradeTracker() {
        this.students = new ArrayList<>();
    }

    // ---------- Add ----------

    public void addStudent(Student student) throws DuplicateStudentException {
        if (isDuplicateId(student.getId())) {
            throw new DuplicateStudentException(
                    "A student with ID '" + student.getId() + "' already exists.");
        }
        students.add(student);
    }

    public boolean isDuplicateId(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id.trim())) {
                return true;
            }
        }
        return false;
    }

    // ---------- Read ----------

    public List<Student> getAllStudents() {
        return students;
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

    public Student findById(String id) throws StudentNotFoundException {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id.trim())) {
                return s;
            }
        }
        throw new StudentNotFoundException("No student found with ID '" + id + "'.");
    }

    /** Searches by exact ID first, then falls back to a case-insensitive name match. */
    public List<Student> search(String query) {
        List<Student> results = new ArrayList<>();
        String q = query.trim().toLowerCase();
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(query.trim()) || s.getName().toLowerCase().contains(q)) {
                results.add(s);
            }
        }
        return results;
    }

    // ---------- Update ----------

    public void updateStudentMarks(String id, List<Subject> newSubjects) throws StudentNotFoundException {
        Student student = findById(id); // throws if not found
        student.getSubjects().clear();
        for (Subject sub : newSubjects) {
            student.addSubject(sub);
        }
    }

    // ---------- Delete ----------

    public void deleteStudent(String id) throws StudentNotFoundException {
        Student student = findById(id); // throws if not found
        students.remove(student);
    }

    // ---------- View All ----------

    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students have been added yet.");
            return;
        }
        for (Student s : students) {
            s.displayDetails();
        }
    }

    // ---------- Class Performance ----------

    public void showClassPerformance() {
        if (students.isEmpty()) {
            System.out.println("No students have been added yet.");
            return;
        }

        Student topStudent = students.get(0);
        Student bottomStudent = students.get(0);
        double classTotal = 0;
        double highestMarkInClass = Double.NEGATIVE_INFINITY;
        double lowestMarkInClass = Double.MAX_VALUE;
        boolean anySubjectFound = false;

        for (Student s : students) {
            double avg = s.getAverageMarks();
            if (avg > topStudent.getAverageMarks()) {
                topStudent = s;
            }
            if (avg < bottomStudent.getAverageMarks()) {
                bottomStudent = s;
            }
            classTotal += avg;

            for (Subject sub : s.getSubjects()) {
                anySubjectFound = true;
                if (sub.getMarks() > highestMarkInClass) {
                    highestMarkInClass = sub.getMarks();
                }
                if (sub.getMarks() < lowestMarkInClass) {
                    lowestMarkInClass = sub.getMarks();
                }
            }
        }

        double classAverage = classTotal / students.size();

        System.out.println("========================================");
        System.out.println("           CLASS PERFORMANCE           ");
        System.out.println("========================================");
        System.out.printf("Student with Highest Average : %s (%.2f)%n",
                topStudent.getName(), topStudent.getAverageMarks());
        System.out.printf("Student with Lowest Average  : %s (%.2f)%n",
                bottomStudent.getName(), bottomStudent.getAverageMarks());
        if (anySubjectFound) {
            System.out.printf("Highest Mark in the Class    : %.2f%n", highestMarkInClass);
            System.out.printf("Lowest Mark in the Class     : %.2f%n", lowestMarkInClass);
        } else {
            System.out.println("Highest Mark in the Class    : N/A (no marks recorded)");
            System.out.println("Lowest Mark in the Class     : N/A (no marks recorded)");
        }
        System.out.printf("Overall Class Average         : %.2f%n", classAverage);
        System.out.println("========================================");
    }

    // ---------- Summary Report ----------

    public void printSummaryReport() {
        if (students.isEmpty()) {
            System.out.println("No students have been added yet.");
            return;
        }

        System.out.println("========================================================");
        System.out.println("                  SUMMARY REPORT                       ");
        System.out.println("========================================================");
        System.out.printf("%-8s %-15s %-8s %-8s %-8s %-8s %-6s%n",
                "ID", "Name", "Total", "Average", "Highest", "Lowest", "Grade");
        System.out.println("--------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-8s %-15s %-8.2f %-8.2f %-8.2f %-8.2f %-6s%n",
                    s.getId(), s.getName(), s.getTotalMarks(), s.getAverageMarks(),
                    s.getHighestMark(), s.getLowestMark(), s.getGrade());
        }
        System.out.println("========================================================");
        System.out.println("Total Students Registered: " + students.size());
        System.out.println("========================================================");
    }
}
