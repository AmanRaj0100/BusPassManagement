package com.amazon.buspassmanagement.controller;

import java.util.List;

import com.amazon.buspassmanagement.buspassSession;
import com.amazon.buspassmanagement.model.Feedbacks;


public class FeedbacksManagement extends Management {

	private FeedbacksManagement() {
	}
	
	// Create it as a Singleton 
		private static FeedbacksManagement manageFeedbacks = new FeedbacksManagement();
		
		
		public static FeedbacksManagement getInstance() {
			return manageFeedbacks;
		}
		
		public void manageFeedback() {
			
			while(true) {
				try {
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("1: View Feedbacks");
					System.out.println("2: View Feedbacks by User");
					System.out.println("3: Delete Feedbacks");
					System.out.println("4: Quit Managing Feedbacks");
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("Enter Your Choice: ");
					int choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
					boolean quit = false;
					switch(choice) {
					case 1:
						viewFeedbacks();
						break;
						
					case 2:
						System.out.println("Enter User ID: ");
						int userID = Integer.parseInt(scanner.nextLine());//scanner.nexInt();
						viewFeedbacksByUser(userID);
						break;

					case 3:
						deleteFeedback();
						break;
						
					case 4:
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
		
		// Handler for the Feedback
		public void createFeedback() {
			
			feedbacks.getDetails();
			
			// Add the User ID Implicitly.
			feedbacks.userID = buspassSession.user.id;
			feedbacks.raisedBy = buspassSession.user.email;
			
			int result = feedbackdao.insert(feedbacks);
			String message = (result > 0) ? "Feedback Created Successfully" : "Creating Feedback Failed. Try Again.."; 
			System.out.println(message);
		}
		
		
		public void deleteFeedback() {
			
			if(viewFeedbacks()) {
				System.out.println("Enter FeedbackID to be deleted: ");
				feedbacks.feedbackID = Integer.parseInt(scanner.nextLine());
				int result = feedbackdao.delete(feedbacks);
				String message = (result > 0) ? "Feedback Deleted Successfully" : "Deleting Feedback Failed. Try Again.."; 
				System.out.println(message);
			}else {
				System.out.println("Cannot Process your request");
			}
		}
		
		
		public boolean viewFeedbacks() {
			List<Feedbacks> feedbacks = feedbackdao.retrieve();
			
			if(feedbacks.size()>0) {
				for(Feedbacks object : feedbacks) {
					object.prettyPrint();
				}
				return true;
			} else {
				System.out.println("No Feedbacks available to display");
				return false;
			}
		}
		
		public void viewFeedbacksByUser(int userID) {
			
			String sql = "SELECT * from Feedbacks where userID = "+userID;
			
			List<Feedbacks> feedbacks = feedbackdao.retrieve(sql);
			
			if(feedbacks.size()>0) {
				for(Feedbacks object : feedbacks) {
					object.prettyPrint();
				}
			}else {
				System.out.println("No Feedbacks available to display");
			}
		}
}
