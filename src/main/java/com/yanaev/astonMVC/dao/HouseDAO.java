package com.yanaev.astonMVC.dao;

import com.yanaev.astonMVC.models.House;
import com.yanaev.astonMVC.models.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class HouseDAO extends AbstractCrudDao implements CrudRepo<House> {

    @Override
    public List<House> getAll() {
        List<House> houses = new ArrayList<>();
        String SQL = "SELECT * FROM house";
        ResultSet resultSet = getResultSet(SQL);
        House house;
        try {
            while (resultSet.next()) {
                house = fillInHouseWithData(resultSet);
//                house = new House();
//                house.setId(resultSet.getLong("id"));
//                house.setArea(resultSet.getInt("area"));
//                house.setGarage(resultSet.getBoolean("garage"));
//                house = fillInEntityHouseWithUsers(house);
                houses.add(house);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
//        List<House> houses = new ArrayList<>();
//        try {
//            Statement statement = connection.createStatement();
//            String SQL = "SELECT * FROM house";
//            ResultSet resultSet = statement.executeQuery(SQL);
//
//            while (resultSet.next()) {
//                House house = new House();
//                house.setId(resultSet.getLong("id"));
//                house.setArea(resultSet.getInt("area"));
//                house.setGarage(resultSet.getBoolean("garage"));
//                houses.add(house);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return houses;
    }

    @Override
    public Optional<House> getById(Long id) {
        House house = null;
        String SQL = "SELECT * FROM house WHERE id =?";
        ResultSet resultSet = getResultSet(SQL, id);
        try {
            if (resultSet.next()) {
                house = fillInHouseWithData(resultSet);
//                house = new House();
//                house.setId(resultSet.getLong("id"));
//                house.setArea(resultSet.getInt("area"));
//                house.setGarage(resultSet.getBoolean("garage"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        try {
//            PreparedStatement prepStat =
//                    connection.prepareStatement("SELECT * FROM house WHERE id =?");
//
//            prepStat.setLong(1, id);
//
//            ResultSet resultSet = prepStat.executeQuery();
//
//            if (resultSet.next()) {
//                house = new House();
//                house.setId(resultSet.getLong("id"));
//                house.setArea(resultSet.getInt("area"));
//                house.setGarage(resultSet.getBoolean("garage"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        if (house == null) return Optional.empty();
        return Optional.of(house);
    }

    @Override
    public boolean deleteById(Long id) {
        String SQL = "DELETE FROM house WHERE id = " + id;
        executeQuery(SQL);
        return true;
    }

    @Override
    public boolean save(House entity) {
        String SQL = "INSERT INTO house(area, garage) VALUES(?, ?)";
        executeUpdatePreparedStatement(SQL, entity.getArea(), entity.getGarage(), null);
        return true;
//        try {
//            PreparedStatement prepStat = connection.prepareStatement(
//                    "INSERT INTO house(area, garage) VALUES(?, ?)");
//            prepStat.setInt(1, entity.getArea());
//            prepStat.setBoolean(2, entity.getGarage());
//            prepStat.executeUpdate();
//
//            return true;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void updateById(Long id, House entity) {
        String SQL = "UPDATE house SET area=?, garage=? WHERE id=?";
        executeUpdatePreparedStatement(SQL, entity.getArea(), entity.getGarage(), entity.getId());
//        try {
//            PreparedStatement prepStat =
//                    connection.prepareStatement("UPDATE house SET area=?, garage=? WHERE id=?");
//            prepStat.setInt(1, entity.getArea());
//            prepStat.setBoolean(2, entity.getGarage());
//            prepStat.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }


    private House fillInHouseWithData(ResultSet resultSet) throws SQLException {
        House house = new House();
        house.setUsers(new ArrayList<>());
        house.setId(resultSet.getLong("id"));
        house.setArea(resultSet.getInt("area"));
        house.setGarage(resultSet.getBoolean("garage"));
        house = fillInEntityHouseWithUsers(house);
        return house;
    }

    private House fillInEntityHouseWithUsers(House house) throws SQLException {
        String SQL = "SELECT users_id, first_name, last_name, age, phone_number FROM users " +
                "JOIN users_house ON users.id = users_house.users_id " +
                "JOIN house ON house.id = users_house.house_id WHERE house_id = " + house.getId();
        ResultSet rSet = getResultSet(SQL);
        Collection<User> users = house.getUsers();
        while (rSet.next()) {
            User user = new User();
            user.setId(rSet.getLong("users_id"));
            user.setFirstName(rSet.getString("first_name"));
            user.setLastName(rSet.getString("last_name"));
            user.setAge(rSet.getInt("age"));
            user.setPhoneNumber(rSet.getString("phone_number"));
            users.add(user);
        }
        house.setUsers((List<User>) users);
        return house;
    }

    public void addUserToHouse(Long userId, Long houseId) {
        String SQL = "INSERT INTO users_house VALUES(?, ?)";
        addEntityToEntity(SQL, userId, houseId);
    }

}
