package com.shuyun.sbd.utils.jdbc;

import java.sql.*;

/**
 * Component: 保存点示例
 * Description:
 * Date: 16/12/17
 *
 * @author yue.zhang
 */
public class SavePointDemo {

    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/seckill?allowMultiQueries=true";
    private final static String USER = "root";
    private final static String PASS = "root";

    public static void main(String [] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            String sql = "select timestr,order_sn from order_seq";
            rs = stmt.executeQuery(sql);
            System.out.println("List result set for reference....");
            printRs(rs);

            Savepoint savepoint1 = conn.setSavepoint("ROWS_DELETED_1");
            System.out.println("Deleting row....");
            sql = "DELETE FROM order_seq where timestr = 2016121701";
            stmt.executeUpdate(sql);
            conn.rollback(savepoint1);

            Savepoint savepoint2 = conn.setSavepoint("ROWS_DELETED_2");
            System.out.println("Deleting row....");
            sql = "DELETE FROM order_seq where timestr = 2016121702";
            stmt.executeUpdate(sql);

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
