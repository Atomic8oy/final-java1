import java.util.Scanner;

public class StudentManager {

    // name, notes
    static String[][] students = new String[128][3];
    static int nextID = 0;

    public static void menu() {
        System.out.println("=".repeat(16) + " Student Manager " + "=".repeat(17));
        System.out.println("1.Show all students");
        System.out.println("2.Add a student");
        System.out.println("3.Modify student");
        System.out.println("4.Remove student");
        System.out.println("5.Check signups");
        System.out.println("0. <- Return to main menu");
        System.out.println("=".repeat(50));
    }

    public static String[] get_student(int studentID) {
        return students[studentID];
    }

    public static int nextAvalableID(int from) {
        for (int i = from; i < students.length; i++) {
            if (students[i][0] == null) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isValidID(int id) {
        return id >= 0 && id < 128 && students[id][0] != null;
    }

    public static int get_student_id(Scanner keyboard) {
        int studentID = -1;
        System.out.print("Enter the student ID: ");
        try{
            studentID = Integer.parseInt(keyboard.nextLine());
        } catch (Exception e) {
            System.out.println("Failed to get an integer input.");
            return -1;
        }
        if (isValidID(studentID)) {
            return studentID;
        }
        System.out.println("Student not found! Please enter a student id.");
        return -1;
    }

    public static void print_students() {
        boolean foundMatch = false;
        for (int i = 0; i < students.length; i++) {
            if (students[i][1] != null) {
                System.out.println(i + "." + students[i][0] + " | " + students[i][1]);
                foundMatch = true;
            }
        }
        if (!foundMatch) {
            System.out.println("No student was found!");
        }
    }

    public static boolean add(String name, String note) {
        try {
            students[nextID][0] = name;
            students[nextID][1] = note;
            nextID = nextAvalableID(nextID);
            return true;

        } catch (Exception e) {
            System.out.println("An error occurred: " + e);
            return false;
        }
    }

    public static boolean modify(int id, String name, String note) {
        try {
            students[id][0] = name; // Update name
            students[id][1] = note; // Update note
            return true;

        } catch (Exception e) {
            System.out.println("An error occurred: " + e);
            return false;
        }
    }

    public static boolean remove(int id) {
        try {
            students[id][0] = null;
            students[id][1] = null;
            nextID = id;
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e);
            return false;
        }
    }


    public static String[][] get_students() {
        return students;
    }


    public static void start(Scanner keyboard) {
        boolean smloop = true;
        boolean restart = false;
        String choice = "";
        int id = -1;

        while (smloop) {
            if (!restart) {
                menu();
                choice = keyboard.nextLine();
            }
            switch (choice) {
                case "1":
                    print_students();
                    break;
                case "2":
                    System.out.print("Enter the full name of the student: ");
                    String name = keyboard.nextLine();
                    if (name == "") {
                        System.out.println("Do not leave this input empty.");
                        restart = false;
                        break;
                    }
                    System.out.print("Enter notes for this student (optional): ");
                    String note = keyboard.nextLine();

                    restart = !add(name, note);
                    if (!restart) {
                        System.out.println("Successfull operation.");
                        restart = false;
                    }
                    break;
                case "3":
                    id = get_student_id(keyboard);
                    if (id == -1) {
                        restart = false;
                        break;
                    }

                    System.out.print("Enter the new name (current: " + students[id][0] + "): ");
                    String newName = keyboard.nextLine();
                    if (newName == "") {
                        System.out.println("Don't leave this input empty!");
                        restart = true;
                        break;
                    }

                    System.out.println("Current note: \n" + students[id][1] + "\n---------");
                    System.out.print("Enter the new note: ");
                    String newNote = keyboard.nextLine();

                    restart = !modify(id, newName, newNote);
                    if (!restart) {
                        System.out.println("Function exit successfull.");
                    }
                    break;
                case "4":
                    id = get_student_id(keyboard);

                    if (id != -1) {
                        System.out.print("Are you sure that you want to remove " + students[id][0] + "? (Y/N): ");
                        String confirm = keyboard.nextLine();
                        if (confirm.equalsIgnoreCase("Y")) {
                            restart = !remove(id);
                            System.out.println("Student was removed successfully.");
                        } else {
                            restart = false;
                            break;
                        }
                    } else {
                        restart = false;
                        break;
                    }
                    break;
                case "0":
                    smloop = false;
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