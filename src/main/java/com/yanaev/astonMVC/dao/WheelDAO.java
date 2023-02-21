package com.yanaev.astonMVC.dao;

import com.yanaev.astonMVC.models.Wheel;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class WheelDAO extends AbstractCrudDao implements CrudRepo<Wheel> {

    @Override
    public List<Wheel> getAll() {
        List<Wheel> wheels = new ArrayList<>();
        String SQL = "SELECT * FROM wheel";
        ResultSet resultSet = getResultSet(SQL);
        Wheel wheel;
        try {
            while (resultSet.next()) {
                wheel = fillInWheelWithData(resultSet);
                wheels.add(wheel);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return wheels;
    }

    @Override
    public Optional<Wheel> getById(Long id) {
        Wheel wheel = null;
        String SQL = "SELECT * FROM wheel WHERE id =?";
        ResultSet resultSet = getResultSet(SQL, id);
        try {
            if (resultSet.next()) {
                wheel = fillInWheelWithData(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (wheel == null) return Optional.empty();
        return Optional.of(wheel);
    }

    @Override
    public boolean deleteById(Long id) {
        String SQL = "DELETE FROM wheel WHERE id = " + id;
        executeQuery(SQL);
        return true;
    }

    @Override
    public boolean save(Wheel entity) {
        String SQL =
                "INSERT INTO wheel(type, radius, car_id) VALUES(?, ?, ?)";
        executeUpdatePreparedStatement(SQL,
                entity.getType(), entity.getRadius(), entity.getCarId(), null);
        return true;
    }

    @Override
    public void updateById(Long id, Wheel entity) {
        String SQL = "UPDATE wheel SET type=?, radius=?, car_id=? WHERE id=?";
        executeUpdatePreparedStatement(SQL,
                entity.getType(), entity.getRadius(),  entity.getCarId(), entity.getId());
    }


    private Wheel fillInWheelWithData(ResultSet resultSet) throws SQLException {
        Wheel wheel = new Wheel();
        wheel.setId(resultSet.getLong("id"));
        wheel.setType(resultSet.getString("type"));
        wheel.setRadius(resultSet.getInt("radius"));
        wheel.setCarId(resultSet.getLong("car_id"));
        return wheel;
    }
}
