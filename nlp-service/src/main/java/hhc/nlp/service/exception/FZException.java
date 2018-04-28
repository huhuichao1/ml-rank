package hhc.nlp.service.exception;


import hhc.common.entity.ExceptionEnum;

/**
 * Created by mingyang on 2017/6/15.
 */
public class FZException extends Exception {
    private static final long serialVersionUID = -5984598672603910431L;
    private int               errCode;

    public FZException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public FZException(ExceptionEnum restReturnEnum) {
        super(restReturnEnum.getDesc());
        this.errCode = restReturnEnum.getCode();
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
