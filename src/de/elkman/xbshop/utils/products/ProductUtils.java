package de.elkman.xbshop.utils.products;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

import de.elkman.xbshop.main.Main;

public class ProductUtils {

	public ProductUtils(Main plugin) {
		
		new ShopProduct(101, "Colored Dust", ShopCategory.PARTICLE, Material.REDSTONE,0, "Bunter Staub?;Gibt es sowas überhaupt?", false);
		new ShopProduct(102, "Herzen", ShopCategory.PARTICLE, Material.RED_ROSE, 0, "Verbreite mit diesem Partikel;gaaanz viiiieeel Liebe...", false);
		new ShopProduct(103, "Lava", ShopCategory.PARTICLE, Material.LAVA_BUCKET,0, "Ziemlich heiss!", false);
		new ShopProduct(104, "Musik", ShopCategory.PARTICLE, Material.JUKEBOX,0, "Ta Ta Ta TAAAAAA", false);
		new ShopProduct(105, "Portal", ShopCategory.PARTICLE, Material.OBSIDIAN,0, "Bringt dich dieser Partikel;in eine andere Welt?", false);
		new ShopProduct(106, "Slime", ShopCategory.PARTICLE, Material.SLIME_BALL,0, "Vielleicht schaffst du es ja,;dir bei xBlackJack den Adminrang;zu erschleimen :^)", false);
		new ShopProduct(107, "Snow", ShopCategory.PARTICLE, Material.SNOW_BALL,0, "Lei-se rie-selt der Schnee,;still und starr ruht der See;weih-nacht-lich glän-zet der Wald:;Freu-e dich, Christ-kind kommt bald!", false);
		new ShopProduct(108, "Spell", ShopCategory.PARTICLE, Material.GLASS_BOTTLE,0, "Gibt's den auch in bunt?", false);
		new ShopProduct(109, "Spell (Bunt)", ShopCategory.PARTICLE, Material.POTION,0, "Sehr schön und bunt!", false);
		new ShopProduct(110, "Verzauberung", ShopCategory.PARTICLE, Material.ENCHANTMENT_TABLE,0, "Einfach zauberhaft!", false);
		new ShopProduct(111, "Villager", ShopCategory.PARTICLE, Material.EMERALD,0, "Das sieht aber ganz;fröhlich aus...", false);
		new ShopProduct(112, "Water", ShopCategory.PARTICLE, Material.WATER_BUCKET,0, "Bitte mach nicht die schöne;Lobby nass!", false);
		new ShopProduct(113, "Wut", ShopCategory.PARTICLE, Material.ROTTEN_FLESH,0, "Schlechten Tag gehabt?", false);
		
		new ShopProduct(201, "Diamanthelm", ShopCategory.ARMOR, Material.DIAMOND_HELMET, 10, null, false);
		new ShopProduct(202, "Diamantbrustpanzer", ShopCategory.ARMOR, Material.DIAMOND_CHESTPLATE, 19, null, false);
		new ShopProduct(203, "Diamanthose", ShopCategory.ARMOR, Material.DIAMOND_LEGGINGS, 28, null, false);
		new ShopProduct(204, "Diamantschuhe", ShopCategory.ARMOR, Material.DIAMOND_BOOTS, 37, null, false);
		new ShopProduct(205, "Eisenhelm", ShopCategory.ARMOR, Material.IRON_HELMET, 11, null, false);
		new ShopProduct(206, "Eisenbrustpanzer", ShopCategory.ARMOR, Material.IRON_CHESTPLATE, 20, null, false);
		new ShopProduct(207, "Eisenhose", ShopCategory.ARMOR, Material.IRON_LEGGINGS, 29, null, false);
		new ShopProduct(208, "Eisenschuhe", ShopCategory.ARMOR, Material.IRON_BOOTS, 38, null, false);
		new ShopProduct(209, "Goldhelm", ShopCategory.ARMOR, Material.GOLD_HELMET, 12, null, false);
		new ShopProduct(210, "Goldbrustpanzer", ShopCategory.ARMOR, Material.GOLD_CHESTPLATE, 21, null, false);
		new ShopProduct(211, "Goldhose", ShopCategory.ARMOR, Material.GOLD_LEGGINGS, 30, null, false);
		new ShopProduct(212, "Goldschuhe", ShopCategory.ARMOR, Material.GOLD_BOOTS, 39, null, false);
		new ShopProduct(213, "Kettenhelm", ShopCategory.ARMOR, Material.CHAINMAIL_HELMET, 13, null, false);
		new ShopProduct(214, "Kettenbrustpanzer", ShopCategory.ARMOR, Material.CHAINMAIL_CHESTPLATE, 22, null, false);
		new ShopProduct(215, "Kettenhose", ShopCategory.ARMOR, Material.CHAINMAIL_LEGGINGS, 31, null, false);
		new ShopProduct(216, "Kettenschuhe", ShopCategory.ARMOR, Material.CHAINMAIL_BOOTS, 40, null, false);
		new ShopProduct(217, "Lederhelm", ShopCategory.ARMOR, Material.LEATHER_HELMET, 14, null, false);
		new ShopProduct(218, "Lederbrustpanzer", ShopCategory.ARMOR, Material.LEATHER_CHESTPLATE, 23, null, false);
		new ShopProduct(219, "Lederhose", ShopCategory.ARMOR, Material.LEATHER_LEGGINGS, 32, null, false);
		new ShopProduct(220, "Lederschuhe", ShopCategory.ARMOR, Material.LEATHER_BOOTS, 41, null, false);
		new ShopProduct(221, "Helmet Upgrade", ShopCategory.ARMOR, Material.BOOK, 16, null, false);
		new ShopProduct(222, "Chestplate Upgrade", ShopCategory.ARMOR, Material.BOOK, 25, null, false);
		new ShopProduct(223, "Leggings Upgrade", ShopCategory.ARMOR, Material.BOOK, 34, null, false);
		new ShopProduct(224, "Boots Upgrade", ShopCategory.ARMOR, Material.BOOK, 43, null, false);

		new ShopProduct(301, "Speed I", ShopCategory.ABILITY, Material.SUGAR, 10, "§7Mit dieser Fähigkeit bist;du schneller als die anderen!", false);
		new ShopProduct(302, "Speed II", ShopCategory.ABILITY, Material.SUGAR, 10, "§7Mit dieser Fähigkeit bist;du schneller als ALLE anderen!", false);
		new ShopProduct(303, "Jumpboost I", ShopCategory.ABILITY, Material.LEATHER_BOOTS, 10, "§7Mit dieser Fähigkeit springst;du höher als die anderen!", false);
		new ShopProduct(304, "Jumpboost II", ShopCategory.ABILITY, Material.CHAINMAIL_BOOTS, 10, "§7Mit dieser Fähigkeit springst;du höher als ALLE anderen!", false);
		new ShopProduct(305, "Doppelsprung", ShopCategory.ABILITY, Material.FIREWORK, 10, "§7Boooiiing Boooiiing", false);
		new ShopProduct(306, "Dreifachsprung", ShopCategory.ABILITY, Material.FIREWORK, 10, "§7Boooiiing Boooiiing Boooiiing", false);
		
		new ShopProduct(401, "Jetpack", ShopCategory.GADGET, Material.FLINT_AND_STEEL, 10, "§7Fliege mit diesem Jetpack;über die Wolken!", false);
		
		new ShopProduct(501, "Statsreset", ShopCategory.SPECIAL, Material.REDSTONE_BLOCK, 10, "§7Deine gesamten xBlackJack Statistiken;werden §lunwiederuflich §7gelöscht!", false);
		new ShopProduct(502, "Eventpass", ShopCategory.SPECIAL, Material.NAME_TAG, 10, "§7You can create an Event.", false);
		new ShopProduct(503, "Particle Circle Upgrade", ShopCategory.SPECIAL, Material.BOOK,0, "Mit dem Parikel Upgrade spawnt;ein Kreis um dich herum;wenn du sneakst.", false);
		new ShopProduct(504, "Particle Cube Upgrade", ShopCategory.SPECIAL, Material.BOOK,0, "Mit dem Parikel Upgrade spawnt;ein Würfel um dich herum;wenn du sneakst.", false);
		new ShopProduct(505, "Particle Hitbox Upgrade", ShopCategory.SPECIAL, Material.BOOK,0, "Mit dem Parikel Upgrade spawnt;ein Quader um dich herum;wenn du sneakst. (wie deine Hitbox)", false);
	}
	
