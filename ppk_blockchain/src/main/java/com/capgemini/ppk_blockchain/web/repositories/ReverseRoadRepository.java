package com.capgemini.ppk_blockchain.web.repositories;

import com.capgemini.ppk_blockchain.web.db.models.ReverseRoads;
import org.springframework.data.repository.CrudRepository;

public interface ReverseRoadRepository extends CrudRepository<ReverseRoads, Long> {

    ReverseRoads findByLatAndLon(double lat, double lon);

}
