package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;

public class RandomMaxFlow extends Algorithm {

    public MaxFlowCalculator m = null;

    public RandomMaxFlow(ArrayList<SurgeryCenter> surgeryCenters, ArrayList<Hospital> hospitals) {
        super(surgeryCenters, hospitals, "RandomMaxFlow");

        double[][] graph = new double[surgeryCenters.size() + hospitals.size() + 2][surgeryCenters.size() + hospitals.size() + 2];
        double totalHospRate = 0.0;
        //System.out.println("STARTING GRAPH SET UP");
        //find total rate to normalize each one
        for (Hospital h : hospitals) {
            totalHospRate += h.arrivalRate;
        }
        //set up the source vertex
        for (int i = 0; i < hospitals.size(); i++) {
            graph[0][i + 1] = hospitals.get(i).arrivalRate / totalHospRate;
        }
        //set up hospital vertices
        for (int i = 0; i < hospitals.size(); i++) {
            for (int j = 0; j < hospitals.get(i).adjacentSurgeryCenters.size(); j++) {
                int surgID = hospitals.get(i).adjacentSurgeryCenters.get(j);
                graph[i + 1][hospitals.size() + surgID + 1] = 1.0;
            }
        }

        double totalSurgRate = 0.0;
        //find total rate to normalize each one
        for (SurgeryCenter s : surgeryCenters) {
            totalSurgRate += s.arrivalRate;
        }
        //set up last vertex
        for (int i = 0; i < this.surgeryCenters.size(); i++) {
            graph[i + hospitals.size()+1][graph.length - 1] = surgeryCenters.get(i).arrivalRate / totalSurgRate;
        }
        
        //System.out.println("STARTING MAX FLOW SET UP");
        m = new MaxFlowCalculator(graph, 0, hospitals.size() + surgeryCenters.size() + 1);
        //System.out.println("ENDING MAX FLOW OBJECT");
    }

    @Override
    public void processDonation(Event e) {
        // use m.flow() to appropriately flip coins to assign livers.

        Hospital hosp = this.hospitals.get(e.place);
        if(hosp.adjacentSurgeryCenters.size()==0){
            return;
        }
        double rand = Math.random();
        int currentEdgeNo = -1;

        while (rand >= 0.0) {
            currentEdgeNo++;
            rand -= m.flow(hosp.id+1, hospitals.size()+ hosp.adjacentSurgeryCenters.get(currentEdgeNo)+1) / m.flow(0, hosp.id + 1);
            
        }

        SurgeryCenter candidateCenter = this.surgeryCenters.get(hosp.adjacentSurgeryCenters.get(currentEdgeNo));
        for (int i = 0; i < hosp.adjacentSurgeryCenters.size(); i++) {
            if (!candidateCenter.patientQueue.isEmpty()) {
                break;
            }
            candidateCenter = this.surgeryCenters.get(hosp.adjacentSurgeryCenters.get((currentEdgeNo + i) % hosp.adjacentSurgeryCenters.size()));
        }
        if (!candidateCenter.patientQueue.isEmpty()) {
            //System.out.println("DONATION HERE");
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
