package cn.hejinyo.exception;

import cn.hejinyo.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : HejinYo   hejinyo@gmail.com 2017/8/13 19:12
 * @apiNote :
 */
@RestControllerAdvice
public class ExceptionHandle {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class.getName());

    /**
     * infoException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InfoException.class)
    public Result infoExceptionException(InfoException e) {
        //e.printStackTrace();
        return Result.error(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validException(MethodArgumentNotValidException mnve) {
        //e.printStackTrace();
        return Result.error(mnve.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 500 Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public Result exception(Exception ex, HttpServletResponse response) {
        if (ex instanceof HttpMessageNotReadableException) {
            return Result.error(HttpStatus.BAD_REQUEST.getReasonPhrase());
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            return Result.error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            return Result.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        } else {
            ex.printStackTrace();
        }
        logger.error("系统发生未知错误异常", ex);
        return Result.error("未知错误:" + ex.getMessage());
    }
}
