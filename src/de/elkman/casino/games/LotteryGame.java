package de.elkman.casino.games;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.inventorys.WarenkorbInventory;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.xblackjack.xbbroad.main.XBBroad;
import de.elkman.xbshop.main.Var;

public class LotteryGame {

	public static HashMap<UUID, Inventory> invs = new HashMap<>();
	
	public static HashMap<UUID, Integer> clicked = new HashMap<>();
	
	public static HashMap<UUID, Integer> win = new HashMap<>();
	
	public static void start(Player p){
		Inventory inv = p.getServer().createInventory(null, 45, Var.INV_CASINO_LOTTERY_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		
		ShopCustomer sc = new ShopCustomer(p);
		p.sendMessage(Main.getPrefix() + "§7Good Luck!");
		sc.removeChips(Var.CASINO_LOTTERY_PRICE, true);
		
		for (int i = 0; i < 45; i++){
			inv.setItem(i, InventoryUtils.getBackgroundGlass(15));
		}
		
		p.openInventory(inv);
		invs.put(p.getUniqueId(), inv);
		clicked.put(p.getUniqueId(), 0);
		win.put(p.getUniqueId(), 0);
	}
	
	@SuppressWarnings("deprecation")
	public static void buyInventory(Player p){
		Inventory inv = p.getServer().createInventory(null, 45, Var.INV_CASINO_BUY_LOTTERY_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		ShopCustomer sc = new ShopCustomer(p);
		
		for (int i = 0; i < 45; i++){
			if (i == 9 || i == 10 || i == 11 || i == 18 || i == 19 || i == 20 || i == 27 || i == 28 || i == 29){
				ItemStack glas = (new ItemStack(160, 1, (short) 0, (byte) 14));
				ItemMeta meta = glas.getItemMeta();
				meta.setDisplayName("§c§lCancel");
				glas.setItemMeta(meta);
				inv.setItem(i, glas);
			}else if (i == 15 || i == 16 || i == 17 || i == 24 || i == 25 || i == 26 || i == 33 || i == 34 || i == 35){
				ItemStack glas = (new ItemStack(160, 1, (short) 0, (byte) 5));
				ItemMeta meta = glas.getItemMeta();
				meta.setDisplayName("§a§lConfirm purchase");
				glas.setItemMeta(meta);
				if (sc.getChips() >= Var.CASINO_LOTTERY_PRICE){
					glas = InventoryUtils.addGlow(glas);
				}
				inv.setItem(i, glas);
			}else if (i == 12 || i == 13 || i == 14 || i == 21 || i == 23 || i == 30 || i == 31 || i == 32){
				inv.setItem(i, InventoryUtils.getBackgroundGlass(0));
			}else if (i == 22){
				inv.setItem(i, InventoryUtils.getItemStack("§aLottery Ticket", Material.PAPER, 1, null));
			}else if (i == 4){
				inv.setItem(i, WarenkorbInventory.getInduvidualRechnung(p, "Lottery Ticket", Var.CASINO_LOTTERY_PRICE, "Casino"));
			}else{
				inv.setItem(i, InventoryUtils.getBackgroundGlass(15));
			}
		}
		p.openInventory(inv);
	}
	
	public static void click(Player p, int slot, ItemStack stack){
		if (stack.getType() == Material.STAINED_GLASS_PANE){
			int c = clicked.get(p.getUniqueId());
			c++;
			clicked.remove(p.getUniqueId());
			clicked.put(p.getUniqueId(), c);
			int chips = 0;
			Random r = new Random();
			int z = r.nextInt(2);
			switch (z) {
			case 0:
				Random r2 = new Random();
				chips = r2.nextInt(220);
				break;
			}
			int w = win.get(p.getUniqueId());
			w = w + chips;
			win.remove(p.getUniqueId());
			win.put(p.getUniqueId(), w);
			invs.get(p.getUniqueId()).setItem(slot, getLottoItemstack(chips));
			if (chips == 0){
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 5, 5);
			}else{
				p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5, 5);
			}
			if (c == 5){
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
				ShopCustomer sc = new ShopCustomer(p);
				sc.addChips(w, false);
				p.sendMessage(Main.getPrefix(PrefixColor.SUCCESS) + "You earned §a" + w + " Chips§7.");
				invs.remove(p.getUniqueId());
				win.remove(p.getUniqueId());
				clicked.remove(p.getUniqueId());
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getLottoItemstack(int chips){
		if (chips == 0){
			ItemStack stack = new ItemStack(Material.getMaterial(372));
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("§c+ 0 Chips");
			stack.setItemMeta(meta);
			return stack;
		}else if (chips < 100){
			ItemStack stack = new ItemStack(Material.GOLD_NUGGET);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("§a+ " + chips + " Chips");
			stack.setItemMeta(meta);
			return stack;
		}else if (chips < 150){
			ItemStack stack = new ItemStack(Material.GOLD_INGOT);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("§a§l+ " + chips + " Chips");
			stack.setItemMeta(meta);
			return stack;
		}else if (chips >= 150){
			ItemStack stack = new ItemStack(Material.GOLD_BLOCK);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("§e§k!!§a §l+ " + chips + " Chips §e§k!!");
			stack.setItemMeta(meta);
			return stack;
		}
		ItemStack stack = new ItemStack(Material.getMaterial(372));
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§c+ 0 Chips");
		stack.setItemMeta(meta);
		return stack;
	}
}