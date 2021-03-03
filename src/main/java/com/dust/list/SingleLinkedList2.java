package com.dust.list;

/**
 * 单链表实现
 * 带头结点
 * 
 * @author Aaron
 *
 */
public class SingleLinkedList2<E> {
    // 头结点，不保存数据
    private Node<E> head;
    private int size = 0;
    
    public SingleLinkedList2() {
        this.head = new Node<E>(null);
    }
    
    public void add(E item) {
        insert(item, size);
    }
    
    /**
     * 指定位置插入元素
     * 
     * @param item
     * @param size2
     */
    public void insert(E item, int size) {
        checkBounds(size, 0, size);
        Node<E> pre = getNode(size - 1);
        Node<E> newNode = new Node<>(item);
        newNode.next = pre.next;
        pre.next = newNode;
        size++;
    }
    
    /**
     * 移除指定位置的元素
     * 
     * @param position
     * @return
     */
    public E remove(int position) {
        checkBounds(position, 0, size - 1);
        Node<E> pre = getNode(position - 1);
        Node<E> delItem = pre.next;
        
        pre.next = delItem.next;
        size--;
        
        return delItem.item;
    }

    /**
     * 获取指定位置节点
     * @param position
     * @return
     */
    private Node<E> getNode(int position) {
        Node<E> cur = head;
        if (cur == null) {
            return null;
        }
        for (int i = 0; i < position; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 校验
     * @param position
     * @param low
     * @param high
     */
    private void checkBounds(int position, int low, int high) {
        if (position > high || position < low) {
            throw new IndexOutOfBoundsException(String.valueOf(position));
        }
    }

    /**
     * 节点类
     * 
     * 静态内部类的作用：只是为了降低包的深度，方便类的使用，
     * 静态内部类适用于包含类当中，但又不依赖与外在的类，不用使用外在类的非静态属性和方法，
     * 只是为了方便管理类结构而定义。在创建静态内部类的时候，不需要外部类对象的引用。
     * 
     * @author Aaron
     *
     * @param <E>
     */
    private static class Node<E> {
        private E item;
        private Node<E> next;
        
        public Node(E item) {
            this.item = item;
            this.next = null;
        }
    }
}
