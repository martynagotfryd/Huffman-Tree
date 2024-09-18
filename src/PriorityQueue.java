import java.util.ArrayList;
import java.util.List;

class PriorityQueue<T extends Comparable<T>> {
    private List<T> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    public void insert(T item) {
        heap.add(item);
        heapifyUp(heap.size() - 1);
    }

    public T delMin() {
        if (heap.size() == 0) {
            return null;
        }
        T item = heap.get(0);
        T lastItem = heap.remove(heap.size() - 1);
        if (heap.size() > 0) {
            heap.set(0, lastItem);
            heapifyDown(0);
        }
        return item;
    }

    public int size() {
        return heap.size();
    }

    private void heapifyUp(int index) {
        T item = heap.get(index);
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap.get(parentIndex);
            if (item.compareTo(parent) >= 0) {
                break;
            }
            heap.set(index, parent);
            index = parentIndex;
        }
        heap.set(index, item);
    }

    private void heapifyDown(int index) {
        int size = heap.size();
        T item = heap.get(index);
        while (index < size / 2) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
            int smallestChildIndex = leftChildIndex;
            if (rightChildIndex < size && heap.get(rightChildIndex).compareTo(heap.get(leftChildIndex)) < 0) {
                smallestChildIndex = rightChildIndex;
            }
            T smallestChild = heap.get(smallestChildIndex);
            if (item.compareTo(smallestChild) <= 0) {
                break;
            }
            heap.set(index, smallestChild);
            index = smallestChildIndex;
        }
        heap.set(index, item);
    }
}
