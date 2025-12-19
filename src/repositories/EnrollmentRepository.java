package src.repositories;

import java.util.List;
import src.models.Enrollment;
import java.sql.Connection;
import java.sql.PreparedStatement;

import src.models.Enrollment;

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
    }

    public List<Enrollment> getEnrollmentsByStudent(int studentId) {
        return null;
    }
}
