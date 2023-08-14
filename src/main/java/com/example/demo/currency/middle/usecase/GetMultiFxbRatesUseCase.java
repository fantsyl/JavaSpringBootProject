package com.example.demo.currency.middle.usecase;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.com.nextbank.ap6.forex.middle.domain.currency.Currency.CurrencyEnum;
import tw.com.nextbank.ap6.forex.middle.domain.rate.service.RateFactory;
import tw.com.nextbank.ap6.forex.middle.domain.rate.service.RateFactory.RateType;
import tw.com.nextbank.ap6.infra.util.validator.aop.FieldsValidate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** 取多筆指定幣別的Fxb匯率資訊 UseCase */
@Slf4j
@RequiredArgsConstructor
@Service
public class GetMultiFxbRatesUseCase {
  private final RateFactory rateFactory;

  /** 取多筆中台暫存匯率(含客戶優惠匯率) */
  @FieldsValidate
  public List<CurrencyRate> getImbRate(@NotNull UseCaseCommand command) {
    if (command.getQueryCurrencies().isEmpty()) {
      return new ArrayList<>();
    }

    List<CurrencyRate> result = new ArrayList<>();
    // 取各帳戶幣別的匯率
    rateFactory
        .getExchangeRateCalculator(RateType.GetImbRate)
        .getMultiFxbRate(
            command.getQueryCurrencies().stream()
                .map(CurrencyEnum::getByStringValue)
                .collect(Collectors.toList()))
        .forEach(
            fxRate -> {
              result.add(
                  CurrencyRate.builder()
                      .currency(fxRate.getPk().getCurrency().getValue())
                      .buyRate(fxRate.getFxbBuyRate())
                      .sellRate(fxRate.getFxbSellRate())
                      .cusBuyRate(fxRate.getCustomerBuyRate())
                      .cusSellRate(fxRate.getCustomerSellRate())
                      .lowBuyPastDay(fxRate.calculateLowBuyPastDay())
                      .fxUpdateTime(fxRate.getLatestRefreshTime())
                      .build());
            });
    return result;
  }

  /** 取多筆即期匯率(不含客戶優惠匯率) */
  @FieldsValidate
  public List<CurrencyRate> getSpotRate(@NotNull UseCaseCommand command) {
    if (command.getQueryCurrencies().isEmpty()) {
      return new ArrayList<>();
    }
    List<CurrencyRate> result = new ArrayList<>();
    // 取各帳戶幣別的匯率
    rateFactory
        .getExchangeRateCalculator(RateType.GetSpotRate)
        .getMultiFxbRate(
            command.getQueryCurrencies().stream()
                .map(CurrencyEnum::getByStringValue)
                .collect(Collectors.toList()))
        .forEach(
            fxRate -> {
              result.add(
                  CurrencyRate.builder()
                      .currency(fxRate.getPk().getCurrency().getValue())
                      .buyRate(fxRate.getFxbBuyRate())
                      .sellRate(fxRate.getFxbSellRate())
                      .cusBuyRate(fxRate.getCustomerBuyRate())
                      .cusSellRate(fxRate.getCustomerSellRate())
                      .lowBuyPastDay(fxRate.calculateLowBuyPastDay())
                      .fxUpdateTime(fxRate.getLatestRefreshTime())
                      .build());
            });
    return result;
  }

  /** 取多筆即期匯率(含客戶優惠匯率) */
  @FieldsValidate
  public List<CurrencyRate> getSpotRateWithDiscountRate(@NotNull UseCaseCommand command) {
    if (command.getQueryCurrencies().isEmpty()) {
      return new ArrayList<>();
    }
    List<CurrencyRate> result = new ArrayList<>();
    // 取各帳戶幣別的匯率
    rateFactory
        .getExchangeRateCalculator(RateType.GetSpotRate)
        .getMultiDiscountAndFxbRate(
            command.getQueryCurrencies().stream()
                .map(CurrencyEnum::getByStringValue)
                .collect(Collectors.toList()))
        .forEach(
            fxRate -> {
              result.add(
                  CurrencyRate.builder()
                      .currency(fxRate.getPk().getCurrency().getValue())
                      .buyRate(fxRate.getFxbBuyRate())
                      .sellRate(fxRate.getFxbSellRate())
                      .cusBuyRate(fxRate.getCustomerBuyRate())
                      .cusSellRate(fxRate.getCustomerSellRate())
                      .lowBuyPastDay(fxRate.calculateLowBuyPastDay())
                      .fxUpdateTime(fxRate.getLatestRefreshTime())
                      .build());
            });
    return result;
  }

  @Value
  @Builder
  public static class UseCaseCommand {

    /** 查詢幣別(多筆) */
    @NotNull List<String> queryCurrencies;
  }

  @Value
  @Builder
  public static class CurrencyRate {

    /** 幣別 */
    String currency;
    /** Fxb買匯 */
    BigDecimal buyRate;
    /** Fxb賣匯 */
    BigDecimal sellRate;
    /** 優惠買匯 */
    BigDecimal cusBuyRate;
    /** 優惠賣匯 */
    BigDecimal cusSellRate;
    /** 客戶買價最低點往前推天數 */
    Integer lowBuyPastDay;
    /** 牌告更新時間 */
    LocalDateTime fxUpdateTime;
  }
}
