package repository;

import JDBC.JDBCConnect;
import entity.User;
import util.DateTimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffRepository {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public User getUser(String username, String password){
        User user = null;
        conn = (new JDBCConnect()).JDBCConnector();
        String sql = "SELECT * FROM `user` WHERE `username` = ? and `password` = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                System.out.println(1);
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setStaffId(rs.getInt("staff_id"));
                user.setActive(rs.getBoolean("is_active"));
                user.setManager(rs.getBoolean("is_manager"));
//                user.setCreatedAt(new DateTimeUtil().getDate(rs.getString("create_at")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
