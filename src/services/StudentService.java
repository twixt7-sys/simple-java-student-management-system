package src.services;

import src.repositories.StudentRepository;
import src.utils.Validator;

import java.util.List;

import src.exceptions.InvalidInputException;

import src.models.Student;

// Contains business logic and validation for student operations.
// Acts as a bridge between UI and repository.
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public StudentService() {
        this.studentRepository = new StudentRepository();
    }

    public void createStudent(Student student) throws InvalidInputException {
        validateStudent(student);
        studentRepository.addStudent(student);
    }

    public void updateStudent(Student student) throws InvalidInputException {
        validateStudent(student);
        studentRepository.updateStudent(student);
    }

    public void deleteStudent(int studentId) {
        studentRepository.deleteStudent(studentId);
    }

    private void validateStudent(Student student) throws InvalidInputException {
        if (Validator.isNullOrEmpty(student.getStudentNumber())) {
            throw new InvalidInputException("Student number is required");
        }
        if (Validator.isNullOrEmpty(student.getFirstName())) {
            throw new InvalidInputException("First name is required");
        }
        if (Validator.isNullOrEmpty(student.getLastName())) {
            throw new InvalidInputException("Last name is required");
        }
        if (!Validator.isValidEmail(student.getEmail())) {
            throw new InvalidInputException("Invalid email format");
        }
    }

}
