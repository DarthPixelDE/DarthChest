package darthchest.main;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import net.minecraft.server.v1_11_R1.Block;
import net.minecraft.server.v1_11_R1.Item;




public class MySQLConnection {

	
	private Connection connection;
    private String host, database, username, password;
    private int port;
    private Statement statement;
    private Plugin p;
	
	public MySQLConnection(String Host, int Port, String Database, String Username, String Password, Plugin plugin){
		host = Host;
	    port = Port;
	    database = Database;
	    username = Username;
	    password = Password;	
	    p = plugin;
	}
	
	public void connect(){
		try {     
            openConnection();
            statement = connection.createStatement();    
            
            String sql = "CREATE TABLE IF NOT EXISTS DarthChest_items " +
                    "(ItemID INTEGER NOT NULL, "  +
            		"ItemMaterial VARCHAR(255) NOT NULL, "+
                    " Price DOUBLE NOT NULL)"; 
            
            statement.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS DarthChest_autoChests " +
                    "(World VARCHAR(255) NOT NULL, "  + 
                    " signX DOUBLE NOT NULL, "
                    + "signY DOUBLE NOT NULL, "
                    + "signZ DOUBLE NOT NULL, "
                    + "chestX DOUBLE NOT NULL, "
                    + "chestY DOUBLE NOT NULL,"
                    + "chestZ DOUBLE NOT NULL, "
                    + "receiver VARCHAR(255) NOT NULL)"; 
            statement.execute(sql);
            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void disconnect(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	    private void openConnection() throws SQLException, ClassNotFoundException {
	    if (connection != null && !connection.isClosed()) {
	        return;
	    }

	    synchronized (this) {
	        if (connection != null && !connection.isClosed()) {
	            return;
	        }
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
	    }
	}
	    
	    
	    public LinkedList<SellableItem> loadSellableList(){
	    	
	    	LinkedList<SellableItem> ausgabe = null;
	    	
	    	
	    		ausgabe = new LinkedList<SellableItem>();
	    		
	    		
	    		ResultSet result;
				try {
					result = statement.executeQuery("SELECT * FROM DarthChest_items");
					
					while (result.next()) {
		    		    ItemStack i = new ItemStack(Material.matchMaterial(result.getString("ItemMaterial")));
		    		    double price = result.getDouble("Price");	    		    
		    		    ausgabe.add(new SellableItem(i, price));		    		    
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	    		
	   
	    	
	    	return ausgabe;
	    }
	    
	    public LinkedList<AutoSeller> loadAutoSellerList(){
	    	LinkedList<AutoSeller> ausgabe = null;
	    	
	    	
    		ausgabe = new LinkedList<AutoSeller>();
    		
    		
    		ResultSet result;
			try {
				result = statement.executeQuery("SELECT * FROM DarthChest_autoChests");
				
				while (result.next()) {
	    		 World w =  p.getServer().getWorld(UUID.fromString(result.getString("World")));
	    		 double x,y,z;
	    		 
	    		 x = result.getDouble("signX");
	    		 y = result.getDouble("signY");
	    		 z = result.getDouble("signZ");
	    		 Location signLoc = new Location(w,x,y,z);
	    		 
	    		 x= result.getDouble("chestX");
	    		 y= result.getDouble("chestY");
	    		 z= result.getDouble("chestZ");
	    		 Location chestLoc = new Location(w,x,y,z);
	    		 
	    		 OfflinePlayer player = p.getServer().getOfflinePlayer(UUID.fromString(result.getString("receiver")));
	    		 
	    		 ausgabe.add(new AutoSeller(signLoc, player,chestLoc));   		 
	    		    
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    		
   
    	
    	return ausgabe;
	    }
	    
	    
	    public void saveAutoSellerList(LinkedList<AutoSeller> list){
	    	
	    	PreparedStatement ps;
			Statement statement;
			try {
				statement = connection.createStatement();
				statement.execute("TRUNCATE TABLE DarthChest_autoChests");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	try {   		
				
				
				ps = connection.prepareStatement("INSERT INTO `DarthChest_autoChests`(World, signX, signY, signZ, chestX, chestY, chestZ, receiver) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");			
				
				for (AutoSeller autoSeller : list) {					
					ps.setString(1, autoSeller.getSignLocation().getWorld().getUID().toString());
			    	ps.setDouble(2, autoSeller.getSignLocation().getX());
			    	ps.setDouble(3, autoSeller.getSignLocation().getY());
			    	ps.setDouble(4, autoSeller.getSignLocation().getZ());
			    	ps.setDouble(5, autoSeller.getChestLocation().getX());
			    	ps.setDouble(6, autoSeller.getChestLocation().getY());
			    	ps.setDouble(7, autoSeller.getChestLocation().getZ());
			    	ps.setString(8, autoSeller.getReceiver().getUniqueId().toString());			    	
			    	ps.executeUpdate();					
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	
	    	
	    }
	    
	    public void saveItemList(LinkedList<SellableItem> list){
	    	PreparedStatement ps;
			Statement statement;
			try {
				statement = connection.createStatement();
				statement.execute("TRUNCATE TABLE DarthChest_items");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	try {   		
				
				
				ps = connection.prepareStatement("INSERT INTO `DarthChest_items`(ItemID, ItemMaterial, Price) VALUES (?, ?, ?);");			
				
				for (SellableItem sellableItem : list) {					
					ps.setString(1, String.valueOf(sellableItem.getItem().getTypeId()));
					ps.setString(2, sellableItem.getItem().getType().toString());					
			    	ps.setDouble(3, sellableItem.getPrice());			    	
			    	ps.executeUpdate();					
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    
	    
	    
	 
	
}
