package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;

/**
 *
 * @author Sinan Kivanc
 */
public class ExpectationGreedy extends Algorithm {

    public ExpectationGreedy(ArrayList<SurgeryCenter> surgeryCenters, ArrayList<Hospital> hospitals) {
        super(surgeryCenters, hospitals, "ExpectationGreedy");
    }

    @Override
    public void processDonation(Event e) {
        //System.out.println(e.place);
        Hospital hosp = this.hospitals.get(e.place);
        double topRate = 0.0;
        int candidateSurgID = 0;
        for (int surgeryCenterID : hosp.adjacentSurgeryCenters) {
            double rate=this.surgeryCenters.get(surgeryCenterID).sumRate();
            if(rate>= topRate){
                topRate= rate;
                candidateSurgID=surgeryCenterID;
            }
        }
        if (topRate != 0.0) {
            Patient candidate = this.surgeryCenters.get(candidateSurgID).patientQueue.get(0);
            int highestMeld=candidate.meldScore;
            for(Patient p: this.surgeryCenters.get(candidateSurgID).patientQueue){
                if(p.meldScore > highestMeld){
                    highestMeld=p.meldScore;
                    candidate=p;
                }
            }
            if(candidate != null){
                //System.out.println("HERE");
                this.surgeryCenters.get(candidateSurgID).removePerson(candidate);
                candidate.departureTime = e.time;
                candidate.causeOfDeath=1;
                this.donations++;
            }
        }

    }
}
