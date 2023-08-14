package com.example.demo.currency;

import com.example.demo.currency.model.GetCurrencyResponse;
import com.example.demo.domain.exception.ValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetCurrencyFlow {


private final GetCurrencyResponse getCurrencyResponse;

    public GetCurrencyResponse getCurrencyTranslate() throws ValidException {
        // 取codebook外匯幣別設定
        List<String> currencies = getAllAvailableFxCurrencies();
        List<GetMultiFxbRatesUseCase.CurrencyRate> fxRateList = this.getMultiFxbRatesUseCase.getImbRate(
                UseCaseCommand.builder()
                        .queryCurrencies(currencies)
                        .build()
        );
        List<GetCurrencyResponse.CurrencyResponse> currencyList = new ArrayList<>();
        for (String currency : currencies) {
            // 若找不到任何一筆幣別匯率,直接噴出異常
            GetMultiFxbRatesUseCase.CurrencyRate fxRate = fxRateList.stream().filter(v -> Objects.equals(v.getCurrency(), currency)).findFirst()
                    .orElseThrow(() -> new ValidException(
                            ErrorCodeEnum.DATA_NOT_FOUND_ERROR, String.format("%s匯率", currency)));

            Date fxUpdateTime = Date.from(fxRate.getFxUpdateTime().atZone(ZoneId.systemDefault()).toInstant());
            currencyList.add(
                    GetCurrencyResponse.CurrencyResponse.builder()
                            .buyRate(fxRate.getBuyRate())
                            .sellRate(fxRate.getSellRate())
                            .currency(fxRate.getCurrency())
                            .lowBuyPastDay(fxRate.getLowBuyPastDay())
                            .fxUpdateTime(fxUpdateTime)
                            .build()
            );
        }
        return GetCurrencyResponse.builder().currencyList(currencyList).build();
    }
    private List<String> getAllAvailableFxCurrencies() {
        Set<String> result = new HashSet<>();
        // 取codebook by CodeType='FXCurrency'
        List<CodeBook> codeBooks = codeBookForexSettingsPersistence.findAllFxCurrencySortByOrdinalAsc();
        codeBooks.forEach(data -> {
            // 撈出CodeBook CodeId為幣別縮寫, ex: USD/EUR , *注意需排除TWD幣別
            if (!data.getCodeId().equalsIgnoreCase("TWD")) {
                result.add(data.getCodeId());
            }
        });
        return new ArrayList<>(result).stream().distinct().collect(Collectors.toList());
    }
}
