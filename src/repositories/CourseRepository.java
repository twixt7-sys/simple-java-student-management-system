package src.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import src.models.Course;

// Handles all database operations related to courses.
// Uses JDBC for CRUD operations.
public class CourseRepository {

    public void addCourse(Course course) {
        String sql = "INSERT INTO courses (course_code, course_name, units) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getUnits());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(Course course) {
        String sql = "UPDATE courses SET course_code = ?, course_name = ?, units = ? WHERE course_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getUnits());
            stmt.setInt(4, course.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("course_id"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setUnits(rs.getInt("units"));
                return course;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("course_id"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setUnits(rs.getInt("units"));
                courses.add(course);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }
}
