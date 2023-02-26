package com.amazon.buspassmanagement;

import java.util.Date;

import com.amazon.buspassmanagement.model.User;

public class AdminMenu extends Menu {

	private static AdminMenu adminMenu =new AdminMenu();
	
	public static Menu getInstance() {
		return adminMenu;
	}
	
	public void showMenu() {
		
		// Login Code should come before the Menu becomes Visible to the Admin
		User adminUser = new User();
		
		System.out.println("Enter Your Email:");
		adminUser.email = scanner.nextLine();
		
		System.out.println("Enter Your Password:");
		adminUser.password = scanner.nextLine();
		
		boolean result = auth.loginUser(adminUser);
		
		if(result && adminUser.type == 1) {
			
			buspassSession.user = adminUser;
		
			System.out.println("*********************");
			System.out.println("Welcome to Admin App");
			System.out.println("Hello, "+adminUser.name);
			System.out.println("Its: "+new Date());
			System.out.println("*********************");
			
			boolean quit = false;
			
			while(true) {
				try {
		        	System.out.println("1: Manage Routes");
		        	System.out.println("2: Manage Stops");
		        	System.out.println("3: Manage Vehicles");
		        	System.out.println("4: Manage Bus Pass");
		        	System.out.println("5: Manage Feedbacks");
		        	System.out.println("6: Quit Admin App");
		        	System.out.println("Select an Option");
		        	
		        	int choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
		        	
		        	switch (choice) {
						case 1:
							routes.manageRoute();
							break;
							
						case 2:
							stops.manageStop();
							break;
		
						case 3:
							vehicles.manageVehicle();
							break;
							
						case 4:
							buspass.manageBusPass();
							break;
							
						case 5:
							feedbacks.manageFeedback();
							break;
							
						case 6:
							System.out.println("Thank You for Using Admin App !!");
							quit = true;
							break;
			
						default:
							System.err.println("Invalid Choice...");
							break;
					}
		        	
		        	if(quit) {
		        		break;
		        	}
				} catch (Exception e) {
					System.err.println("Invalid Input:" +e);
				}	
	        }
		}
		else {
			System.err.println("Invalid Credentials. Please Try Again !!");
		}
	}
}
