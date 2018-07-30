package de.elkman.xbshop.utils.products;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import org.bukkit.Bukkit;

import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;

public class ProductPriceUtils {

	
	public static void setPrice(ShopProduct sp, int id) throws IOException{
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
			try {
				BufferedReader reader = null;
				StringBuilder builder = new StringBuilder(128000);
				URL url = new URL("http://50shadesofjava.com/api/xBlackJack/xBShop/prices.html");
				try {
					reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
					int count;
					char[]data = new char[5000];
					while ((count = reader.read(data)) != -1) {
						builder.append(data, 0, count);
					}
				}finally{
				}
				String[] ids = builder.toString().split(";");
				HashMap<Integer, Integer> idPrice = new HashMap<>();
				for (String idstring : ids){
					try {
						int cID = Integer.parseInt(idstring.split("=")[0]);
						int cPrice = Integer.parseInt(idstring.split("=")[1]);
						idPrice.put(cID, cPrice);
					} catch (Exception e) {
					}
				}
				if (!idPrice.containsKey(id)){
					Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + " §cThe price of §e" + sp.getName() + " §7(" + id + ") §cis not given");
					sp.setPrice(10);
				}else{
					sp.setPrice(idPrice.get(id));
//					Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.SUCCESS) + " §aSuccessfully loaded price of §7" + sp.getName() + " (" + id + ")§a: §d" + idPrice.get(id) + " Chips");
				}
			} catch (IOException e) {
				sp.setPrice(10);
				Bukkit.getConsoleSender().sendMessage(Main.getPrefix(PrefixColor.WARNING) + " §cUnknown Error while loading price of §e" + sp.getName() + " §7(" + id + ")");
			}
		});
	}
}
