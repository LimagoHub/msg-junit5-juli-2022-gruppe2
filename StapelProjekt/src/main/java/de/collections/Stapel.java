package de.collections;

public class Stapel {


    private int [] feld;
    private int index;

    public Stapel() {
        feld = new int [10];
        index = 0;
    }

    public void push(int value) throws StapelException {
        if(isFull()) throw new StapelException("Overflow");
        feld[index++] = value;
    }
    public int pop() throws StapelException {
        if(isEmpty()) throw new StapelException("Underflow");
        return feld[--index];
    }


    public boolean isEmpty () {
        return index <= 0;
    }

    public boolean isFull () {
        return index >= feld.length;
    }

}
