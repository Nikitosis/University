package com.oop.utils;

import com.oop.entities.request.CreditCardCreateRequest;
import com.oop.entities.request.UserCreateRequest;

public class TestData {
    public static UserCreateRequest getUserCreateRequest() {
        UserCreateRequest request = new UserCreateRequest();
        request.setFirstName("fname");
        request.setLastName("lname");
        request.setPassword("pass");
        request.setUsername("usern");

        return request;
    }

    public static CreditCardCreateRequest getCreditCardCreateRequest() {
        CreditCardCreateRequest request = new CreditCardCreateRequest();
        request.setName("Card1");

        return request;
    }
}
