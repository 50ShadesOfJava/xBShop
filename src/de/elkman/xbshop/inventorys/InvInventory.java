package de.elkman.xbshop.inventorys;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.utils.players.SelectedProducts;
import de.elkman.xbshop.utils.products.ProductUtils;
import de.elkman.xbshop.utils.products.ShopCategory;
import de.elkman.xbshop.utils.products.ShopProduct;
import de.xblackjack.xbbroad.main.XBBroad;

public class InvInventory {

	public static Inventory getInvInventory(Player p){
		Inventory inv;
		if (p.getOpenInventory().getTopInventory() == null || p.getOpenInventory().getTopInventory().getName() != Var.INV_INVENTAR_TITLE.replaceAll("&", XBBroad.getAccentColor(p))){
			inv = p.getServer().createInventory(null, 54, Var.INV_INVENTAR_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		}else{
			inv = p.getOpenInventory().getTopInventory();
		}
		if (!ShopInventory.playerCategory.containsKey(p)){
			ShopInventory.playerCategory.put(p, ShopCategory.PARTICLE);
		}
		ShopCustomer sc = new ShopCustomer(p);
		ArrayList<ShopProduct> list = new ArrayList<>();
		list.addAll(sc.getBuyedProducts(sc.getWatchingCategory()));
		boolean empty = false;
		if (list.isEmpty()){
			empty = true;
		}
		
		for (int i = 0; i < 54; i++){
			if (i > 9 && i != 17 && i != 18 && i != 26 && i != 27 && i != 35 && i != 36 && i < 44){
				if (sc.getWatchingCategory() == ShopCategory.ARMOR){
					inv.setItem(i, InventoryUtils.getBackgroundGlass(7));
				}else{
					if (list.isEmpty()){
						inv.setItem(i, InventoryUtils.getBackgroundGlass(7));
					}else{
						ItemStack stack = list.get(0).getStack(p);
						ItemMeta meta = stack.getItemMeta();
						ArrayList<String> lore = new ArrayList<>();
						lore.addAll(meta.getLore());
						lore.remove("§a§lIn deinem Besitz");
						meta.setLore(lore);
						stack.setItemMeta(meta);
						if (SelectedProducts.getSelectedProducts(p, sc.getWatchingCategory()).contains(list.get(0))){
							stack = InventoryUtils.addGlow(stack);
						}
						inv.setItem(i, stack);
						list.remove(list.get(0));
					}
				}
			}else{
				if (i > 0 && i < 6 || i == 7){
					if (i == 1){
						inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Partikel", Material.GLOWSTONE_DUST, 1, "§e" + sc.getBuyedProducts(ShopCategory.PARTICLE).size() + " §7" + ProductUtils.getProdukteString(sc.getBuyedProducts(ShopCategory.PARTICLE).size()), sc.getWatchingCategory() == ShopCategory.PARTICLE));
					}else if (i == 2){
						inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Rüstung", Material.IRON_CHESTPLATE, 1, "§e" + sc.getBuyedProducts(ShopCategory.ARMOR).size() + " §7" + ProductUtils.getProdukteString(sc.getBuyedProducts(ShopCategory.ARMOR).size()), sc.getWatchingCategory() == ShopCategory.ARMOR));
					}else if (i == 3){
						inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Fähigkeiten", Material.POTION, 1, "§e" + sc.getBuyedProducts(ShopCategory.ABILITY).size() + " §7" + ProductUtils.getProdukteString(sc.getBuyedProducts(ShopCategory.ABILITY).size()), sc.getWatchingCategory() == ShopCategory.ABILITY));
					}else if (i == 4){
						inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Gadgets", Material.FLINT_AND_STEEL, 1, "§e" + sc.getBuyedProducts(ShopCategory.GADGET).size() + " §7" + ProductUtils.getProdukteString(sc.getBuyedProducts(ShopCategory.GADGET).size()), sc.getWatchingCategory() == ShopCategory.GADGET));
					}else if (i == 5){
						inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Spezial", Material.SNOW_BALL, 1, "§e" + sc.getBuyedProducts(ShopCategory.SPECIAL).size() + " §7" + ProductUtils.getProdukteString(sc.getBuyedProducts(ShopCategory.SPECIAL).size()), sc.getWatchingCategory() == ShopCategory.SPECIAL));
					}else if (i == 7){
						inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "§b§lDein Inventar", Material.CHEST, 1, null));
					}
				}else{
					inv.setItem(i, InventoryUtils.getBackgroundGlass(15));
				}
			}
		}
		if (sc.getWatchingCategory() == ShopCategory.ARMOR){
			for (ShopProduct sp : list){
				ItemStack stack = sp.getStack(p);
				ItemMeta meta = stack.getItemMeta();
				ArrayList<String> lore = new ArrayList<>();
				lore.addAll(meta.getLore());
				lore.remove("§a§lIn deinem Besitz");
				meta.setLore(lore);
				stack.setItemMeta(meta);
				if (SelectedProducts.getSelectedProducts(p, sc.getWatchingCategory()).contains(sp)){
					stack = InventoryUtils.addGlow(stack);
				}
				inv.setItem(sp.getSlot(), stack);
			}
		}
		if (empty){
			inv.setItem(22, InventoryUtils.getItemStack("§cKeine Produkte", Material.BARRIER, 1, "§7Du kannst dir Produkte;im §6§lShop §7kaufen."));
		}
		return inv;
	}
}
