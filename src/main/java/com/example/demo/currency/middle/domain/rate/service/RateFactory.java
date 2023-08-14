package com.example.demo.currency.middle.domain.rate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/** 匯率工廠 */
@Component
public class RateFactory {

  private final Map<RateType, ExchangeRateCalculator> exchangeRateCalculatorMap;

  @Autowired
  private RateFactory(List<ExchangeRateCalculator> exchangeRateCalculators) {
    exchangeRateCalculatorMap =
        exchangeRateCalculators.stream()
            .collect(
                Collectors.toUnmodifiableMap(
                    ExchangeRateCalculator::getRateType, Function.identity()));
  }

  public ExchangeRateCalculator getExchangeRateCalculator(RateType rateType) {
    return Optional.ofNullable(exchangeRateCalculatorMap.get(rateType))
        .orElseThrow(IllegalArgumentException::new);
  }

  /** 匯率模式 */
  public enum RateType {
    GetImbRate, // 取中台暫存匯率
    GetSpotRate, // 取即時匯率
  }
}
