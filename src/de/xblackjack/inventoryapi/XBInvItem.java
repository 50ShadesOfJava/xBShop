package de.xblackjack.inventoryapi;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class XBInvItem {

    private Integer size;
    private String title;
    private ItemStack itemStack;
    private Inventory inventory;
    private XBInventory xbInventory;
    private HashMap<ClickType, XBAction> actions;

    public XBInvItem(ItemStack itemStack, Inventory inventory, XBInventory xbInventory) {
        actions = new HashMap<>();
        setItemStack(itemStack);
        setInventory(inventory);
        setTitle(inventory.getTitle());
        setXbInventory(xbInventory);
    }

    public XBInvItem addAction(ClickType clickType, XBAction xba) {
        actions.put(clickType, xba);
        return this;
    }

    public void create(Player p) {
        XBInventoryListener.xbInventorys.get(p.getUniqueId()).put(getItemStack(), new XBItemProfile(getItemStack(), actions, getInventory()));
    }

    public void noAction(Player p) {
        XBInventoryListener.xbInventorys.get(p.getUniqueId()).put(getItemStack(), new XBItemProfile(getItemStack(),  null, getInventory()));
    }

    public XBInventory getXbInventory() {
        return xbInventory;
    }

    public void setXbInventory(XBInventory xbInventory) {
        this.xbInventory = xbInventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Integer getSize() {
        return size;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}