INSERT INTO user_accounts (username, password, role)
VALUES ('admin', 'admin123', 'ADMIN');

INSERT INTO students (student_number, first_name, last_name, email, program)
VALUES 
('2023-001', 'Juan', 'Dela Cruz', 'juan@email.com', 'BSIT'),
('2023-002', 'Maria', 'Santos', 'maria@email.com', 'BSIT');

INSERT INTO courses (course_code, course_name, units)
VALUES 
('IT101', 'Introduction to Computing', 3),
('IT102', 'Object Oriented Programming', 3);

INSERT INTO enrollments (student_id, course_id, semester, school_year)
VALUES
(1, 1, '1st Semester', '2024-2025'),
(1, 2, '1st Semester', '2024-2025'),
(2, 1, '1st Semester', '2024-2025');
