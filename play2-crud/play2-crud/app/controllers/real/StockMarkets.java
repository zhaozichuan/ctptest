package controllers.real;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.typesafe.plugin.RedisPlugin;

import controllers.BaseController;
import models.*;
import play.mvc.Result;
import utils.HqTransfor;
import utils.redis.RedisAPI;
import utils.tools.MathTools;
import utils.tools.WorkDayTools;
import play.libs.Json;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;



public class StockMarkets extends BaseController {
	
		
	public static class StockInfo{

		public String stockName;
		public String stockCode;
		public double realtimePrice;
		public String increase;
		public String  orderPrice;
		public String upLimit;
		public String downLimit;

		public String bp1;
		public String bp2;
		public String bp3;
		public String bp4;
		public String bp5;
		public String bn1;
		public String bn2;
		public String bn3;
		public String bn4;
		public String bn5;
		public String sp1;
		public String sp2;
		public String sp3;
		public String sp4;
		public String sp5;
		public String sn1;
		public String sn2;
		public String sn3;
		public String sn4;
		public String sn5;
	}
	
	public static class Chartdata{
		public String t="930" ;
		public double c=0 ;
		public double v=0 ;
		public double a=0 ;
		public double j1=0 ;
		public double j2=0 ;
		public double j3=0;
		public double j4=0 ;
		
	}
	
	
	
	public static class RealChart{
		public String s;
		public String n ;
		public double p ;
		public List<Chartdata> data =new ArrayList<Chartdata>();
		
		
		
	}
	
	
	

