package com.example.price_simulation_tool.controller;

import com.example.price_simulation_tool.service.PricePredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/price-prediction")
public class PricePredictionController {

    private final PricePredictionService pricePredictionService;

    @Autowired
    public PricePredictionController(PricePredictionService pricePredictionService) {
        this.pricePredictionService = pricePredictionService;
    }

    @GetMapping("/calculate")
    public BigDecimal calculatePredictedPrice(
            @RequestParam BigDecimal initialPrice,
            @RequestParam BigDecimal time,
            @RequestParam BigDecimal randomShock) {
        return pricePredictionService.calculatePredictedPrice(initialPrice, time, randomShock);
    }
}