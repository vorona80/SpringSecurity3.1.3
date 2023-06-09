package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.model.User;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImpl userServiceImpl;
    private RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleService) {
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getAllUsers(Model model) {
        model.addAttribute("user", userServiceImpl.getAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String getNewUser(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "new";
        userServiceImpl.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String getUserById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userServiceImpl.getUser(id));
        return "edit";
    }

    @PostMapping("/{id}")
    public String saveUpdateUser(@ModelAttribute("user") @Validated User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "edit";
        userServiceImpl.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }
}


