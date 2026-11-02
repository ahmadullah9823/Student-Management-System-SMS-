package universitycgpa;

import universitycgpa.model.Course;
import universitycgpa.model.Result;
import universitycgpa.model.Student;
import universitycgpa.service.GradeCalculator;
import universitycgpa.service.RegistrationService;

import java.util.Scanner;

/**
 * CampusCGPA: Course Registration and CGPA Calculator
 * Console menu-driven program.
 */
public class UniversityCGPA {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        RegistrationService service = new RegistrationService();
        Student current = null;
        int choice;

        System.out.println("=== Welcome to CampusCGPA ===");

        do {                                   // do-while: menu runs at least once
            printMenu();
            choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        current = registerStudent();
                        break;
                    case 2:
                        service.showCourses();
                        break;
                    case 3:
                        System.out.print("Type (Theory/Lab): ");
                        service.showCourses(sc.nextLine().trim());
                        break;
                    case 4:
                        System.out.print("Course code: ");
                        System.out.println(service.addCourse(current, sc.nextLine().trim()));
                        break;
                    case 5:
                        System.out.print("Course code to drop: ");
                        System.out.println(service.dropCourse(current, sc.nextLine().trim()));
                        break;
                    case 6:
                        showRegistered(current);
                        break;
                    case 7:
                        System.out.println(service.confirmRegistration(current));
                        break;
                    case 8:
                        enterMarks(current);
                        break;
                    case 9:
                        printGradeSheet(current);
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                        continue;              // continue: go straight to the loop condition
                }
            } catch (NullPointerException e) {
                System.out.println("Error: Please register a student first (option 1).");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Maximum " + Student.MAX_COURSES + " courses allowed.");
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Error: ID must be exactly 7 digits (e.g. 2501012).");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. Register student");
        System.out.println("2. Show all courses");
        System.out.println("3. Show courses by type");
        System.out.println("4. Add course");
        System.out.println("5. Drop course");
        System.out.println("6. Show my registered courses");
        System.out.println("7. Confirm registration (credit check)");
        System.out.println("8. Enter marks");
        System.out.println("9. Print grade sheet");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------");
    }

    // Asks again if the input is not a number
    private static int readInt(String message) {
        int value = 0;
        boolean ok = false;
        while (!ok) {
            System.out.print(message);
            String line = sc.nextLine().trim();
            try {
                value = Integer.parseInt(line);
                ok = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number only.");
            }
        }
        return value;
    }

    private static Student registerStudent() {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("ID (7 digits): ");
        String id = sc.nextLine().trim();
        System.out.print("Department: ");
        String dept = sc.nextLine().trim();

        Student s = new Student(name, id, dept);
        System.out.println("Student registered successfully!");
        return s;
    }

    private static void showRegistered(Student s) {
        s.showInfo();                       // NullPointerException if s is null
        System.out.println("-------- Registered Courses --------");
        for (int i = 0; i < s.getCourseCount(); i++) {
            s.getCourse(i).printDetails();
        }
    }

    private static void enterMarks(Student s) {
        int semester = readInt("Semester number: ");
        int count = s.getCourseCount();     // NullPointerException if s is null
        if (count == 0) {
            System.out.println("No registered courses. Add courses first.");
            return;
        }
        for (int i = 0; i < count; i++) {
            Course c = s.getCourse(i);
            if (s.hasResult(c.getCourseCode())) {
                continue;                   // marks already entered
            }
            int marks;
            do {
                marks = readInt("Marks for " + c.getCourseCode() + " (0-100): ");
                if (!GradeCalculator.isValidMarks(marks)) {
                    System.out.println("Marks must be between 0 and 100.");
                }
            } while (!GradeCalculator.isValidMarks(marks));

            s.addResult(new Result(c, marks, semester));
        }
        System.out.println("Marks saved.");
    }

    private static void printGradeSheet(Student s) {
        int n = s.getResultCount();         // NullPointerException if s is null
        if (n == 0) {
            System.out.println("No results yet. Enter marks first (option 8).");
            return;
        }
        Result[] results = s.getResults();

        System.out.println("\n============== GRADE SHEET ==============");
        System.out.println("Name: " + s.getName() + "     ID: " + s.getId());
        System.out.println("Dept: " + s.getDepartment());
        printLine(60);
        System.out.printf("%-8s %-26s %4s  %5s  %-5s %s%n",
                "Code", "Course", "Cr", "Marks", "Grade", "GP");
        printLine(60);

        for (int i = 0; i < n; i++) {
            results[i].printDetails();
        }
        printLine(60);

        // GPA for each semester (nested loop: semester x result)
        for (int sem = 1; sem <= 12; sem++) {
            boolean found = false;
            for (int i = 0; i < n; i++) {
                if (results[i].getSemester() == sem) {
                    found = true;
                    break;                  // break: one match is enough
                }
            }
            if (found) {
                System.out.println("Semester " + sem + " GPA : "
                        + GradeCalculator.calculateGPA(results, n, sem));
            }
        }

        double cgpa = GradeCalculator.calculateGPA(results, n);
        String status = cgpa >= 2.0 ? "Pass" : "Fail";       // ternary operator
        System.out.println("CGPA         : " + cgpa + "   (" + status + ")");
        System.out.println("=========================================");
    }

    private static void printLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
