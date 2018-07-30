package de.elkman.xbshop.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.elkman.casino.inventorys.CasinoInventory;
import de.elkman.xbshop.inventorys.MainInventory;
import de.elkman.xbshop.main.Main;

public class InteractListener implements Listener{

	public InteractListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (e.getItem() != null){
				if (e.getItem().getItemMeta() != null){
					if (e.getItem().getItemMeta().getDisplayName() != null){
						if (e.getItem().getItemMeta().getDisplayName() != null){
							String name = e.getItem().getItemMeta().getDisplayName();
							if (e.getItem().getType() == Material.NETHER_STAR){
								if (name.contains("Shop")){
									p.openInventory(MainInventory.getMainInventory(p));
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractAtEntityEvent e){
		if (e.getRightClicked() instanceof Zombie){
			e.setCancelled(true);
			Player p = e.getPlayer();
			p.openInventory(CasinoInventory.getMainInventory(p));
		}
	}
}