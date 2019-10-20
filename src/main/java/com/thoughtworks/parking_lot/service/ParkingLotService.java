package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepo;

    public ParkingLot save(ParkingLot parkingLot) {
       return parkingLotRepo.save(parkingLot);
    }

    public boolean delete(String name) {
        Optional<ParkingLot> parkingLot = parkingLotRepo.findByNameLike(name);
        if(parkingLot.isPresent()){
            parkingLotRepo.delete(parkingLot.get());
            return true;
        }
        return false;
    }
}
