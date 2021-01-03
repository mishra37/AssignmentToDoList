// --== CS400 File Header Information ==--
// Name: Rahul S
// Email: sudhakar2@wisc.edu
// Team: LB
// Role: Frontend Developer
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class that provides the Front end interface that the user interacts with, implementation done for
 * it to work best with RedBlackTree which uses an Assignment object.
 * 
 * @author Rahul S
 *
 */
public class FrontEndInterface {
  static Scanner sc = new Scanner(System.in);

  /**
   * The driver method that begins the front end user interface. It inputs a command from the user
   * and calls the appropriate method through switch statements.
   * 
   * @param schedule
   */
  public static void beginPrompt(AssignmentScheduler schedule) {
    menu(); // prints the menu options
    Boolean whileBool = true;

    String usrIn = sc.nextLine();

    while (whileBool) {
      switch (usrIn) {
        case "A":
        case "a":
          addAssignment(schedule);
          break;
        case "S":
        case "s":
          assignmentStored(schedule);
          break;
        case "L":
        case "l":
          loadFromFile(schedule);
          break;
        case "F":
        case "f":
          findAssignment(schedule);
          break;
        case "R":
        case "r":
          removeAssignment(schedule);
          break;
        case "P":
        case "p":
          printAssignments(schedule);
          break;
        case "C":
        case "c":
          menu();
          break;
        case "E":
        case "e":
          whileBool = false;
          break;
        default:
          System.out.println("Please enter a valid command");
          break;
      }

      System.out.println("Proceed(Y/N): ");
      usrIn = sc.next();

      if (usrIn.equals("N") || usrIn.equals("n")) {
        usrIn = sc.nextLine();
        break;
      } else if (usrIn.equals("Y") || usrIn.equals("y")) {
        usrIn = sc.nextLine();
        System.out.println("Enter another command.");
        usrIn = sc.nextLine();
      }
    }
  }

  /**
   * Adds the assignment once the user has inputed its attributes correctly. It invokes the
   * Assignment's constructors to create and store the it in the schedule.
   * 
   * @param schedule
   */
  private static void addAssignment(AssignmentScheduler schedule) {
    System.out
        .println("Enter the due date (MM/DD/YYYY), the name of the assignment, the course name"
            + " and the number of points it is worth.");
    System.out.println("Please enter each attribute on a new line.");

    Date dueDate = dateHelper();

    String assnName = sc.nextLine();
    String courseName = sc.nextLine();
    Integer points = 0;

    Boolean boolVal = true;

    while (boolVal) {
      try {
        points = sc.nextInt();
        boolVal = false;
      } catch (Exception e) {
        System.out.println("Please enter the number of points correctly.");
        sc.nextLine();
      }
    }

    boolVal = true;
    Assignment userAssignment = new Assignment(dueDate, assnName, courseName, points);
    try {
      if (schedule.add(userAssignment)) {
        System.out.println("The assignment was stored successfully.");
        System.out.println();
      } else {
        System.out.println("The assignment was not stored.");
        System.out.println();
      }
    } catch (java.lang.IllegalArgumentException iae) {
      System.out.println("Scheduler already contains that assignment.");
    }

  }


  /**
   * Removes the assignment from the schedule associated with the particular date value. Once found
   * it displays the attributes of the assignment that has to be removed, else displays a message.
   * 
   * @param schedule
   */
  private static void removeAssignment(AssignmentScheduler schedule) {
    System.out.println("Enter the date (MM/DD/YYYY) of the assignment you want to remove");

    Date dueDate = dateHelper();
    System.out.println("List the course for which you want to remove the assignment.");
    String course = sc.nextLine();
    System.out.println("The assignment trying to be removed is: ");

    try {
      System.out.println(schedule.get(dueDate, course).toString());
      if (schedule.get(dueDate, course).isDeleted) {
        System.out.println(
            "The assignment you were trying to find has already been removed or completed.");
      } else if (schedule.deleteNode(dueDate) != null) {
        System.out.println("The assignment was removed successfully.");
        System.out.println();
      } else {
        System.out
            .println("The assignment you were trying to remove did not exist in the scheduler.");
      }
    } catch (NoSuchElementException e1) {
      System.out.println(
          "The assignment you were trying to remove did not exist in the schedule or has been "
              + "deleted. Please try adding the assignment, or checking if it exists in the "
              + "schedule first.");
      return;
    } catch (Exception e) {
      System.out.println("The assignment you are trying to remove did not exist in the tree");
      return;
    }
  }

