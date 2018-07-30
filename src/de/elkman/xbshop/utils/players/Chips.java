package de.elkman.xbshop.utils.players;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.elkman.xbshop.sql.MySQL;

public class Chips {

	public static HashMap<Player, Integer> playerChips = new HashMap<>();

	public static int loadChips(Player p){
		playerChips.remove(p);
		try {
			playerChips.put(p, MySQL.getInt("SELECT Chips FROM shop_players WHERE UUID = '" + p.getUniqueId().toString() + "'" , "Chips"));
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while Downloading Chips from §e" + p.getName()) ;
			playerChips.put(p, 1000);
		}
		return playerChips.get(p);
	}
	
	public static void unloadChips(Player p){
		playerChips.remove(p);
	}
	
	public static int getChips(Player p){
		if (!playerChips.containsKey(p)){
			return 0;
		}
		return playerChips.get(p);
	}
	
}
