package darthchest.main;

import org.bukkit.inventory.ItemStack;

public class SellableItem {
	
	
	private ItemStack item;
	private double price;
	
	public SellableItem(ItemStack Item, double Price){
		item = Item.clone();
		price = Price;
	}
	
	
	public ItemStack getItem(){
		return item;
		
		
	}
	
	public double getPrice(){
		return price;
	}
	

}
