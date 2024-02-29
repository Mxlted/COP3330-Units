// unit1.hw

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Constants
        final double COST_PER_CREDIT_HOUR = 120.25;
        final double HEALTH_AND_ID_FEES = 35.00;

        // Input Student Details
        System.out.print("Enter the Student’s Id: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter the Student’s full name: ");
        String studentName = scanner.nextLine();

        // Input Course Details
        System.out.print("Enter crn/credit hours for the first class: ");
        String[] firstCourseDetails = scanner.nextLine().split("/");
        int firstCourseCRN = Integer.parseInt(firstCourseDetails[0]);
        int firstCourseCredit = Integer.parseInt(firstCourseDetails[1]);

        System.out.print("Enter crn/credit hours for the second class: ");
        String[] secondCourseDetails = scanner.nextLine().split("/");
        int secondCourseCRN = Integer.parseInt(secondCourseDetails[0]);
        int secondCourseCredit = Integer.parseInt(secondCourseDetails[1]);

        // Calculations
        double firstCourseCost = firstCourseCredit * COST_PER_CREDIT_HOUR;
        double secondCourseCost = secondCourseCredit * COST_PER_CREDIT_HOUR;
        double totalCost = firstCourseCost + secondCourseCost + HEALTH_AND_ID_FEES;

        // Displaying the Invoice
        System.out.println("Thank you!\nHERE IS THE FEE INVOICE...");
        System.out.println("SIMPLE COLLEGE\nORLANDO FL 10101");
        System.out.println("*************************");
        System.out.println("Fee Invoice Prepared for:");
        System.out.println("[" + studentName + "][" + studentId + "]");
        System.out.println("1 Credit Hour = $" + COST_PER_CREDIT_HOUR);
        System.out.println("CRN CREDIT HOURS");
        System.out.println(firstCourseCRN + " " + firstCourseCredit + " $" + String.format("%.2f", firstCourseCost));
        System.out.println(secondCourseCRN + " " + secondCourseCredit + " $" + String.format("%.2f", secondCourseCost));
        System.out.println("Health & id fees $" + HEALTH_AND_ID_FEES);
        System.out.println("----------------------------------------");
        System.out.println("Total Payments $" + String.format("%.2f", totalCost));
    }
}
