package com.ide.codekit.casttotv.Model;

public class DataModel {
    private long id;
    private String path;
    private String name;
    private String size;

    public DataModel(long id, String path, String name, String size) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
