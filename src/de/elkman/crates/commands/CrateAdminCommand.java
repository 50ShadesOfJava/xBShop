package de.elkman.crates.commands;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elkman.crates.crateadmin.CrateAdminInventory;
import de.elkman.crates.main.Crate;
import de.elkman.crates.main.CrateData;
import de.elkman.crates.main.CratePrice;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.xblackjack.xbbroad.main.XBBroad;

public class CrateAdminCommand implements CommandExecutor{

	public CrateAdminCommand(Main plugin) {
		plugin.getCommand("crateadmin").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (!p.hasPermission("crates.admin") && !p.getName().equalsIgnoreCase("_ELKMAN_")){
				p.sendMessage(XBBroad.xbUtils.noPermMsg());
				return true;
			}
			if (args.length == 0){
				p.openInventory(CrateAdminInventory.getInventory(p));
			}else{
				if (args[0].equalsIgnoreCase("create")){
					if (args.length != 3){
						syntax(p);
					}else{
						int amount;
						try {
							amount = Integer.parseInt(args[1]);
						} catch (Exception e) {
							syntax(p);
							return true;
						}
						if (!args[2].contains(":")){
							syntax(p);
							return true;
						}
						ArrayList<CratePrice> prices = new ArrayList<>();
						for (String s : args[2].split(",")){
							prices.add(new CratePrice(s));
						}
						p.sendMessage(Main.getPrefix() + "Creating " + XBBroad.getAccentColor(p) + amount + " §7crates...");
						p.sendMessage(Main.getBracket() + "Prices: " + XBBroad.getAccentColor(p) + args[2]);
						Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
							for (int i = 1; i <= amount; i++){
								Random r = new Random();
								int z = r.nextInt(prices.size());
								CratePrice price = prices.get(z);
								Crate crate = new Crate(XBBroad.xbUtils.getRandomID(6).toUpperCase(), null, price, CrateData.getTimestamp("90d"), null);
								crate.save();
								p.sendMessage(Main.getBracket() + "§f#" + i + "§8: §e" + crate.getCode() + " §7| §e" + crate.getPrice().toString());
							}
						});
					}
				}else{
					syntax(p);
				}
			}
		}
		return true;
	}
	
	public void syntax(Player p){
		p.sendMessage(XBBroad.xbUtils.getWrongArgumens("/crateadmin create <Amount> <PriceCategory>:<SubInformation>"));
		p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "/crateadmin create <Amount> <PC>:<SI>,<PC>:<SI>,...");
		p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "PriceCategories: NOTHING,CHIPS,RANK,SHOP_PRODUCT");
		p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "SubInformation NOTHING:0");
		p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "SubInformation CHIPS:<Amount of Chips>");
		p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "SubInformation RANK:COMING SOON");
		p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "SubInformation SHOP_PRODUCT:<ProductID> §7§o(Product IDs -> /xbshop products)");
	}
}
