package darthchest.main;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	
	RegisteredServiceProvider<Economy> serv = getServer().getServicesManager().getRegistration(Economy.class);


	
	
	
}
