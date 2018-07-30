package de.elkman.xbshop.inventorys;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.utils.products.ProductUtils;
import de.elkman.xbshop.utils.products.ShopProduct;
import de.xblackjack.xbbroad.main.XBBroad;

public class WarenkorbInventory {

	@SuppressWarnings("deprecation")
	public static Inventory getWarenkorbGUI(Player p){
		Inventory inv;
		if (p.getOpenInventory().getTopInventory() == null || p.getOpenInventory().getTopInventory().getName() != Var.INV_WARENKORB_TITLE.replaceAll("&", XBBroad.getAccentColor(p))){
			inv = p.getServer().createInventory(null, 45, Var.INV_WARENKORB_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		}else{
			inv = p.getOpenInventory().getTopInventory();
		}
		ShopCustomer sc = new ShopCustomer(p);
		ArrayList<ShopProduct> list = new ArrayList<>();
		list.addAll(sc.getWarenkorb());
		
		
		for (int i = 0; i < 45; i++){
			if (i == 4){
				inv.setItem(i, getRechnung(p));
			}else if (i < 9 || i > 35){
				inv.setItem(i, InventoryUtils.getBackgroundGlass(15));
			}else if (i == 12 || i == 13 || i == 14 || i == 21 || i == 22 || i == 23 || i == 30 || i == 31 || i == 32){
				if (list.isEmpty()){
					inv.setItem(i, InventoryUtils.getBackgroundGlass(0));
				}else{
					ItemStack stack = list.get(0).getStack(p);
					ItemMeta meta = stack.getItemMeta();
					ArrayList<String> lore = new ArrayList<>();
					lore.addAll(meta.getLore());
					lore.remove("§7 ➥ §a§lAktiviert");
					lore.remove("§7 ➥ §c§lDeaktiviert");
					meta.setLore(lore);
					stack.setItemMeta(meta);
					inv.setItem(i, stack);
					list.remove(list.get(0));
				}
			}else if (i == 9 || i == 10 || i == 11 || i == 18 || i == 19 || i == 20 || i == 27 || i == 28 || i == 29){
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
				if (sc.getChips() >= getWarenkorbPrice(sc)){
					glas = InventoryUtils.addGlow(glas);
				}
				inv.setItem(i, glas);
			}
		}
		return inv;
	}
	
	public static ItemStack getRechnung(Player p){
		ShopCustomer sc = new ShopCustomer(p);
		ItemStack stack = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§6§lBill:");
		ArrayList<String> lore = new ArrayList<>();
		int price = getWarenkorbPrice(sc);
		for (ShopProduct sp : sc.getWarenkorb()){
			lore.add("§7(" + ProductUtils.getCategoryName(sp.getCategory()) + ") §a" + sp.getName() + "§7: §f+ " + sp.getPrice() + " Chips");
		}
		lore.add("");
		lore.add("§6§lTotal: §f§n" + price + " Chips");
		lore.add("");
		if (price > sc.getChips()){
			lore.add("§7You have: §c" + sc.getChips() + " Chips");
		}else{
			lore.add("§7You have: §b" + sc.getChips() + " Chips");
			lore.add("§7After buying: §b" + (sc.getChips()-price) + " Chips");
		}
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static ItemStack getInduvidualRechnung(Player p, String name, int price, String category){
		ShopCustomer sc = new ShopCustomer(p);
		ItemStack stack = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§6§lBill:");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7(" + category + ") §a" + name + "§7: §f+ " + price + " Chips");
		lore.add("");
		lore.add("§6§lTotal: §f§n" + price + " Chips");
		lore.add("");
		if (price > sc.getChips()){
			lore.add("§7You have: §c" + sc.getChips() + " Chips");
		}else{
			lore.add("§7You have: §b" + sc.getChips() + " Chips");
			lore.add("§7After buying: §b" + (sc.getChips()-price) + " Chips");
		}
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static int getWarenkorbPrice(ShopCustomer sc){
		int gesamt = 0;
		for (ShopProduct sp : sc.getWarenkorb()){
			gesamt = gesamt + sp.getPrice();
		}
		return gesamt;
	}
}
