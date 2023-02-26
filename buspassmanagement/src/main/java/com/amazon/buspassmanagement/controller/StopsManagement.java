package com.amazon.buspassmanagement.controller;

import java.util.ArrayList;
import java.util.List;


import com.amazon.buspassmanagement.buspassSession;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Stops;


public class StopsManagement extends Management{

	public StopsManagement() {
		// TODO Auto-generated constructor stub
	}
	
	private static  StopsManagement manageStops = new StopsManagement();
	
	public static StopsManagement getInstance() {
		return manageStops;
	}

	public void manageStop() {
		
		while(true) {
			try {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("1: Create New Stop");
				System.out.println("2: Retrieve Stop");
				System.out.println("3: Update Stop");
				System.out.println("4: Remove Stop");
				System.out.println("5: Quit Managing Stops");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("Enter your choice: ");
				int choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
				boolean quit = false;
				switch(choice) {
				case 1:
					if (createStop())
						System.out.println("Stops Added");
					else 
						System.err.println("Something Went Wrong");
					break;
					
				case 2:
					displayStop();
					break;
					
				case 3:
					updateStop();
					break;

				case 4:
					deleteStop();
					break;
					
				case 5:
					quit = true;
					break;
					
				default:
					System.err.println("Invalid Choice");
				}
				
				if (quit)
					break;
			} catch (Exception e) {
				System.err.println("Invalid Input"+e);
			}
		} 	
	}

	private boolean createStop() {
		RoutesManagement manageRoutes = RoutesManagement.getInstance();
		manageRoutes.retrieveRoute();
		System.out.println("Select the routeID of the Route for which you want to add Stops: ");
		stops.routeID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		stops.adminID = buspassSession.user.id;
		int choice = 0;
		int sequenceOrder =0;
		boolean inserted = false;
		
		while(true) {
			//scanner.nextLine();
			System.out.println("Enter Stop Address: ");
			stops.address = scanner.nextLine();
			stops.sequenceOrder=++sequenceOrder;
			
			System.out.println("Are you done adding Stops? Y->1 / N->0 ");
			choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
			if (stopdao.insert(stops) > 0)
				inserted = true;
			else
				inserted = false;
			if(choice==1)
				break;
		}
		return inserted;
	}
	
	public void retrieveStop(Routes route) {
		String sql = "SELECT * FROM Stops WHERE routeID = '"+route.routeID+"'";
		List <Stops> stop = new ArrayList<Stops>();
		stop = stopdao.retrieve(sql);
		if(stop.size()>0) {
			for (Stops stopsDetails :stop) {
				stops.prettyPrintForAdmin(stopsDetails);
			}
		}else {
			System.out.println("There are no stops available for this Route.");
		}
	}
	
	private void updateStop() {
		System.out.println("Do you want update all ths stops of a route or change a particular stop");
		System.out.println("1. for All Stops \n2. for Changing Particular Stop");
		int choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		displayStop();
		if (choice == 1) {
			stopdao.delete(stops);
			createStop();
		}
		else {
			System.out.println("Enter Stop ID of the Stop to be updated:");
			stops.stopID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
			List <Stops> stop = new ArrayList<Stops>();
			int id = stops.stopID -1;
			String sql="Select * from Stops where stopID = " +id;
			stop = stopdao.retrieve(sql);
			for (Stops stopsDetails : stop) {
				stops.sequenceOrder = stopsDetails.sequenceOrder + 1;
			}
			System.out.println("Enter the Stop Address: ");
			//scanner.nextLine();
			stops.address = scanner.nextLine();
			stops.adminID = buspassSession.user.id;
			stopdao.update(stops);
		}
	}
	
	private void deleteStop() {
		
		if(displayStop()) {
			System.out.println("Select the routeID of the Route for which you want to delete all of its Stops: ");
			stops.routeID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
			
			if (stopdao.delete(stops) > 0)
				System.out.println("Stops Deleted Successfully");
			else
				System.out.println("Stops Cannot be Deleted");
		}
		else {
			System.out.println("Cannot process your request");
		}
	}
	
	
	public boolean displayStop() {
        
        System.out.println("Enter the Route ID for the stops you want to search");
        stops.routeID = Integer.parseInt(scanner.nextLine());
        String sql = "SELECT * FROM Stops WHERE routeID = '"+stops.routeID+"'";
        List <Stops> getStop =  new ArrayList<Stops>();
        getStop = stopdao.retrieve(sql);
        if(getStop.size()>0) {
        	System.out.println("stopID\taddress\tsequenceOrder\trouteID\tadminID\tcreatedOn");
            for (Stops stops : getStop) {
                System.out.println(stops.stopID+ "\t" +stops.address+ "\t" +stops.sequenceOrder+ "\t" +stops.routeID+ "\t" +stops.adminID+ "\t" +stops.createdOn);
            }
            return true;
        } else {
        	System.out.println("No Stops exists for this particular Route");
        	return false;
        }   
    }
}
