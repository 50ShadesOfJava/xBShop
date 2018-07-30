package de.elkman.xbshop.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.elkman.casino.main.CasinoInventoryClickListener;
import de.elkman.casino.main.CasinoInventoryCloseListener;
import de.elkman.casino.main.DailyMain;
import de.elkman.crates.commands.CrateAdminCommand;
import de.elkman.crates.commands.CrateCommand;
import de.elkman.crates.commands.CrateInfoCommand;
import de.elkman.crates.crateadmin.CrateAdminInventory;
import de.elkman.crates.crateadmin.CrateInfoInventory;
import de.elkman.crates.crateadmin.CrateListInventorys;
import de.elkman.xbshop.api.AsyncMoveEvent;
import de.elkman.xbshop.commands.ChipsCommand;
import de.elkman.xbshop.commands.SendchipsCommand;
import de.elkman.xbshop.commands.XBShopCommand;
import de.elkman.xbshop.extras.DoubleJump;
import de.elkman.xbshop.extras.Jetpack;
import de.elkman.xbshop.extras.ParticleSpawner;
import de.elkman.xbshop.listener.ClickListener;
import de.elkman.xbshop.listener.InteractListener;
import de.elkman.xbshop.listener.JoinListener;
import de.elkman.xbshop.listener.QuitListener;
import de.elkman.xbshop.sql.MySQL;
import de.elkman.xbshop.utils.products.ProductUtils;
import de.xblackjack.inventoryapi.XBInventoryListener;

public class Main extends JavaPlugin{

	public void onEnable(){
		instance = this;
		registerClasses();
		AsyncMoveEvent.runTask();
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}
	
	public void registerClasses(){
		new CasinoInventoryClickListener(this);
		new CasinoInventoryCloseListener(this);
		new DailyMain(this);
		
		new CrateAdminCommand(this);
		new CrateCommand(this);
		new CrateInfoCommand(this);
		
		new CrateAdminInventory(this);
		new CrateInfoInventory(this);
		new CrateListInventorys(this);
		
		new ChipsCommand(this);
		new SendchipsCommand(this);
		new XBShopCommand(this);
		
		new DoubleJump(this);
		new Jetpack(this);
		new ParticleSpawner(this);	
		
		new ClickListener(this);
		new InteractListener(this);
		new JoinListener(this);
		new QuitListener(this);
		
		new MySQL(this);
		
		new ProductUtils(this);
		
		new XBInventoryListener(this);
		
	}
	
	public static Main instance;
	public static Main getInstance(){
		return instance;
	}
	
	private static final String PREFIX = "§1▐ §9§oxB§1» §7";
	
	public static String getPrefix(PrefixColor prefixColor) {
        switch (prefixColor) {
            case NORMAL:
                return PREFIX;
            case SUCCESS:
                return PREFIX.replace("§1", "§2")
                        .replace("§9", "§a");
            case WARNING:
                return PREFIX.replace("§1", "§4")
                        .replace("§9", "§c");
            default:
                return PREFIX;
        }
    }
	
	public static String getPrefix() {
        return PREFIX;
    }

    public static String getBracket() {
        return PREFIX.substring(0, 4) + "§7";
    }
    
    public static String getBracket(PrefixColor prefixColor) {
        return getPrefix(prefixColor).substring(0, 4) + "§7";
    }
    
    public enum PrefixColor {
        SUCCESS,
        WARNING,
        NORMAL;
    }
}