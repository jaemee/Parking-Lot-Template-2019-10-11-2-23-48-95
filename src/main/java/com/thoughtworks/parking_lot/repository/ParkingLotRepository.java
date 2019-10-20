package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("parkingLot")
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    Optional<ParkingLot> findByNameLike(String name);

    @Query("SELECT COUNT(p.*) FROM ParkingLot p WHERE p.orders.status='Open' AND p.name=:name")
    Integer getTotalNumberOfParkedCars(@Param("name") String name);
}
