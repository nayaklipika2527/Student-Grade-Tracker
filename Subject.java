/**
 * Subject.java
 *
 * Represents a single subject and the marks a student scored in it.
 * Encapsulates validation so that an invalid Subject object can never exist:
 * the name cannot be empty and marks must be within the 0-100 range.
 */
public class Subject {

    private String name;
    private double marks;

    public Subject(String name, double marks) {
        setName(name);
        setMarks(marks);
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setMarks(double marks) {
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        }
        this.marks = marks;
    }

    @Override
    public String toString() {
        return String.format("%-20s : %6.2f", name, marks);
    }
}
