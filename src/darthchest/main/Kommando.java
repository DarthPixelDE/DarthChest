package darthchest.main;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.milkbowl.vault.economy.Economy;

public class Kommando implements CommandExecutor{

	
	private Economy econ = null;
	private Plugin plugin = null;
	
	public Kommando(Plugin Plugin, Economy Econ){
		plugin = Plugin;
		econ = Econ;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		if(sender instanceof Player) {
			if(cmd.getName().equals("test")){
				sender.sendMessage(sender.getName() + ":" +econ.hasAccount((OfflinePlayer)sender));
				sender.sendMessage(sender.getName() + ": " + econ.getBalance((OfflinePlayer)sender));
				
				return true;
			}else if(cmd.getName().equals("add")){
				econ.depositPlayer((OfflinePlayer)sender, 5000);
			}
			
		}
		
		return false;
	}

}
