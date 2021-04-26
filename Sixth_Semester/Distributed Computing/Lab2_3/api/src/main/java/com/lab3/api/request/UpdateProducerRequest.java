package com.lab3.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateProducerRequest implements Serializable {
    private Long id;
    private String name;
}
