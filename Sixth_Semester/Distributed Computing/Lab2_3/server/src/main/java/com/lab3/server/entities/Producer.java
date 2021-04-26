package com.lab3.server.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Producer implements Serializable {
    private Long id;
    private String name;
    private List<Product> products;
}
