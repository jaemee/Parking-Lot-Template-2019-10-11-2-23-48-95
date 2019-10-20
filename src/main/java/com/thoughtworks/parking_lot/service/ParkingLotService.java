package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Iterable<ParkingLot> findAll(int page) {
        return parkingLotRepo.findAll(PageRequest.of(page,15));
    }

    public Optional<ParkingLot> findByNameLike(String name) {
        return parkingLotRepo.findByNameLike(name);
    }

    public boolean isUpdated(String name, Integer capacity) {
        Optional<ParkingLot> parkingLot = findByNameLike(name);
        if(parkingLot.isPresent()){
            parkingLot.get().setCapacity(capacity);
            parkingLotRepo.save(parkingLot.get());
            return true;
        }
        return false;
    }
}
