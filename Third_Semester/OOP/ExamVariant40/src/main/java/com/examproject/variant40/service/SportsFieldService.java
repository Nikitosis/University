package com.examproject.variant40.service;

import com.examproject.variant40.entity.SportsField;
import com.examproject.variant40.exception.NotFoundException;
import com.examproject.variant40.repository.SportsFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SportsFieldService {
    @Autowired
    private SportsFieldRepository sportsFieldRepository;

    public SportsField getByName(String name) throws NotFoundException{
        return sportsFieldRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Sports field not found by name"));
    }
}
