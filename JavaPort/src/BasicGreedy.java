package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;

public class BasicGreedy extends Algorithm{
	
	public BasicGreedy( ArrayList<SurgeryCenter> surgeryCenters, ArrayList<Hospital> hospitals){
		super(surgeryCenters,hospitals,"BasicGreedy");
	}

	@Override
	public void processDonation(Event e){
                //System.out.println(e.place);
                Hospital hosp = this.hospitals.get(e.place);
                int bestMeld = -1;
		Patient candidate=null;
		Integer candidateSurgID=0;
		for(Integer surgCenterID : hosp.adjacentSurgeryCenters){
                        //System.out.println(this.surgeryCenters.get(surgCenterID).patientQueue.size()+","+ e.place);
			for(Patient p:  this.surgeryCenters.get(surgCenterID).patientQueue){
				if(bestMeld < p.meldScore){
                                        bestMeld = p.meldScore;
					candidate=p;
					candidateSurgID=surgCenterID;
				}
      
			}
		}
                if(bestMeld != -1){
                    this.surgeryCenters.get(candidateSurgID).removePerson(candidate);
                    candidate.departureTime=e.time;
                    candidate.causeOfDeath=1;
                    this.donations++;
                }

	}
}