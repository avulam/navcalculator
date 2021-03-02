package com.turvo.navcalculator.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holding {
    private String date;
    private String security;
    private Integer quantity;
    private String portfolio;
}
