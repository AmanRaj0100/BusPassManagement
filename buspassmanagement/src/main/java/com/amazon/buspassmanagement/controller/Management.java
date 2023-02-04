package com.amazon.buspassmanagement.controller;


import java.util.Scanner;

import com.amazon.buspassmanagement.db.DB;

import com.amazon.buspassmanagement.db.BusPassDAO;
import com.amazon.buspassmanagement.db.FeedbacksDAO;
import com.amazon.buspassmanagement.db.RoutesDAO;
import com.amazon.buspassmanagement.db.StopsDAO;
import com.amazon.buspassmanagement.db.UserDAO;
import com.amazon.buspassmanagement.db.VehiclesDAO;

import com.amazon.buspassmanagement.model.User;
import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.model.Feedbacks;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Stops;
import com.amazon.buspassmanagement.model.Vehicles;

public class Management {
	
	Scanner scanner = new Scanner(System.in);
	
	DB db = DB.getInstance();
	AuthenticationService service = AuthenticationService.getInstance();
	RoutesManagement manageRoutes = RoutesManagement.getInstance();
	StopsManagement manageStops = StopsManagement.getInstance();
	VehiclesManagement manageVehicle = VehiclesManagement.getInstance();
	
	User user = new User();
	BusPass buspass = new BusPass();
	Feedbacks feedbacks = new Feedbacks();
	Routes routes = new Routes();
	Stops stops = new Stops();
	Vehicles vehicles = new Vehicles();
	
	BusPassDAO buspassdao = new BusPassDAO();
	FeedbacksDAO feedbackdao = new FeedbacksDAO();
	RoutesDAO routedao = new RoutesDAO();
	StopsDAO stopdao = new StopsDAO();
	UserDAO dao = new UserDAO();
	VehiclesDAO vehicledao = new VehiclesDAO();
}
