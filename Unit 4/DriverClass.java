import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DriverClass {
    private static College valenceCollege = new College();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            switch (mainMenu()) {
                case 1:
                    valenceCollege.enrollStudent();
                    break;
                case 2:
                    valenceCollege.manageCourse();
                    break;
                case 3:
                    valenceCollege.searchStudent();
                    break;
                case 4:
                    valenceCollege.printInvoice(false);
                    break;
                case 5:
                    valenceCollege.printInvoice(true);
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static int mainMenu() {
        System.out.println("Choose from the following options:");
        System.out.println("1- Add a new student");
        System.out.println("2- Add/Delete a course");
        System.out.println("3- Search for a student");
        System.out.println("4- Print fee invoice");
        System.out.println("5- Print fee invoice sorted by crn");
        System.out.println("0- Exit program");
        System.out.print("Enter your selection: ");
        int option = scanner.nextInt();
        return option;
    }
}

class Student {
    private int studentId;
    private String studentName;
    private double gpa;
    private ArrayList<Integer> listOfCrns = new ArrayList<>();
    private static final double CREDIT_HOUR_COST = 120.25;
    private static final double HEALTH_ID_FEES = 35.00;

    public Student(String studentName, int studentId, double gpa, ArrayList<Integer> listOfCrns) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.gpa = gpa;
        this.listOfCrns = listOfCrns;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public ArrayList<Integer> getListOfCrns() {
        return listOfCrns;
    }

    public void setListOfCrns(ArrayList<Integer> listOfCrns) {
        this.listOfCrns = listOfCrns;
    }

    private double calculateTotalPayment() {
        double totalPayment = listOfCrns.stream()
            .mapToDouble(crn -> College.courseDetails.get(crn) * CREDIT_HOUR_COST)
            .sum();
        totalPayment += HEALTH_ID_FEES;
        if (gpa >= 3.5 && totalPayment > 700) {
            totalPayment *= 0.75; // Applying 25% discount
        }
        return totalPayment;
    }

    public void printFeeInvoice() {
        System.out.println("VALENCE COLLEGE\nORLANDO FL 10101\n---------------------");
        System.out.printf("Fee Invoice Prepared for Student:\n%s-%s\n", studentId, studentName);
        System.out.println("1 Credit Hour = $" + CREDIT_HOUR_COST);
        System.out.println("CRN CR_PREFIX CR_HOURS");
        listOfCrns.forEach(crn -> {
            int creditHours = College.courseDetails.get(crn);
            System.out.printf("%d %s %d $%.2f\n", crn, College.coursePrefixes.get(crn), creditHours, creditHours * CREDIT_HOUR_COST);
        });
        System.out.printf("Health & id fees $%.2f\n", HEALTH_ID_FEES);
        System.out.println("--------------------------------------");
        System.out.printf("Total Payments $%.2f\n", calculateTotalPayment());
    }
}

class College {
    private ArrayList<Student> list = new ArrayList<>();
    public static Map<Integer, Integer> courseDetails = new HashMap<>();
    public static Map<Integer, String> coursePrefixes = new HashMap<>();

    static {
        courseDetails.put(4587, 4); coursePrefixes.put(4587, "MAT 236");
        courseDetails.put(4599, 3); coursePrefixes.put(4599, "COP 220");
        courseDetails.put(8997, 1); coursePrefixes.put(8997, "GOL 124");
        courseDetails.put(9696, 3); coursePrefixes.put(9696, "COP 100");
        courseDetails.put(4580, 1); coursePrefixes.put(4580, "MAT 136");
        courseDetails.put(2599, 3); coursePrefixes.put(2599, "COP 260");
        courseDetails.put(1997, 1); coursePrefixes.put(1997, "CAP 424");
        courseDetails.put(3696, 2); coursePrefixes.put(3696, "KOL 110");
    }

    public void enrollStudent() {
        System.out.print("Enter the student's id: ");
        int id = DriverClass.scanner.nextInt();
        if (searchById(id)) {
            System.out.println("Sorry, " + id + " is already assigned to another student");
            return;
        }
        DriverClass.scanner.nextLine(); // consume newline
        System.out.print("Enter student's name: ");
        String name = DriverClass.scanner.nextLine();
        System.out.print("Enter how many courses " + name + " is taking? ");
        int numCourses = DriverClass.scanner.nextInt();
        ArrayList<Integer> courses = new ArrayList<>();
        System.out.println("Enter the " + numCourses + " course numbers");
        for (int i = 0; i < numCourses; i++) {
            courses.add(DriverClass.scanner.nextInt());
        }
        System.out.print("Enter " + name + "'s current gpa: ");
        double gpa = DriverClass.scanner.nextDouble();
        list.add(new Student(name, id, gpa, courses));
        System.out.println(name + " added successfully!");
    }

    public boolean searchById(int studentId) {
        return list.stream().anyMatch(student -> student.getStudentId() == studentId);
    }

    public boolean addCourse(int studentId, int crn) {
        for (Student student : list) {
            if (student.getStudentId() == studentId) {
                if (!student.getListOfCrns().contains(crn)) {
                    student.getListOfCrns().add(crn);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteCourse(int studentId, int crn) {
        for (Student student : list) {
            if (student.getStudentId() == studentId) {
                return student.getListOfCrns().remove(Integer.valueOf(crn));
            }
        }
        return false;
    }

    public void printInvoice(int studentId, boolean sorted) {
        Student student = list.stream().filter(s -> s.getStudentId() == studentId).findFirst().orElse(null);
        if (student != null) {
            if (sorted) {
                ArrayList<Integer> sortedCrns = new ArrayList<>(student.getListOfCrns());
                Collections.sort(sortedCrns, Comparator.comparingInt(crn -> crn));
                student.setListOfCrns(sortedCrns);
                student.printFeeInvoice();
                student.setListOfCrns(sortedCrns); // Reset to original after printing sorted invoice
            } else {
                student.printFeeInvoice();
            }
        } else {
            System.out.println("No Student found!");
        }
    }

    public void manageCourse() {
        System.out.print("Enter the student's id: ");
        int studentId = DriverClass.scanner.nextInt();
        Student student = list.stream().filter(s -> s.getStudentId() == studentId).findFirst().orElse(null);
        if (student == null) {
            System.out.println("No Student found!");
            return;
        }
        // Code for adding/deleting courses goes here
    }

    public void searchStudent() {
        System.out.print("Enter the student’s id: ");
        int studentId = DriverClass.scanner.nextInt();
        if (searchById(studentId)) {
            System.out.println("Student found!");
        } else {
            System.out.println("No Student found!");
        }
    }

    public void printInvoice(boolean sorted) {
        System.out.print("Enter the student’s id: ");
        int studentId = DriverClass.scanner.nextInt();
        printInvoice(studentId, sorted);
    }
}
