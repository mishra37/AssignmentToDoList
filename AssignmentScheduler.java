import java.util.NoSuchElementException;

// --== CS400 File Header Information ==--
// Name: Ayushi Mishra
// Email: mishra37@wisc.edu
// Team: LB
// Role: Backend Developer
// TA: Divyanshu Saxena
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
/**
 * 
 * @author ayushi
 *
 * This class implements Red Black Tree data structure, which stores objects of Assignment
 * class as nodes
 * @param T - Assignment
 */
public class AssignmentScheduler extends RedBlackTree<Assignment> {

  private int size;

  /**
   * method checks if a particular key is present in the RBT
   *
   * @return true if key if present in RBT
   */
  public boolean contains(Assignment assign) {
    return containsHelper(root, assign);
  }

  /**
   * Helper for contains method
   *
   * @param node - root
   * @param key  - key to lookup
   * @return true if the node can be found with specified key, false if marked deleted
   */
  private boolean containsHelper(Node<Assignment> node, Assignment assign) {
    // if root node is null, we return false
    if (node == null) {
      return false;
    }
    // uses compareTo method to store resulting positive or negative value in compare
    int compare = node.data.compareTo(assign);

    if (compare > 0) {
      return containsHelper(node.leftChild, assign);
    } else if (compare < 0) {
      return containsHelper(node.rightChild, assign);
    } else {
      if (node.data.isDeleted)
        return false;
      return true;
    }
  }

  /**
   * Get Value for a given key
   * 
   * @return value for the key
   */
  public Assignment get(Date date, String course) {
    // dummy object to pass into getHelper
    Assignment obj = new Assignment(date, "dummy", course, 40);
    // dummy node
    Node<Assignment> node = new Node<Assignment>(obj);
    return getHelper(node, root, date, course);
  }

  /**
   * Get Value for a given key
   * 
   * @return value for the key
   */
  public Assignment get(Assignment obj) {
    // dummy object to pass into getHelper
    // dummy node
    Node<Assignment> node = new Node<Assignment>(obj);
    return getHelper(node, root, obj.getDate(), obj.getCourseName());
  }

  /**
   * Helper for get method
   * 
   * @param key  key to lookup
   * @param node root
   * @return node for the key
   */
  private Assignment getHelper(Node<Assignment> node, Node<Assignment> subtree, Date date,
      String course) {
    if (subtree == null)
      return null;
    int compare = node.data.compareTo(subtree.data);
    if (node.data.getDate().compareTo(subtree.data.getDate()) == 0) {
      if (node.data.getCourseName().equals(subtree.data.getCourseName())) {
        compare = 0;
      }
    }
    if (compare == 0) {
      return subtree.data;
    }

    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty
        return null;
      } else
        return getHelper(node, subtree.leftChild, date, course);
    }

    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty
        return null;
      } else
        return getHelper(node, subtree.rightChild, date, course);
    }
  }

  /**
   * This method calls to string from the RBT class.
   * 
   * @return string containing the values of this tree in order
   * 
   */
  @Override
  public String toString() {
    return root.toString();
  }

  /**
   * this method adds a new element to the RBT, if not already present
   * 
   * @param value to be added
   * @return true if is successfully adds the element
   */
  public boolean add(Assignment value) throws java.lang.IllegalArgumentException {
    // calls insert only if RBT does not already contains the value to be added
    // to avoid duplicates
    if (this.contains(value)) {
      return false;
    } else {
      if (this.get(value) != null)
        this.get(value).isDeleted = false;
      else
        this.insert(value);
    }
    // increments size on adding a new element
    size++;
    return true;
  }

  /**
   * This method returns the number of key-value pair stored in the red black tree
   * 
   * @return returns the size of tree
   */
  @SuppressWarnings("unused")
  public int getSize() {
    return size;
  }

  /**
   * This method deletes value of the element by marking it deleted, but not actually removing the
   * node.
   * 
   */
  public Assignment deleteNode(Date date) {
    return deleteNodeHelper(root, date);
  }

  /**
   * helper method for delete node method, which marks the assignment object found using key as
   * deleted
   * 
   * @throws NoSuchElementException - if root node is null and if the found node is already marked
   *                                deleted  
   * @param node - root
   * @param date - used as key
   * 
   */
  public Assignment deleteNodeHelper(Node<Assignment> node, Date date)
      throws NoSuchElementException {
    // returns if the node to be deleted is null
    if (node == null) {
      throw new NoSuchElementException("Element cannot be found in the tree.");
    }
    // uses compareTo method to store resulting positive or negative value in compare
    int compare = node.data.getDate().compareTo(date);

    // if the data of node from where the operation is to be started is greater than
    // the value to be deleted, we recursively go down the left-subtree until we find the
    // value to be deleted
    if (compare > 0) {
      return deleteNodeHelper(node.leftChild, date);
    }
    // else we traverse down the right subtree until we find the value
    else if (compare < 0) {
      return deleteNodeHelper(node.rightChild, date);
      // as soon as the value matches the data to be deleted, i.e. compare == 0,
      // we mark the node as isDeleted
    } else {
      // checks if the element is already deleted
      if (node.data.isDeleted)
        throw new NoSuchElementException("Element has already been deleted from the tree.");
      node.data.isDeleted = true;
    }
    // returns the node marked deleted
    size--;
    return node.data;
  }

  /**
   * method displays the Due date, Assignment name, Course Name, points of the product
   * 
   * @param returned object of class Assignment with details given by the user
   */
  public static void displayGet(Assignment returned) {
    try {
      System.out.println(returned.toString());
    } catch (java.lang.NullPointerException npe) {
      System.out
          .println("The assignment you were trying to find could not be found in the schedule.");
    }

  }
}
