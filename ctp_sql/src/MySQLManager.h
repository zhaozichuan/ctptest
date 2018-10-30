/*    
* MySQLManager.h    
*    
*    Created on: 8.18, 2012   
*            Author: Terry
*/

#ifndef MYSQLMANAGER_H_    
#define MYSQLMANAGER_H_     
#include <mysql.h>    

#include <string>    
#include <iostream>    
#include <vector>    

#include <string.h>    

using namespace std;    

class MySQLManager    
{    
public:    
        /*    
         * Init MySQL    
         * @param hosts:         Host IP address    
         * @param userName:        Login UserName    
         * @param password:        Login Password    
         * @param dbName:        Database Name    
         * @param port:                Host listen port number    
         */    
        MySQLManager(std::string hosts, std::string userName, std::string password, std::string dbName, unsigned int port);    
        ~MySQLManager();    
        void initConnection();    
        /*    
         * Making query from database    
         * @param mysql:        MySQL Object    
         * @param sql:                Running SQL command    
         */    
        bool runSQLCommand(std::string sql);
        /*
         * ִ�в������
         * @param sql: ִ�е�SQL���
         * @return: ��Ӱ�������  
         */
        unsigned int insert(std::string sql);
        /**    
         * Destroy MySQL object    
         * @param mysql                MySQL object    
         */    
        void destroyConnection();    
        bool getConnectionStatus();    
        vector< vector<string> > getResult();    
protected:    
        void setUserName(std::string userName);    
        void setHosts(std::string hosts);    
        void setPassword(std::string password);    
        void setDBName(std::string dbName);    
        void setPort(unsigned int port);    
private:    
        bool IsConnected;    
        vector< vector<string> > resultList;    
        MYSQL mySQLClient;    
        unsigned int DEFAULTPORT;    
        char * HOSTS;    
        char * USERNAME;    
        char * PASSWORD;    
        char * DBNAME;    
};    

#endif /* MYSQLMANAGER_H_ */