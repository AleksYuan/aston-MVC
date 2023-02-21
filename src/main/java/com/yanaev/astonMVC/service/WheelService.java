package com.yanaev.astonMVC.service;

import com.yanaev.astonMVC.dao.CarDAO;
import com.yanaev.astonMVC.dao.WheelDAO;
import com.yanaev.astonMVC.models.Car;
import com.yanaev.astonMVC.models.House;
import com.yanaev.astonMVC.models.User;
import com.yanaev.astonMVC.models.Wheel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WheelService {

    private final WheelDAO wheelDAO;

    public WheelService(WheelDAO wheelDAO) {
        this.wheelDAO = wheelDAO;
    }

    public List<Wheel> getAllWheelFromRepo() {
        return wheelDAO.getAll();
    };

    public Wheel getWheelByIdFromRepo(Long id) {
        Optional<Wheel> res = wheelDAO.getById(id);
        return res.orElse(null);
    }

    public void updateWheelByIdInRepo(Long id, Wheel wheel) {
        Wheel current = wheelDAO.getById(id).get();
        current.setType(wheel.getType());
        current.setRadius(wheel.getRadius());
        current.setCarId(wheel.getCarId());
        wheelDAO.updateById(id, current);
    }

    public void deleteWheelByIdFromRepo(Long id) {
        wheelDAO.deleteById(id);
    }

    public void addNewWheelInREpo(Wheel wheel) {
        wheelDAO.save(wheel);
    }
}
