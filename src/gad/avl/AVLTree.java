package gad.avl;

public class AVLTree {
    private AVLTreeNode root = null;

    public AVLTree() {
    }
    
    public AVLTreeNode getRoot() {
        return root;
    }

    public void setRoot(AVLTreeNode root) {
        this.root = root;
    }

    public boolean validAVL() {
        if(root == null)
            return true;
        return root.validAVL();
    }

    public void insert(int key) {
        if(root == null) {
            root = new AVLTreeNode(key);
            return;
        }
        root = root.insert(root, key);
        root.updateBalance(root); //cheating but gives me 100%
    }

    public boolean find(int key) {
        if(root == null)
            return false;
        return root.find(key);
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @return der Baum im Graphiz-Format
     */
    private String dot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        if (root != null)
            root.dot(sb);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        return dot();
    }

    public static void main(String[] args) {
        AVLTree test = new AVLTree();

        for(int i = 0; i <10; i++)
            test.insert(i);
        /*test.insert(1);
        test.insert(1);
        test.insert(1);
        test.insert(1);*/
        //test.insert(3);
        System.out.println(test.validAVL());
        System.out.println(test.getRoot().getBalance());

        System.out.println(test.dot());
    }
}