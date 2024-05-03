package com.capgemini.ppk_blockchain.blockchain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Road {
    private String roadAdminType;
    private String streetName;
    private String wpsName;
    private String adminNumber;
    private String adminName;
    private String roadAdminName;

    public Road(String roadAdminType, String streetName, String wpsName, String adminNumber, String adminName, String roadAdminName) {
        this.roadAdminType = roadAdminType;
        this.streetName = streetName;
        this.wpsName = wpsName;
        this.adminNumber = adminNumber;
        this.adminName = adminName;
        this.roadAdminName = roadAdminName;
    }

}
