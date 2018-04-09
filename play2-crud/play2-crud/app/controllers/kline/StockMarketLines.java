package controllers.kline;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import controllers.BaseController;
import controllers.BaseController.Status;
import controllers.real.StockMarkets;
import models.DailyStockMarket;
import models.ResultRtn;
import models.Stock;
import models.Time_line2;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.HqTransfor;
import utils.redis.RedisAPI_K;
import utils.tools.Dbmange;
import utils.tools.MathTools;
import utils.tools.WorkDayTools;

public class StockMarketLines extends BaseController {

	
	
    public static Result refresh(){
    	ResultRtn resultRtn = new ResultRtn();
    	//Logger.info("log test");
    	/*
    	List<Stock> stocks = Stock.find.where().like("code", "600%").setMaxRows(10).findList();
    	String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		System.out.println("StockMarketKLineUpdateActor begin:"+new SimpleDateFormat("yyyyMMddHHmmss-SSS").format(new Date()));
    	for(Stock each:stocks){
    		StockMarketKLineUpdateActor.updateDay(each.code,each.name,date);
    		StockMarketKLineUpdateActor.updateWeek(each.code,each.name,date);
    		StockMarketKLineUpdateActor.updateMonth(each.code,each.name,date);
    	}
		System.out.println("StockMarketKLineUpdateActor end:"+new SimpleDateFormat("yyyyMMddHHmmss-SSS").format(new Date()));
		//*/
    	resultRtn.msg = "refresh done";
		return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
    }
	
	
	
//tmp test zzc add	
	
	
//	public static Result KLineResource2(String mid,String symbol,String kType,int lenth)
//	{
//		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
//		ResultRtn resultRtn = new ResultRtn();
//		try {
//			String sqlstr="select symbol,name,time,AVG(open),AVG(high),AVG(low),AVG(close),sum(volume),sum(amount) from daily_k group by week(time)";
//			List<SqlRow> list = Ebean.createSqlQuery(sqlstr).findList();
//		//	resultRtn.business.put("symbol", symbol);
//		//	resultRtn.business.put("name", list.get(0).get("name"));
//		//	resultRtn.business.put("data", list)
//			StringBuffer returnStr =new StringBuffer();
//			returnStr.append(symbol).append("\n")
//			.append(list.get(0).get("name")).append("\n")
//			
//			;
//			for(int i=0; i<list.size(); i++ )
//			{
//				
//				returnStr.append(list.get(i).get("time")).append(",")
//				         .append(0).append(",")
//				         .append(df.format(list.get(i).get("AVG(open)"))).append(",")
//				          .append(df.format(list.get(i).get("AVG(high)"))).append(",")
//				           .append(df.format(list.get(i).get("AVG(low)"))).append(",")
//				            .append(df.format(list.get(i).get("AVG(close)"))).append(",")
//				            .append(df.format(list.get(i).get("sum(volume)"))).append(",")
//				            .append(df.format(list.get(i).get("sum(volume)"))).append("\n")
//				            ;
//			}
//			
//					return ok(returnStr.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			resultRtn.msg = "server error";
//			resultRtn.errCode = -1;
//			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
//		}
//	}
//	
//	
//	
//public static Result KLineResource005(String mid,String symbol,String kType,int length,String callback)
//	{
//		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.00"); 
//		 SimpleDateFormat formatter;
//		    formatter = new SimpleDateFormat ("yyyyMMdd");
//		    
//		    SimpleDateFormat formatter1;
//		    formatter1 = new SimpleDateFormat ("HHmmss");
//		
//		 if("399001".equals(symbol))
//				symbol="2A01";
//	    
//		    
//		Stock tomstock =Stock.find.where().eq("code", symbol).findUnique(); 
//			if(tomstock==null)
//				return ok("stock is not exist in stock table");
//					 ResultRtn resultRtn = new ResultRtn();
//		
//					 
//					 
//					
//				     
//						if("2A01".equals(symbol))
//							symbol="399001";
//					
//					
//		try {
//			StringBuffer returnStr =new StringBuffer();
//			
//			returnStr.append(callback+"({\"s\":\"")
//			.append(symbol).append("\",\"n\":\"")
//			.append(tomstock.name).append("\",\"data\":[");
//			
//			StringBuffer sqlBuffer=new StringBuffer();
//			sqlBuffer.append("select min(id),max(id),max(t.price),min(t.price),sum(volume),sum(amount),concat( date_format( date, '%Y-%m-%d %H:' ) , floor( date_format( date, '%i' ) /5 ) ) AS c,date from time_line3 t ")
//					 .append("where t.stk_code ='"+ symbol +"' group by c limit 240");
//			//String sqlstr="select max(id),min(id), symbol,name,time,open,MAX(high),MIN(low),close,sum(volume),sum(amount) from daily_k  where symbol='"+symbol +"' group by year(time),week(time)  order by time desc limit "+length;
//			
//			String sqlstr =sqlBuffer.toString();
//			List<SqlRow> list = Ebean.createSqlQuery(sqlstr).findList();
//			
//			
//			if(list.size()==0){
//			    
//				return ok( returnStr+"]})");	
//						
//	     	}
//			
//			for(int i=list.size()-1; i>0; i-- )
//			{
//				
//				
//				//Time_line3 t=Time_line3.find.byId(maxId);
//				
////				if(i==17){
////					//int j=0;
////					System.out.println("---"+i+"---"+list.get(i).get("max(id)"));
////						
////				}
//				//System.out.println("---"+i+"---"+list.get(i).get("max(id)"));
//				//System.out.println("---"+df.format(list.get(i).get("LOWER(low)").toString()));
//				
//				//formatter.format(list.get(i).get("date"));
//				
//				returnStr.append("{\"d\":").append(formatter.format(list.get(i).get("date"))).append(",")
//				.append("\"").append("t").append("\":").append(formatter1.format(list.get(i).get("date"))).append(",") 
//				.append("\"o\":").append(Time_line2.find.byId(Long.parseLong(list.get(i).get("max(id)").toString())).price).append(",")
//				.append("\"h\":").append(df.format(list.get(i).get("max(t.price)"))).append(",")
//				.append("\"l\":").append(df.format(list.get(i).get("min(t.price)"))).append(",")
//				.append("\"c\":").append(Time_line2.find.byId(Long.parseLong(list.get(i).get("min(id)").toString())).price).append(",")
//				.append("\"v\":").append(df.format(list.get(i).get("sum(volume)"))).append(",")
//				.append("\"a\":").append(df.format(list.get(i).get("sum(amount)"))).append("},")
//				;
//				   
//			}
//			
//			//		return ok(returnStr.toString());
//                           return ok( returnStr.substring(0,returnStr.length()-1)+"]})");	
//	} catch (Exception e) {
//			e.printStackTrace();
//			resultRtn.msg = "server error";
//			resultRtn.errCode = -1;
//			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
//		}
//	}
//	
//	
//	
//	
//	
//	public static Result KLineResource200(String mid,String symbol,String kType,int length,String callback)
//	{
//		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.00"); 
//		 SimpleDateFormat formatter;
//		    formatter = new SimpleDateFormat ("yyyyMMdd");
//		
//		 if("399001".equals(symbol))
//				symbol="2A01";
//	    
//		    
//		Stock tomstock =Stock.find.where().eq("code", symbol).findUnique(); 
//			if(tomstock==null)
//				return ok("stock is not exist in stock table");
//					 ResultRtn resultRtn = new ResultRtn();
//		
//					 
//					 
//					
//				     
//						if("2A01".equals(symbol))
//							symbol="399001";
//					
//					
//		try {
//			StringBuffer returnStr =new StringBuffer();
//			
//			returnStr.append(callback+"({\"s\":\"")
//			.append(symbol).append("\",\"n\":\"")
//			.append(tomstock.name).append("\",\"data\":[");
//			
//			String sqlstr="select max(id),min(id), symbol,name,time,open,MAX(high),MIN(low),close,sum(volume),sum(amount) from daily_k  where symbol='"+symbol +"' group by year(time),week(time)  order by time desc limit "+length;
//			List<SqlRow> list = Ebean.createSqlQuery(sqlstr).findList();
//			
//			
//			if(list.size()==0){
//			    
//				return ok( returnStr+"]})");	
//						
//	     	}
//			
//			for(int i=list.size()-1; i>0; i-- )
//			{
//				//System.out.println("---"+df.format(list.get(i).get("LOWER(low)").toString()));
//				returnStr.append("{\"d\":").append(formatter.format(list.get(i).get("time"))).append(",")
//				.append("\"").append("t").append("\":").append("0").append(",") 
//				.append("\"o\":").append(DailyStockMarket.find.byId(Long.parseLong(list.get(i).get("max(id)").toString())).open).append(",")
//				.append("\"h\":").append(df.format(list.get(i).get("MAX(high)"))).append(",")
//				.append("\"l\":").append(df.format(list.get(i).get("MIN(low)"))).append(",")
//				.append("\"c\":").append(DailyStockMarket.find.byId(Long.parseLong(list.get(i).get("min(id)").toString())).close).append(",")
//				.append("\"v\":").append(df.format(list.get(i).get("sum(volume)"))).append(",")
//				.append("\"a\":").append(df.format(list.get(i).get("sum(amount)"))).append("},")
//				;
//				   
//			}
//			
//			//		return ok(returnStr.toString());
//                           return ok( returnStr.substring(0,returnStr.length()-1)+"]})");	
//	} catch (Exception e) {
//			e.printStackTrace();
//			resultRtn.msg = "server error";
//			resultRtn.errCode = -1;
//			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
//		}
//	}	
	
//	public static Result KLineResource300(String mid,String symbol,String kType,int length,String callback)
//	{
//		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
//	
//		 if("399001".equals(symbol))
//				symbol="2A01";
//	
//		 SimpleDateFormat formatter;
//		    formatter = new SimpleDateFormat ("yyyyMMdd");   
//	
//		 ResultRtn resultRtn = new ResultRtn();
//		Stock tomstock =Stock.find.where().eq("code", symbol).findUnique(); 
//		if(tomstock==null)
//			return ok("stock is not exist in stock table");
//		
//     
//		if("2A01".equals(symbol))
//			symbol="399001";
//	
//	
//		
//		
//		
//		try {
//			String sqlstr="select max(id),min(id),symbol,name,time,open,MAX(high),MIN(low),close,sum(volume),sum(amount) from daily_k  where symbol='"+symbol +"'  group by month(time),year(time) order by time  desc limit "+length;
//			List<SqlRow> list = Ebean.createSqlQuery(sqlstr).findList();
//		//	resultRtn.business.put("symbol", symbol);
//		//	resultRtn.business.put("name", list.get(0).get("name"));
//		//	resultRtn.business.put("data", list)
//			StringBuffer returnStr =new StringBuffer();
//		
//			returnStr.append(callback+"({\"s\":\"")
//			.append(symbol).append("\",\"n\":\"")
//			.append(tomstock.name).append("\",\"data\":[");
//			
//            if(list.size()==0){
//			    
//				return ok( returnStr+"]})");	
//						
//	     	}
//			
//			for(int i=list.size()-1; i>0; i-- )
//			{
//				
//				
//				
//				returnStr.append("{\"d\":").append(formatter.format(list.get(i).get("time"))).append(",")
//				.append("\"").append("t").append("\":").append("0").append(",")
//				.append("\"o\":").append(DailyStockMarket.find.byId(Long.parseLong(list.get(i).get("max(id)").toString())).open).append(",")
//				.append("\"h\":").append(df.format(list.get(i).get("MAX(high)"))).append(",")
//				.append("\"l\":").append(df.format(list.get(i).get("MIN(low)"))).append(",")
//				.append("\"c\":").append(DailyStockMarket.find.byId(Long.parseLong(list.get(i).get("min(id)").toString())).close).append(",")
//				.append("\"v\":").append(df.format(list.get(i).get("sum(volume)"))).append(",")
//				.append("\"a\":").append(df.format(list.get(i).get("sum(amount)"))).append("},")
//				;
//				
//			}
//			
//				//	return ok(returnStr.toString());
//                             return ok( returnStr.substring(0,returnStr.length()-1)+"]})");	
//	} catch (Exception e) {
//			e.printStackTrace();
//			resultRtn.msg = "server error";
//			resultRtn.errCode = -1;
//			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
//		}
//	}	
//	
//	
//	public static Result KLineResource3(String mid,String symbol,String kType,int lenth)
//	{
//		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
//		ResultRtn resultRtn = new ResultRtn();
//		try {
//			String sqlstr="select symbol,name,time,AVG(open),AVG(high),AVG(low),AVG(close),sum(volume),sum(amount) from daily_k group by month(time)";
//			List<SqlRow> list = Ebean.createSqlQuery(sqlstr).findList();
//		//	resultRtn.business.put("symbol", symbol);
//		//	resultRtn.business.put("name", list.get(0).get("name"));
//		//	resultRtn.business.put("data", list)
//			StringBuffer returnStr =new StringBuffer();
//			returnStr.append(symbol).append("\n")
//			.append(list.get(0).get("name")).append("\n")
//			
//			;
//			for(int i=0; i<list.size(); i++ )
//			{
//				returnStr.append(list.get(i).get("time")).append(",")
//				         .append(0).append(",")
//				         .append(df.format(list.get(i).get("AVG(open)"))).append(",")
//				          .append(df.format(list.get(i).get("AVG(high)"))).append(",")
//				           .append(df.format(list.get(i).get("AVG(low)"))).append(",")
//				            .append(df.format(list.get(i).get("AVG(close)"))).append(",")
//				            .append(df.format(list.get(i).get("sum(volume)"))).append(",")
//				            .append(df.format(list.get(i).get("sum(volume)"))).append("\n")
//				            ;
//			}
//			
//					return ok(returnStr.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			resultRtn.msg = "server error";
//			resultRtn.errCode = -1;
//			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
//		}
//	}
//	
	
    
    
