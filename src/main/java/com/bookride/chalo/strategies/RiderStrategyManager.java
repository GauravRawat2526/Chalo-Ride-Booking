package com.bookride.chalo.strategies;

import com.bookride.chalo.strategies.Impl.DriverMatchingHighestRatedDriverStrategy;
import com.bookride.chalo.strategies.Impl.DriverMatchingNearestDriverStrategy;
import com.bookride.chalo.strategies.Impl.RideFareDefaultCalculationStrategy;
import com.bookride.chalo.strategies.Impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RiderStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final RideFareDefaultCalculationStrategy rideFareDefaultCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double rating){
        if(rating >= 4.8){
            return driverMatchingHighestRatedDriverStrategy;
        }
        return driverMatchingNearestDriverStrategy;
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        LocalTime sergeStartTime = LocalTime.of(16 , 0);
        LocalTime sergeEndTime = LocalTime.of(21 ,0);
        LocalTime currentTime = LocalTime.now();

        return currentTime.isAfter(sergeStartTime) &&
                currentTime.isBefore(sergeEndTime) ? rideFareSurgePricingFareCalculationStrategy : rideFareDefaultCalculationStrategy;



    }

}
