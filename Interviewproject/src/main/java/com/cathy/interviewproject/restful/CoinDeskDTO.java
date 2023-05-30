package com.cathy.interviewproject.restful;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CoinDeskDTO {
    private CoinDeskTimeDTO time;
    private String disclaimer;
    private String chartName;
    private Map<String,CoinDeskCurrencyDTO> bpi;
}
