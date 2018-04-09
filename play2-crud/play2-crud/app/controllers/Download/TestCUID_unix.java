package controllers.Download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.*;
public class TestCUID_unix extends BaseController {
	//static String LOCAL_PATH="D://file//";
	static String LOCAL_PATH="//root//fileNew//";
	static String LOCAL_PATH_YAHOO="//root//fileY//";
	public static Result test22() {
		String pattern = "\\s*@(\\S+):([\\s\\S]+)";

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher("@zzc");

		if(m.find()) {
			//Notification notification = new Notification();
			System.out.println("0000"+m.group(1));
		
		}
		
		return ok("1");
	}
	
	
	
	
	
	public static int downfileYahoo(String stk_code) {
	//待下载文件地址
		String date1 = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		
		String downstock="";
		if(stk_code.startsWith("00")||stk_code.startsWith("30")){
			downstock=stk_code+".sz";
		}else{
			downstock=stk_code+".ss";
		}
	  String fileUrl="http://table.finance.yahoo.com/table.csv?s="+downstock;
	  InputStream in=null;
	  OutputStream out=null;
	  HttpURLConnection conn=null;
	  String fileName=null;
	  try {
	   //初始化连接
	   URL url=new URL(fileUrl);
	   conn = (HttpURLConnection) url.openConnection();
	   conn.setDoInput(true);
	   conn.setDoOutput(true);

	   //获取文件名
	   String disposition=conn.getHeaderField("Content-Disposition");
	   if(disposition!=null&&!"".equals(disposition)){
	    //从头中获取文件名
	    fileName=disposition.split(";")[1].split("=")[1].replaceAll("\"","");
	   }else{
	    //从地址中获取文件名
	    fileName=fileUrl.substring(fileUrl.lastIndexOf("/")+1);
	   }

	   if(fileName!=null&&!"".equals(fileName)){
	    //文件名解码
	    //fileName=URLDecoder.decode(fileName, "utf-8");
	    fileName=stk_code+"csv";
		   
	   }else{
	    //如果无法获取文件名，则随机生成一个
	    fileName="file_"+(int)(Math.random()*10);
	   }

	   //读取数据
	   if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
	    byte[] buffer=new byte[2048];
	    in = conn.getInputStream();
	    out=new FileOutputStream(new File(LOCAL_PATH_YAHOO,fileName));
	    int count=0;
	    int finished=0;
	    int size=conn.getContentLength();
	    while((count=in.read(buffer))!=-1){
	     if(count!=0){
	      out.write(buffer,0,count);
	      finished+=count;
	      //System.out.printf("########################################---->%1$.2f%%\n",(double)finished/size*100);
	     }else{
	      break;
	     }
	    }
	    System.out.println("downfile ok "+ stk_code);
	    return 1;
	   }
	  } catch (MalformedURLException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }finally{
	   try {
	    out.close();
	    in.close();
	    conn.disconnect();
	   } catch (IOException e) {
	    e.printStackTrace();
	   }
	  }
	  return 0;
	}
	
	public static int downfile163(String stk_code) {
		//待下载文件地址
			String date1 = new SimpleDateFormat("yyyyMMdd").format(new Date());
			
			
			String downstock="";
			
			if(stk_code.startsWith("00")||stk_code.startsWith("30")){
				downstock="1"+stk_code;
			}else{
				downstock="0"+stk_code;
			}
			
			
			if("1A0001".equals(stk_code))
				downstock="0000001";
         
			if("2A01".equals(stk_code))
				downstock="1399001";
		
			if("399006".equals(stk_code))
				downstock="1399006";
		
			
			
		  String fileUrl="http://quotes.money.163.com/service/chddata.html?code="+downstock+"&start=19990101&end="+date1+"&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP;";
		  InputStream in=null;
		  OutputStream out=null;
		  HttpURLConnection conn=null;
		  String fileName=null;
		  try {
		   //初始化连接
		   URL url=new URL(fileUrl);
		   conn = (HttpURLConnection) url.openConnection();
		   conn.setDoInput(true);
		   conn.setDoOutput(true);

		   //获取文件名
		   String disposition=conn.getHeaderField("Content-Disposition");
		   if(disposition!=null&&!"".equals(disposition)){
		    //从头中获取文件名
		    fileName=disposition.split(";")[1].split("=")[1].replaceAll("\"","");
		   }else{
		    //从地址中获取文件名
		    fileName=fileUrl.substring(fileUrl.lastIndexOf("/")+1);
		   }

		   if(fileName!=null&&!"".equals(fileName)){
		    //文件名解码
		    fileName=URLDecoder.decode(fileName, "utf-8");
		   }else{
		    //如果无法获取文件名，则随机生成一个
		    fileName="file_"+(int)(Math.random()*10);
		   }

		   //读取数据
		   if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
		    byte[] buffer=new byte[2048];
		    in = conn.getInputStream();
		    out=new FileOutputStream(new File(LOCAL_PATH,fileName));
		    int count=0;
		    int finished=0;
		    int size=conn.getContentLength();
		    while((count=in.read(buffer))!=-1){
		     if(count!=0){
		      out.write(buffer,0,count);
		      finished+=count;
		      //System.out.printf("########################################---->%1$.2f%%\n",(double)finished/size*100);
		     }else{
		      break;
		     }
		    }
		    System.out.println("downfile ok "+ stk_code);
		    return 1;
		   }
		  } catch (MalformedURLException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }finally{
		   try {
		    out.close();
		    in.close();
		    conn.disconnect();
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		  }
		  return 0;
		}
	
	public static Result Test1() {
		ResultRtn resultRtn = new ResultRtn();
		Status status = new Status();
		int i=0, j=0;
		List<Stock> list=    Stock.find.all(); 
		//List<Stock> list=    new ArrayList<Stock>(); 
	//	list.add(e)
		
		for(Stock tmpstock:list){
		  i++;
			if(downfile163(tmpstock.code)==1){
				insertK_day163(tmpstock.code);
				j++;
			}
		}
	//	downfile("600000");
//		insertK_day("600000"); 
		
		//status.status=String.valueOf(StockMarkets.stock_realPrice("600030"));
//		try {
//			aa=httpGetData.getHttpDate("action=60&stockindex=1&MaxCount=1&Grid=600030", "http://42.121.107.194:7780/reqxml");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ArrayList a = new ArrayList();
//		a.add("1");
//		a.add("2");
//		resultRtn.msg=String.valueOf(StockMarkets.stock_realPrice("600030"));
//		
//		Hashtable bb=(Hashtable)StockMarkets.stock_simpleHQ("600030");
//		resultRtn.msg =(String)bb.get("stockName");
//		resultRtn.business.put("test", a);
		
		
		return ok("OK");
	}
	
	
	public static Result Test2(String stkCode) {
		ResultRtn resultRtn = new ResultRtn();
		Status status = new Status();
		int i=0, j=0;
		//List<Stock> list=    Stock.find.all(); 
		//List<Stock> list=    new ArrayList<Stock>(); 
	//	list.add(e)
		
		if(downfile163(stkCode)==1){
	     insertK_day163(stkCode);
		}	
		return ok("OK");
	}
	
	
	  public static String insertK_day_Yahoo(String code) {
		  
		  String Mysql_Driver = "com.mysql.jdbc.Driver";
		  String url= "jdbc:mysql://127.0.0.1:3307/bstockdev";
		  Connection conn = null;
		 
	    	try { 
	    		DailyStockMarket a_k =new DailyStockMarket();
	    		  Class.forName(Mysql_Driver);
				  conn = DriverManager.getConnection(url, "root", "");
	    		
	    		File csv = new File(LOCAL_PATH_YAHOO+code+".csv"); // CSV文件

	    		BufferedReader br = new BufferedReader(new FileReader(csv));

	    		// 读取直到最后一行 
	    		String line = ""; 
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    		br.readLine();
	    		while ((line = br.readLine()) != null) { 
	    		// 把一行数据分割成多个字段 
	    			String[] items =line.split(",");
	    			System.out.println("--"+items[0]);
	    			try {
						a_k.time =sdf.parse(items[0]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						return "-1";
					    
					};
//					try {
//						a_k.time =sdf.parse("2013-12-11");
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					String sql = "insert into bstock.daily_k(symbol,name,time,high,close,low,open,volume,amount)"
//							+ " values(:symbol,:name,:time,:high,:close,:low,:open,:volume,:amount)"; 
					//String name =new String(items[2],"utf8")；
					String sql = "insert into bstockdev.daily_k(symbol,name,time,high,close,low,open,volume,amount)"
							+ " values('"+items[1].substring(1,items[1].length())+"','"+items[2]+"','"+items[0]+"',"+Float.parseFloat(items[4])
							+ ","+Float.parseFloat(items[3])+","+Float.parseFloat(items[5])+","+Float.parseFloat(items[6])+","+Float.parseFloat(items[11])+","+Float.parseFloat(items[12])
							+ ")"; 
			
					
					System.out.println("sql:"+sql);
//			int j=		Ebean.createSqlUpdate(sql).setParameter("symbol",items[1].substring(1,items[2].length()))
//					.setParameter("name",items[2])
//					.setParameter("time",a_k.time)
//					.setParameter("high",Float.parseFloat(items[4]))
//					.setParameter("close",Float.parseFloat(items[3]))
//					.setParameter("low",Float.parseFloat(items[5]))
//					.setParameter("open",Float.parseFloat(items[6]))
//					.setParameter("volume",Float.parseFloat(items[11]))
//					.setParameter("amount",Float.parseFloat(items[12]))
//					.execute();
					
//	    			a_k.symbol ="600030";//items[1].substring(1,items[2].length());
//	    			a_k.name = "aa";//items[2];
//	    			a_k.high =(float) 0.01;//Float.parseFloat(items[4]);
//	    			a_k.close =(float) 0.01;//Float.parseFloat(items[3]);
//	    			a_k.low =(float) 0.01;//Float.parseFloat(items[5]);
//	    			a_k.open =(float) 0.01;//Float.parseFloat(items[6]);
//	    			a_k.volume =(float) 0.01;//Float.parseFloat(items[11]);
//	    			a_k.amount =(float) 0.01;//Float.parseFloat(items[12]);
//	    			a_k.save();			
	    			//StringTokenizer st = new StringTokenizer(line, ",");

//	    		while (st.hasMoreTokens()) { 
//	    		// 每一行的多个字段用TAB隔开表示 
//	    		System.out.print(st.nextToken() + "\t"); 
//	    		} 
	    		
					
					
						  Statement st = conn.createStatement();
						  st.execute(sql);
						 
						  
						 
	    		
	    		} 
	    		
	    		
	    		conn.close();
	    		br.close();

	    		} catch (IOException e) { 
	    		// 捕获File对象生成时的异常 
	    		e.printStackTrace(); 
	    		} catch (Exception e) { 
	    		// 捕获BufferedReader对象关闭时的异常 
	    		e.printStackTrace(); 
	    		} 
	  	  return "0";
	    		} 

 public static String insertK_day163(String code) {
		  
		  String Mysql_Driver = "com.mysql.jdbc.Driver";
		  String url= "jdbc:mysql://127.0.0.1:3307/bstockdev";
		  Connection conn = null;
		 
			if("1A0001".equals(code))
				code="000001";
         
			if("2A01".equals(code))
				code="399001";
		
			if("399006".equals(code))
				code="399006";
		  
		  
		  
	    	try { 
	    		DailyStockMarket a_k =new DailyStockMarket();
	    		  Class.forName(Mysql_Driver);
				  conn = DriverManager.getConnection(url, "root", "");
	    		
	    		File csv = new File(LOCAL_PATH+code+".csv"); // CSV文件

	    		BufferedReader br = new BufferedReader(new FileReader(csv));

	    		// 读取直到最后一行 
	    		String line = ""; 
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    		br.readLine();
	    		while ((line = br.readLine()) != null) { 
	    		// 把一行数据分割成多个字段 
	    			String[] items =line.split(",");
	    			System.out.println("--"+items[0]);
	    			try {
						a_k.time =sdf.parse(items[0]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						return "-1";
					    
					};
					
					if(Float.parseFloat(items[12])==0.00){
						continue;
					}
					
//					try {
//						a_k.time =sdf.parse("2013-12-11");
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					String sql = "insert into bstock.daily_k(symbol,name,time,high,close,low,open,volume,amount)"
//							+ " values(:symbol,:name,:time,:high,:close,:low,:open,:volume,:amount)"; 
					//String name =new String(items[2],"utf8")；
					String sql = "insert into bstockdev.daily_k(symbol,name,time,high,close,low,open,volume,amount)"
							+ " values('"+items[1].substring(1,items[1].length())+"','"+items[2]+"','"+items[0]+"',"+Float.parseFloat(items[4])
							+ ","+Float.parseFloat(items[3])+","+Float.parseFloat(items[5])+","+Float.parseFloat(items[6])+","+Float.parseFloat(items[11])+","+Float.parseFloat(items[12])
							+ ")"; 
			
					
					System.out.println("sql:"+sql);
//			int j=		Ebean.createSqlUpdate(sql).setParameter("symbol",items[1].substring(1,items[2].length()))
//					.setParameter("name",items[2])
//					.setParameter("time",a_k.time)
//					.setParameter("high",Float.parseFloat(items[4]))
//					.setParameter("close",Float.parseFloat(items[3]))
//					.setParameter("low",Float.parseFloat(items[5]))
//					.setParameter("open",Float.parseFloat(items[6]))
//					.setParameter("volume",Float.parseFloat(items[11]))
//					.setParameter("amount",Float.parseFloat(items[12]))
//					.execute();
					
//	    			a_k.symbol ="600030";//items[1].substring(1,items[2].length());
//	    			a_k.name = "aa";//items[2];
//	    			a_k.high =(float) 0.01;//Float.parseFloat(items[4]);
//	    			a_k.close =(float) 0.01;//Float.parseFloat(items[3]);
//	    			a_k.low =(float) 0.01;//Float.parseFloat(items[5]);
//	    			a_k.open =(float) 0.01;//Float.parseFloat(items[6]);
//	    			a_k.volume =(float) 0.01;//Float.parseFloat(items[11]);
//	    			a_k.amount =(float) 0.01;//Float.parseFloat(items[12]);
//	    			a_k.save();			
	    			//StringTokenizer st = new StringTokenizer(line, ",");

//	    		while (st.hasMoreTokens()) { 
//	    		// 每一行的多个字段用TAB隔开表示 
//	    		System.out.print(st.nextToken() + "\t"); 
//	    		} 
	    		
					
					
						  Statement st = conn.createStatement();
						  st.execute(sql);
						 
						  
						 
	    		
	    		} 
	    		
	    		
	    		conn.close();
	    		br.close();

	    		} catch (IOException e) { 
	    		// 捕获File对象生成时的异常 
	    		e.printStackTrace(); 
	    		} catch (Exception e) { 
	    		// 捕获BufferedReader对象关闭时的异常 
	    		e.printStackTrace(); 
	    		} 
	  	  return "0";
	    		} 			
}

