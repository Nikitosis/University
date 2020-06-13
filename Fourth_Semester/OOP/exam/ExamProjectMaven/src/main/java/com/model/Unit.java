package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Unit implements JsonObject{
    private String name;
    private List<Employee> employees = new ArrayList<>();
    private Organization parentOrganization;


    @Override
    public String toJson() {
        String employeesStr = employees.stream()
                .map(Employee::toJson)
                .collect(Collectors.joining(", "));
        employeesStr = "[" + employeesStr + "]";

        return String.format("{\"name\":\"%s\", \"units\":%s}", name, employeesStr);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        employee.setParentUnit(this);
        employees.add(employee);
    }

    public Organization getParentOrganization() {
        return parentOrganization;
    }

    public void setParentOrganization(Organization parentOrganization) {
        this.parentOrganization = parentOrganization;
    }

    public static class Builder {
        private Unit unit;

        public Builder() {
            unit = new Unit();
        }

        public Builder name(String name) {
            unit.setName(name);
            return this;
        }

        public Builder employees(List<Employee> employees) {
            employees.forEach(employee -> unit.addEmployee(employee));
            return this;
        }

        public Unit build() {
            return unit;
        }
    }
}
