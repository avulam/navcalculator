package com.turvo.navcalculator.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoldingValueOnGivenDay {
    String asOnDay;
    String holdingValue;
    String remarks;

}
