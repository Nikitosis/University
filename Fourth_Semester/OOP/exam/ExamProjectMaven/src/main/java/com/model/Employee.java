package com.model;

public class Employee implements JsonObject{
    private String name;
    private String surname;
    private Unit parentUnit;

    @Override
    public String toJson() {
        return String.format("{\"name\":\"%s\", \"surname\":\"%s\"}", name, surname);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Unit getParentUnit() {
        return parentUnit;
    }

    public void setParentUnit(Unit parentUnit) {
        this.parentUnit = parentUnit;
    }

    public static class Builder {
        private Employee employee;

        public Builder() {
            employee = new Employee();
        }

        public Builder name(String name) {
            employee.setName(name);
            return this;
        }

        public Builder surname(String surname) {
            employee.setSurname(surname);
            return this;
        }

        public Builder parentUnit(Unit parentUnit) {
            employee.setParentUnit(parentUnit);
            return this;
        }

        public Employee build() {
            return employee;
        }
    }
}
