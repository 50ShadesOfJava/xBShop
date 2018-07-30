package de.elkman.xbshop.inventorys;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Var;
import de.xblackjack.xbbroad.main.XBBroad;

public class MainInventory {
	
	public static Inventory getMainInventory(Player p){
		Inventory inv = p.getServer().createInventory(null, 45, Var.INV_MAIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		
		for (int i = 0; i < 45; i++){
			inv.setItem(i, InventoryUtils.getBackgroundGlass(15));
		}
		ShopCustomer sc = new ShopCustomer(p);
		inv.setItem(11, InventoryUtils.getItemStack("§6§lShoppen gehen", Material.MINECART, 1, "§7Kaufe mit deinen §6" + sc.getChips() + " Chips §7Partikel,;Rüstunsteile, Fähigkeiten und Gadgets!"));
		inv.setItem(15, InventoryUtils.getItemStack("§5§lIm Casino zocken", Material.GOLD_NUGGET, 1, "§7Versuche deine §5" + sc.getChips() + " Chips;zu verdoppeln indem du;Glücksspiele spielst!"));
		inv.setItem(31, InventoryUtils.getItemStack("§b§lInventar", Material.CHEST, 1, "§7Sieh dir deine gekauften Produkte;an und aktiviere/deaktiviere sie!"));
		
		return inv;
	}
}
