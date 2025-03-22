package dao.operations;

import dao.DataSource;
import dao.entity.Order;
import dao.entity.TableNumber;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements CRUDOperations<Order> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT id, table_number, amount_paid, amount_due, customer_arrival_date FROM \"order\"";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(mapFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public Order findById(Long id) {
        String query = "SELECT id, table_number, amount_paid, amount_due, customer_arrival_date FROM \"order\" WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Order with id " + id + " not found");
    }

    private Order mapFromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setTableNumber(TableNumber.valueOf(resultSet.getString("table_number")));
        order.setAmountPaid(resultSet.getDouble("amount_paid"));
        order.setAmountDue(resultSet.getDouble("amount_due"));
        order.setCustomerArrivalDateTime(resultSet.getDate("customer_arrival_date").toLocalDate());
        return order;
    }

    @SneakyThrows
    @Override
    public List<Order> saveAll(List<Order> entities) {
        List<Order> orders = new ArrayList<>();
        String query = "INSERT INTO \"order\" (id, table_number, amount_paid, amount_due, customer_arrival_date) " +
                "VALUES (?, CAST(? AS table_number_enum), ?, ?, ?) " +  // Explicit cast
                "ON CONFLICT (id) DO UPDATE " +
                "SET table_number = EXCLUDED.table_number, " +
                "    amount_paid = EXCLUDED.amount_paid, " +
                "    amount_due = EXCLUDED.amount_due, " +
                "    customer_arrival_date = EXCLUDED.customer_arrival_date " +
                "RETURNING id, table_number, amount_paid, amount_due, customer_arrival_date";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            for (Order entityToSave : entities) {
                statement.setLong(1, entityToSave.getId());
                statement.setString(2, entityToSave.getTableNumber().name());  // Ensure it’s an ENUM name
                statement.setDouble(3, entityToSave.getAmountPaid());
                statement.setDouble(4, entityToSave.getAmountDue());
                statement.setDate(5, Date.valueOf(entityToSave.getCustomerArrivalDateTime()));
                statement.addBatch();
            }

            statement.executeBatch(); // Execute batch first

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    orders.add(mapFromResultSet(resultSet));
                }
            }
        }
        return orders;
    }
}
