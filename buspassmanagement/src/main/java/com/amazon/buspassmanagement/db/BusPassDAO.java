package com.amazon.buspassmanagement.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.model.BusPass;

public class BusPassDAO implements DAO<BusPass> {
	
	DB db = DB.getInstance();

	public int insert(BusPass object) {
		String sql = "INSERT INTO BusPass (userID, routeID, status) VALUES ("+object.userID+", "+object.routeID+", "+object.status+")";
		return db.executeSQL(sql);
	}

	public int update(BusPass object) {
		String sql = "UPDATE BusPass set approvedRejectedOn = '"+object.approvedRejectedOn
				+"', validTill = '"+object.validTill+"', status = "+object.status +" WHERE buspassID = "+object.buspassID;
		return db.executeSQL(sql);
	}

	public int delete(BusPass object) {
		String sql = "DELETE FROM BusPass WHERE buspassID = "+object.buspassID;
		return db.executeSQL(sql);
	}

	public List<BusPass> retrieve() {
		String sql = "SELECT * from BusPass";
		
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<BusPass> buspasses = new ArrayList<BusPass>();
		
		try {
			while(set.next()) {
				
				BusPass buspass = new BusPass();
				
				// Read the row from ResultSet and put the data into Object
				buspass.buspassID = set.getInt("buspassID");
				buspass.requestedOn = set.getString("requestedOn");
				buspass.approvedRejectedOn = set.getString("approvedRejectedOn");
				buspass.validTill = set.getString("validTill");
				buspass.status = set.getInt("status");
				buspass.userID = set.getInt("userID");
				buspass.routeID = set.getInt("routeID");
				buspass.createdOn = set.getString("createdOn");
				
				buspasses.add(buspass);
			}
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}

		
		return buspasses;
	}

	public List<BusPass> retrieve(String sql) {
		
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<BusPass> buspasses = new ArrayList<BusPass>();
		
		try {
			while(set.next()) {
				
				BusPass buspass = new BusPass();
				
				// Read the row from ResultSet and put the data into Object
				buspass.buspassID = set.getInt("buspassID");
				buspass.requestedOn = set.getString("requestedOn");
				buspass.approvedRejectedOn = set.getString("approvedRejectedOn");
				buspass.validTill = set.getString("validTill");
				buspass.status = set.getInt("status");
				buspass.userID = set.getInt("userID");
				buspass.routeID = set.getInt("routeID");
				buspass.createdOn = set.getString("createdOn");
				
				buspasses.add(buspass);
			}
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}

		
		return buspasses;
	}

}
