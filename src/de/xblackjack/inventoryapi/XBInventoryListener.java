package de.xblackjack.inventoryapi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import de.elkman.xbshop.main.Main;

public class XBInventoryListener implements Listener {

	public XBInventoryListener(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
    public static HashMap<UUID, HashMap<ItemStack, XBItemProfile>> xbInventorys = new HashMap<>();
    public static HashMap<UUID, HashMap<ItemStack, XBItemStackProfile>> xbItemsMap = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (xbInventorys.containsKey(e.getPlayer().getUniqueId())) return;
        xbInventorys.put(e.getPlayer().getUniqueId(), new HashMap<>());
        if (xbItemsMap.containsKey(e.getPlayer().getUniqueId())) return;
        xbItemsMap.put(e.getPlayer().getUniqueId(), new HashMap<>());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!xbItemsMap.containsKey(p.getUniqueId())) return;
        Map<ItemStack, XBItemStackProfile> xbItems = xbItemsMap.get(e.getPlayer().getUniqueId());
        if (xbItems.containsKey(e.getItem())) {
            if (xbItems.get(e.getItem()).getActions().contains(e.getAction())) {
                xbItems.get(e.getItem()).getXbAction().run();
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (!xbItemsMap.containsKey(p.getUniqueId())) return;
        Map<ItemStack, XBItemStackProfile> xbItems = xbItemsMap.get(e.getPlayer().getUniqueId());
        ItemStack itemStack = e.getItemDrop().getItemStack();
        if (itemStack == null) return;
        if (xbItems.containsKey(itemStack)) {
            XBItemStack.DropAction dropAction = xbItems.get(itemStack).getDropAction();
            e.setCancelled(dropAction == XBItemStack.DropAction.CANCELED);
            if (dropAction == XBItemStack.DropAction.REMOVE) e.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onInvClickEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;

        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != null && e.getInventory() != null) {
            Map<ItemStack, XBItemProfile> invItems = xbInventorys.get(e.getWhoClicked().getUniqueId());
            if (invItems.containsKey(e.getCurrentItem()) && invItems.get(e.getCurrentItem()).getInventory().equals(e.getClickedInventory())) {
                if (e.getInventory().getName().equals(invItems.get(e.getCurrentItem()).getInventory().getName())) {
                    if (invItems.get(e.getCurrentItem()).isCanceled()) {
                        e.setCancelled(true);
                    }
                    if (invItems.get(e.getCurrentItem()).getActions() == null) return;
                    if (invItems.get(e.getCurrentItem()).getActions().containsKey(e.getClick())) {
                        invItems.get(e.getCurrentItem()).getActions().get(e.getClick()).run();
                    }
                }
            }
        }

        Player p = (Player) e.getWhoClicked();
        if (!xbItemsMap.containsKey(p.getUniqueId())) return;
        Map<ItemStack, XBItemStackProfile> xbItems = xbItemsMap.get(p.getUniqueId());
        ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null) return;
        if (xbItems.containsKey(itemStack)) {
            XBItemStack.InventoryClickAction inventoryClickAction = xbItems.get(itemStack).getInventoryClickAction();
            e.setCancelled(inventoryClickAction == XBItemStack.InventoryClickAction.CANCELED);
        }

    }

}