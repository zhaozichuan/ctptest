#include <iostream>
#include <stdio.h>
#include <string>
#include <unordered_map>
#include "CustomMdSpi.h"
#include "CustomTradeSpi.h"
#include "TickToKlineHelper.h"
#include "MySQLManager.h" 
#pragma comment(lib,"libmysql.lib")//连接MysQL需要的库  
using namespace std;

// 链接库
#pragma comment (lib, "thostmduserapi.lib")
#pragma comment (lib, "thosttraderapi.lib")
//#pragma comment (lib, "mysqlclient.lib")
//#pragma comment (lib, "libmysql.lib")
// ---- 全局变量 ---- //
/*
// 公共参数
TThostFtdcBrokerIDType gBrokerID = "9999";                         // 模拟经纪商代码
TThostFtdcInvestorIDType gInvesterID = "";                         // 投资者账户名
TThostFtdcPasswordType gInvesterPassword = "";                     // 投资者密码

// 行情参数
CThostFtdcMdApi *g_pMdUserApi = nullptr;                           // 行情指针
char gMdFrontAddr[] = "tcp://180.168.146.187:10010";               // 模拟行情前置地址
char *g_pInstrumentID[] = {"i1809", "jm1805", "i1805", "j1805"}; // 行情合约代码列表，中、上、大、郑交易所各选一种
int instrumentNum = 4;                                             // 行情合约订阅数量
unordered_map<string, TickToKlineHelper> g_KlineHash;              // 不同合约的k线存储表

// 交易参数
CThostFtdcTraderApi *g_pTradeUserApi = nullptr;                    // 交易指针
char gTradeFrontAddr[] = "tcp://180.168.146.187:10001";            // 模拟交易前置地址
TThostFtdcInstrumentIDType g_pTradeInstrumentID = "j1805";        // 所交易的合约代码
TThostFtdcDirectionType gTradeDirection = THOST_FTDC_D_Sell;       // 买卖方向
TThostFtdcPriceType gLimitPrice = 22735;                           // 交易价格
*/


// 公共参数
TThostFtdcBrokerIDType gBrokerID = "66666";                         // 模拟经纪商代码
TThostFtdcInvestorIDType gInvesterID = "";                         // 投资者账户名
TThostFtdcPasswordType gInvesterPassword = "";                     // 投资者密码

																   // 行情参数
CThostFtdcMdApi *g_pMdUserApi = nullptr;                           // 行情指针
char gMdFrontAddr[] = "tcp://ctp1-md9.citicsf.com:41213";               // 模拟行情前置地址
char *g_pInstrumentID[] = { "i1809", "jm1805", "i1805", "j1805" }; // 行情合约代码列表，中、上、大、郑交易所各选一种

//char *g_pInstrumentID[] = { "600030", "600001", "600028", "600011" };
int instrumentNum = 4;//strlen(g_pInstrumentID[]);                                             // 行情合约订阅数量
unordered_map<string, TickToKlineHelper> g_KlineHash;              // 不同合约的k线存储表

																   // 交易参数
CThostFtdcTraderApi *g_pTradeUserApi = nullptr;                    // 交易指针
char gTradeFrontAddr[] = "tcp://ctp1-front9.citicsf.com:41205";            // 模拟交易前置地址
TThostFtdcInstrumentIDType g_pTradeInstrumentID = "j1805";        // 所交易的合约代码
TThostFtdcDirectionType gTradeDirection = THOST_FTDC_D_Sell;       // 买卖方向
TThostFtdcPriceType gLimitPrice = 22735;                           // 交易价格

MySQLManager *mysql;            // 行情指针





int main()
{
	
	string hosts = "rm-uf65wbvomnp2mufa6o.mysql.rds.aliyuncs.com";
	string name = "root";
	string pass = "Zzc7382788";
	string db = "playebean";
	int port = 3306;
	
	MySQLManager mysql1(hosts, name, pass, db, port);

	mysql1.initConnection();

	mysql = &mysql1;
	getchar();
	// 账号密码
	/*
	
	cout << "请输入账号： ";
	scanf("%s", gInvesterID);
	cout << "请输入密码： ";
	scanf("%s", gInvesterPassword);
	
	*/
	/*
	strcpy(gInvesterID, "107953");
	strcpy(gInvesterPassword, "7382788");
	*/
	strcpy(gInvesterID, "101050839");
	strcpy(gInvesterPassword, "286114");

	
	// 初始化行情线程
	cout << "初始化行情..." << endl;
	g_pMdUserApi = CThostFtdcMdApi::CreateFtdcMdApi();   // 创建行情实例
	CThostFtdcMdSpi *pMdUserSpi = new CustomMdSpi;       // 创建行情回调实例
	g_pMdUserApi->RegisterSpi(pMdUserSpi);               // 注册事件类
	g_pMdUserApi->RegisterFront(gMdFrontAddr);           // 设置行情前置地址
	g_pMdUserApi->Init();                                // 连接运行
	

   /*
	// 初始化交易线程
	cout << "初始化交易..." << endl;
	g_pTradeUserApi = CThostFtdcTraderApi::CreateFtdcTraderApi(); // 创建交易实例
	//TradeChannel tdChnl(g_pTradeUserApi);
	CThostFtdcTraderSpi *pTradeSpi = new CustomTradeSpi;
	//CustomTradeSpi *pTradeSpi = new CustomTradeSpi;               // 创建交易回调实例
	g_pTradeUserApi->RegisterSpi(pTradeSpi);                      // 注册事件类
	g_pTradeUserApi->SubscribePublicTopic(THOST_TERT_RESTART);    // 订阅公共流
	g_pTradeUserApi->SubscribePrivateTopic(THOST_TERT_RESTART);   // 订阅私有流
	g_pTradeUserApi->RegisterFront(gTradeFrontAddr);              // 设置交易前置地址
	g_pTradeUserApi->Init();                                      // 连接运行
	



	g_pTradeUserApi->Join();
	delete pTradeSpi;
	g_pTradeUserApi->Release();
	*/
	// 转换本地k线数据
	//TickToKlineHelper tickToKlineHelper;
	//tickToKlineHelper.KLineFromLocalData("market_data.csv", "K_line_data.csv");
	
	// 等到线程退出
	g_pMdUserApi->Join();
	delete pMdUserSpi;
	g_pMdUserApi->Release();

	getchar();
	return 0;
}