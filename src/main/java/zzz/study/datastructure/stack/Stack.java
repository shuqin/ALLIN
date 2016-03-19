package zzz.study.datastructure.stack;

import java.util.List;

/**
 * a definition of the services a stack would provides
 */
public interface Stack<T> {

    /**
     * isEmpty: judge whether the given stack is empty
     *
     * @return return true if it's empty, otherwise return false
     */
    public boolean isEmpty();

    /**
     * push: push the given element into the stack
     *
     * @param e the element to be pushed
     */
    public void push(T e);

    /**
     * pop: pop the element in the top of stack
     *
     * @return the element in the top of stack ; if the stack is empty , throws exception.
     */
    public T pop();

    /**
     * peek: get the element in the top of stack
     *
     * @return the element in the top of stack ; if the stack is empty , return null;
     */
    public T peek();

    /**
     * size: get the number of elements in the stack
     *
     * @return the number of elements in the stack
     */
    public int size();

    /**
     * toString: get the string representation of the stack
     *
     * @return a string that show the contents of the stack
     */
    public String toString();

    /**
     * unmodifiedList: get unmodified list of stack data
     */
    public List<T> unmodifiedList();

}
