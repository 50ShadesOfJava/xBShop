package de.elkman.xbshop.inventorys;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class InventoryUtils {

	@SuppressWarnings("deprecation")
	public static ItemStack getBackgroundGlass(int color){
		ItemStack glas = (new ItemStack(160, 1, (short) 0, (byte) color));
		ItemMeta meta = glas.getItemMeta();
		meta.setDisplayName("§8");
		glas.setItemMeta(meta);
		
		return glas;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getBackgroundGlass(String displayname, int color, int amount, String lore){
		ItemStack glas = (new ItemStack(160, 1, (short) 0, (byte) color));
		ItemMeta meta = glas.getItemMeta();
		meta.setDisplayName(displayname);
		if (lore != null){
			ArrayList<String> list = new ArrayList<>();
			for (String l : lore.split(";")){
				list.add("§7" + l);
			}
			meta.setLore(list);
		}
		glas.setItemMeta(meta);
		glas.setAmount(amount);
		return glas;
	}
	

	public static ItemStack getItemStack(String name, Material m, int amount, String lore){
		ItemStack stack = new ItemStack(m);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		if (lore != null){
			ArrayList<String> list = new ArrayList<>();
			for (String l : lore.split(";")){
				list.add("§7" + l);
			}
			meta.setLore(list);
		}
		stack.setItemMeta(meta);
		stack.setAmount(amount);
		return stack;
	}
	
	public static ItemStack getItemStack(String name, Material m, int amount, String lore, boolean enchanted){
		if (!enchanted){
			return getItemStack(name, m, amount, lore);
		}
		return addGlow(getItemStack(name, m, amount, lore));
	}
	
	public static ItemStack addGlow(ItemStack item){
		net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		
		if (!nmsStack.hasTag()){
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}	
	     	
		if (tag == null){
			tag = nmsStack.getTag();
		}
	     
		NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
	     
		return CraftItemStack.asCraftMirror(nmsStack);
	}
}
