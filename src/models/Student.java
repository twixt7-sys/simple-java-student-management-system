package src.models;

// Represents a student entity in the system.
// Inherits common attributes from Person.
public class Student extends Person {

    private String studentNumber;
    private String program;

    public Student() {
    }

    public Student(int id, String firstName, String lastName, String email,
            String studentNumber, String program) {
        super(id, firstName, lastName, email);
        this.studentNumber = studentNumber;
        this.program = program;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
