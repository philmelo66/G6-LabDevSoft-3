package com.grupo6.lab3.controller;

import com.grupo6.lab3.dto.VantagemDTO;
import com.grupo6.lab3.service.VantagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vantagens")
public class VantagemController {

    @Autowired
    private VantagemService vantagemService;

    @GetMapping
    public List<VantagemDTO> getAllVantagens() {
        return vantagemService.getAllVantagens();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VantagemDTO> getVantagemById(@PathVariable Long id) {
        return vantagemService.getVantagemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public VantagemDTO createVantagem(@RequestBody VantagemDTO vantagemDTO) {
        return vantagemService.createVantagem(vantagemDTO);
    }
} 