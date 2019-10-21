package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepo;

    public ParkingLotService(@Qualifier("parkingLot") ParkingLotRepository parkingLotRepo) {
        this.parkingLotRepo = parkingLotRepo;
    }

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

    public boolean isParkingLotAvailable(String name) throws NotFoundException {
        int totalAvailableSpace = 0;
        Optional<ParkingLot> parkingLot = findByNameLike(name);
        if(parkingLot.isPresent()){
            int totalParkedCars = parkingLotRepo.getTotalNumberOfParkedCars(name).size();
            totalAvailableSpace = parkingLot.get().getCapacity() - totalParkedCars;

            if(totalAvailableSpace >= 0){
                return true;
            }
            return false;
        }
        throw new NotFoundException("Parking lot not found!");
    }
}
