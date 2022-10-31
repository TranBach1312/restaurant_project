package repository;

import JDBC.JDBCConnect;
import entity.Staff;
import entity.StaffPosition;
import entity.User;
import util.DateTimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StaffRepository {

    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    public static User getUser(String username, String password) {
        User user = null;
        conn = (new JDBCConnect()).JDBCConnector();
        String sql = "SELECT * FROM `user` WHERE `username` = ? and `password` = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            rs = ps.executeQuery();
            if (rs.next()) {
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
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return user;
    }

    public Optional<Staff> getStaffFromUser(User user) {
        Optional<Staff> staffOptional;
        Staff staff = new Staff();
        conn = (new JDBCConnect()).JDBCConnector();
        String sql = "SELECT * FROM `staff` WHERE `id` = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getStaffId());
            rs = ps.executeQuery();
            if (rs.next()) {
                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                staff.setBirthDate(rs.getDate("birth_date"));
                staff.setPhone(rs.getString("phone"));
                staff.setAddress(rs.getString("address"));
                staff.setEmail(rs.getString("email"));
                staff.setStaffPositionId(rs.getInt("staff_position_id"));
                staff.setStartWorkedAt(rs.getDate("started_work_at"));
                staff.setCreateAt(rs.getDate("create_at"));
//                user.setCreatedAt(new DateTimeUtil().getDate(rs.getString("create_at")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        staffOptional = Optional.of(staff);
        return staffOptional;
    }

    public Boolean updateUser(User user) {
        Boolean flag = false;
        String sql = "UPDATE user SET `password` = ?, `is_manager` = ?, `is_active' = ? WHERE `id` = ?`";
        conn = JDBCConnect.JDBCConnector();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setBoolean(2, user.isManager());
            ps.setBoolean(3, user.isActive());
            ps.setInt(4, user.getId());
            if (ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }

        return flag;
    }

    public Boolean updateStaff(Staff staff) {
        Boolean flag = false;
        String sql = "UPDATE staff SET `phone` = ?, `email` = ? WHERE `id` = ?";
        conn = JDBCConnect.JDBCConnector();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, staff.getPhone());
            ps.setString(2, staff.getEmail());
            ps.setInt(3, staff.getId());
            if (ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }

        return flag;
    }
    public static StaffPosition getStaffPosition(int id){
        StaffPosition staffPosition = new StaffPosition();
        conn = (new JDBCConnect()).JDBCConnector();
        String sql = "SELECT * FROM `staff_position` WHERE `id` = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                staffPosition.setId(rs.getInt("id"));
                staffPosition.setName(rs.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return staffPosition;
    }
}
