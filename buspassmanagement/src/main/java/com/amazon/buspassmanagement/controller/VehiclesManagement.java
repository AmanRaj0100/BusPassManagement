package com.amazon.buspassmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.buspassSession;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Stops;
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
				System.out.println("2: List all registered vehicles");
				System.out.println("3: Update Vehicle Information");
				System.out.println("4: Remove Vehicle");
				System.out.println("5: Quit Managing Vehicles");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("Enter Your Choice: ");
				int choice = Integer.parseInt(scanner.nextLine());
				boolean quit = false;
				switch(choice) {
				case 1:
					if (createVehicle())
						System.out.println("Vehicle Added");
					else 
						System.err.println("Something Went wrong");
					break;
					
				case 2:
					retrieveVehicle(vehicles);
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
	
	private void getDetails(Vehicles vehicles) {
		
		System.out.println("Enter Vehicle Registration Number: ");
		String regNo = scanner.nextLine();
		if(!regNo.isEmpty()) {
			vehicles.regNo = regNo;
		}
		System.out.println("Enter Vehicle type: ");
		String type = scanner.nextLine();
		if(!type.isEmpty()) {
			vehicles.type = Integer.parseInt(type);
		}
		System.out.println("Enter Total Number of Seats: ");
		String totalSeats = scanner.nextLine();
		if(!totalSeats.isEmpty()) {
			vehicles.totalSeats = Integer.parseInt(totalSeats);
		}
		System.out.println("Enter Filled Seats ");
		String filledSeats = scanner.nextLine();
		if(!filledSeats.isEmpty()) {
			vehicles.filledSeats = Integer.parseInt(filledSeats);
		}
		System.out.println("Enter Start PickUp Time: ");
		String startPickUpTime = scanner.nextLine();
		if(!startPickUpTime.isEmpty()) {
			vehicles.startPickUpTime = startPickUpTime;
		}
		System.out.println("Enter Start DropOff Time: ");
		String startDropOffTime = scanner.nextLine();
		if(!startDropOffTime.isEmpty()) {
			vehicles.startDropOffTime = startDropOffTime;
		}
		System.out.println("Enter Vehicle Availability:  0. In Maintainence, 1. Available");
		String vehicleAvailability = scanner.nextLine();
		if(!vehicleAvailability.isEmpty()) {
			vehicles.vehicleAvailability = Integer.parseInt(vehicleAvailability);
		}
		System.out.println("Enter Driver ID: ");
		String driverID = scanner.nextLine();
		if(!driverID.isEmpty()) {
			vehicles.driverID = Integer.parseInt(driverID);
		}
		System.out.println("Enter the Route ID: ");
		String routeID = scanner.nextLine();
		if(!routeID.isEmpty()) {
			vehicles.routeID = Integer.parseInt(routeID);
		}
		
		vehicles.adminID = buspassSession.user.id;
	}
	
	private boolean createVehicle() {
		
		getDetails(vehicles);
	    
		if (vehicledao.insert(vehicles) > 0)
			return true;
		else
			return false;
	}

	private boolean retrieveVehicle(Vehicles vehicles) {
		List <Vehicles> vehicle = new ArrayList<Vehicles>();
		vehicle = vehicledao.retrieve();
		
		if(vehicle.size()>0) {
			for (Vehicles vehiclesDetail : vehicle) {
				vehicles.prettyPrintForAdmin(vehiclesDetail);
			}
			return true;
		} else {
			System.out.println("There are no vehicles available.");
			return false;
		}
	}

	private void updateVehicle() {
		
		if(retrieveVehicle(vehicles)) {
		
			System.out.println("Enter VehicleID of the Vehicle to be Updated: ");
			String vehicleID = scanner.nextLine();
			if(!vehicleID.isEmpty()) {
				vehicles.vehicleID = Integer.parseInt(vehicleID);
			}
			
			getDetails(vehicles);
			
			if (vehicledao.update(vehicles) > 0)
				System.out.println("Vehicles Details Updated Successfully");
			else
				System.out.println("Vehicles Details Cannot be Updated");
		}else {
			System.out.println("Cannot process your request.");
		}
	}

	private void deleteVehicle() {
		
		if(retrieveVehicle(vehicles)) {
			System.out.println("Enter Vehicle Registration Number of vehicle you want to delete: ");
			vehicles.regNo = scanner.nextLine();
			
			if (vehicledao.delete(vehicles) > 0)
				System.out.println("Removed Vehicle Successfully");
			else
				System.out.println("Cannot Remove Vehicle");
		}else {
			System.out.println("Cannot process your request.");
		}
	}
	
	
	public void retrieveVehicleForRoute(Routes route) {
		List <Vehicles> vehicle = new ArrayList<Vehicles>();
		String sql = "select * from Vehicles where routeID = '"+route.routeID+"'";
		vehicle = vehicledao.retrieve(sql);
		
		if(vehicle.size()>0) {
			for (Vehicles vehiclesDetail : vehicle) {
				vehicles.prettyPrintForAdmin(vehiclesDetail);
			}
		}else {
			System.out.println("There are no vehicles available for this Route.");
		}
	}
}
