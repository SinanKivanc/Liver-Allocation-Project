package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;


public class Event implements Comparable<Event> { 

	public  double time;
	public int kind;
	public int place;
	public int personID;

	public static final double meldUpdateRate=1./3;
        public static final int donation=0;
        public static final int registration=1;
        public static final int death=2;
        public static final int meldUpdate=3;


	public Event(double time, int kind, int place, int personID){
		this.time = time;
		this.kind= kind;
		this.place = place;
		this.personID= personID;
	}

	public static ArrayList<Event> createEvents(ArrayList<SurgeryCenter> s, ArrayList<Hospital> h){
		ArrayList<Event> events = new ArrayList<Event>();

		double maxTime = 12.0;

		for(Hospital hosp: h){
			double time = 0.0;
			while(time <= maxTime){
				time-=Math.log(1.0-Math.random())/hosp.arrivalRate;
				events.add(new Event(time, Event.donation, hosp.id, 0));
		                //System.out.println("Adding don event at time " + time);

                        }
		}

		int personID = 0;
		ArrayList<Patient> patients = new ArrayList<Patient>();
		for(SurgeryCenter surg : s){
			double time=0.0;
			while(time<= maxTime){
				time-=Math.log(1.0-Math.random())/surg.arrivalRate;

				int initMeld = (int)(4.0-4.0*(Math.log(Math.random())+Math.log(Math.random())+Math.log(Math.random())+Math.log(Math.random())));
				Patient p = new Patient(personID,initMeld, time,surg.id);
				events.add(new Event(time, Event.registration, surg.id, personID));
				patients.add(p);
                                //System.out.println("Adding reg event");

				boolean alive = true;
				int tempMeld= initMeld;
				double updateTime = time;

				while(alive && updateTime<= maxTime){

					updateTime-=Math.log(1.0-Math.random())/(Event.meldUpdateRate+p.mortalityRate());

					if(Math.random() <= p.mortalityRate()/(p.mortalityRate()+Event.meldUpdateRate)){
                                                //System.out.println("Adding death event");
						events.add(new Event(updateTime, Event.death, surg.id, personID));
						alive= false;
					} else {
						//int meltChange = Patient.meldChange(tempMeld);
						//tempMeld+= meldChange;
                                                //System.out.println("Adding meld event");
						events.add(new Event(updateTime,Event.meldUpdate,surg.id, personID));
					}
				}
				personID++;
			}
		}

                Collections.sort(events);
                return events;
	}
        
        @Override
        public int compareTo(Event a){
            if(this.time> a.time){
                return 1;
            } else if(this.time <a.time) {
                return -1;
            } else {
                return 0;
            }
        }


}

