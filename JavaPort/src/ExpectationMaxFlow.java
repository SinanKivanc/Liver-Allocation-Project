package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpectationMaxFlow extends Algorithm {
    
    public MaxFlowCalculator2 m =null;

    public ExpectationMaxFlow(ArrayList<SurgeryCenter> surgeryCenters, ArrayList<Hospital> hospitals) throws InterruptedException {
        super(surgeryCenters, hospitals, "ExpectationMaxFlow");

        int[][] graph = new int[surgeryCenters.size() + hospitals.size() + 2][surgeryCenters.size() + hospitals.size() + 2];
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>(); 
        double totalHospRate = 0.0;

        //find total rate to normalize each one
        for (Hospital h : hospitals) {
            totalHospRate += h.arrivalRate;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        //set up the source vertex
        for (int i = 0; i < hospitals.size(); i++) {
            graph[0][i + 1] = (int)(hospitals.get(i).arrivalRate * 1073741824.0 / totalHospRate);
            list.add(i+1);
        }
        adjList.add(list);
        //set up hospital vertices
        for (int i = 0; i < hospitals.size(); i++) {
            ArrayList<Integer> list1 = new ArrayList<Integer>();
            for (int j = 0; j < hospitals.get(i).adjacentSurgeryCenters.size(); j++) {
                int surgID = hospitals.get(i).adjacentSurgeryCenters.get(j);
                graph[i + 1][hospitals.size() + surgID + 1] = 1073741824;
                list1.add(hospitals.size()+surgID);
            }
            adjList.add(list1);
        }
        
        //double totalMortRate=0.0;
        //for (SurgeryCenter s: surgeryCenters){
            //totalMortRate+=s.sumRate();
        //}
        //set up last vertex
        for (int i = 0; i < this.surgeryCenters.size(); i++) {
            ArrayList<Integer> list2 = new ArrayList<Integer>();
            list2.add(hospitals.size() + surgeryCenters.size() + 1);
            adjList.add(list2);
        }
        
        //System.out.println("STARTING MAX FLOW SET UP");
        m = new MaxFlowCalculator2(graph, 0, hospitals.size() + surgeryCenters.size() + 1);
        //System.out.println("ENDING MAX FLOW OBJECT");
    }

    @Override
    public void processDonation(Event e) {

        Hospital hosp = this.hospitals.get(e.place);
        if(hosp.adjacentSurgeryCenters.size()==0){
            return;
        }

        double totalMortRate=0.0;
        for (SurgeryCenter s: surgeryCenters){
            totalMortRate+=s.sumRate();
        }
        //set up last vertex
        for (int i = 0; i < this.surgeryCenters.size(); i++) {
            m.graph[i + hospitals.size()+1][m.graph.length - 1] = (int)(surgeryCenters.get(i).sumRate()*1073741824.0 /totalMortRate);
        }
	int[][] newGraph=m.graph;
        ArrayList<ArrayList<Integer>> adjList = m.adjList;

        try {
            m = new MaxFlowCalculator3(newGraph, 0, hospitals.size() + surgeryCenters.size() + 1,adjList);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExpectationMaxFlow.class.getName()).log(Level.SEVERE, null, ex);
        }

        double highestFlow =m.flow(hosp.id+1, hospitals.size() + hosp.adjacentSurgeryCenters.get(0)+1);
        int candidateCenterID=hosp.adjacentSurgeryCenters.get(0);
        for(int surgeryCenterID :hosp.adjacentSurgeryCenters){
            if(highestFlow <m.flow(hosp.id+1, hospitals.size() + surgeryCenterID +1)){
                highestFlow=m.flow(hosp.id+1, hospitals.size() + surgeryCenterID +1);
                candidateCenterID=surgeryCenterID;
            }
        }

        SurgeryCenter candidateCenter = this.surgeryCenters.get(candidateCenterID);


        if (!candidateCenter.patientQueue.isEmpty()) {
		System.out.println(m.flow(hosp.id+1, hospitals.size()+ candidateCenterID+1));
            Patient candidate = candidateCenter.patientQueue.get(0);
            int highestMeld = candidate.meldScore;
            for (Patient p : candidateCenter.patientQueue) {
                if (p.meldScore > highestMeld) {
                    highestMeld = p.meldScore;
                    candidate = p;
                }

            }
                candidateCenter.removePerson(candidate);
                candidate.departureTime = e.time;
                candidate.causeOfDeath = 1;
                this.donations++;

        }
    }
}
    

