package src.utils;

import src.repositories.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DemoDataInitializer {

    public static void initializeDemoData() {
        String upsertStudentSql = "INSERT INTO students (student_number, first_name, last_name, email, program) VALUES (?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE first_name = VALUES(first_name), last_name = VALUES(last_name), email = VALUES(email), program = VALUES(program)";
        String selectStudentIdSql = "SELECT student_id FROM students WHERE student_number = ?";

        String upsertCourseSql = "INSERT INTO courses (course_code, course_name, units) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE course_name = VALUES(course_name), units = VALUES(units)";
        String selectCourseIdSql = "SELECT course_id FROM courses WHERE course_code = ?";

        String insertEnrollmentSql = "INSERT IGNORE INTO enrollments (student_id, course_id, semester, school_year) VALUES (?, ?, ?, ?)";

        // Demo data
        String[][] students = {
                {"2023-001", "Juan", "Dela Cruz", "juan@email.com", "BSIT"},
                {"2023-002", "Maria", "Santos", "maria@email.com", "BSIT"},
                {"2023-003", "Pedro", "Gonzales", "pedro@email.com", "BSCS"}
        };

        String[][] courses = {
                {"IT101", "Introduction to Computing", "3"},
                {"IT102", "Object Oriented Programming", "3"},
                {"IT201", "Data Structures", "3"}
        };

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psUpStudent = conn.prepareStatement(upsertStudentSql);
                 PreparedStatement psSelectStudent = conn.prepareStatement(selectStudentIdSql);
                 PreparedStatement psUpCourse = conn.prepareStatement(upsertCourseSql);
                 PreparedStatement psSelectCourse = conn.prepareStatement(selectCourseIdSql);
                 PreparedStatement psInsertEnroll = conn.prepareStatement(insertEnrollmentSql)) {

                // Upsert students
                for (String[] s : students) {
                    psUpStudent.setString(1, s[0]); // student_number
                    psUpStudent.setString(2, s[1]); // first_name
                    psUpStudent.setString(3, s[2]); // last_name
                    psUpStudent.setString(4, s[3]); // email
                    psUpStudent.setString(5, s[4]); // program
                    psUpStudent.executeUpdate();
                }

                // Upsert courses
                for (String[] c : courses) {
                    psUpCourse.setString(1, c[0]); // course_code
                    psUpCourse.setString(2, c[1]); // course_name
                    psUpCourse.setInt(3, Integer.parseInt(c[2])); // units
                    psUpCourse.executeUpdate();
                }

                // Lookup ids
                int s1 = getIdForStudent(psSelectStudent, "2023-001");
                int s2 = getIdForStudent(psSelectStudent, "2023-002");
                int s3 = getIdForStudent(psSelectStudent, "2023-003");

                int c1 = getIdForCourse(psSelectCourse, "IT101");
                int c2 = getIdForCourse(psSelectCourse, "IT102");
                int c3 = getIdForCourse(psSelectCourse, "IT201");

                // Insert enrollments (use INSERT IGNORE so duplicates are harmless)
                psInsertEnroll.setInt(1, s1); psInsertEnroll.setInt(2, c1); psInsertEnroll.setString(3, "1st Semester"); psInsertEnroll.setString(4, "2024-2025"); psInsertEnroll.executeUpdate();
                psInsertEnroll.setInt(1, s1); psInsertEnroll.setInt(2, c2); psInsertEnroll.setString(3, "1st Semester"); psInsertEnroll.setString(4, "2024-2025"); psInsertEnroll.executeUpdate();
                psInsertEnroll.setInt(1, s2); psInsertEnroll.setInt(2, c1); psInsertEnroll.setString(3, "1st Semester"); psInsertEnroll.setString(4, "2024-2025"); psInsertEnroll.executeUpdate();
                psInsertEnroll.setInt(1, s3); psInsertEnroll.setInt(2, c1); psInsertEnroll.setString(3, "1st Semester"); psInsertEnroll.setString(4, "2024-2025"); psInsertEnroll.executeUpdate();
                psInsertEnroll.setInt(1, s3); psInsertEnroll.setInt(2, c3); psInsertEnroll.setString(3, "1st Semester"); psInsertEnroll.setString(4, "2024-2025"); psInsertEnroll.executeUpdate();

                conn.commit();
                System.out.println("Demo data initialized successfully.");
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Failed to initialize demo data, rolled back. Error: " + ex.getMessage());
            }
        } catch (SQLException connEx) {
            System.err.println("Database error during demo initialization: " + connEx.getMessage());
        }
    }

    private static int getIdForStudent(PreparedStatement psSelectStudent, String studentNumber) throws SQLException {
        psSelectStudent.setString(1, studentNumber);
        try (ResultSet rs = psSelectStudent.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("student_id");
            } else {
                throw new SQLException("Student not found: " + studentNumber);
            }
        }
    }

    private static int getIdForCourse(PreparedStatement psSelectCourse, String courseCode) throws SQLException {
        psSelectCourse.setString(1, courseCode);
        try (ResultSet rs = psSelectCourse.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("course_id");
            } else {
                throw new SQLException("Course not found: " + courseCode);
            }
        }
    }
}
