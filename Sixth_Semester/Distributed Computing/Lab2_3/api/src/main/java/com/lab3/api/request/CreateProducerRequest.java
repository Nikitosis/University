package com.lab3.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateProducerRequest implements Serializable {
    private String name;
}
