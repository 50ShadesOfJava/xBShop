package de.elkman.crates.crateadmin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.elkman.crates.main.Crate;
import de.elkman.crates.main.CrateData;
import de.elkman.crates.main.CratePrice;
import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.sql.MySQL;
import de.xblackjack.xbbroad.main.XBBroad;

public class CrateListInventorys implements Listener{

	public CrateListInventorys(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public static ArrayList<Crate> allcrates = new ArrayList<>();
	
	public static void openCrateListInventory(Player p, int page, boolean takenchests){
		Inventory inv;
		if (!takenchests){
			inv = p.getServer().createInventory(null, 54, Var.INV_CRATELIST_AVAILABLE_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		}else{
			inv = p.getServer().createInventory(null, 54, Var.INV_CRATELIST_TAKEN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		}
		p.openInventory(inv);
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			public void run() {
				if (allcrates.isEmpty()){
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM " + CrateData.CRATES_TABLE);
						ResultSet rs = ps.executeQuery();
						while (rs.next()){
							Crate crate = new Crate(rs.getString("Code"), null, new CratePrice(rs.getString("Price")), Timestamp.valueOf(rs.getString("ExpireDate")), null);
							allcrates.add(crate);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM " + CrateData.OLD_CRATES_TABLE);
						ResultSet rs = ps.executeQuery();
						while (rs.next()){
							Crate crate = new Crate(rs.getString("Code"), rs.getString("Taker"), new CratePrice(rs.getString("Price")), null, Timestamp.valueOf(rs.getString("Date")));
							allcrates.add(crate);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), ()->{
						allcrates.clear();
					}, 20*30);
				}
				ArrayList<Crate> crates = new ArrayList<>();
				for (Crate crate : allcrates){
					if (crate.isTaken() == takenchests){
						crates.add(crate);
					}
				}
				if (page != 1){
					int max = (page-1)*36;
					for (int i = 0; i < max; i++){
						if (!crates.isEmpty()){
							crates.remove(crates.get(0));
						}
					}
				}
				boolean lastpage = (crates.size() <= 36);
				for (int i = 0; i < 54; i++){
					if (i == 4){
						if (!takenchests){
							inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Page " + page, Material.CHEST, page, null));
						}else{
							inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Page " + page, Material.ENDER_CHEST, page, null));
						}
					}else if (i == 53){
						if (!lastpage){
							inv.setItem(i, InventoryUtils.getBackgroundGlass("§aNext page §m--§a>", 5, (page+1), null));
						}else{
							inv.setItem(i, InventoryUtils.getBackgroundGlass("§cNext page §m--§c>", 14, 1, null));
						}
					}else if (i == 45){
						if (page > 1){
							inv.setItem(i, InventoryUtils.getBackgroundGlass("§a<§m--§a Last page", 5, (page-1), null));
						}else{
							inv.setItem(i, InventoryUtils.getBackgroundGlass("§c<§m--§c Last page", 14, 1, null));
						}
					}else if (i > 45 || i < 9){
						inv.setItem(i, InventoryUtils.getBackgroundGlass(7));
					}else if (!crates.isEmpty()){
						Crate crate = crates.get(0);
						ItemStack stack = InventoryUtils.getItemStack("§e" + crate.getCode(), crate.getPrice().getMaterial(), 1, "§7Price: §e" + crate.getPrice().getType() + " " + crate.getPrice().getInformationString());
						inv.setItem(i, stack);
						crates.remove(crates.get(0));
					}
					p.updateInventory();
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					public void run() {
						p.updateInventory();
					}
				}, 20);
			}
		});
	}

	@EventHandler
	public void onClick(InventoryClickEvent e){
		if (e.getWhoClicked() instanceof Player){
			Player p = (Player) e.getWhoClicked();
			if (e.getClickedInventory() != null && e.getClickedInventory().getName() != null){
				if (e.getClickedInventory().getName().equalsIgnoreCase(Var.INV_CRATELIST_AVAILABLE_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
					e.setCancelled(true);
					int page = e.getClickedInventory().getItem(4).getAmount();
					if (e.getSlot() == 45){
						if (page > 1){
							openCrateListInventory(p, page-1, false);
						}
					}else if (e.getSlot() == 53){
						if (e.getClickedInventory().getItem(53).getItemMeta().getDisplayName().startsWith("§a")){
							openCrateListInventory(p, page+1, false);
						}
					}else{
						if (e.getCurrentItem() != null){
							if (e.getCurrentItem().getItemMeta() != null){
								if (e.getCurrentItem().getType() == Material.PAPER){
									String name = e.getCurrentItem().getItemMeta().getDisplayName();
									String code = name.substring(name.length()-6, name.length());
									p.closeInventory();
									Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
										Crate crate = CrateData.loadCrateFormDatabase(CrateData.CRATES_TABLE, code);
										p.openInventory(CrateInfoInventory.getInventory(p, crate));
									});
								}
							}
						}
					}
				}else if (e.getClickedInventory().getName().equalsIgnoreCase(Var.INV_CRATELIST_TAKEN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
					e.setCancelled(true);
					int page = e.getClickedInventory().getItem(4).getAmount();
					if (e.getSlot() == 45){
						if (page > 1){
							openCrateListInventory(p, page-1, true);
						}
					}else if (e.getSlot() == 53){
						if (e.getClickedInventory().getItem(53).getItemMeta().getDisplayName().startsWith("§a")){
							openCrateListInventory(p, page+1, true);
						}
					}else{
						if (e.getCurrentItem() != null){
							if (e.getCurrentItem().getItemMeta() != null){
								if (e.getCurrentItem().getType() == Material.PAPER){
									String name = e.getCurrentItem().getItemMeta().getDisplayName();
									String code = name.substring(name.length()-6, name.length());
									p.closeInventory();
									Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
										Crate crate = CrateData.loadCrateFormDatabase(CrateData.OLD_CRATES_TABLE, code);
										p.openInventory(CrateInfoInventory.getInventory(p, crate));
									});
								}
							}
						}
					}
				}
			}
		}
	}
	
}
