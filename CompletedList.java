package edu.ser222.m01_03;

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * CompletedList represents an implementation of a list.
 *
 * @author Pin-Yang Wang, Acuna
 * @version 1.0
 */
public class CompletedList<T> implements ListADT<T>, Iterable<T> {

    //The following three variables are a suggested start if you are using a list implementation.
    //protected int count;
    //protected int modChange;
    //protected DoubleLinearNode<T> head, tail;

    //TODO: implement this!

    protected int count;
    protected int modChange;
    protected DoubleLinearNode head, tail;

    // the double-linked node and the methods
    protected class DoubleLinearNode {
        private T element;
        private DoubleLinearNode next;
        private DoubleLinearNode prev;

        public DoubleLinearNode(T elem) {
            this.element = elem;
            this.next = null;
            this.prev = null;
        }

        // get the element of the node
        public T getElement() { return element; }

        // set the element of the node
        public void setElement(T elem) { this.element = elem; }

        // get to the next node (toward tail)
        public DoubleLinearNode getNext() { return next; }

        // set the "next" pointer of this node to another specified node
        public void setNext(DoubleLinearNode node) { this.next = node; }

        // get to the previous node (toward head)
        public DoubleLinearNode getPrev() { return prev; }

        // set the "prev" pointer of this node to another specified node
        public void setPrev(DoubleLinearNode node) { this.prev = node; }
    }

    // Constructor
    public CompletedList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
        this.modChange = 0;
    }

    // remove the head element
    @Override
    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("List is empty");
        T result = head.getElement();       // store the element of the original head in "result"
        head = head.getNext();              // set the node "next" to the original head as the new head
        if (head == null) {
            tail = null;                    
        } 
        else {
            head.setPrev(null);             // the head node won't have a "prev" node
        }
        count--;
        modChange++;
        return result;
    }

    // remove the tail element
    @Override
    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException("List is empty");
        T result = tail.getElement();       // store the element of the original tail in "result"
        tail = tail.getPrev();              // set the node "prev" to the original tail as the new tail
        if (tail == null) {
            head = null;
        } 
        else {
            tail.setNext(null);             // the tail node won't have a "next" node
        }
        count--;
        modChange++;
        return result;
    }

    // remove a node with a specific element
    @Override
    public T remove(T specific_element) {
        if (isEmpty()) throw new NoSuchElementException("List is empty");

        DoubleLinearNode current = head;
        while (current != null && !current.getElement().equals(specific_element)) {
            current = current.getNext();
        }
        if (current == null) throw new NoSuchElementException("Element not found");

        // if the node is at the head or tail, use the existing method to remove it
        if (current == head) return removeFirst();
        if (current == tail) return removeLast();

        // if the node is in the middle of the lsit, unlink it from the list, and link the "prev" to the "next"
        DoubleLinearNode p = current.getPrev();
        DoubleLinearNode n = current.getNext();
        p.setNext(n);
        n.setPrev(p);

        count--;
        modChange++;
        return current.getElement();
    }

    // an easy way to call the element of the head
    @Override
    public T first() {
        if (isEmpty()) throw new NoSuchElementException("List is empty");
        return head.getElement();
    }

    // an easy way to call the element of the tail
    @Override
    public T last() {
        if (isEmpty()) throw new NoSuchElementException("List is empty");
        return tail.getElement();
    }

    // check if the "element_finding" exists in the list
    @Override
    public boolean contains(T element_finding) {
        DoubleLinearNode current = head;
        while (current != null) {
            if (current.getElement().equals(element_finding)) return true;
            current = current.getNext();
        }
        return false;
    }

    // is the list empty or not
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    // the size of the list
    @Override
    public int size() {
        return count;
    }

    // a way to iterate the elements in the list
    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    // iterator implementation
    private class ListIterator implements Iterator<T> {
        private DoubleLinearNode current = head;                // iteration start from head
        private final int expectedModChange = modChange;        // fail-fast

        @Override
        public boolean hasNext() {
            if (expectedModChange != modChange)
                throw new ConcurrentModificationException();
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            T current_node_element = current.getElement();
            current = current.getNext();
            return current_node_element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Iterator.remove() not supported");
        }
    }

    // showing the list in string
    @Override
    public String toString() {
        if (isEmpty()) return "empty";

        StringBuilder strbdr = new StringBuilder();
        DoubleLinearNode current = head;

        while (current != null) {
            strbdr.append(current.getElement());
            current = current.getNext();
            if (current != null) strbdr.append(" ");
        }
        return strbdr.toString();
    }
}