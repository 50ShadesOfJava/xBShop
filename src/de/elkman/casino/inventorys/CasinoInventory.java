package de.elkman.casino.inventorys;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elkman.casino.main.CasinoMain;
import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.utils.BooleanUtils;
import de.xblackjack.xbbroad.main.XBBroad;

public class CasinoInventory {

	public static Inventory allgemeinInv;
	
	public static Inventory getMainInventory(Player p){
		ShopCustomer sc = new ShopCustomer(p);
		Inventory inv = p.getServer().createInventory(null, 45, Var.INV_CASINOMAIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		CasinoMain.currentGame.remove(p);
		CasinoMain.inserts.remove(p.getUniqueId());
		
		if (allgemeinInv == null){
			allgemeinInv = p.getServer().createInventory(null, 45, Var.INV_CASINOMAIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
			for (int i = 0; i < 45; i++){
				if (getSlots(14).contains(i)){
					allgemeinInv.setItem(i, InventoryUtils.getBackgroundGlass(14));
				}else if (getSlots(13).contains(i)){
					allgemeinInv.setItem(i, InventoryUtils.getBackgroundGlass(13));
				}else if (i == 20){
					allgemeinInv.setItem(i, InventoryUtils.getItemStack("§aRoulette", Material.FIREBALL, 0, "§c§lComing soon"));
				}else if (i == 24){
					allgemeinInv.setItem(i, InventoryUtils.getItemStack("§aBlackJack", Material.GLOWSTONE_DUST, 0, "§c§lComing soon"));
				}else if (i == 30){
					allgemeinInv.setItem(i, InventoryUtils.getItemStack("§aCoin", Material.GOLD_NUGGET, 1, null));
				}else if (i == 31){
					allgemeinInv.setItem(i, InventoryUtils.getItemStack("§aLever", Material.LEVER, 1, null));
				}else if (i == 32){
					allgemeinInv.setItem(i, InventoryUtils.getItemStack("§aLottery Ticket", Material.PAPER, 1, null));
				}else{
					allgemeinInv.setItem(i, InventoryUtils.getBackgroundGlass(5));
				}
			}
		}
		
		inv.setContents(allgemeinInv.getContents());
			
		ItemStack stack = new ItemStack(Material.STORAGE_MINECART);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§e§lDaily Chips");
		ArrayList<String> lore = new ArrayList<>();
		if (sc.hasDailyChipsTaken()){
			lore.add("§c§lAlready taken");
			stack.setType(Material.MINECART);
		}else{
			lore.add("§aYour reward:");
			lore.add("§7+ " + Var.DAILY_CHIPS);
			lore.add("§7+ " + (sc.getDailyChipsStreak()*Var.DAILY_STREAK) + " (Streak of " + sc.getDailyChipsStreak() + ")");
			if (p.hasPermission("xbshop.premium")){
				lore.add("§7+ " + Var.DAILY_PREMIUM + " (Premium boost)");
			}else{
				lore.add("§7§m+ " + Var.DAILY_PREMIUM + " (Premium boost)");
			}
		}
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		
		inv.setItem(13, stack);
		
		return inv;
	}
	
	public static void getInsertInventory(Player p, Inventory inv){
		for (Integer i : getSlots(5)){
			inv.setItem(i, InventoryUtils.getBackgroundGlass(5));
		}
		ShopCustomer sc = new ShopCustomer(p);
		CasinoMain.inserts.put(p.getUniqueId(), 0);
		inv.setItem(20, InventoryUtils.getItemStack(BooleanUtils.getColor(sc.getChips() >= 50) + "50 Chips", Material.NETHER_BRICK_ITEM, 1, null));
		inv.setItem(21, InventoryUtils.getItemStack(BooleanUtils.getColor(sc.getChips() >= 100) + "100 Chips", Material.CLAY_BRICK, 1, null));
		inv.setItem(22, InventoryUtils.getItemStack(BooleanUtils.getColor(sc.getChips() >= 500) + "500 Chips", Material.IRON_INGOT, 1, null));
		inv.setItem(23, InventoryUtils.getItemStack(BooleanUtils.getColor(sc.getChips() >= 1000) + "1000 Chips", Material.GOLD_INGOT, 1, null));
		inv.setItem(24, InventoryUtils.getItemStack(BooleanUtils.getColor(sc.getChips() > 1000) + "All In §7(" + sc.getChips() + " Chips)", Material.DIAMOND, 1, null));
	}
	
	public static ArrayList<Integer> getSlots(int color){
		ArrayList<Integer> list = new ArrayList<>();
		if (color == 14){
			list.add(0);
			list.add(1);
			list.add(7);
			list.add(8);
			list.add(9);
			list.add(17);
			list.add(27);
			list.add(35);
			list.add(36);
			list.add(37);
			list.add(43);
			list.add(44);
		}else if (color == 13){
			list.add(2);
			list.add(3);
			list.add(4);
			list.add(5);
			list.add(6);
			list.add(10);
			list.add(16);
			list.add(18);
			list.add(26);
			list.add(28);
			list.add(34);
			list.add(38);
			list.add(39);
			list.add(40);
			list.add(41);
			list.add(42);
		}else if (color == 5){
			list.add(11);
			list.add(12);
			list.add(13);
			list.add(14);
			list.add(15);
			list.add(19);
			list.add(25);
			list.add(29);
			list.add(30);
			list.add(31);
			list.add(32);
			list.add(33);
		}
		return list;
	}
}
