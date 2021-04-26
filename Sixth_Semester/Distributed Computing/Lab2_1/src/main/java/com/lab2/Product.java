package com.lab2;

import lombok.Data;

@Data
public class Product {
    private String id;
    private Integer year;
    private Integer version;
    private String name;
    private Producer producer;
}
