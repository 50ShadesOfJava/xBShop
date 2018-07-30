package de.xblackjack.inventoryapi;

import java.util.List;

import org.bukkit.event.block.Action;

public class XBItemStackProfile {

    private List<Action> actions;
    private XBAction xbAction;
    private XBItemStack.DropAction dropAction;
    private XBItemStack.InventoryClickAction inventoryClickAction;

    public XBItemStackProfile(List<Action> actions, XBAction xbAction, XBItemStack.DropAction dropAction, XBItemStack.InventoryClickAction inventoryClickAction) {
        setActions(actions);
        setXbAction(xbAction);
        setDropAction(dropAction);
        setInventoryClickAction(inventoryClickAction);
    }

    public XBItemStack.InventoryClickAction getInventoryClickAction() {
        return inventoryClickAction;
    }

    public void setInventoryClickAction(final XBItemStack.InventoryClickAction inventoryClickAction) {
        this.inventoryClickAction = inventoryClickAction;
    }

    public XBItemStack.DropAction getDropAction() {
        return dropAction;
    }

    public void setDropAction(final XBItemStack.DropAction dropAction) {
        this.dropAction = dropAction;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(final List<Action> action) {
        this.actions = action;
    }

    public XBAction getXbAction() {
        return xbAction;
    }

    public void setXbAction(XBAction xbAction) {
        this.xbAction = xbAction;
    }
}