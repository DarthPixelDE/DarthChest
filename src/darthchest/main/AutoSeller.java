package darthchest.main;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class AutoSeller {
	
	
	private Location lS, lC;
	private OfflinePlayer rec;
	
	public AutoSeller(Location locSign, OfflinePlayer receiver, Location locChest){
		lS = locSign;
		lC = locChest;
		rec = receiver;		
	}
	
	public Location getSignLocation(){
		return lS;
	}
	
	public Location getChestLocation(){
		return lC;
	}
	
	public OfflinePlayer getReceiver(){
		return rec;
	}
	
	
	
	
	

}
