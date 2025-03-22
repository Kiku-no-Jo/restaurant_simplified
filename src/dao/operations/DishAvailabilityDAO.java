package dao.operations;

import dao.DataSource;
import dao.entity.DishAvailability;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishAvailabilityDAO implements CRUDOperations<DishAvailability> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<DishAvailability> findAll() {
        List<DishAvailability> availableDishes = new ArrayList<>();
        String query = "SELECT id, dish_name, available_stock FROM dish_availability";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    availableDishes.add(mapFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return availableDishes;
    }

    @Override
    public DishAvailability findById(Long id) {
        String query = "SELECT id, dish_name,avilable_stock FROM dish_availability WHERE id = ?";

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
        throw new RuntimeException("User with id " + id + " not found");
    }

    private DishAvailability mapFromResultSet(ResultSet resultSet) throws SQLException {
        DishAvailability dishAvailability = new DishAvailability();
        dishAvailability.setId(resultSet.getLong("id"));
        dishAvailability.setDishName(resultSet.getString("dish_name"));
        dishAvailability.setAvailability(resultSet.getDouble("available_stock"));
        return dishAvailability;
    }

    @SneakyThrows
    @Override
    public List<DishAvailability> saveAll(List<DishAvailability> entities) {
        List<DishAvailability> dishAvailabilities = new ArrayList<>();
        String query = "INSERT INTO dish_availability (id, dish_name, available_stock) " +
                "VALUES (?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE " +
                "SET dish_name = EXCLUDED.dish_name, " +
                "    available_stock = EXCLUDED.availabale_stock, " +
                "RETURNING id, first_name, last_name, birthday, email, password";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            for (DishAvailability entityToSave : entities) {
                statement.setLong(1, entityToSave.getId());
                statement.setString(2, entityToSave.getDishName());
                statement.setDouble(3, entityToSave.getAvailability());
                statement.addBatch();
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    dishAvailabilities.add(mapFromResultSet(resultSet));
                }
            }
        }
        return dishAvailabilities;
    }
}
