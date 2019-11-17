/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author Alexander
 */
public class AVLTree 
{
    AVLNode root;
    Stack previouslyInsertedData = new Stack();
    
    public AVLTree()
    {
        root = null;
    }
    
    public void addFromKeyboard()
    {
        createNode(3);
        //searchForSpot(avl);
        
    }
    
    public String[] createNode(int n)
    {
        // CREATE THE NODE 
        String[] message = new String[2];        
        boolean repeatedInput = false;
        
        message[0] = "Node succesfully created!";        
                                
        if (!(previouslyInsertedData.contains(n)))
        {
            AVLNode avl = new AVLNode(n);

            previouslyInsertedData.add(n);
                        
            searchForSpot(avl, root);
        }
        else
        {
            message[0] = "The desired input has already been inserted into the Tree.\nPlease enter another value.";
            repeatedInput = true;            
        }                                                 
        
        message[1] = "" + repeatedInput;                
        
        return message;
    }
    
    // Recursive.
    public void searchForSpot(AVLNode inserted, AVLNode temp/*, int generation*/)
    {        
        /* Empty tree        
         * Inserted node becomes its root.
         */
        if (root == null)
        {
            root = inserted;
            System.out.println("New root defined: " + root.data + "\nRoot Balance Coeff.: " + root.balCoeff);
        }
        
        // Tree contains nodes already.
        else
        {            
            System.out.println("Root: " + root.data + "; Temp: " + temp.data);
            
            // If the node to be inserted contains a smaller number than the one it is comparing itself to:
            if (inserted.data < temp.data)
            {
                // If the compared node does not have a left Child, then the node to be inserted will take that place.
                if (temp.leftChild == null)
                {
                    temp.leftChild = inserted;                    
                    
                    if(root.leftChild != null)
                        System.out.println("Root's leftChild: " + root.leftChild.data);                    
                }
                
                // If the node DOES have a left Child, however, then it will keep looking for another, available spot.
                else
                {
                    searchForSpot(inserted, temp.leftChild);
                }
                
                
                temp.leftChild.parent = temp;
                
                // Recalculating balance coefficient
                temp.balCoeff = temp.balCoeff - 1;
                
                System.out.println("Temp's leftChild: " + temp.leftChild.data + "; Temp's leftChild's parent: " + temp.leftChild.parent.data);
                System.out.println("Temp's balanceCoeff.: " + temp.balCoeff + "; Temp's leftChild's balanceCoeff.: " + temp.leftChild.balCoeff);
            }
            
            /* If the node to be inserted contains a larger number than the compared node:
             * Having the same number isn't an option to be considered in this case, given that the program wouldn't
               create a node in the first place if the data it contains has already been inserted in another, previous
               node.
             */
            else
            {
                if (temp.rightChild == null)
                {
                    temp.rightChild = inserted;
                    
                    if(root.rightChild != null)
                        System.out.println("Root's rightChild: " + root.rightChild.data);                              
                }
                else
                {
                    searchForSpot(inserted, temp.rightChild);
                }
                
                temp.rightChild.parent = temp;

                temp.balCoeff = temp.balCoeff + 1;
                    
                System.out.println("Temp's rightChild: " + temp.rightChild.data + "; Temp's rightChild's parent: " + temp.rightChild.parent.data);
                System.out.println("Temp's balanceCoeff.: " + temp.balCoeff + "; Temp's rightChild's balanceCoeff.: " + temp.rightChild.balCoeff);
                
            }
                System.out.println("Root's balanceCoeff.: " + root.balCoeff);
                
                // Rotations
                // SLR
                if (temp.balCoeff == 2 && temp.rightChild.balCoeff >= 0)               
                    SingleLeftRotation(temp, temp.rightChild);
                
                // SRR
                else if (temp.balCoeff == -2 && temp.leftChild.balCoeff < 0)
                {
                    SingleRightRotation(temp, temp.leftChild);
                }                                                             
            
        }
        
    }
    
    public String print_tree (AVLNode p, int cont)
    {
        int i,j;
        String message = "";
        i = cont;
        
        if(p!= null)
        {
            print_tree (p.leftChild, i+1);            
            System.out.println ();
            
            for (j=1; j < i; j++)
            /*{
                System.out.print(" - ");
                System.out.print(" " + p.data);
            }*/
            System.out.print(" - ");
            System.out.print(" " + p.data);
            
            print_tree (p.rightChild, i+1);
        }
        else
            message = "AVLTree could not be printed, as it is empty.\nPlease add some elements first.";
        
        return message;
    }
    
