package com.amazon.buspassmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.buspassSession;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Vehicles;

public class VehiclesManagement extends Management{

	public VehiclesManagement() {
		// TODO Auto-generated constructor stub
	}

	private static  VehiclesManagement manageVehicles = new VehiclesManagement();
	
	public static VehiclesManagement getInstance() {
		return manageVehicles;
	}
	
	public void manageVehicle() {
		
		while(true) {
			try {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("1: Add New Vehicle");
				System.out.println("2: Display Vehicle Details");
				System.out.println("3: Update Vehicle Information");
				System.out.println("4: Remove Vehicle");
				System.out.println("5: Quit Managing Vehicles");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("Enter Your Choice: ");
				int choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
				boolean quit = false;
				switch(choice) {
				case 1:
					if (createVehicle())
						System.out.println("Vehicle Added");
					else 
						System.err.println("Something Went wrong");
					break;
					
				case 2:
					retrieveVehicle();
					break;
					
				case 3:
					updateVehicle();
					break;

				case 4:
					deleteVehicle();
					break;
					
				case 5:
					quit = true;
					break;
					
				default:
					System.err.println("Invalid Choice");
				}
				
				if(quit)
					break;
			} catch (Exception e) {
				System.err.println("Invalid Input"+e);
			}
		} 	
	}

	private boolean createVehicle() {
		
		//scanner.nextLine();
		System.out.println("Enter Vehicle Registration Number: ");
		vehicles.regNo = scanner.nextLine();
		System.out.println("Enter Vehicle type: ");
		vehicles.type = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		System.out.println("Enter Filled Seats ");
		vehicles.filledSeats = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		System.out.println("Enter Total Number of Seats: ");
		vehicles.totalSeats = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		//scanner.nextLine();
		System.out.println("Enter Start PickUp Time: ");
		vehicles.startPickUpTime = scanner.nextLine();
		System.out.println("Enter Start DropOff Time: ");
		vehicles.startDropOffTime = scanner.nextLine();
		System.out.println("Enter Vehicle Availability:  0. In Maintainence, 1. Available");
		vehicles.vehicleAvailability = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		System.out.println("Enter Driver ID: ");
		vehicles.driverID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		System.out.println("Enter the Route ID: ");
		vehicles.routeID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		vehicles.adminID = buspassSession.user.id;
	    
		if (vehicledao.insert(vehicles) > 0)
			return true;
		else
			return false;
	}

	private void retrieveVehicle() {
		List <Vehicles> vehicle = new ArrayList<Vehicles>();
		vehicle = vehicledao.retrieve();
		for (Vehicles vehiclesDetail : vehicle) {
			vehicles.prettyPrintForAdmin(vehiclesDetail);
		}
	}

	private boolean updateVehicle() {
		retrieveVehicle();
		System.out.println("Enter Vehicle ID to be updated: ");
		vehicles.vehicleID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		
		//scanner.nextLine();
		System.out.println("Enter Vehicle Registration Number: ");
		vehicles.regNo = scanner.nextLine();
		System.out.println("Enter Vehicle type: ");
		vehicles.type = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		System.out.println("Enter Filled Seats ");
		vehicles.filledSeats = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		System.out.println("Enter Total Number of Seats: ");
		vehicles.totalSeats = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		//scanner.nextLine();
		System.out.println("Enter Start PickUp Time: ");
		vehicles.startPickUpTime = scanner.nextLine();
		System.out.println("Enter Start DropOff Time: ");
		vehicles.startDropOffTime = scanner.nextLine();
		System.out.println("Enter Vehicle Availability:  0. In Maintainence, 1. Available");
		vehicles.vehicleAvailability = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		System.out.println("Enter Driver ID: ");
		vehicles.driverID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		System.out.println("Enter the Route ID: ");
		vehicles.routeID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		vehicles.adminID = buspassSession.user.id;
		
		
		if (vehicledao.update(vehicles) > 0)
			return true;
		else
			return false;
		
	}

	private boolean deleteVehicle() {
		//scanner.nextLine();
		retrieveVehicle();
		System.out.println("Enter Vehicle Registration Number of vehicle you want to delete: ");
		vehicles.regNo = scanner.nextLine();
		
		if (vehicledao.delete(vehicles) > 0)
			return true;
		else
			return false;
	}
	
	public void retrieveVehicle(Routes route) {
		List <Vehicles> vehicle = new ArrayList<Vehicles>();
		String sql = "select * from Vehicles where routeID = '"+route.routeID+"'";
		vehicle = vehicledao.retrieve(sql);
		for (Vehicles vehiclesDetail : vehicle) {
			vehicles.prettyPrintForAdmin(vehiclesDetail);
		}
	}
}
