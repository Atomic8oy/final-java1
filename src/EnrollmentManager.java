import java.util.Scanner;

public class EnrollmentManager {
    // course id, student id, score
    static String[][] enrollments = new String[2048][3];
    static int nextID = 0;

    // funcs to code: assign score, remove a student from a course, print signed up students in a course
    public static void menu() {
        System.out.println("=".repeat(15) + " Enrollment Manager " + "=".repeat(15));
        System.out.println("1.Print Students");
        System.out.println("2.Print Courses");
        System.out.println("3.Sign up a student in a course");
        System.out.println("4.Get enrollment Information");
        System.out.println("0. <- Return to main menu");
        System.out.println("=".repeat(50));
    }

    public static boolean isValidID(int id) {
        return id >= 0 && id < 2048 && enrollments[id][0] != null;
    }

    public static int get_enrollment_id(int courseID, int studentID) {
        for (int i = 0; i < enrollments.length; i++) {
            if (enrollments[i][0] != null && Integer.parseInt(enrollments[i][0]) == courseID && Integer.parseInt(enrollments[i][1]) == studentID) {
                return i;
            }
        }
        return -1;
    }

    public static int nextAvalableID(int from) {
        for (int i = from; i < enrollments.length; i++) {
            if (enrollments[i][0] == null) {
                return i;
            }
        }
        return -1;
    }

    public static int get_course_signups(int courseID) {
        int counter = 0;
        for (int i = 0; i < enrollments.length; i++) {
            if (enrollments[i][0] != null && Integer.parseInt(enrollments[i][0]) == courseID) {
                counter++;
            }
        }
        return counter;
    }

    public static boolean signup(int studentID, int courseID) {
        try {
            enrollments[nextID][0] = courseID + "";
            enrollments[nextID][1] = studentID + "";
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
            String[] course;
            String[] student;

            if (!restart) {
                menu();
                choice = keyboard.nextLine();
            }
            switch (choice) {
                case "debug":
                    for (int i = 0; i < enrollments.length; i++) {
                        if (enrollments[i][0] != null) {
                            System.out.println(i + "." + enrollments[i][0] + " | " + enrollments[i][1] + " | " + enrollments[i][2]);
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
                    courseID = CourseManager.get_course_id(keyboard);
                    if (courseID == -1) {
                        restart = false;
                        break;
                    }

                    studentID = StudentManager.get_student_id(keyboard);
                    if (studentID == -1) {
                        restart = true;
                        break;
                    }

                    if (get_enrollment_id(courseID, studentID) != -1) {
                        System.out.println("This student is already signed up on this course");
                        restart = false;
                        break;

                    }

                    course = CourseManager.get_course(courseID);
                    if (Integer.parseInt(course[1]) > get_course_signups(courseID)) {
                        restart = !signup(studentID, courseID);
                        System.out.println("Student signed up successfuly.");
                    } else {
                        System.out.println("The course capacity is full.");
                        restart = false;
                    }
                    break;
                case "4":
                    courseID = CourseManager.get_course_id(keyboard);
                    if (courseID == -1) {
                        restart = false;
                        break;
                    }

                    studentID = StudentManager.get_student_id(keyboard);
                    if (studentID == -1) {
                        restart = true;
                        break;
                    }

                    int id = get_enrollment_id(courseID, studentID);
                    if (id == -1) {
                        System.out.println("Enrollment was not found.");
                        restart = false;
                        break;
                    }

                    course = CourseManager.get_course(courseID);
                    student = StudentManager.get_student(studentID);

                    System.out.println(id + "." + course[0] + ", " + student[0] + " | " + enrollments[id][2]);
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

