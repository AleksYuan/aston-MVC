package com.yanaev.astonMVC.dao;

import com.yanaev.astonMVC.models.Car;
import com.yanaev.astonMVC.models.User;
import com.yanaev.astonMVC.models.Wheel;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CarDAO extends AbstractCrudDao implements CrudRepo<Car> {

    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        String SQL = "SELECT * FROM car";
        ResultSet resultSet = getResultSet(SQL);
        Car car;
        try {
            while (resultSet.next()) {
                car = fillInCarWithData(resultSet);
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return cars;
    }

    @Override
    public Optional<Car> getById(Long id) {
        Car car = null;
        String SQL = "SELECT * FROM car WHERE id =?";
        ResultSet resultSet = getResultSet(SQL, id);
        try {
            if (resultSet.next()) {
                car = fillInCarWithData(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (car == null) return Optional.empty();
        return Optional.of(car);
    }

    @Override
    public boolean deleteById(Long id) {
        String SQL = "DELETE FROM car WHERE id = " + id;
        executeQuery(SQL);
        return true;
    }

    @Override
    public boolean save(Car entity) {
        String SQL =
                "INSERT INTO car(name, comment, created_year, house_id) VALUES(?, ?, ?,?)";
        executeUpdatePreparedStatement(SQL,
                entity.getName(), entity.getComment(), entity.getCreatedYear(), entity.getHouseId(), null);
        return true;
    }

    @Override
    public void updateById(Long id, Car entity) {
        String SQL = "UPDATE car SET name=?, comment=?, created_year=?, house_id=? WHERE id=?";
        executeUpdatePreparedStatement(SQL,
                entity.getName(), entity.getComment(), entity.getCreatedYear(), entity.getHouseId(), entity.getId());
    }


    private Car fillInCarWithData(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getLong("id"));
        car.setName(resultSet.getString("name"));
        car.setComment(resultSet.getString("comment"));
        car.setCreatedYear(resultSet.getInt("created_year"));

        Long house_id = resultSet.getLong("house_id");
        car.setHouseId(resultSet.getLong("house_id"));
        return fillInEntityCarWithUsersAndWheels(car);
    }

    private Car fillInEntityCarWithUsersAndWheels(Car car) throws SQLException {
        String SQL = "SELECT users_id, first_name, last_name, age, phone_number FROM users " +
                "JOIN users_car ON users.id = users_car.users_id " +
                "JOIN car ON car.id = users_car.car_id WHERE car_id = " + car.getId();
        ResultSet rSet = getResultSet(SQL);
        List<User> users = car.getUsers();
        while (rSet.next()) {
            User user = new User();
            user.setId(rSet.getLong("users_id"));
            user.setFirstName(rSet.getString("first_name"));
            user.setLastName(rSet.getString("last_name"));
            user.setAge(rSet.getInt("age"));
            user.setPhoneNumber(rSet.getString("phone_number"));
            users.add(user);
        }
        car.setUsers(users);

        String SQL2 = "select * from wheel where car_id=" + car.getId();
        ResultSet rcSet = getResultSet(SQL2);
        List<Wheel> wheels = car.getWheels();
        while (rcSet.next()) {
            Wheel wheel = new Wheel();
            wheel.setId(rcSet.getLong("id"));
            wheel.setType(rcSet.getString("type"));
            wheel.setRadius(rcSet.getInt("radius"));
            wheel.setCarId(rcSet.getLong("car_id"));
            wheels.add(wheel);
        }
        car.setWheels(wheels);
        return car;
    }

    public void addUserToCar(Long userId, Long carId) {
        String SQL = "INSERT INTO users_car VALUES(?, ?)";
        addEntityToEntity(SQL, userId, carId);
    }
}
