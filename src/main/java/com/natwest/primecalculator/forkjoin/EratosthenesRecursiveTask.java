package com.natwest.primecalculator.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Seive of Eratosthenes RecursiveTask class for acquiring all result primes. It will return a List of discovered primes.
 * This will be used with ForkJoin for one of the concurrent experiments for Seive of Eratosthenes.
 */
public class EratosthenesRecursiveTask extends RecursiveTask<List<Integer>> {

    /**
     * lower index to start from
     */
    private int low;
    /**
     * highest index to search
     */
    private int high;
    /**
     * array with the values to search
     */
    private boolean[] array;

    /**
     * constructor
     * @param low
     * @param high
     * @param array
     */
    public EratosthenesRecursiveTask(int low, int high, boolean[] array) {
        this.low = low;
        this.high = high;
        this.array = array;
    }

    /**
     * compute implementation. divide and conquer.
     */
    @Override
    protected List<Integer> compute() {
        //divide and conquer. in chunks of 1000
        if(high - low <= 1000) {
            List<Integer> primes = new ArrayList<>();

            for(int i = low; i < high; ++i){
                if(array[i]){
                    primes.add(i);
                }
            }
            return primes;
        }else{
            int mid = low + (high - low) / 2;
            EratosthenesRecursiveTask left  = new EratosthenesRecursiveTask(low, mid, array);
            EratosthenesRecursiveTask right = new EratosthenesRecursiveTask(mid, high, array);
            left.fork();
            List<Integer> rightResult = right.compute();
            List<Integer> leftResult  = left.join();

            leftResult.addAll(rightResult);
            return leftResult;
        }
    }
}
