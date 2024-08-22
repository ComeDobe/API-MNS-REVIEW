package com.dobe.locmns.controllers;

import com.dobe.locmns.services.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/roles")
@Tag(name = "role")

public class RoleController {
    private RoleService roleService;

    @PostMapping({"/createNewRole"})
    public String createNewRole(String role) {
        return roleService.createNewRole(role);
    }
}
