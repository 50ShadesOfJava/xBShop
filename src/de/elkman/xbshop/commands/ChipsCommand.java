package de.elkman.xbshop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;

public class ChipsCommand implements CommandExecutor{

	public ChipsCommand(Main plugin) {
		plugin.getCommand("chips").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player){
			Player p = (Player) sender;
			ShopCustomer sc = new ShopCustomer(p);
			p.sendMessage(Main.getPrefix() + "§7You have §a" + sc.getChips() + " Chips§7.");
		}else{
			sender.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§aDu hast unendlich Chips! Cool was?");
			sender.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§7Aber bringt dir das überhaupt etwas?");
		}
		return true;
	}

}
