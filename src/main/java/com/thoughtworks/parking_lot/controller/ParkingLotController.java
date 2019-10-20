package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

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

    @DeleteMapping(path="/{name}")
    public ResponseEntity<ParkingLot> deleteParkingLot(@PathVariable("name") String name){
        if(parkingLotService.delete(name)){
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping
    public Iterable<ParkingLot> getAllParkingLots(@RequestParam(defaultValue = "0", required = false) int page){
        return parkingLotService.findAll(page);
    }

    @GetMapping(path="/{name}")
    public ParkingLot getSpecificParkingLot(@PathVariable("name") String name){
        return parkingLotService.findByNameLike(name).get();
    }

    @PatchMapping(path="/{name}")
    public ResponseEntity<ParkingLot>  updateParkingLot(@PathVariable("name") String name, @RequestBody ParkingLot parkingLot){
        if(parkingLotService.isUpdated(name, parkingLot.getCapacity())){
            return new ResponseEntity<>(OK);
        }
        return  new ResponseEntity<>(NOT_FOUND);
    }

}
