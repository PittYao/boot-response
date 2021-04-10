package com.fanyao.boot.response.exception;

import com.fanyao.boot.response.CommonResult;
import com.fanyao.boot.response.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: bugProvider
 * @date: 2019/10/8 09:45
 * @description: 异常处理
 */
@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    /**
     * 处理所有业务异常
     * @param e 业务异常
     * @return 异常信息
     */
    @ExceptionHandler(BusinessException.class)
    CommonResult handleBusinessException(BusinessException e){
        log.error("业务异常-> {}",e.getMessage(), e);
        return CommonResult.errorResult(e.getCode(), e.getMessage());
    }

    /**
     * 处理所有不可知的异常
     * @param e 运行时异常
     * @return 异常信息
     */
    @ExceptionHandler(Exception.class)
    CommonResult handleException(Exception e){
        log.error("系统运行时异常-> {}",e.getMessage(), e);
        return CommonResult.errorResult(ResultEnum.ERROR);
    }



    /**
     * 处理所有接口数据验证异常
     * @param e 参数异常
     * @return 异常信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("参数校验异常->请求参数: {}  错误: {}  异常:", Objects.requireNonNull(e.getBindingResult().getTarget()).toString(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), e);
        return getErrorList(e.getBindingResult());
    }

    /**
     * 获取参数校验具体异常信息
     * @param bindingResult
     */
    private CommonResult getErrorList(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorList = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        return CommonResult.errorResult(ResultEnum.PARAM_VERIFICATION_FAILED, errorList);
    }
}
