/**
 * DuplicateStudentException.java
 *
 * Thrown when an attempt is made to add a student whose ID already exists
 * in the tracker. A checked exception is used deliberately so that the
 * caller is forced to handle the duplicate-ID scenario explicitly.
 */
public class DuplicateStudentException extends Exception {
    public DuplicateStudentException(String message) {
        super(message);
    }
}
