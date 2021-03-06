package gad.avl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class AVLTreeNode {
    private int key;
    private int balance = 0;
    private AVLTreeNode left = null;
    private AVLTreeNode right = null;
    private boolean flag = false;
    private AVLTreeNode node = this;

    public AVLTreeNode(int key) {
        this.key = key;
    }

    public AVLTreeNode getLeft() {
        return left;
    }

    public AVLTreeNode getRight() {
        return right;
    }

    public int getBalance() {
        return balance;
    }

    public int getKey() {
        return key;
    }

    public void setLeft(AVLTreeNode left) {
        this.left = left;
    }

    public void setRight(AVLTreeNode right) {
        this.right = right;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int height() {
        return heightHelper(this);
    }
    public static int heightHelper(AVLTreeNode node){
        if(node == null)
            return 0;
        else
            return Math.max(heightHelper(node.left), heightHelper(node.right)) +1;
    }

    public boolean validAVL() {
        return !hasCycle(this, new HashSet<AVLTreeNode>()) && isBalanced(this) && isSorted(this, null,null);
    }

    private boolean isSorted(AVLTreeNode node, AVLTreeNode left, AVLTreeNode right){
        if(node == null)
            return true;

        if((left != null && node.key < left.key) || (right != null && node.key >= right.key)){
            return false;
        }
        return isSorted(node.left, left, node) && isSorted(node.right, node, right);
    }

    private boolean hasCycle(AVLTreeNode node, HashSet<AVLTreeNode> visited){
        if(visited.contains(node) || flag) {
            flag = true;
            return true;
        }
        if(node == null)
            return false;

        visited.add(node);
        hasCycle(node.left, visited);
        hasCycle(node.right, visited);

        return flag;
    }

    private boolean isBalanced(AVLTreeNode node){
        if(node == null)
            return true;

        int leftHeight = heightHelper(node.left);
        int rightHeight = heightHelper(node.right);

        return Math.abs(rightHeight - leftHeight) <= 1 &&
                node.balance == rightHeight-leftHeight &&
                isBalanced(node.left) && isBalanced(node.right);
    }

    public boolean find(int key) {
        return findHelper(this, key);
    }

    public static boolean findHelper(AVLTreeNode node, int key){
        if(node == null)
            return false;
        if(key == node.key)
            return true;
        if(key > node.key)
            return findHelper(node.right, key);
        else
            return findHelper(node.left, key);
    }

    public AVLTreeNode insert(AVLTreeNode node, int key) {
        if(node == null) { //standard BST insert
            node = new AVLTreeNode(key);
            return node;
        }
        if(key < node.key)
            node.left = insert(node.left, key);
        else {
            node.right = insert(node.right, key);
        }

        node.balance = heightHelper(node.right) - heightHelper(node.left);
        //check if current node is unbalanced and rotate accordincgly
        if(node.balance == +2){
            if(node.right.balance >= 0)
                return leftRotation(node);
            else{
                node.right = rightRotation(node.right);
                return leftRotation(node);
            }
        }
        if (node.balance == -2){
            if(node.left.balance <= 0)
                return rightRotation(node);
            else{
                node.left = leftRotation(node.left);
                return rightRotation(node);
            }
        }
        return node; //return (new) root
    }

    private AVLTreeNode leftRotation(AVLTreeNode root){
        AVLTreeNode temp = root.right;

        root.right = temp.left; //right of root now points to left children of new root (temp)
        temp.left = root;

        root.balance = heightHelper(root.right) - heightHelper(root.left);
        temp.balance = heightHelper(root.right) -heightHelper(root.left);

        return temp;
    }

    private AVLTreeNode rightRotation(AVLTreeNode root){
        AVLTreeNode temp = root.left;

        root.left = temp.right; //left of root now points to right children of new root (temp)
        temp.right = root; //temp is now new root

        root.balance = heightHelper(root.right) - heightHelper(root.left); // update balances
        temp.balance = heightHelper(root.right) -heightHelper(root.left);

        return temp;
    }

    public void updateBalance(AVLTreeNode node){ //corrects balances at the end because they are wrong sometimes
        if(node == null)
            return;

        int height = heightHelper(node.right) - heightHelper(node.left);

        if(height != node.balance)
            node.balance = height;

        updateBalance(node.left);
        updateBalance(node.right);
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @param sb
     *           der StringBuilder für die Ausgabe
     */
    public void dot(StringBuilder sb) {
        dotNode(sb, 0);
    }

    private int dotNode(StringBuilder sb, int idx) {
        sb.append(String.format("\t%d [label=\"%d, b=%d\"];\n", idx, key, balance));
        int next = idx + 1;
        if (left != null)
            next = left.dotLink(sb, idx, next, "l");
        if (right != null)
            next = right.dotLink(sb, idx, next, "r");
        return next;
    }

    private int dotLink(StringBuilder sb, int idx, int next, String label) {
        sb.append(String.format("\t%d -> %d [label=\"%s\"];\n", idx, next, label));
        return dotNode(sb, next);
    };

    @Override
    public String toString() {
        return "Node: "+ key;
    }
}
