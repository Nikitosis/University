package com.task1.work;

import java.util.List;

public class ComponentType {
    private List<Defect> defects;
    private List<Function> functions;

    public ComponentType(List<Defect> defects, List<Function> functions) {
        this.defects = defects;
        this.functions = functions;
    }

    public List<Defect> getDefects() {
        return defects;
    }

    public List<Function> getFunctions() {
        return functions;
    }
}
