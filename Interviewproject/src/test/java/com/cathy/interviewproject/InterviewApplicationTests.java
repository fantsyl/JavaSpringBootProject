package com.cathy.interviewproject;

import com.cathy.interviewproject.restful.CurrencyController;
import com.cathy.interviewproject.restful.CurrencyDTO;
import com.cathy.interviewproject.service.CurrencyService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class InterviewApplicationTests {

    @Autowired
    private CurrencyController currencyController;
    @Autowired
    private CurrencyService currencyService;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void create() {
        CurrencyDTO inputDTO = new CurrencyDTO();
        inputDTO.setUpdated_date("2023/05/30 16:04:04");
        inputDTO.setExchange_rate(0.22F);
        inputDTO.setCurrency_name_cn("日幣");
        inputDTO.setCurrency("JPY");
        ResponseEntity<?> res = currencyController.create(inputDTO);
        assertEquals(inputDTO,new Gson().fromJson(res.getBody().toString(),CurrencyDTO.class));
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void read() {
        CurrencyDTO inputDTO = new CurrencyDTO();
        inputDTO.setUpdated_date("2023/05/6 16:04:04");
        inputDTO.setExchange_rate(0.22F);
        inputDTO.setCurrency_name_cn("日幣");
        inputDTO.setCurrency("JPY");
        ResponseEntity<?> res = currencyController.update(inputDTO);
        assertEquals(inputDTO,res.getBody());
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void update() {
        CurrencyDTO inputDTO = new CurrencyDTO();
        inputDTO.setUpdated_date("2023/05/6 16:04:04");
        inputDTO.setExchange_rate(0.23F);
        inputDTO.setCurrency_name_cn("日幣");
        inputDTO.setCurrency("JPY");
        ResponseEntity<?> res = currencyController.update(inputDTO);
        assertEquals(inputDTO,new Gson().fromJson(res.getBody().toString(),CurrencyDTO.class));
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void delete() {
        String currency = "JPY";
        currencyController.delete(currency);
        ResponseEntity<?> res = currencyController.getById(currency);
        assertNull(res.getBody());
    }
    @Test
    @Order(5)
    @Rollback(value = true)
    public void getCoinDeskJsonToDB() throws IOException, ParseException {
        List<CurrencyDTO> dtos = currencyService.createCoinDeskJsonToDB();
        assertNotNull(dtos);
    }


}
