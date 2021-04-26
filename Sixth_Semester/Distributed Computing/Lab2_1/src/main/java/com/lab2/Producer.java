package com.lab2;

import lombok.Data;

import java.util.List;

@Data
public class Producer {
    private String id;
    private String name;
    private List<Product> products;
}