  /**
   * Checks if the assignment associated with a date value inputed in the method exists in the
   * schedule.
   * 
   * @param schedule
   * @return true if product is found in the scheduler, else false.
   */
  private static boolean assignmentStored(AssignmentScheduler schedule) {
    System.out.println("Enter the due date (MM/DD/YYYY) and the course name "
        + "to check if it is stored in the scheduler.");
    System.out.println("Please enter each attribute on a new line.");

    Date dueDate = dateHelper();
    String courseName = sc.nextLine();

    if (schedule.get(dueDate, courseName) != null && !schedule.get(dueDate, courseName).isDeleted) {
      System.out.println("The assignment is stored in the scheduler.");
      return true;
    }
    System.out.println("The assignment could not be found in the scheduler.");
    return false;
  }

  /**
   * Finds the assignment associated with the date inputed in the method, if it exists in the
   * schedule. Once found it displays the attributes of the desired assignment.
   * 
   * @param schedule
   */
  private static void findAssignment(AssignmentScheduler schedule) {
    System.out.println("Enter the date (MM/DD/YYYY) of the assignment you want to find.");
    Date dueDate = dateHelper();
    System.out.println("List the course for which you want to find the assignment.");
    String course = sc.nextLine();
    try {
      Assignment returned = schedule.get(dueDate, course);
      if (returned == null) {
        System.out
            .println("The assignment you were trying to find could not be found in the schedule.");
        return;
      }
      System.out.println(schedule.get(dueDate, course).toString());
    } catch (NoSuchElementException e1) {
      System.out
          .println("The assignment you were trying to find could not be found in the schedule.");
      return;
    }
  }

  /**
   * Loads and adds products from a file into the schedule. It does so by parsing through the file
   * and then sorting the input, and if it is correct it gets added to the schedule.
   * 
   * @param schedule
   */
  public static void loadFromFile(AssignmentScheduler schedule) {
    File file = new File("assignmentToDo.txt");

    try (Scanner sc = new Scanner(file)) {

      if (sc.nextLine().equals("Due Date, Assignment Name, Course Name, Number of Points")) {
        while (sc.hasNextLine()) {
          String readLine = sc.nextLine();
          String[] allData = readLine.split(",");
          String dateStr = allData[0];
          String[] dateArr = dateStr.split("/");
          Date dueDate = new Date(Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]),
              Integer.parseInt(dateArr[2])); // Date (date, month, year)

          String assnName = allData[1];
          String courseName = allData[2];
          Integer points = Integer.parseInt(allData[3]);
          Assignment toAdd = new Assignment(dueDate, assnName, courseName, points);
          if (schedule.add(toAdd)) {
            continue;
          } else {
            System.out.println(toAdd);
            System.out.println("Some assignments were not added the schedule.");
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("File loaded into the red black tree!");
  }

  /**
   * Prints the assignments in the schedule, similar to how a Linked List or an Array prints the
   * elements.
   * 
   * @param schedule
   */
  private static void printAssignments(AssignmentScheduler schedule) {
    System.out.println(schedule.toString());
  }


  /**
   * Prints the set of commands that can be invoked by the user.
   * 
   */
  private static void menu() {
    System.out.println("---------------------------------------------------------------");
    System.out.println("Welcome to the Assignment Scheduling App.");
    System.out.println("You can use the following commands to manage your scheduler.");
    System.out.println("Enter 'A' to add an assignment to the schedule.");
    System.out.println("Enter 'S' to check if the assignment is stored in the schedule.");
    System.out.println("Enter 'L' to load a file to add assignments into the schedule.");
    System.out.println("Enter 'F' to find the assignment.");
    System.out.println("Enter 'R' to remove the assignment from your schedule.");
    System.out.println("Enter 'P' to print out all the assignments in order.");
    System.out.println("Enter 'E' to exit entering commands");
    System.out.println("Enter 'C' to see the list of commands again.");
    System.out.println("----------------------------------------------------------------");
  }

  /**
   * Helper method that helps the user input a date, till it is inputed correctly. Helper method for
   * addAssignment(), removeAssignment(), assignmentStored(), findAssignment().
   * 
   * @return date - once inputed correctly
   */
  private static Date dateHelper() {
    Boolean boolVal = true;
    String dateStr = "";

    while (boolVal) {
      try {
        dateStr = sc.nextLine();
        if (dateStr.length() == 10 && (dateStr.contains("/"))) { // ensures correct length of input
          for (int i = 0; i < dateStr.length(); i++) {
            if (dateStr.charAt(i) != '/' && !Character.isDigit(dateStr.charAt(i))) {
              boolVal = true;
              break;
            } else {
              boolVal = false;
            }
          }
        } else {
          System.out.println("Please enter the date (MM/DD/YYYY) correctly.");
          continue;
        }
      } catch (Exception e) {
        System.out.println("Please enter the date (MM/DD/YYYY) correctly.");
        sc.nextLine();
      }
    }

    String[] dateArr = dateStr.split("/");
    Date date = new Date(Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]),
        Integer.parseInt(dateArr[2])); // Date (date, month, year)
    return date;
  }

}
