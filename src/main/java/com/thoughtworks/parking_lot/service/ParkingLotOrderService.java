package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.entity.ParkingLotOrder;
import com.thoughtworks.parking_lot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingLotOrderService {

    private static final String OPEN = "Open";

    @Autowired
    private ParkingLotOrderRepository repo;

    @Autowired
    private ParkingLotService parkingLotSvc;

    public ParkingLotOrderService(@Qualifier("orders") ParkingLotOrderRepository repo) {
        this.repo = repo;
    }

    public ParkingLotOrder save(String parkingLotName, ParkingLotOrder order) {
        Optional<ParkingLot> parkingLot = parkingLotSvc.findByNameLike(parkingLotName);
        if(parkingLot.isPresent()){
            order.setParkingLot(parkingLot.get());
            order.setStatus(OPEN);
            order.setCreateTime(getCurrentTime());
            return repo.save(order);
        }
        return null;
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
