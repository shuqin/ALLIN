package zzz.study.datastructure.queue;

import zzz.study.datastructure.list.MyLinkedList;

public class LinkedQueue<T> implements Queue<T> {

    private MyLinkedList<T> queue = new MyLinkedList<T>();

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public void clear() throws Exception {
        queue.clear();
    }

    public void enqueue(T e) throws Exception {
        queue.addLast(e);
    }

    public T dequeue() throws Exception {
        return queue.getNodeData(queue.removeFirst());
    }

    public T head() {
        try {
            return queue.getNodeData(queue.locateElem(1));
        } catch (Exception e) {
            return null;
        }
    }

    public T tail() {
        try {
            return queue.getNodeData(queue.locateElem(queue.size()));
        } catch (Exception e) {
            return null;
        }
    }

    public String toString() {
        return queue.toString();
    }


}
