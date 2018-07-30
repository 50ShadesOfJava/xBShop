package de.elkman.casino.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.elkman.casino.main.CasinoMain;
import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.xblackjack.xbbroad.main.XBBroad;

public class CoinGame {

	public static ArrayList<UUID> inCoinGame = new ArrayList<>();
	
	public static HashMap<UUID, Inventory> invs = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	public static void start(Player p){
		Inventory inv = p.getServer().createInventory(null, 27, Var.INV_CASINO_COIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		
		for (int i = 0; i < 27; i++){
			inv.setItem(i, InventoryUtils.getBackgroundGlass(15));
		}
		
		Random r = new Random();
		int z = r.nextInt(2);
		switch (z) {
		case 0:
			inv.setItem(13, InventoryUtils.getItemStack("§a§lCoin", Material.GOLD_NUGGET, 1, null));
			break;
		case 1:
			inv.setItem(13, InventoryUtils.getItemStack("§c§lCoin", Material.getMaterial(372), 1, null));
			break;
		}
		
		boolean b = false;
		
		r = new Random();
		z = r.nextInt(2);
		switch (z) {
		case 0:
			b = false;
			break;
		case 1:
			b = true;
			break;
		}
		
		
		p.openInventory(inv);
		invs.put(p.getUniqueId(), inv);
		inCoinGame.add(p.getUniqueId());
		
		timer(p, inv, b, 50);
		
	}
	
	public static void timer(Player p, Inventory inv, boolean win, int delay){
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				nextRound(p, inv, win, delay);
				
			}
		}, delay);
	}
	
	@SuppressWarnings("deprecation")
	public static void nextRound(Player p, Inventory inv, boolean win, int delay){
		if (delay > 1000){
			int i = CasinoMain.inserts.get(p.getUniqueId());
			ShopCustomer sc = new ShopCustomer(p);
			if (win){
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
				sc.addChips(i, false);
				p.sendMessage(Main.getPrefix(PrefixColor.SUCCESS) + "§a§lCongratulations!");
				p.sendMessage(Main.getBracket(PrefixColor.SUCCESS) + "You earned §a" + i + " Chips§7.");
			}else{
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 5, 5);
				sc.removeChips(i, false);
				p.sendMessage(Main.getPrefix() + "§c§lBetter Luck next time!");
				p.sendMessage(Main.getBracket() + "You lost §c" + i + " Chips§7.");
			}
			invs.remove(p.getUniqueId());
			p.closeInventory();
		}else{
			win = !win;
			
			if (win){
				inv.setItem(13, InventoryUtils.getItemStack("§a§lCoin", Material.GOLD_NUGGET, 1, null));
			}else{
				inv.setItem(13, InventoryUtils.getItemStack("§c§lCoin", Material.getMaterial(372), 1, null));
			}
			p.playSound(p.getLocation(), Sound.WOOD_CLICK, 5, 5);
			
			delay = delay+(delay/6);
			timer(p, inv, win, delay);
		}
	}
	
	
	
	
	

}
