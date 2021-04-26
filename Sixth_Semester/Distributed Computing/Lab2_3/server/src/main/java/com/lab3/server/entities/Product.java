package com.lab3.server.entities;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private Integer year;
    private Integer version;
    private String name;
    private Producer producer;
}
