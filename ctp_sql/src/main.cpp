#include <iostream>
#include <stdio.h>
#include <string>
#include <unordered_map>
#include "CustomMdSpi.h"
#include "CustomTradeSpi.h"
#include "TickToKlineHelper.h"
#include "MySQLManager.h" 
#pragma comment(lib,"libmysql.lib")//����MysQL��Ҫ�Ŀ�  
using namespace std;

// ���ӿ�
#pragma comment (lib, "thostmduserapi.lib")
#pragma comment (lib, "thosttraderapi.lib")
//#pragma comment (lib, "mysqlclient.lib")
//#pragma comment (lib, "libmysql.lib")
// ---- ȫ�ֱ��� ---- //
/*
// ��������
TThostFtdcBrokerIDType gBrokerID = "9999";                         // ģ�⾭���̴���
TThostFtdcInvestorIDType gInvesterID = "";                         // Ͷ�����˻���
TThostFtdcPasswordType gInvesterPassword = "";                     // Ͷ��������

// �������
CThostFtdcMdApi *g_pMdUserApi = nullptr;                           // ����ָ��
char gMdFrontAddr[] = "tcp://180.168.146.187:10010";               // ģ������ǰ�õ�ַ
char *g_pInstrumentID[] = {"i1809", "jm1805", "i1805", "j1805"}; // �����Լ�����б��С��ϡ���֣��������ѡһ��
int instrumentNum = 4;                                             // �����Լ��������
unordered_map<string, TickToKlineHelper> g_KlineHash;              // ��ͬ��Լ��k�ߴ洢��

// ���ײ���
CThostFtdcTraderApi *g_pTradeUserApi = nullptr;                    // ����ָ��
char gTradeFrontAddr[] = "tcp://180.168.146.187:10001";            // ģ�⽻��ǰ�õ�ַ
TThostFtdcInstrumentIDType g_pTradeInstrumentID = "j1805";        // �����׵ĺ�Լ����
TThostFtdcDirectionType gTradeDirection = THOST_FTDC_D_Sell;       // ��������
TThostFtdcPriceType gLimitPrice = 22735;                           // ���׼۸�
*/


// ��������
TThostFtdcBrokerIDType gBrokerID = "66666";                         // ģ�⾭���̴���
TThostFtdcInvestorIDType gInvesterID = "";                         // Ͷ�����˻���
TThostFtdcPasswordType gInvesterPassword = "";                     // Ͷ��������

																   // �������
CThostFtdcMdApi *g_pMdUserApi = nullptr;                           // ����ָ��
char gMdFrontAddr[] = "tcp://ctp1-md9.citicsf.com:41213";               // ģ������ǰ�õ�ַ
char *g_pInstrumentID[] = { "i1809", "jm1805", "i1805", "j1805" }; // �����Լ�����б��С��ϡ���֣��������ѡһ��

//char *g_pInstrumentID[] = { "600030", "600001", "600028", "600011" };
int instrumentNum = 4;//strlen(g_pInstrumentID[]);                                             // �����Լ��������
unordered_map<string, TickToKlineHelper> g_KlineHash;              // ��ͬ��Լ��k�ߴ洢��

																   // ���ײ���
CThostFtdcTraderApi *g_pTradeUserApi = nullptr;                    // ����ָ��
char gTradeFrontAddr[] = "tcp://ctp1-front9.citicsf.com:41205";            // ģ�⽻��ǰ�õ�ַ
TThostFtdcInstrumentIDType g_pTradeInstrumentID = "j1805";        // �����׵ĺ�Լ����
TThostFtdcDirectionType gTradeDirection = THOST_FTDC_D_Sell;       // ��������
TThostFtdcPriceType gLimitPrice = 22735;                           // ���׼۸�

MySQLManager *mysql;            // ����ָ��





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
	// �˺�����
	/*
	
	cout << "�������˺ţ� ";
	scanf("%s", gInvesterID);
	cout << "���������룺 ";
	scanf("%s", gInvesterPassword);
	
	*/
	/*
	strcpy(gInvesterID, "107953");
	strcpy(gInvesterPassword, "7382788");
	*/
	strcpy(gInvesterID, "101050839");
	strcpy(gInvesterPassword, "286114");

	
	// ��ʼ�������߳�
	cout << "��ʼ������..." << endl;
	g_pMdUserApi = CThostFtdcMdApi::CreateFtdcMdApi();   // ��������ʵ��
	CThostFtdcMdSpi *pMdUserSpi = new CustomMdSpi;       // ��������ص�ʵ��
	g_pMdUserApi->RegisterSpi(pMdUserSpi);               // ע���¼���
	g_pMdUserApi->RegisterFront(gMdFrontAddr);           // ��������ǰ�õ�ַ
	g_pMdUserApi->Init();                                // ��������
	

   /*
	// ��ʼ�������߳�
	cout << "��ʼ������..." << endl;
	g_pTradeUserApi = CThostFtdcTraderApi::CreateFtdcTraderApi(); // ��������ʵ��
	//TradeChannel tdChnl(g_pTradeUserApi);
	CThostFtdcTraderSpi *pTradeSpi = new CustomTradeSpi;
	//CustomTradeSpi *pTradeSpi = new CustomTradeSpi;               // �������׻ص�ʵ��
	g_pTradeUserApi->RegisterSpi(pTradeSpi);                      // ע���¼���
	g_pTradeUserApi->SubscribePublicTopic(THOST_TERT_RESTART);    // ���Ĺ�����
	g_pTradeUserApi->SubscribePrivateTopic(THOST_TERT_RESTART);   // ����˽����
	g_pTradeUserApi->RegisterFront(gTradeFrontAddr);              // ���ý���ǰ�õ�ַ
	g_pTradeUserApi->Init();                                      // ��������
	



	g_pTradeUserApi->Join();
	delete pTradeSpi;
	g_pTradeUserApi->Release();
	*/
	// ת������k������
	//TickToKlineHelper tickToKlineHelper;
	//tickToKlineHelper.KLineFromLocalData("market_data.csv", "K_line_data.csv");
	
	// �ȵ��߳��˳�
	g_pMdUserApi->Join();
	delete pMdUserSpi;
	g_pMdUserApi->Release();

	getchar();
	return 0;
}