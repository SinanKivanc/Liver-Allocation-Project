package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;

public class SurgeryCenter{

	public ArrayList<Patient> patientQueue;
	public int id;
	public double arrivalRate;
	public ArrayList<Integer> adjacentHospitals;
	public ArrayList<Double> distances;
	public double normalizedArrivalRate;

	public SurgeryCenter(int id,double arrivalRate){
		this.patientQueue= new ArrayList<Patient>();
		this.id=id;
		this.arrivalRate=arrivalRate;
		this.adjacentHospitals = new ArrayList<Integer>();
		this.distances = new ArrayList<Double>();
	}


	public void addPerson(Patient p){
		this.patientQueue.add(p);
	}

	public void removePerson(Patient p){
		this.patientQueue.remove(p);
	}
        
        public double sumRate(){
            double rateSum=0;
            for(Patient p: this.patientQueue){
                rateSum+=p.mortalityRate();
            }
            //System.out.println(rateSum);
            
            return rateSum;//(this.patientQueue.size());
        }

}
