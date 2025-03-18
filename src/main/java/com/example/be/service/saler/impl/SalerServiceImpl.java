package com.example.be.service.saler.impl;

import com.example.be.model.Saler;
import com.example.be.repository.SalerRepository;

import com.example.be.service.saler.ISalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalerServiceImpl implements ISalerService {
    @Autowired
    private SalerRepository salerRepository;

    @Override
    public List<Saler> getAllSalers() {
        return salerRepository.findAll();
    }

    @Override
    public Optional<Saler> getSalerById(Integer id) {
        return salerRepository.findById(id);
    }

    @Override
    public Saler createSaler(Saler saler) {
        return salerRepository.save(saler);
    }

    @Override
    public Saler updateSaler(Integer id, Saler saler) {
        if (salerRepository.existsById(id)) {
            saler.setId(id);
            return salerRepository.save(saler);
        }
        return null;
    }

    @Override
    public void deleteSaler(Integer id) {
        salerRepository.deleteById(id);
    }
}