    public static Result KLineGo2(String mid,String stkCode,String kType,int lenth,String callback){
		
   	 String keyvalue ="k"+kType+"_"+stkCode;
   	 String result=RedisAPI_K.get(keyvalue);
     if(result!=null)
    	 return ok( result);
     return KLineGo(mid,stkCode,kType,lenth,callback);
   	
   		// return ok( "0K");
   	}
    
    public static Result KLineGo3(String mid,String stkCode,String kType,int lenth,String callback){
		
       
         return KLineGo(mid,stkCode,kType,lenth,callback);
      	
      		// return ok( "0K");
      	}
    
    
    
    public static Result KLineGo(String mid,String symbol,String kType,int lenth,String callback){
		ResultRtn resultRtn = new ResultRtn();
		
		if("100".equals(kType))
		  return KLineResourceN100(mid,symbol,kType,lenth, callback);
		else if("200".equals(kType))
		  return KLineResource200(mid,symbol,kType,lenth, callback);
		else if("300".equals(kType))
			  return KLineResource300(mid,symbol,kType,lenth, callback);
//		else if("005".equals(kType))
//			  return KLineResource005(mid,symbol,kType,lenth, callback);
		resultRtn.msg = "server error";
		resultRtn.errCode = -1;
		return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
	}
	
	
	public static Result KLineResource100(String mid,String symbol,String kType,int length,String callback)
	{	
		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
		 java.text.DecimalFormat   dt=new   java.text.DecimalFormat("yyyyMMdd"); 
			
	
	     if("399001".equals(symbol))
				symbol="2A01";
	
		 
		 SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat ("yyyyMMdd");
		 Stock tomstock =Stock.find.where().eq("code", symbol).findUnique(); 
			if(tomstock==null)
				return ok("stock is not exist in stock table");
	
			
			
	     
			if("2A01".equals(symbol))
				symbol="399001";
		
			
		    
		ResultRtn resultRtn = new ResultRtn();
		List<DailyStockMarket> list = Ebean.find(DailyStockMarket.class)
				.where().eq("symbol", symbol).orderBy("time desc")
				.setMaxRows(length-1).findList();

		StringBuffer returnStr = new StringBuffer();

		returnStr.append(callback + "({\"s\":\"").append(symbol)
				.append("\",\"n\":\"").append(tomstock.name)
				.append("\",\"data\":[");

		System.out.println("size=----" + list.size());
		if (list.size() == 0) {

			return ok(returnStr + "]})");

		}


		
			
					
	
	for(int i=list.size()-1; i>=0; i-- )
	{
		if(list.get(i).volume ==0)
			continue;
		
		returnStr
		.append("{\"d\":").append(formatter.format(list.get(i).time)).append(",")
		.append("\"").append("t").append("\":").append("0").append(",")
		.append("\"o\":").append(df.format(list.get(i).open)).append(",")
		.append("\"h\":").append(df.format(list.get(i).high)).append(",")
		.append("\"l\":").append(df.format(list.get(i).low)).append(",")
		.append("\"c\":").append(df.format(list.get(i).close)).append(",")
		.append("\"v\":").append(df.format(list.get(i).volume/100)).append(",")
		.append("\"a\":").append(df.format(list.get(i).amount/100)).append("}")
		.append("\"j1\":").append(df.format(list.get(i).volume/200)).append("}")
		.append("\"j3\":").append(df.format(list.get(i).amount/100+list.get(i).volume/100)).append("}")
		.append("\"bs\":").append("\"\"").append("},")
		  
		
		;
	}
	
	//add for today kline			
//	String todayDate = formatter.format(new Date());
//
//    String sqlstr1="select max(price),min(price),max(id),min(id) from time_line  where stk_code ='"+symbol +"' and date= "+todayDate+" group by date";
//  	
//    
//   // List<SqlRow> list1 = Ebean.createSqlQuery(sqlstr1).findList();
//   // SqlRow sr= list1.get(0);
//	SqlRow sr= Ebean.createSqlQuery(sqlstr1).findUnique();

//	if (sr != null) {
//
//		
//	//sr.getDouble(arg0)
//	String  high =df.format(sr.get("max(price)"));
//	String  lower =df.format(sr.get("min(price)"));
//	double  real_close = StockMarketTimeLine.find.byId(sr.getLong("max(id)")).price;
//	
//	double  closeAmount =StockMarketTimeLine.find.byId(sr.getLong("max(id)")).amount;
//	double  closeVol =StockMarketTimeLine.find.byId(sr.getLong("max(id)")).volume;
//	double  real_open =  StockMarketTimeLine.find.byId(sr.getLong("min(id)")).price;
//	
//	returnStr
//	.append("{\"d\":").append(todayDate).append(",");
//	returnStr.append("\"").append("t").append("\":").append("0").append(",");
//	returnStr.append("\"o\":").append(df.format(real_open)).append(",");
//	returnStr.append("\"h\":").append(high).append(",");
//	returnStr.append("\"l\":").append(lower).append(",");
//	returnStr.append("\"c\":").append(df.format(real_close)).append(",");
//	returnStr.append("\"v\":").append(df.format(closeVol/100)).append(",");
//	returnStr.append("\"a\":").append(df.format(closeAmount/100)).append("},");
//	;
//	}
	
	Date today=new Date();
    String time = new SimpleDateFormat("HHmm").format(new Date());

  
  if(WorkDayTools.isHoliday(today)|| WorkDayTools.isWorkEndDay(today))
	{
	//  System.out.println(time+":date --lead to  StockRealDataTask Thread no run...");  
		 return ok( returnStr.substring(0,returnStr.length()-1)+"]})");
	}
	
	if(! ( (time.compareTo("0930")>0&&time.compareTo("1130")<0)||(time.compareTo("1300")>0&&time.compareTo("1500")<0)) ){
	//	System.out.println(time+": time --lead to  StockRealDataTask Thread no run...");  
		 return ok( returnStr.substring(0,returnStr.length()-1)+"]})");
	}
	

	
	
		//String stockCode1=StockMarkets.StockTransfer(symbol);
		HqTransfor.HQ163 hq163=StockMarkets.GetOneHq(symbol);
		
		String  high =df.format(hq163.high);
		String  lower =df.format(hq163.low);
		double  real_close =hq163.price;
		
		double  closeAmount =hq163.turnover/100;
		double  closeVol =hq163.volume/100;
		double  real_open = hq163.open;
		String todayDate = formatter.format(new Date());
		returnStr
		.append("{\"d\":").append(todayDate).append(",");
		returnStr.append("\"").append("t").append("\":").append("0").append(",");
		returnStr.append("\"o\":").append(df.format(real_open)).append(",");
		returnStr.append("\"h\":").append(high).append(",");
		returnStr.append("\"l\":").append(lower).append(",");
		returnStr.append("\"c\":").append(df.format(real_close)).append(",");
		returnStr.append("\"v\":").append(df.format(closeVol/100)).append(",");
		returnStr.append("\"a\":").append(df.format(closeAmount/100)).append(",");
		returnStr.append("\"j1\":").append(df.format(closeAmount/200)).append("}");
		returnStr.append("\"j3\":").append(df.format(closeAmount/100*3)).append("}");
		returnStr.append("\"bs\":").append("\"\"").append("},");
		;

//add for  today  kline  end
	
	//returnStr.append( returnStr.substring(0,returnStr.length()-1)+"]})");
	 return ok( returnStr.substring(0,returnStr.length()-1)+"]})");
	 
	}

	
	
