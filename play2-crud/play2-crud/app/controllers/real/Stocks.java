package controllers.real;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.typesafe.plugin.RedisPlugin;

import models.ResultRtn;
import models.Stock;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import utils.HqTransfor;
import utils.HqTransfor.HQ163_base;
import utils.redis.RedisAPI;
import utils.tools.MathTools;

public class Stocks extends Controller {
	private static  Jedis j;// = Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
	
	
	public static class UpdateStocks{
		public String name;
		public String code;
		public String sname;
		public int status;
		
		
		
	}
	
	public static class RealData{
		public String time;
		public double value;
	}
	
	public static class handicap{
		public String weibi;  
		public String weicha;
		public String buy; 
		public String sell;
	}
	
	public static class sellItem{
		public String s1;
		public String s2;
		public String s3;
		public String s4;
		public String s5;
		}
	
	
	public static class buyInItem{
		public String b1;
		public String b2;
		public String b3;
		public String b4;
		public String b5;
		}
	
	
	public static class analyst{
		public String analystNum;
		public String avaCost;
		}
	
	
	
	
	public static class StockWebDetail{
		public String curPrice    ;
		public String change    ; 
		public String upOrDown   ;
		public String changePercent  ;   
		public String dayHigh   ;
		public String dayLow  ; 
		public String turnover  ;  
		public String volume    ; 
		public String dayReturnPercent  ;  
		public String amountOfmoney  ; 
	
		
	}
	
	public static class StockIndex{
		public String indexName;
		public int    indexType;
		public double  indexVal;
		public Boolean  upOrDown;
		public String  upOrDownVal;
		public String  increase;
		public String  indexCode;
		
	}
	

	public static Result stockRealdata(String mid, int type) {
		ResultRtn resultRtn = new ResultRtn();
		try {

			List<RealData> realDatas=new ArrayList<RealData>();
		
				RealData realData=new RealData();
			switch(type){
			
			case 1:
				realData.time="10:00";
				realData.value=13499.45;
				realDatas.add(realData);
				realData.time="10:05";
				realData.value=13498.45;
				realDatas.add(realData);
				resultRtn.business.put("average", 13500.84);
				break;
			case 2:
				realData.time="10:00";
				realData.value=3399.82;
				realDatas.add(realData);
				realData.time="10:05";
				realData.value=3412.23;
				realDatas.add(realData);
				resultRtn.business.put("average", 3400);
				break;
			case 3:
				realData.time="10:00";
				realData.value=2680.45;
				realDatas.add(realData);
				realData.time="10:05";
				realData.value=2712.45;
				realDatas.add(realData);
				resultRtn.business.put("average", 2700);
				break;
			}
			resultRtn.business.put("data", realDatas);
		}catch(Exception ex){
			resultRtn.msg = "server error";
			resultRtn.errCode = -1;
		}
		return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
	}
	
	public static Result stockQuery(String mid, String simpleCode) {
		ResultRtn resultRtn = new ResultRtn();
		try {

			List<Stock> stockList=new ArrayList<Stock>();
			
		    	if(simpleCode.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
		    		simpleCode="%"+simpleCode+"%";		
		    		stockList=Stock.find.where().like("code", simpleCode).findList();
		    		System.out.println("999");
		    	}else{
		    		simpleCode="%"+simpleCode+"%";
				//RealData realData=new RealData();
		    		System.out.println("999UUUU");
		        stockList=Stock.find.where().like("simple_name", simpleCode).findList();		
		    	}
				
			resultRtn.business.put("data", stockList);
		}catch(Exception ex){
			resultRtn.msg = "server error";
			resultRtn.errCode = -1;
		}
		return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
	}
	
	
	
