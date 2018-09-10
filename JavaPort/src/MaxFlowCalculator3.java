// Java program for multi-threaded implementation of Push-relabel
// Modified from http://people.cs.ksu.edu/~wls77/weston/projects/cis598/hong.pdf
package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicIntegerArray;

class MaxFlowCalculator3 {
    
    public int[][] graph;
    public int V;
    public double totalFlow;
    public ArrayList<ArrayList<Integer>> adjList;
    
    public  AtomicIntegerArray heights;
    public  AtomicIntegerArray  netFlows;
    public  AtomicIntegerArray residualGraph;

    public MaxFlowCalculator3(int[][] graph, int s, int t,ArrayList<ArrayList<Integer>>  adjList) throws InterruptedException {
        this.graph1=graph;
        this.adjList =adjList;
        
        this.V = graph.length;
        
        for(int i=1; i <V;i++){
            for(int j=1; j<V; i++){
                this.residualGraph.set(i+(graph.length*j),graph[i][j]);
            }
        }
        for(int i=1; i<V ; i+=V){
            this.residualGraph.set((i*V)-1,graph[0][i]+graph[i][0]);
        }
        for(int i=0; i<V ; i++){
            if(graph[0][i]!=0){
                netFlows.set(i,graph[0][i]);
            }

        }
        
        this.totalFlow = maxFlow();
    }

    public int flow(int s , int t){
        
        return this.graph1[s][t]-this.residualGraph.get(s*V+t);
    }
    public class VertexThread extends Thread {
        
        public MaxFlowCalculator3 obj;
        private int index;
        private int height;
        private int netFlow;
        private int d;
        private int hhat;
        private int hprime;
        private int vhat;        
        
        public VertexThread(MaxFlowCalculator3 obj, int index){
            this.index=index; 
            this.obj=obj;
        }
        
        @Override
        public void run(){
            while(obj.netFlows.get(index) > 0){
                this.netFlow =obj.netFlows.get(index);
                vhat=1;
                hhat= Integer.MAX_VALUE;
                
                ArrayList<Integer> list =this.obj.adjList.get(index);
                for(Integer a :list){
                    if(obj.residualGraph.get(index*graph1.length+a) > 0){
                        hprime=heights.get(a);
                        if(hprime <heights.get(vhat)){
                            vhat =a;
                            hhat=hprime;
                        }
                                
                    }
                }
                
                if(heights.get(index) >hhat){
                    this.d =Math.min(netFlow,obj.residualGraph.get(index*graph1.length+vhat));
                    obj.residualGraph.set(index*graph1.length+vhat,obj.residualGraph.get(index*graph1.length+vhat)-d);
                    obj.residualGraph.set(vhat*graph1.length+index,obj.residualGraph.get(vhat*graph1.length+index)+d);
                    netFlows.set(index,netFlows.get(index)-d);
                    netFlows.set(vhat,netFlows.get(vhat)+d);
                } else {
                    heights.set(index, hhat+1);
                }
                
            }
                
        }
        
    }
        
        
    
    public int maxFlow() throws InterruptedException {
        
        ArrayList<VertexThread> list = new ArrayList<VertexThread>();
        for(int i =1 ; i < V-1; i++){
            VertexThread thread = new VertexThread(this,i);
            list.add(thread);
            thread.start();
        }
        for(VertexThread thread : list){
            
            thread.join();
        }
        
        return netFlows.get(0);
        
    }

}
