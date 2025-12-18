package src.models;

public class Course {

    private int id;
    private String courseCode;
    private String courseName;
    private int units;

    public Course() {
    }

    public Course(int id, String courseCode, String courseName, int units) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
