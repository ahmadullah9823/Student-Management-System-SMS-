package universitycgpa.model;


public class TheoryCourse extends Course {

    public TheoryCourse(String courseCode, String title, double credit) {
        super(courseCode, title, credit);  
    }

   
    public TheoryCourse(TheoryCourse other) {
        super(other.courseCode, other.title, other.credit);
    }

    @Override
    public String getCourseType() {
        return "Theory";
    }
}
