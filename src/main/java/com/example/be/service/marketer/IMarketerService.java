package com.example.be.service.marketer;

import com.example.be.model.Marketer;
import java.util.List;
import java.util.Optional;

public interface IMarketerService {
    List<Marketer> getAllMarketers();
    Optional<Marketer> getMarketerById(Integer id);
    Marketer createMarketer(Marketer marketer);
    Marketer updateMarketer(Integer id, Marketer marketer);
    void deleteMarketer(Integer id);
}