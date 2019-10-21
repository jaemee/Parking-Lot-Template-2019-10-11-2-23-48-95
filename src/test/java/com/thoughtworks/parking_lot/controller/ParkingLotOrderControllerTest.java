package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.entity.ParkingLotOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotOrderRepository;
import com.thoughtworks.parking_lot.service.ParkingLotOrderService;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotOrderController.class)
@ActiveProfiles(profiles = "test")
public class ParkingLotOrderControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParkingLotOrderService parkingLotOrderSvc;

    @MockBean
    private ParkingLotOrderRepository parkingLotOrderRepo;

    @MockBean
    private ParkingLotService parkingLotSvc;


    @Test
    public void should_add_parking_lot_order() throws Exception {
        ParkingLotOrder order = new ParkingLotOrder();
        ResultActions result = mvc.perform(post("/parkingLots/{name}/orders", "Park1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));
        result.andExpect(status().isCreated());
    }

    @Test
    public void should_update_parking_lot_order_when_leave() throws Exception {
        ParkingLotOrder order = new ParkingLotOrder();
        order.setPlateNumber("JJJ123");
        when(parkingLotOrderRepo.findByPlateNumberLike("JJJ123")).thenReturn(Optional.of(order));
        when(parkingLotOrderSvc.isCarLeft("JJJ123")).thenReturn(true);

        ResultActions result = mvc.perform(patch("/parkingLots/Park3/orders/{plateNUmber}",
                 "JJJ123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        result.andExpect(status().isOk());
    }

    @Test
    void should_return_message_when_parking_is_full() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        when(parkingLot.getName()).thenReturn("Park2");

        when(parkingLotSvc.isParkingLotAvailable("Park2")).thenReturn(false);

        ResultActions result = mvc.perform(post("/parkingLots/{name}/orders", "Park2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingLot)));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Parking lot full!")));
    }
}