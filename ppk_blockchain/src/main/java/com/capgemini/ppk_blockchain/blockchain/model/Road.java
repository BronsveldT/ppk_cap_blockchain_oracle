package com.capgemini.ppk_blockchain.blockchain.model;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Road {
    @ToStringExclude private static final String[] roadCategoryNumberToRoadAdminType = {"R", "P", "G", "W", "O"};
    private String roadAdminType;
    private String streetName;
    private int adminNumber;
    private double distanceTravelledOn;
    private String adminName;
    private String roadAdminName;
    private String municipality;
    private String state;

    public Road(String roadAdminType, String streetName, int adminNumber, String adminName, String roadAdminName) {
        this.roadAdminType = roadAdminType;
        this.streetName = streetName;
        this.adminNumber = adminNumber;
        this.adminName = adminName;
        this.roadAdminName = roadAdminName;
        distanceTravelledOn = 0;
    }

    public Road(int roadCategory, String streetName, String municipality, String state) {
        this.roadAdminType = roadCategoryNumberToRoadAdminType[roadCategory -1];
        this.streetName = streetName;
        this.municipality = municipality;
        this.state = state;
    }

    public void addDistanceTravelled(double kilometersTravelled){
        distanceTravelledOn += kilometersTravelled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // I think this is always false due to the fact some fields can be null.
        if (o == null || getClass() != o.getClass()) return false;
        Road road = (Road) o;
        return adminNumber == road.adminNumber && Objects.equals(roadAdminType, road.roadAdminType) && Objects.equals(streetName, road.streetName) && Objects.equals(adminName, road.adminName) && Objects.equals(roadAdminName, road.roadAdminName);
    }

    public boolean equalsSkippedRoad(Object o) {
        if (this == o) return true; // I think this is always false due to the fact some fields can be null.
        if (o == null || getClass() != o.getClass()) return false;
        Road road = (Road) o;
        return this.roadAdminType.equals(road.roadAdminType) && Objects.equals(this.streetName, road.streetName) &&
                Objects.equals(this.municipality, road.municipality) && Objects.equals(this.state, road.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roadAdminType, streetName, adminNumber, adminName, roadAdminName);
    }
}
