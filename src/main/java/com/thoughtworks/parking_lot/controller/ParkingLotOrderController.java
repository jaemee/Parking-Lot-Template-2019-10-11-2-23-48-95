package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLotOrder;
import com.thoughtworks.parking_lot.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/parkingLots/{parkingLotName}/orders")
public class ParkingLotOrderController {

    @Autowired
    private ParkingLotOrderService parkingLotOrderSvc;

    public ParkingLotOrderController(ParkingLotOrderService parkingLotOrderSvc) {
        this.parkingLotOrderSvc = parkingLotOrderSvc;
    }

    @PostMapping(headers = {"Content-type=application/json"})
    @ResponseStatus(code = CREATED)
    public ParkingLotOrder addParkingLotOrder(@PathVariable String parkingLotName,
                                              @RequestBody ParkingLotOrder order) throws NotFoundException {
        return parkingLotOrderSvc.save(parkingLotName, order);
    }

    @PatchMapping(path="/{plateNUmber}")
    public ResponseEntity<ParkingLotOrder> updateOrder(@PathVariable String plateNUmber){
        if(parkingLotOrderSvc.isCarLeft(plateNUmber)){
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }
}
