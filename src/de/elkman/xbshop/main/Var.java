package de.elkman.xbshop.main;

import de.elkman.crates.main.CrateData;
import de.xblackjack.xbbroad.main.XBBroad;

public class Var {
 
    public static final String INV_MAIN_TITLE = "&§lxB §7- &Shopsystem";
    public static final String INV_SHOP_TITLE = "&§lxB §7- &Shop";
    public static final String INV_WARENKORB_TITLE = "&§lxB §7- &Shopping Cart";
    public static final String INV_INVENTAR_TITLE = "&§lxB §7- &Inventory";
   
    public static final String INV_CASINOMAIN_TITLE = "&§lxB §7- &Casino";
    public static final String INV_CASINO_COIN_TITLE = "&§lxB §7- &Coin";
    public static final String INV_CASINO_LEVER_TITLE = "&§lxB §7- &Lever";
    public static final String INV_CASINO_LOTTERY_TITLE = "&§lxB §7- &Lottery";
    public static final String INV_CASINO_BUY_LOTTERY_TITLE = "&§lxB §7- &$$$";
   
    public static final String INV_CRATEADMIN_TITLE = "&§lxB §7- &CrateSystem";
    public static final String INV_CRATEINFO_TITLE = "&§lxB §7- &Crate-Info";
    public static final String INV_CRATELIST_AVAILABLE_TITLE = "&§lxB §7- &AvailableCrates";
    public static final String INV_CRATELIST_TAKEN_TITLE = "&§lxB §7- &TakenCrates";
    
    public static final int DAILY_CHIPS = 20;
    public static final int DAILY_STREAK = 10;
    public static final int DAILY_PREMIUM = 10;
   
    public static final int CASINO_LOTTERY_PRICE = 300;
    
    public static final int GADGET_SLOT = 3;
    
    @SuppressWarnings("static-access")
	public static String getPDF(){
    	String url = "xblackjack.de/phpmyadmin/sql.php?db=" + XBBroad.getXbSQL().getDbdatabase() + "&table=" + CrateData.CRATES_TABLE + "&printview=1&sql_query=SELECT+*+FROM+`" + CrateData.CRATES_TABLE + "`&token=a29527f84c4e52b7c66b100283e821b6";
    	return url;
    }
}