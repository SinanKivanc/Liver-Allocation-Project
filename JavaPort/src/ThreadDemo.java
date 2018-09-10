

import java.util.*;
import java.lang.*;
import java.io.*;

class PrimeThread extends Thread  {
    int startValue;
    int currentValue;
    boolean[] numbers;
    public static int numThreads;
    public static int maxNumThreads;    
    PrimeThread(int start, boolean[] numbers){
        this.startValue =start;
        this.currentValue = start;
        this.numbers = numbers;
    }
    public void run(){
        numThreads++;
        if(numThreads > maxNumThreads) {
            maxNumThreads = numThreads;
        }
        for(int i = 2*startValue ; i < numbers.length ; i+=startValue){
            numbers[i]=false;
            currentValue=i;
        }
        
        numThreads--;
            
        
    }
}

public class ThreadDemo {
    
    public static void main(String[] args) throws InterruptedException{
        boolean[] numbers = new boolean[1000000];
        //ArrayList<PrimeThread> threads = new ArrayList<PrimeThread>();
        for(int i = 2 ; i<1000000 ; i++){
            numbers[i]= true;
        }
        for(int i =2 ; i<1000000 ; i++){
            if(numbers[i]){
                while(PrimeThread.numThreads >3){
                    
                }
                PrimeThread thread = new PrimeThread(i,numbers);
                //threads.add(thread);
                thread.start();
                //thread.join();
            }

        }
        System.out.println(PrimeThread.maxNumThreads);
    }
    
}
    
