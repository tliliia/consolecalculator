package com.lilykmtd;

import java.util.*;

public final class CalculatorCache {
    public static final int DEFAULTCACHESIZE = 10;

    private LinkedList<CacheItem> calcLinkedList;
    private int cacheSize;

    CalculatorCache() {
        this.calcLinkedList = new LinkedList<CacheItem>();
    }

    CalculatorCache(int cacheSize) {
        this.calcLinkedList = new LinkedList<CacheItem>();
        this.cacheSize = cacheSize;
    }

    public void setCacheSize(int size) {
       if (size < this.cacheSize) {
            //resize linkedlist
            while (calcLinkedList.size() < size) {
                calcLinkedList.pollLast();
            }
        }
        this.cacheSize = size;
    }

    public void saveCalculation(String expression, int precision, double result) {
        CacheItem cacheItem = new CacheItem(expression, precision, result);
        if (calcLinkedList.size() >= this.cacheSize)
            calcLinkedList.pollLast();
        calcLinkedList.push(cacheItem);
    }

    public Double getResultForExpressionWithPrecision(String calculation, int precision){
        CacheItem searchItem = new CacheItem(calculation, precision, 0.0);
        int index = calcLinkedList.indexOf(searchItem);
        if (index != -1) {
            CacheItem cachedItem = calcLinkedList.get(index);
            return cachedItem.result;
        }
        return Double.NaN;
    }
}