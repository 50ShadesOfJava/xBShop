package de.elkman.crates.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elkman.crates.crateadmin.CrateInfoInventory;
import de.elkman.crates.main.AnvilGUI;
import de.elkman.crates.main.Crate;
import de.elkman.crates.main.CrateData;
import de.elkman.crates.main.AnvilGUI.AnvilClickEvent;
import de.elkman.crates.main.AnvilGUI.AnvilSlot;
import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.xblackjack.xbbroad.main.XBBroad;

public class CrateInfoCommand implements CommandExecutor{

	public CrateInfoCommand(Main plugin) {
		plugin.getCommand("crateinfo").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (!p.hasPermission("crates.info") && !p.hasPermission("crates.admin") && !p.getName().equalsIgnoreCase("_ELKMAN_")){
				p.sendMessage(XBBroad.xbUtils.noPermMsg());
				return true;
			}
			if (args.length == 0){
				AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler(){
					@Override
					public void onAnvilClick(AnvilClickEvent event) {
						 if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
							 event.setWillClose(true);
                             event.setWillDestroy(true);
                             p.performCommand("crateinfo " + event.getName().toUpperCase());
	                     }else{
	                    	 event.setWillClose(false);
	                    	 event.setWillDestroy(false);
	                     }
					}
				});
				gui.setSlot(AnvilSlot.INPUT_LEFT, InventoryUtils.getItemStack("Enter the Code", Material.PAPER, 1, "Please enter the Code.;"));
				gui.open();
			}else{
				String code = args[0].toUpperCase();
				p.sendMessage(Main.getPrefix() + "Loading Crate...");
				Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
					Crate crate;
					if (CrateData.crateIsAvailable(code)){
						crate = CrateData.loadCrateFormDatabase(CrateData.CRATES_TABLE, code);
					}else if (CrateData.crateWasTaken(code)){
						crate = CrateData.loadCrateFormDatabase(CrateData.OLD_CRATES_TABLE, code);
					}else{
						p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "§7This Crate don't exists.");
						return;
					}
					p.openInventory(CrateInfoInventory.getInventory(p, crate));
				});
			}
		}
		return true;
	}
}
