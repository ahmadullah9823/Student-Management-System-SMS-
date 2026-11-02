package universitycgpa.model;

import universitycgpa.service.GradeCalculator;


public class Result implements Printable, Gradable {
    private Course course;
    private int marks;
    private int semester;
    private String grade = "F";
    private double gradePoint = 0.0;

    public Result(Course course, int marks, int semester) {
        this.course = course;
        this.semester = semester;
        setMarks(marks);
    }

  
    public boolean setMarks(int marks) {
        if (marks < 0 || marks > 100) {
            return false;
        }
        this.marks = marks;
        this.grade = GradeCalculator.getGradeLetter(marks);
        this.gradePoint = GradeCalculator.getGradePoint(marks);
        return true;
    }

    public Course getCourse() { return course; }
    public int getMarks() { return marks; }
    public int getSemester() { return semester; }

    @Override
    public String getGrade() { return grade; }

    @Override
    public double getGradePoint() { return gradePoint; }

    @Override
    public void printDetails() {
        System.out.printf("%-8s %-26s %4.1f  %5d  %-5s %4.2f%n",
                course.getCourseCode(), course.getTitle(), course.getCredit(),
                marks, grade, gradePoint);
    }
}
