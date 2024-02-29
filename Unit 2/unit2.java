import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Asking user for input
        System.out.print("Enter the Student's Id: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter the Student's full name: ");
        String studentName = scanner.nextLine();
        System.out.print("Enter crn/credit hours for the first class: ");
        String firstClassInput = scanner.nextLine();
        System.out.print("Enter crn/credit hours for the second class: ");
        String secondClassInput = scanner.nextLine();

        // Parsing input
        String[] firstClassData = firstClassInput.split("/");
        int crn1 = Integer.parseInt(firstClassData[0]);
        int creditHours1 = Integer.parseInt(firstClassData[1]);

        String[] secondClassData = secondClassInput.split("/");
        int crn2 = Integer.parseInt(secondClassData[0]);
        int creditHours2 = Integer.parseInt(secondClassData[1]);

        // Instantiation of FeeInvoice
        FeeInvoice feeInvoice = new FeeInvoice(studentName, studentId, crn1, creditHours1, crn2, creditHours2);

        // Printing the fee invoice
        System.out.println("Thank you!\nHERE IS THE FEE INVOICE...");
        feeInvoice.printFeeInvoice();

        scanner.close();
    } // end of main

    // FeeInvoice class inside Main
    static class FeeInvoice {
        private String studentName;
        private String studentId;
        private int crn1, creditHours1, crn2, creditHours2;

        public FeeInvoice(String studentName, String studentId, int crn1, int creditHours1, int crn2, int creditHours2) {
            this.studentName = studentName;
            this.studentId = studentId;
            this.crn1 = crn1;
            this.creditHours1 = creditHours1;
            this.crn2 = crn2;
            this.creditHours2 = creditHours2;
        }

        // Setters and Getters
        // ...

        private double calculateTotalPayment() {
            final double COST_PER_CREDIT = 120.25;
            final double HEALTH_AND_ID_FEES = 35.00;
            return (creditHours1 * COST_PER_CREDIT) + (creditHours2 * COST_PER_CREDIT) + HEALTH_AND_ID_FEES;
        }

        public void printFeeInvoice() {
            System.out.println("SIMPLE COLLEGE\nORLANDO FL 10101\n*************************\nFee Invoice Prepared for:");
            System.out.printf("[%s][%s]\n", studentName, studentId);
            System.out.println("1 Credit Hour = $120.25\nCRN CREDIT HOURS");
            System.out.printf("%d %d $%.2f\n", crn1, creditHours1, creditHours1 * 120.25);
            System.out.printf("%d %d $%.2f\n", crn2, creditHours2, creditHours2 * 120.25);
            System.out.println("Health & id fees $35.00\n----------------------------------------");
            System.out.printf("Total Payments $%.2f\n", calculateTotalPayment());
        }
    }
}