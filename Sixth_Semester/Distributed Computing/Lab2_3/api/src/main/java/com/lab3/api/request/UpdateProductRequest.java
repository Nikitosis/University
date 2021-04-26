package com.lab3.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateProductRequest implements Serializable {
    private Long id;
    private Integer year;
    private Integer version;
    private String name;
}