	public static Result KLineResource200(String mid,String symbol,String kType,int length,String callback)
	{
		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.00"); 
		 SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat ("yyyyMMdd");
		
		
	    
		    
//		Stock tomstock =Stock.find.where().eq("code", symbol).findUnique(); 
//			if(tomstock==null)
//				return ok("stock is not exist in stock table");
//					 ResultRtn resultRtn = new ResultRtn();
//					
//				     
//						if("2A01".equals(symbol))
//							symbol="399001";
		 ResultRtn resultRtn = new ResultRtn();	
					
		try {
			StringBuffer returnStr =new StringBuffer();
			
			returnStr.append(callback+"({\"s\":\"")
			.append(symbol).append("\",\"n\":\"");
			
			
			String sqlstr="select t.securityname,max(t.EID),min(t.EID),t.TRADEDATE,open,MAX(high),MIN(low),t.new,sum(t.TVOL),sum(t.TVALCNY) from TRAD_SK_DAILY_JC t  where t.SECURITYCODE='"+symbol +"' group by year(t.TRADEDATE),week(t.TRADEDATE)  order by TRADEDATE desc limit "+length;
			List<SqlRow> list = Ebean.getServer("emdata").createSqlQuery(sqlstr).findList();
			
			
			
			
			if(list.size()==0){
			    
				return ok( returnStr+"]})");	
						
	     	}
			returnStr.append(list.get(list.size()-1).get("securityname")).append("\",\"data\":[");
			for(int i=list.size()-1; i>0; i-- )
			{
				StringBuffer returnStr1=new StringBuffer();
				//System.out.println(i);
				String sqlstr1="select open from TRAD_SK_DAILY_JC t  where t.EID='"+list.get(i).get("min(t.eid)") +"'limit 1";;
				SqlRow mix_open = Ebean.getServer("emdata").createSqlQuery(sqlstr1).findUnique();
				
				String sqlstr2="select new from TRAD_SK_DAILY_JC t  where t.EID='"+list.get(i).get("max(t.eid)") +"'limit 1";
				SqlRow max_close = Ebean.getServer("emdata").createSqlQuery(sqlstr2).findUnique();
				
				//System.out.println("---"+df.format(list.get(i).get("LOWER(low)").toString()));
				returnStr1.append("{\"d\":").append(formatter.format(list.get(i).getDate("tradedate"))).append(",")
				.append("\"").append("t").append("\":").append("0").append(",") 
				.append("\"o\":").append(mix_open.get("open")).append(",")
				.append("\"h\":").append(df.format(list.get(i).get("MAX(high)"))).append(",")
				.append("\"l\":").append(df.format(list.get(i).get("MIN(low)"))).append(",")
				.append("\"c\":").append(max_close.get("new")).append(",");
				
				if(list.get(i).get("sum(t.TVOL)")==null){
					continue;
				}
				
				if(list.get(i).get("sum(t.TVOL)")==null)
					returnStr1.append("\"v\":").append(0).append(",");	
				else
					returnStr1.append("\"v\":").append(df.format(list.get(i).get("sum(t.TVOL)"))).append(",");
				
				if(list.get(i).get("sum(t.TVALCNY)")==null)
					returnStr1.append("\"a\":").append(0).append(",");
				else
					returnStr1.append("\"v\":").append(df.format(list.get(i).get("sum(t.TVOL)"))).append(",");	
				
				returnStr1.append("\"j1\":").append(0).append(",")
				.append("\"j3\":").append(0).append(",")
				.append("\"bs\":").append("\"\"").append("},");
				;
				returnStr.append(returnStr1); 
			}
			
			//		return ok(returnStr.toString());
                           return ok( returnStr.substring(0,returnStr.length()-1)+"]})");	
	} catch (Exception e) {
			e.printStackTrace();
			resultRtn.msg = "server error";
			resultRtn.errCode = -1;
			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
		}
	}	
	
	
	