	public static Result update_stocks(String mid, long userId,double ver) {
		ResultRtn resultRtn = new ResultRtn();
		try {

			List<Stock> stockList=new ArrayList<Stock>();
			List<UpdateStocks> UpdateStocksList=new ArrayList<UpdateStocks>();
			//Stock.find.select("max(version)").f;
			
			String sqlstr1="select max(version) from stock  ";
		  	
			SqlRow sr= Ebean.createSqlQuery(sqlstr1).findUnique();
			
			stockList = Stock.find.where().gt("version", ver).findList();
			
			for(int i=0;i<stockList.size();i++){
				UpdateStocks updateStocks =new UpdateStocks();
				
				updateStocks.code =stockList.get(i).code;
				updateStocks.name =stockList.get(i).name;
				updateStocks.sname =stockList.get(i).simpleName;
				updateStocks.status =(int)stockList.get(i).stocktotalnum;
				
				UpdateStocksList.add(updateStocks);
			}
			
//			ObjectNode hb = Json.newObject();
//			hb.put("version", sr.get("max(version)").toString());
			//hb.put("updateStocks", UpdateStocksList);
			resultRtn.business.put("version", sr.get("max(version)").toString());
			resultRtn.business.put("updateStocks", UpdateStocksList);
			
			
		}catch(Exception ex){
			resultRtn.msg = "server error";
			resultRtn.errCode = -1;
		}
		return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
	}
	
//	public static Result get_stock_web_detail(String mid, String stockCode , String callback) {
//		 JedisPool pool = RedisAPI.getPool();  
//        j=  pool.getResource();  
//        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
//		ResultRtn resultRtn = new ResultRtn();
//		
////		if("1A0001".equals("stockCode"))
////			stockCode="1000001";
//		 if("2A01".equals(stockCode))
//			stockCode="0.2A01";
//		
//		
//	        ObjectMapper mapper = new ObjectMapper();
//	        StringBuffer strapp =new StringBuffer();
//	        StringBuffer sb=new StringBuffer();
//	    	StringBuffer sb1=new StringBuffer();
//			StringBuffer sb2=new StringBuffer();
//			StringBuffer sb3=new StringBuffer();
//		try {
//
//			JsonNode node = mapper.readTree(j.get(stockCode));
//			
//			//HQ163 hq163=StockMarkets.GetOneHq("163_"+stockCode);
//           //System.out.println("stock list:"+new java.util.Date());
//           resultRtn.msg = "ok";
//           resultRtn.errCode = 0;
//           
//           
//           StockWebDetail  stockWebDetail=new StockWebDetail();
//			        stockWebDetail.curPrice    = node.get("current").asText();
//					stockWebDetail.change   =  node.get("upDowns").asText();
//					if(node.get("rise").asDouble()>0)
//						stockWebDetail.upOrDown   = "true";
//					else
//						stockWebDetail.upOrDown   = "false";
//					
//					stockWebDetail.changePercent =  node.get("rise").asText();
//					stockWebDetail.dayHigh   = node.get("high").asText();
//					stockWebDetail.dayLow =node.get("low").asText();
//					stockWebDetail.turnover =node.get("amount").asText();
//					//stockWebDetail.volume   =String.valueOf(paraseFloatWithChineseUnit(node.get("volume").asText()))+"万";
//					
//					 if("0.2A01".equals(stockCode))
//					 {
//						 stockCode="2A01";
//					 }
//					
//				    models.Stock tmpstock
//					         = Stock.find.where ().eq("code",stockCode ).findList().get(0);
//					
//					try{
//						
//						double  current_tmp= Double.parseDouble(stockWebDetail.curPrice);
//						double  stocktmp=MathTools.div(
//					    	current_tmp*tmpstock.stocktotalnum,100000000.00,2);
//						
//					    stockWebDetail.amountOfmoney = String.valueOf(stocktmp)+"亿";
////					    		String.valueOf(
////							MathTools.div(
////								MathTools.sub
////								  (current_tmp
////										,(double)tmpstock.stocktotalnum)
////										,100000000.00
////									)
////									);//node.get("volume").asText();
////					
//
//				//	System.out.println("--->"+tmpstock.stocktotalnum);
//					
//					}catch(Exception e){
//						stockWebDetail.amountOfmoney  = "-";
//					}
//							
//							
//							
//							//node.get("volume").asText();
//					
//					
//					
//					
//					stockWebDetail.volume   = "-1";
//					
//					stockWebDetail.dayReturnPercent = node.get("huanshou").asText();
//					//stockWebDetail.amountOfmoney =node.get("amount").asText();
//					handicap hc=new handicap();
//					sellItem sell=new sellItem();
//					ObjectNode hb = Json.newObject();
//					buyInItem buy=new buyInItem();
//					//public String otherItem;
//					analyst  anly=new analyst();
//					
//					hc.weibi=node.get("weibi").asText();
//					hc.sell=node.get("neipan").asText();
//					hc.buy=node.get("waipan").asText();
//					hc.weicha=node.get("weicha").asText();
//					sell.s1=node.get("sale1").asText()+","+node.get("sale1_v").asText();
//					sell.s3=node.get("sale3").asText()+","+node.get("sale3_v").asText();
//					buy.b1=node.get("buy1").asText()+","+node.get("buy1_v").asText();
//					buy.b3=node.get("buy3").asText()+","+node.get("buy2_v").asText();
//					
//					sell.s2=node.get("sale2").asText()+","+node.get("sale2_v").asText();
//					sell.s4=node.get("sale4").asText()+","+node.get("sale4_v").asText();
//					buy.b2=node.get("buy2").asText()+","+node.get("buy2_v").asText();
//					buy.b4=node.get("buy4").asText()+","+node.get("buy4_v").asText();
//					sell.s5=node.get("sale5").asText()+","+node.get("sale5_v").asText();
//					buy.b5=node.get("buy5").asText()+","+node.get("buy5_v").asText();
//					
//					
//
//					String otherItem="14:55:22,20.3,1212"  ;
//					//PortfioList.find.where().eq("stock_code", stockCode).findRowCount();
//					int countA=PortfioList.find.where().eq("stock_code", stockCode).findRowCount();
//					
//					
//					String sqlstr="select sum(stock_price)/count(stock_price) from portfio_list p  where  p.stock_code=:stockCode";
//					SqlRow list = Ebean.createSqlQuery(sqlstr).setParameter("stockCode", stockCode).findUnique();
//					
//					anly.analystNum =String.valueOf(countA);	
//						String a =list.getString("sum(stock_price)/count(stock_price)");
//					   if(a==null)
//						anly.avaCost="0";
//					   else
//					    anly.avaCost =df.format(Double.parseDouble(a));
//					
//					//simpleCode="%"+simpleCode+"%";
//		//   stockList=Stock.find.where().like("simple_name", stockCode).findList();		
//		
//			sb1.append(",\"sellItem\":[\"1,").append(sell.s1).append("\",")
//			  .append("\"2,").append(sell.s2).append("\",")
//			  .append("\"3,").append(sell.s3).append("\",")
//			  .append("\"4,").append(sell.s4).append("\",")
//			  .append("\"5,").append(sell.s5).append("\"]");
//			sb2.append(",\"buyInItem\":[\"1,").append(buy.b1).append("\",")
//			.append("\"2,").append(buy.b2).append("\",")
//			.append("\"3,").append(buy.b3).append("\",")
//			.append("\"4,").append(buy.b4).append("\",")
//			.append("\"5,").append(buy.b5).append("\"]");
//			sb3.append(",\"otherItem\":").append("[\"").append(otherItem).append("\"]");
//			resultRtn.business.put("handicap", hc);
//		/*	resultRtn.business.put("sellItem", "[]");
//			resultRtn.business.put("buyInItem", sb2); 
//			resultRtn.business.put("otherItem", sb3);*/
//			resultRtn.business.put("analyst", anly);
//			resultRtn.business.put("stockWebDetail", stockWebDetail);
//	   
//			//resultRtn.business.put("stockWebDetail", sb);
//			String aa =Json.toJson(resultRtn).toString();
//			sb.append(aa.substring(0, aa.length()-2))
//			   .append(sb1)
//			   .append(sb2)
//			   .append(sb3)
//			   .append("}}");
//			strapp.append(callback).append("(")
//		     
//		       .append(sb)
//		       .append(")");
//			//callback+"("+Json.toJson(resultRtn)+")";
//			 
//		}catch(Exception ex){
//			resultRtn.msg = "server error";
//			resultRtn.errCode = -1;
//			strapp.append(ex);
//			RedisAPI.returnResource(pool, j);
//		}
//		
//		try {
//			 RedisAPI.returnResource(pool, j);
//		} catch (Exception e) {
//			// TODO: handle exception
//			
//		}
//		
//		return ok(strapp.toString());
//	}
		
	
	public static Result get_stock_web_detail(String mid, String stockCode , String callback) {
		 JedisPool pool = RedisAPI.getPool();  
         j=  pool.getResource();  
         java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
		ResultRtn resultRtn = new ResultRtn();
		
		String stockCode1=StockMarkets.StockTransfer(stockCode);
//		if(stockCode.startsWith("60"))
//			stockCode1="0"+stockCode;
//		else if(stockCode.startsWith("00")||stockCode.startsWith("30")||stockCode.startsWith("39")){
//			stockCode1="1"+stockCode;
//		}
		
		
		  NumberFormat fmt = NumberFormat.getPercentInstance();
			fmt.setMaximumFractionDigits(2);//最多两位百分小数，如25.23%
         
		
//		if("1A0001".equals("stockCode"))
//			stockCode="1000001";
//		 if("2A01".equals(stockCode))
//			stockCode1="1399001";
//		 else if("1A0001".equals(stockCode)){
//			 stockCode1="0000001";
//		 }
		
	        StringBuffer strapp =new StringBuffer();
	        StringBuffer sb=new StringBuffer();
	    	StringBuffer sb1=new StringBuffer();
			StringBuffer sb2=new StringBuffer();
			StringBuffer sb3=new StringBuffer();
		try {

			
			HqTransfor.HQ163 hq163=StockMarkets.GetOneHq(stockCode1);
			HQ163_base hq163_base=StockMarkets.GetOneHq_163_base(stockCode1);
            //System.out.println("stock list:"+new java.util.Date());
            resultRtn.msg = "ok";
            resultRtn.errCode = 0;
            
            
            StockWebDetail  stockWebDetail=new StockWebDetail();
			        stockWebDetail.curPrice    = String.valueOf(hq163.price);
			        //node.get("current").asText();
					stockWebDetail.change   =  String.valueOf(hq163.updown);
							//node.get("upDowns").asText();
					if(hq163.updown >0)
						stockWebDetail.upOrDown   = "true";
					else
						stockWebDetail.upOrDown   = "false";
					
					stockWebDetail.changePercent = fmt.format(hq163.percent);
							//node.get("rise").asText();
					stockWebDetail.dayHigh   = String.valueOf( hq163.high); 
							//node.get("high").asText();
					stockWebDetail.dayLow =String.valueOf( hq163.low); 
							//node.get("low").asText();
					stockWebDetail.turnover =String.valueOf( hq163.turnover); 
							//node.get("amount").asText();
					//stockWebDetail.volume   =String.valueOf(paraseFloatWithChineseUnit(node.get("volume").asText()))+"万";
					
//					 if("0.2A01".equals(stockCode))
//					 {
//						 stockCode="0000001";
//					 }
					
					 stockWebDetail.turnover   = String.valueOf(MathTools.div(Double.parseDouble(String.valueOf(hq163.turnover)),100000000.00,2))+"亿";//成交额
					
						handicap hc=new handicap();
						sellItem sell=new sellItem();
						buyInItem buy=new buyInItem();
						analyst  anly=new analyst();
    if("2A01".equals(stockCode)||"399006".equals(stockCode)||"1A0001".equals(stockCode)){	

    	stockWebDetail.amountOfmoney    =  "-亿";//总市值
		
		stockWebDetail.volume = "-";//String.valueOf(MathTools.div(node1.get("VOLUME").asDouble(),100000000.00,2))+"亿";//成交额
		
		stockWebDetail.dayReturnPercent = "-";;

		//stockWebDetail.amountOfmoney =node.get("amount").asText();
	
		ObjectNode hb = Json.newObject();
		
		//public String otherItem;
		
		hc.weibi="-";;
		hc.sell="-";
		hc.buy="-";
				
}else{
	

	    stockWebDetail.amountOfmoney    =  String.valueOf(MathTools.div(hq163_base.TCAP,100000000.00,2))+"亿";//总市值
		stockWebDetail.volume = String.valueOf(df.format(hq163_base.PE));//String.valueOf(MathTools.div(node1.get("VOLUME").asDouble(),100000000.00,2))+"亿";//成交额
		stockWebDetail.dayReturnPercent = fmt.format(hq163_base.HS);
		ObjectNode hb = Json.newObject();
		//public String otherItem;
	
		
		hc.weibi=fmt.format(hq163_base.WB);
		hc.sell="-";
		hc.buy="-";
	 
}					
					
					
					
					double wc=hq163.bidvol1+hq163.bidvol2+hq163.bidvol3+hq163.bidvol4+hq163.bidvol5
							  -hq163.askvol1-hq163.askvol2-hq163.askvol3-hq163.askvol4-hq163.askvol5;
				    hc.weicha=	String.valueOf(wc/10000)+"万"             ;
					sell.s1=hq163.ask1+","+hq163.askvol1;
					sell.s2=hq163.ask2+","+hq163.askvol2;
					sell.s3=hq163.ask3+","+hq163.askvol3;
					sell.s4=hq163.ask4+","+hq163.askvol4;
					sell.s5=hq163.ask5+","+hq163.askvol5;
					
					buy.b1=hq163.bid1+","+hq163.bidvol1;
					buy.b2=hq163.bid2+","+hq163.bidvol2;
					buy.b3=hq163.bid3+","+hq163.bidvol3;
					buy.b4=hq163.bid4+","+hq163.bidvol4;
					buy.b5=hq163.bid5+","+hq163.bidvol5;
					
					

					String otherItem="14:55:22,20.3,1212"  ;
					//PortfioList.find.where().eq("stock_code", stockCode).findRowCount();
				//	int countA=PortfioList.find.where().eq("stock_code", stockCode).findRowCount();
					int countA=0;
					
					String sqlstr="select sum(stock_price)/count(stock_price) from portfio_list p  where  p.stock_code=:stockCode";
					SqlRow list = Ebean.createSqlQuery(sqlstr).setParameter("stockCode", stockCode).findUnique();
					
					anly.analystNum =String.valueOf(countA);	
						String a =list.getString("sum(stock_price)/count(stock_price)");
					   if(a==null)
						anly.avaCost="0";
					   else
					    anly.avaCost =df.format(Double.parseDouble(a));
					
					//simpleCode="%"+simpleCode+"%";
		//   stockList=Stock.find.where().like("simple_name", stockCode).findList();		
		
			sb1.append(",\"sellItem\":[\"1,").append(sell.s1).append("\",")
			  .append("\"2,").append(sell.s2).append("\",")
			  .append("\"3,").append(sell.s3).append("\",")
			  .append("\"4,").append(sell.s4).append("\",")
			  .append("\"5,").append(sell.s5).append("\"]");
			sb2.append(",\"buyInItem\":[\"1,").append(buy.b1).append("\",")
			.append("\"2,").append(buy.b2).append("\",")
			.append("\"3,").append(buy.b3).append("\",")
			.append("\"4,").append(buy.b4).append("\",")
			.append("\"5,").append(buy.b5).append("\"]");
			sb3.append(",\"otherItem\":").append("[\"").append(otherItem).append("\"]");
			resultRtn.business.put("handicap", hc);
		/*	resultRtn.business.put("sellItem", "[]");
			resultRtn.business.put("buyInItem", sb2); 
			resultRtn.business.put("otherItem", sb3);*/
			resultRtn.business.put("analyst", anly);
			resultRtn.business.put("stockWebDetail", stockWebDetail);
	   
			//resultRtn.business.put("stockWebDetail", sb);
			String aa =Json.toJson(resultRtn).toString();
			sb.append(aa.substring(0, aa.length()-2))
			   .append(sb1)
			   .append(sb2)
			   .append(sb3)
			   .append("}}");
			strapp.append(callback).append("(")
		     
		       .append(sb)
		       .append(")");
			//callback+"("+Json.toJson(resultRtn)+")";
			 
		}catch(Exception ex){
			resultRtn.msg = "server error";
			resultRtn.errCode = -1;
			strapp.append(ex);
			RedisAPI.returnResource(pool, j);
		}
		
		try {
			 RedisAPI.returnResource(pool, j);
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
		return ok(strapp.toString());
	}
	
	public static Result stockIndex(String mid) {
		 NumberFormat fmt = NumberFormat.getPercentInstance();
			fmt.setMaximumFractionDigits(2);//最多两位百分小数，如25.23%
			java.text.DecimalFormat   df=new   java.text.DecimalFormat("#0.00"); 
		 JedisPool pool = RedisAPI.getPool();  
        j=  pool.getResource();  
		
		ResultRtn resultRtn = new ResultRtn();
	        ObjectMapper mapper = new ObjectMapper();
	    	List<StockIndex>	listStock=new ArrayList<StockIndex>();	  
		try {
			
			HqTransfor.HQ163 hq163_sh=StockMarkets.GetOneHq("Z000001");
			HqTransfor.HQ163 hq163_sz=StockMarkets.GetOneHq("Z399001");
			HqTransfor.HQ163 hq163_cy=StockMarkets.GetOneHq("Z399006");
			HqTransfor.HQ163 hq163_zxb=StockMarkets.GetOneHq("Z399005");
			HqTransfor.HQ163 hq163_300=StockMarkets.GetOneHq("Z399300");
			HqTransfor.HQ163 hq163_50=StockMarkets.GetOneHq("Z399968");

//			JsonNode node = mapper.readTree(j.get("1A0001"));
//			JsonNode node2 = mapper.readTree(j.get("2A01"));//行情商代码 2A01  股票表代码 2A01 返回取数为0.2A01
//			JsonNode node3 = mapper.readTree(j.get("399006"));
			//System.out.println("stock list:"+new java.util.Date());
           resultRtn.msg = "ok";
           resultRtn.errCode = 0;

           
           StockIndex  stockWebDetail=new StockIndex();
			        stockWebDetail.indexName = "上证指数";
					stockWebDetail.increase   =  fmt.format(hq163_sh.percent);
				
					if(	hq163_sh.updown>0)
						stockWebDetail.upOrDown   = true;
					else
						stockWebDetail.upOrDown   = false;
					
					stockWebDetail.upOrDownVal = df.format(hq163_sh.updown); 
					stockWebDetail.indexType=1;
					stockWebDetail.indexVal = hq163_sh.price; 
					stockWebDetail.indexCode ="Z000001";
					
		 StockIndex  stockWebDetail2=new StockIndex();
			        stockWebDetail2.indexName = "深证指数";
					stockWebDetail2.increase   =  fmt.format(hq163_sz.percent);
				
					if(	hq163_sz.updown>0)
						stockWebDetail2.upOrDown   = true;
					else
						stockWebDetail2.upOrDown   = false;
					
					stockWebDetail2.upOrDownVal = df.format(hq163_sz.updown); 
					stockWebDetail2.indexType=1;
					stockWebDetail2.indexVal = hq163_sz.price; 
					stockWebDetail2.indexCode ="Z399001";
					
		 StockIndex  stockWebDetail3=new StockIndex();
				        stockWebDetail3.indexName = "创业板指";
						stockWebDetail3.increase   = fmt.format(hq163_cy.percent);
					
						if(	hq163_cy.updown>0)
							stockWebDetail3.upOrDown   = true;
						else
							stockWebDetail3.upOrDown   = false;
						
						stockWebDetail3.upOrDownVal = df.format(hq163_cy.updown); 
						stockWebDetail3.indexType=1;
						stockWebDetail3.indexVal = hq163_cy.price; 
						stockWebDetail3.indexCode ="Z399006";
		   
		//zxb		
		  StockIndex  stockWebDetail_zxb=new StockIndex();
		  stockWebDetail_zxb.indexName = "中小板指";
		  stockWebDetail_zxb.increase   = fmt.format(hq163_zxb.percent);
						
							if(	hq163_cy.updown>0)
								stockWebDetail_zxb.upOrDown   = true;
							else
								stockWebDetail_zxb.upOrDown   = false;
							
							stockWebDetail_zxb.upOrDownVal = df.format(hq163_zxb.updown); 
							stockWebDetail_zxb.indexType=1;
							stockWebDetail_zxb.indexVal = hq163_zxb.price; 
							stockWebDetail_zxb.indexCode ="Z399005";
							
	      StockIndex  stockWebDetail300=new StockIndex();
	      stockWebDetail300.indexName = "300指数";
	      stockWebDetail300.increase   = fmt.format(hq163_300.percent);
							
								if(	hq163_cy.updown>0)
									stockWebDetail300.upOrDown   = true;
								else
									stockWebDetail300.upOrDown   = false;
								
								stockWebDetail300.upOrDownVal = df.format(hq163_300.updown); 
								stockWebDetail300.indexType=1;
								stockWebDetail300.indexVal = hq163_300.price; 
								stockWebDetail300.indexCode ="Z399300";
								
			StockIndex  stockWebDetail50=new StockIndex();
			stockWebDetail50.indexName = "50指数";
			stockWebDetail50.increase   = fmt.format(hq163_50.percent);
							
								if(	hq163_cy.updown>0)
									stockWebDetail50.upOrDown   = true;
								else
									stockWebDetail50.upOrDown   = false;
								
								stockWebDetail50.upOrDownVal = df.format(hq163_50.updown); 
								stockWebDetail50.indexType=1;
								stockWebDetail50.indexVal = hq163_50.price; 
								stockWebDetail50.indexCode ="Z399968";
			 
			listStock.add(stockWebDetail);
			listStock.add(stockWebDetail2);
			listStock.add(stockWebDetail3);
			listStock.add(stockWebDetail_zxb);
			listStock.add(stockWebDetail300);
			listStock.add(stockWebDetail50);
			
			
			
			
			resultRtn.business.put("stockIndex", listStock);
		     
			  RedisAPI.returnResource(pool, j);
			  return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
		}catch(Exception ex){
			System.out.println("stockIndex" + ex);
			resultRtn.msg = "server error";
			resultRtn.errCode = -1;
			RedisAPI.returnResource(pool, j);
			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
		}
		
	}
	
	
	
	
	public static Result stockIndex_old(String mid) {
		 JedisPool pool = RedisAPI.getPool();  
       j=  pool.getResource();  
		
		ResultRtn resultRtn = new ResultRtn();
	        ObjectMapper mapper = new ObjectMapper();
	    	List<StockIndex>	listStock=new ArrayList<StockIndex>();	  
		try {

			JsonNode node = mapper.readTree(j.get("1A0001"));
			JsonNode node2 = mapper.readTree(j.get("2A01"));//行情商代码 2A01  股票表代码 2A01 返回取数为0.2A01
			JsonNode node3 = mapper.readTree(j.get("399006"));
			//System.out.println("stock list:"+new java.util.Date());
          resultRtn.msg = "ok";
          resultRtn.errCode = 0;

          
          StockIndex  stockWebDetail=new StockIndex();
			        stockWebDetail.indexName = "上证指数";
					stockWebDetail.increase   =  node.get("rise").asText();
					if(node.get("upDowns").asDouble()>0)
						stockWebDetail.upOrDown   = true;
					else
						stockWebDetail.upOrDown   = false;
					
					stockWebDetail.upOrDownVal = node.get("upDowns").asText();  
					stockWebDetail.indexType=1;
					stockWebDetail.indexVal = node.get("current").asDouble();
					stockWebDetail.indexCode ="1A0001";
		   StockIndex  stockWebDetail2=new StockIndex();
			        stockWebDetail2.indexName = "深圳指数";
					stockWebDetail2.increase   =  node2.get("rise").asText();
					if(node2.get("upDowns").asDouble()>0)
						stockWebDetail2.upOrDown   = true;
					else
						stockWebDetail2.upOrDown   = false;
					
					stockWebDetail2.upOrDownVal = node2.get("upDowns").asText();  
					stockWebDetail2.indexType=1;
					stockWebDetail2.indexVal = node2.get("current").asDouble();
					stockWebDetail2.indexCode ="2A01";
					
			 StockIndex  stockWebDetail3=new StockIndex();
				        stockWebDetail3.indexName = "创业板指";
						stockWebDetail3.increase   =  node3.get("rise").asText();
						if(node3.get("upDowns").asDouble()>0)
							stockWebDetail3.upOrDown   = true;
						else
							stockWebDetail3.upOrDown   = false;
						
						stockWebDetail3.upOrDownVal = node3.get("upDowns").asText();  
						stockWebDetail3.indexType=1;
						stockWebDetail3.indexVal = node3.get("current").asDouble();			
						stockWebDetail3.indexCode ="399006";
			listStock.add(stockWebDetail);
			listStock.add(stockWebDetail2);
			listStock.add(stockWebDetail3);
			resultRtn.business.put("stockIndex", listStock);
		     
			  RedisAPI.returnResource(pool, j);
			  return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
		}catch(Exception ex){
			System.out.println("stockIndex" + ex);
			resultRtn.msg = "server error";
			resultRtn.errCode = -1;
			RedisAPI.returnResource(pool, j);
			return ok(Json.toJson(resultRtn).toString().replaceAll("null", "\"\""));
		}
		
	}
	
	public static double computerAVG() {
		
		
		return 0.0;
	}
	
	public static float paraseFloatWithChineseUnit(String string) {
		try{
			float ret = Float.parseFloat(string.substring(0, string.length()-1));//兼容性？
			String unit = string.substring(string.length()-1, string.length());
//			if("万".equals(unit)){
//				ret *= 10000;
//			}
			if("亿".equals(unit)){
				ret *= 10000;
			}
			return ret;
		}catch(Exception e){
			//System.out.println("paraseFloatWithChineseUnit:"+string);
			//e.printStackTrace();
			return 0;
		}
	}
		
}
