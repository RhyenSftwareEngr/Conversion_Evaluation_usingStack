import java.util.ArrayList;

public class LinkedListStack < T > implements myStack < T > {
    private Node < T > top;
    private int numElements = 0;
    private ArrayList < T > arrayList = new ArrayList < > ();

    public class Node < T > {
        private T info;
        private Node < T > link;
        public Node() {}
        public Node(T info, Node < T > link) {
            this.info = info;
            this.link = link;
        }
        public void setInfo(T info) {
            this.info = info;
        }
        public void setLink(Node < T > link) {
            this.link = link;
        }
        public T getInfo() {
            return info;
        }
        public Node < T > getLink() {
            return link;
        }
    }
    //return the number of items in the Stack
    public int size() {
        return (numElements);
    }
    //check to see if the Stack is empty
    public boolean isEmpty() {
        return (top == null);
    }
    //return the top element without removing it from the Stack.
    public T top() throws StackException {
        if (isEmpty())
            throw new StackException("Stack is empty.");
        return top.info;
    }
    //get the top element of the Stack
    public T pop() throws StackException {
        Node < T > temp;
        if (isEmpty()) {
            throw new StackException("Stack underflow.");
        }

        temp = top;
        top = top.getLink();
        numElements--;
        return temp.getInfo();
    }
    //put the element onto the Stack
    public void push(T item) {
        Node < T > newNode = new Node();
        newNode.setInfo(item);
        newNode.setLink(top);
        top = newNode;
        numElements++;
    }

    //clears the Stack
    public void clear() {
        top.setInfo(null);
        top.setLink(null);
        numElements = 0;
    }

    //search the location of an item is in the Stack
    public int search(T item) {
        Node < T > element = top;
        for (int i = 0; i < numElements; i++) {
            if (element.getInfo() == item) {
                return i;
            }
            element = element.getLink();
        }
        return -1;
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        Node < T > element = top;
        for (int i = 0; i < numElements; i++) {
            out.append(element.getInfo()).append(", ");
            element = element.getLink();
        }
        return out.toString();
    }
}