package de.elkman.xbshop.commands;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.elkman.xbshop.utils.BooleanUtils;
import de.elkman.xbshop.utils.players.Chips;
import de.elkman.xbshop.utils.products.ProductUtils;
import de.elkman.xbshop.utils.products.ShopProduct;
import de.xblackjack.inventoryapi.XBInventory;
import de.xblackjack.inventoryapi.XBItemStack;
import de.xblackjack.inventoryapi.XBItemStack.DropAction;
import de.xblackjack.inventoryapi.XBItemStack.InventoryClickAction;
import de.xblackjack.xbbroad.main.XBBroad;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class XBShopCommand implements CommandExecutor{

	public XBShopCommand(Main plugin) {
		plugin.getCommand("xbshop").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (!p.hasPermission("xbshop.admin") && !p.getName().equalsIgnoreCase("_ELKMAN_")){
				p.sendMessage(XBBroad.getXbUtils().noPermMsg());
				return true;
			}
			
			if (args.length == 0){
				p.sendMessage(Main.getPrefix() + "§axBlackJack - Shopsystem §7by _ELKMAN_");
				p.sendMessage(Main.getBracket() + "§e/xbshop chips <add/remove/set/get> <Playername>");
				p.sendMessage(Main.getBracket() + "§e/xbshop products §7§oZeigt dir eine Liste aller Produkte");
				p.sendMessage(Main.getBracket() + "§e/xbshop reload §7§oLädt die Preise neu");
			}else if (args[0].equalsIgnoreCase("products")){
				p.openInventory(getAdminInventory(p));
			}else if (args[0].equalsIgnoreCase("chips")){
				if (args.length < 3){
					p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/xbshop chips <add/remove/set/get> <Playername>");
				}else{
					Player t = Bukkit.getPlayer(args[2]);
					if (t == null){
						p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§7" + args[2] + " §cisn't online.");
					}else{
						if (args[1].equalsIgnoreCase("add")){
							if (args.length < 4){
								p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/xbshop chips add <Playername> <Chips>");
								return true;
							}
							int add;
							try {
								add = Integer.parseInt(args[3]);
							} catch (Exception e) {
								p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/xbshop chips add <Playername> <Chips>");
								return true;
							}
							
							int c = Chips.getChips(t);
							Chips.playerChips.remove(t);
							Chips.playerChips.put(t, c+add);
							p.sendMessage(Main.getPrefix() + "§e" + t.getDisplayName() + " §7has now §e" + Chips.getChips(t) + " Chips§7. §a(+" + add + "§a)");
						}else if (args[1].equalsIgnoreCase("remove")){
							if (args.length < 4){
								p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/xbshop chips remove <Playername> <Chips>");
								return true;
							}
							int rem;
							try {
								rem = Integer.parseInt(args[3]);
							} catch (Exception e) {
								p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/xbshop chips remove <Playername> <Chips>");
								return true;
							}
							
							int c = Chips.getChips(t);
							Chips.playerChips.remove(t);
							Chips.playerChips.put(t, c-rem);
							p.sendMessage(Main.getPrefix() + "§e" + t.getDisplayName() + " §7has now §e" + Chips.getChips(t) + " Chips§7. §c(-" + rem + "§c)");
						}else if (args[1].equalsIgnoreCase("set")){
							if (args.length < 4){
								p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/xbshop chips set <Playername> <Chips>");
								return true;
							}
							int set;
							try {
								set = Integer.parseInt(args[3]);
							} catch (Exception e) {
								p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/xbshop chips set <Playername> <Chips>");
								return true;
							}
							
							int vorher= Chips.getChips(t);
							Chips.playerChips.remove(t);
							Chips.playerChips.put(t, set);
							p.sendMessage(Main.getPrefix() + "§e" + t.getDisplayName() + " §7has now §e" + Chips.getChips(t) + " Chips§7. §e(Before: " + vorher + ")");
						}else if (args[1].equalsIgnoreCase("get")){
							p.sendMessage(Main.getPrefix() + "§e" + t.getDisplayName() + " §7has §e" + Chips.getChips(t) + " Chips§7.");
						}else{
							p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§c/xbshop chips <add/remove/set/get> <Playername>");
						}
					}
				}
			}else if (args[0].equalsIgnoreCase("reload")){
				for (ShopProduct sp : ProductUtils.idProduct.values()){
					Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
						TextComponent text = new TextComponent("");
						text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§e" + sp.getName() + " §7(" + ProductUtils.getCategoryName(sp.getCategory()) + ")").create()));
						text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/xbshop products"));
						try {
							int price = sp.setPrice();
							text.setText(Main.getBracket(PrefixColor.SUCCESS) + " §aSuccessfully loaded price of §7#" + sp.getID() + "§a: §e" + price + " Chips");
						} catch (IOException e) {
							sp.setPrice(1);
							text.setText(Main.getBracket(PrefixColor.WARNING) + " §cUnknown Error while loading price of §7#" + sp.getID());
						} catch (NullPointerException e) {
							sp.setPrice(100);
							text.setText(Main.getBracket(PrefixColor.WARNING) + " §cThe price of §7#" + sp.getID() + " §cis not given");
						}
						p.spigot().sendMessage(text);
					});
				}
			}
		}else{
			sender.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cBitte führe den Command als Spieler aus.");
		}
		return true;
	}

	public static Inventory getAdminInventory(Player p){
		
		XBInventory xbinv = new XBInventory(54, "§8All Items");
		xbinv.fillInventory(InventoryUtils.getBackgroundGlass(15));
	
		
		ArrayList<ShopProduct> idProducts = new ArrayList<>();
		idProducts.addAll(ProductUtils.idProduct.values());
		
		for (int i = 0; i < idProducts.size(); i++){
			ShopProduct sp = idProducts.get(i);
			ItemStack stack = new ItemStack(sp.getMaterial());
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("§e" + sp.getName());
			ArrayList<String> lore = new ArrayList<>();
			lore.add("§7Kategorie: §e" + sp.getCategory().toString());
			lore.add("§7ID: §e" + sp.getID());
			lore.add("§7Preis: §e" + sp.getPrice() + " Chips");
			lore.add("§7Permission benötigt: " + BooleanUtils.getColor(sp.needsPermission()) + sp.needsPermission());
			lore.add("§7Permission: §e" + sp.getPermission());
			if (sp.getDescription() != null){
				lore.add("§7Beschreibung:");
				for (String d : sp.getDescription().split(";")){
					lore.add("§e" + d);
				}
			}
			meta.setLore(lore);
			stack.setItemMeta(meta);
			XBItemStack s = new XBItemStack(stack);
			s.setInventoryClickAction(p, InventoryClickAction.CANCELED);
			s.setDropAction(p, DropAction.CANCELED);
				
			xbinv.addItem(s.getItemStack(), i);
		}
		
		return xbinv.getXBInventory();
	}
}
