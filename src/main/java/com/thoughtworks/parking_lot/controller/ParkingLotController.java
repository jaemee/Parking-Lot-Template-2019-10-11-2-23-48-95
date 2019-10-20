package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/parkingLots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping(headers = {"Content-type=application/json"})
    @ResponseStatus(code = CREATED)
    public ParkingLot addParkingLot(@RequestBody ParkingLot parkingLot){
        return parkingLotService.save(parkingLot);
    }
}
