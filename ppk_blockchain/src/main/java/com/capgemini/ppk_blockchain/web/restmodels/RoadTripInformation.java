package com.capgemini.ppk_blockchain.web.restmodels;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class RoadTripInformation {
    private Set<String> guessedRoads;
    private double rideCosts;

    public RoadTripInformation(Set<String> guessedRoads, double rideCosts) {
        this.guessedRoads = guessedRoads;
        this.rideCosts = rideCosts;
    }
}
