package de.elkman.xbshop.extras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import de.elkman.xbshop.api.AsyncMoveEvent;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.utils.players.SelectedProducts;
import de.elkman.xbshop.utils.products.ProductUtils;
import de.elkman.xbshop.utils.products.ShopCategory;
import de.elkman.xbshop.utils.products.ShopProduct;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class ParticleSpawner implements Listener{

	public ParticleSpawner(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public static final double RADIUS = 1.3;
	public static final double CUBEDRADIUS = 1.0;
	public static final double HITBOXRADIUS = 0.25;
	public static final double HITBOXHIGH = 1.70;
	public static final double INTERVALL = 0.2;
	
	public static HashMap<Integer, Double> dragons = new HashMap<>();
	public static Integer ids = 0;
	
	@EventHandler
	public void onMove(AsyncMoveEvent e){
		Player p = e.getPlayer();
		if (SelectedProducts.playersWithEquipment.contains(p)){
			if (SelectedProducts.getSelectedParticle(p) != null){
				ShopProduct sp = SelectedProducts.getSelectedParticle(p);
				if (p.isSneaking() && SelectedProducts.getSelectedProducts(p, ShopCategory.SPECIAL).contains(ProductUtils.idProduct.get(503))){
					asynchronCircle(p);
				}else if (p.isSneaking() && SelectedProducts.getSelectedProducts(p, ShopCategory.SPECIAL).contains(ProductUtils.idProduct.get(504))){
					asynchronCube(p);
				}else if (p.isSneaking() && SelectedProducts.getSelectedProducts(p, ShopCategory.SPECIAL).contains(ProductUtils.idProduct.get(505))){
					asynchronHitbox(p);
				}else{
					playParticle(p, sp.toEnumParticle(), e.getTo(), p.getVelocity(), 3);
				}
			}
		}
	}
	
	public static ArrayList<Location> getCircleLocationList(double r, Location ploc){
		ArrayList<Location> list = new ArrayList<>();
		for (double x = -r; x <= r; x = x+0.1){
			double y = f(x, r);
			Location loc1 = new Location(ploc.getWorld(), ploc.getX()+x, ploc.getY()+0.5, ploc.getZ()+y);
			list.add(loc1);
			if (x != r && x != -r){
				Location loc2 = new Location(ploc.getWorld(), ploc.getX()-x, ploc.getY()+0.5, ploc.getZ()-y);
				list.add(loc2);
			}
		}
		return list;
	}
	
	public static double f(double x, double r){
//		f(x)=(r²-(x+a)²)^0.5
		return Math.pow(Math.pow(r, 2.0)-Math.pow(x, 2.0), 0.5);
	}
	
	public static void asynchronCircle(Player p){
		double r = RADIUS;
		ShopProduct sp = SelectedProducts.getSelectedParticle(p);
		Location ploc = p.getLocation();
		ArrayList<Location> list = new ArrayList<>();
		for (double x = -r; x <= r; x = x+INTERVALL){
			double y = Math.pow(Math.pow(r, 2.0)-Math.pow(x, 2.0), 0.5);
			Location loc1 = new Location(ploc.getWorld(), ploc.getX()+x, ploc.getY()+0.5, ploc.getZ()+y);
			list.add(loc1);
			if (x != r && x != -r){
				Location loc2 = new Location(ploc.getWorld(), ploc.getX()-x, ploc.getY()+0.5, ploc.getZ()-y);
				list.add(loc2);
			}
		}
		for (Location loc : list){
			playParticle(p, sp.toEnumParticle(), loc, p.getVelocity(), 1);
		}
	}
	
	public static void asynchronCube(Player p){
		double r = CUBEDRADIUS;
		ShopProduct sp = SelectedProducts.getSelectedParticle(p);
		Location ploc = p.getLocation();
		ArrayList<Location> list = new ArrayList<>();
		
//		Bottom
		for (double x = -r; x <= r; x = x+INTERVALL){
			if (x == -r || x == r){
				for (double y = -r; y <= r; y = y+INTERVALL){
					list.add(new Location(ploc.getWorld(), ploc.getX()+x, ploc.getY(), ploc.getZ()+y));
				}
			}else{
				list.add(new Location(ploc.getWorld(), ploc.getX()+x, ploc.getY(), ploc.getZ()+r));
				list.add(new Location(ploc.getWorld(), ploc.getX()+x, ploc.getY(), ploc.getZ()-r));
			}
		}
		
//		Up
		ArrayList<Location> forlist = new ArrayList<>();
		forlist.addAll(list);
		for (Location loc : forlist){
			Location l = new Location(loc.getWorld(), loc.getX(), loc.getY()+2, loc.getZ());
			list.add(l);
		}
		
//		Mid
		for (double y = INTERVALL; y < 2.0; y = y+INTERVALL){
			list.add(new Location(ploc.getWorld(), ploc.getX()+r, ploc.getY()+y, ploc.getZ()+r));
			list.add(new Location(ploc.getWorld(), ploc.getX()-r, ploc.getY()+y, ploc.getZ()+r));
			list.add(new Location(ploc.getWorld(), ploc.getX()+r, ploc.getY()+y, ploc.getZ()-r));
			list.add(new Location(ploc.getWorld(), ploc.getX()-r, ploc.getY()+y, ploc.getZ()-r));
		}
		
		for (Location loc : list){
			playParticle(p, sp.toEnumParticle(), loc, p.getVelocity(), 1);
		}
	}
	
	public static void asynchronHitbox(Player p){
		double r = HITBOXRADIUS;
		ShopProduct sp = SelectedProducts.getSelectedParticle(p);
		Location ploc = p.getLocation();
		ArrayList<Location> list = new ArrayList<>();
		
//		Bottom
		for (double x = -r; x <= r; x = x+INTERVALL){
			if (x == -r || x == r){
				for (double y = -r; y <= r; y = y+INTERVALL){
					list.add(new Location(ploc.getWorld(), ploc.getX()+x, ploc.getY(), ploc.getZ()+y));
				}
			}else{
				list.add(new Location(ploc.getWorld(), ploc.getX()+x, ploc.getY(), ploc.getZ()+r));
				list.add(new Location(ploc.getWorld(), ploc.getX()+x, ploc.getY(), ploc.getZ()-r));
			}
		}
		
//		Up
		ArrayList<Location> forlist = new ArrayList<>();
		forlist.addAll(list);
		for (Location loc : forlist){
			Location l = new Location(loc.getWorld(), loc.getX(), loc.getY()+HITBOXHIGH, loc.getZ());
			list.add(l);
		}
		
//		Middle
		for (double y = 0.2; y < HITBOXHIGH; y = y+0.2){
			list.add(new Location(ploc.getWorld(), ploc.getX()+r, ploc.getY()+y, ploc.getZ()+r));
			list.add(new Location(ploc.getWorld(), ploc.getX()-r, ploc.getY()+y, ploc.getZ()+r));
			list.add(new Location(ploc.getWorld(), ploc.getX()+r, ploc.getY()+y, ploc.getZ()-r));
			list.add(new Location(ploc.getWorld(), ploc.getX()-r, ploc.getY()+y, ploc.getZ()-r));
		}
		
		for (Location loc : list){
			playParticle(p, sp.toEnumParticle(), loc, p.getVelocity(), 1);
		}
	}
	
	public static void asynchronDragon(Player p){
		ids++;
		final int id = ids;
		dragons.put(id, 0.5D);
		
		Location ploc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
		ploc.setY(ploc.getY()+1.2);
		ShopProduct sp = SelectedProducts.getSelectedParticle(p);
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Vector v = null;
				double r = dragons.get(id);
				v = ploc.getDirection().multiply(r);
				ploc.add(v);
				playParticle(p, sp.toEnumParticle(), ploc, p.getVelocity(), 3);
				r = r+0.05D;
				dragons.remove(id);
				if (r >= 6.0){
					t.cancel();
					return;
				}
				dragons.put(id, r);
				
			}
		}, 0, 50);
	}
	
	public static void playParticle(Player p, EnumParticle pa, Location location, Vector vector, int amount) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(pa, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), (float) 0, (float) 0, (float) 0, 1, amount);
		for (Player all : Bukkit.getOnlinePlayers()){
			if (SelectedProducts.playersWithEquipment.contains(all)){
				((CraftPlayer)all).getHandle().playerConnection.sendPacket(packet);
			}
		}
    }
	
	
}
