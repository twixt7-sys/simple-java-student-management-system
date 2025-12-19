package src.services;

import src.models.Course;
import src.repositories.CourseRepository;
import src.exceptions.InvalidInputException;
import src.utils.Validator;

import java.util.List;

// Handles business logic and validation for courses.
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService() {
        courseRepository = new CourseRepository();
    }

    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    public void createCourse(Course course) throws InvalidInputException {
        validateCourse(course);
        courseRepository.addCourse(course);
    }

    public void updateCourse(Course course) throws InvalidInputException {
        if (course.getId() <= 0) {
            throw new InvalidInputException("Invalid course ID");
        }
        validateCourse(course);
        courseRepository.updateCourse(course);
    }

    public void deleteCourse(int courseId) {
        courseRepository.deleteCourse(courseId);
    }

    private void validateCourse(Course course) throws InvalidInputException {
        if (Validator.isNullOrEmpty(course.getCourseCode())) {
            throw new InvalidInputException("Course code is required");
        }
        if (Validator.isNullOrEmpty(course.getCourseName())) {
            throw new InvalidInputException("Course name is required");
        }
        if (course.getUnits() <= 0) {
            throw new InvalidInputException("Units must be greater than zero");
        }
    }
}
