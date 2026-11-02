package universitycgpa.model;

public abstract class Course implements Printable {
    protected String courseCode;
    protected String title;
    protected double credit;

    public Course(String courseCode, String title, double credit) {
        this.courseCode = courseCode;
        this.title = title;
        this.credit = credit;
    }

    public abstract String getCourseType();

    public String getCourseCode(){ 
        return courseCode; 
    }
    public String getTitle(){
        return title; 
    }
    public double getCredit(){
        return credit; 
    }

    @Override
    public void printDetails() {
        System.out.printf("%-8s %-26s %-7s %4.1f%n",
                courseCode, title, getCourseType(), credit);
    }
}
