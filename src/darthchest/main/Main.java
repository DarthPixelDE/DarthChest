package darthchest.main;

import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;

import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.Metrics;

import com.Acrobot.ChestShop.ChestShop;

public class Main extends JavaPlugin {

	private static final Logger log = Logger.getLogger("Minecraft");
	private static Economy econ = null;
	private ChestShop cs = null;
	private LinkedList<AutoSeller> AutoSellerList = new LinkedList<AutoSeller>();
	private MySQLConnection con = null;

	
	@Override
	public void onDisable() {
		log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
		con.disconnect();
	}

	@Override
	public void onEnable() {
		
		con = new MySQLConnection("ms815.nitrado.net", 3306, "ni231492_1_DB", "ni231492_1_DB", "password");
		con.connect();
		
		
		
		
		
		
		if (!setupEconomy()) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		try {
			Metrics metrics = new Metrics();
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}

		this.getCommand("test").setExecutor(new Kommando(this, econ));
		this.getCommand("add").setExecutor(new Kommando(this, econ));
		getServer().getPluginManager().registerEvents(new SignListener(this), this);
		autoLogger();
	}

	private void autoLogger() {

		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				// -191 66 226

				Chest ch = null;
				
				for (AutoSeller autoSeller : AutoSellerList) {
					ch = (Chest)autoSeller.getChestLocation().getBlock().getState();
					if (ch.getInventory().getItem(0) != null) {
						ch.getInventory().getItem(0).setAmount(ch.getInventory().getItem(0).getAmount() - 1);
						econ.depositPlayer(autoSeller.getRecipient(), 10);
					} else {
						for (int i = 0; i < ch.getInventory().getSize() - 1; i++) {
							ch.getInventory().setItem(i, ch.getInventory().getItem(i + 1));
						}
					}
				}
			}
		}, 0L, 10L);

	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		if (getServer().getPluginManager().getPlugin("ChestShop") == null) {
			return false;
		} else {
			cs = (ChestShop) getServer().getPluginManager().getPlugin("ChestShop");
			System.out.println(cs.getConfig().getString("SERVER_ECONOMY_ACCOUNT") + " ist der Server Economy-Account.");
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}



	public static Economy getEcononomy() {
		return econ;
	}

	
	public LinkedList<AutoSeller> getAutoSellerList(){
		return AutoSellerList;
	}

}
