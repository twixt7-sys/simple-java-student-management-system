package src.services;

import src.models.Enrollment;
import src.repositories.EnrollmentRepository;
import src.exceptions.InvalidInputException;
import src.utils.Validator;

public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService() {
        enrollmentRepository = new EnrollmentRepository();
    }

    public void enrollStudent(Enrollment enrollment) throws InvalidInputException {
        if (enrollment.getStudent() == null || enrollment.getCourse() == null) {
            throw new InvalidInputException("Student and Course are required");
        }
        if (Validator.isNullOrEmpty(enrollment.getSemester())) {
            throw new InvalidInputException("Semester is required");
        }
        enrollmentRepository.addEnrollment(enrollment);
    }

    public void removeEnrollment(int enrollmentId) {
    }
}
