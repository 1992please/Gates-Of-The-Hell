/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

/**
 *
 * @author joker
 */
public class Node implements Comparable {

    public Comparable data;
    public Node next;
    public Node previous;

    public Node(Comparable data) {
        this.data = data;
        next = null;
        previous = null;
    }

    public Node(Comparable data, Node next) {
        this.data = data;
        this.next = next;
        previous = null;
    }

    public Node(Comparable data, Node previous, Node next) {
        this.data = data;
        this.previous = previous;
        this.next = next;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public int compareTo(Object t) {
        Node t2 = (Node) t;
        return this.data.compareTo(t2.data);
    }
}
