package darthchest.main;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


import net.minecraft.server.v1_11_R1.Block;
import net.minecraft.server.v1_11_R1.Item;




public class MySQLConnection {

	
	private Connection connection;
    private String host, database, username, password;
    private int port;
    private Statement statement;
	
	public MySQLConnection(String Host, int Port, String Database, String Username, String Password){
		host = Host;
	    port = Port;
	    database = Database;
	    username = Username;
	    password = Password;	    
	}
	
	public void connect(){
		try {     
            openConnection();
            statement = connection.createStatement();    
            
            String sql = "CREATE TABLE IF NOT EXISTS DarthChest_items " +
                    "(ItemType INTEGER NOT NULL, "  + 
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
                    + "recipient VARCHAR(255) NOT NULL)"; 
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
		    		    int ItemType = result.getInt("ItemType");
		    		    double price = result.getDouble("Price");
		    		    Item i = Item.getById(ItemType);
		    		    
		    		    ausgabe.add(new SellableItem(i, price));
		    		    
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	    		
	   
	    	
	    	return ausgabe;
	    }
	    
	    
	    public void saveAutoSellerList(LinkedList<AutoSeller> list){
	    	
	    }
	    
	    public void saveItemList(LinkedList<SellableItem> list){
	    	
	    }
	    
	    
	    
	    
	 
	
}
