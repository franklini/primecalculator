package com.natwest.primecalculator.forkjoin;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RecursiveAction;

/**
 * Seive of Eratosthenes RecursiveAction class for acquiring all result primes. This will be used with ForkJoin for one
 * of the concurrent experiments for Seive of Eratosthenes.
 */
public class EratosthenesRecursiveAction extends RecursiveAction {

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
     * Concurrent Map to store the results
     */
    private ConcurrentMap<Integer, Integer> result;

    /**
     * constructor
     * @param low
     * @param high
     * @param array
     * @param result
     */
    public EratosthenesRecursiveAction(int low, int high, boolean[] array, ConcurrentMap<Integer, Integer> result) {
        this.low = low;
        this.high = high;
        this.array = array;
        this.result = result;
    }

    /**
     * compute implementation. divide and conquer.
     */
    @Override
    protected void compute() {
        //divide and conquer. in chunks of 1000
        if(high - low <= 1000) {
            for(int i = low; i < high; ++i){
                if(array[i]){
                    result.put(i, i);
                }
            }
        }else{
            int mid = low + (high - low) / 2;
            EratosthenesRecursiveAction left  = new EratosthenesRecursiveAction(low, mid, array, result);
            EratosthenesRecursiveAction right = new EratosthenesRecursiveAction(mid, high, array, result);
            invokeAll(left, right);
        }
    }
}
