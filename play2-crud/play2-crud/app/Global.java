import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import org.apache.commons.codec.digest.DigestUtils;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Akka;
import play.libs.Time.CronExpression;
import play.libs.Yaml;
import play.mvc.Action;
import play.mvc.Http.Request;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import thread.StockMarketPriceUpdate;
import utils.redis.RedisAPI;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

////import com.typesafe.plugin.RedisPlugin;

public class Global extends GlobalSettings {

	private static final String KEYWORD = "keyword";
	private static final int STEP = 100;// 3000;

	/*
	 * private static final Injector INJECTOR = createInjector();
	 * 
	 * @Override public <A> A getControllerInstance(Class<A> controllerClass)
	 * throws Exception { return INJECTOR.getInstance(controllerClass); }
	 * 
	 * private static Injector createInjector() { return Guice.createInjector();
	 * }
	 */

	/**
	 * 对请求进行md5加密，暂时先注掉,以便于调试
	 */

	// @Override
	// public Action onRequest(Request request, Method actionMethod) {
	//
	//
	// String keyValue = "";
	// StringBuffer needMD5 = new StringBuffer();
	// TreeMap<String, String[]> parasTreeMap = new TreeMap<String, String[]>();
	// parasTreeMap.putAll(request.queryString());
	// for (String key : parasTreeMap.keySet()) {
	// if ("key".equals(key)) {
	// keyValue = request.getQueryString(key);
	// } else {
	// needMD5.append("&");
	// needMD5.append(key);
	// needMD5.append("=");
	// needMD5.append(request.getQueryString(key));
	// }
	// }
	// needMD5.append("&");
	// needMD5.append(KEYWORD);
	// String needMD5Str = needMD5.toString().replaceFirst("&", "");
	// if (keyValue.equals(DigestUtils.md5Hex(needMD5Str))) {
	// return super.onRequest(request, actionMethod);
	// } else {
	// return null;
	// }
	// }

	@Override
	public Action onRequest(Request request, Method actionMethod) {

		// request.body().asMultipartFormData();

		String keyValue = "";
		StringBuffer needMD5 = new StringBuffer();
		// TreeMap<String, String[]> parasTreeMap = new TreeMap<String,
		// String[]>();
		// parasTreeMap.putAll(request.queryString());
		// for (String key : parasTreeMap.keySet()) {
		// if ("key".equals(key)) {
		// keyValue = request.getQueryString(key);
		// } else {
		// needMD5.append("&");
		// needMD5.append(key);
		// needMD5.append("=");
		// needMD5.append(request.getQueryString(key));
		// }
		// }
		// needMD5.append("&");
		// needMD5.append(KEYWORD);
		// String needMD5Str = needMD5.toString().replaceFirst("&", "");
	System.out.println("|"+request.uri());
		if ("/ycf/upload_avatar1".equals(request.path())||"0".equals(request.getQueryString("mid"))) {
			
			
			return super.onRequest(request, actionMethod);
		}
		
		return super.onRequest(request, actionMethod);
//		2015年8月10日14:27:05 
//		if("0".equals(request.getQueryString("userId"))){
//		
//			String keyvalue = DigestUtils.md5Hex("gongxifacai");
//			if (keyvalue.equalsIgnoreCase(request.getQueryString("keyword"))) 
//			{
//
//				return super.onRequest(request, actionMethod);
//			} 
//			else {
//				return null;
//
//			}
//			
//			
//			  //  return super.onRequest(request, actionMethod);
//		
//		}else{
//			
//			
//			String keyvalue = DigestUtils.md5Hex("keyword");
//			if (keyvalue.equalsIgnoreCase(request.getQueryString("keyword"))) 
//			{
//
//				return super.onRequest(request, actionMethod);
//			} 
//			else {
//				return null;
//
//			}
//
//		}

		// if (keyValue.equals(DigestUtils.md5Hex(needMD5Str))) {
		// return super.onRequest(request, actionMethod);
		// } else {
		// return null;
		// }
		//return super.onRequest(request, actionMethod);

	}

