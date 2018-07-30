package de.elkman.xbshop.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;

public class SendchipsCommand implements CommandExecutor{

	public SendchipsCommand(Main plugin) {
		plugin.getCommand("sendchips").setExecutor(this);
		plugin.getCommand("pay").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (args.length < 2){
				p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/" + cmd.getName() + " <Player> <Chips> <Message>");
			}else{
				String name = args[0];
				Player t = Bukkit.getPlayer(name);
				if (t == null){
					p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c" + name + " is not online.");
				}else{
					int chips = 0;
					try {
						chips = Integer.parseInt(args[1]);
					} catch (Exception e) {
						p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/" + cmd.getName() + " <Player> <Chips> <Message>");
						return true;
					}
					ShopCustomer psc = new ShopCustomer(p);
					ShopCustomer tsc = new ShopCustomer(t);
					if (chips > psc.getChips()){
						p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cYou only have §7" + psc.getChips() + " Chips§c.");
						return true;
					}
					psc.removeChips(chips, true);
					p.sendMessage(Main.getBracket() + "§a" + t.getName() + " §7received §e" + chips + " Chips§7.");
					tsc.addChips(chips, false);
					t.playSound(t.getLocation(), Sound.LEVEL_UP, 5, 5);
					t.sendMessage(Main.getPrefix() + "§a" + p.getName() + " §7gave §e" + chips + " Chips §7to you.");
					if (args.length > 2){
						String message = "";
						for (int i = 2; i < args.length; i++){
							message = message + args[i] + " ";
						}
						t.sendMessage(Main.getBracket() + "§b" + ChatColor.translateAlternateColorCodes('&', message));
					}
				}
			}
		}else{
			sender.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cYou have to be a player to execute this command.");
		}
		return true;
	}
}
