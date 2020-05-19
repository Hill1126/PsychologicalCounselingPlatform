package org.gdou.common.exception;

import com.baidubce.BceClientException;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.exception.runtime.EsIndexException;
import org.gdou.common.exception.runtime.WebSocketNullPointException;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author HILL
 * @version V1.1
 * @date 2020/3/27
 *
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截参数校验异常，打上info日志并返回提示
     * @Author: HILL
     * @date: 2020/3/2 18:03
     *
     * @param e 具体的异常
     * @return: org.gdou.common.result.Result
    **/
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e){
        List<ObjectError> errors = e.getAllErrors();
        StringBuilder sb = new StringBuilder();
        errors.forEach((error)->{
            log.info("{} 错误信息为：{} ","参数校验不通过",error.getDefaultMessage());
        });

        return ResultGenerator.genFailResult(errors.get(0).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result commonExceptionHandler(Exception e){
        log.error("发生全局异常",e.fillInStackTrace());
        return ResultGenerator.genFailResult("系统发生未知错误,错误信息为："+e.getMessage());
    }

    @ExceptionHandler(BceClientException.class)
    public Result bceClientExceptionHandler(BceClientException e){
        log.error("图片上传失败 详情：{}",e.getMessage());
        return ResultGenerator.genFailResult("图片上传失败，请稍后再试");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException e){
        return ResultGenerator.genFailResult("接口参数校验不通过："+e.getMessage());
    }

    @ExceptionHandler(WebSocketNullPointException.class)
    public Result webSocketUserNullExceptionHandler(WebSocketNullPointException e){
        log.error("连接websocket失败，原因【{}】",e.getMessage());
        return ResultGenerator.genFailResult("请登录后操作");
    }

    @ExceptionHandler(EsIndexException.class)
    public Result EsIndexExceptionHandler(EsIndexException e){
        log.error("文章导入es失败，id为【{}】",e.getArticle().getId());
        log.error(e.getMessage());
        return Result.genFailResult("文章导入es失败，请重试。");
    }


}
