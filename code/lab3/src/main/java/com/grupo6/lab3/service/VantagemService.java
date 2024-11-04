package com.grupo6.lab3.service;

import com.grupo6.lab3.entity.Vantagem;
import com.grupo6.lab3.repository.VantagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VantagemService {

    @Autowired
    private VantagemRepository vantagemRepository;

    public Optional<Vantagem> findById(Long id) {
        return vantagemRepository.findById(id);
    }
} 