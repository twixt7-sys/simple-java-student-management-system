# System Overview
The Student Management System is a desktop-based application developed using Java and MySQL. The system is designed to manage student records, course information, and student enrollments in an organized and efficient manner. It allows an administrator to add, update, and view students and courses, as well as enroll students into specific courses per semester.

The system follows a layered architecture consisting of a user interface layer, service layer, repository layer, and model layer. This structure ensures separation of concerns, making the system easier to maintain, understand, and extend. The application uses JDBC to connect to a MySQL database and Swing for the graphical user interface.

---

# OOP Principles Applied

## Encapsulation
Encapsulation is applied by declaring class attributes as private and providing public getter and setter methods. This ensures controlled access to data and protects the integrity of objects.

## Inheritance
Inheritance is demonstrated through the Person class, which serves as a base class for Student. Common attributes such as name and email are inherited by the Student class, reducing code duplication.

## Abstraction
Abstraction is implemented through the use of service and repository layers. The user interface does not directly interact with the database; instead, it communicates with services that abstract the underlying implementation details.

## Association and Composition
Association is shown in the Enrollment class, which links Student and Course objects. This relationship represents real-world enrollment where a student is associated with a course during a specific term.

---

Screenshots of Student and Course Forms

For documentation screenshots:
Run the application
Click the Students button
Take a screenshot of the StudentForm showing input fields and the Save button
Click the Courses button
Take a screenshot of the CourseForm showing course inputs

Label them as:
Figure 1: Student Management Form
Figure 2: Course Management Form