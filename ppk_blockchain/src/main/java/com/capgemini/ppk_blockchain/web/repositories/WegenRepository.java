package com.capgemini.ppk_blockchain.web.repositories;

import com.capgemini.ppk_blockchain.web.db.models.Wegen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WegenRepository extends CrudRepository<Wegen, Integer> {
    @Query(value = "SELECT * FROM WEGEN WHERE street_name = :streetName AND wps_name = :placeName", nativeQuery = true)
    Wegen findWegenByStreetName(String streetName, String placeName);

}
