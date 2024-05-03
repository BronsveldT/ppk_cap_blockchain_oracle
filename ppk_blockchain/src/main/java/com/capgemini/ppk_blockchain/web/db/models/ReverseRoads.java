package com.capgemini.ppk_blockchain.web.db.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ReverseRoads {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "latitude")
    private double lat;
    @Column(name = "longitude")
    private double lon;
    @Column(name = "road_category")
    private int roadCategory;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "road_admin_type")
    private String roadAdminType;

    public ReverseRoads(Integer id, double lat, double lon, int roadCategory, String streetName, String roadAdminType) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.roadCategory = roadCategory;
        this.streetName = streetName;
        this.roadAdminType = roadAdminType;
    }

    public ReverseRoads(double lat, double lon, int roadCategory, String streetName, String roadAdminType) {
        this.lat = lat;
        this.lon = lon;
        this.roadCategory = roadCategory;
        this.streetName = streetName;
        this.roadAdminType = roadAdminType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getRoadCategory() {
        return roadCategory;
    }

    public void setRoadCategory(int roadCategory) {
        this.roadCategory = roadCategory;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getRoadAdminType() {
        return roadAdminType;
    }

    public void setRoadAdminType(String roadAdminType) {
        this.roadAdminType = roadAdminType;
    }

    @Override
    public String toString() {
        return "ReverseRoads{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", roadCategory='" + roadCategory + '\'' +
                ", streetName='" + streetName + '\'' +
                ", roadAdminType='" + roadAdminType + '\'' +
                '}';
    }
}
