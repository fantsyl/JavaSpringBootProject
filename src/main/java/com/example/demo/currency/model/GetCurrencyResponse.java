package com.example.demo.currency.model;

import com.example.demo.infrastructure.util.JsonToString;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Getter
@Builder
public class GetCurrencyResponse {

    /**
     * 匯率列表
     */
    @ApiModelProperty(value = "匯率列表")
    @JsonProperty("currencyList")
    private final List<CurrencyResponse> currencyList;

    @Getter
    @Builder
    public static class CurrencyResponse {

        /**
         * 幣別
         */
        @ApiModelProperty(value = "幣別", example = "USD", required = true)
        @JsonProperty("currency")
        private final String currency;

        /**
         * 幣別 中文
         */
        @ApiModelProperty(value="幣別中文",example="美元",required = true)
        @JsonProperty("currencyCn")
        private final String currencyCn;

        /**
         * 牌告客戶買價
         */
        @ApiModelProperty(value = "牌告客戶買價", example = "28.0300", notes = "取小數點後4位,無條件捨去", required = true)
        @JsonProperty("buyRate")
        @JsonToString(isRounding = true, scale = 4, mode = RoundingMode.DOWN, isDecimalFormat = true, minimumFractionDigits = 4, maximumFractionDigits = 4)
        private final BigDecimal buyRate;

        /**
         * 牌告客戶賣價
         */
        @ApiModelProperty(value = "牌告客戶賣價", example = "28.3500", notes = "取小數點後4位,無條件捨去", required = true)
        @JsonProperty("sellRate")
        @JsonToString(isRounding = true, scale = 4, mode = RoundingMode.DOWN, isDecimalFormat = true, minimumFractionDigits = 4, maximumFractionDigits = 4)
        private final BigDecimal sellRate;

        /**
         * 客戶買價最低點往前推天數
         */
        @ApiModelProperty(value = "客戶買價最低點往前推天數", example = "30")
        @JsonProperty("lowBuyPastDay")
        @JsonToString
        private final Integer lowBuyPastDay;

        /**
         * 牌告更新時間
         */
        @ApiModelProperty(value = "牌告更新時間", example = "1654671241000")
        @JsonProperty("FXUpdateTime")
        @JsonFormat(shape = JsonFormat.Shape.NUMBER)
        private final Date fxUpdateTime;

    }


}
