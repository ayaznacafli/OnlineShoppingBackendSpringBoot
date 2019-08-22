package com.ayaz.onlineshopping.controller.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {


    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handlerNoHandlerFoundException() {
        ModelAndView model = new ModelAndView();
        model.addObject("errorTitle", "The page is not constructed!");
        model.addObject("errorDescription", "The page you are looking for is not available now!");
        model.addObject("title", "404 Error Page");
        return model;
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ModelAndView handlerProductNotFoundException() {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorTitle", "Product not available!");
        model.addObject("errorDescription", "The product you are looking for is not available right now!");
        model.addObject("title", "Product Unavailable");
        return model;
    }


    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(Exception ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorTitle", "Contact Your Administrator!!");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);

        model.addObject("errorDescription", sw.toString());
        model.addObject("title", "Error");
        return model;
    }


}
