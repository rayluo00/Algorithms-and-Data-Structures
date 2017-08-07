import java.util.*;
import java.math.*;

public class SortAlgorithms {

    public static ArrayList<Integer> genRandomData () {
        int dataSize;
        Random randomNum = new Random();
        ArrayList<Integer> randomData = new ArrayList<Integer>();

        dataSize = randomNum.nextInt(1001)+500;

        for (int i = 0; i <= dataSize; i++) {
            randomData.add(randomNum.nextInt(1001));
        }

        return randomData;
    }

    /****************************************************** QUICKSORT ******************************************************/
    public static int partition (ArrayList<Integer> data, int low, int high) {
        int pivot = data.get(high);
        int i = (low-1);

        for (int j = low; j <= high-1; j++) {
            if (data.get(j) <= pivot) {
                i++;
                int temp = data.get(i);
                data.set(i, data.get(j));
                data.set(j, temp);
            }
        }

        int temp = data.get(i+1);
        data.set((i+1), data.get(high));
        data.set(high, temp);

        return (i+1);
    }

    public static void quicksort (ArrayList<Integer> data, int low, int high) {
        int point;
        
        if (low < high) {
            point =  partition(data, low, high);
            quicksort(data, low, point-1);
            quicksort(data, point+1, high);
        }
    }

    /****************************************************** MERGESORT ******************************************************/
    public static ArrayList<Integer> merge (ArrayList<Integer> left, ArrayList<Integer> right) {
        ArrayList<Integer> mergeData = new ArrayList<Integer>();

        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.get(0) > right.get(0)) {
                mergeData.add(right.get(0));
                right.remove(0);
            } else {
                mergeData.add(left.get(0));
                left.remove(0);
            }
        }

        while (!left.isEmpty()) {
            mergeData.add(left.get(0));
            left.remove(0);
        }

        while (!right.isEmpty()) {
            mergeData.add(right.get(0));
            right.remove(0);
        }
        
        return mergeData;
    }
    
    public static ArrayList<Integer> mergesort (ArrayList<Integer> data) {
        int dataSize = data.size();
        ArrayList<Integer> left = new ArrayList<Integer>();
        ArrayList<Integer> right = new ArrayList<Integer>();
        
        if (dataSize == 1)
            return data;
        
        for (int i = 0; i < dataSize; i++) {
            if (i < (dataSize/2))
                left.add(data.get(i));
            else
                right.add(data.get(i));
        }

        left = mergesort(left);
        right = mergesort(right);

        return merge(left, right);
    }

    /****************************************************** HEAPSORT ******************************************************/
    public static void heapify (ArrayList<Integer> data, int size, int index) {
        int largest = index;
        int left = (2 * index) + 1;
        int right = (2 * index) + 2;

        if (left < size && data.get(left) > data.get(largest))
            largest = left;

        if (right < size && data.get(right) > data.get(largest))
            largest = right;

        if (largest != index) {
            int temp = data.get(index);
            data.set(index, data.get(largest));
            data.set(largest, temp);

            heapify(data, size, largest);
        }
    }
    
    public static void heapsort (ArrayList<Integer> data) {
        int dataSize = data.size();

        for (int i = dataSize / 2 - 1; i >= 0; i--) {
            heapify(data, dataSize, i);
        }

        for (int i = (dataSize-1); i >= 0; i--) {
            int temp = data.get(0);
            data.set(0, data.get(i));
            data.set(i, temp);
            heapify(data, i, 0);
        }
    }
    
    /****************************************************** INSERT SORT ******************************************************/
    public static void insertsort (ArrayList<Integer> data) {
        int temp;
        int dataSize = data.size();

        for (int i = 1; i < dataSize; i++) {
            for (int j = i; j > 0; j--) {
                if (data.get(j) < data.get(j-1)) {
                    temp = data.get(j);
                    data.set(j, data.get(j-1));
                    data.set((j-1), temp);
                }
            }
        }
    }

    public static long fib (int x) {
        if (x < 2)
            return x;

        return (fib(x-1)+fib(x-2));
    }
    
    public static void main (String[] args) {
        ArrayList<Integer> data = new ArrayList<Integer>();

        /*for (int i = 0; i < 10; i++) {
            System.out.print(fib(i)+", ");
        }
        System.out.println(fib(10));*/
        
        data = genRandomData();
        System.out.println("======================== BEFORE SORT ========================\n\n"+data);
        quicksort(data, 0, (data.size()-1));
        //data = mergesort(data);
        //heapsort(data);
        //insertsort(data);
        System.out.println("======================== AFTER SORT ========================\n\n"+data);
    }
}
