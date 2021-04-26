package com.lab3.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProducerDto implements Serializable {
    private Long id;
    private String name;
    private List<ProductDto> products;
}
