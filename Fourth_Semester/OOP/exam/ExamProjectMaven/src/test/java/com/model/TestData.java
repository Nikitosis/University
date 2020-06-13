package com.model;

import java.util.Arrays;

public class TestData {
    public static Ministry getMinistry() {
        Employee emp1 = new Employee.Builder()
                .name("Vasya")
                .surname("Pupkin")
                .build();
        Employee emp2 = new Employee.Builder()
                .name("Zhenya")
                .surname("Mihaylov")
                .build();

        Unit taxiUnit = new Unit.Builder()
                .name("Taxi")
                .employees(Arrays.asList(emp1, emp2))
                .build();

        Organization infrostructureOrganization = new Organization.Builder()
                .name("Infrostructure")
                .units(Arrays.asList(taxiUnit))
                .build();

        Ministry economyMinistry = new Ministry.Builder()
                .name("Ministry of economy")
                .organizations(Arrays.asList(infrostructureOrganization))
                .build();
        return economyMinistry;
    }

    public static Employee getEmployee(String name, String surname){
        return new Employee.Builder()
                .name(name)
                .surname(surname)
                .build();
    }
}