    //String stockCode1=StockMarkets.StockTransfer(stockCode); 
    //先用此函数转换代码  再调GetOneHq;
    public static HqTransfor.HQ163 GetOneHq(String stkcode) {
    	//stkcode="600200";
    	String stockCode1=StockTransfer(stkcode);
 	   
 	   try {
 		 // HqTransfor.HQ163 acc = (HqTransfor.HQ163)mapper.readValue(res,HqTransfor.HQ163.class );
 		 //  System.out.println(":"+acc.price);
 		  HqTransfor.HQ163 acc =new HqTransfor.HQ163();
 		acc.code=stockCode1;
 		acc.percent=Double.parseDouble(RedisAPI.hget(stockCode1,"ask1")==null?"0":RedisAPI.hget(stockCode1,"ask1"));
 		acc.ask1=Double.parseDouble(RedisAPI.hget(stockCode1,"ask1")==null?"0":RedisAPI.hget(stockCode1,"ask1"));
  		acc.ask2 =Double.parseDouble(RedisAPI.hget(stockCode1,"ask2")==null?"0":RedisAPI.hget(stockCode1,"ask2"));
  		acc.ask3 =Double.parseDouble(RedisAPI.hget(stockCode1,"ask3")==null?"0":RedisAPI.hget(stockCode1,"ask3"));
  		acc.ask4 =Double.parseDouble(RedisAPI.hget(stockCode1,"ask4")==null?"0":RedisAPI.hget(stockCode1,"ask4"));
  		acc.ask5 =Double.parseDouble(RedisAPI.hget(stockCode1,"ask5")==null?"0":RedisAPI.hget(stockCode1,"ask5"));
  		acc.ask6 =Double.parseDouble(RedisAPI.hget(stockCode1,"ask6")==null?"0":RedisAPI.hget(stockCode1,"ask6"));
  		acc.ask7 =Double.parseDouble(RedisAPI.hget(stockCode1,"ask7")==null?"0":RedisAPI.hget(stockCode1,"ask7"));
  		acc.ask8 =Double.parseDouble(RedisAPI.hget(stockCode1,"ask8")==null?"0":RedisAPI.hget(stockCode1,"ask8"));
  		acc.ask9 =Double.parseDouble(RedisAPI.hget(stockCode1,"ask9")==null?"0":RedisAPI.hget(stockCode1,"ask9"));
  		acc.ask10=Double.parseDouble(RedisAPI.hget(stockCode1,"ask10")==null?"0":RedisAPI.hget(stockCode1,"ask10"));
  		
  		acc.askvol1  =Long.parseLong(RedisAPI.hget(stockCode1,"askvol1")==null?"0":RedisAPI.hget(stockCode1,"askvol1"));  
  		acc.askvol2  =Long.parseLong(RedisAPI.hget(stockCode1,"askvol2")==null?"0":RedisAPI.hget(stockCode1,"askvol2"));  
  		acc.askvol3  =Long.parseLong(RedisAPI.hget(stockCode1,"askvol3")==null?"0":RedisAPI.hget(stockCode1,"askvol3"));  
  		acc.askvol4  =Long.parseLong(RedisAPI.hget(stockCode1,"askvol4")==null?"0":RedisAPI.hget(stockCode1,"askvol4"));  
  		acc.askvol5  =Long.parseLong(RedisAPI.hget(stockCode1,"askvol5")==null?"0":RedisAPI.hget(stockCode1,"askvol5"));  
  		acc.askvol6  =Long.parseLong(RedisAPI.hget(stockCode1,"askvol6")==null?"0":RedisAPI.hget(stockCode1,"askvol6"));  
  		acc.askvol7  =Long.parseLong(RedisAPI.hget(stockCode1,"askvol7")==null?"0":RedisAPI.hget(stockCode1,"askvol7"));  
  		acc.askvol8  =Long.parseLong(RedisAPI.hget(stockCode1,"askvol8")==null?"0":RedisAPI.hget(stockCode1,"askvol8"));  
  		acc.askvol9  =Long.parseLong(RedisAPI.hget(stockCode1,"askvol9")==null?"0":RedisAPI.hget(stockCode1,"askvol9"));  
  		acc.askvol10 =Long.parseLong(RedisAPI.hget(stockCode1,"askvol10")==null?"0":RedisAPI.hget(stockCode1,"askvol10"));

  		acc.bidvol1  =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol1")==null?"0":RedisAPI.hget(stockCode1,"bidvol1"));
  		acc.bidvol2  =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol2")==null?"0":RedisAPI.hget(stockCode1,"bidvol2"));
  		acc.bidvol3  =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol3")==null?"0":RedisAPI.hget(stockCode1,"bidvol3"));
  		acc.bidvol4  =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol4")==null?"0":RedisAPI.hget(stockCode1,"bidvol4"));
  		acc.bidvol5  =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol5")==null?"0":RedisAPI.hget(stockCode1,"bidvol5"));
  		acc.bidvol6  =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol6")==null?"0":RedisAPI.hget(stockCode1,"bidvol6"));
  		acc.bidvol7  =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol7")==null?"0":RedisAPI.hget(stockCode1,"bidvol7"));
  		acc.bidvol8  =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol8")==null?"0":RedisAPI.hget(stockCode1,"bidvol8"));
  		acc.bidvol9  =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol9")==null?"0":RedisAPI.hget(stockCode1,"bidvol9"));
  		acc.bidvol10 =Long.parseLong(RedisAPI.hget(stockCode1,"bidvol10")==null?"0":RedisAPI.hget(stockCode1,"bidvol10"));
  		 
  		acc.bid1 =Double.parseDouble(RedisAPI.hget(stockCode1,"bid1")==null?"0":RedisAPI.hget(stockCode1,"bid1"));
  		acc.bid2 =Double.parseDouble(RedisAPI.hget(stockCode1,"bid2")==null?"0":RedisAPI.hget(stockCode1,"bid2"));  
  		acc.bid3 =Double.parseDouble(RedisAPI.hget(stockCode1,"bid3")==null?"0":RedisAPI.hget(stockCode1,"bid3"));
  		acc.bid4 =Double.parseDouble(RedisAPI.hget(stockCode1,"bid4")==null?"0":RedisAPI.hget(stockCode1,"bid4"));
  		acc.bid5 =Double.parseDouble(RedisAPI.hget(stockCode1,"bid5")==null?"0":RedisAPI.hget(stockCode1,"bid5"));
  		acc.bid6 =Double.parseDouble(RedisAPI.hget(stockCode1,"bid6")==null?"0":RedisAPI.hget(stockCode1,"bid6"));
  		acc.bid7 =Double.parseDouble(RedisAPI.hget(stockCode1,"bid7")==null?"0":RedisAPI.hget(stockCode1,"bid7"));
  		acc.bid8 =Double.parseDouble(RedisAPI.hget(stockCode1,"bid8")==null?"0":RedisAPI.hget(stockCode1,"bid8"));
  		acc.bid9 =Double.parseDouble(RedisAPI.hget(stockCode1,"bid9")==null?"0":RedisAPI.hget(stockCode1,"bid9"));
  		acc.bid10=Double.parseDouble(RedisAPI.hget(stockCode1,"bid10")==null?"0":RedisAPI.hget(stockCode1,"bid10"));
 		  
  		acc.Close =Double.parseDouble(RedisAPI.hget(stockCode1,"Close")==null?"0":RedisAPI.hget(stockCode1,"Close"));
  		acc.yestclose =Double.parseDouble(RedisAPI.hget(stockCode1,"yestclose")==null?"0":RedisAPI.hget(stockCode1,"yestclose"));
  		acc.open =Double.parseDouble(RedisAPI.hget(stockCode1,"open")==null?"0":RedisAPI.hget(stockCode1,"open"));
  		acc.price =Double.parseDouble(RedisAPI.hget(stockCode1,"price")==null?"0":RedisAPI.hget(stockCode1,"price"));
  		acc.high =Double.parseDouble(RedisAPI.hget(stockCode1,"high")==null?"0":RedisAPI.hget(stockCode1,"high"));
  		acc.low =Double.parseDouble(RedisAPI.hget(stockCode1,"low")==null?"0":RedisAPI.hget(stockCode1,"low"));
  		acc.volume =Long.parseLong(RedisAPI.hget(stockCode1,"volume")==null?"0":RedisAPI.hget(stockCode1,"volume"));
  		//acc.turnover =Double.parseDouble(RedisAPI.hget(stockCode1,"turnover")==null?"0":RedisAPI.hget(stockCode1,"turnover"));
 		
  		
  	  try{
          String price=RedisAPI.hget(stockCode1,"price")==null?"0":RedisAPI.hget(stockCode1,"price"); 
          String yestclose=RedisAPI.hget(stockCode1, "yestclose")==null?"0":RedisAPI.hget(stockCode1,"yestclose"); 
          
          
          acc.updown  = MathTools.sub(Double.parseDouble(price) , Double.parseDouble(yestclose));
          acc.percent = MathTools.div(acc.updown,Double.parseDouble(yestclose)) ;  
          
          //System.out.println("zzc"+h163.ask4);
         
          }catch(Exception e){
          	System.err.println("代码没有"+acc.code);
          	acc.percent =0;
          	acc.updown  =0;
          //	System.out.println(e);
          	
          }
  		
  		
  		return acc;
 		 //  System.out.println("result"+acc.price);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	   
 	   
 	   return null;
    }
    
    
    public static Hashtable GetOneHq_KLine(String stkcode,String field) {
    	
       String stockCode1=StockTransfer(stkcode);
       Hashtable tmptable=new Hashtable();
 	   try{
       
 	  		double Close =Double.parseDouble(RedisAPI.hget(stockCode1,"Close")==null?"0":RedisAPI.hget(stockCode1,"Close"));
 	  		double yestclose =Double.parseDouble(RedisAPI.hget(stockCode1,"yestclose")==null?"0":RedisAPI.hget(stockCode1,"yestclose"));
 	  		double open =Double.parseDouble(RedisAPI.hget(stockCode1,"open")==null?"0":RedisAPI.hget(stockCode1,"open"));
 	  		double price =Double.parseDouble(RedisAPI.hget(stockCode1,"price")==null?"0":RedisAPI.hget(stockCode1,"price"));
 	  		double high =Double.parseDouble(RedisAPI.hget(stockCode1,"high")==null?"0":RedisAPI.hget(stockCode1,"high"));
 	  		double low =Double.parseDouble(RedisAPI.hget(stockCode1,"low")==null?"0":RedisAPI.hget(stockCode1,"low"));
 	  		double volume =Long.parseLong(RedisAPI.hget(stockCode1,"volume")==null?"0":RedisAPI.hget(stockCode1,"volume"));
 	  		double turnover =Double.parseDouble(RedisAPI.hget(stockCode1,"turnover")==null?"0":RedisAPI.hget(stockCode1,"turnover"));
          
 	  		//tmptable.put("Close", value) 
 	   
 	  		
 	     }catch(Exception e){
          
        	  System.err.println("代码没有"+stkcode);
          	
          }
  		
  		
 	      return tmptable;
		
 	   
    }
    

    
    public static String GetOneHq_field(String stkcode,String field) {
    	
        String stockCode1=StockTransfer(stkcode);
  	   
  	   try{
        
           String tmpfield=RedisAPI.hget(stockCode1, field)==null?"0":RedisAPI.hget(stockCode1,field); 
          
           return tmpfield;
           
  	   
  	     }catch(Exception e){
           
         	  System.err.println("代码没有"+stkcode);
           	
           }
   		
   		
  	      return  "0";
 		
  	   
     }

   
    
    
    
    
    public static String StockTransfer_163(String stockCode) {
    	
    	if(stockCode.length()==7)
    		return stockCode;
    	String stockCode1=stockCode;
		if(stockCode.startsWith("60"))
			stockCode1="0"+stockCode;
		else if(stockCode.startsWith("00")||stockCode.startsWith("30")||stockCode.startsWith("39")){
			stockCode1="1"+stockCode;
		}
	
		 if("2A01".equals(stockCode))
				stockCode1="1399001";
		 else if("1A0001".equals(stockCode)){
				 stockCode1="0000001";
		 }
 	   
 	   return stockCode1;
    }
    
    
public static String StockTransfer(String stockCode) {
    	
    	if(stockCode.length()==9)
    		return stockCode;
    	String stockCode1=stockCode;
    	
    		
    	if(stockCode.startsWith("60")||stockCode.startsWith("J50")||stockCode.startsWith("Z00"))
			stockCode1=stockCode+".SH";
		else if(stockCode.startsWith("J15")||stockCode.startsWith("30")||stockCode.startsWith("Z39")||stockCode.startsWith("00")){
			stockCode1=stockCode+".SZ";
		}
    	
    	if(stockCode.startsWith("Z")||stockCode.startsWith("J")||stockCode.startsWith("G"))
    		stockCode1=stockCode1.substring(1);
	
//		 if("2A01".equals(stockCode))
//				stockCode1="1399001";
//		 else if("1A0001".equals(stockCode)){
//				 stockCode1="0000001";
//		 }
 	   
 	   return stockCode1;
    }
    
    public static double stock_Price_tenPrice_redis(String stockCode,double tradetype ) {
    	
    	
        try {
        	
        	HqTransfor.HQ163 hq163=GetOneHq(stockCode);
        
    	      if(hq163.bid1==0.0&&hq163.ask1==0.0)
    	        return -1;
    	      
    	      if(tradetype<0&&hq163.bid1==0.0)//卖出，买一无值，不成交
    	       return   0.0;
    	      
    	      if(tradetype>0&&hq163.ask1==0.0)//买入，卖出无值，不成交
       	       return   0.0;
//       	  
     	  return hq163.price;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 
			e.printStackTrace();
			System.out.println("stock_realPrice---"+e);
			return  0.0;
		}
    
    
}
    
    
    
    
    public static double stock_Price_redis(String stockCode ) {
    	
 	
     try {
     	
     	HqTransfor.HQ163 hq163=GetOneHq(stockCode);
     	
  	    return hq163.price;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 
			e.printStackTrace();
			System.out.println("stock_realPrice---"+e);
			return  0.0;
		}
 
 
}
 
    
    
     
 public static Result stock_HQ_CharV2(String stockCode,String callback) {
	      ResultRtn resultRtn = new ResultRtn();
		  Status status = new Status();
	 
    	  java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
 		
    	  java.text.SimpleDateFormat sdate=new java.text.SimpleDateFormat ("YYYYMMddhhmmss");
    	  java.text.SimpleDateFormat sdate1=new java.text.SimpleDateFormat ("YYYY-MM-dd");
    	  
    	  Date tt=new Date();
    	  System.out.println("Date:"+ tt);
    	  String dateStr=sdate.format(tt);
    	  
          String dataStr1=sdate1.format(tt);
          System.out.println("dateStr"+dateStr);
          
    	  try {

    	   String stockCode1=StockTransfer(stockCode);
    	   String stockName ="";
    	   List <Stock> t1=Stock.find.where().eq("code", stockCode).findList();
           if(t1.size()>0){
        	   stockName=  t1.get(0).name;	 
           }
	      
       	   String yesday=GetOneHq_field(stockCode,"yestclose");

	       System.out.println("stockcode:"+stockCode);
	       String tmpdate3 = dataStr1;
			
			// 判断是否取前一个工作日
			List<Time_line2> list =null;
			Calendar cal = Calendar.getInstance();
  	    //  if(list.isEmpty()||list==null){
  	      String time = new SimpleDateFormat("HHmm").format(new Date());
  	     
          if(WorkDayTools.isHoliday(tt))
  	      {
  	    	dateStr=WorkDayTools.getBeforeTradeDay(tt);
//  	    	 list =   Time_line3.find.where().eq("stk_code", stockCode1)
//                     .eq("date",dateStr.substring(0, 4)+dateStr.substring(5, 7)+dateStr.substring(8, 10))
//                     .findList();
  	    	 tmpdate3=dateStr.substring(0, 4)+dateStr.substring(5, 7)+dateStr.substring(8, 10);
  	    	list =   Time_line2.find.where().eq("stk_code", stockCode)
                    .eq("date",tmpdate3)
                    .findList();
  	      
  	      
  	      }else if(time.compareTo("0930")<0||WorkDayTools.isWorkEndDay(tt)){

  	  		  
  	  		
	  	      cal.add(Calendar.DAY_OF_MONTH, -1);
		      tt= cal.getTime();
  	  		  
  	  	      if(WorkDayTools.isHoliday(tt)){
  	  	    	tmpdate3=WorkDayTools.getBeforeTradeDay(tt);
  	  	    	tmpdate3=tmpdate3.substring(0,4)+tmpdate3.substring(5,7)+tmpdate3.substring(8,10);
  	  	        //tmpdate3=new SimpleDateFormat("yyyyMMdd").format(tt);
  	  	      }else{
		      
	  	  	      while(WorkDayTools.isWorkEndDay(tt)||WorkDayTools.isHoliday(tt)){
	  	  	    		  cal.add(Calendar.DAY_OF_MONTH, -1);
	  	  	    	      tt= cal.getTime();
	  	  	    	  }
	  	  	    		
	  	  	     	  //  tmpdate3= WorkDayTools.calculateDate(dataStr1,-1,"yyyy-MM-dd");
  	  			    
  	  	    	  tmpdate3=new SimpleDateFormat("yyyyMMdd").format(tt);
  	  	    	  //cal.
  	  	      }
  	  	    	  list=Time_line2.find.where()
  	  	    			  	.eq("stk_code", stockCode)
  	                        .eq( "date", tmpdate3 )
  	                        .findList();
  	  	 //   	  System.out.println("dateStr1->"+stockCode+tmpdate3+tmpdate3.substring(4, 8));
//  	   	    	  dateStr =  tmpdate3;
  	  	    	System.out.println("final:"+tmpdate3); 
  	  	      
  	      }else{
  	      
  	      
  	      ///////////////////////////////////////////////////////////////////////////
  	
			
				list = Time_line2.find.where()
						.eq("stk_code", stockCode)
						.eq("date", dateStr.substring(0, 8))
						.findList();
    	      System.out.println("final:"+dateStr);
  	      
  	      }   
    	
  	     //temp add 
  	    System.out.println("stockcode11:"+stockCode);
  	 
	     
//	      list =   Time_line2.find.where().eq("stk_code", stockCode)
//          .eq("date","20160106")
//          .findList();
    	      
    	      
    	      StringBuffer returnStr =new StringBuffer();
//    	      if(list.isEmpty()||list==null){
//    	    	  return ok("stock timeline is not");
//    	      }
    	      

    			
    	          RealChart real =new RealChart();
    	          real.s =stockCode;
    	          real.p =Double.parseDouble(yesday);
    	          real.n= stockName;
    	          
    	          if(real.p==0)
    	        	  real.p=10;
    	          Chartdata chartdata0 =new Chartdata();   
    	        
    	        
//            if(list.isEmpty()||list==null){
//    	        	
//    	            StringBuffer sb=new StringBuffer("ok({\"s\":\"");
//    	            		sb.append( real.s);
//    	            		sb.append( "\",\"n\":\"");
//    	            		sb.append( real.n);
//    	            		sb.append( "\",\"p\":");
//    	            		sb.append( real.p);
//    	            		sb.append( ",");
//    	        	sb.append("\"data\":[{\"t\":\"0930\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0931\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0932\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0933\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0934\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0935\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0936\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0937\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0938\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0939\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0940\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0941\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0942\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0943\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0944\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0945\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0946\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0947\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0948\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0949\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"0950\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0951\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0952\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0953\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0954\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0955\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0956\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0957\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0958\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"0959\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1000\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1001\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1002\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1003\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1004\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1005\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1006\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1007\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1008\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1009\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1010\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1011\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1012\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1013\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1014\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1015\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1016\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1017\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1018\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1019\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1020\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1021\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1022\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1023\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1024\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1025\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1026\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1027\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1028\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1029\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1030\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1031\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1032\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1033\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1034\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1035\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1036\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1037\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1038\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1039\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1040\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1041\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1042\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1043\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1044\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1045\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1046\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1047\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1048\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1049\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1050\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1051\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1052\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1053\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1054\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1055\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1056\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1057\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1058\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1059\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1100\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1101\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1102\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1103\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1104\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1105\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1106\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1107\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1108\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1109\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1110\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1111\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1112\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1113\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1114\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1115\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1116\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1117\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1118\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1119\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1120\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1121\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1122\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1123\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1124\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1125\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1126\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1127\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1128\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1129\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1130\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	
//    	        	sb.append(",{\"t\":\"1300\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1301\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1302\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1303\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1304\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1305\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1306\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1307\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1308\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1309\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1310\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1311\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1312\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1313\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1314\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1315\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1316\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1317\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1318\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1319\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1320\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1321\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1322\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1323\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1324\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1325\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1326\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1327\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1328\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1329\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1330\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1331\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1332\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1333\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1334\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1335\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1336\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1337\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1338\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1339\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1340\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1341\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1342\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1343\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1344\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1345\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1346\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1347\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1348\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1349\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1350\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1351\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1352\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1353\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1354\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1355\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1356\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1357\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1358\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1359\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1400\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1401\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1402\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1403\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1404\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1405\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1406\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1407\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1408\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1409\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1410\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1411\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1412\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1413\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1414\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1415\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1416\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1417\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1418\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1419\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1420\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1421\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1422\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1423\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1424\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1425\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1426\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1427\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1428\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1429\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1430\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1431\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1432\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1433\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1434\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1435\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1436\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1437\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1438\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1439\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1440\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1441\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1442\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1443\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1444\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1445\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1446\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1447\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1448\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1449\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1450\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1451\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1452\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1453\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1454\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1455\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1456\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1457\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1458\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0},{\"t\":\"1459\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}");
//    	        	sb.append( ",{\"t\":\"1500\",\"c\":0.0,\"v\":0.0,\"a\":0.0,\"j1\":0.0,\"j2\":0.0,\"j3\":0.0,\"j4\":0.0}]})");
//    	        	
//      	    	  return ok(sb.toString());
//      	      }
    	        
    	        
    	        

				
    	        chartdata0.t="0929";
				chartdata0.c =0;
  				chartdata0.v =0;
  				chartdata0.a =0;

				chartdata0.j1 =0;
				chartdata0.j2 =0;
				chartdata0.j3 =0;
				chartdata0.j4 =0;

  				
  				real.data.add(chartdata0); 
    	          
  				if(list==null||list.isEmpty())
  				return ok(callback+"("+Json.toJson(real).toString().replaceAll("null", "\"\"")+")");
  				
  				
  				
  				
    	        chartdata0.t =list.get(0).time;
  				chartdata0.c =list.get(0).price;
  				chartdata0.v =new BigDecimal(list.get(0).volume).doubleValue();
  				chartdata0.a =new BigDecimal(list.get(0).amount).doubleValue();

				chartdata0.j1 =new BigDecimal(list.get(0).money1).doubleValue();
				chartdata0.j2 =new BigDecimal(list.get(0).money2).doubleValue();
				chartdata0.j3 =new BigDecimal(list.get(0).money3).doubleValue();
				chartdata0.j4 =new BigDecimal(list.get(0).money4).doubleValue();
  				
  				
    	      
      			for(int i=1; i<list.size(); i++ )
      			{
      				//List<data> dataList =new ArrayList<data>()
      				Chartdata chartdata =new Chartdata();
      				
      				//System.out.println("-----===="+list.get(i).date);
      				chartdata.t =list.get(i).time;
      				chartdata.c =list.get(i).price;
      				chartdata.v =new BigDecimal(list.get(i).volume).doubleValue()-new BigDecimal(list.get(i-1).volume).doubleValue();
      				chartdata.a =new BigDecimal(list.get(i).amount).doubleValue()-new BigDecimal(list.get(i-1).amount).doubleValue();
      				//chartdata.a =list.get(i).amount;
//保留       				
//      				chartdata.j1 =new BigDecimal(list.get(i).money1).doubleValue()-new BigDecimal(list.get(i-1).money1).doubleValue();
//      				chartdata.j2 =new BigDecimal(list.get(i).money2).doubleValue()-new BigDecimal(list.get(i-1).money2).doubleValue();
//      				chartdata.j3 =new BigDecimal(list.get(i).money3).doubleValue()-new BigDecimal(list.get(i-1).money3).doubleValue();
//      				chartdata.j4 =new BigDecimal(list.get(i).money4).doubleValue()-new BigDecimal(list.get(i-1).money4).doubleValue();
//      				

  				chartdata.j1 =new BigDecimal(list.get(i).money1).doubleValue();
  				chartdata.j2 =new BigDecimal(list.get(i).money2).doubleValue();
  				chartdata.j3 =new BigDecimal(list.get(i).money3).doubleValue();
  				chartdata.j4 =new BigDecimal(list.get(i).money4).doubleValue();
  				
  				
////test				
//  				chartdata.t =list.get(i).time;
//  				chartdata.c =0;
//  				chartdata.v =0.00;
//  				chartdata.a =0.00;
//
//				chartdata.j1 =0.00;
//				chartdata.j2 =0.00;
//				chartdata.j3 =0.00;
//				chartdata.j4 =0.00;
////test end
      				
      				real.data.add(chartdata);
      				
      			}
      			
      			//resultRtn.business.put();
    			//returnStr.append( returnStr.substring(0,returnStr.length())+"]})");
      			return ok(callback+"("+Json.toJson(real).toString().replaceAll("null", "\"\"")+")");
    	  } catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return ok("error");
    	  }    
			  
        
        
    }
 
 
 
