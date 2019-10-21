package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.entity.ParkingLotOrder;
import com.thoughtworks.parking_lot.repository.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingLotOrderService {

    private static final String OPEN = "Open";
    private static final String CLOSE = "Close";

    @Autowired
    private ParkingLotOrderRepository parkingLotOrderRepo;

    @Autowired
    private ParkingLotService parkingLotSvc;

    public ParkingLotOrderService(@Qualifier("orders") ParkingLotOrderRepository parkingLotOrderRepo) {
        this.parkingLotOrderRepo = parkingLotOrderRepo;
    }

    public ParkingLotOrder save(String parkingLotName, ParkingLotOrder order) throws NotFoundException, BadRequest {
        Optional<ParkingLot> parkingLot = parkingLotSvc.findByNameLike(parkingLotName);
        if(parkingLot.isPresent()){
            if(parkingLotSvc.isParkingLotAvailable(parkingLotName)){
                order.setParkingLot(parkingLot.get());
                order.setStatus(OPEN);
                order.setCreateTime(getCurrentTime());
                return parkingLotOrderRepo.save(order);
            }else {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    public ParkingLotOrder findByPlateNumberLike(String plateNumber){
        return parkingLotOrderRepo.findByPlateNumberLike(plateNumber).get();
    }

    public boolean isCarLeft(String plateNumber) {
        Optional<ParkingLotOrder> order = parkingLotOrderRepo.findByPlateNumberLike(plateNumber);
        if(order.isPresent()){
            order.get().setCloseTime(getCurrentTime());
            order.get().setStatus(CLOSE);
            parkingLotOrderRepo.save(order.get());
            return true;
        }
        return false;
    }
}
