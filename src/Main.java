import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void menu() {
        System.out.println("=".repeat(19) + " Main Menu " + "=".repeat(20));
        System.out.println("1.Students");
        System.out.println("2.Courses");
        System.out.println("3.Enrollment");
        System.out.println("4.Save Data");
        System.out.println("5.Load Data");
        System.out.println("0.Exit");
        System.out.println("=".repeat(50));
    }

    public static boolean save() {
        try {
            FileWriter studentWriter = new FileWriter("data/students.data");
            studentWriter.write("");
            for (int i = 0; i < StudentManager.students.length; i++) {
                studentWriter.append(StudentManager.students[i][0] + "\n");
                studentWriter.append(StudentManager.students[i][1] + "\n");
            }
            studentWriter.close();

            FileWriter courseWriter = new FileWriter("data/courses.data");
            courseWriter.write("");
            for (int i = 0; i < CourseManager.courses.length; i++) {
                courseWriter.append(CourseManager.courses[i][0] + "\n");
                courseWriter.append(CourseManager.courses[i][1] + "\n");
            }
            courseWriter.close();

            FileWriter enrollmentWriter = new FileWriter("data/enrollments.data");
            enrollmentWriter.write("");
            for (int i = 0; i < EnrollmentManager.enrollments.length; i++) {
                enrollmentWriter.append(EnrollmentManager.enrollments[i][0] + "\n");
                enrollmentWriter.append(EnrollmentManager.enrollments[i][1] + "\n");
                enrollmentWriter.append(EnrollmentManager.enrollments[i][2] + "\n");
            }
            enrollmentWriter.close();

            return true;
        } catch (IOException e) {
            System.out.println("An error occured: " + e);
            return false;
        }
    }

    public static boolean load() {
        try {
            FileReader studentReader = new FileReader("data/students.data");
            Object[] studentData = studentReader.readAllLines().toArray();
            int id = 0;
            int p = 0;
            for (int i = 0; i < studentData.length; i++) {
                if (studentData[i].toString().equals("null")) {
                    StudentManager.students[id][p] = null;
                } else {
                    StudentManager.students[id][p] = studentData[i].toString();
                }
                p++;
                if (p == 2) {
                    p = 0;
                    id++;
                }
            }

            FileReader courseReader = new FileReader("data/courses.data");
            Object[] courseData = courseReader.readAllLines().toArray();
            id = 0;
            p = 0;
            for (int i = 0; i < courseData.length; i++) {
                if (courseData[i].toString().equals("null")) {
                    CourseManager.courses[id][p] = null;
                } else {
                    CourseManager.courses[id][p] = courseData[i].toString();
                }
                p++;
                if (p == 2) {
                    p = 0;
                    id++;
                }
            }

            FileReader enrollmentReader = new FileReader("data/5" +
                    "enrollments.data");
            Object[] enrollmentData = enrollmentReader.readAllLines().toArray();
            id = 0;
            p = 0;
            for (int i = 0; i < enrollmentData.length; i++) {
                if (enrollmentData[i].toString().equals("null")) {
                    EnrollmentManager.enrollments[id][p] = null;
                } else {
                    EnrollmentManager.enrollments[id][p] = enrollmentData[i].toString();
                }
                p++;
                if (p == 3) {
                    p = 0;
                    id++;
                }
            }

            return true;
        } catch (FileNotFoundException e) {
            System.out.println("No save files found!");
            return false;
        } catch (IOException e) {
            System.out.println("An error occured: " + e);
            return false;
        }
    }

    public void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("School Management Program");
        boolean mainLoop = true;

        while (mainLoop) {
            menu();

            String opCode = keyboard.nextLine();
            switch (opCode) {
                case "1":
                    StudentManager.start(keyboard);
                    break;
                case "2":
                    CourseManager.start(keyboard);
                    break;
                case "3":
                    EnrollmentManager.start(keyboard);
                    break;
                case "4":
                    if (save()) {
                        System.out.println("Data have saved successfully.");
                    } else {
                        System.out.println("Failed to save.");
                    }
                    break;
                case "5":
                    if (load()) {
                        System.out.println("Data have load successfully");
                    } else {
                        System.out.println("Failed to load.");
                    }
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

