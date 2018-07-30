package de.elkman.xbshop.utils.products;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.elkman.xbshop.utils.BooleanUtils;
import de.elkman.xbshop.utils.players.Chips;
import de.elkman.xbshop.utils.players.OwnedProducts;
import de.elkman.xbshop.utils.players.SelectedProducts;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ShopProduct {

	private int id;
	private String name;
	private ShopCategory type;
	private Material mat;
	private int price;
	private int invSlot;
	private String description;
	private boolean permission;
	
	
	public ShopProduct(int productID, String productName, ShopCategory category, Material material, int slot, String desc, boolean needsPermission) {
		id = productID;
		name = productName;
		type = category;
		mat = material;
		invSlot = slot;
		description = desc;
		permission = needsPermission;
		try {
			setPrice();
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + " §cUnknown Error while loading price of §e" + name + " §7(" + id + ")");
			setPrice(10);
		}
		ProductUtils.idProduct.put(getID(), this);
		ProductUtils.nameProduct.put(getName(), this);
	}
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public ShopCategory getCategory(){
		return type;
	}
	
	public Material getMaterial(){
		return mat;
	}
	
	public int getPrice(){
		return price;
	}

	public int setPrice() throws IOException, NullPointerException{
		ProductPriceUtils.setPrice(this, id);
		return getPrice();
	}
	
	public int setPrice(int i){
		price = i;
		return getPrice();
	}
	
	public String getDescription(){
		return description;
	}
	
	public int getSlot(){
		return invSlot;
	}
	
	public ItemStack getStack(Player p){
		ShopCustomer sc = new ShopCustomer(p);
		ItemStack stack = new ItemStack(getMaterial());
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§a" + name);
		ArrayList<String> lore = new ArrayList<>();
		if (getDescription() != null){
			for (String d : getDescription().split(";")){
				lore.add("§7" + d);
			}
			lore.add("");
		}
		if (OwnedProducts.playerHasProduct(p, getID())){
			lore.add("§a§lIn deinem Besitz");
			if (SelectedProducts.selectedProducts.containsKey(p.getName())){
				if (SelectedProducts.selectedProducts.get(p.getName()).contains(this)){
					lore.add("§7 ➥ §a§lAktiviert");
				}else{
					lore.add("§7 ➥ §c§lDeaktiviert");
				}
			}
		}else{
			lore.add("§7Preis: " + BooleanUtils.getColor(Chips.getChips(p) >= getPrice()) + getPrice() + " Chips");
			if (sc.getWarenkorb().contains(this)){
				lore.add("");
				lore.add("§6§lIm Warenkorb");
			}
		}
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public EnumParticle toEnumParticle(){
		if (getName().equalsIgnoreCase("Colored Dust")){
			return EnumParticle.REDSTONE;
		}else if (getName().equalsIgnoreCase("Herzen")){
			return EnumParticle.HEART;
		}else if (getName().equalsIgnoreCase("Lava")){
			return EnumParticle.LAVA;
		}else if (getName().equalsIgnoreCase("Musik")){
			return EnumParticle.NOTE;
		}else if (getName().equalsIgnoreCase("Portal")){
			return EnumParticle.PORTAL;
		}else if (getName().equalsIgnoreCase("Slime")){
			return EnumParticle.SLIME;
		}else if (getName().equalsIgnoreCase("Snow")){
			return EnumParticle.SNOWBALL;
		}else if (getName().equalsIgnoreCase("Spell")){
			return EnumParticle.SPELL;
		}else if (getName().equalsIgnoreCase("Spell (Bunt)")){
			return EnumParticle.SPELL_MOB;
		}else if (getName().equalsIgnoreCase("Verzauberung")){
			return EnumParticle.ENCHANTMENT_TABLE;
		}else if (getName().equalsIgnoreCase("Villager")){
			return EnumParticle.VILLAGER_HAPPY;
		}else if (getName().equalsIgnoreCase("Water")){
			return EnumParticle.WATER_DROP;
		}else if (getName().equalsIgnoreCase("Wut")){
			return EnumParticle.VILLAGER_ANGRY;
		}
		return EnumParticle.BLOCK_CRACK;
	}
	
	public String getPermission(){
		return "xbshop." + type.toString().toLowerCase() + "." + id;
	}
	
	public boolean needsPermission(){
		return permission;
	}
}
