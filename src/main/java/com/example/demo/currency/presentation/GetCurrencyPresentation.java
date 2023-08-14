package com.example.demo.currency.presentation;

import com.example.demo.currency.GetCurrencyFlow;
import com.example.demo.currency.model.GetCurrencyResponse;
import com.example.demo.domain.exception.ValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class GetCurrencyPresentation {
        private final GetCurrencyFlow getCurrencyFlow;

        public GetCurrencyResponse toResponse() throws ValidException {
            return this.getCurrencyFlow.getCurrencyTranslate();
        }


}
