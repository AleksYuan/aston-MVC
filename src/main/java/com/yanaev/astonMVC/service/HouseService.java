package com.yanaev.astonMVC.service;

import com.yanaev.astonMVC.dao.HouseDAO;
import com.yanaev.astonMVC.models.Car;
import com.yanaev.astonMVC.models.House;
import com.yanaev.astonMVC.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseService {

    private final HouseDAO houseDAO;

    public HouseService(HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    public List<House> getAllHouseFromRepo() {
        return houseDAO.getAll();
    };

    public House getHouseByIdFromRepo(Long id) {
        Optional<House> res = houseDAO.getById(id);
        return res.orElse(null);
    }

    public void updateHouseByIdInRepo(Long id, House house) {
        House current = houseDAO.getById(id).get();
        current.setArea(house.getArea());
        current.setGarage(house.getGarage());
        houseDAO.updateById(id, current);
    }

    public void deleteHouseByIdFromRepo(Long id) {
        houseDAO.deleteById(id);
    }

    public void addNewHouseInREpo(House house) {
        houseDAO.save(house);
    }

    public void addUserToHouse(User user, House house) {
        List<User> usersInHouse = house.getUsers();
        usersInHouse.add(user);
        house.setUsers(usersInHouse);
        houseDAO.addUserToHouse(user.getId(), house.getId());
    }
}
