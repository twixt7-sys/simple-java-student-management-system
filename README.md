# Student Management System 🎓

A modern, feature-rich desktop application for managing students, courses, and enrollments built with **pure Java Swing** (no external frameworks).

## ✨ Features

### Modern UI/UX
- **Dark Theme**: Professional dark interface with smooth color transitions
- **Rounded Components**: Custom rounded buttons and text fields for a polished look
- **Responsive Design**: Adaptive layout that works across different window sizes
- **Real-time Validation**: Input validation with helpful error messages displayed inline

### Core Functionality
- **Student Management**: Add, edit, delete, and search students
- **Course Management**: Create and manage courses with credit values
- **Enrollment Management**: Enroll students in courses with grade tracking
- **User Authentication**: Secure login system with demo credentials

### Demo Data
The system comes pre-loaded with demo data:
- **User Account**: `admin` / `admin123`
- **Sample Students**: 8 diverse students across different programs
- **Sample Courses**: 10 courses spanning Computer Science, IT, SE, and Data Science
- Ready-to-use data for immediate testing

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher

### Running the Application

1. **Compile the project**:
   ```bash
   javac -d . src/**/*.java Main.java
   ```

2. **Run the application**:
   ```bash
   java Main
   ```

3. **Login with demo credentials**:
   - Username: `admin`
   - Password: `admin123`

## 🎨 Design Highlights

### Custom Components
- **RoundedButton**: Beautiful gradient buttons with hover effects
- **RoundedTextField**: Elegant text input fields with focus feedback
- **ShadowPanel**: Depth and dimension through shadow effects
- **Theme System**: Centralized color management for consistency

### Color Palette
- **Primary**: Accent Blue (#3B82F6)
- **Background**: Dark Gray (#101218)
- **Surface**: Darker Gray (#1C1F28)
- **Text**: Light Gray (#E6E6E6)
- **Success**: Green (#22C55E)
- **Error**: Red (#EF4444)

### Navigation
- Intuitive tabbed interface with emoji icons
- Quick switching between Students, Courses, and Enrollments
- Logout functionality for security

## 📋 How to Use

### Managing Students
1. Click **👥 Students** tab
2. Fill in student information (Number, Name, Email, Program)
3. Click **Add New** to save, **Update** to modify, **Delete** to remove
4. Select from the table to edit existing students

### Managing Courses
1. Click **📚 Courses** tab
2. Enter course code, name, and credit value
3. Use buttons to add, update, or remove courses
4. Table shows all available courses

### Managing Enrollments
1. Click **📋 Enrollments** tab
2. Enter Student ID and Course ID
3. Optionally add a grade
4. Click **Enroll** to create the enrollment

## 🏗️ Architecture

```
src/
├── models/           # Data models (Student, Course, Enrollment, UserAccount)
├── repositories/     # Database access layer
├── services/         # Business logic layer
├── ui/               # User interface components
│   ├── components/   # Reusable UI widgets (RoundedButton, Theme, etc.)
│   └── forms/        # Form panels (StudentForm, CourseForm, etc.)
├── exceptions/       # Custom exceptions
└── utils/            # Utility classes (Validator, DemoDataInitializer)
```

## 📦 What's Inside

### UI Components
- **LoginForm.java**: Polished login screen
- **MainFrame.java**: Application shell with navigation
- **StudentForm.java**: Student CRUD interface
- **CourseForm.java**: Course management
- **EnrollmentForm.java**: Enrollment management

### Supporting Classes
- **Theme.java**: Centralized theme management
- **RoundedButton.java**: Custom button with rounded corners
- **RoundedTextField.java**: Custom text field with borders
- **DemoDataInitializer.java**: Auto-loads sample data
- **UIEffects.java**: Hover effects and styling utilities

## 🎯 Why This Project Stands Out

✅ **Pure Java** - No external dependencies, just Swing  
✅ **Professional Design** - Modern dark theme, custom components  
✅ **User-Friendly** - Intuitive navigation, helpful feedback  
✅ **Clean Code** - Well-organized, documented, maintainable  
✅ **Complete Feature Set** - Full CRUD operations + authentication  
✅ **Production Ready** - Input validation, error handling, demo data  

## 🔐 Security Note
This is a demo/educational application. The login uses plain text passwords. For production use, implement proper password hashing (bcrypt, scrypt) and consider using JDBC with parameterized queries.

## 📝 License
Educational use only

---

**Made with ❤️ for students and professors alike**
