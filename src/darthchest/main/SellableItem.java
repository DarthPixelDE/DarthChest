package darthchest.main;

import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_11_R1.Item;

public class SellableItem {
	
	
	private Item item;
	private double price;
	
	public SellableItem(Item Item, double Price){
		item = Item;
		
		
		
		price = Price;
	}
	
	
	public Item getItem(){
		return item;
	}
	
	public double getPrice(){
		return price;
	}
	

}
