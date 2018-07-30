package de.elkman.crates.crateadmin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Var;
import de.xblackjack.xbbroad.main.XBBroad;

public class CrateAdminInventory implements Listener{

	public CrateAdminInventory(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public static Inventory getInventory(Player p){
		Inventory inv = Main.getInstance().getServer().createInventory(null, 27, Var.INV_CRATEADMIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)));
		for (int i = 0; i < 27; i++){
			if (i == 10){
				inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Available Crates", Material.CHEST, 1, null));
			}else if (i == 11){
				inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Taken Crates", Material.ENDER_CHEST, 1, null));
			}else if (i == 13){
				inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Crate-Info", Material.PAPER, 1, null));
			}else if (i == 14){
				inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Create new Crate(s)", Material.WORKBENCH, 1, null));
			}else if (i == 16){
				inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(p) + "Create a PDF", Material.EMPTY_MAP, 1, null));
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
				if (e.getClickedInventory().getName().equalsIgnoreCase(Var.INV_CRATEADMIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
					e.setCancelled(true);
					if (e.getSlot() == 13){
						p.performCommand("crateinfo");
					}else if (e.getSlot() == 10){
						CrateListInventorys.openCrateListInventory(p, 1, false);
					}else if (e.getSlot() == 11){
						CrateListInventorys.openCrateListInventory(p, 1, true);
					}else if (e.getSlot() == 16){
						p.sendMessage(Main.getPrefix() + "§7You can print the CrateCodes here:");
						p.sendMessage(Var.getPDF());
//						TextComponent component = new TextComponent(Main.getBracket() + "§7Click §ehere§7 to copy the URL.");
//						component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7§oClick").create()));
//						component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Var.getPDF()));
//						p.spigot().sendMessage(component);
						
//						IChatBaseComponent chat = ChatSerializer.a("{\"text\":\"§7Or click here\",\"extra\":"
//								+ "[{\"text\":\" here\",\"hoverEvent\":{\"action\":\"show_text\", "
//								+ "\"value\":\"§7click here\"},\"clickEvent\":{\"action\":\"open_url\",\"value\":"
//								+ "\"" + Var.getPDF() + "\"}}]}");
//								+ "\"xblackjack.de\"}}]}");
//						
//						PacketPlayOutChat packet = new PacketPlayOutChat(chat);
//						((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
						p.closeInventory();
					}
				}
			}
		}
	}
	
}
