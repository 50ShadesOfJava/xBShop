package de.elkman.xbshop.extras;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import de.elkman.xbshop.api.AsyncMoveEvent;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.utils.players.SelectedProducts;
import de.elkman.xbshop.utils.products.ShopCategory;
import de.elkman.xbshop.utils.products.ShopProduct;

public class DoubleJump implements Listener{

	public DoubleJump(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public static ArrayList<Player> oneJump = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(AsyncMoveEvent e){
		Player p = e.getPlayer();
		if (SelectedProducts.playersWithEquipment.contains(p)){
			ShopProduct sp = SelectedProducts.getSelectedProduct(p, ShopCategory.ABILITY);
			if (sp != null){
				if (sp.getName().equalsIgnoreCase("Doppelsprung") || sp.getName().equalsIgnoreCase("Dreifachsprung")){
					try {
						if (p.isOnGround()){
							p.setAllowFlight(true);
							oneJump.remove(p);
						}
					} catch (Exception e2) {
					}
				}
			}
		}
	}
	@EventHandler
	public void onDoubleJump(PlayerToggleFlightEvent e){
		Player p = e.getPlayer();
		if (SelectedProducts.playersWithEquipment.contains(p)){
			ShopProduct sp = SelectedProducts.getSelectedProduct(p, ShopCategory.ABILITY);
			if (sp != null){
				if (sp.getName().equalsIgnoreCase("Doppelsprung") || sp.getName().equalsIgnoreCase("Dreifachsprung")){
					if (e.isFlying() && p.getGameMode() != GameMode.CREATIVE){
						e.setCancelled(true);
						if (sp.getName().equalsIgnoreCase("Dreifachsprung") && oneJump.contains(p)){
//							Der dritte Sprung
							Vector v = p.getLocation().getDirection();
							v.multiply(0.3);
							v.setY(0.9F);
							p.setVelocity(v);
							p.setFlying(false);
							p.setAllowFlight(false);
							p.setFallDistance(-5.0F);
							oneJump.remove(p);
							p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 10, 10);
							p.getWorld().playEffect(p.getLocation(), Effect.FIREWORKS_SPARK, 2);
						}else{
							Vector v = p.getLocation().getDirection();
							v.setY(0.5F);
							v.multiply(1.3);
							p.setVelocity(v);
							p.setFlying(false);
							p.setFallDistance(-4.0F);
							p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 5, 5);
							p.getWorld().playEffect(p.getLocation(), Effect.FIREWORKS_SPARK, 2);
							if (sp.getName().equalsIgnoreCase("Doppelsprung") || oneJump.contains(p)){
								p.setAllowFlight(false);
							}else{
								oneJump.add(p);
							}
						}
					}
				}
			}
		}
	}
}
