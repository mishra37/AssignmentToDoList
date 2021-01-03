// --== CS400 File Header Information ==--
// Name: Ayushi Mishra
// Email: mishra37@wisc.edu
// Team: LB
// TA: Divyanshu Saxena
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestRedBlackTree {
  RedBlackTree<Integer> tree;
  String expectedOutput;
  String actualOutput;

  @BeforeEach
  public void initialiseTree() {
    tree = new RedBlackTree<Integer>();
    expectedOutput = "";
    actualOutput = "";
  }

  /**
   * 
   * this test method checks if performs a left-rotate and re-color operation to regain red-black
   * property
   * 
   */

  @Test
  public void test1() {
    // inserts first node as 7
    tree.insert(7);
    actualOutput = tree.toString();
    expectedOutput = "[7]";
    // checks if 7 gets inserted correctly
    assertEquals(expectedOutput, actualOutput);
    assertEquals(tree.root.data, 7);
    // checks if re-color took place to retain red-black property
    assertEquals(tree.root.isBlack, true);
    tree.insert(14);
    tree.insert(18);
    actualOutput = tree.toString();
    expectedOutput = "[14, 7, 18]";
    // checks if nodes gets inserted correctly
    assertEquals(expectedOutput, actualOutput);

    // checks if the tree performs left-rotate and color-change operation
    assertEquals(tree.root.data, 14);
    assertEquals(tree.root.leftChild.data, 7);
    assertEquals(tree.root.rightChild.data, 18);

    // checks for null children
    assertEquals(tree.root.leftChild.leftChild, null);
    assertEquals(tree.root.leftChild.rightChild, null);
    assertEquals(tree.root.rightChild.leftChild, null);
    assertEquals(tree.root.rightChild.rightChild, null);
    // checks for re-color of nodes
    assertEquals(tree.root.isBlack, true);
    assertEquals(tree.root.leftChild.isBlack, false);
    assertEquals(tree.root.rightChild.isBlack, false);

  }

  /**
   * 
   * when sibling of parent is red, then RBT performs re-color operation to re-balance
   * 
   */
  @Test
  public void test2() {
    tree.insert(7);
    tree.insert(14);
    tree.insert(18);
    tree.insert(23);
    actualOutput = tree.toString();
    expectedOutput = "[14, 7, 18, 23]";
    // checks if nodes gets inserted correctly
    assertEquals(expectedOutput, actualOutput);
    assertEquals(tree.root.rightChild.rightChild.data, 23);
    // checks for null children
    assertEquals(tree.root.leftChild.leftChild, null);
    assertEquals(tree.root.leftChild.rightChild, null);
    assertEquals(tree.root.rightChild.leftChild, null);
    // checks for re-color of nodes
    assertEquals(tree.root.isBlack, true);
    assertEquals(tree.root.leftChild.isBlack, true);
    assertEquals(tree.root.rightChild.isBlack, true);
    assertEquals(tree.root.rightChild.rightChild.isBlack, false);
    tree.insert(1);
    tree.insert(11);
    actualOutput = tree.toString();
    expectedOutput = "[14, 7, 18, 1, 11, 23]";

    // checks if nodes gets inserted correctly
    assertEquals(expectedOutput, actualOutput);

    assertEquals(tree.root.leftChild.leftChild.data, 1);
    assertEquals(tree.root.leftChild.rightChild.data, 11);
    // checks for null children
    assertEquals(tree.root.rightChild.leftChild, null);
    assertEquals(tree.root.leftChild.leftChild.leftChild, null);
    assertEquals(tree.root.leftChild.leftChild.rightChild, null);
    assertEquals(tree.root.leftChild.rightChild.leftChild, null);
    assertEquals(tree.root.leftChild.rightChild.rightChild, null);
    // no re-color because tree is already balanced

  }

  /**
   * 
   * this method checks if RBT performs right-left rotate and color change operation to regain its
   * properties
   * 
   */
  @Test
  public void test3() {
    tree.insert(7);
    tree.insert(14);
    tree.insert(18);
    tree.insert(23);
    tree.insert(1);
    tree.insert(11);
    tree.insert(20);
    actualOutput = tree.toString();
    expectedOutput = "[14, 7, 20, 1, 11, 18, 23]";
    // checks if nodes gets inserted correctly
    assertEquals(expectedOutput, actualOutput);
    // since the newly added node is red, which is added to the left of last red node
    // checks if tree performs restructure and re-color operation to re-balance
    assertEquals(tree.root.leftChild.data, 7);
    assertEquals(tree.root.rightChild.data, 20);
    assertEquals(tree.root.rightChild.rightChild.data, 23);
    assertEquals(tree.root.rightChild.leftChild.data, 18);

    assertEquals(tree.root.isBlack, true);
    assertEquals(tree.root.leftChild.isBlack, true);
    assertEquals(tree.root.rightChild.isBlack, true);
    assertEquals(tree.root.leftChild.leftChild.isBlack, false);
    assertEquals(tree.root.leftChild.rightChild.isBlack, false);
    assertEquals(tree.root.rightChild.leftChild.isBlack, false);
    assertEquals(tree.root.rightChild.rightChild.isBlack, false);
    tree.insert(29);
    tree.insert(25);
    actualOutput = tree.toString();
    expectedOutput = "[14, 7, 20, 1, 11, 18, 25, 23, 29]";

    // checks if nodes gets inserted correctly
    assertEquals(expectedOutput, actualOutput);
    // checks data for newly added nodes
    assertEquals(tree.root.rightChild.rightChild.data, 25);
    assertEquals(tree.root.rightChild.rightChild.leftChild.data, 23);
    assertEquals(tree.root.rightChild.rightChild.rightChild.data, 29);

    assertEquals(tree.root.rightChild.isBlack, false);
    assertEquals(tree.root.rightChild.rightChild.isBlack, true);
    assertEquals(tree.root.rightChild.leftChild.isBlack, true);
    assertEquals(tree.root.rightChild.rightChild.leftChild.isBlack, false);
    assertEquals(tree.root.rightChild.rightChild.rightChild.isBlack, false);

  }

  /**
   * This test method handles red property violation and cascading fixes Also, performs a right
   * rotate on parent node and its left child to retain RBT properties
   * 
   */
  @Test
  public void test4() {
    tree.insert(7);
    tree.insert(14);
    tree.insert(18);
    tree.insert(23);
    tree.insert(1);
    tree.insert(11);
    tree.insert(20);
    tree.insert(29);
    tree.insert(25);
    tree.insert(27);
    actualOutput = tree.toString();
    expectedOutput = "[20, 14, 25, 7, 18, 23, 29, 1, 11, 27]";
    // checks if nodes gets inserted correctly
    assertEquals(expectedOutput, actualOutput);

    // performs a left rotate and color change operation to re-balance tree properties
    assertEquals(tree.root.isBlack, true);
    assertEquals(tree.root.leftChild.isBlack, false);
    assertEquals(tree.root.rightChild.isBlack, false);
    assertEquals(tree.root.leftChild.leftChild.isBlack, true);
    assertEquals(tree.root.leftChild.rightChild.isBlack, true);
    assertEquals(tree.root.rightChild.leftChild.isBlack, true);
    assertEquals(tree.root.rightChild.rightChild.isBlack, true);
    assertEquals(tree.root.rightChild.rightChild.leftChild.isBlack, false);
    assertEquals(tree.root.leftChild.leftChild.leftChild.isBlack, false);
    assertEquals(tree.root.leftChild.leftChild.rightChild.isBlack, false);

    // inserts 26 as the left-most red node
    tree.insert(26);
    actualOutput = tree.toString();
    expectedOutput = "[20, 14, 25, 7, 18, 23, 27, 1, 11, 26, 29]";
    // checks if nodes gets inserted correctly
    assertEquals(expectedOutput, actualOutput);
    // performs a right-rotate and color change operation
    assertEquals(tree.root.rightChild.rightChild.data, 27);
    assertEquals(tree.root.rightChild.rightChild.leftChild.data, 26);
    assertEquals(tree.root.rightChild.rightChild.rightChild.data, 29);
    assertEquals(tree.root.rightChild.rightChild.isBlack, true);
    assertEquals(tree.root.rightChild.rightChild.leftChild.isBlack, false);
    assertEquals(tree.root.rightChild.rightChild.rightChild.isBlack, false);
  }
}
