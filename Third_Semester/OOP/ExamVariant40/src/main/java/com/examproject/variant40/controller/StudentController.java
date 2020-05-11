package com.examproject.variant40.controller;

import com.examproject.variant40.aggregation.input.StudentSettings;
import com.examproject.variant40.aggregation.output.StudentDTO;
import com.examproject.variant40.aggregation.output.StudentDetailsDTO;
import com.examproject.variant40.service.StudentService;
import com.examproject.variant40.utils.ObjectMapperUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ObjectMapperUtils objectMapperUtils;

    @GetMapping
    public List<StudentDTO> getAll() {
        return objectMapperUtils.mapAll(studentService.getAll(), StudentDTO.class);
    }

    @GetMapping("/{studentId}")
    public StudentDetailsDTO getStudent(@PathVariable("studentId") Long studentId) {
        return objectMapperUtils.map(studentService.getById(studentId),StudentDetailsDTO.class);
    }

    @PostMapping
    public StudentDetailsDTO createStudent(@RequestBody StudentSettings studentSettings) {
        return objectMapperUtils.map(studentService.createStudent(studentSettings),StudentDetailsDTO.class);
    }
}