	@Override
	public void onStart(Application app) {
		super.onStart(app);

		Logger.info("global begin:"
				+ new SimpleDateFormat("yyyyMMddHHmmss-SSS").format(new Date()));

		// test this.loadInitialData();
		// test this.loadFakeDataInRedis();
		// this.fetchingRealtimeStockMarketsFromWStock();

		/**
		 * new thread
		 */

		
	//	this.loadStockList();
	//	this.loadStockThread(); // 10秒更新实时行情数据
	//	this.loadStockReal_163();
	//	this.loadStockMarketPriceUpdate();//5秒行情和 图形行情 启动
	//	this.loadStockMarketKLineUpdate();
	//	this.loadStockMarketKLineUpdate_file();
		// this.loadBackTable();
		// strategy
		//this.loadEveryDay();
		// strategy();

		// 实时数据查询
		// this.StockMarketPriceUpdate();
		// K线数据更新
		// this.StockMarketKLineUpdate();
		//this.loadStrategyJingRealTime();//更新策略表净值
	}

	




		
	
	
	
	
	

	
		
	
	

	
	
	
	private void loadStockMarketPriceUpdate() {

		// StockMarketPriceUpdate.cleanHisData();//清理历史数据

		// 线程服务调度方式
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		System.out.println("---->");
		//1分钟图形
		long initialDelay1 = 3;
		long period1 = 60;

		// start

		JedisPool pool = RedisAPI.getPool();
		Jedis j = pool.getResource();

		JsonNode node;
		try {



				
			//1分钟图形
		
			StockMarketPriceUpdate sktask = new StockMarketPriceUpdate("600030");
			// 从现在开始1秒钟之后，每隔10秒钟执行一次job1
			service.scheduleAtFixedRate(sktask, initialDelay1, period1,
					TimeUnit.SECONDS);
		   
		//	grid="";
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// end

	}

	private void loadStockList() {

		long daySpan = 24 * 60 * 60 * 1000;
		System.out.println("About to stocklist task.");

		// 规定的每天时间15:33:30运行
		final SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd '08:45:00'");
		// 首次运行时间
		SimpleDateFormat simFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str_date = sdf.format(new Date());
		// java.util.Date startTime;
		java.util.Date startTime = new Date();
		try {
			startTime = simFmt.parse(str_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Timer timer = new Timer();
		//StockListTask sktask = new StockListTask();
		// timer.schedule(new RemindTask(), seconds*1000);
		// 以每24小时执行一次
		//timer.scheduleAtFixedRate(sktask, startTime, daySpan);

		System.out.println(" stocklist task put in.");
	}

	private void loadBackTable() {

		long daySpan = 24 * 60 * 60 * 1000;
		System.out.println("About to Backtable.");

		// 规定的每天时间15:33:30运行
		final SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd '16:45:00'");
		// 首次运行时间
		SimpleDateFormat simFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str_date = sdf.format(new Date());
		// java.util.Date startTime;
		java.util.Date startTime = new Date();
		try {
			// startTime = simFmt.parse(str_date);
			startTime = sdf.parse(str_date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Timer timer = new Timer();
		//StockRealLineBackUp sktask = new StockRealLineBackUp();
		// timer.schedule(new RemindTask(), seconds*1000);
		// 以每24小时执行一次
		//timer.scheduleAtFixedRate(sktask, startTime, daySpan);

		System.out.println(" stocklist task put in.");
	}

	private void loadEveryDay() {

		long daySpan = 24 * 60 * 60 * 1000;
		System.out.println("About to Backtable.");

		// 规定的每天时间15:33:30运行
		final SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd '20:45:00'");
		final SimpleDateFormat sidf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// 首次运行时间
		String startDate = sdf.format(new Date());
		Date startTime = new Date();
		try {
			startTime = sidf.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// java.util.Date startTime=new Date();

		if (System.currentTimeMillis() > startTime.getTime()) {

			startTime = new Date(startTime.getTime() + daySpan);

		}

		Timer timer = new Timer();
		//EveryDayRask sktask = new EveryDayRask();
		// timer.schedule(new RemindTask(), seconds*1000);
		// 以每24小时执行一次
		//timer.scheduleAtFixedRate(sktask, startTime, daySpan);

		System.out.println(" EveryDayRask put in.");
	}

}
