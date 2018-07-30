package de.elkman.xbshop.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.elkman.xbshop.utils.products.ShopProduct;

public class PlayerBuyProductEvent extends Event{

	public static HandlerList handlers = new HandlerList();
	Player player;
	ShopCustomer sc;
	ShopProduct sp;
	
	public PlayerBuyProductEvent(Player p, ShopProduct product) {
		player = p;
		sc = new ShopCustomer(p);
		sp = product;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public ShopCustomer getShopCustomer(){
		return sc;
	}
	
	public ShopProduct getProduct(){
		return sp;
	}
	
	@Override
	public HandlerList getHandlers(){
		return handlers;
	}

	public static HandlerList getHandlerList(){
		return handlers;
	}
}
