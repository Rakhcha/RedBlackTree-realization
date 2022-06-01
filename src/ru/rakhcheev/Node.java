package ru.rakhcheev;

public class Node {

    private int value;
    private int color; // 0 - black // 1 - red
    private Node left = null;
    private Node right = null;
    private Node parent = null;

    // ==============

    public Node(int value) {
        this.value = value;
        this.color = 0;
    }

    public void add(int value) {
        if (value > this.getValue()) {
            if (this.getRight() == null) {
                Node newNode = new Node(value);
                newNode.setColor(1);
                newNode.setParent(this);
                this.setRight(newNode);
                newNode.optimization();
            } else this.getRight().add(value);
        } else {
            if (this.getLeft() == null) {
                Node newNode = new Node(value);
                newNode.setColor(1);
                newNode.setParent(this);
                this.setLeft(newNode);
                newNode.optimization();
            } else this.getLeft().add(value);
        }
    }

    private void optimization() {
        if (this.getParent() == null) {
            this.setColor(0);
            return;
        }

        if (this.getColor() != this.getParent().getColor() || (this.getColor() == 0 && this.getParent().getColor() == 0)) return;

        Node uncleNode = this.getParent().getUncle();

        if(uncleNode != null) {
            if(uncleNode.getColor() == this.getParent().getColor()) {
                this.getParent().swapColor();
                this.getParent().getParent().optimization();
                return;
            }
            this.swapSide();
        }
        this.swapSide();
    }

    private void swapColor(){
        Node parent = this.getParent();
        int tempColor = parent.getColor();
        parent.setColor(this.getColor());
        parent.getLeft().setColor(tempColor);
        parent.getRight().setColor(tempColor);
    }

    private void swapSide(){

        if(this.getSide().equals(this.getParent().getSide())){
            if(this.getSide().equals("Right")){
                this.getParent().leftSwap();
                this.getParent().optimization();
                return;
            }
            this.getParent().rightSwap();
            this.getParent().optimization();
            return;
        }


        if(this.getSide().equals("Right")) {
            this.leftSwap();
            this.getParent().getLeft().swapSide();
        }
        else {
            this.rightSwap();
            this.getParent().getRight().swapSide();
        }



    }

    private String getSide(){
        if(this.getParent().getRight() == this) return "Right";
        return "Left";
    }

    private void rightSwap(){
        Node parent = this.getParent();
        Node tempNode = new Node(parent.getValue());

        tempNode.setColor(this.getColor());
        tempNode.setRight(parent.getRight());
        if(parent.getRight() != null) parent.getRight().setParent(tempNode);
        tempNode.setLeft(this.getRight());
        tempNode.setParent(parent);
        if(this.getRight() != null) this.getRight().setParent(tempNode);

        parent.setLeft(this.getLeft());
        if(this.getLeft() != null) this.getLeft().setParent(parent);
        parent.setRight(tempNode);
        parent.setValue(this.getValue());
        //tempNode.afterSwapCheck();
    }

    private void leftSwap(){
        Node parent = this.getParent();
        Node tempNode = new Node(parent.getValue());

        tempNode.setColor(this.getColor());
        tempNode.setLeft(parent.getLeft());
        if(parent.getLeft() != null) parent.getLeft().setParent(tempNode);
        tempNode.setRight(this.getLeft());
        tempNode.setParent(parent);
        if(this.getLeft() != null) this.getLeft().setParent(tempNode);

        parent.setRight(this.getRight());
        if(this.getRight() != null) this.getRight().setParent(parent);
        parent.setLeft(tempNode);
        parent.setValue(this.getValue());
        //tempNode.afterSwapCheck();
    }

    private void afterSwapCheck(){

        if(this.getLeft() != null)
            if(this.getLeft().getColor() == this.getColor()) this.getLeft().optimization();

        if(this.getRight() != null)
            if(this.getRight().getColor() == this.getColor()) this.getRight().optimization();
    }

    private Node getUncle() {
        Node parentNode = this.getParent();
        if (parentNode == null) return null;
        if (parentNode.getLeft() == this) return parentNode.getRight();
        return parentNode.getLeft();
    }


    // ==============

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Node - " + value + (color == 1 ? " red " : " black ") + "(" + " left: " + left + " right: " + right +")";

    }

}
