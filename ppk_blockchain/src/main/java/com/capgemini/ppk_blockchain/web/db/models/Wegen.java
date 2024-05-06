package com.capgemini.ppk_blockchain.web.db.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Wegen {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "road_category")
    private Integer roadCategory;

    @Column(name = "road_admin_type")
    private String roadAdminType;

    @Column(name = "road_number")
    private String roadNumber;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "wps_name")
    private String wpsName;

    @Column(name = "admin_number")
    private Integer adminNumber;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "road_admin_name")
    private String roadAdminName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoadCategory() {
        return roadCategory;
    }

    public void setRoadCategory(Integer roadCategory) {
        this.roadCategory = roadCategory;
    }

    public String getRoadAdminType() {
        return roadAdminType;
    }

    public void setRoadAdminType(String roadAdminType) {
        this.roadAdminType = roadAdminType;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getWpsName() {
        return wpsName;
    }

    public void setWpsName(String wpsName) {
        this.wpsName = wpsName;
    }

    public Integer getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(Integer adminNumber) {
        this.adminNumber = adminNumber;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getRoadAdminName() {
        return roadAdminName;
    }

    public void setRoadAdminName(String roadAdminName) {
        this.roadAdminName = roadAdminName;
    }

    @Override
    public String toString() {
        return "Wegen{" +
                "id=" + id +
                ", roadCategory=" + roadCategory +
                ", roadAdminType='" + roadAdminType + '\'' +
                ", roadNumber='" + roadNumber + '\'' +
                ", streetName='" + streetName + '\'' +
                ", wpsName='" + wpsName + '\'' +
                ", adminNumber=" + adminNumber +
                ", adminName='" + adminName + '\'' +
                ", roadAdminName='" + roadAdminName + '\'' +
                '}';
    }
}
