package com.amazon.buspassmanagement.model;

/*
 * MSSQL:
	create table Routes(
	routeID INT IDENTITY(1,1),
	title NVARCHAR(50) not null,
	description NVARCHAR(100),
	adminID INT,
	createdOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(routeID));
 */



public class Routes {

	// Attributes
	public int routeID;
    public String title;
    public String description;
    public int adminID;
    public String createdOn;
	
	public Routes() {
		
	}
	
	public Routes(int routeID, String title, String description, int adminID, String createdOn) {
		this.routeID = routeID;
		this.title = title;
		this.description = description;
		this.adminID = adminID;
		this.createdOn=createdOn;
	}
	
	public void prettyPrintForAdmin(Routes route) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("RouteID:\t\t"+route.routeID);
		System.out.println("Title:\t\t\t"+route.title);
		System.out.println("Description:\t\t"+route.description);
		System.out.println("AdminID:\t\t"+route.adminID);
		System.out.println("CreatedOn:\t\t"+route.createdOn);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
	}
	
	public void prettyPrintForUser(Routes route) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("RouteID:\t"+route.routeID);
		System.out.println("Title:\t\t"+route.title);
		System.out.println("Description:\t"+route.description);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
	}

	@Override
	public String toString() {
		return "Routes [routeID=" + routeID + ", title=" + title + ", description=" + description + ", adminID="
				+ adminID + ", createdOn=" + createdOn + "]";
	}

	
}
