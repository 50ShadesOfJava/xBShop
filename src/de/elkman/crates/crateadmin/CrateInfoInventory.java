package de.elkman.crates.crateadmin;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.elkman.crates.main.Crate;
import de.elkman.crates.main.CrateSystem;
import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Var;
import de.xblackjack.xbbroad.main.XBBroad;

public class CrateInfoInventory implements Listener{

	public CrateInfoInventory(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@SuppressWarnings("deprecation")
	public static Inventory getInventory(Player p, Crate crate){
		Inventory inv = Main.getInstance().getServer().createInventory(null, 27, Var.INV_CRATEINFO_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		for (int i = 0; i < 27; i++){
			if (i == 10){
				if (!crate.isTaken()){
					inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Available Crate", Material.CHEST, 1, null));
				}else{
					inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Taken Crate", Material.ENDER_CHEST, 1, null));
				}
			}else if (i == 12){
				inv.setItem(i, InventoryUtils.getItemStack("§7Code:", Material.PAPER, 1, XBBroad.getAccentColor(p) + crate.getCode()));
			}else if (i == 13){
				inv.setItem(i, InventoryUtils.getItemStack("§7Price:", Material.GOLD_INGOT, 1, XBBroad.getAccentColor(p) + crate.getPrice().getType().toString() + ";" + "§7SubInfo: " + XBBroad.getAccentColor(p) + crate.getPrice().getInformationString()));
			}else if (i == 14){
				if (!crate.isTaken()){
					inv.setItem(i, InventoryUtils.getItemStack("§7Expires:", Material.BOOK, 1, XBBroad.getAccentColor(p) + new SimpleDateFormat("dd.MM.YYYY hh:mm").format(new Date(crate.getExpireDate().getTime()))));
				}else{
					inv.setItem(i, InventoryUtils.getItemStack("§7Taken at:", Material.BOOK, 1, XBBroad.getAccentColor(p) + new SimpleDateFormat("dd.MM.YYYY hh:mm").format(new Date(crate.getTakenDate().getTime()))));
				}
			}else if (i == 15 && crate.isTaken()){
				ItemStack stack = new ItemStack(Material.getMaterial(397), 1, (short) 3);
				SkullMeta smeta = (SkullMeta) stack.getItemMeta();
				smeta.setDisplayName("§7Taken by:");
				ArrayList<String> lore = new ArrayList<>();
				lore.add(XBBroad.getAccentColor(p) + crate.getTaker());
				smeta.setLore(lore);
				String owner = crate.getTaker();
				if (owner.contains("[DELETED BY ADMIN]")){
					owner = owner.replace("[DELETED BY ADMIN] ", "");
				}
				smeta.setOwner(owner);
				stack.setItemMeta(smeta);
				inv.setItem(i, stack);
			}else if (i == 16 && !crate.isTaken()){
				inv.setItem(i, InventoryUtils.getItemStack("§4Delete Crate", Material.BARRIER, 1, "§cThis action cannot be undone."));
			}else{
				inv.setItem(i, InventoryUtils.getBackgroundGlass(15));
			}
		}
		return inv;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if (e.getWhoClicked() instanceof Player){
			Player p = (Player) e.getWhoClicked();
			if (e.getClickedInventory() != null && e.getClickedInventory().getName() != null){
				if (e.getClickedInventory().getName().equalsIgnoreCase(Var.INV_CRATEINFO_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
					e.setCancelled(true);
					if (e.getSlot() == 16 && e.getInventory().getItem(10).getType() == Material.CHEST){
						//Delete Crate
						String code = e.getInventory().getItem(12).getItemMeta().getLore().get(0).substring(e.getInventory().getItem(12).getItemMeta().getLore().get(0).length()-6, e.getInventory().getItem(12).getItemMeta().getLore().get(0).length());
						CrateSystem.deleteCrate(p, "[DELETED BY ADMIN] " + p.getName(), code.toUpperCase());
						p.closeInventory();
					}else if (e.getSlot() == 10 && e.getInventory().getItem(10).getType() == Material.CHEST){
						CrateListInventorys.openCrateListInventory(p, 1, false);
					}else if (e.getSlot() == 10 && e.getInventory().getItem(10).getType() == Material.ENDER_CHEST){
						CrateListInventorys.openCrateListInventory(p, 1, true);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getTimeDifference(Timestamp timestamp){
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return (timestamp.getMonth()-now.getMonth()) + " Months, " + (timestamp.getDay()-now.getDate()) + " Days and " + (timestamp.getHours()-now.getHours()) + " Hours";
	}
}
