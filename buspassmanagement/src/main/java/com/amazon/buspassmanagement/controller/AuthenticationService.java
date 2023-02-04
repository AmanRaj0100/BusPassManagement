package com.amazon.buspassmanagement.controller;

import java.util.List;

//import java.util.Iterator;
//import java.util.LinkedHashMap;

import com.amazon.buspassmanagement.db.passEncryption;
import com.amazon.buspassmanagement.model.User;

public class AuthenticationService extends Management{
	
	private static AuthenticationService service = new AuthenticationService();
	
	passEncryption encrypt = passEncryption.getInstance();

	/*
	// Dummy DataBase in RAM
	LinkedHashMap<Integer, User> users = new LinkedHashMap<Integer, User>();
	
	private AuthenticationService(){
		
		User user1 = new User();
		user1.id = 1;
		user1.name = "John Watson";
		user1.email = "john@example.com";
		user1.password = "admin123";
		user1.type = 1;		// admin
		
		User user2 = new User();
		user2.id = 2;
		user2.name = "Fionna Flynn";
		user2.email = "fionna@example.com";
		user2.password = "fionna123";
		user2.type = 2;		// user or visitor
		
		// Dummy Users in the HashMap :)
		users.put(user1.id, user1);	// 1, user1
		users.put(user2.id, user2); // 2. user2
		
		System.out.println("DataBase Details:");
		System.out.println(users);
	}
	*/
	
	private AuthenticationService(){
		
		/*
		User user1 = new User();
		user1.id = 1;
		user1.name = "Aman Srivastava";
		user1.phone = "+91 80847 31205";
		user1.email = "aman@example.com";
		user1.password = "admin123";
		user1.address = "Gachibowli, HYD";
		user1.department = "admin";
		user1.type = 1;		// admin
		
		User user2 = new User();
		user2.id = 2;
		user2.name = "John Watson";
		user2.phone = "+91 99999 11111";
		user2.email = "john@example.com";
		user2.password = "john123";
		user2.address = "Redwood Shores";
		user2.department = "hr";
		user2.type = 2;		// user or visitor
		
		User user3 = new User();
		user3.id = 2;
		user3.name = "Fionna Flynn";
		user3.phone = "+91 99999 22222";
		user3.email = "fionna@example.com";
		user3.password = "fionna123";
		user3.address = "Country Homes";
		user3.department = "hr";
		user3.type = 2;		// user or visitor
		
		//Add 3 Users in DataBase
		UserDAO dao = new UserDAO();
		dao.insert(user1);
		dao.insert(user2);
		dao.insert(user3);
		*/
	}
	
	public static AuthenticationService getInstance() {
		return service;
	}
	
	/*public boolean loginUser(User user) {
		
		boolean result = false;
		
		
		Iterator<Integer> itr = users.keySet().iterator();
		while(itr.hasNext()) {
			Integer key = itr.next();
			
			User u = users.get(key);
			// Select * from User where email = '' and password = ''
			if(u.email.equals(user.email) && u.password.equals(user.password)) {
				// user will now point to a new Object i.e. referred by u
				user.name = u.name;
				user.type = u.type; 
				result = true;
				break;
			}
		
		}
		return result;
	}*/
	
	public boolean loginUser(User user) {
		
		String sql = "SELECT * FROM Users WHERE email = '"+user.email+"' AND password = '"+encrypt.encryptor(user.password)+"'";
		List<User> users = dao.retrieve(sql);
		
		if(users.size() > 0) {
			User u = users.get(0);
			user.id = u.id;
			user.name = u.name;
			user.phone = u.phone;
			user.email = u.email;
			user.address = u.address;
			user.department = u.department;
			user.type = u.type;
			user.createdOn = u.createdOn;
			return true;
		}
		
		return false;
	}
	
	public boolean registerUser(User user) {
		//int result = dao.insert(user);
		//return result > 0;
		return dao.insert(user) > 0;
	}
	
	public boolean updateUser(User user) {
		return dao.update(user) > 0;
	}
	
}
