package com.amazon.buspassmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.buspassSession;
import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Stops;
import com.amazon.buspassmanagement.model.Vehicles;

public class RoutesManagement extends Management {

	public RoutesManagement() {
		// TODO Auto-generated constructor stub
	}
	
	private static  RoutesManagement manageRoutes = new RoutesManagement();
	
	public static RoutesManagement getInstance() {
		return manageRoutes;
	}
	
	public void manageRoute() {
		
		while(true) {
			try {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("1: Create new Route");
				System.out.println("2: Retrieve Route");
				System.out.println("3: Update Route");
				System.out.println("4: Remove Route");
				System.out.println("5: Quit Managing routes");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("Enter Your Choice: ");
				int choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
				boolean quit = false;
				switch(choice) {
				case 1:
					if (createRoute())
						System.out.println("Route Added");
					else 
						System.err.println("Something went wrong");
					break;
					
				case 2:
					retrieveRoute();
					break;
					
				case 3:
					updateRoute();
					break;

				case 4:
					deleteRoute();
					break;
					
				case 5:
					quit = true;
					break;
					
				default:
					
				}
				
				if (quit)
					break;
			} catch (Exception e) {
				System.err.println("Invalid Input"+e);
			}
		} 	
	}
	
	public boolean createRoute() {
		//scanner.nextLine();
		System.out.println("Enter Route title: ");
		routes.title = scanner.nextLine();
		System.out.println("Enter Route Description: ");
		routes.description = scanner.nextLine();
		routes.adminID = buspassSession.user.id;
        
		if (routedao.insert(routes) > 0)
			return true;
		else
			return false;	
	}
	
	public void retrieveRoute() {	
		
		System.out.println("Do you want to see all routes or a particular route?");
		System.out.println("1. All Routes");
		System.out.println("2. Specific Route");
		int choice = Integer.parseInt(scanner.nextLine());
		
		if(choice==1) {
		List <Routes> route = new ArrayList<Routes>();
		route = routedao.retrieve();
			for (Routes routesDetails : route) {
				routes.prettyPrintForAdmin(routesDetails);
			}
		}
		else if (choice == 2){
			System.out.println("Enter the Route Title you want to search");
			routes.title = scanner.nextLine();
			String sql = "SELECT * FROM Routes WHERE title = '"+routes.title+"'";
			List<Routes> getRoutes = new ArrayList<Routes>();
			getRoutes = routedao.retrieve(sql);
			
			if(getRoutes.size()>0) {
				for (Routes route : getRoutes) {
					routes.prettyPrintForAdmin(route);
					manageStops.retrieveStop(route);
					manageVehicle.retrieveVehicleForRoute(route);
				}
			}else {
				System.out.println("Please Enter a valid Route Title or No such route exists.");
			}
		}
		
		else
			System.err.println("Wrong Choice");
	}
	
	public boolean updateRoute() {
		
		retrieveRoute();
		System.out.println("Select the routeID for the Route you want to update: ");
		routes.routeID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
			
			//scanner.nextLine();
			
			System.out.println("Enter Route Title: ");
			String title = scanner.nextLine();
			if(!title.isEmpty()) {
				routes.title = title;
			}
			
			System.out.println("Enter Route Description: ");
			String description = scanner.nextLine();
			if(!description.isEmpty()) {
				routes.description = description;
			}
			
			routes.adminID = buspassSession.user.id;
			
		
		if (routedao.update(routes) > 0)
			return true;
		else
			return false;
	}
	
	
	public void deleteRoute() {
		
		retrieveRoute();
		System.out.println("Enter the routeID for the Route to be deleted: ");
		routes.routeID = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Everyhting will be deleted associated to that Route, including Stops, Vehicles and Bus Passes");
        System.out.println("Delete? \n1. Yes 2. No");
        int choice = Integer.parseInt(scanner.nextLine());
		
        
        if (choice == 1) {
        	String sql = "Select * from Routes where routeID="+routes.routeID;
        	List<Routes> routeDetails = routedao.retrieve(sql);
        	
        	if(routeDetails.size()>0) {
        		sql = "Select * from BusPass where routeId="+routes.routeID;
        		List<BusPass> passDetails = buspassdao.retrieve(sql);
        		
        		if(passDetails.size()>0) {
        			for(BusPass pass:passDetails) {
    					buspassdao.delete(pass);
    				}
        			System.out.println("Bus Passes Deleted");
        		}else {
        			System.out.println("There are no Bus Passes available for this route");
        		}
        		
        		sql = "Select * from Stops where routeID="+routes.routeID;
        		List<Stops> stopDetails = stopdao.retrieve(sql);
        		
        		if(stopDetails.size()>0) {
        			for(Stops stop:stopDetails) {
        				stopdao.delete(stop);
        			}
        			System.out.println("Stops Deleted");
        		}else {
        			System.out.println("There are no Stops available for this route");
        		}
        		
        		sql = "Select * from Vehicles where routeID="+routes.routeID;
        		List<Vehicles> vehicleDetails = vehicledao.retrieve(sql);
        		
        		if(vehicleDetails.size()>0) {
        			for(Vehicles vehicle:vehicleDetails) {
        				vehicledao.delete(vehicle);
        			}
        			System.out.println("Vehicles Deleted");
        		}else {
        			System.out.println("There are no Vehicles available for this route");
        		}
        		
        		int result = routedao.delete(routes);
        		String message = (result > 0) ? "Route Deleted Successfully" : "Deleting Route Failed. Try Again.."; 
    			System.out.println(message);
        		
        	}else {
        		System.err.println("There is no route to display !");
        	}
        	
        }
        else
        	System.out.println("Route Not Deleted");  
	}
	
	public boolean displayRoutes() {
		System.out.println("Enter the Route Title of the Route: ");
		routes.title = scanner.nextLine();
		String sql = "Select * From Routes Where title ='"+routes.title+"';";
		List<Routes> routetoDisplay = new ArrayList<Routes>();
		routetoDisplay = routedao.retrieve(sql);
		
		if(routetoDisplay.size()>0) {
			for (Routes route : routetoDisplay) {
				routes.prettyPrintForUser(route);
			}
			return true;
		} else {
			System.out.println("No Routes to display for this Route Title.");
			return false;
		}	
	}
	
}
