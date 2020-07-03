package gad.avl;

import java.net.MalformedURLException;

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
        return isBalanced(this) && correctBalanceToHeight();
    }
    private boolean correctBalanceToHeight(){
        return false;
    }

    public static boolean isBalanced(AVLTreeNode node){
        return false;

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

    public void insert(int key) {
        insertHelper(this, key);
    }

    private void insertHelper(AVLTreeNode node, int key){
        if(node == null) {
            node = new AVLTreeNode(key);
            return;
        }
        if(key < node.key)
            insertHelper(node.left, key);
        else
            insertHelper(node.right, key);
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
