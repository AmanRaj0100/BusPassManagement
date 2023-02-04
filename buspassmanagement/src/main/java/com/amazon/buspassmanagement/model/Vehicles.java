package com.amazon.buspassmanagement.model;

/*MSSQL:
create table Vehicles(
    vehicleID INT IDENTITY(1,1),
    regNo NVARCHAR (20) NOT NULL,
    type INT NOT NULL,
    filledSeats INT,
    totalSeats INT NOT NULL,
    startPickUpTime NVARCHAR (50),
    startDropOffTime NVARCHAR (50),
    vehicleAvailability bit NOT NULL,
    driverID INT NOT NULL,
    routeID INT constraint vehicle_routeID_fk references Routes(routeID),
    adminID INT constraint vehicle_id_fk references Users(id),
    createdOn DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(vehicleID));
*/

public class Vehicles {
	
	// Attributes
		public int vehicleID;
	    public String regNo;
	    public int type;    // 1 -> Bus 0 -> Innova
	    public int filledSeats;
	    public int totalSeats;
	    public String startPickUpTime;
	    public String startDropOffTime;
	    public int vehicleAvailability; // 1 -> Available 0 -> In Maintenance
	    public int driverID;
	    public int routeID;
	    public int adminID;
	    public String createdOn;
		
	    
	    public Vehicles() {
		}

		public Vehicles(int vehicleID, String regNo, int type, int filledSeats, int totalSeats, String startPickUpTime,
				String startDropOffTime, int vehicleAvailability, int driverID, int routeID, int adminID,
				String createdOn) {
			this.vehicleID = vehicleID;
			this.regNo = regNo;
			this.type = type;
			this.filledSeats = filledSeats;
			this.totalSeats = totalSeats;
			this.startPickUpTime = startPickUpTime;
			this.startDropOffTime = startDropOffTime;
			this.vehicleAvailability = vehicleAvailability;
			this.driverID = driverID;
			this.routeID = routeID;
			this.adminID = adminID;
			this.createdOn = createdOn;
		}
		
		public void prettyPrintForAdmin(Vehicles vehicle) {
			String vehicleType ="";
			String vehicleAvailable="";
			
	        if (vehicle.type == 1)
	            vehicleType = "Bus";
	        if (vehicle.type == 2)
	            vehicleType = "Innova";

	        
	        if (vehicle.vehicleAvailability == 1)
	            vehicleAvailable = "Available";
	        else
	            vehicleAvailable = "Under Maintainance";
	        
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("VehicleID:\t\t"+vehicle.vehicleID);
			System.out.println("Vehicle Registration No:"+vehicle.regNo);
			System.out.println("Vehicle Type:\t\t"+vehicleType);
			System.out.println("Filled Seats:\t\t"+vehicle.filledSeats);
			System.out.println("Total Seats:\t\t"+vehicle.totalSeats);
			System.out.println("Start PickUp Time:\t"+vehicle.startPickUpTime);
			System.out.println("Start DropOff Time:\t"+vehicle.startDropOffTime);
			System.out.println("Vehicle Availability:\t"+vehicleAvailable);
			System.out.println("DriverID:\t\t"+vehicle.driverID);
			System.out.println("RouteID:\t\t"+vehicle.routeID);
			System.out.println("AdminID:\t\t"+vehicle.adminID);
			System.out.println("CreatedOn:\t\t"+vehicle.createdOn);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
		
		public void prettyPrintForUser(Vehicles vehicles) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Vehicle RegdNo:\t\t"+vehicles.regNo);
			
			String vehicleType ="";
			if (vehicles.type == 1)
				vehicleType = "Bus";
			if (vehicles.type == 2)
				vehicleType = "Innova";
			System.out.println("Vehicle Type:\t\t"+vehicleType);
			System.out.println("Filled Seats:\t\t"+vehicles.filledSeats);
			System.out.println("Total Seats:\t\t"+vehicles.totalSeats);
			System.out.println("Available Seats:\t"+(vehicles.totalSeats-vehicles.filledSeats));
			System.out.println("Start Pickup Time:\t"+vehicles.startPickUpTime);
			System.out.println("Drop Off Time:\t\t"+vehicles.startDropOffTime);
			
			String vehicleAvailable;
			if (vehicles.vehicleAvailability == 1)
				vehicleAvailable = "Available";
			else
				vehicleAvailable = "Not Available";

			System.out.println("Vehicle Availability:\t"+vehicleAvailable);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~");
		}

		@Override
		public String toString() {
			return "Vehicles [vehicleID=" + vehicleID + ", regNo=" + regNo + ", type=" + type + ", filledSeats="
					+ filledSeats + ", totalSeats=" + totalSeats + ", startPickUpTime=" + startPickUpTime
					+ ", startDropOffTime=" + startDropOffTime + ", vehicleAvailability=" + vehicleAvailability
					+ ", driverID=" + driverID + ", routeId=" + routeID + ", adminID=" + adminID + ", createdOn="
					+ createdOn + "]";
		}
	    
}
