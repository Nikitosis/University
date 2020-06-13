package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ministry implements JsonObject{
    private String name;
    private List<Organization> organizations = new ArrayList<>();

    @Override
    public String toJson() {
        String organizationsStr = organizations.stream()
                .map(Organization::toJson)
                .collect(Collectors.joining(", "));
        organizationsStr = "[" + organizationsStr + "]";

        return String.format("{\"name\":\"%s\", \"organizations\":%s}", name, organizationsStr);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public void addOrganization(Organization organization) {
        organization.setParentMinistry(this);
        organizations.add(organization);
    }

    public static class Builder {
        private Ministry ministry;

        public Builder() {
            ministry = new Ministry();
        }

        public Builder name(String name) {
            ministry.setName(name);
            return this;
        }

        public Builder organizations(List<Organization> organizations) {
            organizations.forEach(organization -> ministry.addOrganization(organization));
            return this;
        }

        public Ministry build() {
            return ministry;
        }
    }
}
