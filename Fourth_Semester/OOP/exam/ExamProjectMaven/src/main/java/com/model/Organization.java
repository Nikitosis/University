package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Organization implements JsonObject{
    private String name;
    private List<Unit> units = new ArrayList<>();;
    private Ministry parentMinistry;

    @Override
    public String toJson() {
        String unitsStr = units.stream()
                .map(Unit::toJson)
                .collect(Collectors.joining(", "));
        unitsStr = "[" + unitsStr + "]";

        return String.format("{\"name\":\"%s\", \"units\":%s}", name, unitsStr);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public void addUnit(Unit unit) {
        unit.setParentOrganization(this);
        units.add(unit);
    }

    public Ministry getParentMinistry() {
        return parentMinistry;
    }

    public void setParentMinistry(Ministry parentMinistry) {
        this.parentMinistry = parentMinistry;
    }

    public static class Builder {
        private Organization organization;

        public Builder() {
            organization = new Organization();
        }

        public Builder name(String name) {
            organization.setName(name);
            return this;
        }

        public Builder units(List<Unit> units) {
            units.forEach(unit -> organization.addUnit(unit));
            return this;
        }

        public Organization build() {
            return organization;
        }
    }
}
