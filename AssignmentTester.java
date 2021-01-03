// --== CS400 File Header Information ==--
// Name: Uday Malhotra
// Email: umalhotra@wisc.edu
// Team: LB
// Role: Test Engineer 2
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.io.File;
import org.junit.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.*; 

/**
 * This is a tester for the Assignment Planner. 
 * 
 * It attempts to create a planner and test its various functionalities in different manipulated
 * tree conditions.
 * @author Uday Malhotra
 */

public class AssignmentTester {
    /**
     * This method tests the add implementation.
     * 
     * Tree is checked for size, and elements.
     * 
     */
  @Test
    public void testAdd(){
        //creating an initial tree
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        //checking positioning of nodes
        if((test.root.data.getDate().compareTo(new Date(7, 11, 2020))) != 0)
        fail("Test failed");
        if((test.root.leftChild.data.getDate().compareTo(new Date(6, 11, 2020))) != 0)
        fail("Test failed");
        if((test.root.rightChild.data.getDate().compareTo(new Date(9, 11, 2020))) != 0)
        fail("Test failed");

        //manipulating tree to add two additional nodes
        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        //testing new size
        if(test.getSize()!=5)
        fail("Test failed");
        if((test.root.rightChild.leftChild.data.getDate().compareTo(new Date(8, 11, 2020))) != 0)
        fail("Test failed");
        if((test.root.rightChild.rightChild.data.getDate().compareTo(new Date(10, 11, 2020))) != 0)
        fail("Test failed");
        //trying to add a duplicate assignment
        if(test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50)))
        fail("Test failed"); 

        test.deleteNode(new Date(10, 11, 2020));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));
        //trying to re-add the deleted node
        if(!test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        fail("Test failed");
    }
    /**
     * This method tests the functionality of the contains implementation.
     * 
     * Tree is checked for nodes that are present, missing, or deleted.
     * 
     */
    @Test
    public void testContains(){
        //creating an initial tree
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        //checking to see if it detects a contained assignment
        if(!test.contains(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50)))
        fail("Test failed");
        //checking to see if it detects a missing assignment
        if(test.contains(new Assignment(new Date(25, 11, 2020), "Homework", "Linguis 101", 50)))
        fail("Test failed");

        //manipulating tree to add two more assignments
        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        //checking to see if updated tree detects a contained assignment
        if(!test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        fail("Test failed");

        //deleting an assignment(root)
        test.deleteNode(new Date(7, 11, 2020));
         // //checking to see if updated tree detects a contained assignment
        if(!test.contains(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10)))
        fail("Test failed");
         // //checking to see if updated tree detects a deleted assignment
        if(test.contains(new Assignment(new Date(7, 11, 2020),"Quiz", "Math 240", 20)))
        fail("Test failed");

        //deleting another assignment
        test.deleteNode(new Date(10, 11, 2020));
        //checking to see if deleted assignment is detected
        if(test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        fail("Test failed");
    }
    /**
     * This method tests the functionality of the Delete method.
     * 
     * Tree is checked for deleting udeleted nodes, deleting
     * an already deleted node, and deleting a node that is
     * not present.
     * 
     */
    @Test
    public void testDelete(){
        //creating an initial tree
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));

        //deleting a node
        test.deleteNode(new Date(9, 11, 2020));
        //checking if deleted node is present
        if(test.contains(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50)))
        fail("Test failed");

        //manipulating tree by adding two more nodes
        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));
        
        //deleting a node from the manipulated tree
        test.deleteNode(new Date(8, 11, 2020));
        //checking if deleted node is present
        if(test.contains(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100)))
        fail("Test failed");

        //deleting another node(root) from the manipulated tree
        test.deleteNode(new Date(7, 11, 2020));
         //checking if deleted node is present
        if(test.contains(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20)))
        fail("Test failed");

        //deleting and checking another node to set up next text
        test.deleteNode(new Date(10, 11, 2020));
        if(test.contains(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80)))
        fail("Test failed");

        //trying to delete an already deleted node
        try{
            test.deleteNode(new Date(10, 11, 2020));
        }
        catch(NoSuchElementException e){
        //checking if NSE is thrown correctly
            if(!(e.getMessage().contains("already been deleted")))
            fail("Test failed");

        }

        //trying to delete a node that is not present in tree
        try{
            test.deleteNode(new Date(20, 11, 2020));
        }
        catch(NoSuchElementException e){
        //checking if NSE is thrown correctly
            if(!(e.getMessage().contains("cannot be")))
            fail("Test failed");

        }
    }
    /**
     * This method tests the functionality of the get implementation.
     * 
     * Tree is checked for present nodes, missing nodes, and 
     * deleted nodes.
     * 
     */
    @Test
    public void testGet(){
        //creating intial tree
        AssignmentScheduler test = new AssignmentScheduler();
        test.add(new Assignment(new Date(7, 11, 2020), "Quiz", "Math 240", 20));
        test.add(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10));
        test.add(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50));
        //checking if get returns correct assignment
        if((test.get(new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10)).compareTo(
        (new Assignment(new Date(6, 11, 2020), "Quiz", "Astron 123", 10)))!=0))
        fail("Test failed");

        //deleting an assignment
        test.deleteNode(new Date(9, 11, 2020));
        //checking if get returns (done) when called with toString when trying to get deleted assignment
        if(test.get(new Assignment(new Date(9, 11, 2020), "Homework", "Linguis 101", 50))
        .toString().contains("(DONE! )"))
        fail("Test failed");
        //checking if get returns null when trying to get an assignment that is not present
        if(test.get(new Date(25, 11, 2020), "missing")!= null)
        fail("Test failed");

        //manipulating tree by adding two more assignments
        test.add(new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100));
        test.add(new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80));

        //checking if manipulated tree returns correct assignment when running get on a present node
        if(test.get(new Date(10, 11, 2020), "Psych 414").getDate().compareTo(
        (new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80).getDate()))!=0)
        fail("Test failed");
    }
    /**
     * This method tests if file of random assignments
     * is generated succesfully.
     * 
     */
    @Test
    public void testGenerator(){
        //creating an object of the assignment generator class
        AssignmentGenerator fileGen = new AssignmentGenerator();
        //generating file
        fileGen.generateFile();
        //checking if file is generated correctly
        if(!(new File("./assignmentToDo.txt").isFile()))
        fail("Test failed");

    }

    /**
     * This methods tests the functionality of the compareTo method implemented
     * for the Assignment class.
     * 
     * Tests are done for equal dates, greater and smaller dates, equal dates differing
     * in course names, etc.
     * 
     */
    @Test
    public void testCompareTo(){
    //declaring intial assignments to run tests on
    Assignment a = new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80);
    Assignment b = new Assignment(new Date(8, 11, 2020), "Exam", "Math 240", 100);
    Assignment c = new Assignment(new Date(10, 11, 2020), "Quiz", "Psych 414", 80);
    Assignment d = new Assignment(new Date(10, 11, 2020), "Quiz", "Comp Sci 400", 80);
    //assignment a is due later than assignment b
    if(a.compareTo(b) < 1)
    fail("Test failed");
    //assignment b is due earlier than assignment a
    if(b.compareTo(a) > -1)
    fail("Test failed");
    //assignment a and c are the same
    if(a.compareTo(c) != 0)
    fail("Test failed");
    //assignments a and d due on same date, but a is alphabetically higher in course name
    if(a.compareTo(d) < 1)
    fail("Test failed");
     //assignments d and a due on same date, but d is alphabetically lower in course name
    if(d.compareTo(a) > -1)
    fail("Test failed");
    }
}
