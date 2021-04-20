package com.lagou.edu.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

// 用于对Controller进行切面
@ControllerAdvice
public class GlobalExceptionResolver {

    // 捕获Controller中抛出的指定类型的异常，从而达到不同类型的异常区别处理的目的
    @ExceptionHandler({ArithmeticException.class})
    public ModelAndView handleException(ArithmeticException exception, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg",exception.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
