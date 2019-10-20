package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLotOrder;
import com.thoughtworks.parking_lot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "parkingLots")
public class ParkingLotOrderController {

    @Autowired
    private ParkingLotOrderService parkingLotOrderSvc;

    @PostMapping(path="/{parkingLotName}/orders", headers = {"Content-type=application/json"})
    @ResponseStatus(code = CREATED)
    public ParkingLotOrder addParkingLotOrder(@PathVariable("parkingLotName") String parkingLotName,
                                              @RequestBody ParkingLotOrder order){
        return parkingLotOrderSvc.save(parkingLotName, order);
    }

}
