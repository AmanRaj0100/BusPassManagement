package com.amazon.buspassmanagement;

import com.amazon.buspassmanagement.db.DB;

public class App {
	
	private App() {
	}
	
    public static void main(String[] args ){
    	
    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println( "Welcome to Bus Pass Management Application" );
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
       
        
        if(args.length > 0) {
        	DB.FILEPATH = args[0];
        }
        //Menu menu = new Menu();
        //menu.showMainMenu();
        
        //or
        
        new Menu().showMainMenu();
        
    }
    
}
