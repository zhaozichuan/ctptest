package utils.tools;  
  
import java.sql.Connection;  
import java.sql.DatabaseMetaData;  
import java.sql.Driver;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.Enumeration;  
import java.util.Vector;  
  
public class ConnectionPool {  
  
    private ConnectionParam param;  
  
    private String testTable = ""; 
  
    private Vector connections = null; 
  
    public void setParam(ConnectionParam param) {  
        this.param = param;  
    }  
  
    public ConnectionParam getParam() {  
        return param;  
    }  
  
    
    public ConnectionPool(ConnectionParam param) {  
        this.param = param;  
    }  
  
   
  
    public String getTestTable() {  
        return this.testTable;  
    }  
  
    
  
    public void setTestTable(String testTable) {  
        this.testTable = testTable;  
    }  
  
  
    public synchronized void createPool() throws Exception {  
  
        if (connections != null) {  
            return; 
        }  
  
        Driver driver = (Driver) (Class.forName(this.param.getDriver())  
                .newInstance());  
        DriverManager.registerDriver(driver); 
        connections = new Vector();  
  
        createConnections(this.param.getMinConnection());  
        System.out.println("");  
  
    }  
  
   
  
    private void createConnections(int numConnections) throws SQLException {  
  
        for (int x = 0; x < numConnections; x++) {  
          
  
            if (this.param.getMaxConnection() > 0  
                    && this.connections.size() >= this.param.getMaxConnection()) {  
                break;  
            }  
  
            try {  
                connections.addElement(new PooledConnection(newConnection()));  
            } catch (SQLException e) {  
                System.out.println("" + e.getMessage());  
                throw new SQLException();  
            }  
  
            System.out.println("");  
        }  
  
    }  
  
  
    private Connection newConnection() throws SQLException {  
  
        Connection conn = DriverManager.getConnection(this.param.getUrl(),  
                this.param.getUser(), this.param.getPassword());  
  
  
        if (connections.size() == 0) {  
  
            DatabaseMetaData metaData = conn.getMetaData();  
            int driverMaxConnections = metaData.getMaxConnections();  
  
         
  
            if (driverMaxConnections > 0  
                    && this.param.getMaxConnection() > driverMaxConnections) {  
                this.param.setMaxConnection(driverMaxConnections);  
            }  
        }  
        return conn; 
    }  
  
    
  
    public synchronized Connection getConnection() throws SQLException {  
  
        if (connections == null) {  
            return null;
        }  
  
        Connection conn = getFreeConnection(); 
  
        while (conn == null) {  
            wait(250);  
            conn = getFreeConnection(); 
        }  
  
        return conn;
    }  
  
  
  
    private Connection getFreeConnection() throws SQLException {  
  
        Connection conn = findFreeConnection();  
        if (conn == null) {  
            createConnections(this.param.getIncrementalConnections());  
            conn = findFreeConnection();  
            if (conn == null) {  
                return null;  
            }  
        }  
        return conn;  
  
    }  
  
    
  
    private Connection findFreeConnection() throws SQLException {  
  
        Connection conn = null;  
        PooledConnection pConn = null;  
        Enumeration enumerate = connections.elements();  
        while (enumerate.hasMoreElements()) {  
            pConn = (PooledConnection) enumerate.nextElement();  
            if (!pConn.isBusy()) {  
                conn = pConn.getConnection();  
                pConn.setBusy(true);  
                if (!testConnection(conn)) {  
                    try {  
                        conn = newConnection();  
                    } catch (SQLException e) {  
                        System.out.println("" + e.getMessage());  
                        return null;  
                    }  
                    pConn.setConnection(conn);  
                }  
                break; 
            }  
        }  
  
        return conn;
  
    }  
  
 
  
    private boolean testConnection(Connection conn) {  
  
        try {  
  
            if (testTable.equals("")) {  
               
                conn.setAutoCommit(true);  
            } else {  
               
                Statement stmt = conn.createStatement();  
                stmt.execute("select count(*) from " + testTable);  
            }  
  
        } catch (SQLException e) {  
            closeConnection(conn);  
            return false;  
        }  
        return true;  
  
    }  
  
    
  
    public void returnConnection(Connection conn) {  
  
  
        if (connections == null) {  
            System.out.println(" null");  
            return;  
        }  
  
        PooledConnection pConn = null;  
        Enumeration enumerate = connections.elements();  
        while (enumerate.hasMoreElements()) {  
            pConn = (PooledConnection) enumerate.nextElement();  
            if (conn == pConn.getConnection()) {  
                pConn.setBusy(false);  
                break;  
            }  
  
        }  
  
    }  
  
   
  
    public synchronized void refreshConnections() throws SQLException {  
  
        if (connections == null) {  
            System.out.println("null !");  
            return;  
        }  
  
        PooledConnection pConn = null;  
        Enumeration enumerate = connections.elements();  
        while (enumerate.hasMoreElements()) {  
            pConn = (PooledConnection) enumerate.nextElement();  
            if (pConn.isBusy()) {  
                wait(5000); 
            }  
  
            closeConnection(pConn.getConnection());  
            pConn.setConnection(newConnection());  
            pConn.setBusy(false);  
        }  
  
    }  
  
  
    public synchronized void closeConnectionPool() throws SQLException {  
  
        if (connections == null) {  
            System.out.println("null");  
            return;  
        }  
        PooledConnection pConn = null;  
        Enumeration enumerate = connections.elements();  
        while (enumerate.hasMoreElements()) {  
            pConn = (PooledConnection) enumerate.nextElement();  
            if (pConn.isBusy()) {  
                wait(5000); 
            }  
            closeConnection(pConn.getConnection());  
            connections.removeElement(pConn);  
        }  
  
        connections = null;  
    }  
  
 
  
    private void closeConnection(Connection conn) {  
        try {  
            conn.close();  
        } catch (SQLException e) {  
            System.out.println("" + e.getMessage());  
        }  
    }  
  
   
  
    private void wait(int mSeconds) {  
        try {  
            Thread.sleep(mSeconds);  
        } catch (InterruptedException e) {  
        }  
    }  
  
     
  
    class PooledConnection {  
  
        Connection connection = null;
  
        boolean busy = false;
  
        public PooledConnection(Connection connection) {  
            this.connection = connection;  
        }  
  
        public Connection getConnection() {  
            return connection;  
        }  
  
        public void setConnection(Connection connection) {  
            this.connection = connection;  
        }  
  
        public boolean isBusy() {  
            return busy;  
        }  
  
        public void setBusy(boolean busy) {  
            this.busy = busy;  
        }  
  
    }  
}