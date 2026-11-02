package universitycgpa.service;

import universitycgpa.model.Course;
import universitycgpa.model.LabCourse;
import universitycgpa.model.Student;
import universitycgpa.model.TheoryCourse;

public class RegistrationService {
    public static final double MIN_CREDIT = 9.0;
    public static final double MAX_CREDIT = 18.0;

    private Course[] catalog = new Course[9];

    public RegistrationService() {
        // Polymorphism: Course array holds both Theory and Lab courses
        catalog[0] = new TheoryCourse("CSE101", "Intro to Computing", 3.0);
        catalog[1] = new TheoryCourse("CSE102", "Java Programming", 3.0);
        catalog[2] = new LabCourse("CSE103", "Java Programming Lab", 1.5);
        catalog[3] = new TheoryCourse("MAT101", "Calculus I", 3.0);
        catalog[4] = new TheoryCourse("PHY101", "Physics I", 3.0);
        catalog[5] = new LabCourse("PHY102", "Physics I Lab", 1.5);
        catalog[6] = new TheoryCourse("ENG101", "English Language", 3.0);
        catalog[7] = new TheoryCourse("CSE104", "Discrete Mathematics", 3.0);
        catalog[8] = new LabCourse("CSE105", "Digital Logic Lab", 1.5);
    }

    public void showCourses() {
        printHeader();
        for (Course c : catalog) {         
            c.printDetails();
        }
    }

    public void showCourses(String type) {
        printHeader();
        for (Course c : catalog) {
            if (c.getCourseType().equalsIgnoreCase(type)) {
                c.printDetails();
            }
        }
    }

    private void printHeader() {
        System.out.printf("%-8s %-26s %-7s %s%n", "Code", "Title", "Type", "Credit");
        System.out.println("----------------------------------------------------");
    }

    public Course findCourse(String code) {
        for (Course c : catalog) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

    public String addCourse(Student student, String code) {
        Course course = findCourse(code);
        if (course == null) {
            return "Course not found: " + code;
        }
        if (student.hasCourse(code)) {
            return "Already registered: " + code;
        }
        if (student.getTotalCredit() + course.getCredit() > MAX_CREDIT) {
            return "Credit limit exceeded! Maximum is " + MAX_CREDIT;
        }
        student.addCourse(course);
        return "Course added: " + course.getTitle()
                + " (Total credit: " + student.getTotalCredit() + ")";
    }

    public String dropCourse(Student student, String code) {
        boolean dropped = student.dropCourse(code);
        return dropped ? "Course dropped: " + code : "You have not registered: " + code;
    }

    public String confirmRegistration(Student student) {
        double credit = student.getTotalCredit();
        if (credit < MIN_CREDIT) {
            return "Not enough credit! Minimum " + MIN_CREDIT + ", you have " + credit;
        }
        return "Registration confirmed with " + credit + " credits.";
    }
}
