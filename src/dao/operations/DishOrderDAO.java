package dao.operations;

import dao.DataSource;
import dao.entity.DishAvailability;
import dao.entity.DishOrder;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishOrderDAO implements CRUDOperations<DishOrder> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<DishOrder> findAll() {
        List<DishOrder> dishOrders = new ArrayList<>();
        String query = "SELECT id, dish_name, unit_price, quantity_to_order, id_order FROM dish_order";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dishOrders.add(mapFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishOrders;
    }

    @Override
    public DishOrder findById(Long id) {
        String query = "SELECT id, dish_name, unit_price, quantity_to_order, id_order FROM dish_order WHERE id = ?";

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
        throw new RuntimeException("DishOrder with id " + id + " not found");
    }

    private DishOrder mapFromResultSet(ResultSet resultSet) throws SQLException {
        OrderDAO orderDAO = new OrderDAO();

        DishOrder dishOrder = new DishOrder();
        dishOrder.setId(resultSet.getLong("id"));
        dishOrder.setDishName(resultSet.getString("dish_name"));
        dishOrder.setUnitPrice(resultSet.getDouble("unit_price"));
        dishOrder.setQuantityToOrder(resultSet.getDouble("quantity_to_order"));
        dishOrder.setOrder(orderDAO.findById(resultSet.getLong("id_order")));


        return dishOrder;
    }

    @SneakyThrows
    @Override
    public List<DishOrder> saveAll(List<DishOrder> entities) {
        List<DishOrder> dishOrders= new ArrayList<>();
        String query = "INSERT INTO dish_order (id, dish_name, unit_price, quantity_to_order, id_order) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE " +
                "SET dish_name = EXCLUDED.dish_name, " +
                "    unit_price = EXCLUDED.unit_price, " +
                "    quantity_to_order = EXCLUDED.quantity_to_order, " +
                "    id_order = EXCLUDED.id_order" +
                "RETURNING id, dish_name, unit_price, quantity_to_order, id_order";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            for (DishOrder entityToSave : entities) {
                statement.setLong(1, entityToSave.getId());
                statement.setString(2, entityToSave.getDishName());
                statement.setDouble(3, entityToSave.getUnitPrice());
                statement.setDouble(4, entityToSave.getQuantityToOrder());
                statement.addBatch();
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    dishOrders.add(mapFromResultSet(resultSet));
                }
            }
        }
        return dishOrders;
    }
}
