package com.cathy.interviewproject.restful;


import com.cathy.interviewproject.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    public static final String CONTROLLER_NAME = "CurrencyController";

    @RequestMapping(value="",method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody CurrencyDTO inputDTO){
        String logUser = "system";
        String logHandle = CONTROLLER_NAME+".create";
        try{
            CurrencyDTO result = currencyService.create(inputDTO);
            System.out.println("success,logHandle:"+logHandle+",logUser:"+logUser);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e){
            MsgDTO errorDTO = new MsgDTO();
            errorDTO.setCode("server_error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body(errorDTO);
        }
    }

    @RequestMapping(value="/get",method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getById(@PathVariable String currency){
        String logUser = "system";
        String logHandle = CONTROLLER_NAME+".getById";
        try{
            CurrencyDTO result = currencyService.get(currency);
            System.out.println("success,logHandle:"+logHandle+",logUser:"+logUser);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e){
            MsgDTO errorDTO = new MsgDTO();
            errorDTO.setCode("server_error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body(errorDTO);
        }
    }

    @RequestMapping(value="/update",method= RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable CurrencyDTO inputDTO){
        String logUser = "system";
        String logHandle = CONTROLLER_NAME+".update";
        try{
            CurrencyDTO result = currencyService.update(inputDTO);
            System.out.println("success,logHandle:"+logHandle+",logUser:"+logUser);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e){
            MsgDTO errorDTO = new MsgDTO();
            errorDTO.setCode("server_error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body(errorDTO);
        }
    }

    @RequestMapping(value="/delete",method= RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable String currency){
        String logUser = "system";
        String logHandle = CONTROLLER_NAME+".delete";
        try{
            currencyService.delete(currency);
            System.out.println("success,logHandle:"+logHandle+",logUser:"+logUser);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch(Exception e){
            MsgDTO errorDTO = new MsgDTO();
            errorDTO.setCode("server_error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body(errorDTO);
        }
    }
}
