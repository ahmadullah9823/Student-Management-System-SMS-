package universitycgpa.service;

import universitycgpa.model.Result;

public class GradeCalculator {

    public static boolean isValidMarks(int marks) {
        return marks >= 0 && marks <= 100;  
    }


    public static String getGradeLetter(int marks) {
        if (marks >= 80) {
            return "A+";
        } else if (marks >= 75) {
            return "A";
        } else if (marks >= 70) {
            return "A-";
        } else if (marks >= 65) {
            return "B+";
        } else if (marks >= 60) {
            return "B";
        } else if (marks >= 55) {
            return "B-";
        } else if (marks >= 50) {
            return "C+";
        } else if (marks >= 45) {
            return "C";
        } else if (marks >= 40) {
            return "D";
        } else {
            return "F";
        }
    }

    public static double getGradePoint(int marks) {
        if (marks >= 80) return 4.00;
        else if (marks >= 75) return 3.75;
        else if (marks >= 70) return 3.50;
        else if (marks >= 65) return 3.25;
        else if (marks >= 60) return 3.00;
        else if (marks >= 55) return 2.75;
        else if (marks >= 50) return 2.50;
        else if (marks >= 45) return 2.25;
        else if (marks >= 40) return 2.00;
        else return 0.00;
    }


    public static String getRemark(String grade) {
        String remark;
        switch (grade) {
            case "A+":
                remark = "Outstanding";
                break;
            case "A":
            case "A-":
                remark = "Excellent";
                break;
            case "B+":
            case "B":
            case "B-":
                remark = "Good";
                break;
            case "C+":
            case "C":
                remark = "Average";
                break;
            case "D":
                remark = "Poor";
                break;
            default:
                remark = "Fail";
        }
        return remark;
    }

 
    public static double calculateGPA(Result[] results, int count) {
        double totalPoints = 0;
        double totalCredit = 0;
        for (int i = 0; i < count; i++) {
            double credit = results[i].getCourse().getCredit();
            totalPoints += results[i].getGradePoint() * credit;
            totalCredit += credit;
        }
        if (totalCredit == 0) {
            return 0.0;
        }
        return roundTo2(totalPoints / totalCredit);
    }


    public static double calculateGPA(Result[] results, int count, int semester) {
        double totalPoints = 0;
        double totalCredit = 0;
        for (int i = 0; i < count; i++) {
            if (results[i].getSemester() != semester) {
                continue;  
            }
            double credit = results[i].getCourse().getCredit();
            totalPoints += results[i].getGradePoint() * credit;
            totalCredit += credit;
        }
        if (totalCredit == 0) {
            return 0.0;
        }
        return roundTo2(totalPoints / totalCredit);
    }

  
    public static double roundTo2(double value) {
        int scaled = (int) (value * 100 + 0.5);
        return scaled / 100.0;  
    }
}
