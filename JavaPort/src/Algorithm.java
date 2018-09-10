package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Algorithm {

    public ArrayList<SurgeryCenter> surgeryCenters;
    public ArrayList<Hospital> hospitals;
    public HashMap<Integer,Patient> patients;
    public String name;
    public double runDuration;
    public int donations,deaths;

    public Algorithm(ArrayList<SurgeryCenter> surgeryCenters, ArrayList<Hospital> hospitals, String name) {
        this.surgeryCenters = surgeryCenters;
        this.hospitals = hospitals;
        this.patients = new HashMap<Integer,Patient>();
        this.name = name;
    }

    public void run(ArrayList<Event> events) {
        double t0 = System.currentTimeMillis();
        this.patients.clear();
        this.donations=0;
        this.deaths=0;
        for (SurgeryCenter s : this.surgeryCenters) {
            s.patientQueue.clear();
        }

        
        for (Event e : events) {
            this.processEvent(e);
        }
        double runDuration = System.currentTimeMillis() - t0;
        
        
        //System.out.print(runDuration);
        writeResults();
    }

    public void processEvent(Event e) {
        if (e.kind == Event.registration) {
            this.processRegistration(e);
        } else if (e.kind == Event.death) {
            this.processDeath(e);
        } else if (e.kind == Event.donation) {
            this.processDonation(e);
        } else if (e.kind == Event.meldUpdate) {
            this.processUpdate(e);
        }
    }

    public void processRegistration(Event e) {
        Patient p = new Patient(e.personID, (int) Math.random() * 34 + 6, e.time, e.place);
        this.patients.put(e.personID,p);
        //System.out.println(this.surgeryCenters.get(e.place).patientQueue.size());
        this.surgeryCenters.get(e.place).addPerson(p);
    }

    public void processUpdate(Event e) {
        patients.get(e.personID).meldChange();
    }

    public void processDeath(Event e) {
        
        if(patients.get(e.personID).causeOfDeath!=1){
            this.deaths++;
            patients.get(e.personID).departureTime=e.time;
            this.surgeryCenters.get(e.place).removePerson(this.patients.get(e.personID));
            patients.get(e.personID).causeOfDeath=2;
        }
    }

    public void processDonation(Event e) {
        return;

    }

    public void writeResults() {
        System.out.println("RESULTS FOR "+ this.name + ":");
        int totalPeople = patients.size();
        int alive = totalPeople-this.deaths;
        System.out.println("Total People: " + totalPeople);
        System.out.println("Alive: " + alive);
        System.out.println("Deaths: " + this.deaths);
        System.out.println("Donations:"+ this.donations);
        double total=0.0;
        
        for(Patient p : this.patients.values()){
            if(p.causeOfDeath == 2){
                total+=p.departureTime-p.registrationTime;
            }
        }
        double avgLifeTime = total/deaths;
        System.out.println("Average Life Time: " + avgLifeTime);
        
    }

}
