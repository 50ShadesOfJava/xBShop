package de.elkman.crates.main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.bukkit.Bukkit;

import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.sql.MySQL;

public class Crate {

	private String code;
	private String taker;
	private CratePrice price;
	private Timestamp expiredate;
	private Timestamp takendate;
	
	public Crate(String code, String taker, CratePrice price, Timestamp expiredate, Timestamp takenDate) {
		this.code = code;
		this.taker = taker;
		this.price = price;
		this.expiredate = expiredate;
		this.takendate = takenDate;
	}
	
	public CratePrice getPrice(){
		return price;
	}
	
	public String getTaker(){
		return taker;
	}
	
	public boolean isTaken(){
		return (taker != null);
	}
	
	public String getCode(){
		return code;
	}
	
	public void setCode(String arg0){
		this.code = arg0;
	}
	
	public void setPrice(CratePrice arg0){
		this.price = arg0;
	}
	
	public void setTaker(String arg){
		this.taker = arg;
	}
	
	public Timestamp getExpireDate(){
		return expiredate;
	}
	
	public void setExpireDate(Timestamp timestamp){
		expiredate = timestamp;
	}
	
	public Timestamp getTakenDate(){
		return takendate;
	}
	
	public void setTakenDate(Timestamp timestamp){
		takendate = timestamp;
	}
	
	public void save(){
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			public void run() {
				if (!isTaken()){
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO " + CrateData.CRATES_TABLE + " (Code,ExpireDate,Price) VALUES (?,?,?)");
						ps.setString(1, getCode());
						ps.setString(2, getExpireDate().toString());
						ps.setString(3, getPrice().toString());
						ps.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else{
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM " + CrateData.CRATES_TABLE + " WHERE Code = ?");
						ps.setString(1, getCode());
						ps.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO " + CrateData.OLD_CRATES_TABLE + " (Code,Taker,Date,Price) VALUES (?,?,?,?)");
						ps.setString(1, getCode());
						ps.setString(2, getTaker());
						ps.setString(3, getTakenDate().toString());
						ps.setString(4, getPrice().toString());
						ps.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}