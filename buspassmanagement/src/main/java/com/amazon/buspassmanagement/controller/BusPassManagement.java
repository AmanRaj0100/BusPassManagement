package com.amazon.buspassmanagement.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import com.amazon.buspassmanagement.buspassSession;
import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.model.Feedbacks;


public class BusPassManagement extends Management{
	
	private BusPassManagement() {
	}
	
	// Create it as a Singleton 
	private static BusPassManagement manageBusPass = new BusPassManagement();

	public static BusPassManagement getInstance() {
		return manageBusPass;
	}
	
	public void manageBusPass() {
		
		while(true) {
			try {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("1: View Bus Pass Requests");
				System.out.println("2: View Bus Pass Request By UserID");
				System.out.println("3: Update Bus Pass Request");
				System.out.println("4: Suspend Bus Pass on Request");
				System.out.println("5: Delete Bus Pass Request");
				System.out.println("6: Quit Managing BusPasses");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("Enter Your Choice: ");
				int choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
				boolean quit = false;
				switch(choice) {
				case 1:
					viewPassRequests();
					break;
					
				case 2:
					System.out.println("Enter User ID: ");
					int userID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
					viewPassRequestsByUser(userID);
					break;
					
				case 3:
					approveRejectPassRequest();
					break;

				case 4:							
					busPassSuspensionHandler();
					break;
					
				case 5:							
					deletePass();
					break;
					
				case 6:
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

	public void viewPassRequests() {
	
	System.out.println("Enter Route ID to get All the Pass Reqeuests for a Route");
	System.out.println("Or 0 for All Bus Pass Requests");
	System.out.println("Enter Route ID: ");
	
	int routeID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
	
	List<BusPass> buspasses = null;
	
	if(routeID == 0) {
		buspasses = buspassdao.retrieve();
	}else {
		String sql = "SELECT * from BusPass where routeID = "+routeID;
		buspasses = buspassdao.retrieve(sql);
	}
	
	for(BusPass object : buspasses) {
		object.prettyPrint();
	}
}
	
	// Handler for the Bus Pass :)
	public void requestPass() {
		
		buspass.getDetails(false);
		
		// Add the User ID Implicitly.
		buspass.userID = buspassSession.user.id;
		
		// As initially record will be inserted by User where it is a request
		buspass.status = 1; // initial status as requested :)
		
		int result = buspassdao.insert(buspass);
		String message = (result > 0) ? "Pass Requested Successfully" : "Request for Pass Failed. Try Again.."; 
		System.out.println(message);
	}
	
	public void deletePass() {
		
		System.out.println("Enter BusPass ID to be deleted: ");
		buspass.buspassID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		int result = buspassdao.delete(buspass);
		String message = (result > 0) ? "Pass Deleted Successfully" : "Deleting Pass Failed. Try Again.."; 
		System.out.println(message);
	}
	
	/*
	 
	 	Extra Task:
	 	IFF : You wish to UpSkill :)
	 
	 	Scenario: Open the same application in 2 different terminals
	 	1 logged in by user
	 	another logged in by Admin
	 	
	 	If Admin, approves or rejects the pass -> User should be notified :)
	 	
	 	Reference Link
	 	https://github.com/ishantk/AmazonAtlas22/blob/master/Session8/src/com/amazon/atlas/casestudy/YoutubeApp.java
	 
	 */
	
	public void approveRejectPassRequest() {

		System.out.println("Enter Bus Pass ID: ");
		buspass.buspassID = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("2: Approve");
		System.out.println("3: Cancel");
		System.out.println("Enter Approval Choice: ");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		buspass.status = Integer.parseInt(scanner.nextLine());//scanner.nextInt();

    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		buspass.approvedRejectedOn = dateFormat.format(date1);
		
		if(buspass.status == 2) {
			calendar.add(Calendar.YEAR, 1);
			Date date2 = calendar.getTime();
			buspass.validTill = dateFormat.format(date2);
		}else {
			buspass.validTill = buspass.approvedRejectedOn;
		}
		
		int result = buspassdao.update(buspass);
		String message = (result > 0) ? "Pass Request Updated Successfully" : "Updating Pass Request Failed. Try Again.."; 
		System.out.println(message);
	}
	
	public void viewPassRequestsByUser(int userID) {
		
		String sql = "SELECT * from BusPass where userID = "+userID;
		List<BusPass> buspasses = buspassdao.retrieve(sql);
		
		for(BusPass object : buspasses) {
			object.prettyPrint();
		}
	}
	
	public int busPassSuspensionHandler() {
		//Retrieve suspension request from feedbacks 
		String sql = "Select * from Feedbacks where type =" +3;
		List <Feedbacks> feedback = new ArrayList<Feedbacks>();
		feedback = feedbackdao.retrieve(sql);
		int month=0;
		//Displaying the requests to Admin
		for (Feedbacks suspensionRequest : feedback) {
			suspensionRequest.prettyPrint();
			String description = suspensionRequest.description;
			String digits = description.replaceAll("[^0-9]", "");
			month = Integer.parseInt(digits);
		}
		System.out.println("Enter the UserID of the suspension request you want to modify:");
		int choice = Integer.parseInt(scanner.nextLine());
		sql = "Select * from BusPass where userID =" +choice;
		List<BusPass> buspass = new ArrayList<BusPass>();
		buspass = buspassdao.retrieve(sql);
		for (BusPass buspasses : buspass) {
			buspasses.prettyPrint();
		}
		
		// If a particular user has multiple buspasses, we ask for the buspass id to be modified.
		System.out.println("Enter the BusPassID of the suspension request you want to modify:");
		choice = Integer.parseInt(scanner.nextLine());
		sql = "Select * from BusPass where buspassID =" +choice;
		buspass = buspassdao.retrieve(sql);
		//Now we have the buspass on which we have to perform suspension..
		for (BusPass buspasses : buspass) {
			System.out.println("Do you want to Approve or Reject the Suspension Request?");
			System.out.println("1 Approval /n.2 Rejection");
			choice = Integer.parseInt(scanner.nextLine());
			if (choice == 1) {
				//Approve
				//System.out.println("Before"+buspasses.validTill);
				String date1 = buspasses.validTill.substring(0,10);
				String addOn = buspasses.validTill.substring(11,21);
				buspasses.status = 4;
				LocalDate date = LocalDate.parse(date1);
				//Month monthString = date.getMonth();
				date = date.plusMonths(month);
				//System.out.println(date);
				buspasses.validTill = date.toString()+" "+addOn;
				//System.out.println(buspasses.validTill);
				buspassdao.update(buspasses);	
			}
			else if (choice == 2) {
				break;
			}
			else
				System.out.println("Invalid Choice");
		}
		return 0;
	}
}
