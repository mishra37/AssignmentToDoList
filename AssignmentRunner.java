// --== CS400 File Header Information ==--
// Name: Vaishnavi Deshpande
// Email: vvdeshpande@wisc.edu
// Team: LB
// Role: Data Wrangler 2
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


public class AssignmentRunner {
    public static void main(String args[]) {
      System.out.println("now generating file");
      AssignmentGenerator fileGen = new AssignmentGenerator();
      fileGen.generateFile();  
      AssignmentScheduler runner = new AssignmentScheduler();
      FrontEndInterface.beginPrompt(runner); 
    }
  }
