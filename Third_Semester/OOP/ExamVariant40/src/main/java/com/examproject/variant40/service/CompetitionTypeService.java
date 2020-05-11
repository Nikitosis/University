package com.examproject.variant40.service;

import com.examproject.variant40.entity.CompetitionType;
import com.examproject.variant40.exception.NotFoundException;
import com.examproject.variant40.repository.CompetitionTypeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionTypeService {
    @Autowired
    private CompetitionTypeRepository competitionTypeRepository;

    public CompetitionType getByName(String name) throws NotFoundException {
        return competitionTypeRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("CompetitionType not found by name"));
    }
}
