package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Patient{
	
	public int id,initMeld,meldScore,surgeryCenterID;
        public double registrationTime,departureTime;
        public int causeOfDeath;

	public Patient(int id, int initMeld, double registrationTime, int surgeryCenterID){
		this.id= id;
		this.initMeld= initMeld;
		this.meldScore= initMeld;
		this.registrationTime= registrationTime;
		this.surgeryCenterID= surgeryCenterID;
                this.departureTime=0.0;
                this.causeOfDeath=0;
                
	}


	public double mortalityRate(){
		return Math.log(1.0-Math.exp(-4.3+0.16*(double)meldScore)/(1.0+Math.exp(-4.3+0.16*(double)meldScore)))/(-3.0);
	}

	//TODO
    public void meldChange() {
    	return;
    }
}
