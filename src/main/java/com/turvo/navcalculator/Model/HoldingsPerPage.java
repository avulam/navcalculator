package com.turvo.navcalculator.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoldingsPerPage {
    private Integer totalRecords;
    private Integer recordsPerPage;
    private Integer page;
    private String nextPage;
    //private ArrayList<Map<String, String>> data;
    private ArrayList<Holding> data;


}
