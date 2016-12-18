package com.shuyun.sbd.utils.jdbc;

import java.sql.*;

/**
 * Component:
 * Description:
 * Date: 16/12/17
 *
 * @author yue.zhang
 */
public class StatementDemo {

    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/seckill?allowMultiQueries=true";
    private final static String USER = "root";
    private final static String PASS = "root";

    public static void main(String [] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {

            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql = "select timestr,order_sn from order_seq";

            rs = stmt.executeQuery(sql);


            while (rs.next()){
                int timeStr = rs.getInt("timestr");
                int orderSn = rs.getInt("order_sn");
                System.out.print("timeStr: " + timeStr + "  order_sn: " + orderSn);
                System.out.println();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            try {
                if(stmt != null){
                    stmt.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

}
