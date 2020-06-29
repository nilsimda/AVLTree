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
        /*if(balance == -1 || balance == 0) {
            if(left == null)
                return 1;
            return left.height() + 1;
        }
        else {
            if(right == null)
                return 1;
            return right.height()+1;
        }*/
        if(left == null && right == null)
            return 1;
        if(left == null)
            return right.height() +1;
        if(right == null)
            return left.height()+1;

        return Math.max(left.height(), right.height()) +1;
    }

    public boolean validAVL() {
        return false;
    }

    public boolean find(int key) {
        // TODO...
        return false;
    }

    // public ... insert(int key) {
    //
    // }

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
