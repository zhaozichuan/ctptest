package utils;


import redis.clients.jedis.Jedis;
import utils.redis.RedisAPI;

public class HqTransfor {


	public static class MoneyFlow {
		public String symbol="";
		public String r0_in="0";               
		public String r0_out="0";              
		public String r0="0";                  
		public String r1_in="0";               
		public String r1_out="0";              
		public String r1="0";                  
		public String r2_in="0";               
		public String r2_out="0";              
		public String r2="0";                  
		public String r3_in="0";               
		public String r3_out="0";              
		public String r3="0";                  
		public String curr_capital="0";        
		public String name="";                
		public String trade="0";               
		public String changeratio="0";         
		public String volume="0";              
		public String turnover="0";            
		public String r0x_ratio="0";           
		public String opendate="0";            
		public String ticktime="0";            
		public String netamount="0";           

	}
	
	public static class HQ163 {
	    public int id;
	    public String code="";
	    public String type="";
	    public String status="";
	    public String  symbol="";
	    public String  update="";
		public String  name="";
		public String  arrow="";
		public String  time="";
		public String  share="";
	     
	    public double percent=0;
	    public double high=0;
	    public double open=0;
	    public double price=0;
	    public double low=0;
	    public double updown=0;
	    public double yestclose=0;
	    
	    public long  volume=0; 
	    public long turnover=0;
	    public long askvol1=0;
	    public long askvol2=0;
	    public long askvol3=0;
	    public long askvol4=0;
	    public long askvol5=0;
	    public long bidvol1=0;
	    public long bidvol2=0;
	    public long bidvol3=0;
	    public long bidvol4=0;
	    public long bidvol5=0;
	    
	    public long askvol6=0;
	    public long askvol7=0;
	    public long askvol8=0;
	    public long askvol9=0;
	    public long askvol10=0;
	    public long bidvol6=0;
	    public long bidvol7=0;
	    public long bidvol8=0;
	    public long bidvol9=0;
	    public long bidvol10=0;
	    
	    
	    public double bid1=0;
	    public double bid2=0;
	    public double bid3=0;
	    public double bid4=0;
	    public double bid5=0;
	    public double ask1=0;
	    public double ask2=0;
	    public double ask3=0;
	    public double ask4=0;
	    public double ask5=0;

	    
	    public double bid6=0;
	    public double bid7=0;
	    public double bid8=0;
	    public double bid9=0;
	    public double bid10=0;
	    public double ask6=0;
	    public double ask7=0;
	    public double ask8=0;
	    public double ask9=0;
	    public double ask10=0;
	    //dfcf add
	    public int  tradedate=0;
	    public int  tradetime=0;
	    public double Close=0;
	    public String InstrumentStatus="";
	    public long  TotalBidQty=0;
	    public long  TotalOfferQty=0;
	    public double  WeightedAvgBidPx=0;
	    
	    //public int
	    //dfcf add end
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
	
		public String weibi;  
		public String weicha;
		public String waipan; 
		public String neipan;
		
	}
	
	
	
	public static class HQ163_base{
		
		public	HQ163_base(){


			 NO=0.0;
			 SYMBOL="-";
			 NAME="-";
			 PRICE=0.0;
			 PERCENT=0.0;
			 UPDOWN=0.0;
			 FIVE_MINUTE=0.0;
			 OPEN=0.0;
			 YESTCLOSE=0.0;
			 HIGH=0.0;
			 LOW=0.0;
			 VOLUME=0.0;
			 TURNOVER=0.0;
			 HS=0.0;
			 LB=0.0;
			 WB=0.0;
			 ZF=0.0;
			 PE=0.0;
			 MCAP=0.0;
			 TCAP=0.0;
			 MFSUM=0.0;
			 SNAME="-";
			 CODE="-";


			 no="-";
			 zhenfu="-";			
			
		}
		
		
		public double NO;
		public String SYMBOL;
		public String NAME;
		public double PRICE;
		public double PERCENT;
		public double UPDOWN;
		public double FIVE_MINUTE;
		public double OPEN;
		public double YESTCLOSE;
		public double HIGH;
		public double LOW;
		public double VOLUME;
		public double TURNOVER;
		public double HS;
		public double LB;
		public double WB;
		public double ZF;
		public double PE;
		public double MCAP;
		public double TCAP;
		public double MFSUM;
		public String SNAME;
		public String CODE;
		
		
		public String no;
		public String zhenfu;
		
		
		
	}
	
	
	  
