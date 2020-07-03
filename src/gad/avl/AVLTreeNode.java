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

    public AVLTreeNode insert(int key) {
        basicInsert(this, key);
        int balance = heightHelper(right)-heightHelper(left);
        if(Math.abs(balance) <= 1)
            return this;
        if(balance == +2){
            if(heightHelper(right.right)- heightHelper(right.left) >= 0)
                return leftRotation(this);
            else{
                AVLTreeNode temp = rightRotation(this.right);
                return leftRotation(this);
            }
        }
        else{
            if(heightHelper(left.right) -heightHelper(left.left) <= 0)
                return rightRotation(this);
            else{
                AVLTreeNode temp = leftRotation(this.left);
                return rightRotation(this);
            }
        }
    }

    private AVLTreeNode leftRotation(AVLTreeNode root){
        AVLTreeNode temp = root.right;

        root.right = temp.left;
        temp.left = root;

        updateBalance(temp);

        return temp;
    }

    private AVLTreeNode rightRotation(AVLTreeNode root){
        AVLTreeNode temp = root.left;

        root.left = temp.right;
        temp.right = root;

        updateBalance(temp);

        return temp;
    }

    private void updateBalance(AVLTreeNode node){
        if(node == null)
            return;

        int height = height();

        if(height != balance)
            balance = height();

        updateBalance(node.left);
        updateBalance(node.right);
    }

    private AVLTreeNode basicInsert(AVLTreeNode node, int key){
        if(node == null) {
            node = new AVLTreeNode(key);
            return node;
        }
        if(key < node.key)
            node.left = basicInsert(node.left, key);
        else if(key > node.key)
            node.right = basicInsert(node.right, key);

        return node;
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

    public static void main(String[] args) {
        AVLTreeNode node1 = new AVLTreeNode(2);
        AVLTreeNode node2 = new AVLTreeNode( 3);
        AVLTreeNode node3 = new AVLTreeNode(4);
        //AVLTreeNode node4 = new AVLTreeNode(5);

        node1.setRight(node2);
        node1.setLeft(node3);

        System.out.println(node1.insert(5));
    }
}
