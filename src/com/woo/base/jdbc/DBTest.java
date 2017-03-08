package com.woo.base.jdbc;

import java.sql.*;

/**
 * Created by huangfeng on 2017/1/16.
 */
public class DBTest {

    public static void main(String[] args) throws Exception{
       Connection conn=connect();
     //  String ddl="CREATE TABLE test (name varchar2(10))";
       Statement stmt= conn.createStatement();
       //int r=stmt.executeUpdate(ddl);
        String insert="insert into test values ('zehd ce ')";
        stmt.execute(insert);

        String quert="select name from test";

        ResultSet rs =stmt.executeQuery(quert);
       while(rs.next()){
          System.out.println(rs.getString("name"));
       }
        conn.close();

    }



    public static Connection connect() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@168.1.50.20:1522:orcl";
        String username = "twns";
        String password = "twns";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