	  public static void hq163PutValue_10Level_redis(Jedis redis,int type,int level,HqTransfor.HQ163 hq163,double price,long vol) {
		  
		  
		    //Jedis redis = null;
		//try {
				
			//redis = RedisClientPool.jedisPool.getResource();
		 // RedisAPI.hset("1212121", "ask1", "111");
		 // RedisAPI.set("1212121",  "111");
		 // type=1;
		     if(type==0) //卖
			 {
				switch ( level )//档位
				{
				 case 1: 
					 RedisAPI.hset(hq163.code, "ask1", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol1", String.valueOf(vol));
					 break;
				 
				 case 2: 
					 RedisAPI.hset(hq163.code, "ask2", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol2", String.valueOf(vol));
					 break;
				 
				 case 3: 
					 RedisAPI.hset(hq163.code, "ask3", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol3", String.valueOf(vol));
					 break;
					 
				 case 4: 
					 RedisAPI.hset(hq163.code, "ask4", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol4", String.valueOf(vol));
					 break;
			     
				 case 5: 
					 RedisAPI.hset(hq163.code, "ask5", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol5", String.valueOf(vol));
					 break;
					 
				 case 6: 
					 RedisAPI.hset(hq163.code, "ask6", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol6", String.valueOf(vol));
					 break;
			    
				 case 7: 
					 RedisAPI.hset(hq163.code, "ask7", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol7", String.valueOf(vol));
					 break;
				
				 case 8: 
					 RedisAPI.hset(hq163.code, "ask8", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol8", String.valueOf(vol));
					 break;
				
				 case 9: 
					 RedisAPI.hset(hq163.code, "ask9", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol9", String.valueOf(vol));
					 break;
				
				 case 10: 
					 RedisAPI.hset(hq163.code, "ask10", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "askvol10", String.valueOf(vol));
					 break;
					 
				 case -1: 
					 RedisAPI.hset(hq163.code, "bid1", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol1", String.valueOf(vol));
					 break;
				
				 case -2: 
					 RedisAPI.hset(hq163.code, "bid2", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol2", String.valueOf(vol));
					 break;
				
				 case -3: 
					 RedisAPI.hset(hq163.code, "bid3", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol3", String.valueOf(vol));
					 break;
				
				 case -4: 
					 RedisAPI.hset(hq163.code, "bid4", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol4", String.valueOf(vol));
					 break;
					 
				 case -5: 
					 RedisAPI.hset(hq163.code, "bid5", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol5", String.valueOf(vol));
					 break;
					
				 case -6: 
					 RedisAPI.hset(hq163.code, "bid6", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol6", String.valueOf(vol));
					 break;
					 
				 case -7: 
					 RedisAPI.hset(hq163.code, "bid7", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol7", String.valueOf(vol));
					 break;
				
				 case -8: 
					 RedisAPI.hset(hq163.code, "bid8", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol8", String.valueOf(vol));
					 break;
					 
				 case -9: 
					 RedisAPI.hset(hq163.code, "bid9", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol9", String.valueOf(vol));
					 break;
					 
				 case -10: 
					 RedisAPI.hset(hq163.code, "bid10", String.valueOf(price));
					 RedisAPI.hset(hq163.code, "bidvol10", String.valueOf(vol));
					 break;
					 
//				 case 2: hq163.ask3 = price;    hq163.askvol3 = vol;     break;
//
//				 case 3: hq163.ask4 = price;    hq163.askvol4 = vol;     break;
//				 case 4: hq163.ask5 = price;    hq163.askvol5 = vol;     break;
//				 case 5: hq163.ask6 = price;    hq163.askvol6 = vol;     break;
//				 case 6: hq163.ask7 = price;    hq163.askvol7 = vol;     break;
//				 case 7: hq163.ask8 = price;    hq163.askvol8 = vol;     break;
//				 case 8: hq163.ask9 = price;    hq163.askvol9 = vol;     break;
//				 case 9: hq163.ask10 = price;   hq163.askvol10 = vol;     break;

						
				 default :
					 System.out.println("---default");
				       break;
				       
				}
			
			 
				
				
//			}else// if(type==2) //买
//			{
//				switch ( level )//档位
//				{
//				 case -1: 
//					 redis.hset(hq163.code, "bid1", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol1", String.valueOf(vol));
//					 break;
//				
//				 case -2: 
//					 redis.hset(hq163.code, "bid2", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol2", String.valueOf(vol));
//					 break;
//				
//				 case -3: 
//					 redis.hset(hq163.code, "bid3", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol3", String.valueOf(vol));
//					 break;
//				
//				 case -4: 
//					 redis.hset(hq163.code, "bid4", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol4", String.valueOf(vol));
//					 break;
//					 
//				 case -5: 
//					 redis.hset(hq163.code, "bid5", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol5", String.valueOf(vol));
//					 break;
//					
//				 case -6: 
//					 redis.hset(hq163.code, "bid6", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol6", String.valueOf(vol));
//					 break;
//					 
//				 case -7: 
//					 redis.hset(hq163.code, "bid7", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol7", String.valueOf(vol));
//					 break;
//				
//				 case -8: 
//					 redis.hset(hq163.code, "bid8", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol8", String.valueOf(vol));
//					 break;
//					 
//				 case -9: 
//					 redis.hset(hq163.code, "bid9", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol9", String.valueOf(vol));
//					 break;
//					 
//				 case -10: 
//					 redis.hset(hq163.code, "bid10", String.valueOf(price));
//					 redis.hset(hq163.code, "bidvol10", String.valueOf(vol));
//					 break;
//						
//				 default :
//				       break;
//				}
						
				
			}
			  
//		   } catch (Exception e) {
//				// 销毁对象
//				RedisClientPool.jedisPool.returnBrokenResource(redis);
//			  // e.printStackTrace();
//		   } finally {
//				// 还原到连接池
//			   if(redis!=null)
//				RedisClientPool.jedisPool.returnResource(redis);
//			   else
//				   RedisClientPool.jedisPool.returnBrokenResource(redis);
//				
//			}	
		  
		  
		  }
	  
	  
	  
	  public static void hq163PutValue_10Level(int type,int level,HqTransfor.HQ163 hq163,double price,long vol) {
		if(type==0) //卖
		{
			switch ( level )//档位
			{
			 case 0: hq163.ask1 = price;    hq163.askvol1 = vol;     break;
			 case 1: hq163.ask2 = price;    hq163.askvol2 = vol;     break;
			 case 2: hq163.ask3 = price;    hq163.askvol3 = vol;     break;
			 case 3: hq163.ask4 = price;    hq163.askvol4 = vol;     break;
			 case 4: hq163.ask5 = price;    hq163.askvol5 = vol;     break;
			 case 5: hq163.ask6 = price;    hq163.askvol6 = vol;     break;
			 case 6: hq163.ask7 = price;    hq163.askvol7 = vol;     break;
			 case 7: hq163.ask8 = price;    hq163.askvol8 = vol;     break;
			 case 8: hq163.ask9 = price;    hq163.askvol9 = vol;     break;
			 case 9: hq163.ask10 = price;   hq163.askvol10 = vol;     break;

					
			 default :
			       break;
			}
					
			
		}else// if(type==2) //买
		{
			switch ( level )//档位
			{
			 case 0: hq163.bid1 = price;    hq163.bidvol1 = vol;     break;
			 case 1: hq163.bid2 = price;    hq163.bidvol2 = vol;     break;
			 case 2: hq163.bid3 = price;    hq163.bidvol3 = vol;     break;
			 case 3: hq163.bid4 = price;    hq163.bidvol4 = vol;     break;
			 case 4: hq163.bid5 = price;    hq163.bidvol5 = vol;     break;
			 case 5: hq163.bid6 = price;    hq163.bidvol6 = vol;     break;
			 case 6: hq163.bid7 = price;    hq163.bidvol7 = vol;     break;
			 case 7: hq163.bid8 = price;    hq163.bidvol8 = vol;     break;
			 case 8: hq163.bid9 = price;    hq163.bidvol9 = vol;     break;
			 case 9: hq163.bid10 = price;    hq163.bidvol10 = vol;     break;

					
			 default :
			       break;
			}
					
			
		}
		  
	
	  
	  
	  }
/*
 <field index="1" name="Date" desc="交易日" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="int"/>
		<field index="2" name="Time" desc="交易时间" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="int"/>
		<field index="4" name="PreClose" desc="昨收盘价" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="5" name="Open" desc="开盘价" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="6" name="New" desc="最新价" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="7" name="High" desc="最高价" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="8" name="Low" desc="最低价" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="9" name="TotalVolumeTrade" desc="成交总量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="long"/>
		<field index="35" name="TotalValueTrade" desc="成交总金额" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="5" adaptType="double"/>
		<field index="36" name="NumTrades" desc="成交笔数" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="long"/>
		<field index="58" name="Close" desc="收盘价" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="80" name="InstrumentStatus" desc="交易状态" codec="com.em.mdsserver.protocol.codec.AsciiString" decimalPlaces="0" />
		<field index="81" name="TotalBidQty" desc="委托买入总量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="long"/>
		<field index="82" name="TotalOfferQty" desc="委托卖出总量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="long"/>
		<field index="83" name="WeightedAvgBidPx" desc="加权平均委买价格" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="84" name="AltWeightedAvgBidPx" desc="债券加权平均委买价格" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="85" name="WeightedAvgOfferPx" desc="加权平均委卖价格" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="86" name="AltWeightedAvgOfferPx" desc="债券加权平均委卖价格" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>
		<field index="87" name="IOPV" desc="ETF净值估值" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="float"/>

		<field index="258" name="EtfBuyNumber" desc="ETF申购笔数" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="long"/>
		<field index="259" name="EtfBuyAmount" desc="ETF申购数量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="long"/>
		<field index="260" name="EtfBuyMoney" desc="ETF申购金额" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="5" adaptType="double"/>
		<field index="261" name="EtfSellNumber" desc="ETF赎回笔数" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="long"/>
		<field index="262" name="EtfSellAmount" desc="ETF赎回数量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="double"/>
		<field index="263" name="EtfSellMoney" desc="ETF赎回金额" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="5" adaptType="double"/>

		<field index="88" name="YieldToMaturity" desc="债券到期收益率" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="4" adaptType="double"/>

		<field index="264" name="TotalWarrantExecQty" desc="权证执行的总数量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="double"/>
		<field index="265" name="WarLowerPx" desc="权证跌停价格" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="double"/>
		<field index="266" name="WarUpperPx" desc="权证涨停价格" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="double"/>

		<field index="89" name="WithdrawBuyNumber" desc="买入撤单笔数" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="long"/>
		<field index="90" name="WithdrawBuyAmount" desc="买入撤单数量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="double"/>
		<field index="91" name="WithdrawBuyMoney" desc="买入撤单金额" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="5" adaptType="double"/>
		<field index="92" name="WithdrawSellNumber" desc="卖出撤单笔数" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="long"/>
		<field index="93" name="WithdrawSellAmount" desc="卖出撤单数量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="double"/>
		<field index="94" name="WithdrawSellMoney" desc="卖出撤单金额" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="5" adaptType="double"/>
		<field index="267" name="TotalBidNumber" desc="买入总笔数" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="long"/>
		<field index="268" name="TotalOfferNumber" desc="卖出总笔数" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="long"/>
		<field index="269" name="BidTradeMaxDuration" desc="买入委托成交最大等待时间" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="int"/>
		<field index="270" name="OfferTradeMaxDuration" desc="卖出委托成交最大等待时间" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="0" adaptType="int"/>

		<field index="11" name="VirtualBidQty1" desc="虚拟买一量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="long"/>
		<field index="13" name="VirtualAskQty1" desc="虚拟卖一量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="long"/>
		<field index="41" name="VirtualBidQty2" desc="虚拟买二量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="long"/>
		<field index="43" name="VirtualAskQty2" desc="虚拟卖二量" codec="com.em.mdsserver.protocol.codec.SignedInteger" decimalPlaces="3" adaptType="long"/>

 
 
 
 
 */
	  
	  public static void hq163PutValue_redis(Jedis redis1,int index,HqTransfor.HQ163 hq163,Object var) {
		  System.out.println("idnex:"+index);
		 //s Jedis redis = null;
		//	try {
					
				//redis = RedisClientPool.jedisPool.getResource();
				
				  switch ( index )//档位
					{
					 case 1: 
						 RedisAPI.hset(hq163.code, "tradedate", String.valueOf(var));  break;
					  
					 case 2: 
						 RedisAPI.hset(hq163.code, "tradetime", String.valueOf(var)); break;
					 case 4:      
					     RedisAPI.hset(hq163.code, "yestclose", String.valueOf(var)); break;
					 case 5:     
						 RedisAPI.hset(hq163.code, "open", String.valueOf(var)); break;
					 case 6:
						 RedisAPI.hset(hq163.code, "price", String.valueOf(var)); break;
					 case 7: 
						 RedisAPI.hset(hq163.code, "high", String.valueOf(var)); break;
					 case 8: 
						 RedisAPI.hset(hq163.code, "low", String.valueOf(var)); break;
					 case 9: 
						 RedisAPI.hset(hq163.code, "volume", String.valueOf(var)); break;
					 case 35:
						 RedisAPI.hset(hq163.code, "turnover", String.valueOf(var)); break;
					 case 58:
						 RedisAPI.hset(hq163.code, "Close", String.valueOf(var)); break;
							
					 default :
					       break;
					
				  }
		  
//			  } catch (Exception e) {
//					// 销毁对象
//					RedisClientPool.jedisPool.returnBrokenResource(redis);
//				   e.printStackTrace();
//			   } finally {
//					// 还原到连接池
//				   if(redis!=null)
//					RedisClientPool.jedisPool.returnResource(redis);
//					
//				}	
		  
	  
	  }
	
	  
	  
	  
	  public static void hq163PutValue_money(Jedis redis1,int index,HqTransfor.HQ163 hq163,String time,Object var) {
		  System.out.println("idnex:"+index);
		 //s Jedis redis = null;
		//	try {
					
				//redis = RedisClientPool.jedisPool.getResource();
				String moneyChart ="chart_"+hq163.code;
				
				  switch ( index )//档位
					{
					 case 1: 
						 RedisAPI.hset(moneyChart, time, String.valueOf(var));  break;
					  
					 case 2: 
						 RedisAPI.hset(hq163.code, "tradetime", String.valueOf(var)); break;
					 case 4:      
					     RedisAPI.hset(hq163.code, "yestclose", String.valueOf(var)); break;
					 case 5:     
						 RedisAPI.hset(hq163.code, "open", String.valueOf(var)); break;
					 case 6:
						 RedisAPI.hset(hq163.code, "price", String.valueOf(var)); break;
					 case 7: 
						 RedisAPI.hset(hq163.code, "high", String.valueOf(var)); break;
					 case 8: 
						 RedisAPI.hset(hq163.code, "low", String.valueOf(var)); break;
					 case 9: 
						 RedisAPI.hset(hq163.code, "volume", String.valueOf(var)); break;
					 case 35:
						 RedisAPI.hset(hq163.code, "turnover", String.valueOf(var)); break;
					 case 58:
						 RedisAPI.hset(hq163.code, "Close", String.valueOf(var)); break;
							
					 default :
					       break;
					
				  }
		  
//			  } catch (Exception e) {
//					// 销毁对象
//					RedisClientPool.jedisPool.returnBrokenResource(redis);
//				   e.printStackTrace();
//			   } finally {
//					// 还原到连接池
//				   if(redis!=null)
//					RedisClientPool.jedisPool.returnResource(redis);
//					
//				}	
		  
	  
	  }
	  
	  public static void hq163PutValue(int index,HqTransfor.HQ163 hq163,Object var) {
		  System.out.println("idnex:"+index);
		  switch ( index )//档位
			{
			 case 1: hq163.tradedate= (int) var;      break;
			 case 2: hq163.tradetime = (int) var;     break;
			// case 3: hq163.bid3 = price;    hq163.bidvol3 = vol;     break;
			 case 4: hq163.yestclose = Double.parseDouble(String.valueOf(var)) ;       break;
			 case 5: hq163.open =Double.parseDouble(String.valueOf(var)) ;       break;
			 case 6: hq163.price =Double.parseDouble(String.valueOf(var)) ;       break;
			 case 7: hq163.high = Double.parseDouble(String.valueOf(var)) ;       break;
			 case 8: hq163.low = Double.parseDouble(String.valueOf(var)) ;       break;
			 case 9: hq163.volume  = (long) var;       break;
			 case 35: hq163.turnover  = Double.doubleToLongBits((double)var) ;       break;
			 case 58: hq163.Close  = Double.parseDouble(String.valueOf(var)) ;       break;
					
			 default :
			       break;
			
		  }
		  
	  
	  }
	  
	  
}
