package de.elkman.xbshop.utils.players;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import de.elkman.xbshop.utils.products.ShopCategory;
import de.elkman.xbshop.utils.products.ShopProduct;

public class SelectedProducts {

	public static HashMap<String, ArrayList<ShopProduct>> selectedProducts = new HashMap<>();
	public static ArrayList<Player> playersWithEquipment = new ArrayList<>();

	public static ShopProduct getSelectedParticle(Player p){
		if (selectedProducts.containsKey(p.getName())){
			for (ShopProduct sp : selectedProducts.get(p.getName())){
				if (sp.getCategory() == ShopCategory.PARTICLE){
					return sp;
				}
			}
		}
		return null;
	}
	
	public static ArrayList<ShopProduct> getSelectedProducts(Player p, ShopCategory filter){
		ArrayList<ShopProduct> list = new ArrayList<>();
		if (selectedProducts.containsKey(p.getName())){
			for (ShopProduct sp : selectedProducts.get(p.getName())){
				if (filter == sp.getCategory()){
					list.add(sp);
				}
			}
		}
		return list;
	}
	
	public static ShopProduct getSelectedProduct(Player p, ShopCategory filter){
		if (selectedProducts.containsKey(p.getName())){
			for (ShopProduct sp : selectedProducts.get(p.getName())){
				if (sp.getCategory() == filter){
					return sp;
				}
			}
		}
		return null;
	}
	
	public static ShopProduct getSelectedHelmet(Player p){
		if (selectedProducts.containsKey(p.getName())){
			for (ShopProduct sp : selectedProducts.get(p.getName())){
				if (sp.getCategory() == ShopCategory.ARMOR){
					if (sp.getSlot() > 9 && sp.getSlot() < 15){
						return sp;
					}
				}
			}
		}
		return null;
	}
	
	public static ShopProduct getSelectedChestplate(Player p){
		if (selectedProducts.containsKey(p.getName())){
			for (ShopProduct sp : selectedProducts.get(p.getName())){
				if (sp.getCategory() == ShopCategory.ARMOR){
					if (sp.getSlot() > 18 && sp.getSlot() < 24){
						return sp;
					}
				}
			}
		}
		return null;
	}
	
	public static ShopProduct getSelectedLeggings(Player p){
		if (selectedProducts.containsKey(p.getName())){
			for (ShopProduct sp : selectedProducts.get(p.getName())){
				if (sp.getCategory() == ShopCategory.ARMOR){
					if (sp.getSlot() > 27 && sp.getSlot() < 33){
						return sp;
					}
				}
			}
		}
		return null;
	}
	
	public static ShopProduct getSelectedBoots(Player p){
		if (selectedProducts.containsKey(p.getName())){
			for (ShopProduct sp : selectedProducts.get(p.getName())){
				if (sp.getCategory() == ShopCategory.ARMOR){
					if (sp.getSlot() > 36 && sp.getSlot() < 42){
						return sp;
					}
				}
			}
		}
		return null;
	}
}
