package com.cathy.interviewproject.restful;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoinDeskCurrencyDTO {
    private String code;
    private String symbol;
    private String rate;
    private String description;
    private Float rate_float;
}
