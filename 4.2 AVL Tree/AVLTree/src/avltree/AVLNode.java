/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

/**
 *
 * @author Alexander
 */
public class AVLNode 
{
    int data, balCoeff;
    AVLNode parent, leftChild, rightChild;
    
    public AVLNode(int num)
    {
        data = num;
        balCoeff = 0;
        parent = leftChild = rightChild = null;
    }
    
}
