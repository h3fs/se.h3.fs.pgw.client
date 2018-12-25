package se.h3.fs.pgw.api;

import java.io.Serializable;

public class ModuleInfo implements Serializable {
    String name;
    String path;
    String description;

    public ModuleInfo() {
    }

    public ModuleInfo(String name, String path, String description) {
        this.name = name;
        this.path = path;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getName();
    }
}
