package universitycgpa.model;

public class Student extends Person {
    public static final int MAX_COURSES = 10;  

    private String department;
    private String batch;
    private String serial;
    private Course[] registeredCourses = new Course[MAX_COURSES];
    private int courseCount = 0;
    private Result[] results = new Result[MAX_COURSES];
    private int resultCount = 0;

    
    public Student() {
        super();
        this.department = "N/A";
        this.batch = "00";
        this.serial = "00000";
    }


    public Student(String name, String id, String department) {
        super(name, id);
        this.department = department;
        // Short ID throws StringIndexOutOfBoundsException (caught in main)
        this.batch = id.substring(0, 2);
        this.serial = id.substring(2, 7);
    }

    // Copy constructor
    public Student(Student other) {
        this(other.getName(), other.getId(), other.department);
    }

    public String getDepartment() { return department; }
    public String getBatch() { return batch; }

    // Throws ArrayIndexOutOfBoundsException when the array is full
    public void addCourse(Course course) {
        registeredCourses[courseCount] = course;
        courseCount++;
    }

    public boolean hasCourse(String code) {
        for (int i = 0; i < courseCount; i++) {
            if (registeredCourses[i].getCourseCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public boolean dropCourse(String code) {
        for (int i = 0; i < courseCount; i++) {
            if (registeredCourses[i].getCourseCode().equalsIgnoreCase(code)) {
                for (int j = i; j < courseCount - 1; j++) {
                    registeredCourses[j] = registeredCourses[j + 1];
                }
                registeredCourses[courseCount - 1] = null;
                courseCount--;
                return true;
            }
        }
        return false;
    }

    public double getTotalCredit() {
        double sum = 0;   
        for (int i = 0; i < courseCount; i++) {
            sum += registeredCourses[i].getCredit();
        }
        return sum;
    }

    public Course getCourse(int index) { return registeredCourses[index]; }
    public int getCourseCount() { return courseCount; }

    public void addResult(Result result) {
        results[resultCount++] = result;
    }

    public boolean hasResult(String code) {
        for (int i = 0; i < resultCount; i++) {
            if (results[i].getCourse().getCourseCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public Result[] getResults() { return results; }
    public int getResultCount() { return resultCount; }

  
    @Override
    public void showInfo() {
        System.out.println("Name : " + getName());
        System.out.println("ID   : " + getId() + "   (Batch " + batch + ", Serial " + serial + ")");
        System.out.println("Dept : " + department);
        System.out.println("Registered credit: " + getTotalCredit());
    }
}