package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.entity.ParkingLotOrder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("orders")
public interface ParkingLotOrderRepository extends JpaRepository<ParkingLotOrder, Long> {
    Optional<ParkingLotOrder> findByPlateNumberLike(String plateNumber);
}
