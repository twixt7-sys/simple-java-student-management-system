package src.services;

import src.models.Enrollment;
import src.repositories.EnrollmentRepository;
import src.exceptions.InvalidInputException;
import src.utils.Validator;

import java.util.List;

public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService() {
        enrollmentRepository = new EnrollmentRepository();
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.getAllEnrollments();
    }

    public void enrollStudent(Enrollment enrollment) throws InvalidInputException {
        validateEnrollment(enrollment);
        enrollmentRepository.addEnrollment(enrollment);
    }

    public void removeEnrollment(int enrollmentId) {
        enrollmentRepository.deleteEnrollment(enrollmentId);
    }

    private void validateEnrollment(Enrollment enrollment) throws InvalidInputException {
        if (enrollment.getStudent() == null || enrollment.getStudent().getId() <= 0) {
            throw new InvalidInputException("Valid student ID is required");
        }

        if (enrollment.getCourse() == null || enrollment.getCourse().getId() <= 0) {
            throw new InvalidInputException("Valid course ID is required");
        }

        if (Validator.isNullOrEmpty(enrollment.getSemester())) {
            throw new InvalidInputException("Semester is required");
        }

        if (Validator.isNullOrEmpty(enrollment.getSchoolYear())) {
            throw new InvalidInputException("School year is required");
        }
    }
}
