package src.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.models.Course;

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
    }

    public void deleteCourse(int courseId) {
    }

    public Course getCourseById(int courseId) {
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
