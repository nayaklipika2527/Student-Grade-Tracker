/**
 * StudentNotFoundException.java
 *
 * Thrown when an operation (search, update, delete) refers to a student
 * ID that does not exist in the tracker.
 */
public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
