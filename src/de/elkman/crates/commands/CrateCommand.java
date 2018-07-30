package de.elkman.crates.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elkman.crates.main.AnvilGUI;
import de.elkman.crates.main.CrateSystem;
import de.elkman.crates.main.AnvilGUI.AnvilClickEvent;
import de.elkman.crates.main.AnvilGUI.AnvilSlot;
import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.main.Main;
import de.xblackjack.xbbroad.main.XBBroad;
import de.xblackjack.xbbroad.utils.PlayerStats;

public class CrateCommand implements CommandExecutor{

	public CrateCommand(Main plugin) {
		plugin.getCommand("crate").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (args.length == 0){
				AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler(){
					@Override
					public void onAnvilClick(AnvilClickEvent event) {
						PlayerStats stats = XBBroad.getXbBroad().getPlayerStats(p);
						if (!stats.isOnShortCooldown(5L)) {
							stats.setShortCooldown();
							if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
								 event.setWillClose(true);
	                             event.setWillDestroy(true);
	                             CrateSystem.useCrate(p, event.getName());
							}else{
								event.setWillClose(false);
								event.setWillDestroy(false);
							}
						}
					}
				});
				gui.setSlot(AnvilSlot.INPUT_LEFT, InventoryUtils.getItemStack("Enter the Code", Material.PAPER, 1, "Please enter the Code.;"));
				gui.open();
			}
		}
		
		
		
		return true;
	}

}
