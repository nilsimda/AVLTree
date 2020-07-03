package gad.avl;

import java.net.MalformedURLException;
import java.util.HashSet;

public class AVLTreeNode {
    private int key;
    private int balance = 0;
    private AVLTreeNode left = null;
    private AVLTreeNode right = null;

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
        return !hasCycle(this, new HashSet<AVLTreeNode>()) &&isBalanced(this);
    }

    private boolean hasCycle(AVLTreeNode node, HashSet<AVLTreeNode> visited){
        if(node == null)
            return false;
        if(visited.contains(node))
            return true;
        visited.add(node);
        hasCycle(node.left, visited);
        hasCycle(node.right, visited);

        return false;
    }

    private boolean isBalanced(AVLTreeNode node){
        if(node == null)
            return true;

        int leftHeight = heightHelper(node.left);
        int rightHeight = heightHelper(node.right);

        return Math.abs(rightHeight - leftHeight) >= 1 &&
                node.balance == rightHeight-leftHeight &&
                //node.left.key < node.key && node.right.key >= node.key &&
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
        return insertHelper(this, key);
    }

    private AVLTreeNode insertHelper(AVLTreeNode node, int key){
        if(node == null) {
            node = new AVLTreeNode(key);
            return node;
        }
        if(key < node.key)
            node.left = insertHelper(node.left, key);
        else if(key > node.key)
            node.right = insertHelper(node.right, key);

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
}
