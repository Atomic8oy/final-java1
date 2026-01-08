import java.util.Scanner;

public class EnrollmentManager {
    // course id, student id, score
    static String[][] enrollments = new String[2048][3];
    static int nextID = 0;

    public static void menu() {
        System.out.println("=".repeat(15) + " Enrollment Manager " + "=".repeat(15));
        System.out.println("1.Print Students");
        System.out.println("2.Print Courses");
        System.out.println("3.Sign up a student to a course");
        System.out.println("4.Get enrollment Information");
        System.out.println("5.Assign score");
        System.out.println("6.Remove student from course");
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

    public static boolean remove(int enrollmentID) {
        try {
            enrollments[enrollmentID][0] = null;
            enrollments[enrollmentID][1] = null;
            enrollments[enrollmentID][2] = null;
            nextID = enrollmentID;
            return true;
        } catch (Exception e) {
            System.out.println("An error occured: " + e);
            return false;
        }
    }

    public static boolean assign_score(int enrollmentID, float score) {
        try {
            enrollments[enrollmentID][2] = "" + score;
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
            int id = -1;
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
                            System.out.println(i + "." + enrollments[i][0] + ", " + enrollments[i][1] + " | " + enrollments[i][2]);
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

                    id = get_enrollment_id(courseID, studentID);
                    if (id == -1) {
                        System.out.println("This student haven't signed up to this course yet.");
                        restart = false;
                        break;
                    }

                    course = CourseManager.get_course(courseID);
                    student = StudentManager.get_student(studentID);

                    System.out.println(id + "." + course[0] + ", " + student[0] + " | " + enrollments[id][2]);
                    break;
                case "5":
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

                    id = get_enrollment_id(courseID, studentID);
                    if (id == -1) {
                        System.out.println("This student haven't signed up to this course yet.");
                        restart = false;
                        break;
                    }

                    student = StudentManager.get_student(studentID);
                    course = CourseManager.get_course(courseID);

                    float score;
                    System.out.print("Enter a score to asign for " + student[0] + " on " + course[0] + ": ");
                    try {
                        score = Float.parseFloat(keyboard.nextLine());
                    } catch (Exception e) {
                        System.out.println("Failed to get an float input");
                        restart = true;
                        break;
                    }

                    if (score < 0 || score > 20) {
                        restart = true;
                        System.out.println("Score must be a float between 0 and 20");
                        break;
                    }

                    restart = !assign_score(id, score);
                    if (!restart)
                        System.out.println("Successfull.");
                    break;
                case "6":
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

                    id = get_enrollment_id(courseID, studentID);
                    if (id == -1) {
                        System.out.println("This student haven't signed up to this course yet.");
                        restart = false;
                        break;
                    }

                    student = StudentManager.get_student(studentID);
                    course = CourseManager.get_course(courseID);

                    System.out.println("Are you sure that you want to remove " + student[0] + " from " + course[0] + "? (Y/n): ");
                    String confirm = keyboard.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        restart = !remove(id);
                        if (!restart)
                            System.out.println("Enrollment was removed successfully.");
                    } else {
                        restart = false;
                        break;
                    }


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

