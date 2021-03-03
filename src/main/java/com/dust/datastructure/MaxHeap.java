package com.dust.datastructure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * 最大堆（大根堆）
 * 根节点为最大值
 * 提供插入、取根节点方法
 * 提供堆排序方法
 * 
 * @author Aaron
 *
 */
public class MaxHeap<T extends Comparable<T>> {
    private int capacity;
    private int size = 0;
    private Object[] array;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.array = new Object[capacity];
    }

    /**
     * 插入步骤：
     * 1. 在堆的最后新建一个节点并赋值
     * 2. 新节点与父节点比较
     * 3. 如果新节点的数值比父节点大，调换父子节点的位置
     * 4. 重复2、3步，直到最大堆的特性被满足
     *   
     * 时间复杂度：O(logN)
     * 
     * @param item
     */
    @SuppressWarnings("unchecked")
    public void insert(T item) {
        if (size == capacity) {
             array = Arrays.copyOf(array, capacity * 2);
             capacity = 2 * capacity;
//            throw new IllegalStateException("插入失败，堆中元素数目已达到最大值");
        }
        
        // 添加至数组末尾
        array[size] = item;
        size++;
        // 如果新节点的数值比父节点大，调换父子节点的位置
        int index = size - 1;
        while (index > 0 && item.compareTo((T) array[parent(index)]) > 0) {
            swap(array, index, parent(index));
            index = parent(index);
        }
    }
    
    /**
     * 移除根节点，并返回根节点的值
     * 
     * 1. 移除根节点
     * 2. 将最后位置的节点移置根节点处
     * 3. 将子节点和父节点作比较
     * 4. 如果父节点的值比子节点小，交换父子节点位置
     * 5. 重复3、4，直到最大堆的特性被满足
     * @return
     */
    @SuppressWarnings("unchecked")
    public T extractMax() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T oldRoot = (T) array[0];
        array[0] = array[size - 1];
        size--;
        // 调整堆结构
        heapifyDown(this.array, 0, size);
        
        return oldRoot;
    }
    
    /**
     * 堆排序
     * 
     * 升序，不稳定排序
     * 
     * 时间复杂度O(nlogn)
     * 空间复杂度O(1)
     * 
     * @param array
     * @return
     */
    public T[] heapSort(T[] array) {
        // 构建最大堆
        buildMaxHeap(array);
        
        // 排序，循环n-1次
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, 0, i);
            heapifyDown(array, 0, i);
        }
        
        return array;
    }
    
    /**
     * 构建最大堆
     * @param array
     */
    private void buildMaxHeap(T[] array) {
        // 将传入数组视为完全二叉树，从最后一个非叶子节点开始向前遍历，建立最大堆
        int index = array.length / 2 - 1;
        for (int i = index; i >=0 ; i--) {
            heapifyDown(array, i, array.length);
        }
    }

    /**
     * 以index为根，向下调整最大堆结构
     * 
     * @param array
     * @param index
     * @param len
     */
    @SuppressWarnings("unchecked")
    private void heapifyDown(Object[] array, int index, int len) {
        int largeIndex = index;
        int nonLeafIndex = len / 2 - 1;
        // 此处可直接以最后一个非叶节点的索引为边界，如JDK PriorityQueue中代码
        if (largeIndex <= nonLeafIndex) {
            largeIndex = leftChild(index);
            // 判断左右孩子中较大者
            int rightIndex = rightChild(index);
            if (rightIndex < len) {
                // 如果右孩子更大，重新赋值largeIndex
                if (((T) array[largeIndex]).compareTo((T) array[rightIndex]) < 0) {
                    largeIndex = rightIndex;
                }                
            }
            
            if (((T) array[index]).compareTo((T) array[largeIndex]) < 0) {
                // 将当前节点与子节点中较大者进行交换
                swap(array, index, largeIndex);                
                // 递归
                heapifyDown(array, largeIndex, len);
            }
        }
    }
    
    /**
     * 交换数组中位置为i、j的值
     * @param array2
     * @param i
     * @param j
     */
    private void swap(Object[] array2, int i, int j) {
        Object temp = array2[i];
        array2[i] = array2[j];
        array2[j] = temp;
    }
    
    /**
     * 返回根节点的值
     * 
     * @return
     */
    public T peek() {
        if (size == 0) {
            return null;
        }
        
        return (T) array[0];
    }
    
    /**
     * 获取索引位置为i的元素的父节点的索引位置
     * @param i
     * @return
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }
    
    /**
     * 返回左子节点位置
     * @param i
     * @return
     */
    private int leftChild(int i) {
        return (2 * i) + 1;
    }
    
    /**
     * 返回右子节点位置
     * @param i
     * @return
     */
    private int rightChild(int i) {
        return (2 * i) + 2;
    }
    
    /**
     * 是否有左子节点
     * @param i
     * @return
     */
    private boolean hasLeftChild(int i) {
        return leftChild(i) < size;
    }
    
    /**
     * 是否有右子节点
     * @param i
     * @return
     */
    private boolean hasRightChild(int i) {
        return rightChild(i) < size;
    }
    
    public static void main(String[] args) {
//        Integer[] arr = new Integer[] {23, 51, 17, 18, 98, 27, 25, 78, 17, 100, 78};
//        MaxHeap<Integer> heap = new MaxHeap<>(50);
//        // 测试堆排序
//        heap.heapSort(arr);
//        
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i] + " ");
//        }
        
//        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
//        priorityQueue.add(55);
//        priorityQueue.add(67);
//        priorityQueue.add(55);
//        
//        Iterator<Integer> iterator = priorityQueue.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(priorityQueue.poll());
//        }
        PriorityQueue<Item> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new Item(11));
        priorityQueue.offer(new Item(12));
        Iterator<Item> iterator = priorityQueue.iterator();
        while (iterator.hasNext()) {
            System.out.println(priorityQueue.poll());
        }
    }
    
    static class Item {
        private int a;
        
        public Item(int a) {
            this.a = a;
        }
        
        @Override
        public String toString() {
            return a + "";
        }
    }
}
