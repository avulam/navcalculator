package com.turvo.navcalculator.service;

import com.google.gson.Gson;
import com.turvo.navcalculator.Model.Holding;
import com.turvo.navcalculator.Model.HoldingsPerPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class HoldingService {

    @Autowired
    private RestTemplate restTemplate;

    private HoldingsPerPage getHoldingInfoPerPage(String pageLink) {
        String holdingsJsonStr = restTemplate.getForObject(pageLink, String.class);
        Gson g = new Gson();
        HoldingsPerPage holdingsPerPage = g.fromJson(holdingsJsonStr, HoldingsPerPage.class);
        return holdingsPerPage;
    }

    public List<Holding> getHoldingsAsOnDate(String asOnDay) {
        List<Holding> allHoldingData = new ArrayList<Holding>();
        String pageLink = "https://raw.githubusercontent.com/arcjsonapi/HoldingValueCalculator/master/paging/holding_start";

        while (!ObjectUtils.isEmpty(pageLink)) {
            HoldingsPerPage holdingsPerPage = getHoldingInfoPerPage(pageLink);
            if (ObjectUtils.isEmpty(holdingsPerPage)) {
                System.out.println("HoldingsPerPage Data  returned from site is null ");
                return null;
            }
            List<Holding> holding = searchHoldingDataAsOnDay(holdingsPerPage.getData(), asOnDay);
            allHoldingData.addAll(holding);
            pageLink = holdingsPerPage.getNextPage();
        }

        return allHoldingData;
    }

    private List<Holding> searchHoldingDataAsOnDay(ArrayList<Holding> data, String asOnDay) {
        List<Holding> results = data.stream().filter(item ->
                item.getDate().equals(asOnDay))
                .collect(toList());

        return results;
    }

}

