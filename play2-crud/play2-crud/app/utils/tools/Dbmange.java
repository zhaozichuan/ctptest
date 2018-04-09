package utils.tools;
import java.sql.Connection;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import utils.redis.RedisAPI;



public  class Dbmange {
	 public static   ConnectionPool conPool=null;
          // FIXME(ZOwl):
	 
	  public static void initDb() {
	  
		ConnectionParam conParam=new ConnectionParam();
		conParam.setDriver("com.mysql.jdbc.Driver");
		//conParam.setUrl("jdbc:mysql://120.25.13.80:3306/hqwork");
		conParam.setUrl("jdbc:mysql://120.25.13.80:3306/hqwork");
		conParam.setUser("root");
		conParam.setPassword("");
		ConnectionPool conPool1=new ConnectionPool(conParam);
		
		try {
			conPool1.createPool();
			conPool=conPool1;
			//conn= conPool.getConnection();
		
		
		}catch (Exception e) {
			// TODO: handle exception
		}
	  }
	  
	  public static Connection  getConn() {
		  
			
			try {
			if(conPool==null){
				initDb();
			}
				
			conPool.createPool();
    		return	conPool.getConnection();
			
			}catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		  }
	  
	  
}
