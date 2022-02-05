package com.goaudits.automation.framework.api.databaseConn;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class ConnectDB {
    public ConnectDB() {
    }

    ;

    public Connection connectDB(String dbConnectionType, String hostName, String dbName, String userName, String password) throws SQLException {

        //Loading the required JDBC Driver class
        //Class.forName("com.microsoft.mysql.jdbc.Driver");
        //Class.forName("org.postgresql.Driver");

        Connection conn = null;
        switch (dbConnectionType) {
            case "stg_mysql":
                //Creating a mysql connection to the database
                conn = DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbName, userName, password);
                break;
            case "stg_roach":
            case "stg_roach_lms":
                //creating roach connection to the database
                String url = "jdbc:postgresql://" + hostName + ":26257/" + dbName;
                Properties props = new Properties();
                props.setProperty("user", userName);
                props.setProperty("password", password);
                log.info("POSTGRES URL :" + url + "Username : " + userName + "Password :" + password);
                conn = DriverManager.getConnection(url, props);
                break;
        }
        return conn;
    }

    public Jedis connectRedis(String dbConnectionType, String hostName, int db) {
        Jedis jedis = new Jedis(hostName);
        jedis.select(db);
        return jedis;
    }
}
