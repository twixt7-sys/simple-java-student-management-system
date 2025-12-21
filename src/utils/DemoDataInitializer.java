package src.utils;

import src.models.Course;
import src.models.Student;
import src.models.UserAccount;
import src.services.CourseService;
import src.services.StudentService;
import src.services.UserAccountService;

public class DemoDataInitializer {

    public static void initializeDemoData() {
        try {
            UserAccountService userService = new UserAccountService();
            StudentService studentService = new StudentService();
            CourseService courseService = new CourseService();

            // Add demo user if none exist
            try {
                UserAccount admin = new UserAccount();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setRole("admin");
                userService.register(admin);
            } catch (Exception ex) {
                // User already exists
            }

            // Add demo students
            String[][] students = {
                {"S001", "John", "Smith", "john.smith@university.edu", "Computer Science"},
                {"S002", "Emily", "Johnson", "emily.johnson@university.edu", "Information Technology"},
                {"S003", "Michael", "Brown", "michael.brown@university.edu", "Software Engineering"},
                {"S004", "Sarah", "Davis", "sarah.davis@university.edu", "Data Science"},
                {"S005", "James", "Wilson", "james.wilson@university.edu", "Cybersecurity"},
                {"S006", "Jessica", "Martinez", "jessica.martinez@university.edu", "Web Development"},
                {"S007", "Robert", "Taylor", "robert.taylor@university.edu", "Computer Science"},
                {"S008", "Amanda", "Anderson", "amanda.anderson@university.edu", "Information Technology"}
            };

            for (String[] s : students) {
                try {
                    Student student = new Student();
                    student.setStudentNumber(s[0]);
                    student.setFirstName(s[1]);
                    student.setLastName(s[2]);
                    student.setEmail(s[3]);
                    student.setProgram(s[4]);
                    studentService.createStudent(student);
                } catch (Exception ex) {
                    // Student already exists
                }
            }

            // Add demo courses
            String[][] courses = {
                {"CS101", "Introduction to Computer Science", "3"},
                {"CS201", "Data Structures", "3"},
                {"CS301", "Algorithms", "4"},
                {"IT101", "Web Development Fundamentals", "3"},
                {"IT201", "Database Design", "3"},
                {"IT301", "Cloud Computing", "4"},
                {"SE101", "Software Engineering Principles", "3"},
                {"SE201", "Object-Oriented Design", "3"},
                {"DS101", "Data Science Basics", "4"},
                {"CY101", "Introduction to Cybersecurity", "3"}
            };

            for (String[] c : courses) {
                try {
                    Course course = new Course();
                    course.setCourseCode(c[0]);
                    course.setCourseName(c[1]);
                    course.setUnits(Integer.parseInt(c[2]));
                    courseService.createCourse(course);
                } catch (Exception ex) {
                    // Course already exists
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