    private void SingleLeftRotation(AVLNode parent, AVLNode child)
    {
        // Node link reconnections
        /* If the original Child contains its own leftChild, then the Parent will adopt it as its rightChild.           
           Regardless, the Parent becomes the original Child's leftChild.
         */
        if (child.leftChild == null)
            parent.rightChild = null;
        else
            parent.rightChild = child.leftChild;
        
        child.leftChild = parent;
        
        /* If the original Parent contains a parent of its own, then it now becomes the original Child's parent--and in
           turn, the Parent's parent adopts the original Child as its own rightChild.
           If not, then the Child stops having a parent node.
        
         * In the end, the Child becomes the original Parent's parent.
         */
        if (parent.parent != null)
        {
            child.parent = parent.parent;
            parent.parent.rightChild = child;
        }
        else
            child.parent = null;
        
        parent.parent = child;
        
        // Root re-assignment
        if (root == parent)
            root = child;
        
        /* Balance adjustment
         * Taken from the HTML
         */
        int w = parent.balCoeff;       
        
        parent.balCoeff = w - 1 - max(child.balCoeff, 0);
        child.balCoeff = min3(w-2, w + child.balCoeff - 2, child.balCoeff - 1);
        
        child.parent.balCoeff = child.parent.balCoeff - 1;

        
        System.out.println("SLR made. \nRoot: " + root.data + " [" + root.balCoeff + "]" +
                            "\nRoot's leftChild: " + root.leftChild.data + " [" + root.leftChild.balCoeff + "]" +
                            "\nRoot's rightChild: " + root.rightChild.data + " [" + root.rightChild.balCoeff + "]");
        
    }
    
    private void SingleRightRotation(AVLNode parent, AVLNode child)
    {
        // Node link reconnections
        /* If the original Child contains its own leftChild, then the Parent will adopt it as its rightChild.           
           Regardless, the Parent becomes the original Child's leftChild.
         */
        if (child.rightChild == null)
            parent.leftChild = null;
        else
            parent.leftChild = child.rightChild;
        
        child.rightChild = parent;
        
        /* If the original Parent contains a parent of its own, then it now becomes the original Child's parent--and in
           turn, the Parent's parent adopts the original Child as its own rightChild.
           If not, then the Child stops having a parent node.
        
         * In the end, the Child becomes the original Parent's parent.
         */
        if (parent.parent != null)
        {
            child.parent = parent.parent;
            parent.parent.leftChild = child;
        }
        else
            child.parent = null;
        
        parent.parent = child;
        
        // Root re-assignment
        if (root == parent)
            root = child;
        
        /* Balance adjustment
         * Taken from the HTML
         */
        int w = parent.balCoeff;       
        
        parent.balCoeff = w + 1 - min(child.balCoeff, 0);
        child.balCoeff = max(min(w+2, w - child.balCoeff + 2), child.balCoeff + 1);
        
        child.parent.balCoeff = child.parent.balCoeff + 1;

        
        System.out.println("SRR made. \nRoot: " + root.data + " [" + root.balCoeff + "]" +
                            "\nRoot's leftChild: " + root.leftChild.data + " [" + root.leftChild.balCoeff + "]" +
                            "\nRoot's rightChild: " + root.rightChild.data + " [" + root.rightChild.balCoeff + "]");
    }
    
    private int max(int n1, int n2)
    {
        int res;
        
        if (n1 < n2)
            res = n2;
        else
            res = n1;
        
        return res;
    }
    
    private int min(int n1, int n2)
    {
        int res;
        
        if (n1 < n2)
            res = n1;
        else
            res = n2;
        
        return res;
    }
    
    private int min3(int n1, int n2, int n3)
    {
        int res = 0;
        
        if (n1 < n2 && n1 < n3)
            res = n1;
        else if (n2 < n1 && n2 < n3)
            res = n2;
        else if (n3 < n1 && n3 < n2)
            res = n3;
        
        // If two of the parameters are the same number:
        else
        {
            if (n1 < n2)
                res = n1;
            else if (n2 < n3)
                res = n2;
            else if (n3 < n1)
                res = n3;
            
            // If all 3 parameters are the same number:
            else
                res = n1;
        }
        
        return res;
    }
}
