package darthchest.main;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import net.milkbowl.vault.economy.Economy;
//import net.minecraft.server.v1_11_R1.Item;

public class Kommando implements CommandExecutor{

	
	private Economy econ = null;
	private Main plugin = null;
	
	public Kommando(Plugin Plugin, Economy Econ){
		plugin = (Main)Plugin;
		econ = Econ;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		
			if(cmd.getName().equals("test")){
				sender.sendMessage(sender.getName() + ":" +econ.hasAccount((OfflinePlayer)sender));
				sender.sendMessage(sender.getName() + ": " + econ.getBalance((OfflinePlayer)sender));
				//TODO: DEBUG
				for(int i =0; i< plugin.getSellableItemList().size(); i++){
					System.out.println(plugin.getSellableItemList().get(i).getItem().getData());							
				}			
				
				return true;
			}else if(cmd.getName().equals("darth_additem")){
				if(args.length == 1){
					if(isNumeric(args[0])){
						if(sender instanceof Player){
							Player p = (Player) sender;
							if(!(p.getInventory().getItemInMainHand().getType().equals(Material.AIR) || p.getInventory().getItemInMainHand().getType().equals(Material.STATIONARY_LAVA) || p.getInventory().getItemInMainHand().getType().equals(Material.STATIONARY_WATER))){
								plugin.getSellableItemList().add(new SellableItem(p.getInventory().getItemInMainHand(),Double.parseDouble(args[0])));							
								
								
								//TODO: DEBUG
								for(int i =0; i< plugin.getSellableItemList().size(); i++){
									System.out.println(plugin.getSellableItemList().get(i).getItem());							
								}
								p.sendMessage("Successfully added item");
								return true;
							}else p.sendMessage(p.getInventory().getItemInMainHand().getType()+ " kann nicht verkauft werden.");
						}else sender.sendMessage("Command is Player-only");					
					}else sender.sendMessage("Price can only be a numeric Value");
				}else sender.sendMessage("Wrong number of arguments");			
			}			
				
		return false;
	}
	
	
	private boolean isNumeric(String s){
		try{
			Double.parseDouble(s);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}

}
