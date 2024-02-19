package com.ide.codekit.casttotv.Model;

public class DataModel {
    private String serviceName;
    private String address;
    private String hostAddress;

    public DataModel(String serviceName, String address, String hostAddress) {
        this.serviceName = serviceName;
        this.address = address;
        this.hostAddress = hostAddress;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    // Optionally, override toString() for debugging or logging purposes
    @Override
    public String toString() {
        return "DataModel{" +
                "serviceName='" + serviceName + '\'' +
                ", address='" + address + '\'' +
                ", hostAddress='" + hostAddress + '\'' +
                '}';
    }
}
