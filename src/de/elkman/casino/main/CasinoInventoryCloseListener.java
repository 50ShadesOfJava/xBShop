package de.elkman.casino.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.elkman.casino.games.CoinGame;
import de.elkman.casino.games.LeverGame;
import de.elkman.casino.games.LotteryGame;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Var;
import de.xblackjack.xbbroad.main.XBBroad;

public class CasinoInventoryCloseListener implements Listener{

	public CasinoInventoryCloseListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
			try {
				Player p = (Player) e.getPlayer();
				if (e.getInventory().getName().equalsIgnoreCase(Var.INV_CASINOMAIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
					if (CasinoMain.inserts.containsKey(p.getUniqueId())){
						if (CasinoMain.inserts.get(p.getUniqueId()) == 0){
							CasinoMain.inserts.remove(p.getUniqueId());
						}
					}
				}else if(e.getInventory().getName().equalsIgnoreCase(Var.INV_CASINO_COIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p))) && CoinGame.invs.containsKey(p.getUniqueId())){
					p.openInventory(CoinGame.invs.get(p.getUniqueId()));
				}else if(e.getInventory().getName().equalsIgnoreCase(Var.INV_CASINO_LOTTERY_TITLE.replaceAll("&", XBBroad.getAccentColor(p))) && LotteryGame.invs.containsKey(p.getUniqueId())){
					p.openInventory(LotteryGame.invs.get(p.getUniqueId()));
				}else if(e.getInventory().getName().equalsIgnoreCase(Var.INV_CASINO_LEVER_TITLE.replaceAll("&", XBBroad.getAccentColor(p))) && LeverGame.inLeverGame.containsKey(p.getUniqueId())){
					p.openInventory(LeverGame.inLeverGame.get(p.getUniqueId()).getInventory());
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
	}
}