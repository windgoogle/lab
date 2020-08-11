package com.woo.base.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author huangfeng
 * THS 工具类
 * 检测管理进程启动状态
 * 启动和停止管理进程
 * 检测管理进程通讯端口
 * 维持管理进程和Master的心跳连接
 * 获取管理进程用户和密码
 */
public class THSUtil {

    private static final String JDBC_DRIVER_NAME= "org.sqlite.JDBC";
    private static final String DB_URL_PREFIX = "jdbc:sqlite:";
    private static final String DB_NAME = "db.sqlite3";

    /**
     * 获取THS管理控制台用户名密码
     * 两种方法：
     * 1.查管理控制台数据库
     * 2.调用THS提供的接口
     * 暂用第一种()
     * @param root
     * @return
     */
    public static String[] getAdminUserAndPwd(String root){
        String[] usrAndPwd=new String[2];
        String dburl=DB_URL_PREFIX+root+DB_NAME;
        try{
            Class.forName(JDBC_DRIVER_NAME);
            Connection conn=DriverManager.getConnection(dburl);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select password,username from auth_user where is_superuser=1");
            while (rs.next()) {
                usrAndPwd[0] = rs.getString("password");
                usrAndPwd[1] = rs.getString("username");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return usrAndPwd;
    }


    public static String fetchMngProcPort() {
       String port="";
        //TODO
        return port;
    }


    public static void main(String[] args) {
        String root="E:\\test\\LB_win\\web\\";
        String [] result=getAdminUserAndPwd(root);
        System.out.println(result[1]+","+result[0]);
    }
}
