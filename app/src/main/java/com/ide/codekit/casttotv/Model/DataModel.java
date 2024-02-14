package com.ide.codekit.casttotv.Model;

public class DataModel {
    private long id;
    private String path;
    private String name;
    private double sizeInMB;

    public DataModel(long id, String path, String name, double sizeInMB) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.sizeInMB = sizeInMB;
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

    public double getSizeInMB() {
        return sizeInMB;
    }

    public void setSizeInMB(double sizeInMB) {
        this.sizeInMB = sizeInMB;
    }
}
