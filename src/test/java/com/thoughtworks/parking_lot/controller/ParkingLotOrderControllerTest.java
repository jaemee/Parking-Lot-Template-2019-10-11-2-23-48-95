package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.entity.ParkingLotOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotOrderRepository;
import com.thoughtworks.parking_lot.service.ParkingLotOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ParkingLotOrderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @MockBean
    private ParkingLotOrderService parkingLotOrderSvc;

    @MockBean
    private ParkingLotOrderRepository parkingLotOrderRepo;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private ObjectMapper objectMapper;

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

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Close")));
    }
}