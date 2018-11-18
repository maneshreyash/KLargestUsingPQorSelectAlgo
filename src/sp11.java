//import java.util.*;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class sp11 {

    private static int T = 77;

    private static int[] selectPQ(int[] arr, int k){
        PriorityQueue<Integer> pq = new PriorityQueue<>(k);
        int i = 0;

        while(i < k){
            pq.add(arr[i]);
            i++;
        }

        while(i < arr.length){
            if(arr[i] > pq.peek()){
                pq.poll();
                pq.add(arr[i]);
            }
            i++;
        }

        int[] result = new int[k];
        for (int x = k - 1; x >= 0 ; x--) {
            result[x] =  pq.poll();
        }
        return result;
    }

    private static int[] select(int[] arr, int k){
        int[] res = new int[k];
        int i = 0;
        select(arr, 0, arr.length, k);
        for (int x = arr.length - 1; x >= arr.length - k; x--) {
            res[i] = arr[x];
            i++;
        }
        return res;
    }


    private static int partition(int[] A, int p, int r)
    {
        int temp;
        int i = p - 1;
        int x = A[r];
        for(int j = p; j < r; j++){
            if(A[j] <= x){
                i = i + 1;
                temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        temp = A[i + 1];
        A[i + 1] = A[r];
        A[r] = temp;
        return i + 1;
    }

    private static int randomizedPartition(int[] A, int p, int r){
        Random random = new Random();
        int i = p + random.nextInt(r - p + 1);
        int temp = A[i];
        A[i] = A[r];
        A[r] = temp;
        return partition(A, p, r);
    }

    private static int select(int[] arr, int p, int n, int k){
        int left, right;
        if(n < T){
            insertionSort(arr, p, p + n - 1);
        }
        int q = randomizedPartition(arr, p, p + n - 1);
        left = q - p;
        right = n - left - 1;
        if(right >= k){
            return select(arr, q + 1, right, k);
        }else if((right + 1) == k){
            return arr[q];
        }
        return select(arr, p, left, k - right - 1);
    }

    private static void insertionSort(int[] arr, int p, int r){
        int temp, j;
        for (int i = p + 1; i <= r; i++) {
            temp = arr[i];
            j = i - 1;
            while(j >= p && temp < arr[j]){
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = temp;
        }
    }



    public static void main(String[] args) {
        Random random = new Random();
        int[] arr = new int[10];
        int k = 3;
        for(int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(999);
        }

        System.out.println();
        System.out.println("Using Priority Queue:  ");
        Timer timer = new Timer();
        System.out.println(Arrays.toString(selectPQ(arr, k)));
        timer.end();
        System.out.println(timer);


        System.out.println("_________________");
        System.out.println();
        System.out.println("Using Select Algorithm:  ");

        Timer timer1 = new Timer();
        System.out.println(Arrays.toString(select(arr, k)));
        timer1.end();
        System.out.println(timer1);
    }
    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        private Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        private void end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            //return this;
        }

        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime + " milliSec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }
}
