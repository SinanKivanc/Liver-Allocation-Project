/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;

/**
 *
 * @author Sinan Kivanc
 */
public class JavaPort {

    public static ArrayList<SurgeryCenter> s;
    public static ArrayList<Hospital> h;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        //readFiles("surg2hosp.csv", "hosp2surgery.csv");
        readFiles("testSurg.csv", "testHosp.csv");
        ArrayList<Event> events = Event.createEvents(JavaPort.s, JavaPort.h);
        //PrintWriter pw = new PrintWriter(new File("Events.csv"));
        //for(int i =0 ; i < events.size(); i++){
        // pw.println(events.get(i).time + ", " +events.get(i).kind + ", " + events.get(i).place + ", "+ events.get(i).personID);
        //}

        //BasicGreedy test0 = new BasicGreedy(s, h);
        //test0.run(events);

        //ExpectationGreedy test1 = new ExpectationGreedy(s, h);
	//test1.run(events);
        
        //RandomMaxFlow test2 = new RandomMaxFlow(s, h);
        //test2.run(events);
        
        ExpectationMaxFlow test3 = new ExpectationMaxFlow(s, h);
        test3.run(events);

    }

    public static void readFiles(String filename1, String filename2) throws FileNotFoundException {
        JavaPort.s = new ArrayList<SurgeryCenter>();
        JavaPort.h = new ArrayList<Hospital>();
        Scanner sc1 = new Scanner(new File(filename1));
        sc1.useDelimiter(",|\\n");
        while (sc1.hasNextLine()) {
            int surgID = sc1.nextInt();
            SurgeryCenter s = new SurgeryCenter(surgID, Math.random() * 10.0 + 1.0);
            while (sc1.hasNext()) {
                String x = sc1.next();
                if (x.charAt(0) == '-') {
                    sc1.nextLine();
                    break;
                }
                int hospID = Integer.parseInt(x);
                double dist = sc1.nextDouble();
                s.adjacentHospitals.add(hospID);
                s.distances.add(dist);
            }
            JavaPort.s.add(s);

        }
        Scanner sc2 = new Scanner(new File(filename2));
        sc2.useDelimiter(",|\\n");
        while (sc2.hasNextLine()) {
            int hospID = sc2.nextInt();
            Hospital h = new Hospital(hospID, Math.random());
            while (sc2.hasNext()) {
                String x = sc2.next();
                if (x.charAt(0) == '-') {
                    sc2 .nextLine();
                    break;
                }
                int surgID = Integer.parseInt(x);                
                double dist = sc2.nextDouble();
                h.adjacentSurgeryCenters.add(surgID);
                h.distances.add(dist);
            }
            JavaPort.h.add(h);

        }

        return;
    }

}
