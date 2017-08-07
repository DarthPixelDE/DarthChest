package darthchest.main;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.Plugin;

public class SignListener implements Listener {

	private LinkedList<AutoSeller> AutoSellerList;

	private Main plugin = null;

	public SignListener(Plugin p) {
		super();
		plugin = (Main) p;
		AutoSellerList = plugin.getAutoSellerList();
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (e.getLine(0).equals("AUTOSELL")) {
			Location signLoc = e.getBlock().getLocation();
			Location chestLoc = null;
			chestLoc = nearChest(signLoc);
			if (e.getLine(3).equals("")) {
				if (chestLoc != null) {
					AutoSellerList.add(new AutoSeller(signLoc, (OfflinePlayer) e.getPlayer(), chestLoc));
					e.getPlayer().sendMessage("AutoSell Kiste erfolgreich erstellt.");
				}
			} else {
				if (chestLoc != null) {
					AutoSellerList
							.add(new AutoSeller(signLoc, plugin.getServer().getOfflinePlayer(e.getLine(3)), chestLoc));
					e.getPlayer().sendMessage("AutoSell Kiste erfolgreich erstellt.");
				}
				
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getBlock().getType().equals(Material.CHEST) || isSign(e.getBlock())){
			
			for (AutoSeller autoSeller : AutoSellerList) {
				if(autoSeller.getChestLocation().equals(e.getBlock().getLocation()) || autoSeller.getSignLocation().equals(e.getBlock().getLocation())){
					AutoSellerList.remove(autoSeller);
				}
			}			
		}			
	}
	
	
	
	private Boolean isSign(Block block){		
		if(block.getType().equals(Material.SIGN) || block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN)){
			return true;
		}
		
		return false;
	}

	private Location nearChest(Location loc) {

		Location[] locArray = new Location[6];
		Location chestLoc = null;

		locArray[0] = new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ());
		locArray[1] = new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ());
		locArray[2] = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());
		locArray[3] = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ());
		locArray[4] = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1);
		locArray[5] = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1);

		for (int i = 0; i < locArray.length; i++) {
			if (locArray[i].getBlock().getType().equals(Material.CHEST)) {
				chestLoc = locArray[i];
				break;
			}
		}
		return chestLoc;
	}

}
