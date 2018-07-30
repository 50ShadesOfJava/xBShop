package de.elkman.xbshop.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.elkman.casino.main.DailyMain;
import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.inventorys.ShopInventory;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.sql.MySQL;
import de.elkman.xbshop.utils.players.Chips;
import de.elkman.xbshop.utils.players.OwnedProducts;

public class QuitListener implements Listener{

	public QuitListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		ShopCustomer sc = new ShopCustomer(p);
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			public void run() {
			 	MySQL.save(p, sc.getChips(), DailyMain.dailytaken.get(p), OwnedProducts.getOwnedProducts(p));
				
				Chips.unloadChips(p);
				OwnedProducts.unloadProducts(p);
			}
		});
		
		ShopInventory.warenkorb.remove(p);
	}
}