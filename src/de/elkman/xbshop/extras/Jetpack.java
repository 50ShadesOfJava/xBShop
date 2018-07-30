package de.elkman.xbshop.extras;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.utils.players.SelectedProducts;
import de.elkman.xbshop.utils.products.ShopCategory;
import de.elkman.xbshop.utils.products.ShopProduct;
import de.xblackjack.xbbroad.main.XBBroad;

public class Jetpack implements Listener{

	public Jetpack(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if (SelectedProducts.playersWithEquipment.contains(p)){
			if (e.getItem() != null){
				if (e.getItem().getItemMeta().getDisplayName() != null){
					if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
						if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7« " + XBBroad.getAccentColor(p) + "§l" + "Jetpack" + " §7»")){
							e.setCancelled(true);
							ShopProduct sp = SelectedProducts.getSelectedProduct(p, ShopCategory.GADGET);
							if (sp != null){
								if (sp.getName().equalsIgnoreCase("Jetpack")){
									Vector v = p.getEyeLocation().getDirection();
									v.multiply(0.8F);
									v.setY(0.5);
									p.setVelocity(v);
									p.playSound(p.getLocation(), Sound.BLAZE_HIT, 10, 10);
									p.setFallDistance(-15);
								}
							}
						}
					}
				}
			}
		}
	}
}