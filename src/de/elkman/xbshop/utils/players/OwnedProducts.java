package de.elkman.xbshop.utils.players;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.elkman.xbshop.sql.MySQL;

public class OwnedProducts {

	public static HashMap<Player, ArrayList<Integer>> playerIDs = new HashMap<>();
	
	public static ArrayList<Integer> loadOwnedProducts(Player p){
		playerIDs.remove(p);
		ArrayList<Integer> list = new ArrayList<>();
		try {
			String s = MySQL.getString("SELECT Products FROM shop_players WHERE UUID = '" + p.getUniqueId().toString() + "'", "Products");
			for (String part : s.split("§")){
				if (part.length() > 0){
					try {
						list.add(Integer.parseInt(part));
					} catch (Exception e) {
						Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cPlayer §e" + p.getName() + " §ctried to load ShopProduct with ID §e" + part);
					}
				}
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while Downloading Products from §e" + p.getName()) ;
		}
		playerIDs.put(p, list);
		return playerIDs.get(p);
	}
	
	public static ArrayList<Integer> getOwnedProducts(Player p){
		if (!playerIDs.containsKey(p)){
			return new ArrayList<Integer>();
		}
		return playerIDs.get(p);
	}
	
	public static void unloadProducts(Player p){
		playerIDs.remove(p);
	}
	
	
	public static boolean playerHasProduct(Player p, int productID){
		return getOwnedProducts(p).contains(productID);
	}

}