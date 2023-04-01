/*=============================================================================
|   Assignment:  HW 01 - Building and managing a BST
|
|       Author:  Priyansh Patel
|     Language:  Java
|
|   To Compile:  javac Hw01.java
|
|   To Execute:  java Hw01 filename
|                     where filename is in the current directory and contains
|                     commands to insert, delete, print.
|
|        Class:  COP3503 - CS II Spring 2021
|   Instructor:  McAlpin
|     Due Date:  February 28
|
+=============================================================================*/

import java.util.*;
import java.io.*;

// node class
class Node
{
  int key;      // holds data
  Node left;    // connects to left node
  Node right;   // connects to right node

  // constructor to create a node
  public Node(int data)
  {
    key = data;
    left = null;
    right = null;
  }
}

public class Hw01
{
  // insert key method (recursive)
  public static Node insert(int key, Node root)
  {
    // base case
    if (root == null)
    {
      // create a new node
      root = new Node(key);
      return root;
    }

    // recursive cases
    if (key >= root.key)
    {
      // insert node on the right
      root.right = insert(key, root.right);
    }
    else
    {
      // insert node on the left
      root.left = insert(key, root.left);
    }

    return root;
  }

  // search key method (recursive)
  public static Node search(int key, Node root) throws Exception
  {
    // base case
    if (root.key == key || root == null)
    {
      System.out.println(root.key + ": found");
      return root;
    }

    // resursive cases
    if (key > root.key)
    {
      // search on the right node
      root.right = search(key, root.right);
    }
    else
    {
      // search on left node
      root.left = search(key, root.left);
    }

    return root;
  }

  // delete key method (recursive)
  public static Node delete(int key, Node root) throws Exception
  {
    if (root == null)
    {
      // key could not be found
      System.out.println("d " + key + ": integer " + key + "NOT found - NOT deleted");
      return null;
    }
    // key is bigger (go to right node)
    else if (key > root.key)
    {
      root.right = delete(key, root.right);
    }
    // key is smaller (go tot left node)
    else if (key < root.key)
    {
      root.left = delete(key, root.left);
    }
    // found key to delete
    else
    {
      // case: no right child but has a left child
      if (root.right == null)
      {
        // left child to root
        root = root.left;
      }

      // case: no left child but has a right child
      else if (root.left == null)
      {
        // right child to root
        root = root.right;
      }

      // case: has no children
      else if (root.right == null && root.left == null)
      {
        root = null;
      }

      // case: has both children
      else
      {
        root.key = getMaxKey(root.left);
        root.left = delete(root.key, root.left);
      }
    }

    return root;
  }

  // find max value in BST
  public static int getMaxKey(Node root)
  {
    // iterate through right child node
    while (root.right != null)
    {
      root = root.right;
    }

    return root.key;
  }

  // print inorder transversal (left, root, right)
  public static void print_inorder(Node root)
  {
    if (root == null)
    {
      return;
    }

    print_inorder(root.left);
    System.out.print(" " + root.key);
    print_inorder(root.right);
  }

  // outputs NID; difficulity rating; duration
  public static void complexityIndicator()
  {
    System.err.println("pr348838;3.0;20.0");
  }

  // data of children nodes
  public static int countChildren(Node root)
  {
    int count;

    // base case
    if (root == null)
    {
      return 0;
    }

    // add left and right nodes
    count = 1 + countChildren(root.left) + countChildren(root.right);

    return count;
  }

  // depth of BST
  public static int getDepth(Node root)
  {
    int left, right;

    // base case
    if (root == null)
    {
      return 0;
    }

    // counts nodes on the left and right
    right = 1 + getDepth(root.right);
    left = 1 + getDepth(root.left);

    // return largest depth count
    if (right > left)
    {
      return right;
    }
    else
    {
      return left;
    }

  }

  // main method
  public static void main(String[] args) throws Exception
  {
    // use scanner to read file contents
    Scanner scnr = new Scanner(new FileInputStream(args[0]));
    ArrayList<String> commands = new ArrayList<>();

    int data;                           // holds key
    String tmp = null;                  // holds each commands from arraylist
    String[] array_strings;             // splits tmp into string[]

    Node root = null;                   // declare node (root)

    String line = scnr.nextLine();       // holds each line read from file

    // adds each command in the array list
    commands.add(line);

    // creates an arraylist of commands
    while (line != null)
    {
      try
      {
        // reads next line
        line = scnr.nextLine();
      }
      catch (Exception e)
      {
        // end of the line has reached
        break;
      }
      // add to ArrayList commands
      commands.add(line);
    }

    scnr.close();       // close the scanner

    System.out.println(args[0] + " contains:");     // Header file

    // prints each command
    for (int i = 0; i < commands.size(); i++)
    {
      System.out.println(commands.get(i));
    }

    for (int i = 0; i < commands.size(); i++)
    {
      tmp = commands.get(i);

      // insert into BST
      if (tmp.charAt(0) == 'i')
      {
        try
        {
          array_strings = tmp.split(" ", 2);

          // convert string to data
          data = Integer.parseInt(array_strings[1]);
        }
        // missing numeric parameter
        catch (Exception e)
        {
          System.out.println("i command:missing numeric parameter");
          continue;
        }

        // insert data into BST
        root = insert(data, root);
      }

      // delete from BST
      else if (tmp.charAt(0) == 'd')
      {
        try
        {
          array_strings = tmp.split(" ", 2);

          // convert string to data
          data = Integer.parseInt(array_strings[1]);
        }
        // missing numeric parameter
        catch (Exception e)
        {
          System.out.println("d command:missing numeric parameter");
          continue;
        }

        // delete data from BST
        try
        {
          root = delete(data, root);
        }
        // could not delete
        catch (Exception e)
        {
          System.out.println("d " + data + ": integer " + data + " NOT deleted");
          continue;
        }
      }

      // search key in BST
      else if (tmp.charAt(0) == 's')
      {
        try
        {
          array_strings = tmp.split(" ", 2);

          // convert string to data
          data = Integer.parseInt(array_strings[1]);
        }
        // missing numeric parameter
        catch (Exception e)
        {
          System.out.println("s command:missing numeric parameter");
          continue;
        }

        // find data in BST
        try
        {
          root = search(data, root);
        }
        // did not find data in BST
        catch (Exception e)
        {
          System.out.println("s " + data + ": integer " + data + " NOT found");
          continue;
        }
      }

      // print BST in inorder tranversal
      else if (tmp.charAt(0) == 'p')
      {
        print_inorder(root);
        System.out.println();
      }

      // quit program
      else if (tmp.charAt(0) == 'q')
      {
        System.out.println("left children:          " + countChildren(root.left));
        System.out.println("left depth:             " + getDepth(root.left));
        System.out.println("right children:         " + countChildren(root.right));
        System.out.println("right depth:            " + getDepth(root.right));
        complexityIndicator();
      }
    }
  }
}


/*=============================================================================
|     I Priyansh Patel (pr348838) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied  or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
