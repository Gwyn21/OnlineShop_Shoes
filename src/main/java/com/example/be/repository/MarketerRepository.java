package com.example.be.repository;

import com.example.be.model.Marketer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketerRepository extends JpaRepository<Marketer, Integer> {
}
