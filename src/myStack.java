public interface myStack < T > {
    public int size(); /* returns the size of the stack */
    public boolean isEmpty(); /* checks if empty */
    public T top() throws StackException;
    public T pop() throws StackException;
    public void push(T item) throws StackException;
    public void clear();
    public int search(T item); /* return the location of T*/
}