package com.capgemini.ppk_blockchain.web.db.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReverseRoads {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "latitude")
    private double lat;
    @Column(name = "longitude")
    private double lon;
    @Column(name = "road_category")
    private Integer roadCategory;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "road_admin_type")
    private String roadAdminType;
    @Column(name = "admin_number")
    private Integer adminNumber;
    @Column(name = "admin_name")
    private String adminName;
    @Column(name = "road_admin_name")
    private String roadAdminName;

    public ReverseRoads(Integer id, double lat, double lon,
                        int roadCategory,
                        String streetName, String roadAdminType) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.roadCategory = roadCategory;
        this.streetName = streetName;
        this.roadAdminType = roadAdminType;
    }

    public ReverseRoads(double lat, double lon, Integer roadCategory, String streetName, String roadAdminType
    , Integer adminNumber, String adminName, String roadAdminName) {
        this.lat = lat;
        this.lon = lon;
        this.roadCategory = roadCategory;
        this.streetName = streetName;
        this.roadAdminType = roadAdminType;
        this.adminNumber = adminNumber;
        this.adminName = adminName;
        this.roadAdminName = roadAdminName;
    }
}
