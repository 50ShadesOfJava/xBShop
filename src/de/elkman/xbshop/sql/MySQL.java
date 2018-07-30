package de.elkman.xbshop.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.elkman.crates.main.CrateData;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.xblackjack.xbbroad.main.XBBroad;

public class MySQL {
	
	@SuppressWarnings("deprecation")
	public MySQL(Main plugin) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
			public void run() {
				createTables();
			}
		}, 20);
		
	}
	
	@SuppressWarnings("static-access")
	public static Connection getConnection(){
		return XBBroad.getXbSQL().getCon();
	}
	
	public static void createTables(){
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS shop_players (UUID VARCHAR(100), Chips Integer, Daily Boolean, DailyStreak Integer, Products VARCHAR(1000))");
			ps.executeUpdate();
			ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + CrateData.CRATES_TABLE + " (Code VARCHAR(50), ExpireDate VARCHAR(100), Price VARCHAR(1000))");
			ps.executeUpdate();
			ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + CrateData.OLD_CRATES_TABLE + " (Code VARCHAR(50), Taker VARCHAR(100), Date VARCHAR(100), Price VARCHAR(1000))");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean uuidExists(String uuid){
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM shop_players WHERE UUID = ?");
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
		}
		return false;
	}
	
	public static void insertUser(String uuid){
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO shop_players (UUID,Chips,Daily,DailyStreak,Products) VALUES (?,?,?,?,?)");
			ps.setString(1, uuid);
			ps.setInt(2, 0);
			ps.setBoolean(3, false);
			ps.setInt(4, 0);
			ps.setString(5, "");
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getInt(String statement, String arg1){
		PreparedStatement ps;
		try {
			ps = getConnection().prepareStatement(statement);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				return rs.getInt(arg1);
			}
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while loading integer §e" + statement + " §c/ §e" + arg1);
		}
		return 0;
	}
	
	public static boolean getBoolean(String statement, String arg1){
		PreparedStatement ps;
		try {
			ps = getConnection().prepareStatement(statement);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				return rs.getBoolean(arg1);
			}
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while loading boolean §e" + statement + " §c/ §e" + arg1);
		}
		return false;
	}
	
	public static String getString(String statement, String arg1){
		PreparedStatement ps;
		try {
			ps = getConnection().prepareStatement(statement);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				return rs.getString(arg1);
			}
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while loading string §e" + statement + " §c/ §e" + arg1);
		}
		return "";
	}
	
	public static void save(Player p, int chips, boolean daily, ArrayList<Integer> productIds){
		String uuid = p.getUniqueId().toString();
		String products = "";
		for (Integer i : productIds){
			if (products.equals("")){
				products = "" + i;
			}else{
				products = products + "§" + i;
			}
		}
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE shop_players SET Chips = ?, Daily = ?, DailyStreak = ?, Products = ? WHERE UUID = ?");
			ps.setInt(1, chips);
			ps.setBoolean(2, daily);
			ps.setString(4, products);
			ps.setInt(3, 1);
			ps.setString(5, uuid);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while saving §e" + uuid + " §7(" + p.getName() + ")");
		}
	}
}
