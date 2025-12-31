import java.util.Scanner;

public class Main {

    public void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("School Management Program");
        boolean mainLoop = true;

        while (mainLoop) {
            System.out.println("=".repeat(19) + " Main Menu " + "=".repeat(20));
            System.out.println("1.Students");
            System.out.println("2.Courses");
            System.out.println("3.Save Data");
            System.out.println("4.Load Data");
            System.out.println("0.Exit");
            System.out.println("=".repeat(50));

            String opCode = keyboard.nextLine();
            switch (opCode) {
                case "1":
                    StudentManager.start(keyboard);
                    break;
                case "2":
                    CourseManager.start(keyboard);
                    break;
                case "0":
                    mainLoop = false;
                    break;
                case "":
                    break;
                default:
                    System.out.println("Invalid entry! Please enter a valid number.");
                    break;
            }
            System.out.println();
        }

        keyboard.close();

    }

}

