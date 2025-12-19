# System Overview

The Student Management System is a desktop based application developed using Java, Swing, and MySQL. The system is designed to manage student records, course information, user access, and student enrollments in an organized and efficient manner. It allows authorized users to log in, manage students and courses, and enroll students into specific courses per semester and school year.

The system follows a layered architecture consisting of the user interface layer, service layer, repository layer, and model layer. This structure ensures proper separation of concerns, making the system easier to maintain, understand, and extend. Database operations are handled using JDBC, while the graphical user interface is implemented using Java Swing with CardLayout for navigation between screens.

A simple authentication mechanism is implemented to control access to the system. Users must log in before accessing the main features, and a logout option is provided to return to the login screen.

---

# OOP Principles Applied

## Encapsulation
Encapsulation is applied throughout the system by declaring class attributes as private and exposing them through public getter and setter methods. This ensures controlled access to data and protects the internal state of objects. Examples include the Student, Course, Enrollment, and UserAccount model classes.

## Inheritance
Inheritance is demonstrated through the Person class, which acts as a base class for Student. Common attributes such as first name, last name, and email are defined in the Person class and inherited by Student, reducing redundancy and improving code reuse.

## Abstraction
Abstraction is achieved through the use of service and repository layers. The user interface layer does not directly communicate with the database. Instead, it interacts with service classes that handle validation and business logic, which in turn delegate database operations to repository classes. This hides implementation details and simplifies the UI layer.

## Association and Composition
Association is clearly shown in the Enrollment class, which links Student and Course objects. This relationship represents real world enrollment where a student is associated with a course for a specific semester and school year. The Enrollment object is composed of Student and Course objects, reflecting a meaningful domain relationship.

---

# System Modules

## User Authentication Module
The system includes a simple login module that authenticates users before granting access to the main system. User credentials are stored in the database, and successful login redirects the user to the main application interface. A logout function allows users to securely return to the login screen.

## Student Management Module
This module allows users to add, update, delete, and view student records. Student information includes student number, name, email, and program. Records are displayed in a table and can be selected for editing.

## Course Management Module
The course management module enables users to manage course records, including course name and related details. Similar to the student module, courses can be added, updated, deleted, and viewed through a table based interface.

## Enrollment Management Module
The enrollment module allows students to be enrolled in specific courses for a given semester and school year. It manages the association between students and courses and displays enrollment records in a tabular format.

---

# Screenshots of System Forms

For documentation screenshots:
- Run the application
- Log in using a valid user account
- Click the Students button
- Take a screenshot of the StudentForm showing input fields, action buttons, and the student table
- Click the Courses button
- Take a screenshot of the CourseForm showing course input fields and controls
- Click the Enrollments button
- Take a screenshot of the EnrollmentForm showing enrollment details and the enrollment table

Label the screenshots as:
- Figure 1: Login Form
- Figure 2: Student Management Form
- Figure 3: Course Management Form
- Figure 4: Enrollment Management Form