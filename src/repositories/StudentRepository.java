package src.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import src.models.Student;

// Handles all database operations related to students.
// Uses JDBC for CRUD operations.
public class StudentRepository {

    public void addStudent(Student student) {
        String sql = "INSERT INTO students (student_number, first_name, last_name, email, program) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStudentNumber());
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getProgram());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, email = ?, program = ? WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getProgram());
            stmt.setInt(5, student.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setProgram(rs.getString("program"));
                return student;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setProgram(rs.getString("program"));
                students.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }
}
