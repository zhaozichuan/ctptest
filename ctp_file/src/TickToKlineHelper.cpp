#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <algorithm>
#include "CTP_API/ThostFtdcUserApiStruct.h"
#include "TickToKlineHelper.h"
#include <time.h>

const int kDataLineNum = 2 * 60; // 1����k����������(ĩβ����һ���ӵ���ȥ��)
      int lastMin =0;
void TickToKlineHelper::KLineFromLocalData(const std::string &sFilePath, const std::string &dFilePath)
{
	// �������������
	m_priceVec.clear();
	m_volumeVec.clear();
	m_KLineDataArray.clear();

	std::cout << "��ʼת��tick��k��..." << std::endl;
	// Ĭ�϶�ȡ��tick���ݱ���4���ֶΣ���Լ���롢����ʱ�䡢���¼ۡ��ɽ���
	std::ifstream srcInFile;
	std::ofstream dstOutFile;
	srcInFile.open(sFilePath, std::ios::in);
	dstOutFile.open(dFilePath, std::ios::out);
	dstOutFile << "���̼�" << ','
		<< "��߼�" << ','
		<< "��ͼ�" << ','
		<< "���̼�" << ',' 
		<< "�ɽ���" << std::endl;

	// һ������ļ�һ�߼���k�����ݣ�1����k��ÿ�ζ�ȡ60 * 2 = 120������
	std::string lineStr;
	bool isFirstLine = true;
	while (std::getline(srcInFile, lineStr))
	{
		if (isFirstLine)
		{
			// ������һ�б�ͷ
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

		// ����k��

		if (m_priceVec.size() == kDataLineNum)
		{
			KLineDataType k_line_data;
			k_line_data.open_price = m_priceVec.front();
			k_line_data.high_price = *std::max_element(m_priceVec.cbegin(), m_priceVec.cend());
			k_line_data.low_price = *std::min_element(m_priceVec.cbegin(), m_priceVec.cend());
			k_line_data.close_price = m_priceVec.back();
			// �ɽ�������ʵ���㷨�ǵ�ǰ�������һ���ɽ�����ȥ��ȥһ���������һ���ɽ���
			k_line_data.volume = m_volumeVec.back() - m_volumeVec.front(); 
			//m_KLineDataArray.push_back(k_line_data); // �˴����Դ浽�ڴ�
			
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

	std::cout << "k�����ɳɹ�" << std::endl;
}

void TickToKlineHelper::KLineFromRealtimeData(CThostFtdcDepthMarketDataField *pDepthMarketData)
{
	m_priceVec.push_back(pDepthMarketData->LastPrice);
	m_volumeVec.push_back(pDepthMarketData->Volume);

	std::vector<double> min_priceVec; // �洢1���ӵļ۸�
	std::vector<int> min_volumeVec; // �洢1���ӵĳɽ���

	min_priceVec.push_back(pDepthMarketData->LastPrice);
	min_volumeVec.push_back(pDepthMarketData->Volume);

	/* //zzc add һ�����ڼ۸���
	time_t time_seconds = time(0);
	struct tm now_time;
	localtime_s(&now_time, &time_seconds);

	//if(now_time.tm_sec-)

	std::cout << m_priceVec.size() << std::endl;
	double open_price = m_priceVec.front(); //���̼�
	double high_price = *std::max_element(m_priceVec.cbegin(), m_priceVec.cend());
	double low_price = *std::min_element(m_priceVec.cbegin(), m_priceVec.cend());
	double close_price = m_priceVec.back();

	std::cout << "һ���ӿ���" << open_price << std::endl;
	std::cout << "һ�������" << high_price << std::endl;
	std::cout << "һ�������" << low_price << std::endl;
	std::cout << "һ�������" << close_price << std::endl;

	char filePath[100] = { '\0' };
	sprintf(filePath, "%s_min.csv", pDepthMarketData->InstrumentID);
	std::ofstream outFile;
	outFile.open(filePath, std::ios::app); // �ļ�׷��д��
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
		// �ɽ�������ʵ���㷨�ǵ�ǰ�������һ���ɽ�����ȥ��ȥһ���������һ���ɽ���
		k_line_data.volume = m_volumeVec.back() - m_volumeVec.front();
		k_line_data.date_time = time_seconds;
		m_KLineDataArray.push_back(k_line_data); // �˴����Դ浽�ڴ�


		char filePath[100] = { '\0' };
		sprintf(filePath, "%k_market_data.csv", pDepthMarketData->InstrumentID);
		std::ofstream outFile;
		outFile.open(filePath, std::ios::app); // �ļ�׷��д�� 
		outFile << pDepthMarketData->InstrumentID << ","
			    << pDepthMarketData->UpdateTime << "." << pDepthMarketData->UpdateMillisec << ","
			    << k_line_data.close_price << "," << asctime(gmtime(&k_line_data.date_time))
			    << std::endl;

		min_priceVec.clear();
		min_volumeVec.clear();
	}


}
