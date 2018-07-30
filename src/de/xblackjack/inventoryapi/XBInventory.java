package de.xblackjack.inventoryapi;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class XBInventory {

    private Inventory inventory;
    private Integer size;
    private String title;

    public XBInventory(Integer size, String title) {
        setInventory(Bukkit.createInventory(null, size, title));
        setSize(size);
        setTitle(title);
    }

    public XBInvItem addItem(ItemStack itemStack, Integer... slot) {
        for (Integer o : slot) {
            this.inventory.setItem(o, itemStack);
        }
        return new XBInvItem(itemStack, getInventory(), this);
    }

    public XBInvItem fillInventory(ItemStack itemStack) {
        for (int i = 0; i < size; ++i) {
            this.inventory.setItem(i, itemStack);
        }
        return new XBInvItem(itemStack, getInventory(), this);
    }


    public Inventory getXBInventory() {
        return getInventory();
    }

    private Inventory getInventory() {
        return inventory;
    }

    private void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @SuppressWarnings("unused")
	private Integer getSize() {
        return size;
    }

    private void setSize(Integer size) {
        this.size = size;
    }

    @SuppressWarnings("unused")
	private String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }
}