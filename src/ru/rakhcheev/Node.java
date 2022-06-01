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
        }
        this.swap();
    }

    private void swapColor(){
        Node parent = this.getParent();
        int tempColor = parent.getColor();
        parent.setColor(this.getColor());
        parent.getLeft().setColor(tempColor);
        parent.getRight().setColor(tempColor);
    }

    private void swap(){

        boolean rightSide = this.getSide().equals(this.getParent().getSide());
        System.out.println(this.value);
        if(this.getSide().equals("Right")){
            if(rightSide) {
                this.getParent().leftSwap();

            }
            else {
                this.leftSwap();
                this.swap();
            }
        } else {
            if(rightSide) this.getParent().rightSwap();
            else {
                this.rightSwap();
                this.swap();
            }
        }
    }



    private void rightSwap(){
        Node rightThisTempNode = this.getLeft(); // null?
        this.setLeft(this.getRight());
        this.setRight(this.getUncle());

        Node parentNode = this.getParent();
        parentNode.setLeft(rightThisTempNode);
        parentNode.setRight(this);

        int value = this.getValue();

        this.setValue(parentNode.getValue());
        parentNode.setValue(value);
    }

    private void leftSwap(){

        Node rightThisTempNode = this.getRight(); // null?
        this.setRight(this.getLeft());
        this.setLeft(this.getUncle());

        Node parentNode = this.getParent();
        parentNode.setRight(rightThisTempNode);
        parentNode.setLeft(this);

        int value = this.getValue();

        this.setValue(parentNode.getValue());
        parentNode.setValue(value);
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

    private String getSide(){
        if(this.getParent().getRight() == this) return "Right";
        return "Left";
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
        if(left != null) left.setParent(this);
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
        if(right != null) right.setParent(this);
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
