package src.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import src.models.Course;
import src.models.Enrollment;
import src.models.Student;

public class EnrollmentRepository {

    public void addEnrollment(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (student_id, course_id, semester, school_year) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, enrollment.getStudent().getId());
            stmt.setInt(2, enrollment.getCourse().getId());
            stmt.setString(3, enrollment.getSemester());
            stmt.setString(4, enrollment.getSchoolYear());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, enrollmentId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();

        String sql = """
            SELECT e.enrollment_id, e.semester, e.school_year,
                   s.student_id, s.first_name, s.last_name,
                   c.course_id, c.course_name
            FROM enrollments e
            JOIN students s ON e.student_id = s.student_id
            JOIN courses c ON e.course_id = c.course_id
        """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getInt("enrollment_id"));
                enrollment.setSemester(rs.getString("semester"));
                enrollment.setSchoolYear(rs.getString("school_year"));

                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                enrollment.setStudent(student);

                Course course = new Course();
                course.setId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                enrollment.setCourse(course);

                enrollments.add(enrollment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return enrollments;
    }
}
