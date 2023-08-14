package com.example.demo.domain.exception;

import com.example.demo.domain.constant.ErrorInfo;
import com.example.demo.domain.model.ResultError;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 共用的Exception，主要用來呈現預期中的檢核錯誤
 */
@Data
public class ValidException extends Exception {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 錯誤訊息集合
     */
    private List<ResultError> resultErrors;

    /**
     * Constructor
     */
    public ValidException() {
        super();
        this.resultErrors = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param resultErrors 錯誤訊息列表
     */
    public ValidException(List<ResultError> resultErrors) {
        super();
        this.resultErrors = resultErrors;
    }

    /**
     * Constructor
     */
    public ValidException(String errorCode, String errorMessage) {
        this();
        this.resultErrors.add(new ResultError(errorCode, errorMessage));
    }

    /**
     * Constructor for ResultError
     */
    public ValidException(ResultError resultError) {
        this();
        this.resultErrors.add(resultError);
    }

    /**
     * Constructor for ErrorInfo
     */
    public ValidException(ErrorInfo error, String... msgs) {
        this();
        this.addErrorInfo(error, msgs);
    }


    /**
     * Constructor - caused by
     *
     * @param causedByThrowable 此錯誤造成的
     **/
    public ValidException(Throwable causedByThrowable) {
        super(causedByThrowable);
        this.resultErrors = new ArrayList<>();
    }

    /**
     * Constructor - caused by for ErrorInfo
     *
     * @param causedByThrowable 此錯誤造成的
     **/
    public ValidException(Throwable causedByThrowable, ErrorInfo error, String... msgs) {
        this(causedByThrowable);
        this.addErrorInfo(error, msgs);
    }

    /**
     * 回傳第一個ResultError
     *
     * @return ResultError Result Error API預期的例外錯誤
     */
    public ResultError getResultError() {
        return CollectionUtils.isEmpty(resultErrors) ? null : this.resultErrors.get(0);
    }

    /**
     * 取得錯誤訊息
     */
    @Override
    public String getMessage() {
        return CollectionUtils.emptyIfNull(this.resultErrors).toString();
    }

    /**
     * ErrorInfo to ResultError
     *
     * @param error ErrorInfo
     * @param msgs  format args
     */
    private void addErrorInfo(ErrorInfo error, String... msgs) {
        String errorMsg = error.getErrorMessage();
        if (ArrayUtils.isNotEmpty(msgs)) {
            MessageFormat fmt = new MessageFormat(errorMsg);
            errorMsg = fmt.format(msgs);
        }
        this.resultErrors.add(new ResultError(error.getErrorCode(), errorMsg));
    }
}
