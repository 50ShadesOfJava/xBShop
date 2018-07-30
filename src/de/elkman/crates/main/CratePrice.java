package de.elkman.crates.main;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.elkman.xbshop.utils.products.ProductUtils;
import de.elkman.xbshop.utils.products.ShopCategory;
import de.elkman.xbshop.utils.products.ShopProduct;
import de.xblackjack.xbbroad.main.XBBroad;

public class CratePrice {

	public enum CratePriceType {
		NOTHING ("Nothing", Material.REDSTONE),
		CHIPS ("Chips", Material.GOLD_NUGGET),
		RANK ("Rank", Material.DIAMOND_SWORD),
		SHOP_PRODUCT ("Shop Product", Material.NETHER_STAR);
		
		private String name;
		private Material mat;
		
		private CratePriceType(String name, Material mat){
			this.name = name;
			this.mat = mat;
		}
		
		public String getName(){
			return this.name;
		}
		
		public Material getMaterial(){
			return this.mat;
		}
	}
	
	public static CratePrice getChipsPrice(int min, int max){
		int chips = new Random().nextInt(max+min)+min;
		return new CratePrice("CHIPS:" + chips);
	}
	
	public static CratePrice getProductPrice(ShopCategory category){
		Random r = new Random();
		int z = r.nextInt(ProductUtils.idProduct.size());
		ShopProduct sp = ((ArrayList<ShopProduct>)ProductUtils.idProduct.values()).get(z);
		while(category != sp.getCategory()){
			z = r.nextInt(ProductUtils.idProduct.size());
			sp = ((ArrayList<ShopProduct>)ProductUtils.idProduct.values()).get(z);
		}
		return new CratePrice("SHOP_PRODUCT:" + sp.getID());
	}
	
	private String priceString;
	private CratePriceType type;
	
	public CratePrice(String arg0) {
		priceString = arg0;
		type = CratePriceType.valueOf(arg0.split(":")[0]);
	}
	
	public String toString(){
		return priceString;
	}
	
	public String getInformationString(){
		return priceString.split(":")[1];
	}
	
	public CratePriceType getType(){
		return type;
	}
	
	public ShopProduct getProduct(){
		if (getType() == CratePriceType.SHOP_PRODUCT){
			int id;
			try {
				id = Integer.parseInt(getInformationString());
			} catch (Exception e) {
				System.err.println("CratePrice: ShopProduct ID is not given!");
				return null;
			}
			return ProductUtils.idProduct.get(id);
		}
		System.err.println("getProduct() in CratePrice but Price is no Product!");
		return null;
	}
	
	public void givePrice(Player p){
		ShopCustomer sc = new ShopCustomer(p);
		switch (getType()) {
		case NOTHING:
			p.sendMessage(Main.getBracket() + "§cBad luck! §7The crate is empty.");
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 5, 5);
			break;
			
		case CHIPS:
			int chips = Integer.parseInt(getInformationString());
			sc.addChips(chips, false);
			p.sendMessage(Main.getBracket() + "§7You found §a" + chips + " Chips§7!");
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
			break;
			
//		case RANK:
//			p.sendMessage(arg0);
//			break;
//			
		case SHOP_PRODUCT:
			ShopProduct sp = getProduct();
			if (sc.getBuyedProducts().contains(sp)){
				p.sendMessage(Main.getBracket() + "§cBad luck! §7You already own " + XBBroad.getAccentColor(p) + sp.getName() + "§7.");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 5, 5);
			}else{
				p.sendMessage(Main.getBracket() + "§7You found a §aShopProduct§7!");
				sc.buyFreeProduct(sp);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
			}
			break;
			
		default:
			p.sendMessage(Main.getBracket(PrefixColor.WARNING) + "Error");
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 5, 5);
			break;
		}
	}
	
	public Material getMaterial(){
		if (type == CratePriceType.SHOP_PRODUCT){
			return getProduct().getMaterial();
		}else{
			return type.getMaterial();
		}
	}
}
