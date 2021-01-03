// --== CS400 File Header Information ==--
// Name: Ayushi Mishra
// Email: mishra37@wisc.edu
// Team: LB
// Role: Backend Developer
// TA: Divyanshu Saxena
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
import java.util.LinkedList;

public class RedBlackTree<T extends Comparable<T>> {
   
  /**
   * 
   * Binary Search Tree implementation with a Node inner class for representing the nodes within a
   * binary search tree. You can use this class' insert method to build a binary search tree, and
   * its toString method to display the level order (breadth first) traversal of values in that
   * tree.
   */

  /**
   * 
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always be maintained.
   * 
   */
  protected static class Node<T> {
    public T data;
    public Node<T> parent; // null for root node
    public Node<T> leftChild;
    public Node<T> rightChild;
    public boolean isBlack;
    
    public Node(T data) {
      this.data = data;
      this.isBlack = false;
    }

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

    /**
     * This method performs a level order traversal of the tree rooted at the current node. The
     * string representations of each data value within this tree are assembled into a comma
     * separated string within brackets (similar to many implementations of java.util.Collection).
     * 
     * @return string containing the values of this tree in level order
     */

    @Override
    public String toString() { // display subtree in order traversal
      String output = "[";
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.leftChild != null)
          q.add(next.leftChild);
        if (next.rightChild != null)
          q.add(next.rightChild);
        output += next.data.toString();
        if (!q.isEmpty())
          output += ", ";
      }
      return output + "]";
    }
  }

  protected Node<T> root; // reference to root node of tree, null when empty

  /**
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the tree already contains data
   * 
   */
  public void insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    Node<T> newNode = new Node<>(data);

    if (root == null) {
      root = newNode;
    } // add first node to an empty tree
    else
      insertHelper(newNode, root); // recursively insert into subtree
      root.isBlack = true;
  }

  /**
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   * 
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descenedent beneath
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references (as
   *                                  defined by Comparable.compareTo())
   */

  private void insertHelper(Node<T> newNode, Node<T> subtree) {
    int compare = newNode.data.compareTo(subtree.data);
    // do not allow duplicate values to be stored within this tree
    if (compare == 0)
      throw new IllegalArgumentException("This RedBlackTree already contains that value.");
    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.leftChild);
    }
    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        enforceRBTreePropertiesAfterInsert(newNode);
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.rightChild);
    }

  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection, like java.util.ArrayList, LinkedList,
   * etc).
   * 
   * @return string containing the values of this tree in level order
   * 
   */
  @Override
  public String toString() {
    return root.toString();
  }

  /**
   * 
   * Performs the rotation operation on the provided nodes within this BST. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation (sometimes
   * called a left-right rotation). When the provided child is a rightChild of the provided parent,
   * this method will perform a left rotation (sometimes called a right-left rotation). When the
   * provided nodes are not related in one of these ways, this method will throw an
   * IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments) of these ways, this method will throw an IllegalArgumentException.
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent
   * 
   *                                  node references are not initially (pre-rotation) related that
   *                                  way
   */

  @SuppressWarnings({"unused", "unchecked"})
  public void rotate(Node<T> child, Node<T> parent) {
    if (!child.parent.equals(parent) || parent == null) {
      throw new IllegalArgumentException("Child and parent are not related.");
    }
    if (child.isLeftChild()) {
      rightRotate(child, parent);
    } else if (!child.isLeftChild()) {
      leftRotate(child, parent);
    }
  }

  /**
   * 
   * When the provided child is a leftChild of the provided parent, this method will perform a right
   * rotation (sometimes called a left-right rotation).
   * 
   * @param child
   * @param parent
   */
  private void rightRotate(Node<T> child, Node<T> parent) {
    Node<T> oldRight;
    Node<T> temp = parent;
    // if no grandparent exists
    if (root.equals(parent)) {
      root = child;
    }
    // when grandparent node is present, we check if parent is rightchild
    // or leftchild of grandparent
    else {
      child.parent = temp.parent;
      if (parent.isLeftChild())
        parent.parent.leftChild = child;
      else
        parent.parent.rightChild = child;
      child.parent = temp.parent;
    }
    // connects parent and child after rotating
    oldRight = child.rightChild;
    parent.leftChild = oldRight;
    child.rightChild = temp;
    temp.parent = child;
  }

  /**
   * When the provided child is a rightChild of the provided parent, this method will perform a left
   * rotation (sometimes called a right-left rotation).
   * 
   * @param child
   * @param parent
   */
  private void leftRotate(Node<T> child, Node<T> parent) {
    Node<T> oldLeft;
    Node<T> temp = parent;
    // if no grandparent exists
    if (root.equals(parent)) {
      root = child;
    }
    // when grandparent node is present, we check if parent is right child
    // or left child of grandparent
    else {
      // connects child and grandparent after rotating
      child.parent = temp.parent;
      if (!parent.isLeftChild())
        parent.parent.rightChild = child;
      else
        parent.parent.leftChild = child;
      child.parent = temp.parent;
    }
    // connects parent and child after rotating
    oldLeft = child.leftChild;
    parent.rightChild = oldLeft;
    child.leftChild = temp;
    temp.parent = child;
  }

  /**    
  *  this method checks for any violation in red black tree properties
  *  and performs respective right or left rotation and re-color operation to maintain 
  *  red-black properties
  *  @param newNode
  */

  private void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
    Node<T> grandparent = null;
    Node<T> sibling = null;
    // if newNode is the root node, then set its color to black
    if (newNode.parent == null) {
      newNode.isBlack = true;
    } else {
      // if parent is not null
      Node<T> parent = newNode.parent;
      if (parent.isBlack) {
        return;
      }
      // parent is red, then checks sibling
      else {
        if (parent != null) {
          if (newNode.parent.parent != null) {
            grandparent = newNode.parent.parent;
            if (parent.parent != null) {
              sibling = parent.isLeftChild() ? grandparent.rightChild : grandparent.leftChild;
            }
          }
        }
        // sibling is NULL or is BLACK
        // handles 4 cases
        if (sibling == null || sibling.isBlack) {
          // case 1: if newNode is the left child of parent and parent is left child of grandparent
          // performs a right rotate
          // case 2: if newNode is the right child of parent and parent is right child of
          // grandparent
          // performs a left rotate
          if ((newNode.isLeftChild() && parent.isLeftChild())
              || (!newNode.isLeftChild() && !parent.isLeftChild())) {
            // calls rotate on parent and grandparent node,
            // which further calls private helper (left and right rotate depending on position
            // of parent)
            rotate(parent, grandparent);
            // performs a re-color after reconstruction
            // sets grandparent to red and parent to black
            grandparent.isBlack = false;
            parent.isBlack = true;
          }
          // case 3: if right child of parent and parent is left child of grandparent
          // then first performs left rotation on child and parent,
          // then performs right rotation on parent and grandparent

          // case 4: if left child of parent and parent is right child of grandparent
          // then first performs right rotation on child and parent,
          // then performs left rotation on parent and grandparent
          else if ((newNode.isLeftChild() && !parent.isLeftChild())
              || (!newNode.isLeftChild() && parent.isLeftChild())) {

            // performs double rotate : left-right or right-left
            rotate(newNode, parent);
            rotate(newNode, grandparent);
            // performs a re-color after reconstruction
            // sets grandparent to red and child to black
            grandparent.isBlack = false;
            newNode.isBlack = true;
          }
        }

        // if sibling is RED we re-color
        else {
          parent.isBlack = true;
          sibling.isBlack = true;
          if (!grandparent.equals(root)) {
            grandparent.isBlack = false;
          }
          // cascading fix
          enforceRBTreePropertiesAfterInsert(grandparent);
        }
      }
    }
  }
}

