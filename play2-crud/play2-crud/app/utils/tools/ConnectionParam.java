package utils.tools;  
  
import java.io.Serializable;  
  
/** 
 * @author niudongjie.pt ��ݿ����ӳز��� 
 */  
public class ConnectionParam implements Serializable {  
  
    /** 
     *  
     */  
    private static final long serialVersionUID = 1L;  
  
    private String driver; 
    private String url; 
    private String user;  
    private String password; 
    private int minConnection=5; 
    private int maxConnection=100; 
    private long timeoutValue=10000; 
    private long waitTime=5;
    private int incrementalConnections=1;
      
    
    
    public String getDriver() {  
        return driver;  
    }  
  
    public void setDriver(String driver) {  
        this.driver = driver;  
    }  
  
    public String getUrl() {  
        return url;  
    }  
  
    public void setUrl(String url) {  
        this.url = url;  
    }  
  
    public String getUser() {  
        return user;  
    }  
  
    public void setUser(String user) {  
        this.user = user;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    public int getMinConnection() {  
        return minConnection;  
    }  
  
    public void setMinConnection(int minConnection) {  
        this.minConnection = minConnection;  
    }  
  
    public int getMaxConnection() {  
        return maxConnection;  
    }  
  
    public void setMaxConnection(int maxConnection) {  
        this.maxConnection = maxConnection;  
    }  
  
    public long getTimeoutValue() {  
        return timeoutValue;  
    }  
  
    public void setTimeoutValue(long timeoutValue) {  
        this.timeoutValue = timeoutValue;  
    }  
  
    public long getWaitTime() {  
        return waitTime;  
    }  
  
    public void setWaitTime(long waitTime) {  
        this.waitTime = waitTime;  
    }  
  
    public void setIncrementalConnections(int incrementalConnections) {  
        this.incrementalConnections = incrementalConnections;  
    }  
  
    public int getIncrementalConnections() {  
        return incrementalConnections;  
    }  
  
} 