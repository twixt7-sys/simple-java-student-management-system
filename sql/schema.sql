-- 1) DROP tables if they exist (safe to run multiple times)
SET @OLD_FK_CHECKS = @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS user_accounts;

SET FOREIGN_KEY_CHECKS = @OLD_FK_CHECKS;

-- 2) CREATE tables (MySQL/MariaDB-compatible)
CREATE TABLE user_accounts (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','USER') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE students (
    student_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_number VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    program VARCHAR(100),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Changed column names to match Java code: course_code, course_name
CREATE TABLE courses (
    course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(50) NOT NULL UNIQUE,
    course_name VARCHAR(255) NOT NULL,
    units INT NOT NULL DEFAULT 3
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE enrollments (
    enrollment_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    semester VARCHAR(50) NOT NULL,
    school_year VARCHAR(20) NOT NULL,
    enrolled_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_enrollment (student_id, course_id, semester, school_year),
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3) Insert test data (use columns expected by code)
INSERT INTO user_accounts (username, password, role)
VALUES ('admin', 'admin123', 'ADMIN');

INSERT INTO students (student_number, first_name, last_name, email, program)
VALUES 
('2023-001', 'Juan', 'Dela Cruz', 'juan@email.com', 'BSIT'),
('2023-002', 'Maria', 'Santos', 'maria@email.com', 'BSIT'),
('2023-003', 'Pedro', 'Gonzales', 'pedro@email.com', 'BSCS');

-- Use course_code and course_name here to match repository queries
INSERT INTO courses (course_code, course_name, units)
VALUES 
('IT101', 'Introduction to Computing', 3),
('IT102', 'Object Oriented Programming', 3),
('IT201', 'Data Structures', 3);

-- enrollments: reference the auto-generated student_id/course_id values
INSERT INTO enrollments (student_id, course_id, semester, school_year)
VALUES
(1, 1, '1st Semester', '2024-2025'),
(1, 2, '1st Semester', '2024-2025'),
(2, 1, '1st Semester', '2024-2025'),
(3, 1, '1st Semester', '2024-2025'),
(3, 3, '1st Semester', '2024-2025');

-- 4) Useful JOIN queries (examples to validate schema and sample data)
-- a) List all students with their enrolled courses
SELECT s.student_id, s.student_number, CONCAT(s.first_name, ' ', s.last_name) AS student_name,
       c.course_id, c.course_code AS course_code, c.course_name AS course_name, e.semester, e.school_year, e.enrollment_id
FROM students s
JOIN enrollments e ON s.student_id = e.student_id
JOIN courses c ON c.course_id = e.course_id
ORDER BY s.student_number, c.course_code;

-- b) Course roster: students per course
SELECT c.course_id, c.course_code AS course_code, c.course_name AS course_name,
       s.student_id, s.student_number, CONCAT(s.first_name, ' ', s.last_name) AS student_name
FROM courses c
LEFT JOIN enrollments e ON c.course_id = e.course_id
LEFT JOIN students s ON e.student_id = s.student_id
ORDER BY c.course_code, s.last_name;

-- c) Count of students per course
SELECT c.course_id, c.course_code AS course_code, c.course_name AS course_name, COUNT(e.student_id) AS enrolled_students
FROM courses c
LEFT JOIN enrollments e ON c.course_id = e.course_id
GROUP BY c.course_id, c.course_code, c.course_name
ORDER BY enrolled_students DESC;

-- d) Transcript-like view for a specific student number (replace '2023-001' as needed)
SELECT s.student_id, s.student_number, CONCAT(s.first_name, ' ', s.last_name) AS student_name,
       c.course_id, c.course_code AS course_code, c.course_name AS course_name, c.units, e.semester, e.school_year
FROM students s
JOIN enrollments e ON s.student_id = e.student_id
JOIN courses c ON e.course_id = c.course_id
WHERE s.student_number = '2023-001'
ORDER BY e.school_year, e.semester, c.course_code;
