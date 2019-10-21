package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("parkingLot")
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    Optional<ParkingLot> findByNameLike(String name);

    @Query(value = "SELECT p FROM ParkingLot p JOIN p.orders o WHERE o.status='Open' AND p.name=:name")
    List<ParkingLot> getTotalNumberOfParkedCars(@Param("name") String name);
}
