package de.elkman.casino.main;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.elkman.casino.games.CoinGame;
import de.elkman.casino.games.LeverGame;
import de.elkman.casino.games.LotteryGame;
import de.elkman.casino.inventorys.CasinoInventory;
import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.xblackjack.xbbroad.main.XBBroad;
import de.elkman.xbshop.main.Var;

public class CasinoInventoryClickListener implements Listener{

	public CasinoInventoryClickListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if (e.getWhoClicked() instanceof Player){
			Player p = (Player) e.getWhoClicked();
			if (e.getClickedInventory() != null && e.getClickedInventory().getTitle() != null){
				if (e.getCurrentItem() != null){
					if (e.getCurrentItem().getItemMeta() != null){
						if (e.getCurrentItem().getItemMeta().getDisplayName() != null){
							int slot = e.getSlot();
							String name = e.getCurrentItem().getItemMeta().getDisplayName();
							ShopCustomer sc = new ShopCustomer(p);
							if (e.getClickedInventory().getTitle().equalsIgnoreCase(Var.INV_CASINOMAIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
								e.setCancelled(true);
								if (!CasinoMain.inserts.containsKey(p.getUniqueId())){
									if (slot == 13){
										DailyMain.click(p);
									}else if (slot == 20 || slot == 24 || slot == 30 || slot == 31 || slot == 32){
										if (e.getCurrentItem().getAmount() == 0){
											p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "This game is still in development.");
											p.playSound(p.getLocation(), Sound.NOTE_BASS, 5, 5);
											return;
										}
										if (slot == 20){
											CasinoMain.currentGame.put(p, CasinoGame.ROULETTE);
										}else if (slot == 24){
											CasinoMain.currentGame.put(p, CasinoGame.BLACKJACK);
										}else if (slot == 30){
											CasinoMain.currentGame.put(p, CasinoGame.COIN);
										}else if (slot == 31){
											CasinoMain.currentGame.put(p, CasinoGame.LEVER);
										}else if (slot == 32){
											CasinoMain.currentGame.put(p, CasinoGame.LOTTERY_TICKET);
											LotteryGame.buyInventory(p);
											return;
										}
										CasinoInventory.getInsertInventory(p, e.getClickedInventory());
									}
								}else{
									if (name.startsWith("ßc")){
										return;
									}
									if (slot == 20){
										CasinoMain.inserts.remove(p.getUniqueId());
										CasinoMain.inserts.put(p.getUniqueId(), 50);
									}else if (slot == 21){
										CasinoMain.inserts.remove(p.getUniqueId());
										CasinoMain.inserts.put(p.getUniqueId(), 100);
									}else if (slot == 22){
										CasinoMain.inserts.remove(p.getUniqueId());
										CasinoMain.inserts.put(p.getUniqueId(), 500);
									}else if (slot == 23){
										CasinoMain.inserts.remove(p.getUniqueId());
										CasinoMain.inserts.put(p.getUniqueId(), 1000);
									}else if (slot == 24){
										CasinoMain.inserts.remove(p.getUniqueId());
										CasinoMain.inserts.put(p.getUniqueId(), sc.getChips());
									}else{
										return;
									}
									if (CasinoMain.currentGame.get(p) == (CasinoGame.COIN)){
										CoinGame.start(p);
									}else if (CasinoMain.currentGame.get(p) == (CasinoGame.LEVER)){
										new LeverGame(p);
									}
								}
							}else if (e.getClickedInventory().getTitle().equalsIgnoreCase(Var.INV_CASINO_COIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
								e.setCancelled(true);
							}else if (e.getClickedInventory().getTitle().equalsIgnoreCase(Var.INV_CASINO_LOTTERY_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
								e.setCancelled(true);
								if (LotteryGame.invs.containsKey(p.getUniqueId())){
									LotteryGame.click(p, slot, e.getCurrentItem());
								}
							}else if (e.getClickedInventory().getTitle().equalsIgnoreCase(Var.INV_CASINO_LEVER_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
								e.setCancelled(true);
								if (slot == 33){
									try {
										LeverGame game = LeverGame.inLeverGame.get(p.getUniqueId());
										game.click();
									} catch (Exception e2) {
									}
								}
							}else if (e.getClickedInventory().getTitle().equalsIgnoreCase(Var.INV_CASINO_BUY_LOTTERY_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
								e.setCancelled(true);
								int i = slot;
								if (i == 9 || i == 10 || i == 11 || i == 18 || i == 19 || i == 20 || i == 27 || i == 28 || i == 29){
									p.openInventory(CasinoInventory.getMainInventory(p));
									CasinoMain.currentGame.remove(p);
								}else if (i == 15 || i == 16 || i == 17 || i == 24 || i == 25 || i == 26 || i == 33 || i == 34 || i == 35){
									if (sc.getChips() >= Var.CASINO_LOTTERY_PRICE){
										LotteryGame.start(p);
									}else{
										p.closeInventory();
										p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "ßcDir fehlen ß7" + (Var.CASINO_LOTTERY_PRICE-sc.getChips()) + " Chipsßc um den Kauf abzuschlieﬂen.");
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
