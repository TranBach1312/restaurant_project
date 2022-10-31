package repository;

import JDBC.JDBCConnect;
import com.sun.org.apache.xpath.internal.operations.Or;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {

    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    public static List<Dish> getDishFromRs(ResultSet rs) {
        List<Dish> dishListToken = new ArrayList<>();
        try {
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getDouble("price"));
                dish.setCategoryId(rs.getInt("category_id"));
                Image image = new Image("resources/" + rs.getString("image"));

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                dish.setImage(imageView);
                dish.setTotalSold(rs.getInt("total_sold"));
//                dish.setDescription(rs.getString("description"));
                dish.setAverageCostPrice(rs.getDouble("average_cost_price"));
                dish.setSoldOut(rs.getBoolean("sold_out"));
                dish.setDeleted(rs.getBoolean("deleted"));
                dishListToken.add(dish);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return dishListToken;
    }

    public static ObservableList<Dish> getDishList(DishCategory dishCategory) {
        ObservableList<Dish> dishList = null;
        String sql = "SELECT * FROM `dish` WHERE `category_id` = ?";
        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dishCategory.getId());
            rs = ps.executeQuery();

            List<Dish> dishListToken = getDishFromRs(rs);
            dishList = FXCollections.observableList(dishListToken);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return dishList;
    }

    public static ObservableList<Dish> getDishList(String string) {
        ObservableList<Dish> dishList = null;
        String searchText = "%" + string + "%";
        String sql = "SELECT * FROM `dish` WHERE `name` LIKE ?";
        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sql);
            ps.setString(1, searchText.toString().trim());
            rs = ps.executeQuery();

            List<Dish> dishListToken = getDishFromRs(rs);
            dishList = FXCollections.observableList(dishListToken);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return dishList;
    }

    public static ObservableList<Dish> getDishList() {
        ObservableList<Dish> dishList = null;
        String sql = "SELECT * FROM dish";
        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            List<Dish> dishListToken = getDishFromRs(rs);
            dishList = FXCollections.observableList(dishListToken);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return dishList;
    }

    public static ObservableList<DinnerTable> getDinnerTable() {
        ObservableList<DinnerTable> dinnerTableList = null;
        String sql = "SELECT * FROM dinner_table";
        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            List<DinnerTable> dinnerTableListToken = getDinnerTableFromRs(rs);
            dinnerTableList = FXCollections.observableList(dinnerTableListToken);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return dinnerTableList;
    }

    public static List<DinnerTable> getDinnerTableFromRs(ResultSet rs) {
        List<DinnerTable> dinnerTableListToken = new ArrayList<>();
        try {
            while (rs.next()) {
                DinnerTable dinnerTable = new DinnerTable();
                dinnerTable.setId(rs.getInt("id"));
                dinnerTable.setName(rs.getString("table_name"));
                dinnerTable.setServingQuantity(rs.getInt("serving_quantity"));
                dinnerTable.setInUse(rs.getBoolean("in_use"));
                dinnerTable.setSurcharge(0);
                dinnerTableListToken.add(dinnerTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return dinnerTableListToken;
    }

    public static ObservableList<DishCategory> getDishCategory() {
        ObservableList<DishCategory> dishCategoryList = null;
        String sql = "SELECT * FROM dish_category";
        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            List<DishCategory> dishCategoryListToken = getDishCategoryFromRs(rs);
            dishCategoryList = FXCollections.observableList(dishCategoryListToken);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return dishCategoryList;
    }

    public static List<DishCategory> getDishCategoryFromRs(ResultSet rs) {
        List<DishCategory> dishCategoryListToken = new ArrayList<>();
        try {
            while (rs.next()) {
                DishCategory dishCategory = new DishCategory();
                dishCategory.setId(rs.getInt("id"));
                dishCategory.setName(rs.getString("name"));
//                dishCategory.setDescription(rs.getString("description"));
//                dishCategory.setDeleted(rs.getBoolean("deleted"));
                dishCategoryListToken.add(dishCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return dishCategoryListToken;
    }

    public static boolean insertNewOrderItem(ArrayList<OrderItem> orderItemArrayList, int orderId) {
        String deleteSql = "Delete from `order_item` where `order_id` = ?";
        StringBuilder sql = new StringBuilder("INSERT INTO `order_item`(`order_id`, `dish_id`, `quantity`, `total_money`)" +
                "VALUES (?, ?, ?, ?)");
        boolean flag = false;
        for (int i = 1; i < orderItemArrayList.size(); i++) {
            sql.append(", (?, ?, ?, ?)");
        }
        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(deleteSql);
            ps.setInt(1, orderId);
            ps.executeUpdate();

            ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < (orderItemArrayList.size()) * 4; i += 4) {
                OrderItem orderItem = orderItemArrayList.get((i / 4));
                ps.setInt(i + 1, orderId);
                ps.setInt(i + 2, orderItem.getDish().getId());
                ps.setInt(i + 3, orderItem.getQuantity());
                ps.setDouble(i + 4, orderItem.getTotalMoney());
            }
            if (ps.executeUpdate() > 0) {
                flag = true;
            } else {
                System.out.println("False");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return flag;
    }

    public static boolean insertNewOrder(Order order) {
        Boolean flag = false;
        String sql = "INSERT INTO `order`(`table_id`, `total_discount`, `total_money`, `status`, `update_at`)" +
                "VALUE(?, ?, ?, ?, ?)";
        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getTable().getId());
            ps.setDouble(2, 0.0);
            ps.setDouble(3, order.getTotalMoney());
            ps.setBoolean(4, order.isStatus());
            ps.setTimestamp(5, order.getUpdateAt());
            if (ps.executeUpdate() > 0) {
                System.out.println("Create table success!");
                flag = true;
            } else {
                System.out.println("False");
            }
            ResultSet rsGetId = ps.getGeneratedKeys();
            int generatedKey = 0;
            if (rsGetId.next()) {
                generatedKey = rsGetId.getInt(1);
                order.setId(generatedKey);
            }
            insertNewOrderItem(order.getOrderItems(), order.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    public static boolean updateOrder(Order order) {
        Boolean flag = false;
        String sql = "UPDATE `order` set `table_id` = ?, `total_discount` = ?, `total_money` = ?, `status` = ?, `update_at` = ?" +
                "WHERE id = ?";
        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, order.getTable().getId());
            ps.setDouble(2, 0.0);
            ps.setDouble(3, order.getTotalMoney());
            ps.setBoolean(4, order.isStatus());
            ps.setTimestamp(5, order.getUpdateAt());
            ps.setInt(6, order.getId());
            if (ps.executeUpdate() > 0) {
                flag = true;
            } else {
                System.out.println("False");
            }
            insertNewOrderItem(order.getOrderItems(), order.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    public static Optional<Order> getOrderFromTable(DinnerTable dinnerTable) {
        Optional<Order> orderOptional = Optional.empty();
        String sqlGetOrder = "SELECT * FROM `order` " +
                " WHERE `table_id` = ? and `status` = 0" +
                " LIMIT 1";

        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sqlGetOrder);
            ps.setInt(1, dinnerTable.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTable(dinnerTable);
                order.setOrderItems(getOrderItemFromOrder(order));
                order.setUpdateAt(rs.getTimestamp("update_at"));
                order.setStatus(rs.getBoolean("status"));
                order.setTotalMoney(rs.getDouble("total_money"));
                orderOptional = Optional.of(order);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return orderOptional;
    }

    public static ArrayList<OrderItem> getOrderItemFromOrder(Order order) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM `order_item`" +
                "WHERE `order_id` = ?";
        ResultSet thisRs = null;
        PreparedStatement thisPs = null;
        try {
            thisPs = conn.prepareStatement(sql);
            thisPs.setInt(1, order.getId());
            thisRs = thisPs.executeQuery();
            while (thisRs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(thisRs.getInt("id"));
                orderItem.setDish(getDishById(thisRs.getInt("dish_id")));
                orderItem.setQuantity(thisRs.getInt("quantity"));
                orderItem.setTotalMoney(thisRs.getDouble("total_money"));
                orderItems.add(orderItem);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(thisRs);
            JDBCConnect.closePreparedStatement(thisPs);
        }
        return orderItems;
    }

    public static Dish getDishById(int id) {
        Dish dish = new Dish();
        String sqlGetOrder = "SELECT * FROM `dish`" +
                "WHERE `id` = ?";
        ResultSet thisRs = null;
        PreparedStatement thisPs = null;

        try {
            thisPs = conn.prepareStatement(sqlGetOrder);
            thisPs.setInt(1, id);
            thisRs = thisPs.executeQuery();
            if (thisRs.next()) {
                dish.setId(thisRs.getInt("id"));
                dish.setName(thisRs.getString("name"));
                dish.setPrice(thisRs.getDouble("price"));
                dish.setCategoryId(thisRs.getInt("category_id"));
                Image image = new Image("resources/" + thisRs.getString("image"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                dish.setImage(imageView);
                dish.setTotalSold(thisRs.getInt("total_sold"));
                dish.setDescription(thisRs.getString("description"));
                dish.setAverageCostPrice(thisRs.getDouble("average_cost_price"));
                dish.setSoldOut(thisRs.getBoolean("sold_out"));
                dish.setDeleted(thisRs.getBoolean("deleted"));
                dish.toString();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(thisRs);
            JDBCConnect.closePreparedStatement(thisPs);
        }
        return dish;
    }

    public static void updateTable(DinnerTable dinnerTable) {
        String sql = "UPDATE `dinner_table` set `in_use` = ? WHERE `id` = ?";
        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, dinnerTable.isInUse());
            ps.setInt(2, dinnerTable.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Optional<Customer> getCustomerFromPhone(String phone) {
        Optional<Customer> customer = Optional.empty();
        String sql = "SELECT * FROM `customer` " +
                " WHERE `phone` = ? ";

        try {
            conn = JDBCConnect.JDBCConnector();
            ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                Customer customerTmp = new Customer();
                customerTmp.setId(rs.getInt("id"));
                customerTmp.setPhone(rs.getString("phone"));
                customerTmp.setMemberLevel(rs.getInt("member_level"));
                customer = Optional.of(customerTmp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return customer;
    }

    public static boolean insertNewCustomer(Customer customer) {
        Boolean flag = false;
        String sql = "INSERT INTO `customer`(name, phone, member_level)" +
                "VALUE(?, ?, ?)";
        conn = JDBCConnect.JDBCConnector();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setInt(3, 0);
            if (ps.executeUpdate() > 0){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return flag;
    }

    public static boolean updateLevelMember(Customer customer){
        Boolean flag = false;
        String sql = "UPDATE `customer` set `member_level` = ? WHERE `id` = ?";
        conn = JDBCConnect.JDBCConnector();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, customer.getMemberLevel() + 1);
            ps.setInt(2, customer.getId());
            if (ps.executeUpdate() > 0){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCConnect.closePreparedStatement(ps);
            JDBCConnect.closeConnection(conn);
        }
        return flag;
    }
}

