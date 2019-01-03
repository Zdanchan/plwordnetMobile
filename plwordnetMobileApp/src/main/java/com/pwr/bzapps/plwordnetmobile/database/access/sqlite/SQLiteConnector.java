package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.File;
import java.sql.*;
import java.util.Collection;

public class SQLiteConnector {

    public static <T extends Entity> T getResultForQuery(String query, Class<T> clazz){
        Connection conn = connectToDatabaseFile();
        Statement statement = null;
        T result = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            result = SQLiteEntityWrapper.wrapResultSetWithEntity(resultSet, clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T extends Entity> Collection<T> getResultListForQuery(String query, Class<T> clazz, int resultLimit){
        Connection conn = connectToDatabaseFile();
        Statement statement = null;
        Collection<T> results = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            results = SQLiteEntityWrapper.wrapResultSetCollectionWithEntity(resultSet, clazz, resultLimit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static <T extends Entity> Collection<T> getResultListForQuery(String query, Class<T> clazz){
        Connection conn = connectToDatabaseFile();
        Statement statement = null;
        Collection<T> results = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            results = SQLiteEntityWrapper.wrapResultSetCollectionWithEntity(resultSet, clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }


    private static Connection connectToDatabaseFile() {
        if(!new File(Settings.getSqliteDbFileLocation()).exists()){
            new File(Settings.getSqliteDbFileLocation()).mkdirs();
        }
        String url = "jdbc:sqlite:" + Settings.getSqliteDbFile();
        Connection conn;
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
            return conn;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void closeConnection(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
