package de.elkman.casino.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.elkman.casino.inventorys.CasinoInventory;
import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.sql.MySQL;

public class DailyMain implements Listener{

	public DailyMain(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		dailySchedular(plugin);
	}
	
	public static HashMap<Player, Boolean> dailytaken = new HashMap<>();
	public static HashMap<Player, Integer> dailystreak = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	public static void dailySchedular(Main plugin){
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				SimpleDateFormat time = new SimpleDateFormat("HH:mm");
				time.setTimeZone(TimeZone.getDefault());
				time = new SimpleDateFormat("HH:mm");
				String timestring = time.format(new Date());
				if (timestring.equalsIgnoreCase("00:00")){
					Bukkit.broadcastMessage(Main.getPrefix(PrefixColor.SUCCESS) + "The DailyChips are reseted!");
					ArrayList<Player> taken = new ArrayList<>();
					for (Player p : dailytaken.keySet()){
						if (dailytaken.get(p)){
							taken.add(p);
						}
					}
					dailytaken.clear();
					HashMap<Player, Integer> streak = new HashMap<>();
					streak.putAll(dailystreak);
					for (Player all : Bukkit.getOnlinePlayers()){
						dailytaken.put(all, false);
						if (taken.contains(all)){
							int c = dailystreak.get(all);
							c++;
							dailystreak.remove(all);
							dailystreak.put(all, c);
						}else{
							dailystreak.remove(all);
							dailystreak.put(all, 0);
						}
					}
					HashMap<String, Integer> mysqlStreaks = new HashMap<>();
					ArrayList<String> mysqlTaken = new ArrayList<>();
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM shop_players");
						ResultSet rs = ps.executeQuery();
						while (rs.next()){
							try {
								boolean t = rs.getBoolean("Daily");
								int s = rs.getInt("DailyStreak");
								String uuid = rs.getString("UUID");
								mysqlStreaks.put(uuid, s);
								if (t){
									mysqlTaken.add(uuid);
								}
							} catch (Exception e) {
								Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while loading DailyInformation of a Player.");
							}
						}
						for (String uuid : mysqlStreaks.keySet()){
							try {
								if (mysqlTaken.contains(uuid)){
									int c = mysqlStreaks.get(uuid);
									c++;
									PreparedStatement ps2 = MySQL.getConnection().prepareStatement("UPDATE shop_players SET DailyStreak = ? WHERE UUID = ?");
									ps2.setInt(1, c);
									ps2.setString(2, uuid);
									ps2.execute();
								}else{
									PreparedStatement ps2 = MySQL.getConnection().prepareStatement("UPDATE shop_players SET DailyStreak = ? WHERE UUID = ?");
									ps2.setInt(1, 0);
									ps2.setString(2, uuid);
									ps2.execute();
								}
							} catch (SQLException e) {
								Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while updating the DailyStreak of §e" + uuid);
							}
						}
						try {
							PreparedStatement ps3 = MySQL.getConnection().prepareStatement("UPDATE shop_players SET Daily = ?");
							ps3.setBoolean(1, false);
							ps3.execute();
						} catch (SQLException e) {
							Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while setting all Daily Booleans to false.");
						}
					} catch (SQLException e) {
						Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while loading DailyInformation.");
					}
				}
			}
		}, 20, 20*60);
	}
	
	public static void loadDailyProfile(Player p){
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
			try {
				dailytaken.put(p, MySQL.getBoolean("SELECT Daily FROM shop_players WHERE UUID = '" + p.getUniqueId().toString() + "'", "Daily"));
				dailystreak.put(p, MySQL.getInt("SELECT DailyStreak FROM shop_players WHERE UUID = '" + p.getUniqueId().toString() + "'", "DailyStreak"));
			} catch (Exception e) {
				dailytaken.put(p, false);
				dailystreak.put(p, 0);
				Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while Downloading DailyStreak from §e" + p.getName()) ;
			}
		});
	}
	
	public static void unloadDailyProfile(Player p){
		dailytaken.remove(p);
		dailystreak.remove(p);
	}
	
	public static void click(Player p){
		if (!dailytaken.get(p)){
			int s = dailystreak.get(p);
			dailytaken.remove(p);
			dailytaken.put(p, true);
			
			ShopCustomer sc = new ShopCustomer(p);
			int chips = Var.DAILY_CHIPS;
			if (p.hasPermission("xbshop.premium")){
				chips = chips + Var.DAILY_PREMIUM;
			}
			chips = chips + (s*Var.DAILY_STREAK);
			
			sc.addChips(chips, true);
			p.getOpenInventory().getTopInventory().setContents(CasinoInventory.getMainInventory(p).getContents());
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
		}
	}
}
