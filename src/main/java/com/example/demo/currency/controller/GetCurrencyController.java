package com.example.demo.currency.controller;

import com.example.demo.currency.model.GetCurrencyResponse;
import com.example.demo.currency.presentation.GetCurrencyPresentation;
import com.example.demo.domain.exception.ValidException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "Currency API", description = "")
@RestController
@RequestMapping("/currency")
public class GetCurrencyController {
    private final GetCurrencyPresentation getCurrencyPresentation;

    //constructor
    public GetCurrencyController(GetCurrencyPresentation getCurrencyPresentation) {
        this.getCurrencyPresentation = getCurrencyPresentation;
    }

    @ApiOperation(
            value = "牌告匯率",
            notes = "取得牌告匯率列表",
            response = GetCurrencyResponse.class
    )
    @PostMapping("/translate")
    public GetCurrencyResponse getCurrencyTranslate() throws ValidException {
        return this.getCurrencyPresentation.toResponse();
    }

}

