package de.elkman.xbshop.api;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.elkman.casino.inventorys.CasinoInventory;
import de.elkman.casino.main.DailyMain;
import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.inventorys.ShopInventory;
import de.elkman.xbshop.inventorys.WarenkorbInventory;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.utils.players.Chips;
import de.elkman.xbshop.utils.players.OwnedProducts;
import de.elkman.xbshop.utils.players.SelectedProducts;
import de.elkman.xbshop.utils.products.ProductUtils;
import de.elkman.xbshop.utils.products.ShopCategory;
import de.elkman.xbshop.utils.products.ShopProduct;
import de.xblackjack.xbbroad.main.XBBroad;

public class ShopCustomer {

	private Player player;
	private String name;
	private UUID uuid;
	
	public ShopCustomer(Player p) {
		player = p;
		name = p.getName();
		uuid = p.getUniqueId();
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public String getName(){
		return name;
	}
	
	public UUID getUniqueID(){
		return uuid;
	}
	
	public boolean hasDailyChipsTaken(){
		return DailyMain.dailytaken.get(player);
	}
	
	public int getDailyChipsStreak(){
		return DailyMain.dailystreak.get(player);
	}
	
	public int getChips(){
		return Chips.getChips(getPlayer());
	}
	
	public void addChips(int chips, boolean msg){
		int c = getChips();
		Chips.playerChips.remove(player);
		Chips.playerChips.put(player, c+chips);
		if (msg){
			player.sendMessage(Main.getPrefix() + "You earned §a" + chips + " Chips§7.");
		}
		XBBroad.xbBroad.getPlayerStats(getPlayer()).setChips(getChips());;
	}
	
	public void removeChips(int chips, boolean msg){
		int c = getChips();
		Chips.playerChips.remove(player);
		Chips.playerChips.put(player, c-chips);
		if (msg){
			player.sendMessage(Main.getPrefix() + "You payed §c" + chips + " Chips§7.");
		}
		XBBroad.xbBroad.getPlayerStats(getPlayer()).setChips(getChips());;
	}
	
	public ArrayList<ShopProduct> getWarenkorb(){
		if (ShopInventory.warenkorb.containsKey(player)){
			return ShopInventory.warenkorb.get(player);
		}
		return new ArrayList<ShopProduct>();
	}
	
	public ShopCategory getWatchingCategory(){
		if (ShopInventory.playerCategory.containsKey(player)){
			return ShopInventory.playerCategory.get(player);
		}
		return ShopCategory.PARTICLE;
	}
	
	public ArrayList<ShopProduct> getBuyedProducts(){
		ArrayList<ShopProduct> list = new ArrayList<>();
		for (Integer i : OwnedProducts.getOwnedProducts(getPlayer())){
			list.add(ProductUtils.idProduct.get(i));
		}
		return list;
	}
	
	public ArrayList<ShopProduct> getBuyedProducts(ShopCategory filter){
		ArrayList<ShopProduct> list = new ArrayList<>();
		for (Integer i : OwnedProducts.getOwnedProducts(getPlayer())){
			ShopProduct sp = ProductUtils.idProduct.get(i);
			if (filter == sp.getCategory()){
				list.add(ProductUtils.idProduct.get(i));
			}
		}
		return list;
	}
	
	public void openEventPassWarenkorb(){
		if (ShopInventory.warenkorb.containsKey(getPlayer())){
			ShopInventory.warenkorb.get(getPlayer()).clear();
		}else{
			ShopInventory.warenkorb.put(getPlayer(), new ArrayList<ShopProduct>());
		}
		ShopInventory.warenkorb.get(getPlayer()).add(ProductUtils.nameProduct.get("Eventpass"));
		getPlayer().openInventory(WarenkorbInventory.getWarenkorbGUI(getPlayer()));
	}
	
	public void openCasino(){
		player.openInventory(CasinoInventory.getMainInventory(player));
	}
	
	public void buyProduct(ShopProduct sp){
		if (sp.getID() != 501 && sp.getID() != 502){
			OwnedProducts.playerIDs.get(player).add(sp.getID());
		}
		removeChips(sp.getPrice(), false);
		player.sendMessage(Main.getBracket() + "§7You received " + XBBroad.getAccentColor(this.player) + sp.getName() + " §7(" + ProductUtils.getCategoryName(sp.getCategory()) + ")!");
		Bukkit.getPluginManager().callEvent(new PlayerBuyProductEvent(getPlayer(), sp));
	}
	
	public void buyFreeProduct(ShopProduct sp){
		if (sp.getID() != 501 && sp.getID() != 502){
			OwnedProducts.playerIDs.get(player).add(sp.getID());
		}
		player.sendMessage(Main.getBracket() + "§7You received " + XBBroad.getAccentColor(this.player) + sp.getName() + " §7(" + ProductUtils.getCategoryName(sp.getCategory()) + ")!");
		Bukkit.getPluginManager().callEvent(new PlayerBuyProductEvent(getPlayer(), sp));
	}
	
	public void equip(){
		if (!SelectedProducts.selectedProducts.containsKey(getPlayer().getName())){
			SelectedProducts.selectedProducts.put(getPlayer().getName(), new ArrayList<ShopProduct>());
		}
		if (!SelectedProducts.playersWithEquipment.contains(getPlayer())){
			SelectedProducts.playersWithEquipment.add(getPlayer());
		}
		if (SelectedProducts.getSelectedHelmet(getPlayer()) != null){
			equipArmor(SelectedProducts.getSelectedHelmet(getPlayer()));
		}
		if (SelectedProducts.getSelectedChestplate(getPlayer()) != null){
			equipArmor(SelectedProducts.getSelectedChestplate(getPlayer()));
		}
		if (SelectedProducts.getSelectedLeggings(getPlayer()) != null){
			equipArmor(SelectedProducts.getSelectedLeggings(getPlayer()));
		}
		if (SelectedProducts.getSelectedBoots(getPlayer()) != null){
			equipArmor(SelectedProducts.getSelectedBoots(getPlayer()));
		}
		if (SelectedProducts.getSelectedProduct(getPlayer(), ShopCategory.ABILITY) != null){
			ShopProduct sp = SelectedProducts.getSelectedProduct(getPlayer(), ShopCategory.ABILITY);
			equipEffect(sp);
		}
		equipGadget();
	}
	
	public void unequip(){
		SelectedProducts.playersWithEquipment.remove(getPlayer());
		getPlayer().getInventory().setArmorContents(null);
		unequipEffect();
		unequipGadget();
	}
	
	public void equipArmor(ShopProduct sp){
		ItemStack stack = new ItemStack(sp.getMaterial());
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§a" + sp.getName());
		stack.setItemMeta(meta);
		if (sp.getSlot() > 9 && sp.getSlot() < 15){
			if (SelectedProducts.getSelectedProducts(getPlayer(), ShopCategory.ARMOR).contains(ProductUtils.idProduct.get(221))){
				stack = InventoryUtils.addGlow(stack);
			}
			player.getInventory().setHelmet(stack);
		}else if (sp.getSlot() > 18 && sp.getSlot() < 24){
			if (SelectedProducts.getSelectedProducts(getPlayer(), ShopCategory.ARMOR).contains(ProductUtils.idProduct.get(222))){
				stack = InventoryUtils.addGlow(stack);
			}
			player.getInventory().setChestplate(stack);
		}else if (sp.getSlot() > 27 && sp.getSlot() < 33){
			if (SelectedProducts.getSelectedProducts(getPlayer(), ShopCategory.ARMOR).contains(ProductUtils.idProduct.get(223))){
				stack = InventoryUtils.addGlow(stack);
			}
			player.getInventory().setLeggings(stack);
		}else if (sp.getSlot() > 36 && sp.getSlot() < 42){
			if (SelectedProducts.getSelectedProducts(getPlayer(), ShopCategory.ARMOR).contains(ProductUtils.idProduct.get(224))){
				stack = InventoryUtils.addGlow(stack);
			}
			player.getInventory().setBoots(stack);
		}
	}
	
	public void unequipArmor(ShopProduct sp){
		if (sp.getSlot() > 9 && sp.getSlot() < 17){
			player.getInventory().setHelmet(new ItemStack(Material.AIR));
		}else if (sp.getSlot() > 18 && sp.getSlot() < 26){
			player.getInventory().setChestplate(new ItemStack(Material.AIR));
		}else if (sp.getSlot() > 27 && sp.getSlot() < 35){
			player.getInventory().setLeggings(new ItemStack(Material.AIR));
		}else if (sp.getSlot() > 36 && sp.getSlot() < 44){
			player.getInventory().setBoots(new ItemStack(Material.AIR));
		}
	}
	

	public void equipEffect(ShopProduct sp){
		unequipEffect();
		PotionEffectType type = null;
		int a = 0;
		if (sp.getName().contains("II")){
			a = 1;
		}
		if (sp.getName().startsWith("Speed")){
			type = PotionEffectType.SPEED;
		}else if (sp.getName().startsWith("Jumpboost")){
			type = PotionEffectType.JUMP;
		}
		if (type != null){
			getPlayer().addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, a));
		}
	}
	
	public void unequipEffect(){
		getPlayer().setAllowFlight(false);
		for (PotionEffect pe : getPlayer().getActivePotionEffects()){
			getPlayer().removePotionEffect(pe.getType());
		}
	}
	
	public void equipGadget(){
		ShopProduct sp = SelectedProducts.getSelectedProduct(getPlayer(), ShopCategory.GADGET);
		if (sp != null){
			ItemStack stack = new ItemStack(sp.getMaterial());
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("§7« " + XBBroad.getAccentColor(this.player) + "§l" + sp.getName() + " §7»");
			stack.setItemMeta(meta);
			getPlayer().getInventory().setItem(Var.GADGET_SLOT, stack);
		}
	}
	
	public void unequipGadget(){
		ItemStack stack = new ItemStack(Material.AIR);
		getPlayer().getInventory().setItem(7, stack);
	}
}