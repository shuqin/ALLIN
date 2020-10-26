package zzz.study.datastructure.stack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * DyStack implements the services of a stack specified by the
 * stack.StackADT interface
 *
 * @author shuqin1984
 *
 */

public class DyStack<T> implements Stack<T> {

    LinkedList<T> ds;        // store the elements of the stack using a variable list.

    /**
     * create a empty stack
     */
    public DyStack() {
        ds = new LinkedList<T>();
    }

    /**
     * judge whether the given stack is empty
     *
     * @return if it's empty, return true, else return false
     *
     */
    public boolean isEmpty() {

        return ds.isEmpty();
    }

    /**
     * remove all the elements in the stack.
     *
     */
    public void  clear() {
        ds.clear();
    }

    /**
     * push the given element into stack
     *
     * @param e the element to be pushed
     *
     */
    public void push(T e) {

        ds.push(e);
    }

    /**
     * pop the element in the top of stack
     *
     * @throws Exception if the stack is empty
     * @return the element in the top of stack
     *
     */
    public T pop()	{

        if (ds.isEmpty())
            throw new RuntimeException("栈空！");
        return ds.pop();

    }

    /**
     * get the element in the top of stack
     *
     * @return the element in the top of stack ; if the stack is empty , return null;
     *
     */
    public T peek()  {

        if (ds.isEmpty())
            return null;
        return ds.peek();
    }

    /**
     * get the number of elements in the stack
     *
     * @return the number of elements in the stack
     *
     */
    public int size() {
        return  ds.size();
    }

    /**
     * traverse the stack
     *
     * @return a string that show the contents of the stack
     *
     */
    public String toString() {

        return ds.toString();
    }

    public List<T> unmodifiedList() {
        return Collections.unmodifiableList(ds);
    }


}



