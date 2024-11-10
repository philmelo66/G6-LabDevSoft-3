package com.grupo6.lab3.controller;

import com.grupo6.lab3.dto.LoginDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/login")
    public void login(@ApiParam(value = "Login request body", required = true) @RequestBody LoginDTO loginRequest) {}
} 