package com.example.price_simulation_tool.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

@Service
public class PricePredictionService {

    private static final BigDecimal DEFAULT_DRIFT_RATE = BigDecimal.valueOf(0.2);
    private static final BigDecimal DEFAULT_VOLATILITY = BigDecimal.valueOf(0.3);

    private static final Random random = new Random();

    public BigDecimal calculatePredictedPrice(BigDecimal initialPrice, BigDecimal time) {
        // constant_part = drift_rate - (volatility^2 /2)
        BigDecimal driftConstantPart = DEFAULT_DRIFT_RATE
                .subtract(BigDecimal.valueOf(Math.pow(DEFAULT_VOLATILITY.doubleValue(), 2) / 2));
        BigDecimal drift = driftConstantPart.multiply(time);
        BigDecimal diffusion = calculateWienerProcess(time).multiply(DEFAULT_VOLATILITY);
        BigDecimal driftDiffusionSum = drift.add(diffusion);
        return initialPrice.multiply(BigDecimal.valueOf(Math.exp(driftDiffusionSum.doubleValue())));
    }

    public BigDecimal calculateWienerProcess(BigDecimal deltaT) {
        double dt = deltaT.doubleValue();
        double u1 = random.nextDouble();
        double u2 = random.nextDouble();
        double standardNormal = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2.0 * Math.PI * u2);

        double increment = standardNormal * Math.sqrt(dt);

        return new BigDecimal(increment, MathContext.DECIMAL64);
    }
}