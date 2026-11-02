package universitycgpa.model;


public class LabCourse extends Course {

    public LabCourse(String courseCode, String title, double credit) {
        super(courseCode, title, credit);
    }

    @Override
    public String getCourseType() {
        return "Lab";
    }
}
