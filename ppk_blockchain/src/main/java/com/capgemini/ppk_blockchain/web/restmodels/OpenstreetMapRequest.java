package com.capgemini.ppk_blockchain.web.restmodels;

public class OpenstreetMapRequest {
    private double lat;
    private double lon;
    private String format;
    private int addressdetails;
    private int zoom;

    public OpenstreetMapRequest(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.format = "JSON";
        this.addressdetails = 1;
        this.zoom = 1;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getAddressdetails() {
        return addressdetails;
    }

    public void setAddressdetails(int addressdetails) {
        this.addressdetails = addressdetails;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
}
