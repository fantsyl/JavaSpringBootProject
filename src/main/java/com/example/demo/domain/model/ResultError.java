package com.example.demo.domain.model;

import com.example.demo.domain.constant.ErrorInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;

/**
 * Result Error API預期的例外錯誤
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "API預期的例外錯誤")
public class ResultError implements ErrorInfo {

    /** 錯誤代碼 */
    @JsonProperty(value = "errorCode")
    @ApiModelProperty(value = "錯誤代碼", position = 1)
    private String errorCode;

    /** 錯誤訊息 */
    @JsonProperty(value = "errorMessage")
    @ApiModelProperty(value = "錯誤訊息", position = 2)
    private String errorMessage;

    /** constructor */
    public ResultError(ErrorInfo error, String... msgs) {
        String errorMsg = error.getErrorMessage();
        if (ArrayUtils.isNotEmpty(msgs)) {
            MessageFormat fmt = new MessageFormat(errorMsg);
            errorMsg = fmt.format(msgs);
        }
        this.errorCode = error.getErrorCode();
        this.errorMessage = errorMsg;
    }
}
