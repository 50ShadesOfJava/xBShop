package de.elkman.xbshop.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.elkman.casino.main.DailyMain;
import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.elkman.xbshop.sql.MySQL;
import de.elkman.xbshop.utils.players.Chips;
import de.elkman.xbshop.utils.players.OwnedProducts;

public class JoinListener implements Listener{

	public JoinListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			public void run() {
				try {
					if (!MySQL.uuidExists(p.getUniqueId().toString())){
						MySQL.insertUser(p.getUniqueId().toString());
					}
				} catch (Exception e2) {
					Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cError while loading User §e" + p.getName()) ;
				}
				Chips.loadChips(p);
				OwnedProducts.loadOwnedProducts(p);
				DailyMain.loadDailyProfile(p);
			}
		});
		
		ShopCustomer sc = new ShopCustomer(p);
		sc.equip();
	}
}
