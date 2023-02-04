package com.amazon.buspassmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.buspassSession;
import com.amazon.buspassmanagement.model.Routes;

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
			scanner.nextLine();
			System.out.println("Enter the Route Title you want to search");
			routes.title = scanner.nextLine();
			String sql = "SELECT * FROM Routes WHERE title = '"+routes.title+"'";
			List<Routes> getRoutes = new ArrayList<Routes>();
			getRoutes = routedao.retrieve(sql);
			for (Routes route : getRoutes) {
				routes.prettyPrintForAdmin(route);
				manageStops.retrieveStop(route);
				manageVehicle.retrieveVehicle(route);
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
	
	public boolean deleteRoute() {
		
		retrieveRoute();
		System.out.println("Select the routeID for the Route you want to delete: ");
		routes.routeID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		
		if (routedao.delete(routes) > 0)
			return true;
		else
			return false;
	}
	
	public void displayRoutes() {
		System.out.println("Enter the Route Title of the Route: ");
		routes.title = scanner.nextLine();
		String sql = "Select * From Routes Where title ='"+routes.title+"';";
		List<Routes> routetoDisplay = new ArrayList<Routes>();
		routetoDisplay = routedao.retrieve(sql);
		
		for (Routes route : routetoDisplay) {
			routes.prettyPrintForUser(route);
		}	
	}
	
}
