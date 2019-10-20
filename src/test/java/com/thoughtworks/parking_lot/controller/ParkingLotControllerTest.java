package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "test")
class ParkingLotControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParkingLotService parkingLotService;

    @MockBean
    private ParkingLotRepository parkingLotRepo;

    @Test
    void should_add_parkingLot() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        ResultActions result = mvc.perform(post("/parkingLots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingLot)));
        result.andExpect(status().isCreated());
    }

    @Test
    void should_return_ok_when_parking_lot_deleted() throws Exception {
        when(parkingLotService.delete("Parking 1")).thenReturn(true);
        ResultActions result = mvc.perform(delete("/parkingLots/Parking 1"));
        result.andExpect(status().isOk());
    }

    @Test
    void should_not_delete_and_return_not_found_when_parking_lot_not_exist() throws Exception {
        ResultActions result = mvc.perform(delete("/parkingLots/Parking 1"));
        result.andExpect(status().isNotFound());
    }

    @Test
    void should_return_parking_lots_with_pagination() throws Exception {
        ResultActions result = mvc.perform(get("/parkingLots")
                .contentType(MediaType.APPLICATION_JSON).param("page", "0"));
        result.andExpect(status().isOk());
    }

    @Test
    void should_return_specific_parking_lot_when_input_by_name() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("Parking1");
        parkingLot.setCapacity(15);
        when(parkingLotService.findByNameLike("Parking1")).thenReturn(Optional.of(parkingLot));
        ResultActions result = mvc.perform(get("/parkingLots/Parking1")
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.capacity", is(15)));
    }

    @Test
    void should_return_ok_when_update_parking_lot_success() throws Exception {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setCapacity(13);
        when(parkingLotService.findByNameLike("Parking1")).thenReturn(Optional.of(parkingLot));
        when(parkingLotService.isUpdated("Parking1", 13)).thenReturn(true);

        ResultActions result = mvc.perform(patch("/parkingLots/{name}", "Parking1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingLot)));

        result.andExpect(status().isOk());
    }
}