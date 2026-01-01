import java.util.Scanner;

public class EnrollmentManager {
    // course id, student id, score
    String[][] enrollements = new String[2048][3];
    int nextID = 0;

    public static void menu() {
        System.out.println("=".repeat(15) + " Enrollment Manager " + "=".repeat(15));
        System.out.println("1.Print Students");
        System.out.println("2.Print Courses");
        System.out.println("0. <- Return to main menu");
        System.out.println("=".repeat(50));
    }

    public static void start(Scanner keyboard) {
        boolean enrollemntLoop = true;
        boolean restart = false;
        String choice = "";

        while (enrollemntLoop) {
            if (!restart) {
                menu();
                choice = keyboard.nextLine();
            }
            switch (choice) {
                case "1":
                    StudentManager.print_students();
                    restart = false;
                    break;
                case "2":
                    CourseManager.print_courses();
                    restart = false;
                    break;
                case "":
                    restart = false;
                    break;
                case "0":
                    enrollemntLoop = false;
                    break;
                default:
                    System.out.println("Please enter a valid input.");
                    break;
            }
            System.out.println();
        }
    }

}

