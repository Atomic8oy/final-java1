import java.util.InputMismatchException;
import java.util.Scanner;

public class CourseManager {

    // course name, max spots,
    static String[][] courses = new String[64][2];
    static int nextID = 0;

    public static void menu() {
        System.out.println("=".repeat(17) + " Course Manager " + "=".repeat(17));
        System.out.println("1.Show all courses");
        System.out.println("2.Add a course");
        System.out.println("3.Modify course");
        System.out.println("4.Remove course");
        System.out.println("0. <- Return to main menu");
        System.out.println("=".repeat(50));
    }

    public static int calculate_course_signups(int courseID) {
        return 0;
    }

    public static int nextAvalableID(int from) {
        for (int i = from; i < courses.length; i++) {
            if (courses[i][0] == null) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isValidID(int id) {
        return id >= 0 && id < 64 && courses[id][0] != null;
    }

    public static void print_courses() {
        boolean foundMatch = false;
        for (int i = 0; i < courses.length; i++) {
            if (courses[i][0] != null) {
                int remaining = Integer.parseInt(courses[i][1]) - calculate_course_signups(i);
                System.out.println(i + "." + courses[i][0] + " | " + remaining + " / " + courses[i][1]);
                foundMatch = true;
            }
        }
        if (!foundMatch) {
            System.out.println("No courses was found!");
        }
    }

    public static boolean add(String courseName, int maxSpots) {
        try {
            courses[nextID][0] = courseName;
            courses[nextID][1] = maxSpots + ""; // normal (String) typecast did not work. don't ask cuz it works
            nextID = nextAvalableID(nextID);
            return true;
        } catch (Exception e) {
            System.out.println("An error occured: " + e);
            return false;
        }
    }

    public static boolean modify(int courseID, String newCourseName, int newMaxSpots) {
        try {
            courses[courseID][0] = newCourseName;
            courses[courseID][1] = newMaxSpots + ""; // samething as above
            return true;
        } catch (Exception e) {
            System.out.println("An error occured: " + e);
            return false;
        }
    }

    public static boolean remove(int courseID) {
        try {
            courses[courseID][0] = null;
            courses[courseID][1] = null;
            nextID = courseID;
            return true;
        } catch (Exception e) {
            System.out.println("An error occured: " + e);
            return false;
        }
    }

    public static void start(Scanner keyboard) {
        boolean couseLoop = true;
        boolean restart = false;
        String choice = "";

        while (couseLoop) {
            int courseID = -1;
            if (!restart) {
                menu();
                choice = "";
                choice = keyboard.nextLine();
            }
            switch (choice) {
                case "1":
                    print_courses();
                    break;
                case "2":
                    System.out.print("Enter course name: ");
                    String courseName = keyboard.nextLine();
                    if (courseName == "") {
                        System.out.println("Please don't leave this input empty");
                        break;
                    }
                    int maxSpots = -1;
                    System.out.print("Enter maximum spots: ");
                    try {
                        maxSpots = Integer.parseInt(keyboard.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid input.");
                        restart = true;
                        break;
                    }
                    if (maxSpots < 1) {
                        System.out.println("This input can't be less than one.");
                        restart = true;
                        break;
                    }
                    restart = !add(courseName, maxSpots);
                    break;
                case "3":
                    try {
                        System.out.print("Enter couse ID: ");
                        courseID = Integer.parseInt(keyboard.nextLine());
                    } catch (Exception e) {
                        System.out.println("Failed to get an integer input.");
                        restart = false;
                        break;
                    }
                    if (!isValidID(courseID)) {
                        System.out.println("Course was not found! Enter a valid course id.");
                        restart = false;
                        break;
                    }

                    System.out.print("Enter course name (Current name: " + courses[courseID][0] + "): ");
                    String newCourseName = keyboard.nextLine();
                    if (newCourseName == "") {
                        System.out.println("Please don't leave this input empty");
                        restart = true;
                        break;
                    }

                    int newMaxSpots = -1;
                    System.out.print("Enter maximum spots: ");
                    try {
                        newMaxSpots = Integer.parseInt(keyboard.nextLine());
                    } catch (InputMismatchException e) {
                        System.out.println("Failed to get an integer input.");
                        restart = true;
                        break;
                    }
                    if (newMaxSpots < 1) {
                        System.out.println("This input can't be less than one.");
                        restart = true;
                        break;
                    }

                    restart = !modify(courseID, newCourseName, newMaxSpots);
                    break;
                case "4":
                    System.out.print("Enter the course ID: ");
                    try{
                        courseID = Integer.parseInt(keyboard.nextLine());
                    } catch (Exception e) {
                        System.out.println("Failed to get an integer input.");
                        restart = false;
                        break;
                    }
                    if (isValidID(courseID)) {
                        System.out.print("Are you sure that you want to remove " + courses[courseID][0] + "? (Y/N): ");
                        String confirm = keyboard.nextLine();
                        if (confirm.equalsIgnoreCase("Y")) {
                            restart = !remove(courseID);
                            if (!restart)
                                System.out.println("Course was removed successfully.");
                        } else {
                            restart = false;
                            break;
                        }
                    } else {
                        System.out.println("The course was not found! Please enter a student id.");
                        restart = false;
                        break;
                    }
                    break;
                case "0":
                    couseLoop = false;
                    break;
                case "":
                    break;
                default:
                    System.out.println("Please enter a valid input.");
                    break;
            }
            System.out.println();
        }
    }
}
