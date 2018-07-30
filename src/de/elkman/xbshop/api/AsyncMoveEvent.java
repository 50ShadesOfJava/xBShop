package de.elkman.xbshop.api;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.elkman.xbshop.main.Main;

public class AsyncMoveEvent extends Event{

	public static HandlerList handlers = new HandlerList();
	Player player;
	Location from;
	Location to;
	
	public static HashMap<Player, Location> fromLocations = new HashMap<>();
	
	public static void runTask(){
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), ()->{
			for (Player all : Bukkit.getOnlinePlayers()){
				Location from = all.getLocation();
				if (fromLocations.containsKey(all)) {
					from = fromLocations.get(all);
				}
				if (!from.equals(all.getLocation())) {
					Bukkit.getServer().getPluginManager().callEvent(new AsyncMoveEvent(from, all.getLocation(), all));
				}
				fromLocations.put(all, all.getLocation());
			}
		}, 20, 2);
	}
	
	public AsyncMoveEvent(Location from, Location to, Player p) {
		this.player = p;
		this.from = from;
		this.to = to;
	}
	
	public Location getFrom(){
		return from;
	}
	
	public Location getTo(){
		return to;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public ShopCustomer getShopCustomer(){
		return new ShopCustomer(player);
	}
	
	@Override
	public HandlerList getHandlers(){
		return handlers;
	}

	public static HandlerList getHandlerList(){
		return handlers;
	}
}