	public static HashMap<Integer, ShopProduct> idProduct = new HashMap<>();
	public static HashMap<String, ShopProduct> nameProduct = new HashMap<>();
	
	public static int getMaximumProducts(){
		return idProduct.size();
	}
	
	public static int getPrice(ShopProduct sp, int id){
		return sp.getPrice();
	}
	
	public static ArrayList<ShopProduct> getProducts(ShopCategory category){
		ArrayList<ShopProduct> list = new ArrayList<>();
		for (ShopProduct sp : idProduct.values()){
			if (sp.getCategory() == category){
				list.add(sp);
			}
		}
		return list;
	}
	
	public static String getCategoryName(ShopCategory category){
		if (category == ShopCategory.PARTICLE){
			return "Partikel";
		}else if (category == ShopCategory.ARMOR){
			return "Rüstung";
		}else if (category == ShopCategory.ABILITY){
			return "Fähigkeiten";
		}else if (category == ShopCategory.GADGET){
			return "Gadgets";
		}else if (category == ShopCategory.SPECIAL){
			return "Spezial";
		}
		return "Partikel";
	}
	
	public static ShopCategory getCategory(String categoryname){
		if (categoryname.contains("Partikel")){
			return ShopCategory.PARTICLE;
		}else if (categoryname.contains("Rüstung")){
			return ShopCategory.ARMOR;
		}else if (categoryname.contains("Fähigkeiten")){
			return ShopCategory.ABILITY;
		}else if (categoryname.contains("Gadgets")){
			return ShopCategory.GADGET;
		}else if (categoryname.contains("Spezial")){
			return ShopCategory.SPECIAL;
		}
		return ShopCategory.PARTICLE;
	}
	
	public static String getProdukteString(int amount){
		if (amount == 1){
			return "Produkt";
		}
		return "Produkte";
	}
}