	public static Result KLineResource300(String mid,String symbol,String kType,int length,String callback)
	{
		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.00"); 
		 SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat ("yyyyMMdd");
		
		
	    
		    
//		Stock tomstock =Stock.find.where().eq("code", symbol).findUnique(); 
//			if(tomstock==null)
//				return ok("stock is not exist in stock table");
//					 ResultRtn resultRtn = new ResultRtn();
//					
//				     
//						if("2A01".equals(symbol))
//							symbol="399001";
		 ResultRtn resultRtn = new ResultRtn();	
					
		try {
			StringBuffer returnStr =new StringBuffer();
			
			returnStr.append(callback+"({\"s\":\"")
			.append(symbol).append("\",\"n\":\"");
			
			
			String sqlstr="select t.securityname,max(t.EID),min(t.EID),t.TRADEDATE,open,MAX(high),MIN(low),t.new,sum(t.TVOL),sum(t.TVALCNY) from TRAD_SK_DAILY_JC t  where t.SECURITYCODE='"+symbol +"' group by year(t.TRADEDATE),month(t.TRADEDATE)  order by TRADEDATE desc limit "+length;
			List<SqlRow> list = Ebean.getServer("emdata").createSqlQuery(sqlstr).findList();
			
			
			
			
			if(list.size()==0){
			    
				return ok( returnStr+"]})");	
						
	     	}
			returnStr.append(list.get(list.size()-1).get("securityname")).append("\",\"data\":[");
			for(int i=list.size()-1; i>0; i-- )
			{
				StringBuffer returnStr1=new StringBuffer();
				//System.out.println(i);
				String sqlstr1="select open from TRAD_SK_DAILY_JC t  where t.EID='"+list.get(i).get("min(t.eid)") +"'limit 1";;
				SqlRow mix_open = Ebean.getServer("emdata").createSqlQuery(sqlstr1).findUnique();
				
				String sqlstr2="select new from TRAD_SK_DAILY_JC t  where t.EID='"+list.get(i).get("max(t.eid)") +"'limit 1";
				SqlRow max_close = Ebean.getServer("emdata").createSqlQuery(sqlstr2).findUnique();
				
				//System.out.println("---"+df.format(list.get(i).get("LOWER(low)").toString()));
				returnStr1.append("{\"d\":").append(formatter.format(list.get(i).getDate("tradedate"))).append(",")
				.append("\"").append("t").append("\":").append("0").append(",") 
				.append("\"o\":").append(mix_open.get("open")).append(",")
				.append("\"h\":").append(df.format(list.get(i).get("MAX(high)"))).append(",")
				.append("\"l\":").append(df.format(list.get(i).get("MIN(low)"))).append(",")
				.append("\"c\":").append(max_close.get("new")).append(",");
				
				if(list.get(i).get("sum(t.TVOL)")==null){
					continue;
				}
				
				if(list.get(i).get("sum(t.TVOL)")==null)
					returnStr1.append("\"v\":").append(0).append(",");	
				else
					returnStr1.append("\"v\":").append(df.format(list.get(i).get("sum(t.TVOL)"))).append(",");
				
				if(list.get(i).get("sum(t.TVALCNY)")==null)
					returnStr1.append("\"a\":").append(0).append(",");
				else
					returnStr1.append("\"v\":").append(df.format(list.get(i).get("sum(t.TVOL)"))).append(",");	
				
				returnStr1.append("\"j1\":").append(0).append(",")
				.append("\"j3\":").append(0).append(",")
				.append("\"bs\":").append("\"\"").append("},");
				;
				returnStr.append(returnStr1); 
			}
			
			//		return ok(returnStr.toString());
                           return ok( returnStr.substring(0,returnStr.length()-1)+"]})");	
	} catch (Exception e) {
			e.printStackTrace();
			resultRtn.msg = "server error";
			resultRtn.errCode = -1;
			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
		}
	}
	
	
	public static Result KLineResource300_old(String mid,String symbol,String kType,int length,String callback)
	{
		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
	
		 if("399001".equals(symbol))
				symbol="2A01";
	
		 SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat ("yyyyMMdd");   
	
		 ResultRtn resultRtn = new ResultRtn();
		Stock tomstock =Stock.find.where().eq("code", symbol).findUnique(); 
		if(tomstock==null)
			return ok("stock is not exist in stock table");
		
     
		if("2A01".equals(symbol))
			symbol="399001";
	
	
		
		
		
		try {
			String sqlstr="select max(id),min(id),symbol,name,time,open,MAX(high),MIN(low),close,sum(volume),sum(amount) from daily_k  where symbol='"+symbol +"'  group by month(time),year(time) order by time  desc limit "+length;
			List<SqlRow> list = Ebean.createSqlQuery(sqlstr).findList();
		//	resultRtn.business.put("symbol", symbol);
		//	resultRtn.business.put("name", list.get(0).get("name"));
		//	resultRtn.business.put("data", list)
			StringBuffer returnStr =new StringBuffer();
		
			returnStr.append(callback+"({\"s\":\"")
			.append(symbol).append("\",\"n\":\"")
			.append(tomstock.name).append("\",\"data\":[");
			
            if(list.size()==0){
			    
				return ok( returnStr+"]})");	
						
	     	}
			
			for(int i=list.size()-1; i>0; i-- )
			{
				
				
				
				returnStr.append("{\"d\":").append(formatter.format(list.get(i).get("time"))).append(",")
				.append("\"").append("t").append("\":").append("0").append(",")
			//	.append("\"o\":").append(DailyStockMarket.find.byId(Long.parseLong(list.get(i).get("max(id)").toString())).open).append(",")
				.append("\"h\":").append(df.format(list.get(i).get("MAX(high)"))).append(",")
				.append("\"l\":").append(df.format(list.get(i).get("MIN(low)"))).append(",")
			//	.append("\"c\":").append(DailyStockMarket.find.byId(Long.parseLong(list.get(i).get("min(id)").toString())).close).append(",")
				.append("\"v\":").append(df.format(list.get(i).get("sum(volume)"))).append(",")
				.append("\"a\":").append(df.format(list.get(i).get("sum(amount)"))).append("},")
				;
				
			}
			
				//	return ok(returnStr.toString());
                             return ok( returnStr.substring(0,returnStr.length()-1)+"]})");	
	} catch (Exception e) {
			e.printStackTrace();
			resultRtn.msg = "server error";
			resultRtn.errCode = -1;
			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
		}
	}
	
	
	
