package de.xblackjack.inventoryapi;

import java.util.HashMap;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class XBItemProfile {

    private ItemStack itemStack;
    private HashMap<ClickType, XBAction> actions;
    private Inventory inventory;
    private boolean canceled = true;

    public XBItemProfile(ItemStack itemStack, HashMap<ClickType, XBAction> actions, Inventory inv) {
        setItemStack(itemStack);
        setActions(actions);
        setInventory(inv);
        setCanceled(true);
    }

    public XBItemProfile(ItemStack itemStack, HashMap<ClickType, XBAction> actions, Inventory inv, boolean canceled) {
        setItemStack(itemStack);
        setActions(actions);
        setInventory(inv);
        setCanceled(canceled);
    }

    public HashMap<ClickType, XBAction> getActions() {
        return actions;
    }

    public void setActions(final HashMap<ClickType, XBAction> actions) {
        this.actions = actions;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}