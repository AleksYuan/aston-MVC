package com.yanaev.astonMVC.web;

import com.yanaev.astonMVC.models.House;
import com.yanaev.astonMVC.service.HouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/house")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("houses", houseService.getAllHouseFromRepo());
        return "/house/all";
    }

    @GetMapping("/new")
    public String newHouse(@ModelAttribute("house") House house) {
        return "/house/new";
    }

    @PostMapping("/new")
    public String addNewHouse(@ModelAttribute("house") House house) {
        houseService.addNewHouseInREpo(house);
        return "redirect:/house/all";
    }

    @GetMapping("/{id}")
    public String getHouseById(Model model, @PathVariable("id") Long id) {
        House house = houseService.getHouseByIdFromRepo(id);
        if (house == null) return "redirect:/house/all";
        model.addAttribute("house", house);
        return "/house/one";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("house", houseService.getHouseByIdFromRepo(id));
        return "/house/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("house") House house, @PathVariable("id") Long id) {
        houseService.updateHouseByIdInRepo(id, house);
        return "redirect: /house/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        houseService.deleteHouseByIdFromRepo(id);
        return "redirect: /house/all";
    }

}
