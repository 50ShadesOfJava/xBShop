package de.elkman.xbshop.inventorys;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.utils.products.ProductUtils;
import de.elkman.xbshop.utils.products.ShopCategory;
import de.elkman.xbshop.utils.products.ShopProduct;
import de.xblackjack.xbbroad.main.XBBroad;

public class ShopInventory {

	public static HashMap<Player, ArrayList<ShopProduct>> warenkorb = new HashMap<>();
	public static HashMap<Player, ShopCategory> playerCategory = new HashMap<>();

	public static Inventory getShopInventory(Player p){
		Inventory inv;
		if (p.getOpenInventory().getTopInventory() == null || p.getOpenInventory().getTopInventory().getName() != Var.INV_SHOP_TITLE.replaceAll("&", XBBroad.getAccentColor(p))){
			inv = p.getServer().createInventory(null, 54, Var.INV_SHOP_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		}else{
			inv = p.getOpenInventory().getTopInventory();
		}
		if (!warenkorb.containsKey(p)){
			warenkorb.put(p, new ArrayList<ShopProduct>());
		}
		if (!playerCategory.containsKey(p)){
			playerCategory.put(p, ShopCategory.PARTICLE);
		}
		ShopCustomer sc = new ShopCustomer(p);
		ArrayList<ShopProduct> list = new ArrayList<>();
		list.addAll(ProductUtils.getProducts(sc.getWatchingCategory()));
		
		
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
						lore.remove("§7 ➥ §a§lAktiviert");
						lore.remove("§7 ➥ §c§lDeaktiviert");
						meta.setLore(lore);
						stack.setItemMeta(meta);
						inv.setItem(i, stack);
						list.remove(list.get(0));
					}
				}
			}else{
				if (i > 0 && i < 6 || i == 7){
					if (i == 1){
						inv.setItem(i, InventoryUtils.getItemStack("§6Partikel", Material.GLOWSTONE_DUST, 1, "§e" + ProductUtils.getProducts(ShopCategory.PARTICLE).size() + " §7" + ProductUtils.getProdukteString(ProductUtils.getProducts(ShopCategory.PARTICLE).size()), sc.getWatchingCategory() == ShopCategory.PARTICLE));
					}else if (i == 2){
						inv.setItem(i, InventoryUtils.getItemStack("§6Rüstung", Material.IRON_CHESTPLATE, 1, "§e" + ProductUtils.getProducts(ShopCategory.ARMOR).size() + " §7" + ProductUtils.getProdukteString(ProductUtils.getProducts(ShopCategory.ARMOR).size()), sc.getWatchingCategory() == ShopCategory.ARMOR));
					}else if (i == 3){
						inv.setItem(i, InventoryUtils.getItemStack("§6Fähigkeiten", Material.POTION, 1, "§e" + ProductUtils.getProducts(ShopCategory.ABILITY).size() + " §7" + ProductUtils.getProdukteString(ProductUtils.getProducts(ShopCategory.ABILITY).size()), sc.getWatchingCategory() == ShopCategory.ABILITY));
					}else if (i == 4){
						inv.setItem(i, InventoryUtils.getItemStack("§6Gadgets", Material.FLINT_AND_STEEL, 1, "§e" + ProductUtils.getProducts(ShopCategory.GADGET).size() + " §7" + ProductUtils.getProdukteString(ProductUtils.getProducts(ShopCategory.GADGET).size()), sc.getWatchingCategory() == ShopCategory.GADGET));
					}else if (i == 5){
						inv.setItem(i, InventoryUtils.getItemStack("§6Spezial", Material.SNOW_BALL, 1, "§e" + ProductUtils.getProducts(ShopCategory.SPECIAL).size() + " §7" + ProductUtils.getProdukteString(ProductUtils.getProducts(ShopCategory.SPECIAL).size()), sc.getWatchingCategory() == ShopCategory.SPECIAL));
					}else if (i == 7){
						if (sc.getWarenkorb().isEmpty()){
							inv.setItem(i, InventoryUtils.getItemStack("§6§lWarenkorb", Material.MINECART, 0, "§cKeine Produkte"));
						}else{
							inv.setItem(i, InventoryUtils.getItemStack("§6§lWarenkorb", Material.STORAGE_MINECART, sc.getWarenkorb().size(), "§a" + sc.getWarenkorb().size() + " " + ProductUtils.getProdukteString(sc.getWarenkorb().size())));
						}
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
				lore.remove("§7 ➥ §a§lAktiviert");
				lore.remove("§7 ➥ §c§lDeaktiviert");
				meta.setLore(lore);
				stack.setItemMeta(meta);
				inv.setItem(sp.getSlot(), stack);
			}
		}
		return inv;
	}
}
