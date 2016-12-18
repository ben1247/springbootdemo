package com.shuyun.sbd.utils.jdbc;

import java.sql.*;

/**
 * Component: 事务demo
 * Description:
 * Date: 16/12/17
 *
 * @author yue.zhang
 */
public class TransactionsDemo {

    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/seckill?allowMultiQueries=true";
    private final static String USER = "root";
    private final static String PASS = "root";

    public static void main(String [] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {

            // register jdbc driver
            Class.forName("com.mysql.jdbc.Driver");

            // open a connection
            System.out.println("Connection to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // set auto commit as false
            conn.setAutoCommit(false);

            System.out.println("Creating statement ...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

            System.out.println("Inserting one row....");
            String sql = "INSERT INTO order_seq VALUES(2016121703,111)";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO order_seq VALUES(2016121700000000000000004,222)";
            stmt.executeUpdate(sql);

            System.out.println("Commiting data here....");
            conn.commit();

            sql = "select timestr,order_sn from order_seq";
            rs = stmt.executeQuery(sql);
            System.out.println("List result set for reference....");
            printRs(rs);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Rolling back data here....");
            try {
                if(conn != null){
                    conn.rollback();
                }
            }catch (SQLException e1){
                e1.printStackTrace();
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

    public static void printRs(ResultSet rs) throws SQLException {
        rs.beforeFirst();
        while (rs.next()){
            int timeStr = rs.getInt("timestr");
            int orderSn = rs.getInt("order_sn");
            System.out.print("timeStr: " + timeStr + "  order_sn: " + orderSn);
            System.out.println();
        }
    }

}
