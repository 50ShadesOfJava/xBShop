package de.elkman.xbshop.utils.products;

import org.bukkit.Material;

public enum ShopCategory {

	PARTICLE (Material.REDSTONE),
	ARMOR (Material.IRON_CHESTPLATE),
	ABILITY (Material.POTION),
	GADGET (Material.FLINT_AND_STEEL),
	SPECIAL (Material.SNOW_BALL);
	
	private Material mat;
	
	private ShopCategory(Material mat){
		this.mat = mat;
	}
	
	public Material getMaterial(){
		return this.mat;
	}

}
