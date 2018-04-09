package thread;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

import models.Stock;
import utils.redis.RedisAPI;
import utils.redis.RedisAPI_K;
import utils.tools.Dbmange;
import utils.tools.httpGetData;


public class GoKfile  {
	static String LOCAL_PATH="D://fileKLine//";
	//static String LOCAL_PATH="//root//fileNew//";
	static String LOCAL_PATH_YAHOO="//root//fileY//";
	
	
	
	
	
	
	
	
	public static String transfor(String stk_code) {
		
		String downstock="";
		
		if(stk_code.startsWith("00")||stk_code.startsWith("30")){
			downstock="1"+stk_code;
		
		}else if(stk_code.startsWith("60")){
			downstock="0"+stk_code;
			 
		}else if(stk_code.startsWith("Z39")||stk_code.startsWith("J15")){
			downstock="1"+stk_code.substring(1,stk_code.length());
		}else if(stk_code.startsWith("Z00")||stk_code.startsWith("J50")){
			downstock="0"+stk_code.substring(1,stk_code.length());
		}
		return downstock;
	}
	
	
	public static int downk_redis(String stk_code,int len){
		
		String responseStr="";
		String fileUrl="http://120.25.13.80:9002/kline1?stkCode="+stk_code+"&len="+len+"&mid=1&kType=300&callback=onKLineRecvData&_=1455425568775";
		//System.out.println("163:"+new Date().getTime());
		try {
			responseStr = httpGetData.getHttpDate("callback=onKLineRecvData&_=1455425568775", fileUrl);
			System.out.println(responseStr);
		} catch (Exception e) {
			// TODO: handle exception
		} 
			
		return 1;
	}
	
	
	
	public static int downfile163(String stk_code,int len) {
		//寰呬笅杞芥枃浠跺湴鍧�
			String date1 = new SimpleDateFormat("yyyyMMdd").format(new Date());
			
			//String downstock=transfor(stk_code);
			
		  String fileUrl="http://120.25.13.80:9003/kline3?stkCode="+stk_code+"&len=240&mid=1&kType="+len+"&callback=onKLineRecvData&_=1455425568775";
		  InputStream in=null;
		  OutputStream out=null;
		  HttpURLConnection conn=null;
		  String fileName=stk_code+"."+len;
		  try {
		   //鍒濆鍖栬繛鎺�
		   URL url=new URL(fileUrl);
		   conn = (HttpURLConnection) url.openConnection();
		   conn.setDoInput(true);
		   conn.setDoOutput(true);

//		   //鑾峰彇鏂囦欢鍚�
//		   String disposition=conn.getHeaderField("Content-Disposition");
//		   if(disposition!=null&&!"".equals(disposition)){
//		    //浠庡ご涓幏鍙栨枃浠跺悕
//		   // fileName=disposition.split(";")[1].split("=")[1].replaceAll("\"","");
//		   }else{
//		    //浠庡湴鍧�涓幏鍙栨枃浠跺悕
//		    fileName=fileUrl.substring(fileUrl.lastIndexOf("/")+1);
//		   }

		   if(fileName!=null&&!"".equals(fileName)){
		    //鏂囦欢鍚嶈В鐮�
		    fileName=URLDecoder.decode(fileName, "utf-8");
		   }else{
		    //濡傛灉鏃犳硶鑾峰彇鏂囦欢鍚嶏紝鍒欓殢鏈虹敓鎴愪竴涓�
		    fileName="file_"+(int)(Math.random()*10);
		   }

		   //璇诲彇鏁版嵁
		   if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
		    byte[] buffer=new byte[2048];
		    in = conn.getInputStream();
		    	
		    //add +++++++++++++++++++++++++++++++++++++++++++++++++  redis 
		    String msg = "";// 保存调用http服务后的响应信息
		    BufferedReader in2 = new BufferedReader(new InputStreamReader(
                    (InputStream) conn.getInputStream(), "UTF-8"));
            msg = in2.readLine();
           // in2.close();
            
            System.out.println("++++"+msg);
            
            RedisAPI_K.set("k"+len+"_"+stk_code, msg);
		    
            //add end ++++++++++++++++++++++++++++++++++++++++++++++++++redis end+++++++++++++++++++
		    
		    
		    
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
	
	
	

	
	
	
 public static void main(String[] args) {
	
	   System.out.println("-->");
	   
	   int i=0, j=0,k=0;
	
		try {
			Dbmange.initDb();// 初始化数据库连接
			Connection conn1 = Dbmange.conPool.getConnection();

			PreparedStatement psmt1 = conn1.prepareStatement("SELECT * FROM stock");

			// psmt1 = conn.prepareStatement("SELECT * FROM user where id=110");

			ResultSet rs = psmt1.executeQuery();
			Dbmange.conPool.returnConnection(conn1);
			rs.beforeFirst();
			while (rs.next()) {

				// if(!WorkDayTools.IstradeTime()){
				// return;
				// }

				// String code1 = "002486.SZ";//test
				String code1 = rs.getString("code");
				String stockName = rs.getString("name");
				//
				i++;
				if (downfile163(code1, 300) == 1) {
					// insertK_day163(tmpstock.code);
					j++;
				}
				
				
				if (downfile163(code1, 200) == 1) {
					// insertK_day163(tmpstock.code);
					k++;
				}
				
				if (downfile163(code1, 100) == 1) {
					// insertK_day163(tmpstock.code);
					k++;
				}

			}
			System.out.println(" total:i=" + i + "   ok:j=" + j);
		} catch (Exception e) {
			System.out.println("err"+e);

		}
	

	   
	   
	  // downk_redis("300036",200);
	   
}
 
 
}