 public static Result stock_HQ_Char(String stockCode,float r,String callback) {
	 
 	String data="action=60&stockindex=1&MaxCount=1&Grid="+stockCode;
 	String responseStr;
 	Time_line  sTime =new Time_line();
 	 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
		
 	  java.text.SimpleDateFormat sdate=new java.text.SimpleDateFormat ("YYYYMMddhhmmss");
 	  java.text.SimpleDateFormat sdate1=new java.text.SimpleDateFormat ("YYYY-MM-dd");
 	 // Date tt=new java.util.Date(); 
// 	 String testD="2015-08-30";
	  Date tt=new Date();
//		try {
//			tt = sdate1.parse(testD);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} 
 	  System.out.println("Date:"+ tt);
 	  String dateStr=sdate.format(tt);
 	  //dateStr ="201509020000";
 	  
       String dataStr1=sdate1.format(tt);
       System.out.println("dateStr"+dateStr);
       
 	  try {
 		  
// 		  if("0.2A01".equals(stockCode))
// 		     data="action=60&stockindex=1&MaxCount=1&Grid="+"2A01";
        
		
 		  
 		  
//			responseStr=httpGetData.getHttpDate(data, "http://42.121.107.194:7780/reqxml");
		
//		  System.out.println("responseStr"+responseStr);
//		  int beg=responseStr.indexOf('[');
//       int end=responseStr.indexOf(']');  
//      
//	      String HqStr=  responseStr.substring(beg, end); 
//	      String[] items = HqStr.split("\\|");
//	       sTime.stkCode= stockCode ;
//	       String yesday=items[24];//昨收
 	
				//return ok(returnStr.toString());
      //163get
 		  String stockCode1=stockCode;
 		  if ("2A01".equals(stockCode))
				  stockCode1 = "399001";  
 		
 		   
 		  if(stockCode1.startsWith("60"))
 			  stockCode1="0"+stockCode1;
 		  else 
 			  stockCode1="1"+stockCode1;
 		  
 		  
 		  if ("1A0001".equals(stockCode))
 			  stockCode1 = "0000001"; 
	    //   responseStr=httpGetData.getHttpDate(data, "http://quotes.money.163.com/hs/service/diyrank.php?query=SYMBOL:"+stockCode1+";STYPE:EQA&fields=YESTCLOSE");
	      
// 		  responseStr=httpGetData.getHttpDate(data,  "http://api.money.126.net/data/feed/"+stockCode1+"?callback=ok" ); 
//	       int index_close=responseStr.indexOf("yestclose");
//	      String tmpstr= responseStr.substring(index_close);
//	      int index_dou= tmpstr.indexOf(',');
	      
	      
	       //String[] items = HqStr.split("\\,");
	       String yesday="0";//tmpstr.substring(12,index_dou);

	       System.out.println("stockcode:"+stockCode);
	       

 		
 		  
// 		  ObjectMapper mapper = new ObjectMapper(); 
// 		  JsonNode node = mapper.readTree(j.get(stockCode));
// 		  String yesday=node.get("lastClose").asText();
 		  
 		  
 		  
				if ("2A01".equals(stockCode))
				stockCode = "399001";
			
			
			// 判断是否取前一个工作日
			List<Time_line> list =null;
			Calendar cal = Calendar.getInstance();
	    //  if(list.isEmpty()||list==null){
	      String time = new SimpleDateFormat("HHmm").format(new Date());
	     
       if(WorkDayTools.isHoliday(tt))
	      {
	    	dateStr=WorkDayTools.getBeforeTradeDay(tt);
	    	 list =   Time_line.find.where().eq("stk_code", stockCode)
                  .eq("date",dateStr.substring(0, 4)+dateStr.substring(5, 7)+dateStr.substring(8, 10))
                  .findList();
	      }else if(time.compareTo("0930")<0||WorkDayTools.isWorkEndDay(tt)){

	  		  
	  		  String tmpdate3 = dataStr1;
	  	      cal.add(Calendar.DAY_OF_MONTH, -1);
		      tt= cal.getTime();

	  		  
	  	      if(WorkDayTools.isHoliday(tt)){
	  	    	tmpdate3=WorkDayTools.getBeforeTradeDay(tt);
	  	    tmpdate3=tmpdate3.substring(0,4)+tmpdate3.substring(5,7)+tmpdate3.substring(8,10);
	  	        //tmpdate3=new SimpleDateFormat("yyyyMMdd").format(tt);
	  	      }else{
		      
	  	  	      while(WorkDayTools.isWorkEndDay(tt)||WorkDayTools.isHoliday(tt)){
	  	  	    		  cal.add(Calendar.DAY_OF_MONTH, -1);
	  	  	    	      tt= cal.getTime();
	  	  	    	  }
	  	  	    		
	  	  	     	  //  tmpdate3= WorkDayTools.calculateDate(dataStr1,-1,"yyyy-MM-dd");
	  			    
	  	    	  tmpdate3=new SimpleDateFormat("yyyyMMdd").format(tt);
	  	    	  //cal.
	  	      }
	  	    	  list=Time_line.find.where().eq("stk_code", stockCode)
	                        .eq( "date", tmpdate3 )
	                        .findList();
	  	 //   	  System.out.println("dateStr1->"+stockCode+tmpdate3+tmpdate3.substring(4, 8));
//	   	    	  dateStr =  tmpdate3;
	  	    	System.out.println("final:"+tmpdate3); 
	  	      
	      }else{
	      
	      
	      ///////////////////////////////////////////////////////////////////////////
	
			
	       
 	      list =   Time_line.find.where().eq("stk_code", stockCode)
 	    		                                               .eq("date",dateStr.substring(0, 8))
 	    		                                               .findList();
 	      System.out.println("final:"+dateStr);
	      
	      }   
 	
	     //temp add 
	    System.out.println("stockcode11:"+stockCode);
	 
	     
//	      list =   StockMarketTimeLine.find.where().eq("stk_code", stockCode)
//       .eq("date","20150902")
//       .findList();
// 	      
 	      
 	      StringBuffer returnStr =new StringBuffer();
 	      if(list.isEmpty()||list==null){
 	    	  return ok("stock timeline is not");
// 	    	  return ok(tt.toString()+dateStr);
 	      }
 	      
 	        returnStr.append(callback+"({\"s\":\"")
 			.append(stockCode).append("\",\"n\":\"")
 			.append(list.get(0).stkName).append("\",\"p\":")
 			.append(yesday).append(",\"data\":[");
 			
 			
 			for(int i=0; i<list.size(); i++ )
 			{
 				double volume1=0.00;
 			    double amount1=0.00;
 				if(i>0){ 
 					
 					if(list.get(i).volume-8*list.get(i-1).volume>0){
 						volume1=0;
 					}else{
 					 volume1=(list.get(i).volume-list.get(i-1).volume);
 					 amount1=list.get(i).amount-list.get(i-1).amount;
 					 
 					 if(volume1<0)
 						 volume1=0;
 					
 					 if(amount1<0)
 						 amount1=0;
 					}
 					//if(volume1>)
 					 
 					 
 				}else{
 					 volume1=(list.get(0).volume);
 					 amount1=list.get(0).amount;
 					
 				}
 					returnStr.append("{\"").append("t").append("\":").append(list.get(i).time).append(",")
 				.append("\"c\":").append(df.format(list.get(i).price)).append(",")
 				.append("\"v\":").append(String.valueOf(df.format(volume1))).append(",")
 				.append("\"a\":").append(String.valueOf(df.format(amount1))).append("},")
 				     ;
 			}
 			
 			//returnStr.append( returnStr.substring(0,returnStr.length())+"]})");
 			 return ok(returnStr.substring(0,returnStr.length())+"]})");
 	  } catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return ok("error");
 	  }    
			  
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("stock_realPrice---"+e);
//		}
//		return null;
     
     
 }
 
 
 public static HqTransfor.HQ163_base GetOneHq_163_base(String stkcode) {
	 ObjectMapper mapper = new ObjectMapper();
	 
	    String stockCode1=StockTransfer(stkcode);
	   
	   stkcode="163_base_"+stockCode1;
	   String res=RedisAPI.get(stockCode1);
	   //String res=j.get(stkcode);
	   if(res==null)
		   return null;
	   try {
		   HqTransfor.HQ163_base acc = (HqTransfor.HQ163_base)mapper.readValue(res,HqTransfor.HQ163_base.class );
		//   System.out.println(":"+acc.HIGH);
		   return acc;
		 //  System.out.println("result"+acc.price);
	  } catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   return null;
 }
       
    
   
}
