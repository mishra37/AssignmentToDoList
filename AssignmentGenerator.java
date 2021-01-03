// --== CS400 File Header Information ==--
// Name: Vaishnavi Deshpande
// Email: vvdeshpande@wisc.edu
// Team: LB
// Role: Data Wrangler 2
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.Random;
import java.io.FileWriter;

/**
 * This is the class that generates random assignment objects that can be added to the red black
 * tree
 * 
 * It generates and calls the insert method to do the same.
 * 
 */

public class AssignmentGenerator {
  Assignment[] assignments = new Assignment[1000];
  // array of random product names to store initially
  String[] courseNames = {"ASTRON 103", "ECE 252", "PSYCH 101", "CS 400", "MATH 234", "MUSIC 113",
      "MATH 340", "CS 320", "AFRO AMER 101", "ILS 215"};
  String[] types = {"Weekly Quiz", "Project", "Discussion Notes", "Homework", "Essay", "Exam",
      "Midterm", "Lecture Videos", "Textbook Readings", "Presentation"};

  // this method generates random products and adds it to the hash table
  public void generateFile() {
    int index = 0;
    Random dayGen = new Random();
    Random pointsGen = new Random();
    for (int i = 0; i < types.length; i++) {
      for (int j = 0; j < courseNames.length; j++) {
        int dayOfOct = dayGen.nextInt(31) + 1;
        Date dateObj = new Date(dayOfOct, 10, 2020);
        assignments[index] =
            new Assignment(dateObj, types[i], courseNames[j], (pointsGen.nextInt(4) + 1) * 10);
        index++;
      }
    }
    
    // writing to a file
    try {
      FileWriter fw = new FileWriter("assignmentToDo.txt");
      // comma separated file
      fw.write("Due Date, Assignment Name, Course Name, Number of Points\n");
      for (int i = 0; i < index; i++) {
        fw.write(assignments[i].getDate() + "," + assignments[i].getAssignName() + ","
            + assignments[i].getCourseName() + "," + assignments[i].getPoints() + "\n");
      }
      fw.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
