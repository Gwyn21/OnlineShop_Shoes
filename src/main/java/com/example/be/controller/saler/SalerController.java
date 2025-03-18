package com.example.be.controller.saler;


import com.example.be.model.Saler;
import com.example.be.service.saler.ISalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
@CrossOrigin("*")
public class SalerController {
    @Autowired
    private ISalerService salerService;

    @GetMapping
    public List<Saler> getAllSalers() {
        return salerService.getAllSalers();
    }

    @GetMapping("/{id}")
    public Optional<Saler> getSalerById(@PathVariable Integer id) {
        return salerService.getSalerById(id);
    }

    @PostMapping
    public Saler createSaler(@RequestBody Saler saler) {
        return salerService.createSaler(saler);
    }

    @PutMapping("/{id}")
    public Saler updateSaler(@PathVariable Integer id, @RequestBody Saler saler) {
        return salerService.updateSaler(id, saler);
    }

    @DeleteMapping("/{id}")
    public void deleteSaler(@PathVariable Integer id) {
        salerService.deleteSaler(id);
    }
}

