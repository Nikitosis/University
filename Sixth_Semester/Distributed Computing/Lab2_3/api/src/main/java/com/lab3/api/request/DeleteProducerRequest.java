package com.lab3.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteProducerRequest implements Serializable {
    private Long id;
}
