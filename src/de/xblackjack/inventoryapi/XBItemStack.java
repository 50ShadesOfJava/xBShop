package de.xblackjack.inventoryapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class XBItemStack {

    private ItemStack itemStack;
    private XBAction xbAction;
    private List<Action> actions;
    private DropAction dropAction;
    private InventoryClickAction inventoryClickAction;

    public XBItemStack(ItemStack itemStack) {
        dropAction = DropAction.CANCELED;
        this.inventoryClickAction = InventoryClickAction.CANCELED;
        setItemStack(itemStack);
    }

    public void give(Player p, Integer... i) {
        for (Integer lol : i) {
            p.getInventory().setItem(lol, itemStack);
        }
    }

    public void setAction(Player p, XBAction xba, Action... action) {
        setXbAction(xba);
        setActions(new ArrayList<>());
        getActions().addAll(Arrays.asList(action));
        XBInventoryListener.xbItemsMap.get(p.getUniqueId()).put(itemStack, new XBItemStackProfile(getActions(), xba, dropAction , inventoryClickAction));
    }

    public void setDropAction(Player p, DropAction dropAction) {
        this.dropAction = dropAction;
        if (XBInventoryListener.xbItemsMap.get(p.getUniqueId()).containsKey(itemStack)){
            XBInventoryListener.xbItemsMap.get(p.getUniqueId()).get(itemStack).setDropAction(dropAction);
        }
    }

    public void setInventoryClickAction(Player p, InventoryClickAction inventoryClickAction) {
        this.inventoryClickAction = inventoryClickAction;
        if (XBInventoryListener.xbItemsMap.get(p.getUniqueId()).containsKey(itemStack)){
            XBInventoryListener.xbItemsMap.get(p.getUniqueId()).get(itemStack).setInventoryClickAction(inventoryClickAction);
        }
    }

    public enum InventoryClickAction {
        CANCELED,
        NONE;
    }

    public enum DropAction {
        REMOVE,
        CANCELED,
        NONE;
    }

    public DropAction getDropAction() {
        return dropAction;
    }

    public void setDropAction(final DropAction dropAction) {
        this.dropAction = dropAction;
    }

    public XBAction getXbAction() {
        return xbAction;
    }

    public void setXbAction(XBAction xbAction) {
        this.xbAction = xbAction;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(final List<Action> actions) {
        this.actions = actions;
    }
}
