/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

/**
 *
 * @author joker
 */
public class DLinkedList {

    private Node head;
    private int nElements;

    /**
     * initializes the list. The head is set to null and doesn't point to
     * anything.
     */
    public DLinkedList() {
        head = null;
        nElements = 0;
    }

    /**
     *
     * @return returns the number of elements in the list.
     */
    public int size() {
        return nElements;
    }

    /**
     *
     * @return returns the data found in the head of the list. the head of a
     * linked list is the first node of the list. this method returns null in
     * case of no elements are in the list.
     */
    public Comparable getHead() {
        return head;
    }

    /**
     *
     * @return returns the data found in the tail of the list. the tail of a
     * list is the last node in the last. this method returns null in case of
     * there are no elements in the list.
     */
    public Comparable getTail() {
        Node current = head;
        while (current != null) {
            if (current.next == null) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     *
     * @return returns the tail.
     */
    private Node getTailNode() {
        Node current = head;
        while (current != null) {
            if (current.next == null) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     *
     * @param item is the data required to be added to the beginning of the
     * list. This method inserts item at the beginning of the list.
     */
    public void insertFirst(Comparable item) {
        Node newNode = new Node(item, head);
        if (head != null) {
            head.previous = newNode;
        }
        head = newNode;
        nElements += 1;
    }

    /**
     *
     * @param item inserts item at the end of the list. item is the object
     * (data) to be inserted at the end of the list.
     */
    public void insertLast(Comparable item) {
        if (nElements == 0) {
            head = new Node(item);
            nElements = 1;
            return;
        }
        Node tail = getTailNode();
        tail.next = new Node(item, tail, null);
        nElements += 1;
    }

    /**
     *
     * @return returns true in case of success removal of the first item from
     * the list. false for otherwise cases.
     */
    public boolean removeFirstItem() {
        if (nElements == 0) {
            return false;
        }
        head = head.next;
        if (head != null) {
            head.previous = null;
        }
        nElements -= 1;
        return true;
    }

    /**
     *
     * @return returns true in case of success removal of the last item from the
     * list.
     */
    public boolean removeLastItem() {
        // making sure the list contains elements
        if (nElements == 0) {
            return false;
        } // when the list contains only when element, that's a special case
        else if (nElements == 1) {
            head = null;
        } // looping through the whole list
        else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.previous.next = null;
        }
        nElements -= 1;
        return true;
    }

    /**
     *
     * @param item the data to be used in comparison for removal
     * @return returns true if the method finds a node holding data equal to the
     * supplied item and removes it.
     */
    public boolean removeFirst(Comparable item) {
        // making sure that the list is not empty
        if (nElements == 0) {
            return false;
        } // checking for the head since it's a special case
        else if (nElements == 1) {
            if (head.data.compareTo(item) == 0) {
                head = null;
                nElements -= 1;
                return true;
            } else {
                return false;
            }
        }
        // looping through the whole list
        Node current = head.next;
        while (current != null) {
            if (current.data.compareTo(item) == 0) {
                current.previous.next = current.next;
                nElements -= 1;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * clears the list.
     */
    public void removeAll() {
        head = null;
        nElements = 0;
    }

    /**
     *
     * @param item is the data to be used in comparing the nodes in the list.
     * all nodes holding the same data as item are deleted.
     */
    public void removeAll(Comparable item) {
        // making sure that the list is not empty
        if (nElements == 0) {
            return;
        }
        // checking the head since it's a special case
        while (head.data.compareTo(item) == 0) {
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
            nElements -= 1;
            if (nElements == 0) {
                return;
            }
        }
        // looping through the whole list
        Node current = head.next;
        while (current != null) {
            if (current.data.compareTo(item) == 0) {
                current.previous.next = current.next;
                if (current.next != null) {
                    current.next.previous = current.previous;
                }
                nElements -= 1;
            }
            current = current.next;
        }
    }

    /**
     *
     * @param index is the index of the required element.
     * @return returns the required data found at the supplied index. returns
     * null in case of bad index is supplied (like out of bound)
     */
    public Comparable getItemAt(int index) {
        if (index < 0 || index >= nElements) {
            return null;
        }
        Node current = head;
        while (index > 0) {
            current = current.next;
            index -= 1;
        }
        return current.data;
    }

    /**
     *
     * @param index is the index of the required node.
     * @return returns reference of the required node found at the supplied
     * index.
     */
    private Node getNodeAt(int index) {
        Node current = head;
        while (index > 0) {
            current = current.next;
            index -= 1;
        }
        return current;
    }

    /**
     *
     * @param index is the index of the required node
     * @param item is the data to be written and the specified node.
     * @return returns true in case of success operation.
     */
    public boolean setItemAt(int index, Comparable item) {
        if (index < 0 || index >= nElements) {
            return false;
        }
        Node node = getNodeAt(index);
        node.data = item;
        return true;
    }

    /**
     *
     * @param index is the index required to insert at. All of the nodes
     * starting from this index (before insertion) are shifted.
     * @param item is the data of the new node
     * @return returns true in case of success operation.
     */
    public boolean insertAt(int index, Comparable item) {
        if (index < 0 || index >= nElements) {
            return false;
        }
        Node temp = getNodeAt(index);
        Node newNode = new Node(item, (index - 1 < 0) ? null : temp.previous, temp);
        if (index == 0) { // the head is a special case
            temp.previous = newNode;
            head = newNode;
        } else {
            temp.previous.next = newNode;
            temp.previous = newNode;
        }
        nElements += 1;
        return true;
    }

    /**
     *
     * @param item is the item required to be removed from the list.
     * @return returns true in case of success operation of removing last
     * occurance of node holding the specified data.
     */
    public boolean removeLast(Comparable item) {
        // checking if empty
        if (nElements == 0) {
            return false;
        } // checking the head and the list being of 1 elements. That's a special case.
        else if (nElements == 1) {
            if (head.data.compareTo(item) == 0) {
                head = null;
                nElements = 0;
                return true;
            }
            return false;
        }
        // looping through the list
        Node current = head.next;
        Node requiredNode = null;
        while (current != null) { // the first loop is a must to be executed
            if (current.data.compareTo(item) == 0) {
                requiredNode = current;
            }
            current = current.next;
        }
        if (requiredNode == null) {
            return false;
        }
        requiredNode.previous.next = requiredNode.next;
        nElements -= 1;
        return true;
    }

    /**
     *
     * @param item is the item required to find in the list
     * @return returns true if a node holding the same value of item exists in
     * the list.
     */
    public boolean isMember(Comparable item) {
        Node current = head;
        while (current != null) {
            if (current.data.compareTo(item) == 0) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     *
     * @param item is the item required to get its index.
     * @return returns the index of first occurrence of item in the list. -1 is
     * returned in case of item doesn't exist in the list.
     */
    public int getIndexOf(Comparable item) {
        int index = 0;
        Node current = head;
        while (current != null) {
            if (current.data.compareTo(item) == 0) {
                return index;
            }
            current = current.next;
            index += 1;
        }
        return -1;
    }

    /**
     *
     * @param index is the index required to remove the node at.
     * @return returns true in case of success operation.
     */
    public boolean removeItemAt(int index) {
        // checking the index bounds
        if (index < 0 || index >= nElements) {
            return false;
        } // if index is equal to 0, that's a special case #head
        else if (index == 0) {
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
        } else {
            Node requiredNode = getNodeAt(index);
            requiredNode.previous.next = requiredNode.next;
            if (requiredNode.next != null) {
                requiredNode.next.previous = requiredNode.previous;
            }
        }
        nElements -= 1;
        return true;
    }

    /**
     * swaps the elements located at the indeces provided.
     *
     * @param index1 index of the first item
     * @param index2 index of the second item
     * @return return boolean value indicating the success (true) or failure
     * (false) of the operation.
     */
    public boolean swap(int index1, int index2) {
        if (index1 < 0 || index1 >= nElements
                || index2 < 0 || index2 >= nElements) {
            return false;
        }
        Comparable item = getItemAt(index1);
        setItemAt(index1, getItemAt(index2));
        setItemAt(index2, item);
        return true;
    }

    /**
     * sorts the linked list using bubble sort algorithm
     */
    public void bubbleSort() {
        int loopsRemaining = nElements - 1;
        while (loopsRemaining > 0) {
            int counter = 0;
            boolean swapped = false;
            while (counter < loopsRemaining) {
                if (getItemAt(counter).compareTo(getItemAt(counter + 1)) > 0) {
                    swap(counter, counter + 1);
                    swapped = true;
                }
                counter += 1;
            }
            if (!swapped) {
                break;
            }
            loopsRemaining -= 1;
        }
    }

    /**
     * sorts the list using selection sort algorithm
     */
    public void selectionSort() {
        int index = 0;
        while (index < nElements - 1) {
            int minIndex = index;
            int counter = minIndex + 1;
            while (counter < nElements) {
                if (getItemAt(counter).compareTo(getItemAt(minIndex)) < 0) {
                    minIndex = counter;
                }
                counter += 1;
            }
            if (minIndex != index) {
                swap(index, minIndex);
            }
            index += 1;
        }
    }

    /**
     * sorts the list using insertion sort algorithm
     */
    public void insertionsort() {
        if (nElements < 2) {
            return;
        }
        int counter = 1;
        while (counter < nElements) {
            int indexer = counter - 1;
            while (indexer >= 0
                    && getItemAt(indexer).compareTo(getItemAt(counter)) > 0) {
                indexer -= 1;
            }
            insertAt(indexer + 1, getItemAt(counter));
            removeItemAt(counter + 1);
            counter += 1;
        }
    }

    /**
     *
     * @return returns Comparable value which is the minimum value among the
     * list item. null is returned in case of calling this method with list has
     * no elements.
     */
    public Comparable getMin() {
        return getMin(0, nElements - 1);
    }

    /**
     * This method returns the minimum value (in Comparable type) from the range
     * starting from (including) startIndex to the end of the list.
     *
     * @param startIndex the start index from which minimum value is chosen.
     * @return returns Comparable value which is the minimum value among the
     * list item. null is returned in case of calling this method with list has
     * no elements or index provided was out of range.
     */
    public Comparable getMin(int startIndex) {
        return getMin(startIndex, nElements - 1);
    }

    /**
     * This method returns the minimum value (in Comparable type) found in the
     * range specified by startIndex and endIndex (inclusive).
     *
     * @param startIndex the index from the minimum value is chosen.
     * @param endIndex the ending index for the range from which the minimum
     * value is chosen.
     * @returnreturns Comparable value which is the minimum value among the list
     * item. null is returned in case of failure calls like startIndex >
     * endIndex or the indeces are out of range.
     */
    public Comparable getMin(int startIndex, int endIndex) {
        if (nElements == 0 || startIndex > endIndex || startIndex < 0
                || endIndex >= nElements) {
            return null;
        }
        Comparable item = getItemAt(startIndex);
        startIndex += 1;
        while (startIndex <= endIndex) {
            if (getItemAt(startIndex).compareTo(item) < 0) {
                item = getItemAt(startIndex);
            }
            startIndex += 1;
        }
        return item;
    }

    @Override
    public String toString() {
        String result = new String();
        Node current = head;
        while (current != null) {
            result += current + "\r\n";
            current = current.next;
        }
        return result;
    }
}