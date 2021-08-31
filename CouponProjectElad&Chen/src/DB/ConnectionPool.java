package DB;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * This class is produce the communication to the database
 */
public class ConnectionPool {

    private static final int NUM_OF_CONS = 10;
    private static ConnectionPool instance = null;
    private Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        openAllConnections();
    }

    /**
     * checking twice if the instance is null
     * @return instance status
     */
    public static ConnectionPool getInstance() {
        //before locking the critical code...
        if (instance == null) {
            //create the connection pool
            synchronized (ConnectionPool.class) {
                //before creating the code.....
                if (instance == null) {
                    try {
                        instance = new ConnectionPool();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }


    /**
     * this method synchronized the connections before pop and give any
     * @return connection to the server
     * @throws InterruptedException
     */
    public Connection getConnection() throws InterruptedException{
        synchronized (connections){
            if (connections.isEmpty()){
                //wait until we will get a connection back
                connections.wait();

            }
            return connections.pop();
        }
    }

    /**
     * this method synchronized the connections and notify  when there is a free connection
     * @param connection
     */
    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            //notify that we got back a connection from the user...
            connections.notify();
        }
    }

    /*
        this method will open 10 connection in advanced
        @throws SQLException
     */


    /**
     *Perform the process of connecting to the database
     * @throws SQLException
     */
    private void openAllConnections() throws SQLException {
        for (int index = 0; index < NUM_OF_CONS; index += 1) {
            //load the configuration file and update the data for connection
        }
        //make the connection ......
        Connection connection = DriverManager.getConnection(DatabaseManager.url, DatabaseManager.username, DatabaseManager.password);
        connections.push(connection);
    }


    /**
     *Perform the process of unconnecting to the database
     * @throws SQLException
     */
    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONS) {
                connections.wait();
            }
            connections.removeAllElements();
            instance = null;
        }
    }
}
