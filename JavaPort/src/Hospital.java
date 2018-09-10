package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Hospital{

	public int id;
	public double arrivalRate;
	public ArrayList<Integer> adjacentSurgeryCenters;
	public ArrayList<Double> distances;

	public Hospital(int id,double arrivalRate){
		this.id= id;
		this.arrivalRate=arrivalRate;
                    this.adjacentSurgeryCenters = new ArrayList<Integer>();
		this.distances = new ArrayList<Double>();
	}

}