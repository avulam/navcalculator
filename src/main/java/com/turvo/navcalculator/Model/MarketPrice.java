package com.turvo.navcalculator.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketPrice {
    private String date;
    private String security;
    private Double price;
}
