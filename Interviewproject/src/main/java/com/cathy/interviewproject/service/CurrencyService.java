package com.cathy.interviewproject.service;

import com.cathy.interviewproject.domain.Currency;
import com.cathy.interviewproject.repository.CurrencyRepository;
import com.cathy.interviewproject.restful.CoinDeskCurrencyDTO;
import com.cathy.interviewproject.restful.CoinDeskDTO;
import com.cathy.interviewproject.restful.CurrencyDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CurrencyService {
    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private CurrencyRepository currencyRepository;

    public CurrencyDTO create(CurrencyDTO inputDTO) throws ParseException {
        Currency c = new Currency(inputDTO.getCurrency());
        c = copy(c, inputDTO);
        return createDTO(c);
    }

    public CurrencyDTO get(String currency) {
        Currency c = currencyRepository.getReferenceById(currency);
        return createDTO(c);
    }

    public CurrencyDTO update(CurrencyDTO inputDTO) throws ParseException {
        Optional<Currency> op = currencyRepository.findById(inputDTO.getCurrency());
        if(!op.isPresent()){
            return null;
        }
        Currency c = op.get();
        c = copy(c,inputDTO);
        c = currencyRepository.save(c);
        return createDTO(c);
    }

    public void delete(String currency) {
        currencyRepository.deleteById(currency);
    }

    public List<CurrencyDTO> createCoinDeskJsonToDB() throws IOException, ParseException {
        CoinDeskDTO cdDTO = getJsonStrByUrl();
        Map<String, CoinDeskCurrencyDTO> map = cdDTO.getBpi();
        List<CurrencyDTO> dtos = new ArrayList<>();
        for(Map.Entry<String,CoinDeskCurrencyDTO> entry:map.entrySet()){
            String currency = entry.getKey();
            String currencyCn = "";
            CoinDeskCurrencyDTO cdcDTO = entry.getValue();
            switch (currency){
                case "USD":
                    currencyCn = "美金";
                    break;
                case "GBD":
                    currencyCn="英鎊";
                    break;
                case "EUR":
                    currencyCn="歐元";
                    break;
                            }
            CurrencyDTO dto = new CurrencyDTO();
            dto.setCurrency(currency);
            dto.setCurrency_name_cn(currencyCn);
            dto.setExchange_rate(cdcDTO.getRate_float());
            dto.setUpdated_date(cdDTO.getTime().getUpdated());
            create(dto);
            dtos.add(dto);
        }
        return dtos;
    }

    //OTHER
    public Currency copy(Currency c, CurrencyDTO inputDTO) throws ParseException {
        if (inputDTO.getCurrency_name_cn() != null) {
            c.setCurrency_name_cn(inputDTO.getCurrency_name_cn());
        }
        if (inputDTO.getExchange_rate() != null) {
            c.setExchange_rate(inputDTO.getExchange_rate());
        }
        if (inputDTO.getUpdated_date() != null) {
            c.setUpdated_date(getDateByString(inputDTO.getUpdated_date()));
        }
        return c;
    }

    public CurrencyDTO createDTO(Currency c) {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setCurrency(c.getCurrency());
        dto.setCurrency_name_cn(c.getCurrency_name_cn());
        dto.setExchange_rate(c.getExchange_rate());
        dto.setUpdated_date(c.getUpdated_date().toString());
        return dto;
    }

    public Date getDateByString(String date) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date);
        return date1;
    }

    public CoinDeskDTO getJsonStrByUrl() throws IOException {
        URL url = new URL("http://example.com/json");
        InputStreamReader reader = new InputStreamReader(url.openStream());
        CoinDeskDTO dto = new Gson().fromJson(reader, CoinDeskDTO.class);
        reader.close();
        return dto;
    }

}
