package com.yanaev.astonMVC.web;

import com.yanaev.astonMVC.models.House;
import com.yanaev.astonMVC.models.Wheel;
import com.yanaev.astonMVC.service.WheelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wheel")
public class WheelController {
    private final WheelService wheelService;

    public WheelController(WheelService wheelService) {
        this.wheelService = wheelService;
    }


    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("wheels", wheelService.getAllWheelFromRepo());
        return "/wheel/all";
    }

    @GetMapping("/new")
    public String newWheel(@ModelAttribute("wheel") Wheel wheel) {
        return "/wheel/new";
    }

    @PostMapping("/new")
    public String addNewWheel(@ModelAttribute("wheel") Wheel wheel) {
        wheelService.addNewWheelInREpo(wheel);
        return "redirect:/wheel/all";
    }

    @GetMapping("/{id}")
    public String getWheelById(Model model, @PathVariable("id") Long id) {
        Wheel wheel = wheelService.getWheelByIdFromRepo(id);
        if (wheel == null) return "redirect:/wheel/all";
        model.addAttribute("wheel", wheel);
        return "/wheel/one";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("wheel", wheelService.getWheelByIdFromRepo(id));
        return "/wheel/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("wheel") Wheel wheel, @PathVariable("id") Long id) {
        wheelService.updateWheelByIdInRepo(id, wheel);
        return "redirect:/wheel/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        wheelService.deleteWheelByIdFromRepo(id);
        return "redirect:/wheel/all";
    }
}
