package de.elkman.crates.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import de.elkman.xbshop.sql.MySQL;

public class CrateData {

	public static final String CRATES_TABLE = "crates_available";
	public static final String OLD_CRATES_TABLE = "crates_unavailable";
	
	public static boolean crateIsAvailable(String code){
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM " + CRATES_TABLE + " WHERE Code = ?");
			ps.setString(1, code.toUpperCase());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean crateWasTaken(String code){
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM " + OLD_CRATES_TABLE + " WHERE Code = ?");
			ps.setString(1, code.toUpperCase());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Crate loadCrateFormDatabase(String database, String code){
		Crate crate = new Crate(code, null, null, null, null);
		if (database == CrateData.CRATES_TABLE){
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM " + CrateData.CRATES_TABLE + " WHERE Code = ?");
				ps.setString(1, code);
				ResultSet rs = ps.executeQuery();
				while (rs.next()){
					crate.setExpireDate(Timestamp.valueOf(rs.getString("ExpireDate")));
					crate.setPrice(new CratePrice(rs.getString("Price")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if (database == CrateData.OLD_CRATES_TABLE){
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM " + CrateData.OLD_CRATES_TABLE + " WHERE Code = ?");
				ps.setString(1, code);
				ResultSet rs = ps.executeQuery();
				while (rs.next()){
					crate.setTaker(rs.getString("Taker"));
					crate.setTakenDate(Timestamp.valueOf(rs.getString("Date")));
					crate.setPrice(new CratePrice(rs.getString("Price")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return crate;
	}
	
	@SuppressWarnings("deprecation")
	public static Timestamp getTimestamp(String duration) {
		int l = Integer.parseInt(duration.substring(0, duration.length() - 1));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if (duration.endsWith("m")) {
			timestamp.setMinutes(timestamp.getMinutes() + l);
		}
		if (duration.endsWith("h")) {
			timestamp.setHours(timestamp.getHours() + l);
		}
		if (duration.endsWith("d")) {
			timestamp.setHours(timestamp.getHours() + (l * 24));
		}
		if (duration.endsWith("w")) {
			timestamp.setMonth(timestamp.getMonth() + (l / 4));
		}
		return timestamp;
	}
	
}
