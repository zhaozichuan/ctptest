package thread;


import controllers.real.StockMarkets;
import models.Time_line;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import utils.redis.RedisAPI;
import utils.tools.WorkDayTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;


public class StockMarketPriceUpdate extends TimerTask{
	private static String cleanDate = "";
	private static final int STEP =100;// 3000;
	private static JedisPool pool=RedisAPI.getPool();;
	private static Jedis j=pool.getResource(); ;//Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
	private  String grid ;
	public StockMarketPriceUpdate(String tmpgrid){
		
		  pool = RedisAPI.getPool();  
    	  j=  pool.getResource();  
    	 grid = tmpgrid;
    	 //System.out.println("StockMarketPriceUpdate contru");
	}
	
	
	
	
	
	
	public void run(){  

		
		String time = new SimpleDateFormat("HHmm").format(new Date());
    	Date today=new Date();
	
    	if(WorkDayTools.isHoliday(today)|| WorkDayTools.isWorkEndDay(today))
		{
			System.out.println(time+":date --lead to  StockMarketPriceUpdate Thread no run...");  
    		return;
		}
		
		if(! ( (time.compareTo("0930")>0&&time.compareTo("1130")<0)||(time.compareTo("1300")>0&&time.compareTo("1500")<0)) ){
    		System.out.println(time+": time --lead to  StockMarketPriceUpdate Thread no run...");  
    		return;
    	}
    	//cleanHisData();
	     System.out.println("StockMarketPriceUpdate Thread run..."+time);  
	     try {
      	 
	    	 doRequest_163(grid);
		
	     } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	  // timer.cancel(); //Terminate the timer thread  
           
     }  
	
    
    
    
  
	

    

    
    public void doRequest_163(String stkCodes) throws Exception {
    	
           	       String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        		
            		Time_line stockMarketTimeLine = new Time_line();
        			
                    stockMarketTimeLine.stkCode =stkCodes;
                  	stockMarketTimeLine.stkName =stkCodes;
                  	

                  	try{
                  	 
                     double price =Double.parseDouble(StockMarkets.GetOneHq_field(stkCodes,"price"));
                  	 double volume =Double.parseDouble(StockMarkets.GetOneHq_field(stkCodes,"volume"));
                  	 double turnover =Double.parseDouble(StockMarkets.GetOneHq_field(stkCodes,"turnover"));
                
                  	stockMarketTimeLine.amount =turnover;
                  	stockMarketTimeLine.volume =volume;
                  	stockMarketTimeLine.price =price;
                  	
                  	
                  	stockMarketTimeLine.date =timeStamp.substring(0, 8);
                  	stockMarketTimeLine.time =timeStamp.substring(8, 12);
                  	stockMarketTimeLine.save();
                 
        		    
                  	
        		   }catch(Exception e){
                  	System.out.println("real time error "+stkCodes);
                  	
                  }
                  	
                  	System.out.println(stkCodes+"TimeLine is save");
        	}
  
        	  
                  

     
    
    
//    public static void cleanHisData() {
//    	String dateToClean = DateUtil.calculateDate(null,-2,"yyyyMMdd");
//    	System.out.println("clean date--->"+dateToClean);
//    	if(cleanDate.equals(dateToClean)){
//    		return;
//    	}
//    	try{
//    		
//    		CallableSql	sql = Ebean.createCallableSql("delete from time_line where date < "+dateToClean);
//			int	ret = Ebean.execute(sql);
//				if(ret==0){
//					Logger.info("Delete time_line date success:"+dateToClean);
//				}else{
//					Logger.info("Delete time_line date failed:"+dateToClean);
//				}
//			
//    	}catch (Exception e){
//    		e.printStackTrace();
//    	}
//	}   
    
    
    
    }

