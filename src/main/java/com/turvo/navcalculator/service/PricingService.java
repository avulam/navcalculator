package com.turvo.navcalculator.service;

import com.google.gson.Gson;
import com.turvo.navcalculator.Model.MarketPrice;
import com.turvo.navcalculator.Model.MarketPricesPerPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PricingService {

    @Autowired
    private RestTemplate restTemplate;

    private MarketPricesPerPage getPricingInfoPerPage(String pageLink) {
        String marketPricesJsonStr = restTemplate.getForObject(pageLink, String.class);
        Gson g = new Gson();
        MarketPricesPerPage marketPricesPerPage = g.fromJson(marketPricesJsonStr, MarketPricesPerPage.class);
        return marketPricesPerPage;
    }

    public List<MarketPrice> getPricingAsOnDate(String asOnDay) {
        List<MarketPrice> allMarketPriceData = new ArrayList<MarketPrice>();
        String pageLink = "https://raw.githubusercontent.com/arcjsonapi/HoldingValueCalculator/master/paging/pricing_start";

        while (!ObjectUtils.isEmpty(pageLink)) {
            MarketPricesPerPage marketPricesPerPage = getPricingInfoPerPage(pageLink);
            if (ObjectUtils.isEmpty(marketPricesPerPage)) {
                System.out.println("MarketPricesPerPage Data  returned from site is null ");
                return null;
            }
            List<MarketPrice> marketPriceData = searchMarketPriceDataAsOnDay(marketPricesPerPage.getData(), asOnDay);
            allMarketPriceData.addAll(marketPriceData);
            pageLink = marketPricesPerPage.getNextPage();
        }

        return allMarketPriceData;
    }

    private List<MarketPrice> searchMarketPriceDataAsOnDay(ArrayList<MarketPrice> data, String asOnDay) {

        List<MarketPrice> results = data.stream().filter(item ->
                item.getDate().equals(asOnDay))
                .collect(toList());

        return results;
    }


}
