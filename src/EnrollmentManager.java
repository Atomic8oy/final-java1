import java.util.Scanner;

public class EnrollmentManager {
    // course id, student id, score
    static String[][] enrollements = new String[2048][3];
    static int nextID = 0;

    // funcs to code: assign score, remove a student from a course, print signed up students in a course
    public static void menu() {
        System.out.println("=".repeat(15) + " Enrollment Manager " + "=".repeat(15));
        System.out.println("1.Print Students");
        System.out.println("2.Print Courses");
        System.out.println("3.Sign up a student in a course");
        System.out.println("4.Get enrollment ID");
        System.out.println("0. <- Return to main menu");
        System.out.println("=".repeat(50));
    }

    public static boolean isValidID(int id) {
        return id >= 0 && id < 2048 && enrollements[id][0] != null;
    }

    public static int get_enrollment_id(int courseID, int studentID) {
        for (int i = 0; i < enrollements.length; i++) {
            if (enrollements[i][0] != null && Integer.parseInt(enrollements[i][0]) == courseID && Integer.parseInt(enrollements[i][1]) == studentID) {
                return i;
            }
        }
        return -1;
    }

    public static int nextAvalableID(int from) {
        for (int i = from; i < enrollements.length; i++) {
            if (enrollements[i][0] == null) {
                return i;
            }
        }
        return -1;
    }

    public static int get_course_signups(int courseID) {
        int counter = 0;
        for (int i = 0; i < enrollements.length; i++) {
            if (enrollements[i][0] != null && Integer.parseInt(enrollements[i][0]) == courseID) {
                counter++;
            }
        }
        return counter;
    }

    public static boolean signup(int studentID, int courseID) {
        try {
            enrollements[nextID][0] = courseID + "";
            enrollements[nextID][1] = studentID + "";
            nextID = nextAvalableID(nextID);
            return true;
        } catch (Exception e) {
            System.out.println("An error occured: " + e);
            return false;
        }
    }

    public static void start(Scanner keyboard) {
        boolean enrollemntLoop = true;
        boolean restart = false;
        String choice = "";

        while (enrollemntLoop) {
            int courseID = -1;
            int studentID = -1;

            if (!restart) {
                menu();
                choice = keyboard.nextLine();
            }
            switch (choice) {
                case "debug":
                    for (int i = 0; i < enrollements.length; i++) {
                        if (enrollements[i][0] != null) {
                            System.out.println(i + "." + enrollements[i][0] + " | " + enrollements[i][1] + " | " + enrollements[i][2]);
                        }
                    }
                    break;
                case "1":
                    StudentManager.print_students();
                    break;
                case "2":
                    CourseManager.print_courses();
                    break;
                case "3":
                    System.out.print("Enter course id: ");
                    try {
                        courseID = Integer.parseInt(keyboard.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Failed to get an Integer input.");
                        restart = false;
                        break;
                    }
                    if (!CourseManager.isValidID(courseID)) {
                        System.out.println("Invalid course id");
                        restart = false;
                        break;
                    }

                    System.out.print("Enter student id: ");
                    try {
                        studentID = Integer.parseInt(keyboard.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Failed to get an Integer input.");
                        restart = false;
                        break;
                    }
                    if (!StudentManager.isValidID(studentID)) {
                        System.out.println("Invalid student id");
                        restart = false;
                        break;
                    }

                    if (get_enrollment_id(courseID, studentID) != -1) {
                        System.out.println("This student is already signed up on this course");
                        restart = false;
                        break;

                    }

                    String[] course = CourseManager.get_course(courseID);
                    if (Integer.parseInt(course[1]) > get_course_signups(courseID)) {
                        restart = !signup(studentID, courseID);
                        System.out.println("Student signed up successfuly.");
                    } else {
                        System.out.println("The course capacity is full.");
                        restart = false;
                    }
                    break;
                case "":
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

