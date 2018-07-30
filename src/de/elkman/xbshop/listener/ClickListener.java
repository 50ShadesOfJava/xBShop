package de.elkman.xbshop.listener;

import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.elkman.casino.inventorys.CasinoInventory;
import de.elkman.xbshop.api.ShopCustomer;
import de.elkman.xbshop.inventorys.InvInventory;
import de.elkman.xbshop.inventorys.ShopInventory;
import de.elkman.xbshop.inventorys.WarenkorbInventory;
import de.elkman.xbshop.main.Main;
import de.elkman.xbshop.main.Main.PrefixColor;
import de.elkman.xbshop.main.Var;
import de.elkman.xbshop.utils.players.SelectedProducts;
import de.elkman.xbshop.utils.products.ProductUtils;
import de.elkman.xbshop.utils.products.ShopCategory;
import de.elkman.xbshop.utils.products.ShopProduct;
import de.xblackjack.xbbroad.main.XBBroad;

public class ClickListener implements Listener{

	public ClickListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if (e.getWhoClicked() instanceof Player){
			Player p = (Player) e.getWhoClicked();
			if (e.getClickedInventory() != null && e.getClickedInventory().getTitle() != null){
				if (e.getCurrentItem() != null){
					if (e.getCurrentItem().getItemMeta() != null){
						if (e.getCurrentItem().getItemMeta().getDisplayName() != null){
							int i = e.getSlot();
							ShopCustomer sc = new ShopCustomer(p);
							if (e.getClickedInventory().getTitle().equalsIgnoreCase(Var.INV_MAIN_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
								e.setCancelled(true);
								String name = e.getCurrentItem().getItemMeta().getDisplayName();
								if (name.equalsIgnoreCase("§6§lShoppen gehen")){
									p.openInventory(ShopInventory.getShopInventory(p));
								}else if (name.equalsIgnoreCase("§b§lInventar")){
									p.openInventory(InvInventory.getInvInventory(p));
								}else if (name.equalsIgnoreCase("§5§lIm Casino zocken")){
									p.openInventory(CasinoInventory.getMainInventory(p));
								}
							}else if (e.getClickedInventory().getTitle().equalsIgnoreCase(Var.INV_SHOP_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
								e.setCancelled(true);
								String name = e.getCurrentItem().getItemMeta().getDisplayName();
								if (e.getSlot() > 0 && e.getSlot() < 6){
									ShopCategory cat = ProductUtils.getCategory(name);
									if (cat != sc.getWatchingCategory()){
										ShopInventory.playerCategory.remove(p);
										ShopInventory.playerCategory.put(p, cat);
										p.openInventory(ShopInventory.getShopInventory(p));
									}
								}else if (i > 9 && i != 17 && i != 18 && i != 26 && i != 27 && i != 35 && i != 36 && i < 44){
									if (name.length() > 2){
										String productName = name.substring(2, name.length());
										ShopProduct sp = ProductUtils.nameProduct.get(productName);
										if (!sc.getBuyedProducts().contains(sp)){
											if (!sc.getWarenkorb().contains(sp)){
												ShopInventory.warenkorb.get(p).add(sp);
											}else{
												ShopInventory.warenkorb.get(p).remove(sp);
											}
											p.openInventory(ShopInventory.getShopInventory(p));
										}
									}
								}else if (i == 7 && !sc.getWarenkorb().isEmpty()){
									p.openInventory(WarenkorbInventory.getWarenkorbGUI(p));
									p.playSound(p.getLocation(), Sound.HORSE_SADDLE, 5, 5);
								}
							}else if (e.getClickedInventory().getTitle().equalsIgnoreCase(Var.INV_WARENKORB_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
								e.setCancelled(true);
								if (i == 9 || i == 10 || i == 11 || i == 18 || i == 19 || i == 20 || i == 27 || i == 28 || i == 29){
									p.openInventory(ShopInventory.getShopInventory(p));
								}else if (i == 15 || i == 16 || i == 17 || i == 24 || i == 25 || i == 26 || i == 33 || i == 34 || i == 35){
									int price = WarenkorbInventory.getWarenkorbPrice(sc);
									if (sc.getChips() >= price){
										p.sendMessage(Main.getPrefix() + "Dir wurden §c" + price + " Chips §7abgebucht.");
										ArrayList<ShopProduct> list = new ArrayList<>();
										list.addAll(sc.getWarenkorb());
										for (ShopProduct sp : list){
											if (!sc.getBuyedProducts().contains(sp)){
												sc.buyProduct(sp);
											}
										}
										ShopInventory.warenkorb.remove(p);
										p.closeInventory();
										p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
									}else{
										p.closeInventory();
										p.sendMessage(Main.getPrefix(PrefixColor.WARNING) + "§cDir fehlen §7" + (price-sc.getChips()) + " Chips§c um den Kauf abzuschließen.");
									}
								}
							}else if (e.getClickedInventory().getTitle().equalsIgnoreCase(Var.INV_INVENTAR_TITLE.replaceAll("&", XBBroad.getAccentColor(p)))){
								e.setCancelled(true);
								String name = e.getCurrentItem().getItemMeta().getDisplayName();
								if (e.getSlot() > 0 && e.getSlot() < 6){
									ShopCategory cat = ProductUtils.getCategory(name);
									if (cat != sc.getWatchingCategory()){
										ShopInventory.playerCategory.remove(p);
										ShopInventory.playerCategory.put(p, cat);
										p.openInventory(InvInventory.getInvInventory(p));
									}
								}else if (i > 9 && i != 17 && i != 18 && i != 26 && i != 27 && i != 35 && i != 36 && i < 44){
									if (name.length() > 2){
										String productName = name.substring(2, name.length());
										ShopProduct sp = ProductUtils.nameProduct.get(productName);
										if (sc.getBuyedProducts().contains(sp)){
											if (sc.getWatchingCategory() == ShopCategory.PARTICLE){
												if (SelectedProducts.getSelectedParticle(p) != null){
													ShopProduct current = SelectedProducts.getSelectedParticle(p);
													if (current == sp){
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
													}else{
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
														SelectedProducts.selectedProducts.get(p.getName()).add(sp);
													}
												}else{
													SelectedProducts.selectedProducts.get(p.getName()).add(sp);
												}
												p.openInventory(InvInventory.getInvInventory(p));
											}else if (sc.getWatchingCategory() == ShopCategory.ARMOR){
												ShopProduct current = null;
												if (sp.getSlot() == 16 || sp.getSlot() == 25 || sp.getSlot() == 34 || sp.getSlot() == 43){
													if (SelectedProducts.selectedProducts.get(p.getName()).contains(sp)){
														SelectedProducts.selectedProducts.get(p.getName()).remove(sp);
													}else{
														SelectedProducts.selectedProducts.get(p.getName()).add(sp);
													}
													if (sp.getSlot() == 16){
														if (SelectedProducts.getSelectedHelmet(p) != null){
															sc.equipArmor(SelectedProducts.getSelectedHelmet(p));
														}
													}else if (sp.getSlot() == 25){
														if (SelectedProducts.getSelectedChestplate(p) != null){
															sc.equipArmor(SelectedProducts.getSelectedChestplate(p));
														}
													}else if (sp.getSlot() == 34){
														if (SelectedProducts.getSelectedLeggings(p) != null){
															sc.equipArmor(SelectedProducts.getSelectedLeggings(p));
														}
													}else if (sp.getSlot() == 43){
														if (SelectedProducts.getSelectedBoots(p) != null){
															sc.equipArmor(SelectedProducts.getSelectedBoots(p));
														}
													}
												}else{
													if (sp.getSlot() > 9 && sp.getSlot() < 15){
														current = SelectedProducts.getSelectedHelmet(p);
													}else if (sp.getSlot() > 18 && sp.getSlot() < 24){
														current = SelectedProducts.getSelectedChestplate(p);
													}else if (sp.getSlot() > 27 && sp.getSlot() < 33){
														current = SelectedProducts.getSelectedLeggings(p);
													}else if (sp.getSlot() > 36 && sp.getSlot() < 42){
														current = SelectedProducts.getSelectedBoots(p);
													}
													if (current == null){
														SelectedProducts.selectedProducts.get(p.getName()).add(sp);
														sc.equipArmor(sp);
													}else if (current != sp){
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
														SelectedProducts.selectedProducts.get(p.getName()).add(sp);
														sc.equipArmor(sp);
													}else if (current == sp){
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
														sc.unequipArmor(sp);
													}
												}
												p.openInventory(InvInventory.getInvInventory(p));
											}else if (sc.getWatchingCategory() == ShopCategory.ABILITY){
												ShopProduct current = SelectedProducts.getSelectedProduct(p, ShopCategory.ABILITY);
												if (current != null){
													if (current == sp){
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
														sc.unequipEffect();
													}else{
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
														sc.unequipEffect();
														SelectedProducts.selectedProducts.get(p.getName()).add(sp);
														sc.equipEffect(sp);
													}
												}else{
													SelectedProducts.selectedProducts.get(p.getName()).add(sp);
													sc.equipEffect(sp);
												}
												p.openInventory(InvInventory.getInvInventory(p));
											}else if (sc.getWatchingCategory() == ShopCategory.GADGET){
												if (SelectedProducts.getSelectedProduct(p, ShopCategory.GADGET) != null){
													ShopProduct current = SelectedProducts.getSelectedProduct(p, ShopCategory.GADGET);
													if (current == sp){
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
														sc.unequipGadget();
													}else{
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
														SelectedProducts.selectedProducts.get(p.getName()).add(sp);
														sc.equipGadget();
													}
												}else{
													SelectedProducts.selectedProducts.get(p.getName()).add(sp);
													sc.equipGadget();
												}
												p.openInventory(InvInventory.getInvInventory(p));
											}else if (sc.getWatchingCategory() == ShopCategory.SPECIAL){
												if (SelectedProducts.getSelectedProduct(p, ShopCategory.SPECIAL) != null){
													ShopProduct current = SelectedProducts.getSelectedProduct(p, ShopCategory.SPECIAL);
													if (current == sp){
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
													}else{
														SelectedProducts.selectedProducts.get(p.getName()).remove(current);
														SelectedProducts.selectedProducts.get(p.getName()).add(sp);
													}
												}else{
													SelectedProducts.selectedProducts.get(p.getName()).add(sp);
												}
												p.openInventory(InvInventory.getInvInventory(p));
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}