package edu.ser222.m01_03;

import java.util.NoSuchElementException;

/**
 * This program provides an implementation of the Deque interface. Also provides a main that
 * demonstrates it.
 * 
 * @author (your name), Acuna
 * @version (version)
 */


public class CompletedDeque<T> implements Deque<T> {

    //TODO: implement all the methods

    /** Node class for double linked structure */
    private class DoubleLinearNode<T> {
        private T element;
        private DoubleLinearNode<T> next;
        private DoubleLinearNode<T> prev;

        public DoubleLinearNode(T elem) {
            element = elem;
            next = null;
            prev = null;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T elem) {
            element = elem;
        }

        public DoubleLinearNode<T> getNext() {
            return next;
        }

        public void setNext(DoubleLinearNode<T> node) {
            next = node;
        }

        public DoubleLinearNode<T> getPrev() {
            return prev;
        }

        public void setPrev(DoubleLinearNode<T> node) {
            prev = node;
        }
    }

    // instance variables
    private DoubleLinearNode<T> front;
    private DoubleLinearNode<T> rear;
    private int count;

    // constructor
    public CompletedDeque() {
        front = null;
        rear = null;
        count = 0;
    }

    // interface implementation
    @Override
    public void enqueueFront(T element) {
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } 
        else {
            newNode.setNext(front);
            front.setPrev(newNode);
            front = newNode;
        }
        count++;
    }

    @Override
    public void enqueueBack(T element) {
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } 
        else {
            rear.setNext(newNode);
            newNode.setPrev(rear);
            rear = newNode;
        }
        count++;
    }

    @Override
    public T dequeueFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T result = front.getElement();
        front = front.getNext();

        if (front == null) {
            rear = null;
        } 
        else {
            front.setPrev(null);
        }

        count--;
        return result;
    }

    @Override
    public T dequeueBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T result = rear.getElement();
        rear = rear.getPrev();

        if (rear == null) {
            front = null;
        } 
        else {
            rear.setNext(null);
        }

        count--;
        return result;
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return front.getElement();
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return rear.getElement();
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "empty";
        }

        StringBuilder sb = new StringBuilder();
        DoubleLinearNode<T> current = rear;
        while (current != null) {
            sb.append(current.getElement());
            if (current.getPrev() != null) {
                sb.append(" ");
            }
            current = current.getPrev();
        }
        return sb.toString();
    }


    /**
     * Program entry point for deque. 
     * @param args command line arguments
     */    
    public static void main(String[] args) {
        CompletedDeque<Integer> deque = new CompletedDeque<>();

        //standard queue behavior
        deque.enqueueBack(3);
        deque.enqueueBack(7);
        deque.enqueueBack(4);
        deque.dequeueFront();        
        deque.enqueueBack(9);
        deque.enqueueBack(8);
        deque.dequeueFront();
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());   

        //deque features
        System.out.println(deque.dequeueFront());        
        deque.enqueueFront(1);
        deque.enqueueFront(11);                         
        deque.enqueueFront(3);                 
        deque.enqueueFront(5);         
        System.out.println(deque.dequeueBack());
        System.out.println(deque.dequeueBack());        
        System.out.println(deque.last());                
        deque.dequeueFront();
        deque.dequeueFront();        
        System.out.println(deque.first());        
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());            
    }
} 