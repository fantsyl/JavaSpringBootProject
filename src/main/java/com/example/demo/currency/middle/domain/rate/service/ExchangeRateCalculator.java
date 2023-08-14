package com.example.demo.currency.middle.domain.rate.service;


public interface ExchangeRateCalculator {

  /**
   * 取匯率模式(給匯率工廠呼叫使用)
   *
   * @return 匯率模式 enum
   */
  RateType getRateType();

  /**
   * 以客戶買賣角度換取單一買賣方向的匯率 | 找不到匯率回傳success=true + data為空值 | 取匯率異常回傳success=false
   *
   * @param customerSellCurrency 客戶賣出幣別
   * @param customerBuyCurrency  客戶買入幣別
   * @return 牌告、客戶優惠買賣匯率 及 最後更新時間
   */
  Result<TransRateForCustomer> getRateForCustomer(@NotNull Currency.CurrencyEnum customerSellCurrency,
      @NotNull Currency.CurrencyEnum customerBuyCurrency);

  /**
   * 以銀行角度換取買賣匯率 | 找不到匯率回傳success=true + data為空值 | 取匯率異常回傳success=false
   *
   * @param queryCurrency 指定查詢幣別
   * @return 牌告買賣匯率 及 最後更新時間
   */
  Result<FxRate> getFxbRate(@NotNull CurrencyEnum queryCurrency);

  /**
   * 以銀行角度換取買賣匯率(含客戶優惠買賣匯率) | 找不到匯率回傳success=true + data為空值 | 取匯率異常回傳success=false
   *
   * @param queryCurrency 指定查詢幣別
   * @return 牌告、客戶優惠買賣匯率 及 最後更新時間
   */
  Result<FxRate> getFxbAndDiscountRate(@NotNull CurrencyEnum queryCurrency);

  /**
   * 換取多筆買賣匯率 (以銀行買賣角度查詢),包含優惠匯率資訊、客戶買價最低點資訊 | 回傳清單僅包含找得到的幣別資訊
   *
   * @param queryCurrencies 指定查詢幣別(多筆)
   * @return 牌告、最後更新時間 及 客戶買價最低點資訊(多筆)
   */
  List<FxRate> getMultiFxbRate(@NotNull List<CurrencyEnum> queryCurrencies);

  /**
   * 換取多筆買賣匯率 (以銀行買賣角度查詢) | 回傳清單僅包含找得到的幣別資訊
   *
   * @param queryCurrencies 指定查詢幣別(多筆)
   * @return 優惠匯率、牌告匯率、最後更新時間(多筆)
   */
  List<FxRate> getMultiDiscountAndFxbRate(@NotNull List<CurrencyEnum> queryCurrencies);

}
