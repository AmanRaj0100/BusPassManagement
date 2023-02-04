package com.amazon.buspassmanagement.model;

/*MSSQL:
create table BusPass(
	buspassID INT IDENTITY(1,1),
	requestedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	approvedRejectedOn DATETIME,
	validTill DATETIME,
	status INT DEFAULT 1,
	userID INT constraint buspass_id_fk references Users(id),
	routeID INT constraint buspass_routeID_fk references Routes(routeID),
	createdOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(buspassID));
*/

import java.util.Scanner;

public class BusPass {

	public int buspassID;
	public String requestedOn;
	public String approvedRejectedOn;
	public String validTill;
	public int status;
	public int userID;
	public int routeID;
	public String createdOn;
	
	
	public BusPass() {
	}


	public BusPass(int buspassID, String requestedOn, String approvedRejectedOn, String validTill, int status,
			int userID, int routeID, String createdOn) {
		this.buspassID = buspassID;
		this.requestedOn = requestedOn;
		this.approvedRejectedOn = approvedRejectedOn;
		this.validTill = validTill;
		this.status = status;
		this.userID = userID;
		this.routeID = routeID;
		this.createdOn = createdOn;
	}
	
	public void getDetails(boolean updateMode) {
		
		Scanner scanner = new Scanner(System.in);
				
		System.out.println("Capturing Bus Pass Details....");
		
		System.out.println("Enter Route ID to apply for Bus Pass:");
		routeID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		
		//scanner.close();
		
	}
	
	public void prettyPrint() {
		System.out.println("~~~~~~~~~~~~~~~~~~~BusPass~~~~~~~~~~~~~~~~~~~");
		System.out.println("BusPass ID:\t"+buspassID);
		System.out.println("Updated On:\t"+approvedRejectedOn);
		System.out.println("Valid Till:\t"+validTill);
		
		String statusText = "";
		
		if(status == 1) {
			statusText = "Requested";
		}else if (status == 2) {
			statusText = "Approved";
		}else if (status == 3) {
			statusText = "Canceled";
		}else {
			statusText = "Suspended";
		}
		
		System.out.println("Status:\t\t"+statusText);
		System.out.println("User ID:\t"+userID);
		System.out.println("Route ID:\t"+routeID);
		System.out.println("Created On:\t"+createdOn);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}


	@Override
	public String toString() {
		return "BusPass [buspassID=" + buspassID + ", requestedOn=" + requestedOn + ", approvedRejectedOn="
				+ approvedRejectedOn + ", validTill=" + validTill + ", status=" + status + ", userID=" + userID
				+ ", routeId=" + routeID + ", createdOn=" + createdOn + "]";
	}
	
	
}
