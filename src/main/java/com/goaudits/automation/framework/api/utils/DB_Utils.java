package com.goaudits.automation.framework.api.utils;

import com.goaudits.automation.framework.api.databaseConn.DB_Client;
import com.goaudits.automation.framework.api.fileHandlers.PropertyFile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class DB_Utils {
    private static PropertyFile configFile;
    private static Connection conn;
    private static DB_Client db_client;
    private static String mysql_stg_hostname, mysql_stg_dbname, mysql_stg_username, mysql_stg_password;
    private static String roach_stg_hostname, roach_stg_dbname, roach_stg_username, roach_stg_password;
    private static String roach_stg_lms_hostname, roach_stg_lms_dbname, roach_stg_lms_username, roach_stg_lms_password;
    private static String roach_stg_finance_hostname, roach_stg_finance_dbname, roach_stg_finance_username, roach_stg_finance_password;
    private static String redis_stg_hostname;
    private static Gson gson;

    static {
        init();
    }

    public static void init() {
        try {
            configFile = new PropertyFile(Utils.getWorkingDirectory() + "/test-classes/goaudits_config.properties");
            mysql_stg_hostname = configFile.getProperty("mysql.stg.hostname");
            mysql_stg_dbname = configFile.getProperty("mysql.stg.dbName");
            mysql_stg_username = configFile.getProperty("mysql.stg.userName");
            mysql_stg_password = configFile.getProperty("mysql.stg.password");


            db_client = new DB_Client();
            gson = new Gson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMysql_stg_hostname() {
        return mysql_stg_hostname;
    }

    public static String getMysql_stg_dbname() {
        return mysql_stg_dbname;
    }

    public static String getMysql_stg_username() {
        return mysql_stg_username;
    }

    public String getMysql_stg_password() {
        return mysql_stg_password;
    }


    public static ResultSet executeQuery(Connection connection, String query) throws SQLException {
        ResultSet resultSet = db_client.executeQuery(connection, query);
        return resultSet;
    }

    public static Integer executeUpdate(Connection connection, String query) throws SQLException {
        Integer result = db_client.executeUpdate(connection, query);
        connection.close();
        return result;
    }

    public static Integer executeInsert(Connection connection, String query) throws SQLException {
        Integer result = db_client.executeInsert(connection, query);
        connection.close();
        return result;
    }

    public static Boolean executeDelete(Connection connection, String query) throws SQLException {
        Boolean result = db_client.executeDelete(connection, query);
        connection.close();
        return result;
    }

    public static Connection getMysql_connection() throws SQLException, ClassNotFoundException {
        conn = db_client.connectDB.connectDB("stg_mysql", mysql_stg_hostname, mysql_stg_dbname, mysql_stg_username, mysql_stg_password);
        return conn;
    }



    public static Connection getMysql_connection(boolean connected) throws SQLException, ClassNotFoundException {
        if (connected)
            return conn;
        else
            return getMysql_connection();
    }



    public static void closeConnection() throws SQLException {
        conn.close();
    }

    public static List<Map<String, String>> executeGetListMap(Connection connection, String query) throws SQLException {
    ResultSet resultSet = db_client.executeQuery(connection, query);
    ResultSetMetaData metaData = resultSet.getMetaData();
    List<String> columns = new ArrayList<>(metaData.getColumnCount());
    for(int i = 1; i <= metaData.getColumnCount(); i++){
        columns.add(metaData.getColumnName(i));
    }
    List<Map<String,String>> data = new ArrayList<>();
    while(resultSet.next()){
        Map<String,String> row = new HashMap<>(columns.size());
        for(String col : columns) {
            row.put(col, resultSet.getString(col));
        }
        data.add(row);
    }

    return data;
    }

    /**
     * ExecuteQuery and give a list of pojo class passed
     *
     * @param connection
     * @param query
     * @param mClass pojo class
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> executeQuery(Connection connection, String query, Class<T> mClass) throws Exception {
    ResultSet resultSet = db_client.executeQuery(connection, query);
    ResultSetMetaData metaData = resultSet.getMetaData();
        List<String> columns = new ArrayList<>(metaData.getColumnCount());
    for(int i = 1; i <= metaData.getColumnCount(); i++){
        columns.add(metaData.getColumnName(i));
    }
        List<JsonObject> data = new ArrayList<>();
        while(resultSet.next()){
            JsonObject jsonObject = new JsonObject();
            for(String columnLabel : columns) {
                jsonObject.addProperty(columnLabel, resultSet.getString(columnLabel));
            }data.add(jsonObject);
        }
        log.info("we got this from DB: "+data);
        return GsonFactory.parseList(data, mClass);
    }
}
