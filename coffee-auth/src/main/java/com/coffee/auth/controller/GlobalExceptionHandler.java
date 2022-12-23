package com.coffee.auth.controller;

import com.coffee.auth.security.exception.TokenAuthenticationException;
import com.coffee.common.core.Constant;
import com.coffee.common.core.R;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * 全局异常处理
 * @author rabit
 * @version v1.0
 * @date 2022/9/9 11:08
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * token校验异常
     * @param ex 异常类
     * @return 返回结果
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleTypeMismatchException(AuthenticationException ex){
        return R.error(Constant.TOKEN_ERROR_CODE,ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleTypeMismatchException(AccessDeniedException ex){
        return R.error(Constant.PERMISSION_DENIED_CODE,ex.getMessage());
    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
//    public R<?> handleTypeMismatchException(UsernameNotFoundException ex){
//        return R.error(Constant.TOKEN_ERROR_CODE,ex.getMessage());
//    }
//
    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleTypeMismatchException(DisabledException ex){
        return R.error(Constant.TOKEN_ERROR_CODE,ex.getMessage());
    }
//
//    @ExceptionHandler(AuthenticationServiceException.class)
//    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
//    public R<?> handleTypeMismatchException(AuthenticationServiceException ex){
//        return R.error(Constant.TOKEN_ERROR_CODE,ex.getMessage());
//    }
}
