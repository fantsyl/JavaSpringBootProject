package com.cathy.interviewproject.restful;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyDTO {
    private String currency;
    private String currency_name_cn;
    private Float exchange_rate;
    private String updated_date;

}
