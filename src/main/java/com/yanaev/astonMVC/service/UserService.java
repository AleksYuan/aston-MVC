package com.yanaev.astonMVC.service;

import com.yanaev.astonMVC.dao.UserDAO;
import com.yanaev.astonMVC.models.Car;
import com.yanaev.astonMVC.models.House;
import com.yanaev.astonMVC.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public List<User> getAllUsersFromRepo() {
        return userDAO.getAll();
    }

    public void updateUserInRepo(Long id, User user) {
        userDAO.updateById(id, user);
    }

    public User getUserByIdFromRepo(Long id) {
        Optional<User> res = userDAO.getById(id);
        return res.orElse(null);
    }

    public void saveUserInRepo(User user) {
        userDAO.save(user);
    }

    public void deleteUserByIdFromRepo(Long id) {
        userDAO.deleteById(id);
    }

    public void addHouseToUser(User user, House house) {
        if (user == null || house == null) return;
        List<House> houses = user.getHouses();
        houses.add(house);
        user.setHouses(houses);
        userDAO.addHouseToUser(user.getId(), house.getId());
    }

    public void addCarToUser(User user, Car car) {
        if (user == null || car == null) return;
        List<Car> cars = user.getCars();
        cars.add(car);
        user.setCars(cars);
        userDAO.addCarToUser(user.getId(), car.getId());
    }
 }
