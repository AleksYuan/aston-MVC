package com.yanaev.astonMVC.web;


import com.yanaev.astonMVC.models.Car;
import com.yanaev.astonMVC.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarService carService;


    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("cars", carService.getAllCarsFromRepo());
        return "/car/all";
    }

    @GetMapping("/new")
    public String newCar(@ModelAttribute("car") Car car) {
        return "/car/new";
    }


    @PostMapping("/new")
    public String addNewCar(@ModelAttribute("car") Car car) {
        carService.addNewCarToRepo(car);
        return "redirect:/car/all";
    }

    @GetMapping("/{id}")
    public String getHouseById(Model model, @PathVariable("id") Long id) {
        Car car = carService.getCarByIdFromRepo(id);
        if (car == null) return "redirect:/users/all";
        model.addAttribute("car", car);
        return "/car/one";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("car", carService.getCarByIdFromRepo(id));
        return "/car/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("car") Car car, @PathVariable("id") Long id) {
        carService.updateCarInRepo(id, car);
        return "redirect: /car/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        carService.deleteCarByIdFromRepo(id);
        return "redirect: /car/all";
    }
}
