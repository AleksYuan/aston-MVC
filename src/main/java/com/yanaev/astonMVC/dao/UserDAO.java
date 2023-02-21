package com.yanaev.astonMVC.dao;


import com.yanaev.astonMVC.models.Car;
import com.yanaev.astonMVC.models.House;
import com.yanaev.astonMVC.models.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAO extends AbstractCrudDao implements CrudRepo<User> {


    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM users";
        ResultSet resultSet = getResultSet(SQL);
        User user;
        try {
            while (resultSet.next()) {
                user = fillInUserWithData(resultSet);
//                user.setId(resultSet.getLong("id"));
//                user.setFirstName(resultSet.getString("first_name"));
//                user.setLastName(resultSet.getString("last_name"));
//                user.setAge(resultSet.getInt("age"));
//                user.setPhoneNumber(resultSet.getString("phone_number"));
//                user = fillInEntityUserWithHouses(user);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return users;
    }

    @Override
    public Optional<User> getById(Long id) {
        User user = null;
        String SQL = "SELECT * FROM users WHERE id =?";
        ResultSet resultSet = getResultSet(SQL, id);
        try {
            if (resultSet.next()) {
                user = fillInUserWithData(resultSet);
//                user = new User();
//                user.setId(resultSet.getLong("id"));
//                user.setFirstName(resultSet.getString("first_name"));
//                user.setLastName(resultSet.getString("last_name"));
//                user.setAge(resultSet.getInt("age"));
//                user.setPhoneNumber(resultSet.getString("phone_number"));
//                user = fillInEntityUserWithHouses(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (user == null) return Optional.empty();
        return Optional.of(user);
//        try {
//            PreparedStatement prepStat =
//                    connection.prepareStatement("SELECT * FROM users WHERE id =?");
//            prepStat.setLong(1, id);
//            ResultSet resultSet = prepStat.executeQuery();
//
//            if (resultSet.next()) {
//                user = new User();
//                user.setId(resultSet.getLong("id"));
//                user.setFirstName(resultSet.getString("first_name"));
//                user.setLastName(resultSet.getString("last_name"));
//                user.setAge(resultSet.getInt("age"));
//                user.setPhoneNumber(resultSet.getString("phone_number"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (user == null) return Optional.empty();
//        return Optional.of(user);
    }

    public boolean save(User user) {
        String SQL =
                "INSERT INTO users(first_name, last_name, age, phone_number) VALUES(?, ?, ?,?)";
        executeUpdatePreparedStatement(SQL, user.getFirstName(), user.getLastName(), user.getAge(), user.getPhoneNumber(), null);
        return true;
//        try {
//            PreparedStatement prepStat = connection.prepareStatement(
//                    "INSERT INTO users(first_name, last_name, age, phone_number) VALUES(?, ?, ?,?)");
//
//            prepStat.setString(1, user.getFirstName());
//            prepStat.setString(2, user.getLastName());
//            prepStat.setInt(3, user.getAge());
//            prepStat.setString(4, user.getPhoneNumber());
//            prepStat.executeUpdate();
//
////            Statement statement = connection.createStatement();
////            String SQL = "INSERT INTO users(first_name, last_name, age, phone_number) VALUES("
////                    + " '" + user.getFirstName() + "', "
////                    + " '" + user.getLastName() + "', "
////                    + user.getAge() + " ,"
////                    + " '" + user.getPhoneNumber() + "' )";
////            statement.executeUpdate(SQL);
//            return true;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void updateById(Long id, User user) {
        String SQL = "UPDATE users SET first_name=?, last_name=?, age=?, phone_number=? WHERE id=?";
        executeUpdatePreparedStatement(SQL,
                user.getFirstName(), user.getLastName(), user.getAge(), user.getPhoneNumber(), user.getId());
//        try {
//            PreparedStatement prepStat =
//                    connection.prepareStatement("UPDATE users " +
//                            "SET first_name=?, last_name=?,age=?, phone_number=? WHERE id=? ");
//            prepStat.setString(1, user.getFirstName());
//            prepStat.setString(2, user.getLastName());
//            prepStat.setInt(3, user.getAge());
//            prepStat.setString(4, user.getPhoneNumber());
//            prepStat.setLong(5, user.getId());
//
//            prepStat.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public boolean deleteById(Long id) {

        String SQL = "DELETE FROM users WHERE id = " + id;
        executeQuery(SQL);
        return true;
//        try {
//            Statement statement = connection.createStatement();
////            String SQL = "DELETE FROM users WHERE id = " + id;
//            return statement.execute(SQL);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }



    private User fillInUserWithData(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setAge(resultSet.getInt("age"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        return fillInEntityUserWithHouses(user);
    }

    private User fillInEntityUserWithHouses(User user) throws SQLException {

        String SQL = "SELECT house_id, area, garage FROM house " +
                "JOIN users_house ON house.id = users_house.house_id " +
                "JOIN users ON users.id = users_house.users_id WHERE users_id =" + user.getId();
        ResultSet rSet = getResultSet(SQL);
        List<House> houses = user.getHouses();
        while (rSet.next()) {
            House house = new House();
            house.setId(rSet.getLong("house_id"));
            house.setArea(rSet.getInt("area"));
            house.setGarage(rSet.getBoolean("garage"));
            houses.add(house);
        }
        user.setHouses(houses);


        String SQL2 = "SELECT car_id, name, comment, created_year, house_id FROM car " +
                "JOIN users_car ON car.id = users_car.car_id " +
                "JOIN users ON users.id = users_car.users_id WHERE users_id =" + user.getId();
        ResultSet rcSet = getResultSet(SQL2);
        List<Car> cars = user.getCars();
        while (rcSet.next()) {
            Car car = new Car();
            car.setId(rcSet.getLong("car_id"));
            car.setName(rcSet.getString("name"));
            car.setComment(rcSet.getString("comment"));
            car.setCreatedYear(rcSet.getInt("created_year"));
            car.setHouseId(rcSet.getLong("house_id"));
            cars.add(car);
        }
        user.setCars(cars);

        return user;
    }


    public void addHouseToUser(Long userId, Long houseId) {
        String SQL = "INSERT INTO users_house VALUES(?, ?)";
        addEntityToEntity(SQL, userId, houseId);
    }

    public void addCarToUser(Long userId, Long carId) {
        String SQL = "INSERT INTO users_car VALUES(?, ?)";
        addEntityToEntity(SQL, userId, carId);
    }

//    // Еще один способ обновить данные в бд, будет немного подольше чем через сет
//    private boolean updateEntityInRepoById(Long id, User user) {
//        Optional<User> current = getById(id);
//        if (current.isEmpty()) return false;
//        User present = current.get();
//
//        present.setFirstName(user.getFirstName());
//        present.setLastName(user.getLastName());
//        present.setAge(user.getAge());
//        present.setPhoneNumber(user.getPhoneNumber());
//
//        save(present);
//        return true;
//    }
}
