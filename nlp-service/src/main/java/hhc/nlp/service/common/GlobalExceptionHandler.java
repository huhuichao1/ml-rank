package hhc.nlp.service.common;


import hhc.common.entity.ExceptionEnum;
import hhc.common.entity.RestResponse;
import hhc.nlp.service.exception.FZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = { Exception.class, RuntimeException.class })
    @ResponseBody
    public RestResponse handler(Exception e) {
        LOGGER.error("异常信息", e);
        RestResponse error = new RestResponse();
        if (e instanceof FZException) {
            error.setStatus(((FZException) e).getErrCode());
            error.setMessage(e.getMessage());
        } else {
            error.setStatus(ExceptionEnum.FAILED.getCode());
            error.setMessage(e.getMessage());
        }
        return error;
    }

    /**
     * 处理所有接口数据验证异常
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(value = { BindException.class })
    @ResponseBody
    public RestResponse handleMethodArgumentNotValidException(BindException e) {
        LOGGER.error("请求参数不合法", e);
        RestResponse error = new RestResponse();
        error.setStatus(ExceptionEnum.PARAMTER_INVALID.getCode());// 参数校验异常
        error.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return error;
    }
}