	public static Result KLineResourceN100(String mid,String symbol,String kType,int length,String callback)
	{	
		 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
		 java.text.DecimalFormat   dt=new   java.text.DecimalFormat("yyyy-MM-dd"); 
		// length=50;
		 SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat ("yyyyMMdd");
		 Stock tomstock =Stock.find.where().eq("code", symbol).findUnique(); 
			if(tomstock==null)
				return ok("stock is not exist in stock table");
	
		ResultRtn resultRtn = new ResultRtn();

//		String sqlstr1="SELECT high,open,low,NEW,TVOL,TVALCNY,TRADEDATE  FROM `TRAD_SK_DAILY_JC` t  where t.SECURITYCODE ='"
//				+ symbol
//				+ "' order by  TRADEDATE desc limit "
//				+ length;

		if(symbol.startsWith("J")||symbol.startsWith("G")||symbol.startsWith("Z"))
			symbol=symbol.substring(1);
		
		StringBuffer strsql2=new StringBuffer();
		strsql2.append("SELECT   t.high,t.open,t.low,t.new ,t.TVOL,t.TVALCNY,t.TRADEDATE , t.SECURITYCODE ,f.FLOWINLS,f.FLOWINM ,f.FLOWINL,f.FLOWINXL")
		       .append(" FROM `TRAD_SK_DAILY_JC` t left JOIN `TD_ASHAREFUNDFLOW` f ")
		       .append(" on t.SECURITYCODE =f.SECURITYCODE  and t.TRADEDATE = f.TRADEDATE ")
		       .append(" where t.SECURITYCODE='").append(symbol).append("'")
			   .append(" order by  TRADEDATE desc limit ")
		       .append(length);
	
		
		
		StringBuffer returnStr = new StringBuffer();
	    
		try {    //begin try1
			List<SqlRow> list=Ebean.getServer("emdata").createSqlQuery(strsql2.toString()).findList();
		    
			System.out.println(list.size());
	
			returnStr.append(callback + "({\"s\":\"").append(symbol)
					.append("\",\"n\":\"").append(tomstock.name)
					.append("\",\"data\":[");

			if (list.size() == 0) {
	
				return ok(returnStr + "]})");
	
			}
					
			for(int i=list.size()-1; i>=0; i-- )
			{
				
				if(list.get(i).getDouble("TVALCNY")==null)
					continue;
				
				System.out.println(formatter.format(list.get(i).getDate(("TRADEDATE")))+i);
				
				returnStr
				.append("{\"d\":").append(formatter.format(list.get(i).getDate(("TRADEDATE")))).append(",");
				returnStr.append("\"").append("t").append("\":").append("0").append(",");
				returnStr.append("\"o\":").append(df.format(list.get(i).getDouble("open"))).append(",");
				returnStr.append("\"h\":").append(list.get(i).getDouble("high")).append(",");
				returnStr.append("\"l\":").append(list.get(i).getDouble("low")).append(",");
				returnStr.append("\"c\":").append(list.get(i).getDouble("new")).append(",");
				returnStr.append("\"v\":").append(df.format(list.get(i).getDouble("TVOL"))).append(",");
				returnStr.append("\"a\":").append(df.format(list.get(i).getDouble("TVALCNY"))).append(",");
				returnStr.append("\"j1\":").append(df.format(list.get(i).getDouble("FLOWINLS")
						                           +list.get(i).getDouble("FLOWINM")
						                           +list.get(i).getDouble("FLOWINL")
						                           +list.get(i).getDouble("FLOWINXL")  )).append(",");
				returnStr.append("\"j3\":").append(df.format(list.get(i).getDouble("FLOWINL")*4*3)).append(",");
				returnStr.append("\"bs\":").append("\"\"").append("},");
				
				;
			}
	
 	
     	Date today=new Date();
        String time = new SimpleDateFormat("HHmm").format(new Date());

  
	//  if(WorkDayTools.isHoliday(today)|| WorkDayTools.isWorkEndDay(today))
	    if( WorkDayTools.isWorkEndDay(today))
		{
		//  System.out.println(time+":date --lead to  StockRealDataTask Thread no run...");  
			 return ok( returnStr.substring(0,returnStr.length()-1)+"]})");
		}
		
		if(! ( (time.compareTo("0930")>0&&time.compareTo("1130")<0)||(time.compareTo("1300")>0&&time.compareTo("1500")<0)) ){
		//	System.out.println(time+": time --lead to  StockRealDataTask Thread no run...");  
			 return ok( returnStr.substring(0,returnStr.length()-1)+"]})");
		}
	

	
	
		//String stockCode1=StockMarkets.StockTransfer(symbol);
		HqTransfor.HQ163 hq163=StockMarkets.GetOneHq(symbol);
		
		String  high =df.format(hq163.high);
		String  lower =df.format(hq163.low);
		double  real_close =hq163.price;
		
		double  closeAmount =hq163.turnover/100;
		double  closeVol =hq163.volume/100;
		double  real_open = hq163.open;
		String todayDate = formatter.format(new Date());
		returnStr
		.append("{\"d\":").append(todayDate).append(",");
		returnStr.append("\"").append("t").append("\":").append("0").append(",");
		returnStr.append("\"o\":").append(df.format(real_open)).append(",");
		returnStr.append("\"h\":").append(high).append(",");
		returnStr.append("\"l\":").append(lower).append(",");
		returnStr.append("\"c\":").append(df.format(real_close)).append(",");
		returnStr.append("\"v\":").append(df.format(closeVol/100)).append(",");
		returnStr.append("\"a\":").append(df.format(closeAmount/100)).append(",");
		returnStr.append("\"j1\":").append(df.format(closeAmount/200)).append(",");
		returnStr.append("\"j3\":").append(df.format(closeAmount/100*3)).append(",");
		returnStr.append("\"bs\":").append("\"\"").append("},");
		;

//add for  today  kline  end
	
		} catch (Exception e) {   //begin try1
			// TODO: handle exception
			System.out.println(e);
		}
		
		
	//returnStr.append( returnStr.substring(0,returnStr.length()-1)+"]})");
	 return ok( returnStr.substring(0,returnStr.length()-1)+"]})");
	 
	}

		 

	}
	
	
	
	

