package com.turvo.navcalculator.controller;

import com.turvo.navcalculator.Model.Holding;
import com.turvo.navcalculator.Model.MarketPrice;
import com.turvo.navcalculator.response.HoldingValueOnGivenDay;
import com.turvo.navcalculator.service.HoldingService;
import com.turvo.navcalculator.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/holdingvalue")
public class NavCalculationContoller {

    @Autowired
    private HoldingService holdingService;

    @Autowired
    private PricingService pricingService;

    @GetMapping("/{date}")
    public HoldingValueOnGivenDay holdingValueForGivenDay(@PathVariable("date") String asOnDay) {
        String nav = "0.00";
        HoldingValueOnGivenDay holdingValueOnGivenDay = new HoldingValueOnGivenDay();
        try {
            nav = calculateNavForGivenDay(asOnDay);
            holdingValueOnGivenDay.setHoldingValue(nav);
            holdingValueOnGivenDay.setRemarks("SUCCESS");
        } catch (Exception e) {
            holdingValueOnGivenDay.setRemarks("Error Processing");

        }
        holdingValueOnGivenDay.setAsOnDay(asOnDay);


        return holdingValueOnGivenDay;
    }

    private List<Holding> findHoldingsForGivenDay(String asOnDay) {
        return holdingService.getHoldingsAsOnDate(asOnDay);
    }

    private List<MarketPrice> findMarketPricesForGivenDay(String asOnDay) {
        return pricingService.getPricingAsOnDate(asOnDay);
    }

    private String calculateNavForGivenDay(String asOnDay) {
        List<Holding> holdings = findHoldingsForGivenDay(asOnDay);
        List<MarketPrice> marketPriceLst = findMarketPricesForGivenDay(asOnDay);
        Double totalNav = 0.0;
        for (Holding holding : holdings) {
            MarketPrice marketPrice = getMarketPriceForHolding(holding.getSecurity(), marketPriceLst);
            totalNav = totalNav + (marketPrice.getPrice() != null ? marketPrice.getPrice() * holding.getQuantity() : 0.0);
        }
        return new DecimalFormat("$#.00").format(totalNav);
    }

    private MarketPrice getMarketPriceForHolding(String security, List<MarketPrice> marketPriceLst) {
        return marketPriceLst.stream().filter(item -> item.getSecurity().equals(security)).findAny().orElse(null);
    }


}
