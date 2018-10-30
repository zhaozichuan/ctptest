#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <algorithm>
#include "CTP_API/ThostFtdcUserApiStruct.h"
#include "TickToKlineHelper.h"
#include <time.h>

const int kDataLineNum = 2 * 60; // 1分钟k线所需行数(末尾不足一分钟的舍去了)
      int lastMin =0;
void TickToKlineHelper::KLineFromLocalData(const std::string &sFilePath, const std::string &dFilePath)
{
	// 先清理残留数据
	m_priceVec.clear();
	m_volumeVec.clear();
	m_KLineDataArray.clear();

	std::cout << "开始转换tick到k线..." << std::endl;
	// 默认读取的tick数据表有4个字段：合约代码、更新时间、最新价、成交量
	std::ifstream srcInFile;
	std::ofstream dstOutFile;
	srcInFile.open(sFilePath, std::ios::in);
	dstOutFile.open(dFilePath, std::ios::out);
	dstOutFile << "开盘价" << ','
		<< "最高价" << ','
		<< "最低价" << ','
		<< "收盘价" << ',' 
		<< "成交量" << std::endl;

	// 一遍解析文件一边计算k线数据，1分钟k线每次读取60 * 2 = 120行数据
	std::string lineStr;
	bool isFirstLine = true;
	while (std::getline(srcInFile, lineStr))
	{
		if (isFirstLine)
		{
			// 跳过第一行表头
			isFirstLine = false;
			continue;
		}
		std::istringstream ss(lineStr);
		std::string fieldStr;
		int count = 4;
		while (std::getline(ss, fieldStr, ','))
		{
			count--;
			if (count == 1)
				m_priceVec.push_back(std::atof(fieldStr.c_str()));
			else if (count == 0)
			{
				m_volumeVec.push_back(std::atoi(fieldStr.c_str()));
				break;
			}
		}

		// 计算k线

		if (m_priceVec.size() == kDataLineNum)
		{
			KLineDataType k_line_data;
			k_line_data.open_price = m_priceVec.front();
			k_line_data.high_price = *std::max_element(m_priceVec.cbegin(), m_priceVec.cend());
			k_line_data.low_price = *std::min_element(m_priceVec.cbegin(), m_priceVec.cend());
			k_line_data.close_price = m_priceVec.back();
			// 成交量的真实的算法是当前区间最后一个成交量减去上去一个区间最后一个成交量
			k_line_data.volume = m_volumeVec.back() - m_volumeVec.front(); 
			//m_KLineDataArray.push_back(k_line_data); // 此处可以存到内存
			
			dstOutFile << k_line_data.open_price << ','
				<< k_line_data.high_price << ','
				<< k_line_data.low_price << ','
				<< k_line_data.close_price << ','
				<< k_line_data.volume << std::endl;

			m_priceVec.clear();
			m_volumeVec.clear();
		}
	}

	srcInFile.close();
	dstOutFile.close();

	std::cout << "k线生成成功" << std::endl;
}

void TickToKlineHelper::KLineFromRealtimeData(CThostFtdcDepthMarketDataField *pDepthMarketData)
{
	m_priceVec.push_back(pDepthMarketData->LastPrice);
	m_volumeVec.push_back(pDepthMarketData->Volume);

	std::vector<double> min_priceVec; // 存储1分钟的价格
	std::vector<int> min_volumeVec; // 存储1分钟的成交量

	min_priceVec.push_back(pDepthMarketData->LastPrice);
	min_volumeVec.push_back(pDepthMarketData->Volume);

	/* //zzc add 一分钟内价格监控
	time_t time_seconds = time(0);
	struct tm now_time;
	localtime_s(&now_time, &time_seconds);

	//if(now_time.tm_sec-)

	std::cout << m_priceVec.size() << std::endl;
	double open_price = m_priceVec.front(); //开盘价
	double high_price = *std::max_element(m_priceVec.cbegin(), m_priceVec.cend());
	double low_price = *std::min_element(m_priceVec.cbegin(), m_priceVec.cend());
	double close_price = m_priceVec.back();

	std::cout << "一分钟开盘" << open_price << std::endl;
	std::cout << "一分钟最高" << high_price << std::endl;
	std::cout << "一分钟最低" << low_price << std::endl;
	std::cout << "一分钟最后" << close_price << std::endl;

	char filePath[100] = { '\0' };
	sprintf(filePath, "%s_min.csv", pDepthMarketData->InstrumentID);
	std::ofstream outFile;
	outFile.open(filePath, std::ios::app); // 文件追加写入
	outFile << pDepthMarketData->UpdateTime << "." << pDepthMarketData->UpdateMillisec << ","
			<< close_price << ","
			<< std::endl;
	outFile.close();






	*///zzc add end

	//add 1 min  hq
	time_t time_seconds = time(0);
	struct tm now_time;
	localtime_s(&now_time, &time_seconds);
	//lastMin = now_time.tm_min;
	
	if (now_time.tm_min > lastMin) {
	
		lastMin = now_time.tm_min;
		printf("---->%d\n", lastMin);
	}else{
	//add 1 min  hq end
	//if (m_priceVec.size() == kDataLineNum){
		KLineDataType k_line_data; 
		k_line_data.open_price = min_priceVec.front();
		k_line_data.high_price = *std::max_element(min_priceVec.cbegin(), min_priceVec.cend());
		k_line_data.low_price = *std::min_element(min_priceVec.cbegin(), min_priceVec.cend());
		k_line_data.close_price = m_priceVec.back();
		// 成交量的真实的算法是当前区间最后一个成交量减去上去一个区间最后一个成交量
		k_line_data.volume = m_volumeVec.back() - m_volumeVec.front();
		k_line_data.date_time = time_seconds;
		m_KLineDataArray.push_back(k_line_data); // 此处可以存到内存


		char filePath[100] = { '\0' };
		sprintf(filePath, "%k_market_data.csv", pDepthMarketData->InstrumentID);
		std::ofstream outFile;
		outFile.open(filePath, std::ios::app); // 文件追加写入 
		outFile << pDepthMarketData->InstrumentID << ","
			    << pDepthMarketData->UpdateTime << "." << pDepthMarketData->UpdateMillisec << ","
			    << k_line_data.close_price << "," << asctime(gmtime(&k_line_data.date_time))
			    << std::endl;

		min_priceVec.clear();
		min_volumeVec.clear();
	}


}
