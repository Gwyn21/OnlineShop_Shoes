package com.example.be.service.saler;

import com.example.be.model.Saler;
import java.util.List;
import java.util.Optional;

public interface ISalerService {
    List<Saler> getAllSalers();
    Optional<Saler> getSalerById(Integer id);
    Saler createSaler(Saler saler);
    Saler updateSaler(Integer id, Saler saler);
    void deleteSaler(Integer id);
}
