package de.elkman.crates.main;

import java.sql.Timestamp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.xblackjack.xbbroad.main.XBBroad;

public class CrateSystem {
	
	public static void useCrate(Player p, String code){
		p.sendMessage(Main.getPrefix() + "Loading Crate...");
//		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
			if (CrateData.crateIsAvailable(code)){
				p.sendMessage(Main.getBracket(PrefixColor.SUCCESS) + "§7Crate found!");
				Crate crate = CrateData.loadCrateFormDatabase(CrateData.CRATES_TABLE, code);
				CratePrice price = crate.getPrice();
				p.sendMessage(Main.getBracket() + "Code " + XBBroad.getAccentColor(p) + code);
				crate.setTaker(p.getName());
				crate.setTakenDate(new Timestamp(System.currentTimeMillis()));
				crate.save();
				price.givePrice(p);
			}else if (CrateData.crateWasTaken(code)){
				p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "§7This Crate was already taken. §cSorry :(");
			}else{
				p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "§7This Crate never existed.");
			}
//		});
	}
	
	public static void deleteCrate(Player p, String executor, String code){
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
			if (CrateData.crateIsAvailable(code)){
				Crate crate = CrateData.loadCrateFormDatabase(CrateData.CRATES_TABLE, code);
				crate.setTaker(executor);
				crate.setTakenDate(new Timestamp(System.currentTimeMillis()));
				crate.save();
				p.sendMessage(Main.getPrefix() + "The Crate #" + code + " got removed.");
			}else if (CrateData.crateWasTaken(code)){
				p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§7This Crate was already taken.");
			}else{
				p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§7This Crate never existed.");
			}
		});
	}
	
}
