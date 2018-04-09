package utils.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.CallableSql;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

public class WorkDayTools {
public static	ConnectionPool conPool1=null;
	
    WorkDayTools() {
//		ConnectionParam conParam1 = new ConnectionParam();
//		conParam1.setDriver("com.mysql.jdbc.Driver");
//		conParam1.setUrl("jdbc:mysql://localhost:3306/bstockdev");
//		conParam1.setUser("root");
//		conParam1.setPassword("");
//	   conPool1 = new ConnectionPool(conParam1);
//	   try {
//		conPool1.createPool();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
    
 public static  String getBeforeTradeDay(Date bgnTime ){
	         
	  Calendar cal=Calendar.getInstance();  
      cal.setTime(bgnTime);   
      SimpleDateFormat   sf   =   new   SimpleDateFormat( "yyyy-MM-dd ");   
      String str_date= sf.format(bgnTime);
      
  	
    try{
    	
    //	int ret1 = Ebean.createSqlUpdate("update simulate_stock set beiyong1=-1 where id = " + s.id).execute();
		
    	// str_date="2015-09-04";
		String sqlstr="SELECT * FROM workday w where '"+ str_date + "' between w.startdate and w.enddate";

        List<SqlRow> list = Ebean.createSqlQuery(sqlstr).findList();
	    
		if(list ==null||list.isEmpty()){
			return "19900101"; 
		}
		
        
		
  		return list.get(0).getString("beforeday");
    }catch(Exception e){
    	return "19900101"; 
    }

	 
    }
 
 
 
 
 public static  Date get_Befor_Or_After_Date(Date bgnTime,int times ){
     
	 Calendar cal=Calendar.getInstance();  
     cal.setTime(bgnTime);   
     cal.add(cal.DAY_OF_YEAR, times);
     return  cal.getTime();
	 
   }
 
 public static boolean isSameDay(Date day1, Date day2) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String ds1 = sdf.format(day1);
	    String ds2 = sdf.format(day2);
	    if (ds1.equals(ds2)) {
	        return true;
	    } else {
	        return false;
	    }
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//getBeforeTradeDay("2015-09-04");
	    System.out.println("---");
	}

	
	public static String getBeforeTradeDay1(String baseDate,int count){
		
		 java.text.SimpleDateFormat sf=new java.text.SimpleDateFormat ("yyyy-MM-dd");
		try {
			
			if(WorkDayTools.isWorkEndDay(sf.parse(baseDate))||WorkDayTools.isHoliday(sf.parse(baseDate))){
			  	baseDate= WorkDayTools.calculateDate(baseDate,-1,"yyyy-MM-dd");
			    return 	getBeforeTradeDay1( baseDate,count);
				
				
			}else{
				if(count==0)
					return baseDate;
				else{
					baseDate= WorkDayTools.calculateDate(baseDate,-1,"yyyy-MM-dd");
					return 	getBeforeTradeDay1( baseDate,count-1);
				}
			}
			
			
			
			
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		 return baseDate;
		
	}
	
	
	
	 public static Boolean isWorkEndDay(Date bgnTime){
		 try{
		  Calendar cal=Calendar.getInstance();  
          cal.setTime(bgnTime);   
          if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){  
        	  return true;
          }
          
		 }catch(Exception e){
			 
			 return false;
		 }
          return false;
	 }  
	
	
	 public static Boolean isHoliday(Date bgnTime) {
		  Calendar cal=Calendar.getInstance();  
          cal.setTime(bgnTime);   
          SimpleDateFormat   sf   =   new   SimpleDateFormat( "yyyy-MM-dd ");   
          String str_date= sf.format(bgnTime);
          
          Boolean rs=false;
      	
        try{
        	
        //	int ret1 = Ebean.createSqlUpdate("update simulate_stock set beiyong1=-1 where id = " + s.id).execute();
    		
        	// str_date="2015-09-04";
    		String sqlstr="SELECT * FROM workday w where '"+ str_date + "' between w.startdate and w.enddate";
    		rs = !Ebean.createSqlQuery(sqlstr).findList().isEmpty();
    	    
    		
      		return rs;
        }catch(Exception e){
        	return false;
        }
        
        
            
	 } 
	 
	 public static String HolidayReturn(Date bgnTime) throws Exception{
		  Calendar cal=Calendar.getInstance();  
          cal.setTime(bgnTime);   
         SimpleDateFormat   sf   =   new   SimpleDateFormat( "yyyy-MM-dd ");   
         String str_date= sf.format(bgnTime);
         
         ResultSet rs;
     	
         try {
       	
 			Connection conn= conPool1.getConnection();
     		PreparedStatement psmt1 = conn.prepareStatement("SELECT * FROM workday w where '"+ str_date + "' between w.startdate and w.enddate");  
     		rs = psmt1.executeQuery();
     	   
          
     	   if(rs.next()) {  
              
              System.out.println("/t" + rs.getString("beforeday"));
              return rs.getString("beforeday");
           }
           
           }catch(Exception e){
           	System.out.println("--"+e);
           }
        
        return str_date;
	 } 
	 
	 
	 
	 
	 

	 /*
		 */
		public static String calculateDate(String baseDate,int add,String fmt){
			String retDate = baseDate;
			try{
				Calendar cal = Calendar.getInstance();
				if(baseDate!=null&&baseDate.trim().length()>0){
					cal.set(Integer.parseInt(baseDate.substring(0, 4)), Integer.parseInt(baseDate.substring(5, 7))-1, Integer.parseInt(baseDate.substring(8, 10)));
				}
				cal.add(Calendar.DAY_OF_MONTH, add);
				retDate = new SimpleDateFormat(fmt).format(cal.getTime());
			}catch (Exception e){
				System.out.println("test"+e);
			}
			return retDate;
			
		}
		
		
		
		public static String getBeforeDay(String baseDate,int add){
		
			 java.text.SimpleDateFormat sf=new java.text.SimpleDateFormat ("yyyy-MM-dd");
	    	 
			try {
				baseDate= WorkDayTools.calculateDate(baseDate,-1,"yyyy-MM-dd");
				while(WorkDayTools.isWorkEndDay(sf.parse(baseDate)))
					baseDate= WorkDayTools.calculateDate(baseDate,-1,"yyyy-MM-dd");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			return baseDate;
			
		}
		
		
		
		 /**  
	     * 计算两个日期之间相差的天数  
	     * @param smdate 较小的时间 
	     * @param bdate  较大的时间 
	     * @return 相差天数 
	     * @throws ParseException  
	     */    
	    public static int daysBetween(Date smdate,Date bdate)     
	    {    
	    	try{
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));           
	    }catch(Exception e){
	    	return 0;
	    	
	    }    
	    	
	    }
	    
	    
	    public static String getMonthFirst()     
	    {
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 	  
		    Calendar   cal_1=Calendar.getInstance();//获取当前日期 
	       // cal_1.add(Calendar.MONTH, -1);
	        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	        String firstDay = format.format(cal_1.getTime());
	        System.out.println("-----1------month firstDay:"+firstDay);
		    return firstDay;
		    
	    }		
	    
	    
	    public static String getWeekFirst()     
	    {
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 	  
		    Calendar   cal_1=Calendar.getInstance();//获取当前日期 
	       // cal_1.add(Calendar.we, -1);
	        cal_1.set(Calendar.DAY_OF_WEEK,2);//设置为1号,当前日期既为本月第一天 
	        String firstDay = format.format(cal_1.getTime());
	        System.out.println("-----1------week firstDay:"+firstDay);
		    return firstDay;
		    
	    }	
	    
	    
	
}
