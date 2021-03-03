package com.dust.list;

/**
 * 单链表实现，无头结点
 * 
 * 包括增、删、改，查、逆置操作
 * 
 * @author Aaron
 *
 * @param <E>
 */
public class SingleLinkedList<E> {
    // 第一个节点
    private Node<E> first;
    private int size = 0;
    
    public SingleLinkedList() {

    }
    
    /**
     * 链表末尾添加元素
     * @param e
     */
    public void add(E e) {
        insert(e, size);
    }
    
    /**
     * 删除指定位置的元素
     * @param position
     * @return
     */
    public E remove(int position) {
        checkBounds(position, 0, size - 1);

        E delItem;
        if (position == 0) {
            delItem = first.item;
            first = first.next;
            size--;
        } else {
            Node<E> pre = getNode(position - 1);
            delItem = pre.next.item;
            pre.next = pre.next.next;
            size--;
        }
        return delItem;
    }
    
    /**
     * 在指定位置插入元素
     * @param e
     * @param position
     */
    public void insert(E e, int position) {
        checkBounds(position, 0, size);
        Node<E> newNode = new Node<E>(e);
        
        if (position == 0) {
            if (first != null) {
                newNode.next = first.next;                
            }
            first = newNode;
        } else {
            Node<E> pre = getNode(position - 1);
            newNode.next = pre.next;
            pre.next = newNode;            
        }
        size++;
    }
    
    public Node<E> get(int position) {
        checkBounds(position, 0, size - 1);
        return getNode(position);
    }
    
    /**
     * 就地逆置
     * 头插法
     */
    public void reverse() {
        Node<E> cur = first;
        Node<E> next = null;
        Node<E> temp = null;

        while (cur != null) {
            next = cur.next;
            cur.next = temp;
            temp = cur;
            // 重新设置first
            first = cur;
            cur = next;
        }
    }
    
    /**
     * 就地逆置
     * 三指针
     */
    public void reverse2() {
        Node<E> cur = first;
        Node<E> next = cur.next;
        Node<E> pre = null;
        
        cur.next = null;
        while (next != null) {
            pre = cur;
            cur = next;
            next = next.next;
            cur.next = pre;
        }
        
        first = cur;
    }
    
    /**
     * 返回指定位置的Node元素
     * 小于0则返回first
     * 
     * @param position
     * @return
     */
    private Node<E> getNode(int position) {
        if (first == null) {
            return null;
        }
        Node<E> pointer = first;
        for (int i = 0; i < position; i++) {
            pointer = pointer.next;
        }
        return pointer;
    }
    
    /**
     * 检测是否存在环
     * 利用速度不一致的双指针沿链表前进，如果成环，则一定会相遇
     * 
     * @return
     */
    public Node<E> checkCircle() {
        if (size == 0) {
            return null;
        }
        
        Node<E> faster = first;
        Node<E> slower = first;
        
        while(faster != null && faster.next != null) {
            faster = faster.next.next;
            slower = slower.next;
            if (faster.equals(slower)) {
                return faster;
            }
        }
        
        return null;
    }
    
    /**
     * 定位环的入口
     * 
     * @return
     */
    public Node<E> findCircleEntry() {
        // 相遇点pointA
        Node<E> pointA = checkCircle();
        if (pointA == null) {
            return null;
        }
        
        Node<E> start = first;
        while (start != pointA) {
            pointA = pointA.next;
            start = start.next;
        }
        return start;
    }
    
    /**
     * 计算环长度
     * @return
     */
    public int getCircleLength() {
        if (size == 0) {
            return 0;
        }
        
        Node<E> faster = first;
        Node<E> slower = first;
        
        int length = 0;
        while(faster != null && faster.next != null) {
            faster = faster.next.next;
            slower = slower.next;
            length++;
            if (faster.equals(slower)) {
                break;
            }
        }
        
        return length;
    }

    /**
     * 校验
     * @param position
     */
    private void checkBounds(int position, int low, int high) {
        if (position < low || position > high) {
            throw new IndexOutOfBoundsException(String.valueOf(position));
        }
    }
    
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        
        Node<E> cur = first;
        String strs = "";
        while (cur != null) {
            strs += cur.item.toString() + ",";
            cur = cur.next;
        }
        return "[" + strs.substring(0, strs.length() - 1) + "]";
    }

    /**
     * 内部节点类，表示链表中的元素
     * 
     * @author F
     *
     * @param <E>
     */
    private static class Node<E> {
        private Node<E> next;
        private E item;
        
        Node(E item) {
            this.item = item;
            this.next = null;
        }
    }
    
    public static void main(String[] args) {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        
        System.out.println(list);
        list.reverse();
        System.out.println(list);
        list.add("xx");
        System.out.println(list);
        list.reverse2();
        System.out.println(list);
        list.remove(100);
    }
}
