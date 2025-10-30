package edu.ser222.m01_03;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * CompletedOrderedList represents an implementation of an ordered list that builds on
 * CompletedList.
 *
 * @author Pin-Yang Wang, Acuna
 * @version 1.0
 */
public class CompletedOrderedList<T extends Comparable<T>> extends CompletedList<T>
        implements OrderedListADT<T> {

    //TODO: implement this!

    @Override
    public void add(T new_element) {
        if (new_element == null) throw new NullPointerException("Cannot add null");

        DoubleLinearNode newNode = new DoubleLinearNode(new_element);

        // add an element to an empty list
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        }
        // insert at the head (the element is smaller than the original head)
        else if (new_element.compareTo(head.getElement()) <= 0) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        // insert at the tail (the element is bigger than the original tail)
        else if (new_element.compareTo(tail.getElement()) >= 0) {
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        // insert in the middle of the list
        else {
            DoubleLinearNode current = head;
            while (current != null && current.getElement().compareTo(new_element) < 0) {
                current = current.getNext();
            }
            // insert BEFORE current
            DoubleLinearNode prev = current.getPrev();

            newNode.setNext(current);
            newNode.setPrev(prev);
            prev.setNext(newNode);
            current.setPrev(newNode);
        }

        count++;
        modChange++;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "empty";
        }
        return super.toString();
    }

}
