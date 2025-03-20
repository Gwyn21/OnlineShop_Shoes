package com.example.be.service.marketer.impl;

import com.example.be.model.Marketer;
import com.example.be.repository.MarketerRepository;

import com.example.be.service.marketer.IMarketerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketerServiceImpl implements IMarketerService {
    @Autowired
    private MarketerRepository marketerRepository;

    @Override
    public List<Marketer> getAllMarketers() {
        return marketerRepository.findAll();
    }

    @Override
    public Optional<Marketer> getMarketerById(Integer id) {
        return marketerRepository.findById(id);
    }

    @Override
    public Marketer createMarketer(Marketer marketer) {
        return marketerRepository.save(marketer);
    }

    @Override
    public Marketer updateMarketer(Integer id, Marketer marketer) {
        if (marketerRepository.existsById(id)) {
            marketer.setId(id);
            return marketerRepository.save(marketer);
        }
        return null;
    }

    @Override
    public void deleteMarketer(Integer id) {
        marketerRepository.deleteById(id);
    }
}
