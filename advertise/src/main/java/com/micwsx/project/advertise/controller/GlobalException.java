package com.micwsx.project.advertise.controller;

import com.micwsx.project.advertise.viewmodel.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class GlobalException {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    public ModelAndView errorHandler(HttpServletRequest request, Exception ex) throws Exception {
        ModelAndView mv=new ModelAndView();
        String url = request.getRequestURL().toString();
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        if (ex instanceof BusinessException) {
            BusinessException ex1 = (BusinessException) ex;
            ex1.setUrl(url);
            ex1.setCode(statusCode);
            mv.addObject("message", ex1.toString());
            logger.error(ex1.toString(),ex);
        }else{
            mv.addObject("message", ex.getMessage());
            logger.error(ex.getMessage());
        }
        mv.addObject("title", "异常页面");
        mv.setViewName("error");
        return mv;
    }
}
