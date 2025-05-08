package com.example.price_simulation_tool.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricePredictionService {

    private static final BigDecimal DEFAULT_DRIFT_RATE = BigDecimal.valueOf(0.2);
    private static final BigDecimal DEFAULT_VOLATILITY = BigDecimal.valueOf(0.3);

    public BigDecimal calculatePredictedPrice(BigDecimal initialPrice, BigDecimal time, BigDecimal randomShock) {
        // constant_part = drift_rate - (volatility^2 /2)
        BigDecimal driftConstantPart = DEFAULT_DRIFT_RATE
                .subtract(BigDecimal.valueOf(Math.pow(DEFAULT_VOLATILITY.doubleValue(), 2) / 2));
        BigDecimal drift = driftConstantPart.multiply(time);
        BigDecimal diffusion = randomShock.multiply(DEFAULT_VOLATILITY);
        BigDecimal driftDiffusionSum = drift.add(diffusion);
        return initialPrice.multiply(BigDecimal.valueOf(Math.exp(driftDiffusionSum.doubleValue())));
    }
}