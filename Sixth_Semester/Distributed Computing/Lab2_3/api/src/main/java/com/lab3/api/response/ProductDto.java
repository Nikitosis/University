package com.lab3.api.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private Long id;
    private Integer year;
    private Integer version;
    private String name;
}
