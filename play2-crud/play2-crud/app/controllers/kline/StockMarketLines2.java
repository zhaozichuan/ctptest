package controllers.kline;



import controllers.BaseController;

import play.mvc.Result;
import utils.redis.RedisAPI_K;


public class StockMarketLines2 extends BaseController {

   
	
	
   
	
	
	

	public static Result KLineGo3(String mid,String stkCode,String kType,String callback){
		
//		if("100".equals(kType))
//		  return KLineResourceN100(mid,symbol,kType,lenth, callback);
//		else if("200".equals(kType))
//		  return KLineResource200(mid,symbol,kType,lenth, callback);
//		else if("300".equals(kType))
//			  return KLineResource300(mid,symbol,kType,lenth, callback);
//		 if("200".equals(kType))
//			  return KLineResource200(mid,symbol,kType,lenth, callback);
		
		
		 String keyvalue ="k"+kType+stkCode;
		 String result=RedisAPI_K.get(keyvalue);
	
		 return ok( result);
	}
	
	
public static Result KLineGo2(String mid,String stkCode,String kType,String callback){
		
	 String keyvalue ="k"+kType+"_"+stkCode;
	 String result=RedisAPI_K.get(keyvalue);

	 return ok( result);
	
		// return ok( "0K");
	}
	
	
//	public static Result KLineResource200(String mid,String symbol,String kType,int length,String callback)
//	{	
//		
//		
//	 
//	}

	
	
	

		 

	}
	
	
	
	

