import java.util.ArrayList;
import java.util.List;

/**
 * Student.java
 *
 * Represents one student record: an ID, a name, and a list of Subjects.
 * All performance calculations (total, average, highest, lowest, grade)
 * live here because they operate purely on this student's own data -
 * a clean example of encapsulation.
 */
public class Student {

    private String id;
    private String name;
    private List<Subject> subjects;

    public Student(String id, String name) {
        setId(id);
        setName(name);
        this.subjects = new ArrayList<>();
    }

    // ---------- Getters / Setters (encapsulation) ----------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        this.id = id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        this.name = name.trim();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    // ---------- Performance calculations ----------

    public double getTotalMarks() {
        double total = 0;
        for (Subject s : subjects) {
            total += s.getMarks();
        }
        return total;
    }

    public double getAverageMarks() {
        if (subjects.isEmpty()) {
            return 0.0;
        }
        return getTotalMarks() / subjects.size();
    }

    public double getHighestMark() {
        if (subjects.isEmpty()) {
            return 0.0;
        }
        double highest = subjects.get(0).getMarks();
        for (Subject s : subjects) {
            if (s.getMarks() > highest) {
                highest = s.getMarks();
            }
        }
        return highest;
    }

    public double getLowestMark() {
        if (subjects.isEmpty()) {
            return 0.0;
        }
        double lowest = subjects.get(0).getMarks();
        for (Subject s : subjects) {
            if (s.getMarks() < lowest) {
                lowest = s.getMarks();
            }
        }
        return lowest;
    }

    /**
     * Grading scale (based on the student's average across all subjects):
     * 90-100 -> A+   80-89 -> A   70-79 -> B+   60-69 -> B
     * 50-59  -> C    40-49 -> D   below 40 -> F
     */
    public String getGrade() {
        double avg = getAverageMarks();
        if (avg >= 90) return "A+";
        if (avg >= 80) return "A";
        if (avg >= 70) return "B+";
        if (avg >= 60) return "B";
        if (avg >= 50) return "C";
        if (avg >= 40) return "D";
        return "F";
    }

    /**
     * Prints a full, formatted breakdown of this student: every subject,
     * then total/average/highest/lowest/grade. Used by "View All Students".
     */
    public void displayDetails() {
        System.out.println("------------------------------------------------------");
        System.out.printf("Student ID   : %s%n", id);
        System.out.printf("Student Name : %s%n", name);
        System.out.println("Subjects & Marks:");
        if (subjects.isEmpty()) {
            System.out.println("   (no subjects recorded)");
        } else {
            for (Subject s : subjects) {
                System.out.println("   " + s);
            }
        }
        System.out.println("------------------------------------------------------");
        System.out.printf("Total Marks  : %.2f%n", getTotalMarks());
        System.out.printf("Average      : %.2f%n", getAverageMarks());
        System.out.printf("Highest      : %.2f%n", getHighestMark());
        System.out.printf("Lowest       : %.2f%n", getLowestMark());
        System.out.printf("Grade        : %s%n", getGrade());
        System.out.println("------------------------------------------------------");
    }

    /** Short one-line form used in search results and lists. */
    @Override
    public String toString() {
        return String.format("[%s] %-15s | Avg: %6.2f | Grade: %s",
                id, name, getAverageMarks(), getGrade());
    }
}
