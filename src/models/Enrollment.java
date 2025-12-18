package src.models;

public class Enrollment {

    private int id;
    private Student student;
    private Course course;
    private String semester;
    private String schoolYear;

    public Enrollment() {
    }

    public Enrollment(int id, Student student, Course course,
            String semester, String schoolYear) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.schoolYear = schoolYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
}
