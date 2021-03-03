package com.dust.datastructure;

/**
 * 优先队列
 * 
 * @author Aaron
 *
 * @param <T>
 */
public class PriorityQueue<T> {
    /*
     * 第一个节点
     */
    Node<T> head = null;
    
    /**
     * 按优先级向队列中添加数据
     * 
     * 时间复杂度O(n)
     * 
     * 可利用heap优化
     * 
     * @param value
     * @param priority
     */
    public void push(T value, int priority) {
        if (head == null) {
            Node<T> head = new Node<>(value, priority);
            this.head = head;
            return;
        }
        
        Node<T> newNode = new Node<>(value, priority);
        if (priority > head.priority) {
            newNode.next = head;
            head = newNode;
            return;
        }
        
        Node<T> cur = head;
        // 注意尾节点的处理
        while (cur.next != null && cur.next.priority > priority) {
            cur = cur.next;
        }
        newNode.next = cur.next;
        cur.next = newNode;
    }
    
    public T pop() {
        if (head == null) {
            return null;
        }
        Node<T> temp = head;
        head = head.next;
        return temp.value;
    }
    
    public T peek() {
        if (head == null) {
            return null;
        }
        return head.value;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    /**
     * 内部节点类
     * @author F
     *
     * @param <T>
     */
    private static class Node<T> {
        T value; // 值
        int priority; // 优先级
        Node<T> next; // 下一个节点
        
        public Node(T value, int priority) {
            this.value = value;
            this.priority = priority;
        }
        
    }
}
