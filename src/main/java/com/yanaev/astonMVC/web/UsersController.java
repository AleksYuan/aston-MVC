package com.yanaev.astonMVC.web;

import com.yanaev.astonMVC.models.Car;
import com.yanaev.astonMVC.models.House;
import com.yanaev.astonMVC.models.User;
import com.yanaev.astonMVC.service.CarService;
import com.yanaev.astonMVC.service.HouseService;
import com.yanaev.astonMVC.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final HouseService houseService;
    private final CarService carService;



    public UsersController(UserService uService, HouseService houseService, CarService carService) {
        this.userService = uService;
        this.houseService = houseService;
        this.carService = carService;
    }

    @GetMapping("/all")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsersFromRepo());
        return "/users/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserByIdFromRepo(id);
        if (user == null) return "redirect:/users/all";
        model.addAttribute("user", user);
        return "/users/one";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user) {
        return "/users/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user) {
        userService.saveUserInRepo(user);
        return "redirect:/users/all";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserByIdFromRepo(id));
        return "/users/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUserInRepo(id, user);
        return "redirect:/users/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserByIdFromRepo(id);
        return "redirect:/users/all";
    }

    @PostMapping("/{idu}/edit/add-house-to-user")
    public String addHouseToUserById(@PathVariable("idu") Long idu,
                                     @RequestParam("idHouse") Long idh,
                                     Model model) {
        User user = userService.getUserByIdFromRepo(idu);
        House house = houseService.getHouseByIdFromRepo(idh);
        userService.addHouseToUser(user, house);
        model.addAttribute(user);
        return "/users/edit";
    }


    @PostMapping("/{idu}/edit/add-car-to-user")
    public String addCarToUserById(@PathVariable("idu") Long idu,
                                     @RequestParam("idCar") Long idc,
                                     Model model) {
        User user = userService.getUserByIdFromRepo(idu);
        Car car = carService.getCarByIdFromRepo(idc);
        userService.addCarToUser(user, car);
        model.addAttribute(user);
        return "/users/edit";
    }
}