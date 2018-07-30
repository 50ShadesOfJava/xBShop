package de.elkman.casino.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.elkman.casino.main.CasinoMain;
import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.inventorys.InventoryUtils;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.xblackjack.xbbroad.main.XBBroad;

public class LeverGame {
	
	public enum ItemType{
		IRON (2, InventoryUtils.getItemStack("§7§lIRON", Material.IRON_BLOCK, 1, null)),
		GOLD (5, InventoryUtils.getItemStack("§6§lGOLD", Material.GOLD_BLOCK, 1, null)),
		DIAMOND (10, InventoryUtils.getItemStack("§b§lDIAMOND", Material.DIAMOND_BLOCK, 1, null));
		
		private int factor;
		private ItemStack stack;
		
		ItemType(int factor, ItemStack stack){
			this.factor = factor;
			this.stack = stack;
		}
		
		public int getFactor(){
			return this.factor;
		}
		
		public ItemStack getItemStack(){
			return this.stack;
		}
	}
	
	public static HashMap<UUID, LeverGame> inLeverGame = new HashMap<>();
	
	public static ItemType getRandomItemType(ItemType not){
		ArrayList<ItemType> types = new ArrayList<>();
		types.add(ItemType.IRON);
		types.add(ItemType.GOLD);
		types.add(ItemType.DIAMOND);
		if (not != null){
			types.remove(not);
		}
		return types.get(new Random().nextInt(types.size()));
	}
	
	private Player player;
	private Inventory inv;
	private int slot;
	private ItemType currentType;
	private HashMap<Integer, ItemType> chosenTypes = new HashMap<>();
	private Timer t;
	
	public LeverGame(Player p){
		this.player = p;
		this.slot = 1;
		this.currentType = getRandomItemType(null);
		createInventory();
		
		inLeverGame.put(p.getUniqueId(), this);
		
		timer();
	}
	
	public void createInventory(){
		this.inv = getPlayer().getServer().createInventory(null, 45, Var.INV_CASINO_LEVER_TITLE.replaceAll("&", XBBroad.getAccentColor(getPlayer())));
		
		for (int i = 0; i < 45; i++){
			if (i == 12){
				inv.setItem(i, InventoryUtils.getItemStack("§7§lIron §8x2", Material.IRON_BLOCK, 2, null));
			}else if (i == 13){
				inv.setItem(i, InventoryUtils.getItemStack("§6§lGold §8x5", Material.GOLD_BLOCK, 5, null));
			}else if (i == 14){
				inv.setItem(i, InventoryUtils.getItemStack("§b§lDiamond §8x10", Material.DIAMOND_BLOCK, 10, null));
			}else if (i == 33){
				inv.setItem(i, InventoryUtils.getItemStack(XBBroad.getAccentColor(getPlayer()) + "§lClick", Material.LEVER, 1, null));
			}else if (i == 29 || i == 30 || i == 31){
				
			}else{
				inv.setItem(i, InventoryUtils.getBackgroundGlass(15));
			}
		}
		getPlayer().openInventory(inv);
	}

	public void updateInventory(){
		if (slot > 1){
			inv.setItem(29, chosenTypes.get(1).getItemStack());
		}else if (slot == 1){
			inv.setItem(29, getCurrentItemType().getItemStack());
		}
		if (slot > 2){
			inv.setItem(30, chosenTypes.get(2).getItemStack());
		}else if (slot == 2){
			inv.setItem(30, getCurrentItemType().getItemStack());
		}
		if (slot > 3){
			inv.setItem(31, chosenTypes.get(3).getItemStack());
		}else if (slot == 3){
			inv.setItem(31, getCurrentItemType().getItemStack());
		}
	}
	
	public Inventory getInventory(){
		return inv;
	}
	
	public int getSlot(){
		return slot;
	}
	
	public ItemType getCurrentItemType(){
		return currentType;
	}
	
	public void click(){
		chosenTypes.remove(slot);
		chosenTypes.put(slot, getCurrentItemType());
		slot++;
		if (slot == 4){
			t.cancel();
			ShopCustomer sc = new ShopCustomer(getPlayer());
			if (hasWon()){
				int insert = CasinoMain.inserts.get(getPlayer().getUniqueId());
				int factor = getCurrentItemType().getFactor();
				sc.addChips(insert*factor, false);
				getPlayer().sendMessage(Main.getPrefix(PrefixColor.SUCCESS) + "§a§lCongratulations!");
				getPlayer().sendMessage(Main.getBracket(PrefixColor.SUCCESS) + "You earned §a" + insert*factor + " Chips§7.");
				getPlayer().playSound(getPlayer().getLocation(), Sound.LEVEL_UP, 5, 5);
			}else{
				int insert = CasinoMain.inserts.get(getPlayer().getUniqueId());
				getPlayer().sendMessage(Main.getPrefix() + "§c§lBetter Luck next time!");
				getPlayer().sendMessage(Main.getBracket() + "You lost §c" + insert + " Chips§7.");
				getPlayer().playSound(getPlayer().getLocation(), Sound.NOTE_BASS, 5, 5);
			}
			inLeverGame.remove(getPlayer().getUniqueId());
		}
		updateInventory();
	}
	
	public boolean hasWon(){
		if (chosenTypes.get(1).toString() == chosenTypes.get(2).toString()){
			return (chosenTypes.get(2).toString() == chosenTypes.get(3).toString());
		}else{
			return false;
		}
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void timer(){
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				if (slot < 4){
					currentType = getRandomItemType(getCurrentItemType());
					getPlayer().playSound(getPlayer().getLocation(), Sound.WOOD_CLICK, 5, 5);
					updateInventory();
				}
			}
			
		}, 100, 100);
	}
}
