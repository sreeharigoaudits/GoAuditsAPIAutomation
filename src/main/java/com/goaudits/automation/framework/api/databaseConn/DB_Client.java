package com.goaudits.automation.framework.api.databaseConn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Client {
    public ConnectDB connectDB;

    public DB_Client(){
        connectDB = new ConnectDB();
    }

    public ResultSet executeQuery(Connection connection, String sqlQuery) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(sqlQuery);
        return resultSet;
    }

    public Integer executeUpdate(Connection connection, String sqlQuery) throws SQLException {
        Statement st = connection.createStatement();
        int result = st.executeUpdate(sqlQuery);
        return result;
    }

    public Integer executeInsert(Connection connection, String sqlQuery) throws SQLException {
        Statement st = connection.createStatement();
        int result = st.executeUpdate(sqlQuery);
        return result;
    }

    public Boolean executeDelete(Connection connection, String sqlQuery) throws SQLException {
        Statement st = connection.createStatement();
        boolean result = st.execute(sqlQuery);
        return result;
    }

}
