package com.yanaev.astonMVC.service;

import com.yanaev.astonMVC.dao.CarDAO;
import com.yanaev.astonMVC.models.Car;
import com.yanaev.astonMVC.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }


    public List<Car> getAllCarsFromRepo() {
        return carDAO.getAll();
    }

    public void updateCarInRepo(Long id, Car car) {
        carDAO.updateById(id, car);
    }

    public Car getCarByIdFromRepo(Long id) {
        Optional<Car> res = carDAO.getById(id);
        return res.orElse(null);
    }

    public void addNewCarToRepo(Car car) {
        carDAO.save(car);
    }

    public void deleteCarByIdFromRepo(Long id) {
        carDAO.deleteById(id);
    }

    public void addUserToCar(User user, Car car) {
        List<User> usersInCar = car.getUsers();
        usersInCar.add(user);
        car.setUsers(usersInCar);
        carDAO.addUserToCar(user.getId(), car.getId());
    }


}
