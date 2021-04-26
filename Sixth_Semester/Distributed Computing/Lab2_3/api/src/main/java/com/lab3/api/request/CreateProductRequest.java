package com.lab3.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateProductRequest implements Serializable {
    private Integer year;
    private Integer version;
    private String name;
    private Long producerId;
}
